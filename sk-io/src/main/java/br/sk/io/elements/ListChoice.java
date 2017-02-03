package br.sk.io.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import br.sk.io.elements.items.ConsoleUIItemIF;
import br.sk.io.elements.items.ListItemIF;
import br.sk.io.prompt.answer.Answer;

public class ListChoice extends AbstractPromptableElement {

	private List<ListItemIF> listItemList;

	private Function<Map<String, Answer>, Set<String>> fnChoices;

	private Function<Map<String, Answer>, Boolean> fnWhen;

	public ListChoice(String message, String name, List<ListItemIF> listItemList) {
		super(message, name);
		this.listItemList = listItemList;
	}

	public Function<Map<String, Answer>, Set<String>> getFnChoices() {
		return fnChoices;
	}

	public void setFnChoices(Function<Map<String, Answer>, Set<String>> fnChoices) {
		this.fnChoices = fnChoices;
	}

	public String getMessage() {
		return message;
	}

	public ArrayList<ConsoleUIItemIF> getListItemList() {
		return new ArrayList<ConsoleUIItemIF>(listItemList);
	}

	public Function<Map<String, Answer>, Boolean> getFnWhen() {
		return fnWhen;
	}

	public void setFnWhen(Function<Map<String, Answer>, Boolean> fnWhen) {
		this.fnWhen = fnWhen;
	}

}
