package br.sk.io.console.prompt;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.Ansi.Color;

import br.sk.io.console.components.InputUI;
import br.sk.io.console.core.AbstractPrompt;
import br.sk.io.console.core.AnswerUI;
import br.sk.io.console.core.CUIRenderer;
import br.sk.io.console.core.ConsoleReaderImpl;
import br.sk.io.console.core.PromptIF;
import br.sk.io.console.core.ReaderIF;
import br.sk.io.console.exception.InvalidInputException;
import br.sk.io.console.prompt.answer.InputUIAnswer;
import jline.console.completer.Completer;

/**
 * Implementation of the input choice prompt. The user will be asked for a
 * string input value. With support of completers an automatic expansion of
 * strings and filenames can be configured. Defining a mask character, a
 * password like input is possible.
 * <p>
 * User: Andreas Wegmann
 * <p>
 * Date: 06.01.16
 */
public class InputUIPrompt extends AbstractPrompt implements PromptIF<InputUI, InputUIAnswer> {

	private InputUI inputElement;
	private ReaderIF reader;
	CUIRenderer itemRenderer = CUIRenderer.getRenderer();
	private boolean invalidInput = false;
	ReaderIF.ReaderInput readerInput;
	private String lineInput;
	Character mask;
	List<Completer> completer;
	private String prompt;
	private String message;

	public InputUIPrompt() throws IOException {
	}

	public InputUIAnswer prompt(InputUI inputElement, HashMap<String, AnswerUI> answers) throws IOException {
		this.inputElement = inputElement;
		this.message = this.inputElement.getFnMessage() != null ? this.inputElement.getFnMessage().apply(answers)
				: this.inputElement.getMessage();

		/*
		 * if (reader == null) { reader = new ConsoleReaderImpl(); }
		 */

		if (renderHeight == 0) {
			renderHeight = 1;
		} else {
			System.out.println(ansi().cursorUp(renderHeight));
		}

		this.completer = inputElement.getCompleter();
		this.mask = inputElement.getMask();

		do {
			prompt();
			read(inputElement, prompt, completer, mask);
			validateInput(inputElement, lineInput, readerInput);
		} while (this.invalidInput);

		String result;
		if (mask == null) {
			result = lineInput;
		} else {
			result = "";
			if (lineInput != null) {
				for (int i = 0; i < lineInput.length(); i++) {
					result += mask;
				}
			}
		}

		renderMessagePromptAndResult(this.message, result);

		return new InputUIAnswer(lineInput);
	}

	private void prompt() {
		String optionalDefaultValue = itemRenderer.renderOptionalDefaultValue(this.inputElement);
		this.prompt = renderMessagePrompt(this.message, invalidInput) + optionalDefaultValue;
	}

	private void read(InputUI inputElement, String prompt, List<Completer> completer, Character mask) throws IOException {
		this.reader = new ConsoleReaderImpl();
		this.readerInput = reader.readLine(completer, prompt, inputElement.getValue(), mask);
		this.lineInput = readerInput.getLineInput();
		setupDefaultValue(inputElement);
	}

	private void setupDefaultValue(InputUI inputElement) {
		if (lineInput == null || lineInput.trim().length() == 0) {
			lineInput = inputElement.getDefaultValue();
		}
	}

	private void validateInput(InputUI inputElement, String lineInput, ReaderIF.ReaderInput readerInput) throws IOException {
		Consumer<String> validator = inputElement.getValidator();
		invalidInput = false;
		if (validator != null) {
			try {
				validator.accept(lineInput);
			} catch (InvalidInputException e) {
				invalidInput = true;
				System.out.print(ansi().cursorDown(renderHeight).fg(Color.RED).a(">> ").reset().a(e.getMessage()));
				System.out.println(ansi().cursorUp(renderHeight).eraseLine());
			}
		}
		if (inputElement.getRequired() != null && StringUtils.isEmpty(lineInput)) {
			invalidInput = true;
			System.out.print(ansi().cursorDown(renderHeight).fg(Color.RED).a(">> ").reset().a("Preenchimento obrigatório"));
			System.out.println(ansi().cursorUp(renderHeight).eraseLine());
		}
	}
}
