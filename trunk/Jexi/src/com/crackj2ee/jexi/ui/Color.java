/*
 * Created on 2004-7-21
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

/**
 * The Color interface. 
 * 
 * @author Xuefeng
 */
public interface Color {

    public static final Color BLACK = ColorFactory.instance().createColor(0);
    public static final Color WHITE = ColorFactory.instance().createColor(0xffffff);
    public static final Color RED   = ColorFactory.instance().createColor(0xff0000);
    public static final Color GREEN = ColorFactory.instance().createColor(0xff00);
    public static final Color BLUE  = ColorFactory.instance().createColor(0xff);

    /**
     * Dispose this color resource. 
     */
    void dispose();

    /**
     * Don't forget override "equals()"! 
     */
    boolean equals(Object o);

    /**
     * Don't forget override "hashCode()"! 
     */
    int hashCode();

    void debug();
}
