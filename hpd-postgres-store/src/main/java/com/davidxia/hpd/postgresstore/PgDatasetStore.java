package com.davidxia.hpd.postgresstore;

import com.davidxia.hpd.model.Dataset;
import com.davidxia.hpd.store.DatasetReader;
import com.davidxia.hpd.store.DatasetWriter;
import com.google.common.base.Throwables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PgDatasetStore extends BasePgStore implements DatasetReader, DatasetWriter {

  private static final Logger LOG = LoggerFactory.getLogger(PgBuildingStore.class);

  private static final String QUERY = "INSERT INTO datasets (type, year, month) VALUES (?, ?, ?)";

  private PgDatasetStore(final Connection conn, final Executor executor) {
    super(conn, executor);
  }

  public static PgDatasetStore create(final Connection conn, final Executor executor) {
    return new PgDatasetStore(conn, executor);
  }

  @Override
  public CompletionStage<Void> writeDataset(final Dataset dataset) {
    return supplyAsync(() -> {
      try (final PreparedStatement st = conn.prepareStatement(QUERY)) {
        st.setString(1, dataset.type().name());
        st.setShort(2, dataset.year());
        st.setShort(3, dataset.month());

        // TODO (davidxia) This is ugly, but the catch SQLException doesn't seem to catch
        // SQLExceptions thrown by executeUpdate(). Why?
        try {
          if (st.executeUpdate() != 1) {
            LOG.warn("SQL statement {} didn't return 1", st);
          }
        } catch (Throwable t) {
          LOG.error("Could not execute SQL statement {}", st, t);
        }
      } catch (SQLException e) {
        LOG.warn("Could not execute SQL statement", e);
        throw Throwables.propagate(e);
      }
      return null;
    });
  }

  @Override
  public CompletionStage<Dataset> readDataset(final Dataset dataset) {
    return null;
  }
}
