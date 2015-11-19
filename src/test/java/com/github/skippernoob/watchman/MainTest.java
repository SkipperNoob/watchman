package com.github.skippernoob.watchman;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MainTest {
    @Rule
    public Timeout timeout = Timeout.seconds(5);

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() throws Exception {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(originalOut);
    }

    @Test
    public void testZeroArguments() throws Exception {
        Main.main(new String[]{});
        assertEquals(cliOutput("0"), out.toString());
    }

    @Test
    public void testSingleArgument() throws Exception {
        Main.main(new String[]{"1"});
        assertEquals(cliOutput("1"), out.toString());
    }

    @Test
    public void testTwoArguments() throws Exception {
        Main.main(new String[]{"40", "2"});
        assertEquals(cliOutput("42"), out.toString());
    }

    private static String cliOutput(String orig) {
        return String.format("%s%s", orig, System.lineSeparator());
    }
}
