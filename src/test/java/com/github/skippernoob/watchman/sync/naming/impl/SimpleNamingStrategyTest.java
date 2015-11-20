package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO: implement tests
public class SimpleNamingStrategyTest {
    @Test
    public void addSuffix() {
        NamingStrategy simpleName = SimpleNamingStrategy.create("bak");
        assertEquals(simpleName.getNewName("testing.txt"), "testing.txt.bak");
    }

    @Test
    public void addSuffixFailed() throws IllegalArgumentException {
        try {
            NamingStrategy simpleName = SimpleNamingStrategy.create(null);
        } catch (IllegalArgumentException e) {
            assertEquals("", e);
        }
    }
}
