package com.davidxia.hpd.downloader;

import com.davidxia.hpd.postgresstore.PgDatasetStore;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

public class Main {

  public static void main(String[] args) {
    final Config config = ConfigFactory.load();
    final Config pgConfig = config.getConfig("postgres");
    final Connection conn = getConnection(pgConfig);

    final ScheduledExecutorService pgExecutor = createPostgresExecutor();

    final PgDatasetStore datasetStore = PgDatasetStore.create(conn, pgExecutor);
    final int startYear = config.getInt("startYear");
    final Calendar cal = Calendar.getInstance();
    final int curMonth = cal.get(Calendar.MONTH);
    final int curYear = cal.get(Calendar.YEAR);

    final Downloader downloader = DefaultDownloader.create();

    // TODO (david) File names aren't consistent. We should parse this page and return the zip file
    // links: http://www1.nyc.gov/site/hpd/about/Building-open-data.page#2013
    IntStream.range(startYear, curYear + 1).flatMap(i -> {
      IntStream.range(0, 12).map(j -> {
        downloader.download()
        return null;
      });
      return null;
    });
  }

  private static Connection getConnection(final Config pgConfig) {
    try {
      return DriverManager.getConnection(
          pgConfig.getString("connectionString"),
          pgConfig.getString("username"),
          pgConfig.getString("password"));
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  private static ScheduledExecutorService createPostgresExecutor() {
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
        .setDaemon(true)
        .setNameFormat("postgres-executor-%d")
        .build();
    return Executors.newScheduledThreadPool(16, threadFactory);
  }
}
