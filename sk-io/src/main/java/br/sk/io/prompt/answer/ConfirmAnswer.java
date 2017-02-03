package br.sk.io.prompt.answer;

import java.util.HashSet;

import br.sk.io.elements.ConfirmChoice;
import br.sk.io.elements.ConfirmChoice.ConfirmationValue;

public class ConfirmAnswer implements Answer {
	ConfirmChoice.ConfirmationValue confirmed;

	/**
	 * Default constructor.
	 *
	 * @param confirm
	 *            the result value to hold.
	 */
	public ConfirmAnswer(ConfirmChoice.ConfirmationValue confirm) {
		this.confirmed = confirm;
	}

	/**
	 * Returns the confirmation value.
	 * 
	 * @return confirmation value.
	 */
	public ConfirmChoice.ConfirmationValue getConfirmed() {
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
