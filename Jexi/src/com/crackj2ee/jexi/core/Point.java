/*
 * Created on 2004-8-3
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

/**
 * Represent a point (x, y). 
 * 
 * @author Xuefeng
 */
public final class Point {
    public int x;
    public int y;

    /**
     * Create a new point (0, 0). 
     */
    public Point() { x = y = 0; }

    /**
     * Create a new point (x, y). 
     * 
     * @param x The point x.
     * @param y The point y.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
