package br.sk.io.prompt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.fusesource.jansi.Ansi;

import br.sk.io.elements.Checkbox;
import br.sk.io.elements.items.ConsoleUIItemIF;
import br.sk.io.elements.items.impl.CheckboxItem;
import br.sk.io.prompt.answer.Answer;
import br.sk.io.prompt.answer.CheckboxAnswer;
import br.sk.io.prompt.reader.ReaderIF;
import br.sk.io.prompt.renderer.CUIRenderer;

/**
 * CheckboxPrompt implements the checkbox choice handling.
 */
public class CheckboxPrompt extends AbstractListablePrompt implements PromptIF<Checkbox, CheckboxAnswer> {

	// checkbox object to prompt the user for.
	private Checkbox checkbox;
	
	private String message;

	/**
	 * helper class with render functionality.
	 */
	CUIRenderer itemRenderer = CUIRenderer.getRenderer();

	/**
	 * Empty default constructor.
	 * 
	 * @throws IOException
	 *             may be thrown by super class
	 */
	public CheckboxPrompt() throws IOException {
		super();
	}

	/**
	 * render the checkbox on the terminal.
	 */
	private void render() {
		int itemNumber = 0;

		if (this.renderHeight == 0) {
			this.renderHeight = (2 + itemList.size());
		} else {
			System.out.println(Ansi.ansi().cursorUp(this.renderHeight));
		}
		System.out.println(renderMessagePrompt(this.message));
		for (ConsoleUIItemIF checkboxItem : itemList) {
			String renderedItem = this.itemRenderer.render(checkboxItem, this.selectedItemIndex == itemNumber);
			System.out.println(renderedItem);
			itemNumber++;
		}
	}

	/**
	 * Prompt the user for selecting zero to many choices from a checkbox.
	 *
	 * @param checkbox
	 *            checkbox with items to choose from.
	 * @return {@link CheckboxAnswer} which holds the users choices.
	 *
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	public CheckboxAnswer prompt(Checkbox checkbox, HashMap<String, Answer> answers) throws IOException {
		this.checkbox = checkbox;
		
		this.message = this.checkbox.getFnMessage() != null ? this.checkbox.getFnMessage().apply(answers)
				: this.checkbox.getMessage();
		
		itemList = this.checkbox.getCheckboxItemList();
		
		if(checkbox.getFnChoices() != null) {
			 List<CheckboxItem> items = checkbox.getFnChoices().apply(answers)
										.stream()
										.map(CheckboxItem::new)
										.collect(Collectors.toList());
			 itemList = new ArrayList<>(items);
		}

		this.reader.addAllowedPrintableKey('j');
		this.reader.addAllowedPrintableKey('k');
		this.reader.addAllowedPrintableKey(' ');
		this.reader.addAllowedSpecialKey(ReaderIF.SpecialKey.DOWN);
		this.reader.addAllowedSpecialKey(ReaderIF.SpecialKey.UP);
		this.reader.addAllowedSpecialKey(ReaderIF.SpecialKey.ENTER);

		this.selectedItemIndex = getFirstSelectableItemIndex();

		render();
		ReaderIF.ReaderInput readerInput = this.reader.read();
		while (readerInput.getSpecialKey() != ReaderIF.SpecialKey.ENTER) {
			if (readerInput.getSpecialKey() == ReaderIF.SpecialKey.PRINTABLE_KEY) {
				if (readerInput.getPrintableKey().equals(' ')) {
					toggleSelection();
				} else if (readerInput.getPrintableKey().equals('j')) {
					this.selectedItemIndex = getNextSelectableItemIndex();
				} else if (readerInput.getPrintableKey().equals('k')) {
					this.selectedItemIndex = getPreviousSelectableItemIndex();
				}
			} else if (readerInput.getSpecialKey() == ReaderIF.SpecialKey.DOWN) {
				this.selectedItemIndex = getNextSelectableItemIndex();
			} else if (readerInput.getSpecialKey() == ReaderIF.SpecialKey.UP) {
				this.selectedItemIndex = getPreviousSelectableItemIndex();
			}
			render();
			readerInput = this.reader.read();
		}

		LinkedHashSet<String> selections = new LinkedHashSet<String>();

		for (ConsoleUIItemIF item : itemList) {
			if ((item instanceof CheckboxItem)) {
				CheckboxItem checkboxItem = (CheckboxItem) item;
				if (checkboxItem.isChecked()) {
					selections.add(checkboxItem.getName());
				}
			}
		}
		renderMessagePromptAndResult(this.message, selections.toString());
		return new CheckboxAnswer(selections);
	}

	/**
	 * Toggles the selection of the currently selected checkbox item.
	 */
	private void toggleSelection() {
		CheckboxItem checkboxItem = (CheckboxItem) itemList.get(this.selectedItemIndex);
		checkboxItem.setChecked(!checkboxItem.isChecked());
	}
}
