/*
 * Created on 2004-7-21
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

/**
 * FontFactory is used to create Font. 
 * <b>NOTE</b>: All fonts are created and only can be created by 
 * FontFactory, and will dispose automatically if you call dispose() 
 * method. In fact, each createFont() call will add a reference count 
 * of the Font, and a dispose() call will remove a reference count. 
 * If the reference is become 0, it will actually disposed, or disposed 
 * when application close. This is taken account of effeciency. 
 * 
 * @author Xuefeng
 */
public abstract class FontFactory {

    //*************************************************************************
    // the only instance of the FontFactory. In this case, 
    // FontFactory is SWTFontFactory. You should change the 
    // class name such as "SwingFontFactory" and re-compile 
    // the project if using Swing or some other GUI. 
    //*************************************************************************
    private static FontFactory instance = new com.crackj2ee.jexi.ui.swt.SWTFontFactory();

    // to prevent the client to create a new instance:
    protected FontFactory() {}

    /**
     * Subclass must implement this method to enumerate all fonts:
     * 
     * @return All fonts' names installed on the system.
     */
    public abstract String[] enumerateFonts();

    /**
     * Get the instance of the FontFactory. 
     * 
     * @return The singleton instance of the FontFactory.
     */
    public static FontFactory instance() {
        return instance;
    }

    /**
     * Create a font (maybe used an existed one). If a font is created 
     * first time, put it to the hash table with the key gernerated as 
     * "font.toString()". 
     * 
     * @param name The name of the font, eg: "Arial", "Tahoma"
     * @param size The size of the font, usually 10, 12.
     * @param bold If the font is bold.
     * @param italic If the font is italic.
     * @param underlined If the font is underlined.
     * @return Font object.
     */
    public abstract Font createFont(String name, int size, boolean bold, boolean italic, boolean underlined);

    /**
     * This method is called by Document when a document is closed. 
     * Just clean all font resources that is used but the FontFactory 
     * is still available.
     */
    public abstract void clearAllFonts();

    /**
     * Create the default English font. 
     * 
     * @return The default English font.
     */
    public Font createDefaultEnglishFont() {
        return createFont("Times New Roman", 14, false, false, false);
    }

    /**
     * How many fonts it used now. 
     * 
     * @return The count of fonts used now.
     */
    public abstract int fontCount();

    public abstract void debug();
}
