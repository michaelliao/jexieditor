/*
 * Created on 2004-7-17
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

/**
 * Char represent a "char" glyph such as "A", "B", "C", 
 * or other Chinese word. Always use GlyphFactory to create 
 * a new Char rather than by "new" keyword. And it is an 
 * immutable class. <br>
 * 
 * <b>NOTE</b> Char is a basic glyph do not contains physical information. 
 * Also it is a "leaf" object that do NOT support add(), remove()... operations.
 * 
 * @see com.crackj2ee.jexi.core.CharFactory
 * 
 * @author Xuefeng
 */
public class Char implements Glyph {

	// Unsupported operation exception description:
	private static final String UNSUPPORTED_OPERATION = 
		"width(), height() operations are not supported by Char.";

	// store the "char" and make it immutable:
    private final char c;

    // decide whether to draw "Return":
    public static boolean showReturn = true;

    // some special Char for convenite use:
    public static final Char RETURN = new Char('\r');
    public static final Char TABLE = new Char('\t');
    public static final Char SPACE = new Char(' ');

    // package-private constructer enforce user to 
    // get the instance from GlyphFactory.
    Char(char c) {
        this.c = c;
    }

    /**
     * Get the char value of this object.
     * 
     * @return char value.
     */
	public char charValue() {
        return this.c;
    }

	/**
	 * <b>IMPORTANT</b>: Used in comparation operation.
	 */
	public boolean equals(Object o) {
		if( this == o )
			return true;
		if( o instanceof Char ) {
			Char c = (Char)o;
			return c.c == this.c;
		}
		return false;
	}

	/**
	 * <b>IMPORTANT</b>: Used in hash table operation.
	 */
	public int hashCode() {
		return c;
	}

	/**
	 * Unsupported operation, Char do not need to know its height.
	 * 
     * @see com.crackj2ee.jexi.core.Glyph#height()
     */
    public int height() {
		throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
    }

    /**
	 * Unsupported operation, Char do not need to know its width.
	 * 
     * @see com.crackj2ee.jexi.core.Glyph#width()
     */
    public int width() {
		throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
    }

    /**
     * Draw a char on the display.
     * 
     * @see com.crackj2ee.jexi.core.Glyph#draw(com.crackj2ee.jexi.ui.Graphics)
     */
    public void draw(com.crackj2ee.jexi.ui.Graphics g) {
        if(c=='\t') // this char does not need to draw. 
            return;
        if(c=='\r') {
            // if(showReturn) { // TODO...
            // drawReturn(); // must draw the RETURN as "_/"
        }
        g.drawChar(c);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if(c=='\r')
            return "[END]";
        if(c=='\t')
            return "[TAB]";
        return "" + c;
    }

}
