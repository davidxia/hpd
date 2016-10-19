package com.davidxia.hpd.downloader;

import java.net.URL;
import java.util.concurrent.CompletionStage;

@FunctionalInterface
interface Downloader {

  CompletionStage<Void> download(URL url);

}
