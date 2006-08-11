/*
 * Created on 2004-7-18
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

/**
 * Compositor interface defines how and when to format a composition 
 * such as 'Paragraph' and 'document'. 
 * 
 * @author Xuefeng
 */
public interface Compositor {

	/**
	 * To format a composition. (When to format)
	 */
	void compose();

	/**
	 * To set the composition. (What to format)
	 * 
     * @param composition The composition to be formatted such as "Paragraph".
	 */
    void setComposition(Composition composition);
}
