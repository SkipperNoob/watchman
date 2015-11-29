package com.github.skippernoob.watchman.sync.impl;

import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.SyncServiceFactory;
import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import com.github.skippernoob.watchman.sync.naming.impl.CountingNamingStrategy;
import com.github.skippernoob.watchman.sync.naming.impl.DateTimeNamingStrategy;
import com.github.skippernoob.watchman.sync.naming.impl.NoopNamingStrategy;
import com.github.skippernoob.watchman.sync.naming.impl.SimpleNamingStrategy;

// TODO: implement factory
public class SyncServiceFactoryImpl implements SyncServiceFactory {
    @Override
    public SyncService create(String source, String destination, String strategyType, String suffix) {
        if (source == null) {
            throw new NullPointerException("source is null");
        }

        if ("".equals(source)) {
            throw new IllegalArgumentException("source is empty");
        }

        if (destination == null) {
            throw new NullPointerException("destination is null");
        }

        if ("".equals(destination)) {
            throw new IllegalArgumentException("destination is empty");
        }

        NamingStrategy strategy;

        if (strategyType == null) {
            if (suffix == null) {
                strategy = NoopNamingStrategy.create();
            } else {
                strategy = SimpleNamingStrategy.create(suffix);
            }
        } else {
            switch (strategyType) {
                case "simple":
                    if (suffix == null) {
                        strategy = SimpleNamingStrategy.createWithDefaultSuffix();
                    } else {
                        strategy = SimpleNamingStrategy.create(suffix);
                    }
                    break;
                case "counting":
                    strategy = CountingNamingStrategy.create();
                    break;
                case "date":
                    strategy = DateTimeNamingStrategy.create(suffix);
                    break;
                case "":
                    throw new IllegalArgumentException("strategy type is empty");
                default:
                    throw new IllegalArgumentException("unknown strategy");
            }
        }

        return LoopBasedSyncService.create(source, destination, strategy);
    }
}
