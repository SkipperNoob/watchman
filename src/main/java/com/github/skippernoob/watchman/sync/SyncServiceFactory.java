package com.github.skippernoob.watchman.sync;

// TODO: consider enum usage
public interface SyncServiceFactory {
    SyncService create(String source, String destination, String strategyType, String suffix);
}
