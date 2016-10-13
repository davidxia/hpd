package com.davidxia.hpd.model;

import static java.lang.String.format;

public enum DobBuildingClass {
  OLD_LAW_TENEMENT((short) 1, "A", "OLD LAW TENEMENT"),
  NEW_LAW_TENEMENT((short) 2, "B", "NEW LAW TENEMENT"),
  HEREAFTER_ERECTED_CLASS_A((short) 5, "E", "HEREAFTER ERECTED CLASS A"),
  HERETOFORE_ERECTED_EXISTING_CLASS_A((short) 6, "F", "HERETOFORE ERECTED EXISTING CLASS A"),
  HERETOFORE_CONVERTED_CLASS_A((short) 7, "G", "HERETOFORE CONVERTED CLASS A"),
  HEREAFTER_CONVERTED_CLASS_A((short) 8, "H", "HEREAFTER CONVERTED CLASS A"),
  JOINT_RESIDENTIAL_ARTISTS((short) 9, "I", "JOINT RESIDENTIAL/ARTISTS"),
  CONVERTED_OLD_LAW_TENEMENT((short) 10, "J", "CONVERTED OLD LAW TENEMENT"),
  HERETOFORE_ERECTED_EXISTING_CLASS_B((short) 15, "O", "HERETOFORE ERECTED EXISTING CLASS B"),
  HERETOFORE_CONVERTED_CLASS_B((short) 16, "P", "HERETOFORE CONVERTED CLASS B"),
  COMMERCIAL_ALTERED_CLASS_A((short) 20, "W", "COMMERCIAL ALTERED CLASS A"),
  NOT_AVAILABLE((short) 24, "N/A", "NOT AVAILABLE"),
  TWO_FAMILY_HOUSE((short) 25, "8", "2 FAMILY HOUSE");

  private final short id;
  private final String shortName;
  private final String longName;

  DobBuildingClass(final short id, final String shortName, final String longName) {
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

  /**
   * @throws IllegalArgumentException if id and name don't match one of the enum values
   */
  public static DobBuildingClass from(final int id, final String name) {
    final DobBuildingClass bc;
    switch (id) {
      case 1:
        bc = OLD_LAW_TENEMENT;
        break;
      case 2:
        bc = NEW_LAW_TENEMENT;
        break;
      case 5:
        bc = HEREAFTER_ERECTED_CLASS_A;
        break;
      case 6:
        bc = HERETOFORE_ERECTED_EXISTING_CLASS_A;
        break;
      case 7:
        bc = HERETOFORE_CONVERTED_CLASS_A;
        break;
      case 8:
        bc = HEREAFTER_CONVERTED_CLASS_A;
        break;
      case 9:
        bc = JOINT_RESIDENTIAL_ARTISTS;
        break;
      case 10:
        bc = CONVERTED_OLD_LAW_TENEMENT;
        break;
      case 15:
        bc = HERETOFORE_ERECTED_EXISTING_CLASS_B;
        break;
      case 16:
        bc = HERETOFORE_CONVERTED_CLASS_B;
        break;
      case 20:
        bc = COMMERCIAL_ALTERED_CLASS_A;
        break;
      case 24:
        bc = NOT_AVAILABLE;
        break;
      case 25:
        bc = TWO_FAMILY_HOUSE;
        break;
      default:
        throw new IllegalArgumentException("Encountered unknown DOB building class ID " + id);
    }

    if (!bc.longName().equals(name.trim().replaceAll(" +", " "))) {
      throw new RuntimeException(
          format("DOB building class with ID %d has wrong name %s", id, name));
    }

    return bc;
  }
}
