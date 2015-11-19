package com.github.skippernoob.watchman.sync.naming.impl;

import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

// TODO: implement simple strategy that uses passed suffix to create new name
// e.g.
// file.txt and suffix ".bak" -> file.txt.bak
public class SimpleNamingStrategy implements NamingStrategy {
    private final String suffix;

    private SimpleNamingStrategy(String suffix) {
        this.suffix = suffix;
    }

    public NamingStrategy create(String suffix) {
        return new SimpleNamingStrategy(suffix);
    }

    @Override
    public String getNewName(String original) {
        throw new UnsupportedOperationException("not implemented");
    }
}
