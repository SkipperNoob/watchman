package com.github.skippernoob.watchman.sync.impl;

import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.SyncServiceFactory;

// TODO: implement factory
public class SyncServiceFactoryImpl implements SyncServiceFactory {
    @Override
    public SyncService create(String source, String destination, String strategyType, String suffix) {
        throw new UnsupportedOperationException("not implemented");
    }
}
