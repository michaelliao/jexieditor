/*
 * Created on 2004-7-17
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

/**
 * Glyph interface represent a vitual "Glyph" component, 
 * may be a simple Character or a composite "Row" that
 * contains several Characters or Pictures. <br>
 * 
 * <b>NOTE</b>: There are two kinds of Glyph: <br>
 * 
 * <b>Basic Glyph</b> that only store the document data 
 * structure but no layout information, such as "Char", 
 * "Picture", "Paragraph". <br>
 * 
 * <b>Physical Glyph</b> that store the physical structure 
 * to display properly on the screen. They are dynamic and 
 * used to format the "Basic Glyph" for display, such as 
 * "Row", "Page".
 * 
 * @author Xuefeng
 */
public interface Glyph {

	/**
	 * Get the width of this glyph. 
	 * This is used to display. 
	 * 
	 * @return The width of this glyph (pixel).
	 */
	int width();

	/**
	 * Get the height of this glyph. 
	 * This is used to display. 
	 * 
	 * @return The height of this glyph (pixel).
	 */
	int height();

	/**
	 * Draw the glyph.
	 * 
	 * @param g The graphics context passed by the caller.
	 */
	void draw(com.crackj2ee.jexi.ui.Graphics g);

}
