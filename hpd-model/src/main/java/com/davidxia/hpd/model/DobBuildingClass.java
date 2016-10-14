package com.davidxia.hpd.model;

import static java.lang.String.format;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

@SuppressWarnings("unused")
public enum DobBuildingClass {
  OLD_LAW_TENEMENT((short) 1, "A", "OLD LAW TENEMENT"),
  NEW_LAW_TENEMENT((short) 2, "B", "NEW LAW TENEMENT"),
  OLD_LAW_SINGLE_ROOM_OCCUPANCY((short) 3, null, "OLD LAW SINGLE ROOM OCCUPANCY"),
  NEW_LAW_SINGLE_ROOM_OCCUPANCY((short) 4, null, "NEW LAW SINGLE ROOM OCCUPANCY"),
  HEREAFTER_ERECTED_CLASS_A((short) 5, "E", "HEREAFTER ERECTED CLASS A"),
  HERETOFORE_ERECTED_EXISTING_CLASS_A((short) 6, "F", "HERETOFORE ERECTED EXISTING CLASS A"),
  HERETOFORE_CONVERTED_CLASS_A((short) 7, "G", "HERETOFORE CONVERTED CLASS A"),
  HEREAFTER_CONVERTED_CLASS_A((short) 8, "H", "HEREAFTER CONVERTED CLASS A"),
  JOINT_RESIDENTIAL_ARTISTS((short) 9, "I", "JOINT RESIDENTIAL/ARTISTS"),
  CONVERTED_OLD_LAW_TENEMENT((short) 10, "J", "CONVERTED OLD LAW TENEMENT"),
  CONVERTED_NEW_LAW_TENEMENT((short) 11, null, "CONVERTED NEW LAW TENEMENT"),
  LODGING_HOME((short) 12, null, "LODGING HOME"),
  Y_TYPE_BUILDING((short) 13, null, "Y-TYPE BUILDING"),
  HEREAFTER_ERECTED_CLASS_B((short) 14, null, "HEREAFTER ERECTED CLASS B"),
  HERETOFORE_ERECTED_EXISTING_CLASS_B((short) 15, "O", "HERETOFORE ERECTED EXISTING CLASS B"),
  HERETOFORE_CONVERTED_CLASS_B((short) 16, "P", "HERETOFORE CONVERTED CLASS B"),
  HEREAFTER_CONVERTED_CLASS_B((short) 17, null, "HEREAFTER CONVERTED CLASS B"),
  COMMERCIAL_ALTERED_CLASS_B((short) 18, null, "COMMERCIAL ALTERED CLASS B"),
  TEMPORARY_CERTIFICATE_OF_OCCUPANCY((short) 19, null, "TEMPORARY CERTIFICATE OF OCCUPANCY"),
  COMMERCIAL_ALTERED_CLASS_A((short) 20, "W", "COMMERCIAL ALTERED CLASS A"),
  PD_GARDEN_MASONETTE((short) 21, null, "PD GARDEN/MASONETTE"),
  CONVERTED_DWELLING((short) 22, null, "CONVERTED DWELLING"),
  ONE_FAMILY_HOUSE((short) 23, null, "1 FAMILY HOUSE"),
  NOT_AVAILABLE((short) 24, "N/A", "NOT AVAILABLE"),
  TWO_FAMILY_HOUSE((short) 25, "8", "2 FAMILY HOUSE"),
  INTERIM_MULTIPLE_DWELLING((short) 26, null, "INTERIM MULTIPLE DWELLING"),
  RESIDENTIAL((short) 27, null, "RESIDENTIAL"),
  MIXED_USE((short) 28, null, "MIXED USE"),
  COMMERCIAL((short) 29, null, "COMMERCIAL"),
  PARKING_LOT((short) 30, null, "PARKING LOT"),
  VACANT_LOT((short) 31, null, "VACANT LOT"),
  RESIDENTIAL_AND_COMMUNITY_FACILITY((short) 32, null, "RESIDENTIAL AND COMMUNITY FACILITY"),
  GP_GARDEN((short) 33, null, "GP GARDEN");

  private static final Map<Short, DobBuildingClass> CLASS_MAP;
  static {
    final ImmutableMap.Builder<Short, DobBuildingClass> mb = ImmutableMap.builder();
    for (final DobBuildingClass c : DobBuildingClass.values()) {
      mb.put(c.id(), c);
    }
    CLASS_MAP = mb.build();
  }

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
   * @throws IllegalArgumentException if id and name don't match one of the enum values.
   */
  public static DobBuildingClass from(final String id, final String name) {
    final Short key;
    try {
      key = Short.valueOf(id);
    } catch (NumberFormatException e) {
      // Some entries in the dataset don't have this set
      return null;
    }

    final DobBuildingClass bc = CLASS_MAP.get(key);
    if (bc == null) {
      throw new IllegalArgumentException("Encountered unknown DOB building class ID " + key);
    }

    if (!bc.longName().equals(name.trim().replaceAll(" +", " "))) {
      throw new RuntimeException(
          format("DOB building class with ID %d has wrong name %s", key, name));
    }

    return bc;
  }
}
