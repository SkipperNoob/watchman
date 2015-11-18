package com.github.skippernoob.watchman;

import java.util.List;

public class MySolution {
    public Integer solve(List<Integer> list) {
        Integer result = 0;

        for (Integer tmp : list){
            result += tmp;
        }

        return result;
    }
}
