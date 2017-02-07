package br.sk.io.prompt.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import br.sk.io.elements.ListChoice;
import br.sk.io.elements.items.ListItemIF;
import br.sk.io.elements.items.impl.ListItem;
import br.sk.io.prompt.answer.Answer;

/**
 * Created by andy on 22.01.16.
 */
public class ListChoicePromptBuilder {
	private final PromptBuilder promptBuilder;
	private String name;
	private String message;
	private List<ListItemIF> itemList = new ArrayList<ListItemIF>();
	private Function<Map<String, Answer>, String> fnMessage;
	private Function<Map<String, Answer>, Set<String>> fnChoices;
	private Function<Map<String, Answer>, Boolean> fnWhen;

	public ListChoicePromptBuilder(PromptBuilder promptBuilder, String name) {
		this.promptBuilder = promptBuilder;
		this.name = name;
	}

	public ListChoicePromptBuilder message(String message) {
		this.message = message;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public ListChoicePromptBuilder message(Function<Map<String, Answer>, String> fnMessage) {
		this.fnMessage = fnMessage;
		if (name == null) {
			name = message;
		}
		return this;
	}

	public ListChoicePromptBuilder when(Function<Map<String, Answer>, Boolean> when) {
		this.fnWhen = when;
		return this;
	}

	public ListChoicePromptBuilder choices(String... choices) {
		//// @formatter:off
		Arrays.asList(choices).stream()
			.filter(StringUtils::isNotBlank)
			.forEach(choice -> itemList.add(new ListItem(choice)));
		// @formatter:on
		return this;
	}

	public ListChoicePromptBuilder choices(Function<Map<String, Answer>, Set<String>> fnChoices) {
		this.fnChoices = fnChoices;
		return this;
	}

	public PromptBuilder build() {
		ListChoice listChoice = new ListChoice(message, name, itemList);
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
