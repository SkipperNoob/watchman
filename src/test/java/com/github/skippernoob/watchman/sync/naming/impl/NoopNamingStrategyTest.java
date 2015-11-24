package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class NoopNamingStrategyTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testReturnsSameName() throws Exception {
        NamingStrategy strategy = NoopNamingStrategy.create();
        assertEquals("testing.txt", strategy.getNewName("testing.txt"));
    }

    @Test
    public void testThrowsIfNameIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("original filename is null");

        NamingStrategy strategy = NoopNamingStrategy.create();
        strategy.getNewName(null);
    }
}
