package com.github.skippernoob.watchman.sync;

public interface SyncServiceFactory {
    SyncService create(String source, String destination, String suffixStrategy, String suffixString);
}
