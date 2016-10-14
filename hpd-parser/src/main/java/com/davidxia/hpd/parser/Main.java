package com.davidxia.hpd.parser;

import com.davidxia.hpd.model.Building;
import com.davidxia.hpd.postgresstore.PgBuildingStore;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) throws Exception {
    final Config config = ConfigFactory.load();
    final String expectedFields = config.getString("fields");
    final Config pgConfig = config.getConfig("postgres");

    final Connection conn;
    try {
      conn = DriverManager.getConnection(
          pgConfig.getString("connectionString"),
          pgConfig.getString("username"),
          pgConfig.getString("password"));
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }

    final BuildingParser buildingParser = DefaultBuildingParser.create();
    final StringSaver stringSaver = FileBasedStringSaver.create(
        config.getString("badLinesFile"), Executors.newSingleThreadExecutor());
    final ScheduledExecutorService pgExecutor = createPostgresExecutor();
    final PgBuildingStore buildingStore = PgBuildingStore.create(conn, pgExecutor);

    final List<CompletableFuture> futures = new ArrayList<>();

    try (final Stream<String> stream = Files.lines(Paths.get(config.getString("dataFile")))) {
      final AtomicBoolean isFirstLine = new AtomicBoolean(true);
      stream.forEach(line -> {
        if (isFirstLine.get()) {
          // First line is the header
          final String fields = line.replaceAll("\\s+","");
          if (!fields.equals(expectedFields)) {
            throw new IllegalArgumentException("Fields in text file don't match expected.");
          }
          isFirstLine.set(false);
          return;
        }

        try {
          final Building building = buildingParser.parse(line, "\\|");
          futures.add(buildingStore.writeBuilding(building).toCompletableFuture());
        } catch (RuntimeException e) {
          LOG.error("Could not parse string {}", line, e);
          stringSaver.save(line).exceptionally(t -> {
            LOG.error("Could not save string {}", line, t);
            return null;
          });
        }
      });
    }

    System.out.println("HI");
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
    System.out.println("DONE");
  }

  private static ScheduledExecutorService createPostgresExecutor() {
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
        .setDaemon(true)
        .setNameFormat("postgres-executor-%d")
        .build();
    return Executors.newScheduledThreadPool(16, threadFactory);
  }

}
