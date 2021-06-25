package com.salesforce.tests.fs;

import org.junit.Test;

import java.io.IOException;

/**
 * Sample Unit Tests
 */
public class SampleTest extends BaseTest {

    @Test
    public void testPwd() throws Exception {
        String[] expectedResults = {
                "/root\n"
        };
        runTest(expectedResults, "pwd", "quit");
    }
}
