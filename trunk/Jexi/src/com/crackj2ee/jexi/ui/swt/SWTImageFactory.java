/*
 * Created on 2004-8-11
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui.swt;

import java.io.InputStream;

import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;


/**
 * Manage all images used in the tool bar. 
 * 
 * @author Xuefeng
 */
public class SWTImageFactory {

    // the display:
    private Display display;
    // store images:
    //private Hashtable images = new Hashtable();

    public SWTImageFactory(Display display) {
        this.display = display;
    }

    /**
     * Load the named image of toolbar. 
     * 
     * @param name The name of the image, such as "open", "copy", etc.
     * @return The image object.
     */
    public Image loadToolbarImage(String name) {
        return loadFromResource("/res/toolbar/" + name + ".bmp");
    }

    /**
     * Load the named image of menu item of colors. 
     * 
     * @param name The name of the image.
     * @return The image object.
     */
    public Image loadMenuColorImage(String name) {
        return loadFromResource("/res/toolbar/color/" + name + ".bmp");
    }

    // load resource from resource:
    private Image loadFromResource(String full_path) {
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(full_path);
            if(is!=null) {
                Image img = new Image(display, is);
                return img;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(is!=null) {
                try { is.close(); } catch(Exception e){}
            }
        }
        return null;
    }

}
