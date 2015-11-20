package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO: implement tests
public class CountingNamingStrategyTest {
    @Test
    public void addFirst() {
        NamingStrategy countingName = CountingNamingStrategy.create();
        assertEquals(countingName.getNewName("lelik"), "lelik.1");
    }

    @Test
    public void addSecond() {
        NamingStrategy countingName = CountingNamingStrategy.create();
        assertEquals(countingName.getNewName("iVan"), "iVan.1");
    }

    @Test
    public void addFirst2() {
        NamingStrategy countingName = CountingNamingStrategy.create();
        assertEquals(countingName.getNewName("lelik"), "lelik.2");
    }
}
