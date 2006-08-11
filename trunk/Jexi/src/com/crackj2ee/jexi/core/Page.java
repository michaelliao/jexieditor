/*
 * Created on 2004-7-18
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import java.util.*;

import com.crackj2ee.jexi.core.format.*;
import com.crackj2ee.jexi.ui.*;


/**
 * Page represent a page object than contains several rows. <br>
 * 
 * <b>NOTE</b>: Page is a physical glyph that is used for display 
 * rather than document structure. <br>
 * 
 * When each paragraph has being formatted into Row(s), the Page(s) 
 * object(s) then are created by formatted Row(s). Page cannot 
 * contains Paragraph because a paragraph may be splitted into 2 or 
 * more pages.
 * 
 * @author Xuefeng
 */
public class Page {

    // this is for drawing, not to near the border of the view:
    protected static final int PAGE_SPACE = 16;

    // store the document:
    Document document;

	// store the rows references:
    private ArrayList rows = new ArrayList(5);

    /**
     * Create a new page. 
     * 
     * @param document
     */
    public Page(Document document) {
        this.document = document;
    }

    /**
     * Get the list of rows.
     * 
     * @return List of rows.
     */
    public List getRows() {
        return this.rows;
    }

    /**
     * Add a Row object.
     * 
     * @param index The position to be added to.
     * @param r The Row object.
     */
    public void addRow(int index, Row r) {
        Assert.checkNull(r);
        Assert.checkTrue(index>=0 && index<=rows.size());
        rows.add(index, r);
    }

    /**
     * Add a Row object in the end of the list. 
     * 
     * @param r The Row object.
     */
    public void addRow(Row r) {
        addRow(rows.size(), r);
    }

	/**
	 * Get the child row.
	 * 
	 * @param index The position of the row.
	 * @return The row object.
	 */
	public Row getRow(int index) {
		Assert.checkRange(index, rows);
		return (Row)rows.get(index);
	}

    /**
     * Get the index of the row in this page. 
     * 
     * @param row The row to be tested.
     * @return The index of the row, or (-1) if not found.
     */
    public int indexOfRow(Row row) {
        return this.rows.indexOf(row);
    }

    /**
	 * Remove the specified row.
	 * 
	 * @param row The specified row to be removed.
	 */
	public void removeRow(Row row) {
		Assert.checkNull(row);
		boolean b = rows.remove(row);
		Assert.checkTrue(b);
	}

	/**
	 * Get the count of the rows this page contains.
	 * 
	 * @return The count of the rows.
	 */
	public int getRowsCount() {
		return rows.size();
	}

	/**
	 * Get the width of the page. (the whole width.)
	 * 
     * @see com.crackj2ee.jexi.core.Glyph#width()
     */
    public int width() {
        return this.document.getPageFormat().getWidth() + PAGE_SPACE;
    }

    /**
	 * Get the height of the page. (the whole height.)
	 * 
     * @see com.crackj2ee.jexi.core.Glyph#height()
     */
    public int height() {
        return this.document.getPageFormat().getHeight() + PAGE_SPACE;
    }

    /**
     * Get the available width of the page used to layout. 
     * 
     * @return The available width.
     */
    public int scaleWidth() {
        return this.document.getPageFormat().scaleWidth();
    }

    /**
     * Get the available height of the page used to layout. 
     * 
     * @return The available height.
     */
    public int scaleHeight() {
        return this.document.getPageFormat().scaleHeight();
    }

    /**
     * Get the rows' total height. 
     * 
     * @return The rows' total height.
     */
    public int rowsHeight() {
        int totalHeight = 0;
        Iterator it = this.rows.iterator();
        while(it.hasNext()) {
            Row row = (Row)it.next();
            totalHeight += row.height();
        }

        // if only one row, the row height can be greater than scale height:
        Assert.checkTrue(totalHeight <= this.scaleHeight() || getRowsCount()==1);
        return totalHeight;
    }

