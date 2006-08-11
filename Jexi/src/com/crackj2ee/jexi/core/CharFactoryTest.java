/*
 * Created on 2004-7-27
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import junit.framework.TestCase;

/**
 * Test case of CharFactory. 
 * 
 * @author Xuefeng
 */
public class CharFactoryTest extends TestCase {

    /**
     * Constructor for CharFactoryTest.
     * @param arg0
     */
    public CharFactoryTest(String arg0) {
        super(arg0);
    }

    public void testCreateChar() {
        CharFactory f = CharFactory.instance();
        assertTrue(f.createChar('\r')==Char.RETURN);
        assertTrue(f.createChar('\t')==Char.TABLE);
        assertTrue(f.createChar(' ')==Char.SPACE);
        assertTrue(f.createChar('a').charValue()=='a');
        assertTrue(f.createChar('X')==f.createChar('X'));
    }

}
