package br.sk.io.prompt;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import br.sk.io.elements.ListChoice;
import br.sk.io.elements.items.ConsoleUIItemIF;
import br.sk.io.elements.items.ListItemIF;
import br.sk.io.elements.items.impl.ListItem;
import br.sk.io.prompt.answer.Answer;
import br.sk.io.prompt.answer.ListAnswer;
import br.sk.io.prompt.reader.ConsoleReaderImpl;
import br.sk.io.prompt.reader.ReaderIF;
import br.sk.io.prompt.renderer.CUIRenderer;

/**
 * ListPrompt implements the list choice handling.
 *
 * User: Andreas Wegmann Date: 01.01.16
 */
public class ListPrompt extends AbstractListablePrompt implements PromptIF<ListChoice, ListAnswer> {
	// the list to let the user choose from
	private ListChoice listChoice;
	private String message;

	CUIRenderer itemRenderer = CUIRenderer.getRenderer();

	public ListPrompt() throws IOException {
		super();
	}

	public ListAnswer prompt(ListChoice listChoice, HashMap<String, Answer> answers) throws IOException {
		this.listChoice = listChoice;
		this.message = this.listChoice.getFnMessage() != null ? this.listChoice.getFnMessage().apply(answers)
				: this.listChoice.getMessage();
		
		itemList = listChoice.getListItemList();
		
		if(listChoice.getFnChoices() != null) {
			 List<ListItemIF> items = listChoice.getFnChoices().apply(answers)
										.stream()
										.map(ListItem::new)
										.collect(Collectors.toList());
			 itemList = new ArrayList<>(items);
		}
		
		if (reader == null) {
			reader = new ConsoleReaderImpl();
		}
		reader.addAllowedPrintableKey('j');
		reader.addAllowedPrintableKey('k');
		reader.addAllowedPrintableKey(' ');
		reader.addAllowedSpecialKey(ReaderIF.SpecialKey.DOWN);
		reader.addAllowedSpecialKey(ReaderIF.SpecialKey.UP);
		reader.addAllowedSpecialKey(ReaderIF.SpecialKey.ENTER);

		selectedItemIndex = getFirstSelectableItemIndex();

		render();
		ReaderIF.ReaderInput readerInput = reader.read();
		while (readerInput.getSpecialKey() != ReaderIF.SpecialKey.ENTER) {
			if (readerInput.getSpecialKey() == ReaderIF.SpecialKey.PRINTABLE_KEY) {
				if (readerInput.getPrintableKey().equals('j')) {
					selectedItemIndex = getNextSelectableItemIndex();
				} else if (readerInput.getPrintableKey().equals('k')) {
					selectedItemIndex = getPreviousSelectableItemIndex();
				}
			} else if (readerInput.getSpecialKey() == ReaderIF.SpecialKey.DOWN) {
				selectedItemIndex = getNextSelectableItemIndex();
			} else if (readerInput.getSpecialKey() == ReaderIF.SpecialKey.UP) {
				selectedItemIndex = getPreviousSelectableItemIndex();
			}

			render();
			readerInput = reader.read();
		}

		ListItem listItem = (ListItem) itemList.get(selectedItemIndex);
		ListAnswer selection = new ListAnswer(listItem.getName());
		renderMessagePromptAndResult(this.message, ((ListItem) itemList.get(selectedItemIndex)).getText());
		return selection;
	}

	private void render() {
		int itemNumber = 0;

		if (renderHeight == 0) {
			renderHeight = 2 + itemList.size();
		} else {
			System.out.println(ansi().cursorUp(renderHeight));
		}

		System.out.println(renderMessagePrompt(this.message));
		for (ConsoleUIItemIF listItem : itemList) {
			String renderedItem = itemRenderer.render(listItem, (selectedItemIndex == itemNumber));
			System.out.println(renderedItem);
			itemNumber++;
		}
	}
}
