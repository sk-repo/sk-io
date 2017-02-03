package br.sk.io.prompt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import br.sk.io.elements.Checkbox;
import br.sk.io.elements.ConfirmChoice;
import br.sk.io.elements.InputValue;
import br.sk.io.elements.ListChoice;
import br.sk.io.elements.PromptableElementIF;
import br.sk.io.prompt.answer.Answer;
import br.sk.io.prompt.answer.CheckboxAnswer;
import br.sk.io.prompt.answer.ConfirmAnswer;
import br.sk.io.prompt.answer.InputAnswer;
import br.sk.io.prompt.answer.ListAnswer;
import br.sk.io.prompt.builder.PromptBuilder;

/**
 * ConsolePrompt encapsulates the prompting of a list of input questions for the
 * user.
 * <p>
 * Created by Andreas Wegmann on 20.01.16.
 */
public class ConsolePrompt {
	// input prompt implementation
	private InputPrompt inputPrompt;

	// checkbox prompt implementation
	private CheckboxPrompt checkboxPrompt;

	// list box prompt implementation
	private ListPrompt listPrompt;

	// confirmation prompt implementation
	private ConfirmPrompt confirmPrompt;

	/* Lazy getter for input prompt */
	private InputPrompt getInputPrompt() throws IOException {
		if (inputPrompt == null) {
			inputPrompt = new InputPrompt();
		}
		return inputPrompt;
	}

	/* Lazy getter for checkbox prompt */
	private CheckboxPrompt getCheckboxPrompt() throws IOException {
		if (checkboxPrompt == null) {
			checkboxPrompt = new CheckboxPrompt();
		}
		return checkboxPrompt;
	}

	/* Lazy getter for list prompt */
	private ListPrompt getListPrompt() throws IOException {
		if (listPrompt == null) {
			listPrompt = new ListPrompt();
		}
		return listPrompt;
	}

	/* Lazy getter for confirm prompt */
	private ConfirmPrompt getConfirmPrompt() throws IOException {
		if (confirmPrompt == null) {
			confirmPrompt = new ConfirmPrompt();
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
	 * element and the user entry as an object implementing {@link Answer}.
	 *
	 * @param promptableElementList
	 *            the list of questions / promts to ask the user for.
	 * @return a map containing a result for each element of
	 *         promptableElementList
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	public HashMap<String, ? extends Answer> prompt(List<PromptableElementIF> promptableElementList) throws IOException {
		HashMap<String, Answer> answers = new HashMap<String, Answer>();

		for (int i = 0; i < promptableElementList.size(); i++) {
			PromptableElementIF promptableElement = promptableElementList.get(i);
			if (promptableElement instanceof ListChoice) {
				doListPrompt(answers, promptableElement);
			} else if (promptableElement instanceof InputValue) {
				doInputPrompt(answers, promptableElement);
			} else if (promptableElement instanceof Checkbox) {
				doCheckboxPrompt(answers, promptableElement);
			} else if (promptableElement instanceof ConfirmChoice) {
				doConfirmChoicePrompt(answers, promptableElement);
			} else {
				throw new IllegalArgumentException("wrong type of promptable element");
			}
		}
		return answers;
	}

	private void doInputPrompt(HashMap<String, Answer> answers, PromptableElementIF promptableElement) throws IOException {
		InputValue inputValue = (InputValue) promptableElement;
		if (inputValue.getFnWhen() != null) {
			if (inputValue.getFnWhen().apply(answers)) {
				InputAnswer result = doPrompt(inputValue, answers);
				answers.put(promptableElement.getName(), result);
			}
		} else {
			InputAnswer result = doPrompt(inputValue, answers);
			answers.put(promptableElement.getName(), result);
		}
	}

	private void doListPrompt(HashMap<String, Answer> answers, PromptableElementIF promptableElement) throws IOException {
		ListChoice listChoice = (ListChoice) promptableElement;
		if (listChoice.getFnWhen() != null) {
			if (listChoice.getFnWhen().apply(answers)) {
				ListAnswer result = doPrompt(listChoice, answers);
				answers.put(promptableElement.getName(), result);
			}
		} else {
			ListAnswer result = doPrompt(listChoice, answers);
			answers.put(promptableElement.getName(), result);
		}
	}

	private void doCheckboxPrompt(HashMap<String, Answer> answers, PromptableElementIF promptableElement) throws IOException {
		Checkbox checkbox = (Checkbox) promptableElement;
		if (checkbox.getFnWhen() != null) {
			if (checkbox.getFnWhen().apply(answers)) {
				CheckboxAnswer result = doPrompt(checkbox, answers);
				answers.put(promptableElement.getName(), result);
			}
		} else {
			CheckboxAnswer result = doPrompt(checkbox, answers);
			answers.put(promptableElement.getName(), result);
		}
	}

	private void doConfirmChoicePrompt(HashMap<String, Answer> answers, PromptableElementIF promptableElement) throws IOException {
		ConfirmChoice confirmChoice = (ConfirmChoice) promptableElement;
		if (confirmChoice.getFnWhen() != null) {
			if (confirmChoice.getFnWhen().apply(answers)) {
				ConfirmAnswer result = doPrompt(confirmChoice, answers);
				answers.put(promptableElement.getName(), result);
			}
		} else {
			ConfirmAnswer result = doPrompt(confirmChoice, answers);
			answers.put(promptableElement.getName(), result);
		}
	}

	/**
	 * Process a {@link ConfirmChoice}.
	 *
	 * @param confirmChoice
	 *            the confirmation to ask the user for.
	 * @return Object of type {@link ConfirmAnswer} holding the users answer
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	private ConfirmAnswer doPrompt(ConfirmChoice confirmChoice, HashMap<String, Answer> answers) throws IOException {
		return getConfirmPrompt().prompt(confirmChoice, answers);
	}

	/**
	 * Process a {@link ListChoice}.
	 *
	 * @param listChoice
	 *            the list to let the user choose an item from.
	 * @return Object of type {@link ListAnswer} holding the uses choice.
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	private ListAnswer doPrompt(ListChoice listChoice, HashMap<String, Answer> answers) throws IOException {
		return getListPrompt().prompt(listChoice, answers);
	}

	/**
	 * Process a {@link InputValue}.
	 *
	 * @param inputValue
	 *            the input value to ask the user for.
	 * @return Object of type {@link InputAnswer} holding the uses input.
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	private InputAnswer doPrompt(InputValue inputValue, HashMap<String, Answer> answers) throws IOException {
		return getInputPrompt().prompt(inputValue, answers);
	}

	/**
	 * Process a {@link Checkbox}.
	 *
	 * @param checkbox
	 *            the checkbox displayed where the user can check values.
	 * @return Object of type {@link CheckboxAnswer} holding the uses choice.
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	private CheckboxAnswer doPrompt(Checkbox checkbox, HashMap<String, Answer> answers) throws IOException {
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
