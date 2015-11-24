package com.github.skippernoob.watchman.sync;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

public interface SyncService {
    void watch();
    String getSource();
    String getDestination();
    NamingStrategy getNamingStrategy();
}
