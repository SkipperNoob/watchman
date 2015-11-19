package com.github.skippernoob.watchman;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class MyJCommander {
    @Parameter
    public List<Integer> parameters = new ArrayList<>();
}
