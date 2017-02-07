package br.sk.io.prompt.builder;

import java.util.Map;
import java.util.function.Function;

import br.sk.io.elements.ConfirmChoice;
import br.sk.io.prompt.answer.Answer;

public class ConfirmChoicePromptBuilder {
	private final PromptBuilder promptBuilder;
	private String name;
	private String message;
	private ConfirmChoice.ConfirmationValue defaultConfirmationValue;
	private Function<Map<String, Answer>, String> fnMessage;
	private Function<Map<String, Answer>, Boolean> fnWhen;

	public ConfirmChoicePromptBuilder(PromptBuilder promptBuilder, String name) {
		this.promptBuilder = promptBuilder;
		this.name = name;
	}

	public ConfirmChoicePromptBuilder message(String message) {
		this.message = message;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public ConfirmChoicePromptBuilder message(Function<Map<String, Answer>, String> fnMessage) {
		this.fnMessage = fnMessage;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public ConfirmChoicePromptBuilder when(Function<Map<String, Answer>, Boolean> when) {
		this.fnWhen = when;
		return this;
	}

	public ConfirmChoicePromptBuilder defaultValue(ConfirmChoice.ConfirmationValue confirmationValue) {
		this.defaultConfirmationValue = confirmationValue;
		return this;
	}

	public PromptBuilder build() {
		ConfirmChoice confirmChoice = new ConfirmChoice(message, name, defaultConfirmationValue);
		if (fnMessage != null) {
			confirmChoice.setFnMessage(fnMessage);
		}
		if (fnWhen != null) {
			confirmChoice.setFnWhen(fnWhen);
		}
		promptBuilder.addPrompt(confirmChoice);
		return promptBuilder;
	}
}
