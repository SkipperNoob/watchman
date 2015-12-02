package com.github.skippernoob.watchman.cli;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class CliParams implements Params {
    @Parameter
    private List<String> list = new ArrayList<>();

    @Parameter(names = "--strategy", description = "Parameter of strategy")
    private String suffixStrategy;


    @Parameter(names = {"--suffix", "-s"}, description = "Suffix")
    private String suffix;

    @Override
    public String getSource() {
        return list.get(0);
    }

    @Override
    public String getDestination() {
        return list.get(1);
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public String getSuffixStrategy() {
        return suffixStrategy;
    }

    public List<String> getArguments() {
        return list;
    }
}
