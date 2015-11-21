package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class SimpleNamingStrategyTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testReturnsNameWithSuffix() {
        NamingStrategy simpleName = SimpleNamingStrategy.create("bak");
        assertEquals("testing.txt.bak", simpleName.getNewName("testing.txt"));
    }

    @Test
    public void testThrowsIfNameIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("original filename is null");

        NamingStrategy strategy = SimpleNamingStrategy.create("bak");
        strategy.getNewName(null);
    }

    @Test
    // is discussed here: http://stackoverflow.com/questions/3881/illegalargumentexception-or-nullpointerexception-for-a-null-parameter
    public void testThrowsIfSuffixIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("suffix is null");

        SimpleNamingStrategy.create(null);
    }
}
