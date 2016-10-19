package com.davidxia.hpd.downloader;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

class MyCallback implements Callback {

  private final CompletableFuture<Void> future;

  private MyCallback(CompletableFuture<Void> future) {
    this.future = future;
  }

  static MyCallback create(final CompletableFuture<Void> future) {
    return new MyCallback(future);
  }

  @Override
  public void onFailure(final Call call, final IOException ex) {
    final String message = MessageFormat.format("Call {0} failed", call);
    final IOException exception = new IOException(message, ex);
    future.completeExceptionally(exception);
  }

  @SuppressFBWarnings("NP_NONNULL_PARAM_VIOLATION")
  @Override
  public void onResponse(final Call call, final Response response) throws IOException {
    future.complete(null);
  }
}
