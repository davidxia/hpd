package com.davidxia.hpd.model;

import static java.lang.String.format;

public enum Boro {
  MANHATTAN((short) 1, "MN", "Manhattan"),
  BRONX((short) 2, "BX", "Bronx"),
  BROOKLYN((short) 3, "BK", "Brooklyn"),
  QUEENS((short) 4, "QN", "Queens"),
  STATEN_ISLAND((short) 5, "SI", "Staten Island");

  private final short id;
  private final String shortName;
  private final String longName;

  Boro(final short id, final String shortName, final String longName) {
    this.id = id;
    this.shortName = shortName;
    this.longName = longName;
  }

  public short id() {
    return id;
  }

  public String shortName() {
    return shortName;
  }

  public String longName() {
    return longName;
  }

  public static Boro from(final int id, final String longName) {
    for (final Boro b : Boro.values()) {
      if (b.id == id && b.longName.equalsIgnoreCase(longName)) {
        return b;
      }
    }
    throw new IllegalArgumentException(format("No Boro with id %d, longName %s", id, longName));
  }
}
