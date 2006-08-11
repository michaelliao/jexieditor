/*
 * Created on 2004-7-21
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

/**
 * Font class is abstract from the platform, and it knows how to map to  
 * real font of the platform. 
 * 
 * @author Xuefeng
 */
public interface Font {

    // some static const:
    public static final int SIZE_6 = 6;
    public static final int SIZE_7 = 7;
    public static final int SIZE_8 = 8;
    public static final int SIZE_9 = 9;
    public static final int SIZE_10 = 10;
    public static final int SIZE_11 = 11;
    public static final int SIZE_12 = 12;
    public static final int SIZE_14 = 14;
    public static final int SIZE_16 = 16;
    public static final int SIZE_18 = 18;
    public static final int SIZE_20 = 20;
    public static final int SIZE_22 = 22;
    public static final int SIZE_24 = 24;
    public static final int SIZE_26 = 26;
    public static final int SIZE_28 = 28;
    public static final int SIZE_36 = 36;
    public static final int SIZE_48 = 48;
    public static final int SIZE_72 = 72;

    /**
     * @return Returns the font name.
     */
    String getName();

    /**
     * @return Returns the font logic size.
     */
    int getSize();

    /**
     * @return Returns the bold.
     */
    boolean getBold();

    /**
     * @return Returns the italic.
     */
    boolean getItalic();

    /**
     * @return Returns the underlined.
     */
    boolean getUnderlined();

    /**
     * The font only equals when the name, the size, the bold... 
     * are exactly equals. 
     * 
     * @return True if all attributes equals.
     */
    boolean equals(Object o);

    /**
     * Get the hash code. 
     * 
     * @return The hash code of this font.
     */
    int hashCode();

    /**
     * This must be implemented.
     */
    void dispose();

    /**
     * This is used to create the corresponding key of the font object.
     * 
     * @return A String like "name-size-bold-italic-underlined".
     */
    String toString();

    /**
     * Get the font height. 
     * 
     * @return The font height (pixel).
     */
    int height();

    void debug();
}
