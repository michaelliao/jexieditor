/*
 * Created on 2004-8-5
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

/**
 * Selection represent the selection of the document. 
 * It uses 2 position to represent the glyphs that user 
 * selected. <br>
 * <b>NOTE</b>: The range is [start, end), so if [1,4), 
 * that means 1, 2, and 3 are selected, 4 are not selected. 
 * 
 * @author Xuefeng
 */
public final class Selection {

    private Document document;

    private Position start = null;
    private Position end = null;

    /**
     * Create a non-select selection. 
     */
    public Selection(Document document) {
        this.document = document;
    }

    /**
     * Get the start position. 
     * 
     * @return The start position.
     */
    public Position getStartPosition() {
        return this.start;
    }

    /**
     * Get the end position. 
     * 
     * @return The end position.
     */
    public Position getEndPosition() {
        return this.end;
    }

    /**
     * Test if the selection is 'non-select' or 'selected'. 
     * 
     * @return True if the selection has contains a range.
     */
    public boolean isSelected() {
        return this.start!=null;
    }

    /**
     * Make the section selected between start and end. 
     * 
     * @param start The start position.
     * @param end The end position.
     */
    public void select(Position start, Position end) {
        if(start==null || end==null) {
            unselect();
            return;
        }
        if(!start.equals(end)) {
            // make sure the start is in front of the end:
            if(start.frontOf(end)) {
                this.start = start;
                this.end = end;
            }
            else { // swap:
                this.start = end;
                this.end = start;
            }
        }
        else
            unselect();
    }

    /**
     * Cancel the selected section. 
     */
    public void unselect() {
        this.start = null;
        this.end = null;
    }

    /**
     * Check if the page is in the selection. 
     * 
     * @param pageIndex The page index.
     * @return True if this page is in the selection.
     */
    public boolean isSelected(int pageIndex) {
        if(!isSelected())
            return false;
        return (this.start.getPageIndex()<=pageIndex)
        	&& (pageIndex<=this.end.getPageIndex());
    }

    /**
     * Check if the page and row is in the selection. 
     * 
     * @param pageIndex The page index.
     * @param rowIndex The row index.
     * @return True if this row in this page is in the selection.
     */
    public boolean isSelected(int pageIndex, int rowIndex) {
        if(!isSelected())
            return false;
        long startLong = (long)this.start.getPageIndex()<<32 | this.start.getRowIndex();
        long endLong = (long)this.end.getPageIndex()<<32 | this.end.getRowIndex();
        long current = (long)pageIndex<<32 | rowIndex;
        return startLong<=current && current<=endLong;
    }

    /**
     * Check if the page and row and column is in the selection. 
     * 
     * @param pageIndex The page index.
     * @param rowIndex The row index.
     * @param columnIndex The column index.
     * @return True if this row in this page is in the selection.
     */
    public boolean isSelected(int pageIndex, int rowIndex, int columnIndex) {
        if(!isSelected())
            return false;
        long startLong = this.start.toLong();
        long endLong = this.end.toLong();
        long current = Position.toLong(pageIndex, rowIndex, columnIndex);
        return startLong<=current && current<endLong;
    }

    /**
     * Check if the paragraph is in the selection. 
     * 
     * @param p The paragraph object.
     * @return True if this paragraph is in the selection.
     */
    public boolean isSelected(Paragraph p) {
        if(!isSelected())
            return false;
        for(int nPage = start.getPageIndex(); nPage<=end.getPageIndex(); nPage++) {
            Page page = document.getPage(nPage);
            for(int nRow = 0; nRow<page.getRowsCount(); nRow++) {
                if(isSelected(nPage, nRow)) { // if this row is selected:
                    Row row = page.getRow(nRow);
                    if(row.getParagraph()==p)
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the first paragarph that is in the selection. If no text is 
     * selected, null will be returned. 
     * 
     * @return The first selected paragraph, or null if none selected.
     */
    public Paragraph getFirstSelectedParagraph() {
        if(!isSelected())
            return null;
        Page page = document.getPage(this.start.getPageIndex());
        Row row = page.getRow(this.start.getRowIndex());
        return row.getParagraph();
    }

    /**
     * Get the first index of glyph in the first paragraph that is in the selection. 
     * If no text is selected, (-1) will be returned. 
     * 
     * @return The first index of the glyph in the first paragraph, or (-1) if none selected.
     */
    public int getFirstSelectedIndex() {
        if(!isSelected())
            return (-1);
        Page page = document.getPage(this.start.getPageIndex());
        Row row = page.getRow(this.start.getRowIndex());
        return row.getStartIndex() + this.start.getColumnIndex();
    }

    /**
     * Get the last index of glyph in the last paragraph that is in the selection. 
     * If no text is selected, (-1) will be returned. <br>
     * <b>NOTE</b>: This index is not selected, the select range is [firstIndex, lastIndex). 
     * 
     * @return The last index of the glyph in the last paragraph, or (-1) if none selected.
     */
    public int getLastSelectedIndex() {
        if(!isSelected())
            return (-1);
        Page page = document.getPage(this.end.getPageIndex());
        Row row = page.getRow(this.end.getRowIndex());
        return row.getStartIndex() + this.end.getColumnIndex();
    }

    /**
     * Get the last paragarph that is in the selection. If no text is 
     * selected, null will be returned. 
     * 
     * @return The last selected paragraph, or null if none selected.
     */
    public Paragraph getLastSelectedParagraph() {
        if(!isSelected())
            return null;
        Page page = document.getPage(this.end.getPageIndex());
        Row row = page.getRow(this.end.getRowIndex());
        return row.getParagraph();
    }

    public void debug() {
        System.out.println("Selection:");
        if(isSelected()) {
            System.out.println("  Start from: " + start.toString() + " : " + getFirstSelectedIndex());
            System.out.println("  End at    : " + end.toString() + " : " + getLastSelectedIndex());
        }
        else
            System.out.println("  Unselected.");
    }
}
