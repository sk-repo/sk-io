package br.sk.io.console.core;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.ResourceBundle;

import org.fusesource.jansi.Ansi;

import br.sk.io.console.components.ConfirmUI;
import br.sk.io.console.components.InputUI;
import br.sk.io.console.components.items.impl.CheckboxItem;
import br.sk.io.console.components.items.impl.ChoiceItem;
import br.sk.io.console.components.items.impl.ListItem;

/**
 * User: Andreas Wegmann Date: 01.01.16
 */
public class CUIRenderer {
	// private final String cursorSymbol = ansi().fg(Ansi.Color.CYAN).a("\uF078>
	// ").toString();
	private final String cursorSymbol;
	private final String noCursorSpace;

	private final String uncheckedBox;
	private final String checkedBox;
	private final String line;

	private static CUIRenderer renderer = new CUIRenderer();
	private final ResourceBundle resourceBundle;

	public CUIRenderer() {
		String os = System.getProperty("os.name");
		if (os.startsWith("Windows")) {
			checkedBox = "(*) ";
			uncheckedBox = "( ) ";
			line = "---------";
			cursorSymbol = ansi().fg(Ansi.Color.CYAN).a("> ").toString();
			noCursorSpace = ansi().fg(Ansi.Color.DEFAULT).a("  ").toString();
		} else {
			checkedBox = "\u25C9 ";
			uncheckedBox = "\u25EF ";
			line = "\u2500─────────────";
			cursorSymbol = ansi().fg(Ansi.Color.CYAN).a("\u276F ").toString();
			noCursorSpace = ansi().fg(Ansi.Color.DEFAULT).a("  ").toString();
		}
		resourceBundle = ResourceBundle.getBundle("skio_messages");
	}

	public static CUIRenderer getRenderer() {
		return renderer;
	}

	public String render(ConsoleUIItemIF item, boolean withCursor) {
		if (item instanceof CheckboxItem) {
			CheckboxItem checkboxItem = (CheckboxItem) item;
			String prefix;
			if (withCursor) {
				prefix = cursorSymbol;
			} else {
				prefix = noCursorSpace;
			}
			return prefix + ansi().fg(checkboxItem.isEnabled() ? Ansi.Color.GREEN : Ansi.Color.WHITE)
					.a(checkboxItem.isChecked() ? checkedBox : uncheckedBox).reset()
					.a(checkboxItem.getText() + (checkboxItem.isDisabled() ? " (" + checkboxItem.getDisabledText() + ")" : "")).reset()
					.toString();
		}

		if (item instanceof ListItem) {
			ListItem listItem = (ListItem) item;
			if (withCursor) {
				return cursorSymbol + ansi().fg(Ansi.Color.CYAN).a(listItem.getText()).reset().toString();
			} else {
				return noCursorSpace + ansi().fg(Ansi.Color.DEFAULT).a(listItem.getText()).reset().toString();
			}
		}

		if (item instanceof ChoiceItem) {
			ChoiceItem checkboxItem = (ChoiceItem) item;
			if (withCursor) {
				return cursorSymbol
						+ ansi().fg(Ansi.Color.CYAN).a(checkboxItem.getKey() + " - " + checkboxItem.getMessage()).reset().toString();
			} else
				return noCursorSpace
						+ ansi().fg(Ansi.Color.DEFAULT).a(checkboxItem.getKey() + " - " + checkboxItem.getMessage()).reset().toString();
		}
		return "";
	}

	public String renderOptionalDefaultValue(InputUI inputElement) {
		if (inputElement.getDefaultValue() != null) {
			return " (" + inputElement.getDefaultValue() + ") ";
		}
		return " ";
	}

	public String renderValue(InputUI inputElement) {
		if (inputElement.getValue() != null) {
			return inputElement.getValue();
		}
		return "";
	}

	public String renderConfirmChoiceOptions(ConfirmUI confirmChoice) {
		if (confirmChoice.getDefaultConfirmation() == ConfirmUI.ConfirmationValue.YES) {
			return resourceBundle.getString("confirmation_yes_default");
		} else if (confirmChoice.getDefaultConfirmation() == ConfirmUI.ConfirmationValue.NO) {
			return resourceBundle.getString("confirmation_no_default");
		}
		return resourceBundle.getString("confirmation_without_default");
	}
}
