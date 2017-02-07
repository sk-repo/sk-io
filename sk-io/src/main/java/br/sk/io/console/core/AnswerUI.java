package br.sk.io.console.core;

import java.util.HashSet;

import br.sk.io.console.components.ConfirmUI.ConfirmationValue;

/**
 * Created by Andreas Wegmann on 03.02.16.
 */
public interface AnswerUI {

	public String value();

	public HashSet<String> list();

	ConfirmationValue confirmation();
}
