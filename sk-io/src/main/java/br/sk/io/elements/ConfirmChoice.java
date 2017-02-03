package br.sk.io.elements;

import java.util.Map;
import java.util.function.Function;

import br.sk.io.prompt.answer.Answer;

public class ConfirmChoice extends AbstractPromptableElement {

	public enum ConfirmationValue {
		YES, NO
	}

	private ConfirmationValue defaultConfirmation = null;

	private Function<Map<String, Answer>, String> fnMessage;

	private Function<Map<String, Answer>, Boolean> fnWhen;

	public ConfirmChoice(String message, String name) {
		super(message, name);
	}

	public ConfirmChoice(String message, String name, ConfirmationValue defaultConfirmation) {
		super(message, name);
		this.defaultConfirmation = defaultConfirmation;
	}

	public ConfirmationValue getDefaultConfirmation() {
		return defaultConfirmation;
	}

	public Function<Map<String, Answer>, String> getFnMessage() {
		return fnMessage;
	}

	public void setFnMessage(Function<Map<String, Answer>, String> fnMessage) {
		this.fnMessage = fnMessage;
	}

	public Function<Map<String, Answer>, Boolean> getFnWhen() {
		return fnWhen;
	}

	public void setFnWhen(Function<Map<String, Answer>, Boolean> fnWhen) {
		this.fnWhen = fnWhen;
	}

}
