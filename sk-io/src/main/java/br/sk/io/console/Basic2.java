package br.sk.io.console;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.HashMap;

import org.fusesource.jansi.AnsiConsole;

import br.sk.io.console.components.ConfirmUI;
import br.sk.io.console.core.AnswerUI;
import br.sk.io.console.prompt.ConsolePrompt;
import br.sk.io.console.prompt.answer.ConfirmUIAnswer;
import br.sk.io.console.prompt.builder.PromptBuilder;
import jline.TerminalFactory;

/**
 * User: Andreas Wegmann Date: 29.11.15
 */
public class Basic2 {

	public static void main(String[] args) throws InterruptedException {
		AnsiConsole.systemInstall();
		System.out.println(ansi().eraseScreen().render("@|red,italic Hello|@ @|green World|@\n@|reset "
				+ "This is a demonstration of ConsoleUI java library. It provides a simple console interface\n"
				+ "for querying information from the user. ConsoleUI is inspired by Inquirer.js which is written\n" + "in JavaScrpt.|@"));

		try {
			ConsolePrompt prompt = new ConsolePrompt();
			PromptBuilder promptBuilder = prompt.getPromptBuilder();

			//// @formatter:off
			promptBuilder.selectOneUI("pizzatype")
				.message("Escolha a pizza ")
				.choices("Margherita", "Veneziana", "Hawai", "Quattro Stagioni")
			.build();
			// @formatter:on

			//// @formatter:off
			promptBuilder.inputUI("name")
	              .message(anwer -> "Digite o nome: "+anwer.get("pizzatype").value())
	              //.mask('*')
	              .choices("Jim", "Jack", "John")
	        .build();
			// @formatter:on



			HashMap<String, ? extends AnswerUI> result = prompt.prompt(promptBuilder.build());
			System.out.println("result = " + result);

			ConfirmUIAnswer delivery = (ConfirmUIAnswer) result.get("delivery");
			if (delivery.getConfirmed() == ConfirmUI.ConfirmationValue.YES) {
				System.out.println("We will deliver the pizza in 5 minutes");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				TerminalFactory.get().restore();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
