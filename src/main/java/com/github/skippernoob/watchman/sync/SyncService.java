package com.github.skippernoob.watchman.sync;

import com.github.skippernoob.watchman.sync.exc.SyncServiceException;
import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

public interface SyncService {
    void watch() throws SyncServiceException;
    String getSource();
    String getDestination();
    NamingStrategy getNamingStrategy();
}
