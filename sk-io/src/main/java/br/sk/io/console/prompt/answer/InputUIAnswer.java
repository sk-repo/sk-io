package br.sk.io.console.prompt.answer;

import java.util.HashSet;

import br.sk.io.console.components.ConfirmUI.ConfirmationValue;
import br.sk.io.console.core.AnswerUI;

/**
 *
 * User: Andreas Wegmann Date: 03.02.16
 */
public class InputUIAnswer implements AnswerUI {
	private String input;

	public InputUIAnswer(String input) {
		this.input = input;
	}

	public String getInput() {
		return input;
	}

	@Override
	public String value() {
		return this.input;
	}

	@Override
	public HashSet<String> list() {
		return null;
	}

	@Override
	public ConfirmationValue confirmation() {
		return null;
	}

	@Override
	public String toString() {
		return "InputResult{" + "input='" + input + '\'' + '}';
	}

}
