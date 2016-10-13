package com.davidxia.hpd.parser;

import com.google.common.base.Throwables;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

final class FileBasedStringSaver implements StringSaver {

  private BufferedWriter writer;
  private Executor executor;

  private FileBasedStringSaver(final BufferedWriter writer, final Executor executor) {
    this.writer = writer;
    this.executor = executor;
  }

  static FileBasedStringSaver create(final String path, final Executor executor)
      throws IOException {
    final BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
    return new FileBasedStringSaver(bw, executor);
  }

  @Override
  public CompletionStage<Void> save(final String string) {
    return CompletableFuture.runAsync(() -> {
      try {
        writer.write(string);
        writer.newLine();
        writer.flush();
      } catch (IOException e) {
        throw Throwables.propagate(e);
      }
    }, executor);
  }
}
