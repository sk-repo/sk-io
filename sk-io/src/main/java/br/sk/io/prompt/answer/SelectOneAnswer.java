package br.sk.io.prompt.answer;

import java.util.HashSet;

import br.sk.io.components.ConfirmUI.ConfirmationValue;

public class SelectOneAnswer implements AnswerUI {

	String selectedId;

	/**
	 * Returns the ID of the selected item.
	 *
	 * @return id of selected item
	 */
	public String getSelectedId() {
		return selectedId;
	}

	/**
	 * Default constructor.
	 *
	 * @param selectedId
	 *            id of selected item.
	 */
	public SelectOneAnswer(String selectedId) {
		this.selectedId = selectedId;
	}

	@Override
	public String value() {
		return this.selectedId;
	}

	@Override
	public HashSet<String> list() {
		return null;
	}

	@Override
	public ConfirmationValue confirmation() {
		return null;
	}

	@Override
	public String toString() {
		return "ListResult{" + "selectedId='" + selectedId + '\'' + '}';
	}

}
