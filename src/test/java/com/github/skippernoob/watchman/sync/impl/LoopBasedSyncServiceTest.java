package com.github.skippernoob.watchman.sync.impl;

import com.github.skippernoob.watchman.sync.ServiceWatchStrategy;
import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import com.github.skippernoob.watchman.sync.naming.impl.CountingNamingStrategy;
import com.github.skippernoob.watchman.sync.naming.impl.NoopNamingStrategy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

import java.io.File;

import static org.junit.Assert.*;

public class LoopBasedSyncServiceTest {
    @Rule
    public Timeout timeout = Timeout.seconds(10);

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private class ControlledWatchStrategy implements ServiceWatchStrategy {
        private volatile boolean shouldLoop = true;

        @Override
        public boolean shouldWatch() {
            return shouldLoop;
        }

        private void stop() {
            shouldLoop = false;
        }
    }

    private ControlledWatchStrategy watchStrategy;

    @Before
    public void setUp() throws Exception {
        watchStrategy = new ControlledWatchStrategy();
    }

    @Test
    public void testServiceCopiesFileFromSourceToDestination() throws Exception {
        File source = folder.newFile("foo.txt");
        File destination = folder.newFolder("put-here");

        Thread thread = runService(source.getAbsolutePath(), destination.getAbsolutePath(), NoopNamingStrategy.create());

        waitFor(2);

        assertTrue(source.setLastModified(now()));

        waitFor(2);

        watchStrategy.stop();

        thread.join();

        File copied = new File(destination, "foo.txt");

        assertTrue(copied.exists());
        assertTrue(copied.lastModified() > source.lastModified());
    }

    @Test
    public void testServiceOverridesExistingFile() throws Exception {
        File source = folder.newFile("foo.txt");
        File destination = folder.newFolder("put-here");
        File existing = folder.newFile("put-here/foo.txt");

        Thread thread = runService(source.getAbsolutePath(), destination.getAbsolutePath(), NoopNamingStrategy.create());

        waitFor(2);

        assertTrue(source.setLastModified(now()));

        waitFor(2);

        watchStrategy.stop();

        thread.join();

        assertTrue(existing.lastModified() > source.lastModified());
    }

    @Test
    public void testServiceCopiesOnlyChangedFiles() throws Exception {
        File root = folder.getRoot();
        File foo = folder.newFile("foo.txt");

        folder.newFile("bar.txt");
        folder.newFile("baz.txt");

        File destination = folder.newFolder("put-here");

        Thread thread = runService(root.getAbsolutePath(), destination.getAbsolutePath(), NoopNamingStrategy.create());

        waitFor(2);

        assertTrue(foo.setLastModified(now()));

        waitFor(2);

        watchStrategy.stop();

        thread.join();

        File[] copiedFiles = destination.listFiles();

        assertNotNull(copiedFiles);
        assertEquals(1, copiedFiles.length);
        assertEquals("foo.txt", copiedFiles[0].getName());
    }

    @Test
    public void testServiceCopiesFileTwiceWithCountingStrategy() throws Exception {
        File foo = folder.newFile("foo.txt");
        File destination = folder.newFolder("put-here");

        Thread thread = runService(foo.getAbsolutePath(), destination.getAbsolutePath(), CountingNamingStrategy.create());

        waitFor(2);

        assertTrue(foo.setLastModified(now()));

        waitFor(2);

        assertTrue(foo.setLastModified(now()));

        waitFor(2);

        watchStrategy.stop();

        thread.join();

        File[] copiedFiles = destination.listFiles();

        assertNotNull(copiedFiles);
        assertEquals(2, copiedFiles.length);
        assertEquals("foo.txt.1", copiedFiles[0].getName());
        assertEquals("foo.txt.2", copiedFiles[1].getName());
    }

    @Test
    public void testServiceCopiesNewlyCreatedFiles() throws Exception {
        File root = folder.getRoot();
        File destination = folder.newFolder("put-here");

        folder.newFile("foo.txt");

        Thread thread = runService(root.getAbsolutePath(), destination.getAbsolutePath(), NoopNamingStrategy.create());

        waitFor(2);

        folder.newFile("bar.txt");

        waitFor(2);

        watchStrategy.stop();

        thread.join();

        File[] copiedFiles = destination.listFiles();

        assertNotNull(copiedFiles);
        assertEquals(1, copiedFiles.length);
        assertEquals("bar.txt", copiedFiles[0].getName());
    }

    private Thread runService(String source,
                              String destination,
                              NamingStrategy strategy) {
        SyncService service = LoopBasedSyncService.create(source, destination, strategy, watchStrategy);
        Thread thread = new Thread(service::watch);
        thread.start();
        return thread;
    }

    private static long now() {
        return System.currentTimeMillis();
    }

    private static void waitFor(int seconds) throws Exception {
        Thread.sleep(seconds * 1000);
    }

    private static String filePath(File root, String path) {
        return new File(root, path).getAbsolutePath();
    }
}
