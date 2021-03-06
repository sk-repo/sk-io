package br.sk.io.console.prompt.answer;

import java.util.HashSet;

import br.sk.io.console.components.ConfirmUI;
import br.sk.io.console.components.ConfirmUI.ConfirmationValue;
import br.sk.io.console.core.AnswerUI;

public class ConfirmUIAnswer implements AnswerUI {
	ConfirmUI.ConfirmationValue confirmed;

	/**
	 * Default constructor.
	 *
	 * @param confirm
	 *            the result value to hold.
	 */
	public ConfirmUIAnswer(ConfirmUI.ConfirmationValue confirm) {
		this.confirmed = confirm;
	}

	/**
	 * Returns the confirmation value.
	 * 
	 * @return confirmation value.
	 */
	public ConfirmUI.ConfirmationValue getConfirmed() {
		return confirmed;
	}

	@Override
	public String value() {
		return this.confirmed.name();
	}

	@Override
	public HashSet<String> list() {
		return null;
	}

	@Override
	public ConfirmationValue confirmation() {
		return confirmed;
	}

	@Override
	public String toString() {
		return "ConfirmResult{" + "confirmed=" + confirmed + '}';
	}

}
