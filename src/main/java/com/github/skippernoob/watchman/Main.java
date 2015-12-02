package com.github.skippernoob.watchman;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.github.skippernoob.watchman.cli.CliParams;
import com.github.skippernoob.watchman.cli.Params;
import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.SyncServiceFactory;
import com.github.skippernoob.watchman.sync.impl.SyncServiceFactoryImpl;

public class Main {
    private static final SyncServiceFactory syncServiceFactory = new SyncServiceFactoryImpl();

    public static void main(String[] args) {
        createSyncService(parseParams(args));
    }

    protected static Params parseParams(String[] args) {
        CliParams params = new CliParams();
        new JCommander(params, args);

        if (params.getArguments().size() == 0) {
            throw new ParameterException("source argument is required");
        }
        if (params.getArguments().size() == 1) {
            throw new ParameterException("destination argument is required");
        }

        return params;
    }

    protected static SyncService createSyncService(Params params) {
        return syncServiceFactory.create(params.getSource(),
            params.getDestination(),
            params.getSuffixStrategy(),
            params.getSuffix());
    }
}
