/*
 * Created on 2004-7-20
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.format;

/**
 * Page Format, associated by a Page object. 
 * See image/page_style.bmp 
 * 
 * @author Xuefeng
 */
public class PageFormat {

    /**
     * The minimum value of the margin.
     */
    public static final int MIN_MARGIN = 12;

    /**
     * The minimum value of the available width.
     */
    public static final int MIN_BODY_WIDTH = 48;

    /**
     * The minimum value of the available height.
     */
    public static final int MIN_BODY_HEIGHT = 50;

    // member variables:
    private int width;
    private int height;

    private int leftMargin;
    private int rightMargin;

    private int topMargin;
    private int bottomMargin;

    /**
     * The default page style: A4 paper.
     */
    public static final PageFormat DEFAULT = new PageFormat(Paper.CUSTOM);

    /**
     * Default page style: A4.
     */
    public PageFormat() {
        reset(Paper.A4);
    }

    /**
     * Page style specified by the paper.
     * 
     * @param paper The paper type.
     */
    public PageFormat(Paper paper) {
        reset(paper);
    }

    /**
     * Reset the page style to the default values by specified paper type. 
     * For example, reset(Paper.A4); 
     * 
     * @param paper The paper type.
     */
    public void reset(Paper paper) {
        this.width = paper.width;
        this.height = paper.height;
        this.leftMargin = paper.leftMargin;
        this.rightMargin = paper.rightMargin;
        this.topMargin = paper.topMargin;
        this.bottomMargin = paper.bottomMargin;
    }

    /**
     * Calculate the available width of the page. 
     * 
     * @return The available width.
     */
    public int scaleWidth() {
        return width - leftMargin - rightMargin;
    }

    /**
     * Calculate the available height of the page. 
     * 
     * @return The available height.
     */
    public int scaleHeight() {
        return height - topMargin - bottomMargin;
    }

    /**
     * Get the left margin. 
     * 
     * @return The left margin.
     */
    public int getLeftMargin() {
        return this.leftMargin;
    }

    /**
     * Get the top margin. 
     * 
     * @return The top margin.
     */
    public int getTopMargin() {
        return this.topMargin;
    }

    /**
     * @return Returns the height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height The height to set.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return Returns the width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width The width to set.
     */
    public void setWidth(int width) {
        this.width = width;
    }
}
