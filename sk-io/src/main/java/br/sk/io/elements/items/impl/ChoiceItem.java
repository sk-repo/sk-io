package br.sk.io.elements.items.impl;

import br.sk.io.elements.items.ChoiceItemIF;

public class ChoiceItem implements ChoiceItemIF {
	private Character key;
	private String name;
	private String message;
	private boolean defaultChoice;

	public ChoiceItem(Character key, String name, String message, boolean isDefaultChoice) {
		this.key = key;
		this.name = name;
		this.message = message;
		this.defaultChoice = isDefaultChoice;
	}

	public Character getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSelectable() {
		return true;
	}

	public boolean isDefaultChoice() {
		return defaultChoice;
	}
}
