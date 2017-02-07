package br.sk.io;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.fusesource.jansi.AnsiConsole;

import br.sk.io.components.ConfirmUI;
import br.sk.io.prompt.ConsolePrompt;
import br.sk.io.prompt.answer.AnswerUI;
import br.sk.io.prompt.builder.PromptBuilder;
import jline.TerminalFactory;

/**
 * User: Andreas Wegmann Date: 29.11.15
 */
public class Basic13 {

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
				.message(anwers -> "Escolha a pizza ")
				.choices("Margherita", "Veneziana", "Hawai", "Quattro Stagioni")
			.build();
			
			//// @formatter:off
			promptBuilder.inputUI("name")
	              .message(anwer -> "Digite o nome")
	              .required()
	              //.mask('*')
	              .choices("Jim", "Jack", "John")
	        .build();
			// @formatter:on

		    // @formatter:on

			//// @formatter:off
            promptBuilder.selectManyUI("topping")
	              .message("Pizzas de Hawai:")
	              .choices(answers -> new HashSet<>(Arrays.asList("A", "B", "C")))
	              .when(answers -> answers.get("pizzatype").value().equals("Hawai"))
	              //.newSeparator("standard toppings").add()
	
	              //.newSeparator("and our speciality...").add()
	
	              .build();
	         // @formatter:on

			//// @formatter:off
            /*
		    promptBuilder.createChoicePrompt()
		              .name("payment")
		              .message("How do you want to pay?")
		
		              .newItem().name("cash").message("Cash").key('c').asDefault().add()
		              .newItem("visa").message("Visa Card").key('v').add()
		              .newItem("master").message("Master Card").key('m').add()
		              .newSeparator("online payment").add()
		              .newItem("paypal").message("Paypal").key('p').add()
		              .build();
		             */
		    // @formatter:on

			//// @formatter:off
		    promptBuilder.confirmUI("delivery")
		          .message(answers -> "Is this pizza for delivery? ABCD")
		          .defaultValue(ConfirmUI.ConfirmationValue.YES)
		          .when(answers -> answers.get("pizzatype").value().equals("Margherita"))
		          .build();
		    // @formatter:on

			HashMap<String, ? extends AnswerUI> result = prompt.prompt(promptBuilder.build());
			System.out.println("result = " + result);

		
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
