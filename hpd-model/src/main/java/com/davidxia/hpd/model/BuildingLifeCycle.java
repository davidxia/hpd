package com.davidxia.hpd.model;

import static java.lang.String.format;

public enum BuildingLifeCycle {
  LAND((short) 1, "Land"),
  PLANNED((short) 2, "Planned"),
  BUILDING((short) 3, "Building"),
  DEMOLISHED((short) 4, "Demolished"),
  UNDER_CONSTRUCTION((short) 5, "UnderConstruction"),
  // Not sure if the numbers below are what's actually in the XML file
  UNDETERMINED((short) 6, "Undetermined");

  private final short id;
  private final String value;

  BuildingLifeCycle(final short id, final String value) {
    this.id = id;
    this.value = value;
  }

  public short id() {
    return id;
  }

  public String value() {
    return value;
  }

  public static BuildingLifeCycle fromString(final String text) {
    if (text != null) {
      for (final BuildingLifeCycle b : BuildingLifeCycle.values()) {
        // Remove all spaces bc some entries don't have spaces in the LifeCycle string
        if (text.replaceAll("\\s+","").equalsIgnoreCase(b.value)) {
          return b;
        }
      }
    }
    throw new IllegalArgumentException(format("No constant with text %s found", text));
  }
}
