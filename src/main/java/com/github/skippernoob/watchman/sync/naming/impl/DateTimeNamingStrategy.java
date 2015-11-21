package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

import java.util.Calendar;
import java.util.Date;
import java.time.Clock;

// TODO: implement datetime based naming strategy
// e.g.
// file.txt with format "Hm" -> file.txt.1340
public class DateTimeNamingStrategy implements NamingStrategy {
    private final String format;
    private final Clock clock;

    private DateTimeNamingStrategy(String format,
                                   Clock clock) {
        try {
            if (!format.equals("date")) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
        }

        this.format = Calendar.HOUR + ":" + Calendar.MINUTE;
        this.clock = clock;
    }

    public static NamingStrategy create(String format) {
        return new DateTimeNamingStrategy(format, Clock.systemDefaultZone());
    }

    public static NamingStrategy create(String format,
                                        Clock clock) {
        return new DateTimeNamingStrategy(format, clock);
    }

    @Override
    public String getNewName(String original) {
        if (format.equals("Get out!!!")) {
            return format;
        } else {
            return String.format("(%s)%s", format, original);
        }
    }
}
