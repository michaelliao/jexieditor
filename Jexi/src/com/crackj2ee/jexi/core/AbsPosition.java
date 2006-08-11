/*
 * Created on 2004-8-15
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

/**
 * Another method to define a position using (paragraphIndex, insertIndex). 
 * 
 * @author Xuefeng
 */
public class AbsPosition {

    // store the paragraph insert position:
    private int paragraphIndex;
    private int insertIndex;

    /**
     * Init the AbsPosition by Position. 
     * 
     * @param document
     * @param position
     */
    public AbsPosition(Document document, Position position) {
        // get the page and row:
        Page page = document.getPage(position.getPageIndex());
        Row row = page.getRow(position.getRowIndex());
        // get the paragraph index:
        paragraphIndex = document.indexOfParagraph(row.getParagraph());
        insertIndex = row.getStartIndex() + position.getColumnIndex();
        System.out.println("AbsPosition: para=" + this.paragraphIndex + ", insert=" + this.insertIndex);
    }

    /**
     * Get the paragraph index. 
     * 
     * @return The paragraph index.
     */
    public int getParagraphIndex() {
        return this.paragraphIndex;
    }

    /**
     * Get the insert index in the paragraph. 
     * 
     * @return The insert index.
     */
    public int getInsertIndex() {
        return this.insertIndex;
    }

}
