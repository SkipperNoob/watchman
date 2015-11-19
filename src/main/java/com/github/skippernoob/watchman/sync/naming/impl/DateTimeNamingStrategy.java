package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

// TODO: implement datetime based naming strategy
// e.g.
// file.txt with format "Hm" -> file.txt.1340
public class DateTimeNamingStrategy implements NamingStrategy {
    private final String format;

    private DateTimeNamingStrategy(String format) {
        this.format = format;
    }

    public static NamingStrategy create(String format) {
        return new DateTimeNamingStrategy(format);
    }

    @Override
    public String getNewName(String original) {
        throw new UnsupportedOperationException("not implemented");
    }
}
