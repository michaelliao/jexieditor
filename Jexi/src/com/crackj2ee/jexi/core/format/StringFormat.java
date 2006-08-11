/*
 * Created on 2004-7-20
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.format;

import com.crackj2ee.jexi.core.*;
import com.crackj2ee.jexi.ui.*;


/**
 * StringFormat is used to render a string of Char glyphs. 
 * A paragraph object contains one or more StringFormat object  
 * and each StringFormat knows the start and end index of the Char(s). 
 * 
 * @author Xuefeng
 */
public class StringFormat implements Cloneable {

    // the reference of paragraph:
    private Paragraph paragraph;
    private int startIndex;
    private int endIndex;

    private Font font;
    private Color color = Color.BLACK;

    /**
     * To create a StringFormat:
     * 
     * @param paragraph The paragraph it belongs to.
     * @param font The font.
     * @param color The font color.
     * @param startIndex The start index of the Char.
     * @param endIndex The end index of the Char.
     */
    public StringFormat(Paragraph paragraph, Font font, Color color, int startIndex, int endIndex) {
        Assert.checkNull(paragraph);
        Assert.checkNull(color);
       // Assert.checkNull(font);
        Assert.checkTrue( 0<=startIndex );
        Assert.checkTrue( endIndex<paragraph.getGlyphsCount() );
        Assert.checkTrue( startIndex<=endIndex );

        this.paragraph = paragraph;
        this.font = font;
        this.color = color;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * Get the font format. 
     * 
     * @return The font object.
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * Set the new font. 
     * 
     * @param font The new font.
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * If some Char(s) are inserted in the range represented by 
     * this StringFormat, then use increase() method to update it. 
     * 
     * @param n How many Char(s) are inserted.
     */
    public void increase(int n) {
        this.endIndex += n;
    }

    /**
     * Once some Glyphs were inserted before the range represented by this StringStyle, 
     * using move() instead of create new StringFormats behind the insert point.
     * 
     * @param steps How many glyphs inserted before.
     */
    public void move(int steps) {
        this.startIndex += steps;
        this.endIndex += steps;
    }

    /**
     * The comparation of two StringFormat will be true if:<br>
     * 1. They belongs to the same paragraph;<br>
     * 2. They have same font style and color;<br>
     * 3. They have same start and end index.
     * 
     * @return true if the comparation succeeded.
     */
    public boolean equals(Object o) {
        if( o instanceof StringFormat ) {
            StringFormat sf = (StringFormat)o;
            return (this.paragraph==sf.paragraph &&
                    this.font.equals(sf.font) &&
                    this.color.equals(sf.color) &&
                    this.startIndex== sf.startIndex &&
                    this.endIndex==sf.endIndex
                );
        }
        return false;
    }

    /**
     * Check if the range contains the specified index.
     * 
     * @param index The specified index.
     * @return True if it contains the index.
     */
    public boolean contains(int index) {
        return ( (this.startIndex<=index) && (index<=this.endIndex) );
    }

    /**
     * Get the font color. 
     * 
     * @return The font color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Set the new color. 
     * 
     * @param color The new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the start index. 
     * 
     * @return The start index.
     */
    public int getStartIndex() {
        return this.startIndex;
    }

    /**
     * Get the end index. 
     * 
     * @return The end index.
     */
    public int getEndIndex() {
        return this.endIndex;
    }

    /**
     * Set the start index. Should be careful to use. 
     * 
     * @param startIndex The start index.
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * Set the end index. Should be careful to use. 
     * 
     * @param endIndex The end index.
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[" + this.startIndex + ", " + this.endIndex + "]";
    }
}
