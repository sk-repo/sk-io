package br.sk.io.prompt.builder;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import br.sk.io.components.InputUI;
import br.sk.io.prompt.answer.AnswerUI;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;

public class InputUIPromptBuilder {
	private final PromptBuilder promptBuilder;
	private String name;
	private String defaultValue;
	private String message;
	private Character mask;
	private ArrayList<Completer> completers;
	private Consumer<String> validator;
	private Function<Map<String, AnswerUI>, String> fnMessage;
	private Boolean required;
	private Function<Map<String, AnswerUI>, Boolean> fnWhen;

	public InputUIPromptBuilder(PromptBuilder promptBuilder, String name) {
		this.promptBuilder = promptBuilder;
		this.name = name;
	}

	public InputUIPromptBuilder defaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	public InputUIPromptBuilder message(String message) {
		this.message = message;
		return this;
	}

	public InputUIPromptBuilder message(Function<Map<String, AnswerUI>, String> message) {
		this.fnMessage = message;
		return this;
	}

	public InputUIPromptBuilder when(Function<Map<String, AnswerUI>, Boolean> when) {
		this.fnWhen = when;
		return this;
	}

	public InputUIPromptBuilder choices(String... completer) {
		if (completers == null) {
			completers = new ArrayList<Completer>();
		}
		this.completers.add(new StringsCompleter(completer));
		return this;
	}

	public InputUIPromptBuilder mask(char mask) {
		this.mask = mask;
		return this;
	}

	public InputUIPromptBuilder required() {
		this.required = true;
		return this;
	}

	public InputUIPromptBuilder validate(Consumer<String> validator) {
		this.validator = validator;
		return this;
	}

	public PromptBuilder build() {
		InputUI inputValue = new InputUI(name, message, null, defaultValue);
		if (fnMessage != null) {
			inputValue.setFnMessage(fnMessage);
		}
		if (completers != null) {
			inputValue.setCompleter(completers);
		}
		if (mask != null) {
			inputValue.setMask(mask);
		}
		if (validator != null) {
			inputValue.setValidator(validator);
		}
		if (required != null) {
			inputValue.setRequired(required);
		}
		if (fnWhen != null) {
			inputValue.setFnWhen(fnWhen);
		}
		promptBuilder.addPrompt(inputValue);
		return promptBuilder;
	}

}
