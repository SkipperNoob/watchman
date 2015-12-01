package com.github.skippernoob.watchman;

import com.beust.jcommander.ParameterException;
import com.github.skippernoob.watchman.cli.Params;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MainTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testReturnsParamsWithRequiredArguments() throws Exception {
        Params params = Main.parseParams(new String[] {"foo", "bar"});
        assertEquals(params.getSource(), "foo");
        assertEquals(params.getDestination(), "bar");
        assertNull(params.getSuffix());
        assertNull(params.getSuffixStrategy());
    }

    @Test
    public void testReturnsParamsWithSuffixWhenLongOptionSpecified() throws Exception {
        Params params = Main.parseParams(new String[] {"foo", "bar", "--suffix", "bak"});
        assertEquals(params.getSuffix(), "bak");
    }

    @Test
    public void testReturnsParamsWithSuffixWhenShortOptionSpecified() throws Exception {
        Params params = Main.parseParams(new String[] {"foo", "bar", "-s", "bak"});
        assertEquals(params.getSuffix(), "bak");
    }

    @Test
    public void testReturnsParamsWithSuffixStrategy() throws Exception {
        Params params = Main.parseParams(new String[] {"foo", "bar", "--strategy", "counting"});
        assertEquals(params.getSuffixStrategy(), "counting");
    }

    @Test
    public void testThrowsIfSourceArgumentIsNotProvided() throws Exception {
        thrown.expect(ParameterException.class);
        thrown.expectMessage("source argument is required");

        Main.parseParams(new String[] {});
    }

    @Test
    public void testThrowsIfDestinationArgumentIsNotProvided() throws Exception {
        thrown.expect(ParameterException.class);
        thrown.expectMessage("destination argument is required");

        Main.parseParams(new String[] {"foo"});
    }

    @Test
    public void testExtraArgumentsAreIgnored() throws Exception {
        Params params = Main.parseParams(new String[] {"foo", "bar", "baz"});
        assertEquals(params.getSource(), "foo");
        assertEquals(params.getDestination(), "bar");
    }

    @Test
    public void testArgumentsCountShouldNotBreakValidation() throws Exception {
        thrown.expect(ParameterException.class);
        thrown.expectMessage("destination argument is required");

        Main.parseParams(new String[] {"--suffix", "bak", "foo"});
    }
}
