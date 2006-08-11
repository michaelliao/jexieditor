/*
 * Created on 2004-7-31
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

/**
 * The color factory. 
 * 
 * @author Xuefeng
 */
public abstract class ColorFactory {

    //*************************************************************************
    // NOTE: This ColorFactory is implement by SWTColorFactoy, 
    // if you want to use another factoy such as SwingColorFactory, 
    // change this following code:
    //*************************************************************************
    private static ColorFactory instance = new com.crackj2ee.jexi.ui.swt.SWTColorFactory();

    // to prevent client to create instance:
    protected ColorFactory() {}

    /**
     * Get the singleton instance of the ColorFactory. 
     * 
     * @return The color factory.
     */
    public static ColorFactory instance() {
        return instance;
    }

    /**
     * Create new color. 
     * 
     * @param r Red, 0-255.
     * @param g Green, 0-255.
     * @param b Blue, 0-255.
     * @return The RGB color.
     */
    public abstract Color createColor(int r, int g, int b);

    /**
     * Create color specified by the predefined constant. 
     * 
     * @param color_rgb Constant such as BLACK, RED, or 0xff00cc.
     * @return The RGB color.
     */
    public com.crackj2ee.jexi.ui.Color createColor(int color_rgb) {
        int r = (color_rgb & 0xff0000) >> 16;
        int g = (color_rgb & 0xff00) >> 8;
        int b = (color_rgb & 0xff);
        return createColor(r, g, b);
    }

    /**
     * The size of the cache. 
     * 
     * @return How many colors used now.
     */
    public abstract int size();

    /**
     * Dispose all colors when application terminates. 
     */
    public abstract void clearAllColors();

    public abstract void debug();
}
