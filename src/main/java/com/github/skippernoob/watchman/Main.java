package com.github.skippernoob.watchman;

import com.beust.jcommander.JCommander;

public class Main {
    public static void main(String[] args) {
        MyJCommander jct = new MyJCommander();
        MySolution solution = new MySolution();

        new JCommander(jct, args);

        solution.solve(jct.parameters);
        System.out.println(solution.solve(jct.parameters));
    }
}
