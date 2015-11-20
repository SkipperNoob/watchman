package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

// TODO: implement noop naming strategy. Used when no suffix is specified
// e.g.
// file.txt -> file.txt
public class NoopNamingStrategy implements NamingStrategy {
    private NoopNamingStrategy() {}

    public static NamingStrategy create() {
        return new NoopNamingStrategy();
    }

    @Override
    public String getNewName(String original) {
        throw new UnsupportedOperationException("not implemented");
    }
}
