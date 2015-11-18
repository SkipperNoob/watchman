package com.github.skippernoob.watchman;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import java.util.List;

public class MyJCommander {
    @Parameter
    public List<Integer> parameters = Lists.newArrayList();

}
