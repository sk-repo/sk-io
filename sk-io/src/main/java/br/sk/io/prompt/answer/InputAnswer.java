package br.sk.io.prompt.answer;

import java.util.HashSet;

import br.sk.io.elements.ConfirmChoice.ConfirmationValue;

/**
 *
 * User: Andreas Wegmann Date: 03.02.16
 */
public class InputAnswer implements Answer {
	private String input;

	public InputAnswer(String input) {
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
