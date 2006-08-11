/*
 * Created on 2004-7-22
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import java.util.*;

import com.crackj2ee.jexi.core.format.*;
import com.crackj2ee.jexi.ui.*;


/**
 * Caret is the position where to insert the glyph. 
 * A Paragraph index and an insert index can locate 
 * the caret. <br>
 * <b>NOTE</b>: Caret object is belonged to Document. 
 * To get a Caret, use Document.getCaret(); to locate 
 * caret to new position, use Document.setCaret(x, y), 
 * the point (x, y) is relative to Document rather 
 * than View. 
 * 
 * @author Xuefeng
 */
public final class Caret {

    // store the Document:
    private final Document document;

    // current page index (0-?):
    private int pageIndex = 0;

    // current row index (0-?):
    private int rowIndex = 0;

    // the caret in the row (0-?):
    private int columnIndex = 0;

    // cache the (x, y) and the height:
    //private int x = (-1);
    //private int y = (-1);
    //private int height = (-1);

    /**
     * Create a caret. Called by Document because only 
     * the document know the caret's location and it's 
     * height. 
     * 
     * @param document The document object.
     * @param pageIndex The paragraph index.
     * @param rowIndex The row index.
     * @param columnIndex The column index.
     */
    public Caret(Document document, int pageIndex, int rowIndex, int columnIndex) {
        this.document = document;
        this.pageIndex = pageIndex;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    /**
     * Move the caret to the next position. (User pressed "->" key). 
     * 
     * @return True if it moved.
     */
    public boolean moveRight() {
        ensureIndexAvailable();

        Page page = document.getPage(pageIndex);
        Row row = (Row)page.getRow(rowIndex);

        if( (columnIndex<row.size()-1) || // obviously can move next!
            (columnIndex==row.size()-1 && !row.isLast()) ) // check if it is the last row.
        {
            // can move in the same row:
            columnIndex++;
            return true;
        }
        else {
            // move to the next row:
            if(rowIndex==page.getRowsCount()-1) {
                // move to the next page:
                if(pageIndex==document.getPageCount()-1)
                    return false; // no next page!
                pageIndex++;
                rowIndex = 0;
                columnIndex = 0;
            }
            else {
                // move in the same page:
                rowIndex++;
                columnIndex = 0;
            }
        }
        return true;
    }

    /**
     * Move the caret to the previous position. (User pressed "<-" key) <br>
     * If it can move, then it will move and return TRUE, else no action is 
     * done and FALSE will returned. 
     * 
     * @return True if it moved.
     */
    public boolean moveLeft() {
        ensureIndexAvailable();

        Page page = document.getPage(pageIndex);
        Row row = (Row)page.getRow(rowIndex);

        if(columnIndex>0) {
            // can move previous in the same row:
            columnIndex--;
            return true;
        }
        else {
            // move to the previous row:
            if(rowIndex>0) {
                // in the same page:
                rowIndex--;
                Row prevRow = (Row)page.getRow(rowIndex);
                if(prevRow.isLast())
                    columnIndex = prevRow.size()-1; // put it before the '\r'.
                else
                    columnIndex = prevRow.size();
            }
            else {
                // in the previous page:
                if(pageIndex==0) return false; // no previous page!
                pageIndex--;
                Page prevPage = document.getPage(pageIndex);
                rowIndex = prevPage.getRowsCount()-1; // the last row of the page!
                Row prevRow = (Row)prevPage.getRow(rowIndex);
                if(prevRow.isLast())
                    columnIndex = prevRow.size()-1;
                else
                    columnIndex = prevRow.size();
            }
        }
        return true;
    }

    /**
     * Move caret to the specified position. 
     * 
     * @param position The position to move to.
     */
    public void moveTo(Position position) {
        this.pageIndex = position.getPageIndex();
        this.rowIndex = position.getRowIndex();
        this.columnIndex = position.getColumnIndex();
    }

    /**
     * Get the height of the caret. So that the View can set 
     * it's bound to {x, y, 2, height}. <br>
     * <b>NOTE</b>: The height is the right glyph's 
     * height, in fact is it's StringFormat.getFont().height() 
     * even if the glyph is not a Char. 
     * 
     * @return The current height of the caret.
     */
    public int getHeight() {
        ensureIndexAvailable();

        Row row = (Row)document.getPage(pageIndex).getRow(rowIndex);
        Paragraph paragraph = row.getParagraph();

        int insertIndex = row.getStartIndex() + columnIndex;
        Glyph g = paragraph.child(insertIndex);
        if(g instanceof Char)
            return paragraph.getStringFormat(insertIndex).getFont().height();
        return g.height();
    }

    /**
     * Get the point of the caret. So that the View can locate 
     * the caret at (Point.x, Point.y). <br>
     * <b>NOTE</b>: The point is relative to document, not View, 
     * so View must transform the returned point. 
     * 
     * @return The point of the caret.
     */
    public Point getLocation() {
        ensureIndexAvailable();

        Page page = document.getPage(pageIndex);
        Row row = (Row)page.getRow(rowIndex);
        Paragraph paragraph = row.getParagraph();
        PageFormat pf = document.getPageFormat();
        int insertIndex = row.getStartIndex() + columnIndex;
        //row.debug();
        //System.out.println("column index=" + columnIndex + ", insert index=" + insertIndex);

        int glyph_height = (-1);

        // calculate x:
        int x = pf.getLeftMargin() + Page.PAGE_SPACE / 2 +
            paragraph.getParagraphFormat().getLeftIndent();
        if(row.isFirst())
            x += row.getParagraph().getParagraphFormat().getFirstIndent();

        Graphics g = Application.instance().getFrame().getDefaultGraphics();
        for(int i=row.getStartIndex(); i<insertIndex; i++) {
            Glyph glyph = row.child(i);
            if(glyph instanceof Char) {
                // get height:
                for(int j=0;;j++) {
                    StringFormat sf = paragraph.getStringFormat(j);
                    if(sf.contains(insertIndex)) {
                        glyph_height = sf.getFont().height();
                        break;
                    }
                }

                g.setFont(paragraph.getStringFormat(i).getFont());
                x += g.getCharWidth(((Char)glyph).charValue());
            }
            else {
                glyph_height = glyph.height();
                if(i<insertIndex)
                    x += glyph.width();
            }
        }
System.out.println("glyph_height="+glyph_height);
        // calculate y:
        int y = pageIndex * (pf.getHeight() + Page.PAGE_SPACE)
            + pf.getTopMargin() + Page.PAGE_SPACE / 2;
        for(int i=0; i<=rowIndex; i++) {
            y += page.getRow(i).height();
        }
        if(glyph_height==(-1)) { // caret is before first glyph.
            Glyph glyph = row.child(row.getStartIndex());
            if(glyph instanceof Char) {
                // get height:
                for(int j=0;;j++) {
                    StringFormat sf = paragraph.getStringFormat(j);
                    if(sf.contains(row.getStartIndex())) {
                        glyph_height = sf.getFont().height();
                        break;
                    }
                }
            }
            else {
                glyph_height = glyph.height();
            }
        }
        y -= ( glyph_height + row.getParagraph().getRowSpace() / 2 );
        return new Point(x, y);
    }

    /**
     * Set the new location of the caret. The (x, y) of the document 
     * is the hit point clicked by user. We must calculate the nearest 
     * location of the document, and reset the paragraph and index. 
     * 
     * @param x The point x.
     * @param y The point y.
     */
    public void setLocation(int x, int y) {
        //System.out.println("setLocation(x=" + x + " y=" + y);
        // first find out which page the user clicked:
        Page page = null;
        Iterator page_it = document.getPages().iterator();
        while(page_it.hasNext()) {
            page = (Page)page_it.next();
            if(page.isEditRegion(x, y)) {
                System.out.println("click on page: "+document.indexOfPage(page));
                this.pageIndex = document.indexOfPage(page);
                break;
            }
        }

        // then find out the row nearest the 'y':
        Row row = null;
        int row_index = (-1);
        PageFormat pf = document.getPageFormat();
        int start_y = y - document.indexOfPage(page) * (pf.getHeight() + Page.PAGE_SPACE) - pf.getTopMargin() - Page.PAGE_SPACE/2;
        if(start_y<=0)
            row_index = 0;
        else {
            int acc_height = 0;
            for(int i=0; i<page.getRowsCount(); i++) {
                row = (Row)page.getRow(i);
                acc_height += row.height();
                if(acc_height>start_y) {
                    row_index = i;
                    break;
                }
            }
            if(row_index==(-1)) // the last row!
                row_index = page.getRowsCount()-1;
        }
        row = (Row)page.getRow(row_index);
        //System.out.println("Row index=" + row_index);
        this.rowIndex = row_index;

        // Paragraph can be got:
        Paragraph para = row.getParagraph();

        // and find out the column nearest the 'x':
        int glyph_index = (-1);
        ParagraphFormat paraFormat = para.getParagraphFormat();
        int start_x = x - Page.PAGE_SPACE/2 - document.getPageFormat().getLeftMargin()
            - paraFormat.getLeftIndent();
        if(row.isFirst())
            start_x -= paraFormat.getFirstIndent();
        //System.out.println("start_x=" + start_x);
        if(start_x<=0)
            glyph_index = row.getStartIndex(); // the first glyph!
        else {
            int acc_width = 0;
            int last_width = 0;
            Graphics g = Application.instance().getFrame().getDefaultGraphics();
            for(int i=row.getStartIndex(); i<=row.getEndIndex(); i++) {
                Glyph glyph = (Glyph)para.child(i);
                if(glyph instanceof Char) {
                    g.setFont(para.getStringFormat(i).getFont());
                    last_width = g.getCharWidth(((Char)glyph).charValue());
                }
                else
                    last_width = para.child(i).width();
                acc_width += last_width;
                if(acc_width>=start_x) {
                    if(start_x > acc_width-last_width/2)
                        glyph_index = i+1;
                    else
                        glyph_index = i;
                    break;
                }
            }
            if(glyph_index==(-1)) {
                glyph_index = row.getEndIndex();
                if(!row.isLast()) glyph_index++;
            }
        }
        //System.out.println("glyph index=" + glyph_index);
        this.columnIndex = glyph_index - row.getStartIndex();

        debug();
        // ok, set new caret:
        document.updateCaret();
    }

    /**
     * Get the row where the caret is. 
     * 
     * @return The row.
     */
    public Row getRow() {
        return document.getPage(pageIndex).getRow(rowIndex);
    }

    /**
     * Get the paragraph where the caret is. 
     * 
     * @return The paragraph.
     */
    public Paragraph getPargraph() {
        return getRow().getParagraph();
    }

    /**
     * Get the position of the caret. 
     * 
     * @return The position of the caret.
     */
    public Position getPosition() {
        return new Position(pageIndex, rowIndex, columnIndex);
    }

    /**
     * Get the insert index of the paragraph. 
     * 
     * @return The insert index.
     */
    public int getInsertIndex() {
        Row row = (Row) document.getPage(pageIndex).getRow(rowIndex);
        return row.getStartIndex()+columnIndex;
    }

    // if min<=v<=max, ok!
    // else min or max will be returned:
    private int ensureRange(int min, int max, int v) {
        if(min>v) return min;
        if(max<v) return max;
        return v;
    }

    // make sure all the index is available:
    private void ensureIndexAvailable() {
        pageIndex = ensureRange(0, document.getPageCount()-1, pageIndex);
        Page page = document.getPage(pageIndex);

        rowIndex = ensureRange(0, page.getRowsCount()-1, rowIndex);
        Row row = (Row)page.getRow(rowIndex);

        if(row.isLast())
            columnIndex = ensureRange(0, row.size()-1, columnIndex);
        else
            columnIndex = ensureRange(0, row.size(), columnIndex);
    }

    public void debug() {
        System.out.println("Caret at page " + pageIndex + " row " + rowIndex + " column " + columnIndex);
    }
}
