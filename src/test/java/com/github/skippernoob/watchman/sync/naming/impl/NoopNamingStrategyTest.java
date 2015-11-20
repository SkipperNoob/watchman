package com.github.skippernoob.watchman.sync.naming.impl;


import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO: implement tests
public class NoopNamingStrategyTest {
    @Test
    public void NoopNamingStrategy() {
        NamingStrategy noopName = NoopNamingStrategy.create();
        assertEquals(noopName.getNewName("testing.txt"), "testing.txt");
    }
}
