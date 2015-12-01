package com.github.skippernoob.watchman;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.github.skippernoob.watchman.cli.CliParams;
import com.github.skippernoob.watchman.cli.Params;
import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.impl.SyncServiceFactoryImpl;


// TODO: you need to use factory here
public class Main {
    public static void main(String[] args) {
        createSyncService(parseParams(args));
    }

    protected static Params parseParams(String[] args) {
        if (args.length == 0) {
            throw new ParameterException("source argument is required");
        }
        if (args.length == 1) {
            throw new ParameterException("destination argument is required");
        }

        Params params = new CliParams();
        new JCommander(params, args);
        return params;
    }

    protected static SyncService createSyncService(Params params) {
        SyncServiceFactoryImpl syncServiceFactory = new SyncServiceFactoryImpl();
        SyncService syncService = syncServiceFactory.create(params.getSource(),
            params.getDestination(),
            params.getSuffixStrategy(),
            params.getSuffix());
        return syncService;
    }
}
