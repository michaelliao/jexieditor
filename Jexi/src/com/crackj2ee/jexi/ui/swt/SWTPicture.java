/*
 * Created on 2004-8-13
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui.swt;

import java.io.*;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import com.crackj2ee.jexi.core.Picture;
import com.crackj2ee.jexi.ui.Graphics;

/**
 * SWTPicture is the implementation of jexi.core.Picture, 
 * it is a Glyph. 
 * 
 * @author Xuefeng
 */
public class SWTPicture extends Picture {

    private Image image;
    private int width;
    private int height;

    SWTPicture(String filename) throws IOException {
        try {
            image = new Image(Display.getCurrent(), filename);
            Rectangle r = image.getBounds();
            this.width = r.width;
            this.height = r.height;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    /* (non-Javadoc)
     * @see jexi.core.Glyph#draw(jexi.ui.Graphics)
     */
    public void draw(Graphics g) {
        SWTGraphics swtg = (SWTGraphics)g;
        swtg.gc.drawImage(image, g.getCurrentX(), g.getCurrentY());
    }

    /* (non-Javadoc)
     * @see jexi.core.Glyph#width()
     */
    public int width() {
        return this.width;
    }

    /* (non-Javadoc)
     * @see jexi.core.Glyph#height()
     */
    public int height() {
        return this.height;
    }
}
