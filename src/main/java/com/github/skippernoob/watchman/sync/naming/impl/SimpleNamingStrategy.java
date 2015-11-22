package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

public class SimpleNamingStrategy implements NamingStrategy {
    private final String suffix;

    private SimpleNamingStrategy(String suffix) {
        this.suffix = suffix;
    }

    public static NamingStrategy create(String suffix) {
        if (suffix == null) {
            throw new NullPointerException("suffix is null");
        }
        return new SimpleNamingStrategy(suffix);
    }

    @Override
    public String getNewName(String original) throws NullPointerException {
        if (original == null) {
            throw new NullPointerException("original filename is null");
        }
        return String.format("%s.%s", original, suffix);
    }
}
