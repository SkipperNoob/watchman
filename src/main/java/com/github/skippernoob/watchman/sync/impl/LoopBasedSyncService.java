package com.github.skippernoob.watchman.sync.impl;

import com.github.skippernoob.watchman.sync.ServiceWatchStrategy;
import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.exc.SyncServiceException;
import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

import java.io.*;
import java.util.*;

public class LoopBasedSyncService implements SyncService {
    private static final long DEFAULT_WAIT_TIME = 1000;
    private final String source;
    private final String destination;
    private final NamingStrategy namingStrategy;
    private final ServiceWatchStrategy watchStrategy;
    private final Map<String, Long> filesLastModified = new HashMap<>();

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
    public void watch() throws SyncServiceException {
        File original = new File(source);
        File replica = new File(destination);
        boolean isSourceFolder = original.isDirectory();

        Set<File> filesToWatch = new HashSet<>();

        if (isSourceFolder) {
            filesToWatch.addAll(Arrays.asList(original.listFiles()));
        } else {
            filesToWatch.add(original);
        }

        for (File file : filesToWatch) {
            filesLastModified.put(file.getAbsolutePath(), file.lastModified());
        }

        while (watchStrategy.shouldWatch()) {
            if (!replica.exists()) {
                if (!replica.mkdir()) {
                    throw new SyncServiceException("failed to create destination folder");
                }
            }

            if (isSourceFolder) {
                filesToWatch.addAll(Arrays.asList(original.listFiles()));
            }

            copyFiles(filesToWatch, replica);

            try {
                Thread.sleep(DEFAULT_WAIT_TIME);
            } catch (InterruptedException e) {
                throw new SyncServiceException("Failed to wait", e);
            }
        }

    }

    private void copyFile(File original, File replica) {
        try (
            FileInputStream input = new FileInputStream(original);
            BufferedInputStream inBuff = new BufferedInputStream(input);
            FileOutputStream output = new FileOutputStream(replica);
            BufferedOutputStream outBuff = new BufferedOutputStream(output)
        ) {
            int length;
            while ((length = inBuff.read()) != -1) {
                outBuff.write(length);
            }
            outBuff.flush();
        } catch (IOException e) {
            throw new SyncServiceException("failed to copy", e);
        }
    }

    private void copyFiles(Collection<File> sources, File destination) {
        for (File file : sources) {
            String filePath = file.getAbsolutePath();
            Long currentLastModified = file.lastModified();
            Long previousLastModified = filesLastModified.get(filePath);
            if (previousLastModified == null || previousLastModified < currentLastModified) {
                filesLastModified.put(filePath, currentLastModified);
                if (file.isFile()) {
                    copyFile(file, new File(destination, namingStrategy.getNewName(file.getName())));
                }
            }
        }
    }
}
