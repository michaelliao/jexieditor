/*
 * Created on 2004-7-20
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

/**
 * Graphics represent a drawing context.
 * 
 * @author Xuefeng
 */
public interface Graphics {

    void drawImage();
    void drawLine(int x1, int y1, int x2, int y2);

    void drawRectangle(int width, int height);
    void drawRectangle(int x, int y, int width, int height);

    void drawChar(char c);
    void drawString(String s);

    void fillRect(int width, int height);
    void fillRect(int x, int y, int width, int height);

    void moveTo(int x, int y);
    //void moveX(int step);
    //void moveY(int step);

    int getCurrentX();
    int getCurrentY();

    /**
     * Set the font of the graphics.
     * 
     * @param font The font object.
     */
    void setFont(Font font);

    /**
     * Set the fore color of the graphics. 
     * 
     * @param color The fore color.
     */
    void setForecolor(Color color);

    /**
     * Set the back color of the graphics. 
     * 
     * @param color The back color.
     */
    void setBackcolor(Color color);

    /**
     * Get the width of the char represented by the Glyph.
     * 
     * @param c The Char.
     * @return Points of the char occupied.
     */
    int getCharWidth(char c);

    /**
     * Get the height of the current font. setFont() should be 
     * called before this call.
     * 
     * @return Points of the char occupied.
     */
    int getCharHeight();

    /**
     * Destroy the graphics and release all resources.
     */
    void dispose();
}
