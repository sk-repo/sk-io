package br.sk.io.components.items.impl;

import br.sk.io.components.items.ListItemIF;

public class ListItem implements ListItemIF {
	String text;
	String name;

	public ListItem(String text, String name) {
		this.text = text;
		if (name == null) {
			this.name = text;
		} else {
			this.name = name;
		}
	}

	public ListItem(String text) {
		this(text, text);
	}

	public ListItem() {
		this(null, null);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public boolean isSelectable() {
		return true;
	}
}
