package br.sk.io.console.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import br.sk.io.console.components.items.ListItemIF;
import br.sk.io.console.core.AbstractPromptableUI;
import br.sk.io.console.core.AnswerUI;
import br.sk.io.console.core.ConsoleUIItemIF;

public class SelectOneUI extends AbstractPromptableUI {

	private List<ListItemIF> listItemList;

	private Function<Map<String, AnswerUI>, Set<String>> fnChoices;

	private Function<Map<String, AnswerUI>, Boolean> fnWhen;

	public SelectOneUI(String message, String name, List<ListItemIF> listItemList) {
		super(message, name);
		this.listItemList = listItemList;
	}

	public Function<Map<String, AnswerUI>, Set<String>> getFnChoices() {
		return fnChoices;
	}

	public void setFnChoices(Function<Map<String, AnswerUI>, Set<String>> fnChoices) {
		this.fnChoices = fnChoices;
	}

	public String getMessage() {
		return message;
	}

	public ArrayList<ConsoleUIItemIF> getListItemList() {
		return new ArrayList<ConsoleUIItemIF>(listItemList);
	}

	public Function<Map<String, AnswerUI>, Boolean> getFnWhen() {
		return fnWhen;
	}

	public void setFnWhen(Function<Map<String, AnswerUI>, Boolean> fnWhen) {
		this.fnWhen = fnWhen;
	}

}
