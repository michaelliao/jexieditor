/*
 * Created on 2004-7-23
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

/**
 * Frame represent a window, or a shell.
 * 
 * @author Xuefeng
 */
public interface Frame {

    /**
     * Get the view. 
     * 
     * @return The View object.
     */
	View getView();

    /**
     * Initialize the frame before the message loop start. 
     */
	void init();

    /**
     * Show the main window and start to run message loop. 
     */
	void show();

    /**
     * Clean up any resources that is used before close the window. 
     */
	void dispose();

    /**
     * This default graphics is created when the Frame initiliazed, 
     * and dispose when the Frame disposed. 
     * 
     * @return The default graphics. (DO NOT dispose it!)
     */
	Graphics getDefaultGraphics();
}
