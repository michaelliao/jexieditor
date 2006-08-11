/*
 * Created on 2004-8-13
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui.swt;

import java.io.IOException;

import com.crackj2ee.jexi.core.Picture;
import com.crackj2ee.jexi.core.PictureFactory;


/**
 * TODO Description here...
 * 
 * @author Xuefeng
 */
public class SWTPictureFactory extends PictureFactory {

    /* (non-Javadoc)
     * @see jexi.core.PictureFactory#createPicture(java.lang.String)
     */
    public Picture createPicture(String filename) throws IOException {
        // TODO Auto-generated method stub
        return new SWTPicture(filename);
    }

}
