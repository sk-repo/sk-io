package br.sk.io.prompt.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import br.sk.io.components.SelectManyUI;
import br.sk.io.components.items.CheckboxItemIF;
import br.sk.io.components.items.impl.CheckboxItem;
import br.sk.io.prompt.answer.AnswerUI;

public class SelectManyUIPromptBuilder {
	private final PromptBuilder promptBuilder;
	private String name;
	private String message;
	private List<CheckboxItemIF> itemList;
	private Function<Map<String, AnswerUI>, String> fnMessage;
	private Function<Map<String, AnswerUI>, Set<String>> fnChoices;
	private Function<Map<String, AnswerUI>, Boolean> fnWhen;

	public SelectManyUIPromptBuilder(PromptBuilder promptBuilder, String name) {
		this.promptBuilder = promptBuilder;
		itemList = new ArrayList<CheckboxItemIF>();
		this.name = name;
	}

	void addItem(CheckboxItemIF checkboxItem) {
		itemList.add(checkboxItem);
	}

	public SelectManyUIPromptBuilder message(String message) {
		this.message = message;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public SelectManyUIPromptBuilder message(Function<Map<String, AnswerUI>, String> fnMessage) {
		this.fnMessage = fnMessage;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public SelectManyUIPromptBuilder when(Function<Map<String, AnswerUI>, Boolean> when) {
		this.fnWhen = when;
		return this;
	}

	public SelectManyUIPromptBuilder choices(String... choices) {
	//// @formatter:off
			Arrays.asList(choices).stream()
				.filter(StringUtils::isNotBlank)
				.forEach(choice -> itemList.add(new CheckboxItem(choice)));
			// @formatter:on
		return this;
	}

	public SelectManyUIPromptBuilder choices(Function<Map<String, AnswerUI>, Set<String>> fnChoices) {
		this.fnChoices = fnChoices;
		return this;
	}

	public PromptBuilder build() {
		SelectManyUI checkbox = new SelectManyUI(message, name, itemList);
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
