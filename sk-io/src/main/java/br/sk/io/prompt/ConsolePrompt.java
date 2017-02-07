package br.sk.io.prompt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import br.sk.io.components.ConfirmUI;
import br.sk.io.components.InputUI;
import br.sk.io.components.PromptableUI;
import br.sk.io.components.SelectManyUI;
import br.sk.io.components.SelectOneUI;
import br.sk.io.prompt.answer.AnswerUI;
import br.sk.io.prompt.answer.SelectManyUIAnswer;
import br.sk.io.prompt.answer.ConfirmUIAnswer;
import br.sk.io.prompt.answer.InputUIAnswer;
import br.sk.io.prompt.answer.SelectOneAnswer;
import br.sk.io.prompt.builder.PromptBuilder;

/**
 * ConsolePrompt encapsulates the prompting of a list of input questions for the
 * user.
 * <p>
 * Created by Andreas Wegmann on 20.01.16.
 */
public class ConsolePrompt {
	// input prompt implementation
	private InputUIPrompt inputPrompt;

	// checkbox prompt implementation
	private SelectManyUIPrompt checkboxPrompt;

	// list box prompt implementation
	private SelectOneUIPrompt listPrompt;

	// confirmation prompt implementation
	private ConfirmUIPrompt confirmPrompt;

	/* Lazy getter for input prompt */
	private InputUIPrompt getInputPrompt() throws IOException {
		if (inputPrompt == null) {
			inputPrompt = new InputUIPrompt();
		}
		return inputPrompt;
	}

	/* Lazy getter for checkbox prompt */
	private SelectManyUIPrompt getCheckboxPrompt() throws IOException {
		if (checkboxPrompt == null) {
			checkboxPrompt = new SelectManyUIPrompt();
		}
		return checkboxPrompt;
	}

	/* Lazy getter for list prompt */
	private SelectOneUIPrompt getListPrompt() throws IOException {
		if (listPrompt == null) {
			listPrompt = new SelectOneUIPrompt();
		}
		return listPrompt;
	}

	/* Lazy getter for confirm prompt */
	private ConfirmUIPrompt getConfirmPrompt() throws IOException {
		if (confirmPrompt == null) {
			confirmPrompt = new ConfirmUIPrompt();
		}
		return confirmPrompt;
	}

	/**
	 * Default constructor for this class.
	 */
	public ConsolePrompt() {
	}

