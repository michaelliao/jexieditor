/*
 * Created on 2004-8-5
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

/**
 * A position can locate the caret or the selection's range.
 * 
 * @author Xuefeng
 */
public final class Position {

    private int pageIndex;
    private int rowIndex;
    private int columnIndex;

    /**
     * Create a position. 
     * 
     * @param pageIndex The page index.
     * @param rowIndex The row index.
     * @param columnIndex The column index.
     */
    public Position(int pageIndex, int rowIndex, int columnIndex) {
        this.pageIndex = pageIndex;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    /**
     * Create a position by AbsPosition. That means, 
     * convert the point (paragraphIndex, insertIndex) 
     * to point (pageIndex, rowIndex, columnIndex). 
     * 
     * @param document The document object.
     * @param absPosition The AbsPosition object.
     */
    public Position(Document document, AbsPosition absPosition) {
        Paragraph paragraph = document.getParagraph(absPosition.getParagraphIndex());
        for(int i=0; i<paragraph.getRowsCount(); i++) {
            Row row = paragraph.getRow(i);
            if(row.contains(absPosition.getInsertIndex())) {
                // found the row!
                for(int j=0; j<document.getPageCount(); j++) {
                    Page page = document.getPage(j);
                    int n = page.indexOfRow(row);
                    if(n!=(-1)) {
                        // found the page!
                        this.pageIndex = j;
                        this.rowIndex = n;
                        this.columnIndex = absPosition.getInsertIndex()-row.getStartIndex();
                    }
                }
            }
        }
    }

    /**
     * Get the page index. 
     * 
     * @return The page index.
     */
    public int getPageIndex() {
        return this.pageIndex;
    }

    /**
     * Get the row index. 
     * 
     * @return The row index.
     */
    public int getRowIndex() {
        return this.rowIndex;
    }

    /**
     * Get the column index.
     * 
     * @return The column index.
     */
    public int getColumnIndex() {
        return this.columnIndex;
    }

    /**
     * To combine the 3 int to a long as: <br>
     * pageIndex << 32 + rowIndex << 16 + columnIndex.
     */
    public static long toLong(int page, int row, int column) {
        return ((long)page<<32) | (row<<16) | column;
    }

    /**
     * To combine the 3 int to a long as: <br>
     * pageIndex << 32 + rowIndex << 16 + columnIndex.
     */
    public long toLong() {
        return toLong(pageIndex, rowIndex, columnIndex);
    }

    /**
     * Test the two position is point to the same position.
     */
    public boolean equals(Object o) {
        if(o instanceof Position) {
            Position p = (Position)o;
            return this.toLong()==p.toLong();
        }
        return false;
    }

    /**
     * Test if this position is in front of the specified position. <br>
     * <b>NOTE</b>: FALSE will be returned if these two are equals. 
     * 
     * @param p The specified position to be compared.
     * @return True if this position is in front of the specified position.
     */
    public boolean frontOf(Position p) {
        long p1 = toLong();
        long p2 = p.toLong();
        return p1<p2;
    }

    /**
     * To get the description of the position. 
     */
    public String toString() {
        return "page=" + pageIndex + ", row=" + rowIndex + ", column=" + columnIndex;
    }
}
