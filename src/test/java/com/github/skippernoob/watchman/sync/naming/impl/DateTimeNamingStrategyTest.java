package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.*;

import static org.junit.Assert.assertEquals;

public class DateTimeNamingStrategyTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final ZonedDateTime fixedDateTime = ZonedDateTime.of(2015, 11, 21, 12, 42, 55, 0, ZoneId.systemDefault());
    private Clock fixedClock;

    @Before
    public void setUp() throws Exception {
        fixedClock = Clock.fixed(fixedDateTime.toInstant(), ZoneId.systemDefault());
    }

    @Test
    public void testReturnsNameWithFormattingSuffix() throws Exception {
        NamingStrategy strategy = DateTimeNamingStrategy.create("yyyyMMdd_HHmm", fixedClock);
        assertEquals("foo.txt.20151121_1242", strategy.getNewName("foo.txt"));
    }

    @Test
    public void testReturnsNameWithEscapedSuffix() throws Exception {
        NamingStrategy strategy = DateTimeNamingStrategy.create("'bak'", fixedClock);
        assertEquals("foo.txt.bak", strategy.getNewName("foo.txt"));
    }

    @Test
    public void testReturnsNameWithMixedSuffix() throws Exception {
        NamingStrategy strategy = DateTimeNamingStrategy.create("'bak'_HHmm", fixedClock);
        assertEquals("foo.txt.bak_1242", strategy.getNewName("foo.txt"));
    }

    @Test
    public void testThrowsIfFormatIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("datetime format is null");

        DateTimeNamingStrategy.create(null);
    }

    @Test
    public void testThrowsIfNameIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("original filename is null");

        NamingStrategy strategy = DateTimeNamingStrategy.create("HH:mm");
        strategy.getNewName(null);
    }

    @Test
    public void testThrowsIfFormatIsWrong() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Unknown pattern letter: f");

        NamingStrategy strategy = DateTimeNamingStrategy.create("foo");
        strategy.getNewName("foo.txt");
    }
}
