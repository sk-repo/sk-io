package br.sk.io.console.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import br.sk.io.console.core.AbstractPromptableUI;
import br.sk.io.console.core.AnswerUI;
import jline.console.completer.Completer;

public class InputUI extends AbstractPromptableUI {
	private String value;
	private String defaultValue;
	private List<Completer> completer;
	private Character mask;
	private Consumer<String> validator;
	private Boolean required;
	private Function<Map<String, AnswerUI>, Boolean> fnWhen;

	public InputUI(String name, String message) {
		super(message, name);
		this.value = null;
		this.defaultValue = null;
	}

	public InputUI(String name, String message, String value, String defaultValue) {
		super(message, name);
		// this.value = value;
		if (value != null)
			throw new IllegalStateException("pre filled values for InputValue are not supported at the moment.");
		this.defaultValue = defaultValue;
	}

	public String getValue() {
		return value;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public List<Completer> getCompleter() {
		return completer;
	}

	public void setCompleter(List<Completer> completer) {
		this.completer = completer;
	}

	public void addCompleter(Completer completer) {
		if (this.completer == null) {
			this.completer = new ArrayList<Completer>();
		}
		this.completer.add(completer);
	}

	public void setMask(Character mask) {
		this.mask = mask;
	}

	public Character getMask() {
		return mask;
	}

	public Consumer<String> getValidator() {
		return validator;
	}

	public void setValidator(Consumer<String> validator) {
		this.validator = validator;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Function<Map<String, AnswerUI>, Boolean> getFnWhen() {
		return fnWhen;
	}

	public void setFnWhen(Function<Map<String, AnswerUI>, Boolean> fnWhen) {
		this.fnWhen = fnWhen;
	}

}
