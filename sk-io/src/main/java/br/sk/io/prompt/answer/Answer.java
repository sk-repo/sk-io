package br.sk.io.prompt.answer;

import java.util.HashSet;

import br.sk.io.elements.ConfirmChoice.ConfirmationValue;

/**
 * Created by Andreas Wegmann on 03.02.16.
 */
public interface Answer {

	public String value();

	public HashSet<String> list();

	ConfirmationValue confirmation();
}
