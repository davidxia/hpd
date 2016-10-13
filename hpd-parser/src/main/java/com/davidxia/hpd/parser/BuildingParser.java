package com.davidxia.hpd.parser;

import com.davidxia.hpd.model.Building;

@FunctionalInterface
interface BuildingParser {

  /**
   * Parses a String into a {@link Building} based on a delimiter.
   */
  Building parse(final String line, final String delimiter);
}
