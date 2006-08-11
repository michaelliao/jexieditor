/*
 * Created on 2004-7-20
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.format;

import com.crackj2ee.jexi.core.*;

/**
 * Paragraph format. 
 * 
 * @author Xuefeng
 */
public class ParagraphFormat {

    // for increase left indent or right indent:
    private static final int INDENT_STEP = 28;
    // the min value of the scale width:
    private static final int MIN_SCALEWIDTH = 32;

    /**
     * Alignment: left.
     */
    public static final int ALIGN_LEFT = 0;
    /**
     * Alignment: center.
     */
    public static final int ALIGN_CENTER = 1;
    /**
     * Alignment: right.
     */
    public static final int ALIGN_RIGHT = 2;

    // member variables:
    private int alignment = ALIGN_LEFT; // alignment.

    private int width; // total width, equals PageFormat.scaleWidth

    private int firstIndent = 24;   // first row indent.
    private int leftIndent = 10;    // left indent.
    private int rightIndent = 10;   // right indent.

    private int rowSpace = 6;    // row space.

    public ParagraphFormat(Document doc) {
        this.width = doc.getPageFormat().scaleWidth();
    }

    /**
     * Calculate the available width of the paragraph to layout. 
     * 
     * @param firstRow If this row is the first row.
     * @return The width the row should taken.
     */
    public int scaleWidth(boolean firstRow) {
        if( firstRow ) { // first row should have additional indent.
            return ( width - leftIndent - rightIndent - firstIndent );
        }
        return ( width - leftIndent - rightIndent );
    }

    /**
     * Increase the left indent. But if the scalewidth is too small, 
     * it will do nothing.
     */
    public void increaseLeftIndent() {
        if( scaleWidth(false)-INDENT_STEP >= MIN_SCALEWIDTH )
            leftIndent += INDENT_STEP;
    }

    /**
     * Increase the right indent. But if the scalewidth is too small, 
     * it will do nothing.
     */
    public void increaseRightIndent() {
        if( scaleWidth(false)-INDENT_STEP >= MIN_SCALEWIDTH )
            rightIndent += INDENT_STEP;
    }

    /**
     * @return Returns the alignment.
     */
    public int getAlignment() {
        return alignment;
    }

    /**
     * @param alignment The alignment to set.
     */
    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    /**
     * @return Returns the firstIndent.
     */
    public int getFirstIndent() {
        return firstIndent;
    }

    /**
     * @param firstIndent The firstIndent to set.
     */
    public void setFirstIndent(int firstIndent) {
        this.firstIndent = firstIndent;
    }

    /**
     * @return Returns the leftIndent.
     */
    public int getLeftIndent() {
        return leftIndent;
    }

    /**
     * @param leftIndent The leftIndent to set.
     */
    public void setLeftIndent(int leftIndent) {
        this.leftIndent = leftIndent;
    }

    /**
     * @return Returns the rightIndent.
     */
    public int getRightIndent() {
        return rightIndent;
    }

    /**
     * @param rightIndent The rightIndent to set.
     */
    public void setRightIndent(int rightIndent) {
        this.rightIndent = rightIndent;
    }

    /**
     * @return Returns the rowSpace.
     */
    public int getRowSpace() {
        return rowSpace;
    }

    /**
     * @param rowSpace The rowSpace to set.
     */
    public void setRowSpace(int rowSpace) {
        this.rowSpace = rowSpace;
    }

    /**
     * Get the paragraph's width. 
     * 
     * @return The paragraph's width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * @param width The width to set.
     */
    public void setWidth(int width) {
        this.width = width;
    }
}
