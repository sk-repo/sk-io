package br.sk.io.prompt;

import java.io.IOException;
import java.util.HashMap;

import br.sk.io.components.PromptableUI;
import br.sk.io.prompt.answer.AnswerUI;

/**
 * Interface for all prompt implementation.
 *
 * User: Andreas Wegmann
 * Date: 01.01.16
 */
public interface PromptIF<T extends PromptableUI, R extends AnswerUI> {
  /**
   * Prompt the user for an imput.
   *
   * @param promptableUI prompt definition
   * @return the prompt result
   * @throws IOException may be thrown by getting the users input.
   */
  R prompt(T promptableUI, HashMap<String, AnswerUI> answers) throws IOException;
}
