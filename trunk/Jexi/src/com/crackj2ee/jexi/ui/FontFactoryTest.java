/*
 * Created on 2004-7-29
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

import junit.framework.TestCase;

/**
 * The test case of FontFactory. 
 * 
 * @author Xuefeng
 */
public class FontFactoryTest extends TestCase {

    FontFactory factory = null;
    Application app = null;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        app = Application.instance();
        app.debugInitButNotShow();

        // ok, we can test FontFactory now!
        factory = FontFactory.instance();

    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        factory.clearAllFonts();
        factory.debug();

        app.debugDisposeNotShow();
    }

    public void testAll() {
        // must no founts:
        assertTrue(factory.fontCount()==0);

        // Now we create 2 fonts:
        Font fa1 = factory.createFont("Arial", 12, true, false, false);
        Font ft1 = factory.createFont("Times New Roman", 16, true, false, false);
        assertTrue(factory.fontCount()==2);

        // we create an exist font:
        Font fa2 = factory.createFont("Arial", 12, true, false, false);
        assertTrue(factory.fontCount()==2); // should still be 2!
        factory.debug();

        // now fa1 dispose:
        fa1.dispose();
        assertTrue(factory.fontCount()==2); // should still be 2!

        // now fa2 dispose:
        fa2.dispose();
        assertTrue(factory.fontCount()==1); // should only ft1 left.

        // different size should be different font:
        Font ft2 = factory.createFont("Times New Roman", 12, true, false, false);
        assertTrue(factory.fontCount()==2); // ft1 and ft2.

        // clear all:
        factory.clearAllFonts();
        assertTrue(factory.fontCount()==0);

        Font tahoma = factory.createFont("Tahoma", 10, false, false, false);
    }

}
