/*
 * Created on 2004-7-24
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui.swt;

/**
 * SWTFont is a platform-dependent class and extends 
 * the abstract Font class. It has a real font resource 
 * and manage its life cycle. 
 * 
 * @author Xuefeng
 */
public class SWTFont implements com.crackj2ee.jexi.ui.Font {

    // store the real font resource:
    private final org.eclipse.swt.graphics.Font font;

    // font attributes:
    private final String name;
    private final int size;
    private final boolean bold;
    private final boolean italic;
    private final boolean underlined;

    private int height; // font's height.

    private int refCount = 0; // reference count.

    private String m_toString = null; // cache "toString()"

    // store the defaultGraphics:
    private SWTGraphics g = null;

    SWTFont(String name, int size, boolean bold, boolean italic, boolean underlined,
        org.eclipse.swt.graphics.Font font)
    {
        // font attributes:
        this.name = name;
        this.size = size;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        // real resource:
        this.font = font;

        // try to measure the font height:
        if(g==null)
            g = (SWTGraphics)com.crackj2ee.jexi.ui.Application.instance().getFrame().getDefaultGraphics();
        // store the original font:
        org.eclipse.swt.graphics.Font orgFont = g.gc.getFont();
        // set new font:
        g.gc.setFont(font);
        // get the font height:
        this.height = g.gc.getFontMetrics().getHeight();
        // ok, restore the original font:
        g.gc.setFont(orgFont);

        // once it created, it has one reference:
        addRef();
    }

    /**
     * @return Returns the font name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Returns the font size.
     */
    public int getSize() {
        return size;
    }

    /**
     * @return Returns the bold.
     */
    public boolean getBold() {
        return bold;
    }

    /**
     * @return Returns the italic.
     */
    public boolean getItalic() {
        return italic;
    }

    /**
     * @return Returns the underlined.
     */
    public boolean getUnderlined() {
        return underlined;
    }

    /**
     * Get the height of the font (pixel). 
     * 
     * @see com.crackj2ee.jexi.ui.Font#height()
     */
    public int height() {
        return this.height;
    }

    /**
     * The font only equals when the name, the size, the bold... 
     * are exactly equals. 
     * 
     * @return True if all attributes equals.
     */
    public boolean equals(Object o) {
        if(o instanceof com.crackj2ee.jexi.ui.Font) {
            com.crackj2ee.jexi.ui.Font f = (com.crackj2ee.jexi.ui.Font)o;
            return this.toString().equals(f.toString());
        }
        return false;
    }

    /**
     * Get the hash code. 
     * 
     * @return The hash code of this font.
     */
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * This is used to create the corresponding key of the font object.
     * 
     * @return A String like "name-size-bold-italic-underlined".
     */
    public String toString() {
        if(m_toString==null) {
            m_toString = toKey(name, size, bold, italic, underlined);
        }
        return m_toString;
    }

    /**
     * This static function is to identify a font. 
     */
    public static String toKey(String name, int size, boolean bold, boolean italic, boolean underlined) {
        return name + "_" + size + "_" + 
            (bold ? "B" : "b") + 
            (italic ? "I" : "i") + 
            (underlined ? "U" : "u");
    }

    /**
     * Distroy the font if no other object referenced it. 
     * 
     * @see com.crackj2ee.jexi.ui.Font#dispose()
     */
    public void dispose() {
        this.removeRef();
        if(this.refCount()>0)
            return;
        // if no other object which referenced this font, 
        // then distroy it:
        font.dispose();
        ((SWTFontFactory)com.crackj2ee.jexi.ui.FontFactory.instance()).remove(this);
    }

    /**
     * Get the native font resource. 
     */
    public org.eclipse.swt.graphics.Font nativeFont() {
        return this.font;
    }

    protected void addRef() {
        this.refCount ++;
    }

    private void removeRef() {
        this.refCount --;
    }

    // check if some one is referenced this font:
    public int refCount() {
        return refCount;
    }

    public void debug() {
        System.out.println("  [font info] " + toString() + ", height=" + height + ", ref=" + refCount);
    }

}
