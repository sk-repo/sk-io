package br.sk.io.components;

import java.util.Map;
import java.util.function.Function;

import br.sk.io.prompt.answer.AnswerUI;

public class ConfirmUI extends AbstractPromptableUI {

	public enum ConfirmationValue {
		YES, NO
	}

	private ConfirmationValue defaultConfirmation = null;

	private Function<Map<String, AnswerUI>, String> fnMessage;

	private Function<Map<String, AnswerUI>, Boolean> fnWhen;

	public ConfirmUI(String message, String name) {
		super(message, name);
	}

	public ConfirmUI(String message, String name, ConfirmationValue defaultConfirmation) {
		super(message, name);
		this.defaultConfirmation = defaultConfirmation;
	}

	public ConfirmationValue getDefaultConfirmation() {
		return defaultConfirmation;
	}

	public Function<Map<String, AnswerUI>, String> getFnMessage() {
		return fnMessage;
	}

	public void setFnMessage(Function<Map<String, AnswerUI>, String> fnMessage) {
		this.fnMessage = fnMessage;
	}

	public Function<Map<String, AnswerUI>, Boolean> getFnWhen() {
		return fnWhen;
	}

	public void setFnWhen(Function<Map<String, AnswerUI>, Boolean> fnWhen) {
		this.fnWhen = fnWhen;
	}

}
