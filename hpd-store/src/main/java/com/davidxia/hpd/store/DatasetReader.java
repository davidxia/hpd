package com.davidxia.hpd.store;

import com.davidxia.hpd.model.Dataset;

import java.util.concurrent.CompletionStage;

/**
 * Reads a {@link Dataset} from the store.
 */
@FunctionalInterface
public interface DatasetReader {
  CompletionStage<Dataset> readDataset(Dataset dataset);
}
