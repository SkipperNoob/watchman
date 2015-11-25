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

        if (source == null && destination == null && strategyType == null && suffix == null) {
            throw new NullPointerException("source is null");
        }

        if (source.equals("")) {
            throw new IllegalArgumentException("source is empty");
        }

        if (destination == null) {
            throw new NullPointerException("destination is null");
        }

        if (destination.equals("")) {
            throw new IllegalArgumentException("destination is empty");
        }

        if (strategyType == null && suffix == null) {
            NamingStrategy strategy = NoopNamingStrategy.create();
            strategy.getNewName(source);
        }

        if (strategyType == null) {
            NamingStrategy strategy = SimpleNamingStrategy.create(suffix);
            strategy.getNewName(source);
        }

        if (strategyType.equals("") && suffix == null) {
            throw new IllegalArgumentException("strategy type is empty");
        }

        if (strategyType.equals("simple") && suffix == null) {
            NamingStrategy strategy = SimpleNamingStrategy.create(suffix);
            strategy.getNewName(source);
        }

        if (strategyType.equals("simple") && suffix.equals("")) {
            NamingStrategy strategy = SimpleNamingStrategy.create(suffix);
            strategy.getNewName(source);
        }

        if (strategyType.equals("counting") && suffix == null) {
            NamingStrategy strategy = CountingNamingStrategy.create();
            strategy.getNewName(source);
        }

        if (strategyType.equals("date") && suffix == null) {
            NamingStrategy strategy = DateTimeNamingStrategy.create(suffix);
            strategy.getNewName(source);
        }

        if (!strategyType.equals("") ||
            !strategyType.equals("simple") ||
            !strategyType.equals("counting") ||
            !strategyType.equals("date")) {
            throw new IllegalArgumentException("unknown strategy");
        }

        return null;
    }
}
