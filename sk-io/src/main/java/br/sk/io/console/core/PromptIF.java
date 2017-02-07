package br.sk.io.console.core;

import java.io.IOException;
import java.util.HashMap;

import br.sk.io.console.components.PromptableUI;

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
