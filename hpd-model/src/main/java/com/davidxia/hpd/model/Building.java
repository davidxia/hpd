package com.davidxia.hpd.model;

import io.norberg.automatter.AutoMatter;

import java.time.Instant;

import javax.annotation.Nullable;

@AutoMatter
public interface Building {

  int hpdId();

  Boro boro();

  String houseNum();

  String lowHouseNum();

  String highHouseNum();

  String streetName();

  String zip();

  int block();

  short lot();

  @Nullable
  Integer bin();

  @Nullable
  Integer communityBoard();

  @Nullable
  String censusTract();

  @Nullable
  String managementProgram();

  @Nullable
  DobBuildingClass dobBuildingClass();

  @Nullable
  Integer legalStories();

  @Nullable
  Integer legalClassA();

  @Nullable
  Integer legalClassB();

  @Nullable
  Integer registrationId();

  @Nullable
  BuildingLifeCycle buildingLifeCycle();

  @Nullable
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
