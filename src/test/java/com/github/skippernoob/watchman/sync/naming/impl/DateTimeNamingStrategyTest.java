package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

// TODO: implement tests
public class DateTimeNamingStrategyTest {
    @Test
    public void addDate() {
        NamingStrategy dateName = DateTimeNamingStrategy.create("date");
        assertEquals(dateName.getNewName("bla"), "(" + Calendar.HOUR + ":" + Calendar.MINUTE + ")bla");
    }

    @Test
    public void dateException() {
        try {
            NamingStrategy dateName = DateTimeNamingStrategy.create("fail");
        } catch (IllegalArgumentException e) {
            assertEquals("", e);
        }
    }
}
