package com.github.skippernoob.watchman.sync.naming.impl;


import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

// TODO: implement counting naming strategy
// e.g.
// file.txt -> file.txt.1
// file.txt -> file.txt.2
public class CountingNamingStrategy implements NamingStrategy {
    private CountingNamingStrategy() {}

    public static NamingStrategy create() {
        return new CountingNamingStrategy();
    }

    @Override
    public String getNewName(String original) {
        throw new UnsupportedOperationException("not implemented");
    }
}
