package br.sk.io.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import br.sk.io.elements.items.CheckboxItemIF;
import br.sk.io.prompt.answer.Answer;

public class Checkbox extends AbstractPromptableElement {

	private List<CheckboxItemIF> checkboxItemList;
	private Function<Map<String, Answer>, Set<String>> fnChoices;
	private Function<Map<String, Answer>, Boolean> fnWhen;

	public Checkbox(String message, String name, List<CheckboxItemIF> checkboxItemList) {
		super(message, name);
		this.checkboxItemList = checkboxItemList;
	}

	public String getMessage() {
		return message;
	}

	public Function<Map<String, Answer>, Set<String>> getFnChoices() {
		return fnChoices;
	}

	public void setFnChoices(Function<Map<String, Answer>, Set<String>> fnChoices) {
		this.fnChoices = fnChoices;
	}

	public ArrayList<CheckboxItemIF> getCheckboxItemList() {
		return new ArrayList<CheckboxItemIF>(checkboxItemList);
	}

	public Function<Map<String, Answer>, Boolean> getFnWhen() {
		return fnWhen;
	}

	public void setFnWhen(Function<Map<String, Answer>, Boolean> fnWhen) {
		this.fnWhen = fnWhen;
	}

}
