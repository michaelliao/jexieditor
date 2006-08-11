/*
 * Created on 2004-7-25
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

/**
 * View interface. A view has width and height, displayed on the 
 * rectagle region specified by the getWidth() and getHeight(). <br>
 * When the size of the window changed, the size of the view is 
 * also changed, and the Frame will call setWidth() and setHeight() 
 * to notify the new size, and call update() to update view. <br>
 * <b>NOTE</b>: View is create by Frame, so use 
 * Application.instance().getFrame().getView() to get the view. 
 * 
 * @author Xuefeng
 */
public interface View {

    /**
     * Init the view. 
     */
    void init(com.crackj2ee.jexi.core.Document document);

    /**
     * Destroy the view and release any resources it used. 
     */
    void dispose();

    /**
     * Update view. Called by Frame or Document. 
     */
    void update();

    /**
     * Get the document. 
     * 
     * @return The document object.
     */
    com.crackj2ee.jexi.core.Document getDocument();

    /**
     * Set the document. Called by Frame. 
     * 
     * @param document The document object.
     */
    void setDocument(com.crackj2ee.jexi.core.Document document);

    /**
     * Get the width of the view. 
     * 
     * @return The width of the view.
     */
    int getWidth();

    /**
     * Get the height of the view. 
     * 
     * @return The height of the view.
     */
    int getHeight();

    /**
     * Set the new size when size changed. 
     * 
     * @param width The new width of the view.
     * @param height The new height of the view.
     */
    void onSizeChanged(int width, int height);

    /**
     * Get the offset (x, y) of the document. 
     * 
     * @return The offset of x.
     */
    int getOffsetX();

    /**
     * Get the offset (x, y) of the document. 
     * 
     * @return The offset of y.
     */
    int getOffsetY();

    /**
     * Set the new offset of x. 
     * 
     * @param x The offset of x.
     */
    void setOffsetX(int x);

    /**
     * Set the new offset of y. 
     * 
     * @param y The offset of y.
     */
    void setOffsetY(int y);

    /**
     * When document's size changed (Page added or removed, or Page size changed), 
     * document will invoke this method to notify view to adjust it's data. Then 
     * view's update() will be called later on. 
     */
    void onDocumentSizeChanged();

    /**
     * When user changed the font name of the selected text. 
     * 
     * @param fontName The font name, or null if ignore.
     * @param fontSize The font size, or null if ignore.
     * @param bold The bold attribute, or null if ignore.
     * @param italic The italic attribute, or null if ignore.
     * @param underlined The underlined attribute, or null if ignore.
     * @param color The color, or null if ignore.
     */
    void onFormatChanged(String fontName, Integer fontSize, Boolean bold, Boolean italic, Boolean underlined, Color color);

    /**
     * Caret2 must reset to the new point with new height. 
     * 
     * @param x The point x.
     * @param y The point y.
     * @param height The height of the caret.
     */
    void onSetCaret(int x, int y, int height);

    /**
     * To ensure the caret is visible. 
     */
    void ensureCaretVisible();

    /**
     * User pressed a key that can be displayed. <br>
     * <b>NOTE</b>: This event will not be triggered if function keys 
     * are pressed such as "Ctrl+?", "F1-F12", "Home", etc.
     * 
     * @param c The char value represent the key.
     */
    void onKeyPressed(char c);

    /**
     * User pressed a function key such as "Enter", "Right", 
     * "F1", "Home", etc. 
     * 
     * @param keycode The keycode.
     * @param shift The shift key is pressed or not.
     * @param ctrl The ctrl key is pressed or not.
     * @param alt The alt key is pressed or not.
     */
    void onFunctionKeyPressed(int keycode, boolean shift, boolean ctrl, boolean alt);

    /**
     * User want to insert a picture from file. 
     * 
     * @param filename The picture file.
     */
    void onInsertPictureFromFile(String filename);

    /**
     * The mouse move event. 
     * 
     * @param x Point x.
     * @param y Point y.
     */
    void onMouseMove(int x, int y);

    void onLButtonDown(int x, int y);
    void onLButtonUp(int x, int y);
    void onRButtonDown(int x, int y);
    void onRButtonUp(int x, int y);

    void onLButtonDblClick(int x, int y);
}
