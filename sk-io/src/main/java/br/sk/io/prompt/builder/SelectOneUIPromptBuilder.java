package br.sk.io.prompt.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import br.sk.io.components.SelectOneUI;
import br.sk.io.components.items.ListItemIF;
import br.sk.io.components.items.impl.ListItem;
import br.sk.io.prompt.answer.AnswerUI;

/**
 * Created by andy on 22.01.16.
 */
public class SelectOneUIPromptBuilder {
	private final PromptBuilder promptBuilder;
	private String name;
	private String message;
	private List<ListItemIF> itemList = new ArrayList<ListItemIF>();
	private Function<Map<String, AnswerUI>, String> fnMessage;
	private Function<Map<String, AnswerUI>, Set<String>> fnChoices;
	private Function<Map<String, AnswerUI>, Boolean> fnWhen;

	public SelectOneUIPromptBuilder(PromptBuilder promptBuilder, String name) {
		this.promptBuilder = promptBuilder;
		this.name = name;
	}

	public SelectOneUIPromptBuilder message(String message) {
		this.message = message;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public SelectOneUIPromptBuilder message(Function<Map<String, AnswerUI>, String> fnMessage) {
		this.fnMessage = fnMessage;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public SelectOneUIPromptBuilder when(Function<Map<String, AnswerUI>, Boolean> when) {
		this.fnWhen = when;
		return this;
	}

	public SelectOneUIPromptBuilder choices(String... choices) {
		//// @formatter:off
		Arrays.asList(choices).stream()
			.filter(StringUtils::isNotBlank)
			.forEach(choice -> itemList.add(new ListItem(choice)));
		// @formatter:on
		return this;
	}

	public SelectOneUIPromptBuilder choices(Function<Map<String, AnswerUI>, Set<String>> fnChoices) {
		this.fnChoices = fnChoices;
		return this;
	}

	public PromptBuilder build() {
		SelectOneUI listChoice = new SelectOneUI(message, name, itemList);
		if (fnMessage != null) {
			listChoice.setFnMessage(fnMessage);
		}
		if (fnChoices != null) {
			listChoice.setFnChoices(fnChoices);
		}
		if (fnWhen != null) {
			listChoice.setFnWhen(fnWhen);
		}
		promptBuilder.addPrompt(listChoice);
		return promptBuilder;
	}

	void addItem(ListItem listItem) {
		this.itemList.add(listItem);
	}

}
