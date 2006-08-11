/*
 * Created on 2004-7-20
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.format;

import com.crackj2ee.jexi.util.*;

/**
 * Paper defines some paper formats, such as A4, B5. 
 * Paper is immutable class. 
 * 
 * @author Xuefeng
 */
public class Paper {

    public final String name;

    public final int width;
    public final int height;

    public final int leftMargin;
    public final int rightMargin;

    public final int topMargin;
    public final int bottomMargin;

    // To make sure that client cannot create a new instance:
    private Paper(String name, float width, float height,
        float leftMargin, float rightMargin, float topMargin,
        float bottomMargin)
    {
        this.name = name;
        this.width = Calc.cm2pixel(width);
        this.height = Calc.cm2pixel(height);
        this.leftMargin = Calc.cm2pixel(leftMargin);
        this.rightMargin = Calc.cm2pixel(rightMargin);
        this.topMargin = Calc.cm2pixel(topMargin);
        this.bottomMargin = Calc.cm2pixel(bottomMargin);
    }

    /**
     * A4
     */
    public static final Paper A4 = new Paper("A4", 21.0f, 29.7f, 3.17f, 3.17f, 2.54f, 2.54f);

    /**
     * Custom
     */
    public static final Paper CUSTOM = new Paper("Custom", 18.0f, 24.0f, 1.2f, 1.2f, 1.0f, 1.0f);
}
