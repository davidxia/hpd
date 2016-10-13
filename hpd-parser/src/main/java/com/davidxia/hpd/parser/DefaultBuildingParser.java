package com.davidxia.hpd.parser;

import com.davidxia.hpd.model.Boro;
import com.davidxia.hpd.model.Building;
import com.davidxia.hpd.model.BuildingBuilder;
import com.davidxia.hpd.model.BuildingLifeCycle;
import com.davidxia.hpd.model.DobBuildingClass;
import com.davidxia.hpd.model.RecordStatus;

import java.time.Instant;

final class DefaultBuildingParser implements BuildingParser{

  private DefaultBuildingParser() {
  }

  static BuildingParser create() {
    return new DefaultBuildingParser();
  }

  /**
   * @throws IllegalArgumentException if line isn't parseable into {@link Building}
   */
  @Override
  public Building parse(final String line, final String delimiter) {
      final String[] parts = line.split(delimiter);
      final Instant now = Instant.now();

      return new BuildingBuilder()
          .hpdId(Integer.valueOf(parts[0]))
          .boro(Boro.from(Integer.valueOf(parts[1]), parts[2]))
          .houseNum(parts[3])
          .highHouseNum(parts[4])
          .lowHouseNum(parts[5])
          .streetName(parts[6])
          .zip(parts[7])
          .block(Short.valueOf(parts[8]))
          .lot(Short.valueOf(parts[9]))
          .bin(Integer.valueOf(parts[10]))
          .communityBoard(Integer.valueOf(parts[11]))
          .censusTract(parts[12])
          .managementProgram(parts[13])
          .dobBuildingClass(DobBuildingClass.from(Integer.valueOf(parts[14]), parts[15]))
          .legalStories(Integer.valueOf(parts[16]))
          .legalClassA(Integer.valueOf(parts[17]))
          .legalClassB(Integer.valueOf(parts[18]))
          .registrationId(Integer.valueOf(parts[19]))
          .buildingLifeCycle(BuildingLifeCycle.fromString(parts[20]))
          .recordStatus(RecordStatus.from(Short.valueOf(parts[21]), parts[22]))
          .created(now)
          .updated(now)
          .build();
  }
}
