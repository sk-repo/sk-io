package br.sk.io.console.prompt.builder;

import java.util.Map;
import java.util.function.Function;

import br.sk.io.console.components.ConfirmUI;
import br.sk.io.console.core.AnswerUI;

public class ConfirmUIPromptBuilder {
	private final PromptBuilder promptBuilder;
	private String name;
	private String message;
	private ConfirmUI.ConfirmationValue defaultConfirmationValue;
	private Function<Map<String, AnswerUI>, String> fnMessage;
	private Function<Map<String, AnswerUI>, Boolean> fnWhen;

	public ConfirmUIPromptBuilder(PromptBuilder promptBuilder, String name) {
		this.promptBuilder = promptBuilder;
		this.name = name;
	}

	public ConfirmUIPromptBuilder message(String message) {
		this.message = message;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public ConfirmUIPromptBuilder message(Function<Map<String, AnswerUI>, String> fnMessage) {
		this.fnMessage = fnMessage;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public ConfirmUIPromptBuilder when(Function<Map<String, AnswerUI>, Boolean> when) {
		this.fnWhen = when;
		return this;
	}

	public ConfirmUIPromptBuilder defaultValue(ConfirmUI.ConfirmationValue confirmationValue) {
		this.defaultConfirmationValue = confirmationValue;
		return this;
	}

	public PromptBuilder build() {
		ConfirmUI confirmChoice = new ConfirmUI(message, name, defaultConfirmationValue);
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
