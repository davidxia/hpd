package com.davidxia.hpd.model;

import io.norberg.automatter.AutoMatter;

import java.time.Instant;

@AutoMatter
public interface Building {

  int hpdId();

  Boro boro();

  String houseNum();

  String lowHouseNum();

  String highHouseNum();

  String streetName();

  String zip();

  short block();

  short lot();

  int bin();

  int communityBoard();

  String censusTract();

  String managementProgram();

  DobBuildingClass dobBuildingClass();

  int legalStories();

  int legalClassA();

  int legalClassB();

  int registrationId();

  BuildingLifeCycle buildingLifeCycle();

  RecordStatus recordStatus();

  Instant created();

  Instant updated();

//  static Building create(
//      final int hpdId,
//      final Boro boro,
//      final String houseNum,
//      final String lowHouseNum,
//      final String highHouseNum,
//      final String streetName,
//      final String zip,
//      final short block,
//      final short lot,
//      final int bin,
//      final int communityBoard,
//      final String lot,
//      final short lot,
//      final short lot,
//      final short lot,
//
//      ) {
//    Preconditions.checkNotNull(heliosJob.getId());
//    Preconditions.checkNotNull(heliosJob.getId().getName());
//    Preconditions.checkNotNull(heliosJob.getId().getVersion());
//    return new AutoValue_Deployment(id, componentId, versionId, heliosJob, startTime, deployedBy);
//  }
}
