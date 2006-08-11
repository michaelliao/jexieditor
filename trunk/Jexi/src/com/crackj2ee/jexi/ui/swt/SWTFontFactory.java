/*
 * Created on 2004-7-27
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui.swt;

import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.crackj2ee.jexi.ui.*;


/**
 * The implementation of FontFactory. 
 * 
 * @author Xuefeng
 */
public class SWTFontFactory extends FontFactory {

    // store all fonts that is used now:
    private Hashtable fonts = new Hashtable();

    // store all fonts' name:
    private String[] fontNames = null;

    // we assume that the Display will always available:
    private Display display = null;

    /**
     * Create new font. 
     * 
     * @see com.crackj2ee.jexi.ui.FontFactory#createFont(java.lang.String, int, boolean, boolean, boolean)
     */
    public com.crackj2ee.jexi.ui.Font createFont(String name, int size, boolean bold, boolean italic, boolean underlined) {
        // first lookup if it already existed:
        String key = com.crackj2ee.jexi.ui.swt.SWTFont.toKey(name, size, bold, italic, underlined);
        Object obj = fonts.get(key);
        if(obj!=null) {
            // found it!
            com.crackj2ee.jexi.ui.swt.SWTFont _font = (com.crackj2ee.jexi.ui.swt.SWTFont)obj;
            _font.addRef();
            return _font;
        }

        if(display==null) {
            SWTFrame frame = (SWTFrame)Application.instance().getFrame();
            display = frame.getDisplay();
        }
        // create real font resource:
        int style = ( bold ? SWT.BOLD : SWT.NORMAL ) | ( italic ? SWT.ITALIC : SWT.NORMAL );
        org.eclipse.swt.graphics.Font f = new org.eclipse.swt.graphics.Font(
            display, name, size, style);
        // wrap as jexi.ui.Font:
        com.crackj2ee.jexi.ui.Font font = new com.crackj2ee.jexi.ui.swt.SWTFont(name, size, bold, italic, underlined, f);
        // cache it:
        fonts.put(font.toString(), font);
        return font;
    }

    // enumerate all fonts installed in the system:
    public String[] enumerateFonts() {
        // TODO Auto-generated method stub
        if(fontNames==null) {
            fontNames = new String[3];
            fontNames[0] = "Arial";
            fontNames[1] = "System";
            fontNames[2] = "Tahoma";
        }
        return fontNames;
    }

    /**
     * dispose all fonts it used now. 
     * 
     * @see com.crackj2ee.jexi.ui.FontFactory#clearAllFonts()
     */
    public void clearAllFonts() {
        Collection all_fonts = new ArrayList( fonts.values() );
        Iterator it = all_fonts.iterator();
        while(it.hasNext()) {
            com.crackj2ee.jexi.ui.swt.SWTFont font = (com.crackj2ee.jexi.ui.swt.SWTFont)it.next();
            while(font.refCount()>0) {
                System.out.println("WARNING: Some fonts are not released.");
                font.dispose();
            }
        }
        // clear hash table:
        fonts.clear();
    }

    /**
     * Remove the font from the cache. 
     * 
     */
    protected void remove(com.crackj2ee.jexi.ui.Font font) {
        fonts.remove(font.toString());
    }

    /**
     * Get the count of current used fonts. 
     *  
     * @see com.crackj2ee.jexi.ui.FontFactory#fontCount()
     */
    public int fontCount() {
        return fonts.size();
    }

    public void debug() {
        System.out.println("\n[SWTFontFactory] fonts=" + fonts.size());
        Collection all_fonts = fonts.values();
        Iterator it = all_fonts.iterator();
        while(it.hasNext()) {
            com.crackj2ee.jexi.ui.swt.SWTFont font = (com.crackj2ee.jexi.ui.swt.SWTFont)it.next();
            font.debug();
        }
    }
}
