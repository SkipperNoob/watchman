package com.github.skippernoob.watchman.sync.naming.impl;


import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

public class CountingNamingStrategy implements NamingStrategy {
    private Map<String, Integer> db = new HashMap<>();
    private static final Integer DEF_NUMBER = 1;
    private static final Integer STEP = 1;

    private CountingNamingStrategy() {
    }

    public static NamingStrategy create() {
        return new CountingNamingStrategy();
    }

    @Override
    public String getNewName(String original) {
        if (original == null) {
            throw new NullPointerException("original filename is null");
        }

        db.compute(original, (k, v) -> (v == null) ? DEF_NUMBER : v + STEP);

        return String.format("%s.%s", original, db.get(original));
    }
}
