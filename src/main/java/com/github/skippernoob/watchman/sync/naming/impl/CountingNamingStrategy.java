package com.github.skippernoob.watchman.sync.naming.impl;


import com.github.skippernoob.watchman.sync.naming.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

// TODO: implement counting naming strategy
// e.g.
// file.txt -> file.txt.1
// file.txt -> file.txt.2
public class CountingNamingStrategy implements NamingStrategy {
    Map<String, Integer> db = new HashMap<>();

    private CountingNamingStrategy() {
    }

    public static NamingStrategy create() {
        return new CountingNamingStrategy();
    }

    @Override
    public String getNewName(String original) {
        int suffix = 0;
        if (db.isEmpty()) {
            db.put(original, 1);
            suffix = 1;
        } else {
            for (Map.Entry<String, Integer> e : db.entrySet()) {
                if (e.getKey().equals(original)) {
                    e.setValue(e.getValue() + 1);
                    suffix = e.getValue();
                } else {
                    db.put(original, 1);
                    suffix = e.getValue();
                }
            }
        }
        return String.format("%s.%s", original, suffix);
    }
}
