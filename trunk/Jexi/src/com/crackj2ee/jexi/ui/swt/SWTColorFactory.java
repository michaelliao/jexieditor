/*
 * Created on 2004-7-31
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui.swt;

import java.util.*;

/**
 * The implementation of ColorFactory. 
 * 
 * @author Xuefeng
 */
public class SWTColorFactory extends com.crackj2ee.jexi.ui.ColorFactory {

    // cache the color:
    private Hashtable colors = new Hashtable();

    // store the Display object:
    private org.eclipse.swt.widgets.Display display = null;

    /**
     * Create color. 
     * 
     * @see com.crackj2ee.jexi.ui.ColorFactory#createColor(int, int, int)
     */
    public com.crackj2ee.jexi.ui.Color createColor(int r, int g, int b) {
        // first check if it already cached:
        Integer key = SWTColor.toKey(r, g, b);
        Object o = colors.get(key);
        if(o!=null) {
            SWTColor color = (SWTColor)o;
            color.addRef(); // add a reference!
            return color;
        }

        // if not found, we create a new color:
        if(this.display==null) {
            this.display = ((com.crackj2ee.jexi.ui.swt.SWTFrame)(com.crackj2ee.jexi.ui.Application.instance().getFrame())).getDisplay();
        }
        SWTColor newColor = new SWTColor(key, new org.eclipse.swt.graphics.Color(display, r, g, b));
        // put it to cache:
        colors.put(key, newColor);
        return newColor;
    }

    /**
     * Clear all color resources when application terminated. 
     * 
     * @see com.crackj2ee.jexi.ui.ColorFactory#clearAllColors()
     */
    public void clearAllColors() {
        Collection all_colors = new ArrayList( colors.values() );
        Iterator it = all_colors.iterator();
        while(it.hasNext()) {
            com.crackj2ee.jexi.ui.swt.SWTColor color = (com.crackj2ee.jexi.ui.swt.SWTColor)it.next();
            while(color.refCount()>0) {
                System.out.println("WARNING: Some colors are not released.");
                color.dispose();
            }
        }
        // clear hash table:
        colors.clear();
    }

    /**
     * Get the size of the cache. 
     */
    public int size() {
        return colors.size();
    }

    // remove the color:
    protected void remove(SWTColor c) {
        colors.remove(c.getKey());
    }

    public void debug() {
        System.out.println("\n[ColorFactory: " + colors.size() + " colors]");
        Iterator it = colors.values().iterator();
        while(it.hasNext()) {
            com.crackj2ee.jexi.ui.Color c = (com.crackj2ee.jexi.ui.Color)it.next();
            c.debug();
        }
        System.out.println("-- END ColorFactory --");
    }
}
