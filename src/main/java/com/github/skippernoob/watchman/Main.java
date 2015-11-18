package com.github.skippernoob.watchman;

import com.beust.jcommander.JCommander;

import java.util.Scanner;

public class Main{

    public static void main(String[] args) {
        MyJCommander jct = new MyJCommander();
        MySolution solution = new MySolution();
        Scanner scanner = new Scanner(System.in);

        String str = scanner.nextLine();
        String[] argv = str.split(" ");

        new JCommander(jct, argv);
        solution.solve(jct.parameters);
        System.out.println(solution.solve(jct.parameters));

        System.out.println(jct.parameters);
    }

}
