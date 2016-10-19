package com.davidxia.hpd.downloader;

import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DefaultDownloader implements Downloader {

  private final OkHttpClient client;

  private DefaultDownloader(final OkHttpClient client) {
    this.client = client;
  }

  static DefaultDownloader create() {
    return new DefaultDownloader(new OkHttpClient());
  }

  @Override
  public CompletionStage<Void> download(final URL url) {
    final Request request = new Request.Builder()
        .url(url)
        .build();

    final CompletableFuture<Void> result = new CompletableFuture<>();

    client.newCall(request).enqueue(MyCallback.create(result));

    return result;
  }
}
