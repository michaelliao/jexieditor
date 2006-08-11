/*
 * Created on 2004-7-25
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

/**
 * The view decorator. 
 * 
 * @author Xuefeng
 */
public abstract class ViewDecorator implements View {

    // a reference to view component:
    protected View component;

    /**
     * To create a wrapped view. 
     * 
     * @param component The view component that will be decorated (or wrapped).
     */
    public ViewDecorator(View component) {
        this.component = component;
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onMouseMove(int, int)
     */
    public void onMouseMove(int x, int y) {
        this.component.onMouseMove(x, y);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onLButtonDown(int, int)
     */
    public void onLButtonDown(int x, int y) {
        this.component.onLButtonDown(x, y);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onLButtonUp(int, int)
     */
    public void onLButtonUp(int x, int y) {
        this.component.onLButtonUp(x, y);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onRButtonDown(int, int)
     */
    public void onRButtonDown(int x, int y) {
        this.component.onRButtonDown(x, y);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onRButtonUp(int, int)
     */
    public void onRButtonUp(int x, int y) {
        this.component.onRButtonUp(x, y);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onLButtonDblClick(int, int)
     */
    public void onLButtonDblClick(int x, int y) {
        this.component.onLButtonDblClick(x, y);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onSetCaret(int, int, int)
     */
    public void onSetCaret(int x, int y, int height) {
        this.component.onSetCaret(x, y, height);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#init(jexi.core.Document)
     */
    public void init(com.crackj2ee.jexi.core.Document document) {
        this.component.init(document);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onKeyPressed(char)
     */
    public void onKeyPressed(char c) {
        this.component.onKeyPressed(c);
    }
    /* (non-Javadoc)
     * @see jexi.ui.View#onFunctionKeyPressed(int, boolean, boolean, boolean)
     */
    public void onFunctionKeyPressed(int keycode, boolean shift, boolean ctrl, boolean alt) {
        this.component.onFunctionKeyPressed(keycode, shift, ctrl, alt);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onFormatChanged(java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, jexi.ui.Color)
     */
    public void onFormatChanged(String fontName, Integer fontSize,
        Boolean bold, Boolean italic, Boolean underlined, Color color)
    {
        this.component.onFormatChanged(fontName, fontSize, bold, italic, underlined, color);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#onInsertPictureFromFile(java.lang.String)
     */
    public void onInsertPictureFromFile(String filename) {
        this.component.onInsertPictureFromFile(filename);
    }
}
