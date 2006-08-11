/*
 * Created on 2004-7-24
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui.swt;

/**
 * The implementation of jexi.ui.Graphics, this is an adapter between 
 * <b>jexi.ui.Graphics</b> and <b>org.eclipse.swt.graphics.GC</b>.
 * 
 * @author Xuefeng
 */
public final class SWTGraphics implements com.crackj2ee.jexi.ui.Graphics {

    // a reference of org.eclipse.swt.graphics.GC:
    protected final org.eclipse.swt.graphics.GC gc;

    // a font reference:
    private com.crackj2ee.jexi.ui.Font font = null;

    private int current_x;
    private int current_y;

    public SWTGraphics(org.eclipse.swt.graphics.GC gc) {
        this.gc = gc;
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#drawImage()
     */
    public void drawImage() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#drawLine(int, int, int, int)
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        gc.drawLine(x1, y1, x2, y2);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#drawRectangle(int, int)
     */
    public void drawRectangle(int width, int height) {
        gc.drawRectangle(current_x, current_y, width, height);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#drawRectangle(int, int, int, int)
     */
    public void drawRectangle(int x, int y, int width, int height) {
        gc.drawRectangle(x, y, width, height);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#drawChar(char)
     */
    public void drawChar(char c) {
        gc.drawString(Character.toString(c), current_x, current_y);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#drawString(java.lang.String)
     */
    public void drawString(String s) {
        gc.drawString(s, current_x, current_y);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#fillRect(int, int)
     */
    public void fillRect(int width, int height) {
        org.eclipse.swt.graphics.Color backColor = gc.getBackground();
        gc.setBackground(gc.getForeground());
        gc.fillRectangle(current_x, current_y, width, height);
        gc.setBackground(backColor);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#fillRect(int, int, int, int)
     */
    public void fillRect(int x, int y, int width, int height) {
        org.eclipse.swt.graphics.Color backColor = gc.getBackground();
        gc.setBackground(gc.getForeground());
        gc.fillRectangle(x, y, width, height);
        gc.setBackground(backColor);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#moveTo(int, int)
     */
    public void moveTo(int x, int y) {
        this.current_x = x;
        this.current_y = y;
    }

    public int getCurrentX() {
        return this.current_x;
    }

    public int getCurrentY() {
        return this.current_y;
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#setFont(jexi.core.style.Font)
     */
    public void setFont(com.crackj2ee.jexi.ui.Font font) {
        com.crackj2ee.jexi.core.Assert.checkNull(font);

        if(this.font==font) return;
        this.font = font;
        gc.setFont(((SWTFont)font).nativeFont());
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#setForecolor(jexi.core.style.Color)
     */
    public void setForecolor(com.crackj2ee.jexi.ui.Color color) {
        org.eclipse.swt.graphics.Color c = ((SWTColor)color).nativeColor();
        gc.setForeground(c);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#setBackcolor(jexi.core.style.Color)
     */
    public void setBackcolor(com.crackj2ee.jexi.ui.Color color) {
        org.eclipse.swt.graphics.Color c = ((SWTColor)color).nativeColor();
        gc.setBackground(c);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#getCharWidth(jexi.core.Glyph)
     */
    public int getCharWidth(char c) {
        if(c=='\r')
            return 0;
        return gc.getAdvanceWidth(c);
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#getCharHeight()
     */
    public int getCharHeight() {
        return gc.getFontMetrics().getHeight();
    }

    /* (non-Javadoc)
     * @see jexi.ui.Graphics#dispose()
     */
    public void dispose() {
        this.gc.dispose();
    }

}
