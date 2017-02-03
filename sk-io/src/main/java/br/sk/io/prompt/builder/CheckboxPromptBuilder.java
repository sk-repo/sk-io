package br.sk.io.prompt.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import br.sk.io.elements.Checkbox;
import br.sk.io.elements.items.CheckboxItemIF;
import br.sk.io.elements.items.impl.CheckboxItem;
import br.sk.io.prompt.answer.Answer;

public class CheckboxPromptBuilder {
	private final PromptBuilder promptBuilder;
	private String name;
	private String message;
	private List<CheckboxItemIF> itemList;
	private Function<Map<String, Answer>, String> fnMessage;
	private Function<Map<String, Answer>, Set<String>> fnChoices;
	private Function<Map<String, Answer>, Boolean> fnWhen;

	public CheckboxPromptBuilder(PromptBuilder promptBuilder, String name) {
		this.promptBuilder = promptBuilder;
		itemList = new ArrayList<CheckboxItemIF>();
		this.name = name;
	}

	void addItem(CheckboxItemIF checkboxItem) {
		itemList.add(checkboxItem);
	}

	public CheckboxPromptBuilder message(String message) {
		this.message = message;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public CheckboxPromptBuilder message(Function<Map<String, Answer>, String> fnMessage) {
		this.fnMessage = fnMessage;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public CheckboxPromptBuilder when(Function<Map<String, Answer>, Boolean> when) {
		this.fnWhen = when;
		return this;
	}

	public CheckboxPromptBuilder choices(String... choices) {
	//// @formatter:off
			Arrays.asList(choices).stream()
				.filter(StringUtils::isNotBlank)
				.forEach(choice -> itemList.add(new CheckboxItem(choice)));
			// @formatter:on
		return this;
	}

	public CheckboxPromptBuilder choices(Function<Map<String, Answer>, Set<String>> fnChoices) {
		this.fnChoices = fnChoices;
		return this;
	}

	public PromptBuilder build() {
		Checkbox checkbox = new Checkbox(message, name, itemList);
		if (fnMessage != null) {
			checkbox.setFnMessage(fnMessage);
		}
		if (fnChoices != null) {
			checkbox.setFnChoices(fnChoices);
		}
		if (fnWhen != null) {
			checkbox.setFnWhen(fnWhen);
		}
		promptBuilder.addPrompt(checkbox);
		return promptBuilder;
	}

}
