package com.davidxia.hpd.store;

import com.davidxia.hpd.model.Building;

import java.util.concurrent.CompletionStage;

@FunctionalInterface
public interface BuildingWriter {
  CompletionStage<Void> writeBuilding(Building building);
}
