package com.davidxia.hpd.store;

import com.davidxia.hpd.model.Dataset;

import java.util.concurrent.CompletionStage;


/**
 * Writes a {@link Dataset} to the store.
 */
@FunctionalInterface
public interface DatasetWriter {
  CompletionStage<Void> writeDataset(Dataset dataset);
}
