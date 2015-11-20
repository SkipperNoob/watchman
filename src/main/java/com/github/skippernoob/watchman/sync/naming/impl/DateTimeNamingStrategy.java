package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

import java.util.Calendar;
import java.util.Date;


// TODO: implement datetime based naming strategy
// e.g.
// file.txt with format "Hm" -> file.txt.1340
public class DateTimeNamingStrategy implements NamingStrategy {
    private final String format;

    private DateTimeNamingStrategy(String format) {
        try {
            if (!format.equals("date")) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
        }

        this.format = Calendar.HOUR + ":" + Calendar.MINUTE;
    }

    public static NamingStrategy create(String format) {
        return new DateTimeNamingStrategy(format);
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
