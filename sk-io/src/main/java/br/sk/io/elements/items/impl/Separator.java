package br.sk.io.elements.items.impl;

import br.sk.io.elements.items.CheckboxItemIF;
import br.sk.io.elements.items.ChoiceItemIF;
import br.sk.io.elements.items.ListItemIF;

public class Separator implements CheckboxItemIF, ListItemIF, ChoiceItemIF {
	private String message;

	public Separator(String message) {
		this.message = message;
	}

	public Separator() {
	}

	public String getMessage() {
		return message;
	}

	public boolean isSelectable() {
		return false;
	}

	@Override
	public String getName() {
		return null;
	}

}
