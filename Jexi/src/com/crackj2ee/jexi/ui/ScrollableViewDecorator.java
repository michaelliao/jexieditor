/*
 * Created on 2004-7-25
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

import com.crackj2ee.jexi.core.Document;

/**
 * Decorator for scroll view. 
 * 
 * @author Xuefeng
 */
public abstract class ScrollableViewDecorator extends ViewDecorator {

    /**
     * Constructor. 
     * 
     * @param component The component to be decorated.
     */
    public ScrollableViewDecorator(View component) {
        super(component);
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#dispose()
     */
    public void dispose() {
        this.component.dispose();
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#getDocument()
     */
    public Document getDocument() {
        return this.component.getDocument();
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#getHeight()
     */
    public int getHeight() {
        return this.component.getHeight();
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#getOffsetX()
     */
    public int getOffsetX() {
        return this.component.getOffsetX();
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#getOffsetY()
     */
    public int getOffsetY() {
        return this.component.getOffsetY();
    }

    /* (non-Javadoc)
     * @see jexi.ui.View#getWidth()
     */
    public int getWidth() {
        return this.component.getWidth();
    }

    /*
     * @see jexi.ui.View#onMouseMove(int, int)
     */
    public void onMouseMove(int x, int y) {
        this.component.onMouseMove(x, y);
    }
}
