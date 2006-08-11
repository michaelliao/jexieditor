/*
 * Created on 2004-7-28
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import junit.framework.TestCase;

/**
 * TODO...
 * 
 * @author Xuefeng
 */
public class ParagraphCompositorTest extends TestCase {

    private Paragraph p1;
    private Paragraph p2;
    private ParagraphCompositor pc;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        pc = new ParagraphCompositor();
        p1 = new Paragraph(null);
        p2 = new Paragraph(null);

        p1.add('a');
        p1.add('b');
        p1.add('c');
        p1.add('d');
        p1.add('e');
        p1.add('f');
        p1.add('g');
        p1.add('h');
        p1.add('i');
        p1.add('j');
        p1.add('0');
        p1.add('1');
        p1.add('2');
        p1.add('3');
        p1.add('4');
        p1.add('5');
        p1.add('6');
        p1.add('7');
        p1.add('8');
        p1.add('9');
    }

    /**
     * Constructor for ParagraphCompositorTest.
     * @param arg0
     */
    public ParagraphCompositorTest(String arg0) {
        super(arg0);
    }

    public void testCompose() {
        // now p1 is "abcdefghij0123456789", 
        // it should be formatted in two rows:
        pc.setComposition(p1);
        pc.compose();
        p1.debug();
        assertTrue(p1.getFormatted());
        assertTrue(p1.getRows().size()==2);

        // now insert a new one into p1:
        p1.add(10, '$');
        assertTrue(!p1.getFormatted());
        // compose again:
        pc.compose();
        assertTrue(p1.getRows().size()==3);
        p1.debug();
    }

}
