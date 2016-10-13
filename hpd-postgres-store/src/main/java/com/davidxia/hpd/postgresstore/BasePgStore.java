package com.davidxia.hpd.postgresstore;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

class BasePgStore {

  protected final Connection conn;
  private final Executor executor;

  protected BasePgStore(final Connection conn, final Executor executor) {
    this.conn = conn;
    this.executor = executor;
  }

  protected <T> CompletableFuture<T> supplyAsync(final Supplier<T> supplier) {
    return CompletableFuture.supplyAsync(supplier, executor);
  }

  protected CompletableFuture<Void> runAsync(final Runnable runnable) {
    return CompletableFuture.runAsync(runnable, executor);
  }
}
