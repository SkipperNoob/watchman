package com.github.skippernoob.watchman.sync.impl;

import com.github.skippernoob.watchman.sync.ServiceWatchStrategy;
import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

public class LoopBasedSyncService implements SyncService {
    private final String source;
    private final String destination;
    private final NamingStrategy namingStrategy;
    private final ServiceWatchStrategy watchStrategy;

    private LoopBasedSyncService(String source,
                                 String destination,
                                 NamingStrategy namingStrategy,
                                 ServiceWatchStrategy watchStrategy) {
        this.source = source;
        this.destination = destination;
        this.namingStrategy = namingStrategy;
        this.watchStrategy = watchStrategy;
    }

    public static SyncService createEndlessService(String source,
                                                   String destination,
                                                   NamingStrategy strategy) {
        return new LoopBasedSyncService(source, destination, strategy, new EndlessServiceWatchStrategy());
    }

    public static SyncService create(String source,
                                     String destination,
                                     NamingStrategy strategy,
                                     ServiceWatchStrategy watchStrategy) {
        return new LoopBasedSyncService(source, destination, strategy, watchStrategy);
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
