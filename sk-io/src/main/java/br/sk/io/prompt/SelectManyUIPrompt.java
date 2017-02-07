package br.sk.io.prompt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.fusesource.jansi.Ansi;

import br.sk.io.components.SelectManyUI;
import br.sk.io.components.items.ConsoleUIItemIF;
import br.sk.io.components.items.impl.CheckboxItem;
import br.sk.io.prompt.answer.AnswerUI;
import br.sk.io.prompt.answer.SelectManyUIAnswer;
import br.sk.io.prompt.reader.ReaderIF;
import br.sk.io.prompt.renderer.CUIRenderer;

/**
 * CheckboxPrompt implements the checkbox choice handling.
 */
public class SelectManyUIPrompt extends AbstractListablePrompt implements PromptIF<SelectManyUI, SelectManyUIAnswer> {

	// checkbox object to prompt the user for.
	private SelectManyUI checkbox;
	
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
	public SelectManyUIPrompt() throws IOException {
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
	 * @return {@link SelectManyUIAnswer} which holds the users choices.
	 *
	 * @throws IOException
	 *             may be thrown by console reader
	 */
	public SelectManyUIAnswer prompt(SelectManyUI checkbox, HashMap<String, AnswerUI> answers) throws IOException {
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
		return new SelectManyUIAnswer(selections);
	}

	/**
	 * Toggles the selection of the currently selected checkbox item.
	 */
	private void toggleSelection() {
		CheckboxItem checkboxItem = (CheckboxItem) itemList.get(this.selectedItemIndex);
		checkboxItem.setChecked(!checkboxItem.isChecked());
	}
}
