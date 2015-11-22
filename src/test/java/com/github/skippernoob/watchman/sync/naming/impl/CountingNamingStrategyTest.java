package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CountingNamingStrategyTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testReturnsForFirstCall() {
        NamingStrategy strategy = CountingNamingStrategy.create();
        assertEquals("foo.txt.1", strategy.getNewName("foo.txt"));
    }

    @Test
    public void testReturnsForSecondCall() {
        NamingStrategy strategy = CountingNamingStrategy.create();
        strategy.getNewName("foo.txt");
        assertEquals("foo.txt.2", strategy.getNewName("foo.txt"));
    }

    @Test
    public void testReturnsForSeparateCalls() {
        NamingStrategy strategy = CountingNamingStrategy.create();

        assertEquals("foo.txt.1", strategy.getNewName("foo.txt"));
        assertEquals("bar.txt.1", strategy.getNewName("bar.txt"));
        assertEquals("foo.txt.2", strategy.getNewName("foo.txt"));
    }

    @Test
    public void testThrowsIfNameIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("original filename is null");

        NamingStrategy strategy = CountingNamingStrategy.create();
        strategy.getNewName(null);
    }
}
