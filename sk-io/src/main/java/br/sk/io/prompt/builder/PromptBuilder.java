package br.sk.io.prompt.builder;

import java.util.ArrayList;
import java.util.List;

import br.sk.io.elements.PromptableElementIF;

/**
 * PromptBuilder is the builder class which creates
 *
 * Created by Andreas Wegmann on 20.01.16.
 */
public class PromptBuilder {
	List<PromptableElementIF> promptList = new ArrayList<PromptableElementIF>();

	public List<PromptableElementIF> build() {
		return promptList;
	}

	public void addPrompt(PromptableElementIF promptableElement) {
		promptList.add(promptableElement);
	}

	public InputPromptBuilder inputPrompt(String name) {
		return new InputPromptBuilder(this, name);
	}

	public ListChoicePromptBuilder listPrompt(String name) {
		return new ListChoicePromptBuilder(this, name);
	}

	/*
	 * public ExpandableChoicePromptBuilder createChoicePrompt() { return new
	 * ExpandableChoicePromptBuilder(this); }
	 */

	public CheckboxPromptBuilder checkboxPrompt(String name) {
		return new CheckboxPromptBuilder(this, name);
	}

	public ConfirmChoicePromptBuilder confirmPrompt(String name) {
		return new ConfirmChoicePromptBuilder(this, name);
	}
}
