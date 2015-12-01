package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeNamingStrategy implements NamingStrategy {
    private final String format;
    private final Clock clock;

    private DateTimeNamingStrategy(String format, Clock clock) {
        if (format == null) {
            throw new NullPointerException("datetime format is null");
        }

        this.format = format;
        this.clock = clock;
    }

    public static NamingStrategy create(String format) {
        return new DateTimeNamingStrategy(format, Clock.systemDefaultZone());
    }

    public static NamingStrategy create(String format, Clock clock) {

        return new DateTimeNamingStrategy(format, clock);
    }

    @Override
    public String getNewName(String original) {
        if (original == null) {
            throw new NullPointerException("original filename is null");
        }

        LocalDateTime fixedTime = LocalDateTime.now(clock);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String suffix = fixedTime.format(formatter);

        return String.format("%s.%s", original, suffix);
    }
}
