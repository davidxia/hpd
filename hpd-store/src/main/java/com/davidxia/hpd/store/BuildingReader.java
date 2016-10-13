package com.davidxia.hpd.store;

import com.davidxia.hpd.model.Building;

import java.util.concurrent.CompletionStage;

@FunctionalInterface
public interface BuildingReader {
  CompletionStage<Building> readBuilding(String buildingId);
}
