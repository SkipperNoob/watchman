package com.github.skippernoob.watchman.sync.impl;

import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

// TODO: implement service
public class LoopBasedSyncService implements SyncService {
    private final String source;
    private final String destination;
    private final NamingStrategy namingStrategy;

    private LoopBasedSyncService(String source, String destination, NamingStrategy namingStrategy) {
        this.source = source;
        this.destination = destination;
        this.namingStrategy = namingStrategy;
    }

    public static SyncService create(String source, String destination, NamingStrategy strategy) {
        return new LoopBasedSyncService(source, destination, strategy);
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public NamingStrategy getNamingStrategy() {
        return namingStrategy;
    }

    @Override
    public void watch() {
        throw new UnsupportedOperationException("not implemented");
    }
}
