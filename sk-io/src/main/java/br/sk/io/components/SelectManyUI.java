package br.sk.io.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import br.sk.io.components.items.CheckboxItemIF;
import br.sk.io.prompt.answer.AnswerUI;

public class SelectManyUI extends AbstractPromptableUI {

	private List<CheckboxItemIF> checkboxItemList;
	private Function<Map<String, AnswerUI>, Set<String>> fnChoices;
	private Function<Map<String, AnswerUI>, Boolean> fnWhen;

	public SelectManyUI(String message, String name, List<CheckboxItemIF> checkboxItemList) {
		super(message, name);
		this.checkboxItemList = checkboxItemList;
	}

	public String getMessage() {
		return message;
	}

	public Function<Map<String, AnswerUI>, Set<String>> getFnChoices() {
		return fnChoices;
	}

	public void setFnChoices(Function<Map<String, AnswerUI>, Set<String>> fnChoices) {
		this.fnChoices = fnChoices;
	}

	public ArrayList<CheckboxItemIF> getCheckboxItemList() {
		return new ArrayList<CheckboxItemIF>(checkboxItemList);
	}

	public Function<Map<String, AnswerUI>, Boolean> getFnWhen() {
		return fnWhen;
	}

	public void setFnWhen(Function<Map<String, AnswerUI>, Boolean> fnWhen) {
		this.fnWhen = fnWhen;
	}

}
