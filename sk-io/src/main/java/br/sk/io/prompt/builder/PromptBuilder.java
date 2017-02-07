package br.sk.io.prompt.builder;

import java.util.ArrayList;
import java.util.List;

import br.sk.io.components.PromptableUI;

/**
 * PromptBuilder is the builder class which creates
 *
 * Created by Andreas Wegmann on 20.01.16.
 */
public class PromptBuilder {
	List<PromptableUI> promptList = new ArrayList<PromptableUI>();

	public List<PromptableUI> build() {
		return promptList;
	}

	public void addPrompt(PromptableUI promptableElement) {
		promptList.add(promptableElement);
	}

	public InputUIPromptBuilder inputUI(String name) {
		return new InputUIPromptBuilder(this, name);
	}

	public SelectOneUIPromptBuilder selectOneUI(String name) {
		return new SelectOneUIPromptBuilder(this, name);
	}

	/*
	 * public ExpandableChoicePromptBuilder createChoicePrompt() { return new
	 * ExpandableChoicePromptBuilder(this); }
	 */

	public SelectManyUIPromptBuilder selectManyUI(String name) {
		return new SelectManyUIPromptBuilder(this, name);
	}

	public ConfirmUIPromptBuilder confirmUI(String name) {
		return new ConfirmUIPromptBuilder(this, name);
	}
}
