/*
 * Created on 2004-7-27
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import junit.framework.TestCase;

/**
 * Test case of Char. 
 * 
 * @author Xuefeng
 */
public class CharTest extends TestCase {

    private Char A;
    private Char A2;
    private Char B;
    private Char c_spec;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        // A and A2 should be the same object!
        A = CharFactory.instance().createChar('a');
        A2 = CharFactory.instance().createChar('a');
        B = CharFactory.instance().createChar('b');
        c_spec = Char.RETURN;
    }

    /**
     * Constructor for CharTest.
     * @param arg0
     */
    public CharTest(String arg0) {
        super(arg0);
    }

    /*
     * Class under test for boolean equals(Object)
     */
    public void testEquals() {
        assertEquals(A, A2);
        assertTrue(A==A2);
        assertTrue(!A.equals(B));
    }

    /*
     * Class under test for int hashCode()
     */
    public void testHashCode() {
        assertEquals(A.hashCode(), A2.hashCode());
        assertTrue(A.hashCode()=='a');
        assertTrue(c_spec.hashCode()=='\r');
    }
}
