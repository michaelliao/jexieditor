/*
 * Created on 2004-7-28
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test suite for jexi.core.
 * 
 * @author Xuefeng
 */
public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for jexi.core");
        //$JUnit-BEGIN$
        suite.addTestSuite(CharTest.class);
        suite.addTestSuite(CharFactoryTest.class);
        suite.addTestSuite(ParagraphTest.class);
        suite.addTestSuite(ParagraphCompositorTest.class);
        //$JUnit-END$
        return suite;
    }
}
