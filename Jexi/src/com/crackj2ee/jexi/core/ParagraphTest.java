/*
 * Created on 2004-7-28
 */
package com.crackj2ee.jexi.core;

import junit.framework.TestCase;

/**
 * Paragraph Test Case. 
 * 
 * @author Xuefeng
 */
public class ParagraphTest extends TestCase {

    private Paragraph p1;
    private Paragraph p2;
    private Paragraph p3;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        p1 = new Paragraph(null);
        p1.debug();
        // p1 is empty!

        p2 = new Paragraph(null);
        p2.add('a');
        p2.add('c');
        p2.add(1, 'b');
        p2.debug();
        // now p2 should be "abc#"

        p3 = new Paragraph(null);
        p3.add(0, 'a');
        p3.add(1, 'b');
        p3.add(2, 'c');
        p3.add(3, '\t');
        p3.add(4, 'x');
        p3.add(5, 'y');
        p3.add(6, 'z');
        p3.add(7, '?');
        p3.add(8, '!');
        p3.debug();
        // now p3 should be "abc xyz[pic]!#"
    }

    /**
     * Constructor for ParagraphTest.
     * @param arg0
     */
    public ParagraphTest(String arg0) {
        super(arg0);
    }

    public void testGetFormatted() {
        // p1 should have not been formatted:
        assertTrue(!p1.getFormatted());
    }

    /*
     * Class under test for void add(char)
     */
    public void testAddchar() {
        assertEquals(p3.child(3), Char.TABLE);
    }

    public void testChild() {
        // p1 only have 'RETURN':
        assertEquals(p1.child(0), Char.RETURN);

        // p2 = 'abc#':
        assertEquals(p2.child(0), CharFactory.instance().createChar('a'));
        assertEquals(p2.child(1), CharFactory.instance().createChar('b'));
        assertEquals(p2.child(2), CharFactory.instance().createChar('c'));
        assertEquals(p2.child(3), Char.RETURN);

        // p3 = 'abc[tab]xyz[pic]!#':
        assertTrue(p3.child(7) instanceof Picture);
    }

    public void testSize() {
        // p1 only have 'RETURN':
        assertTrue(p1.getGlyphsCount()==1);
        // p2 = 'abc#':
        assertTrue(p2.getGlyphsCount()==4);
    }

}
