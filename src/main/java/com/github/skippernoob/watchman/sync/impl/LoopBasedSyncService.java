package com.github.skippernoob.watchman.sync.impl;

import com.github.skippernoob.watchman.sync.ServiceWatchStrategy;
import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoopBasedSyncService implements SyncService {
    private final String source;
    private final String destination;
    private final NamingStrategy namingStrategy;
    private final ServiceWatchStrategy watchStrategy;
    private final Map<String, Long> infoAboutFiles = new HashMap<>();

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
        File original = new File(source);
        File replica = new File(destination);
        File[] filesInDir = null;

        if (original.isFile()) {
            infoAboutFiles.put(original.toString(), original.lastModified());
        }
        if (original.isDirectory()) {
            filesInDir = original.listFiles();
            for (int i = 0; i < filesInDir.length; i++) {
                infoAboutFiles.put(filesInDir[i].toString(), filesInDir[i].lastModified());
            }
        }

        if (!replica.exists()) {
            replica.mkdir();
        }

        while (true) {
            if (original.isFile()) {
                for (Map.Entry<String, Long> entry : infoAboutFiles.entrySet()) {
                    if (original.equals(entry.getKey()) && original.lastModified() != entry.getValue()) {
                        replica = new File(destination, parseName(namingStrategy.getNewName(original.getName())));
                        copyFile(original, replica);
                        infoAboutFiles.replace(original.toString(), original.lastModified());
                    }
                }
            }

            if (original.isDirectory()) {
                for (int i = 0; i < filesInDir.length; i++) {
                    for (Map.Entry<String, Long> entry : infoAboutFiles.entrySet()) {
                        if (filesInDir[i].toString().equals(entry.getKey()) && !entry.getValue().equals(filesInDir[i].lastModified())) {
                            replica = new File(destination, parseName(namingStrategy.getNewName(filesInDir[i].getName())));
                            copyFile(filesInDir[i], replica);
                            infoAboutFiles.replace(original.toString(), original.lastModified());
                        }
                    }
                }
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private String parseName(String name) {
        String[] originalWithSuffix = name.split("\\\\");
        return originalWithSuffix[originalWithSuffix.length - 1];
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
            e.printStackTrace();
        }
    }

//    private void copyDirectory(File original, File replica) {
//        File[] files = original.listFiles();
//        if (!(replica).exists()){
//            replica.mkdir();
//        }
//
//        for (int i = 0; i < files.length; i++){
//            if (files[i].isFile()){
//                File tmp = replica;
//                File toFile = new File(tmp.getAbsolutePath(), files[i].getName());
//                copyFile(files[i], toFile);
//            }else if (files[i].isDirectory()){
//                File dirOriginal = new File(original, files[i].getName());
//                File dirReplica = new File(replica, files[i].getName());
//                copyDirectory(dirOriginal, dirReplica);
//            }else {
//                System.out.println("???");
//            }
//        }
//    }
}