    /**
     * Draw glyphs on this page. 
     */
    public void draw(Graphics g, Selection sel) {
        int org_x = g.getCurrentX();
        int org_y = g.getCurrentY();

        // test if this page is in the selection:
        int pageIndex = document.indexOfPage(this);
        boolean selInThisPage = sel.isSelected(pageIndex);

        // draw page border:
        int x = org_x + PAGE_SPACE / 2;
        int y = org_y + PAGE_SPACE / 2;
        int w = this.document.getPageFormat().getWidth();
        int h = this.document.getPageFormat().getHeight();
        //System.out.println("width="+w+" height="+h);
        g.setForecolor(Color.BLACK);
        g.moveTo(x, y);
        g.drawRectangle(w, h); // draw border.
        x++; y++;
        g.setForecolor(Color.WHITE);
        g.moveTo(x, y);
        g.fillRect(x, y, w-2, h-2); // fill the page as white.

        // now draw each row:
        x = org_x + PAGE_SPACE / 2 + this.document.getPageFormat().getLeftMargin();
        y = org_y + PAGE_SPACE / 2 + this.document.getPageFormat().getTopMargin();

        //Iterator it = rows.iterator();
        //while(it.hasNext()) {
        for(int i=0; i<this.getRowsCount(); i++) {
            Row row = (Row)getRow(i);
            // locate the row:
            ParagraphFormat pf = row.getParagraph().getParagraphFormat();
            boolean b = row.getParagraph().isFirstRow(row);

            g.moveTo(x + pf.getLeftIndent() + (b?pf.getFirstIndent():0), y);
            int colStart = (-1);
            int colEnd = (-1);
            boolean selInThisRow = sel.isSelected(pageIndex, i);
            if(selInThisRow) {
                Position startP = sel.getStartPosition();
                Position endP = sel.getEndPosition();
                // calculate the start column and end column in this row:
                if(pageIndex==startP.getPageIndex() && i==startP.getRowIndex())
                    colStart = startP.getColumnIndex();
                else
                    colStart = 0;
                if(pageIndex==endP.getPageIndex() && i==endP.getRowIndex())
                    colEnd = endP.getColumnIndex();
                else
                    colEnd = row.size();
            }
            // draw row:
            row.draw(g, colStart, colEnd);
            // calculate the next row's location:
            y += row.height();
        }
    }

    /**
     * If this point is in this page. 
     */
    public boolean intersects(int x, int y) {
        Assert.checkTrue(x>=0 && y>=0);
        int index = this.document.indexOfPage(this);
        Assert.checkTrue(index>=0);

        int width = this.document.getPageFormat().getWidth();
        int height = this.document.getPageFormat().getHeight();
        return isInRange(index * height, (index+1) * height, y);
    }

    private boolean isInRange(int min, int max, int v) {
        if(v>=min && v<max)
            return true;
        return false;
    }

    public void debug() {
        System.out.println("\n[ Page ] rows = " + this.rows.size());
        Iterator it = this.rows.iterator();
        while(it.hasNext()) {
            Row row = (Row) it.next();
            row.debug();
        }
        System.out.println("-- End Page --");
    }

    /**
     * Test the point (x, y) is in the edit region of this page. 
     * 
     * @param x The point x.
     * @param y The point y.
     * @return True if the point is in the edit region.
     */
    public boolean isEditRegion(int x, int y) {
        Assert.checkTrue(x>=0 && y>=0);
        int index = this.document.indexOfPage(this);
        Assert.checkTrue(index>=0);

        int left = PAGE_SPACE / 2; //+ this.document.getPageFormat().getLeftMargin();
        int top = height() * index + PAGE_SPACE / 2; //+ this.document.getPageFormat().getTopMargin();

        int width = this.width() - PAGE_SPACE;
        int height = this.height() - PAGE_SPACE;
        if( (left<=x) && x<(left+width) && (top<=y) && y<(top+height) )
            return true;
        return false;
    }

}
