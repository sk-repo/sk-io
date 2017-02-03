package br.sk.io.prompt;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.ResourceBundle;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;

import br.sk.io.prompt.reader.ConsoleReaderImpl;
import br.sk.io.prompt.reader.ReaderIF;

/**
 * Abstract base class for all prompt implementations. User: Andreas Wegmann
 * Date: 06.01.16
 */
public abstract class AbstractPrompt {
	protected int renderHeight;
	protected ResourceBundle resourceBundle;

	// the reader where we get the user input from
	ReaderIF reader;

	/**
	 * Generic method to render the message prompt and the users input after the
	 * prompt. This method is used by all prompt implementations to display the
	 * question and result after the user has made the input.
	 *
	 * @param message
	 *            message to render as colored prompt.
	 * @param resultValue
	 *            result value generated from the prompt implementation
	 */
	protected void renderMessagePromptAndResult(String message, String resultValue) {
		System.out.println(ansi().cursorUp(renderHeight - 1).a(renderMessagePrompt(message)).fg(Ansi.Color.CYAN).a(" " + resultValue)
				.eraseScreen(Ansi.Erase.FORWARD).reset());
	}

	/**
	 * Generic method to render a message prompt. The message (displayed white)
	 * is prefixed by a green question mark.
	 *
	 * @param message
	 *            message to render as a colored prompt.
	 * @return String with ANSI-Color printable prompt.
	 */
	protected String renderMessagePrompt(String message) {
		return (ansi().fg(Color.WHITE).a("[").fg(Color.GREEN).a("?").fg(Color.WHITE).a("] ").fgBright(Ansi.Color.WHITE).bold().a(message))
				.boldOff().fg(Ansi.Color.DEFAULT).toString();
	}

	protected String renderMessagePrompt(String message, boolean invalidInput) {
		if (invalidInput) {
			return (ansi().cursorUp(renderHeight).fg(Color.WHITE).a("[").fg(Color.GREEN).a("?").fg(Color.WHITE).a("] ")
					.fgBright(Ansi.Color.WHITE).bold().a(message)).boldOff().fg(Ansi.Color.DEFAULT).toString();
		}
		return renderMessagePrompt(message);
	}

	/**
	 * Default constructor. Initializes the resource bundle for localized
	 * messages.
	 *
	 * @throws IOException
	 *             may be thrown from console reader
	 */
	public AbstractPrompt() throws IOException {
		resourceBundle = ResourceBundle.getBundle("skio_messages");
		this.reader = new ConsoleReaderImpl();
	}

	/**
	 * Setter for the reader implementation. Usually the prompt implementation
	 * uses the default {@link ConsoleReaderImpl} initialized in the
	 * constructor. This methods is mainly inteded for JUnit tests to inject a
	 * new reader for simulated uses input.
	 *
	 * @param reader
	 *            reader implementation to use.
	 */
	public void setReader(ReaderIF reader) {
		this.reader = reader;
	}
}