	/**
	 * Prompt a list of choices (questions). This method takes a list of
	 * promptable elements, typically created with {@link PromptBuilder}. Each
	 * of the elements is processed and the user entries and answers are filled
	 * in to the result map. The result map contains the key of each promtable
	 * element and the user entry as an object implementing {@link AnswerUI}.
	 *
	 * @param promptableElementList
	 *            the list of questions / promts to ask the user for.
	 * @return a map containing a result for each element of
	 *         promptableElementList
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	public HashMap<String, ? extends AnswerUI> prompt(List<PromptableUI> promptableElementList) throws IOException {
		HashMap<String, AnswerUI> answers = new HashMap<String, AnswerUI>();

		for (int i = 0; i < promptableElementList.size(); i++) {
			PromptableUI promptableElement = promptableElementList.get(i);
			if (promptableElement instanceof SelectOneUI) {
				doListPrompt(answers, promptableElement);
			} else if (promptableElement instanceof InputUI) {
				doInputPrompt(answers, promptableElement);
			} else if (promptableElement instanceof SelectManyUI) {
				doCheckboxPrompt(answers, promptableElement);
			} else if (promptableElement instanceof ConfirmUI) {
				doConfirmChoicePrompt(answers, promptableElement);
			} else {
				throw new IllegalArgumentException("wrong type of promptable element");
			}
		}
		return answers;
	}

	private void doInputPrompt(HashMap<String, AnswerUI> answers, PromptableUI promptableElement) throws IOException {
		InputUI inputValue = (InputUI) promptableElement;
		if (inputValue.getFnWhen() != null) {
			if (inputValue.getFnWhen().apply(answers)) {
				InputUIAnswer result = doPrompt(inputValue, answers);
				answers.put(promptableElement.getName(), result);
			}
		} else {
			InputUIAnswer result = doPrompt(inputValue, answers);
			answers.put(promptableElement.getName(), result);
		}
	}

	private void doListPrompt(HashMap<String, AnswerUI> answers, PromptableUI promptableElement) throws IOException {
		SelectOneUI listChoice = (SelectOneUI) promptableElement;
		if (listChoice.getFnWhen() != null) {
			if (listChoice.getFnWhen().apply(answers)) {
				SelectOneAnswer result = doPrompt(listChoice, answers);
				answers.put(promptableElement.getName(), result);
			}
		} else {
			SelectOneAnswer result = doPrompt(listChoice, answers);
			answers.put(promptableElement.getName(), result);
		}
	}

	private void doCheckboxPrompt(HashMap<String, AnswerUI> answers, PromptableUI promptableElement) throws IOException {
		SelectManyUI checkbox = (SelectManyUI) promptableElement;
		if (checkbox.getFnWhen() != null) {
			if (checkbox.getFnWhen().apply(answers)) {
				SelectManyUIAnswer result = doPrompt(checkbox, answers);
				answers.put(promptableElement.getName(), result);
			}
		} else {
			SelectManyUIAnswer result = doPrompt(checkbox, answers);
			answers.put(promptableElement.getName(), result);
		}
	}

	private void doConfirmChoicePrompt(HashMap<String, AnswerUI> answers, PromptableUI promptableElement) throws IOException {
		ConfirmUI confirmChoice = (ConfirmUI) promptableElement;
		if (confirmChoice.getFnWhen() != null) {
			if (confirmChoice.getFnWhen().apply(answers)) {
				ConfirmUIAnswer result = doPrompt(confirmChoice, answers);
				answers.put(promptableElement.getName(), result);
			}
		} else {
			ConfirmUIAnswer result = doPrompt(confirmChoice, answers);
			answers.put(promptableElement.getName(), result);
		}
	}

	/**
	 * Process a {@link ConfirmUI}.
	 *
	 * @param confirmChoice
	 *            the confirmation to ask the user for.
	 * @return Object of type {@link ConfirmUIAnswer} holding the users answer
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	private ConfirmUIAnswer doPrompt(ConfirmUI confirmChoice, HashMap<String, AnswerUI> answers) throws IOException {
		return getConfirmPrompt().prompt(confirmChoice, answers);
	}

	/**
	 * Process a {@link SelectOneUI}.
	 *
	 * @param listChoice
	 *            the list to let the user choose an item from.
	 * @return Object of type {@link SelectOneAnswer} holding the uses choice.
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	private SelectOneAnswer doPrompt(SelectOneUI listChoice, HashMap<String, AnswerUI> answers) throws IOException {
		return getListPrompt().prompt(listChoice, answers);
	}

	/**
	 * Process a {@link InputUI}.
	 *
	 * @param inputValue
	 *            the input value to ask the user for.
	 * @return Object of type {@link InputUIAnswer} holding the uses input.
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	private InputUIAnswer doPrompt(InputUI inputValue, HashMap<String, AnswerUI> answers) throws IOException {
		return getInputPrompt().prompt(inputValue, answers);
	}

	/**
	 * Process a {@link SelectManyUI}.
	 *
	 * @param checkbox
	 *            the checkbox displayed where the user can check values.
	 * @return Object of type {@link SelectManyUIAnswer} holding the uses choice.
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	private SelectManyUIAnswer doPrompt(SelectManyUI checkbox, HashMap<String, AnswerUI> answers) throws IOException {
		return getCheckboxPrompt().prompt(checkbox, answers);
	}

	/**
	 * Creates a {@link PromptBuilder}.
	 *
	 * @return a new prompt builder object.
	 */
	public PromptBuilder getPromptBuilder() {
		return new PromptBuilder();
	}
}
