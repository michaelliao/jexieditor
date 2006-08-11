/*
 * Created on 2004-8-13
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

/**
 * PictureFactory is in charge of creating new Picture objects. 
 * Use PictureFactory.instance().createPicture("filename"). 
 * 
 * @author Xuefeng
 */
public abstract class PictureFactory {

    // singleton:
    private static PictureFactory instance = new com.crackj2ee.jexi.ui.swt.SWTPictureFactory();

    /**
     * Get the only instance of the PictureFactory. 
     * 
     * @return The instance of the PictureFactory.
     */
    public static PictureFactory instance() {
        return instance;
    }

    // to prevent client to create new instance:
    protected PictureFactory() {}

    /**
     * Create a Picture from file. 
     * 
     * @param filename The full file name, including path.
     * @return The Picture object.
     * @throws IOException If any io error. (such as 'file is not exist')
     */
    public abstract Picture createPicture(String filename) throws java.io.IOException;

}
