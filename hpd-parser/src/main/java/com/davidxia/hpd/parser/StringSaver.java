package com.davidxia.hpd.parser;

import java.util.concurrent.CompletionStage;

// TODO (davidxia) Figure out a better name
@FunctionalInterface
interface StringSaver {

  /**
   * Saves a String to some medium for future processing or error analysis.
   */
  CompletionStage<Void> save(String string);
}
