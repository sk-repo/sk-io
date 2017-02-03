package br.sk.io.prompt;

import java.io.IOException;
import java.util.HashMap;

import br.sk.io.elements.PromptableElementIF;
import br.sk.io.prompt.answer.Answer;

/**
 * Interface for all prompt implementation.
 *
 * User: Andreas Wegmann
 * Date: 01.01.16
 */
public interface PromptIF<T extends PromptableElementIF, R extends Answer> {
  /**
   * Prompt the user for an imput.
   *
   * @param promptableElement prompt definition
   * @return the prompt result
   * @throws IOException may be thrown by getting the users input.
   */
  R prompt(T promptableElement, HashMap<String, Answer> answers) throws IOException;
}
