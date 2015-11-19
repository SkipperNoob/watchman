package com.github.skippernoob.watchman;

import com.github.skippernoob.watchman.cli.Params;
import com.github.skippernoob.watchman.sync.SyncService;

// TODO: you need to use factory here
public class Main {
    public static void main(String[] args) {
        System.out.println("Watchman project will born here soon");
    }

    // TODO: implement parsing params
    protected static SyncService createSyncService(String[] args, Params params) {
        throw new UnsupportedOperationException("not implemented");
    }
}
