package com.davidxia.hpd.model;

import static java.lang.String.format;

public enum RecordStatus {
  ACTIVE((short) 1, "Active", "Active"),
  INACTIVE((short) 2, "Inactive", "Inactive"),
  PENDING((short) 3, "Pending", "Pending");

  private final short id;
  private final String shortName;
  private final String longName;

  RecordStatus(final short id, final String shortName, final String longName) {
    this.id = id;
    this.shortName = shortName;
    this.longName = longName;
  }

  public static RecordStatus from(final short id, final String shortName) {
    for (final RecordStatus rs : RecordStatus.values()) {
      if (rs.id == id && rs.shortName.equals(shortName)) {
        return rs;
      }
    }
    throw new IllegalArgumentException(format("No RecordStatus with id %d, shortName %s",
                                              id, shortName));
  }

  public short id() {
    return id;
  }
}
