package com.github.skippernoob.watchman.sync.impl;

import com.github.skippernoob.watchman.sync.SyncService;
import com.github.skippernoob.watchman.sync.SyncServiceFactory;
import com.github.skippernoob.watchman.sync.naming.impl.CountingNamingStrategy;
import com.github.skippernoob.watchman.sync.naming.impl.DateTimeNamingStrategy;
import com.github.skippernoob.watchman.sync.naming.impl.NoopNamingStrategy;
import com.github.skippernoob.watchman.sync.naming.impl.SimpleNamingStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;

public class SyncServiceFactoryImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final SyncServiceFactory factory = new SyncServiceFactoryImpl();

    @Test
    public void testThrowsIfSourceIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("source is null");

        factory.create(null, null, null, null);
    }

    @Test
    public void testThrowsIfSourceIsEmpty() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("source is empty");

        factory.create("", null, null, null);
    }

    @Test
    public void testThrowsIfDestinationIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("destination is null");

        factory.create("foo", null, null, null);
    }

    @Test
    public void testThrowsIfDestinationIsEmpty() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("destination is empty");

        factory.create("foo", "", null, null);
    }

    @Test
    public void testReturnsSyncServiceWithNoopNamingStrategyIfSuffixAndStrategyTypeAreNull() throws Exception {
        SyncService service = factory.create("foo", "bar", null, null);
        assertTrue(service.getNamingStrategy() instanceof NoopNamingStrategy);
    }

    @Test
    public void testReturnsSyncServiceWithSimpleNamingStrategyIfStrategyTypeIsNull() throws Exception {
        SyncService service = factory.create("foo", "bar", null, ".bak");
        assertTrue(service.getNamingStrategy() instanceof SimpleNamingStrategy);
    }

    @Test
    public void testThrowsIfStrategyTypeIsEmptyAndSuffixIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("strategy type is empty");

        factory.create("foo", "bar", "", null);
    }

    @Test
    public void testReturnsSyncServiceWithSimpleNamingStrategyWithDefaultSuffixIfSuffixIsNull() throws Exception {
        SyncService service = factory.create("foo", "bar", "simple", null);
        assertTrue(service.getNamingStrategy() instanceof SimpleNamingStrategy);
    }

    @Test
    public void testReturnsSyncServiceWithSimpleNamingStrategyWithDefaultSuffixIfSuffixIsEmpty() throws Exception {
        SyncService service = factory.create("foo", "bar", "simple", "");
        assertTrue(service.getNamingStrategy() instanceof SimpleNamingStrategy);
    }

    @Test
    public void testReturnsSyncServiceWithCountingNamingStrategy() throws Exception {
        SyncService service = factory.create("foo", "bar", "counting", null);
        assertTrue(service.getNamingStrategy() instanceof CountingNamingStrategy);
    }

    @Test
    public void testReturnsSyncServiceWithDateTimeNamingStrategy() throws Exception {
        SyncService service = factory.create("foo", "bar", "date", null);
        assertTrue(service.getNamingStrategy() instanceof DateTimeNamingStrategy);
    }

    @Test
    public void testThrowsIfStrategyTypeIsUnknown() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("unknown strategy");

        factory.create("foo", "bar", "unknown", null);
    }
}
