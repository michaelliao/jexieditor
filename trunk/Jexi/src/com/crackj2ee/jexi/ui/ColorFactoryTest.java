/*
 * Created on 2004-7-31
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

import junit.framework.TestCase;

/**
 * TODO Description here...
 * 
 * @author Xuefeng
 */
public class ColorFactoryTest extends TestCase {

    private ColorFactory factory = null;
    Application app = null;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        app = Application.instance();
        app.debugInitButNotShow();

        factory = ColorFactory.instance();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        factory.clearAllColors();

        app.debugDisposeNotShow();
    }

    /**
     * Constructor for ColorFactoryTest.
     * @param arg0
     */
    public ColorFactoryTest(String arg0) {
        super(arg0);
    }

    public void testCreateColor() {
        // first we create 3 colors:
        Color c1 = factory.createColor(1,2,3);
        Color c2 = factory.createColor(4,5,6);
        Color c3 = factory.createColor(7,8,9);
        assertTrue(factory.size()==3); // should be 3 colors!

        // and we create an existing color:
        Color c4 = factory.createColor(1,2,3);
        assertTrue(factory.size()==3); // should be still 3!
        assertTrue(c1==c4); // should be the same objects!

        // we dispose c4:
        c4.dispose();
        assertTrue(factory.size()==3); // should be still 3!

        // we dispose c3:
        c3.dispose();
        assertTrue(factory.size()==2); // should be 2 now!

        factory.debug();

    }

}
