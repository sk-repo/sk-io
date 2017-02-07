package br.sk.io.components;

import java.util.Map;
import java.util.function.Function;

import br.sk.io.prompt.answer.AnswerUI;

public class AbstractPromptableUI implements PromptableUI {

	protected String message;
	protected String name;
	protected Function<Map<String, AnswerUI>, String> fnMessage;

	public AbstractPromptableUI(String message, String name) {
		this.message = message;
		this.name = name;
	}

	public AbstractPromptableUI(Function<Map<String, AnswerUI>, String> fnMessage, String name) {
		super();
		this.name = name;
		this.fnMessage = fnMessage;
	}

	public String getMessage() {
		return message;
	}

	public String getName() {
		return name;
	}

	public Function<Map<String, AnswerUI>, String> getFnMessage() {
		return fnMessage;
	}

	public void setFnMessage(Function<Map<String, AnswerUI>, String> fnMessage) {
		this.fnMessage = fnMessage;
	}

}
