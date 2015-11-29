package com.github.skippernoob.watchman.sync.impl;

import com.github.skippernoob.watchman.sync.ServiceWatchStrategy;

public class EndlessServiceWatchStrategy implements ServiceWatchStrategy {
    @Override
    public boolean shouldWatch() {
        return true;
    }
}
