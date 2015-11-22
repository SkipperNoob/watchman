package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

public class NoopNamingStrategy implements NamingStrategy {
    private NoopNamingStrategy() {}

    public static NamingStrategy create() {
        return new NoopNamingStrategy();
    }

    @Override
    public String getNewName(String original) throws NullPointerException {
        if (original == null) {
            throw new NullPointerException("original filename is null");
        }
        return original;
    }
}
