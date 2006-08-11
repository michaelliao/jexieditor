/*
 * Created on 2004-7-31
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import java.util.*;

/**
 * Document compositor will compose all paragraphs and pages. 
 * 
 * @author Xuefeng
 */
public final class DocumentCompositor implements Compositor {

    private Document document = null;

    /* (non-Javadoc)
     * @see jexi.core.Compositor#compose()
     */
    public void compose() {
        int org_pages = document.getPageCount();

        // first clear all pages:
        document.clearAllPages();

        // create a Page object:
        Page page = new Page(document);

        // then compose each Paragraph if needed:
        Compositor pCompositor = new ParagraphCompositor();
        Iterator it = document.getParagraphs().iterator();
        while(it.hasNext()) {
            Paragraph p = (Paragraph)it.next();
            if(!p.getFormatted()) {
                pCompositor.setComposition(p);
                pCompositor.compose();
            }
            // now we get the formatted paragraph:
            Iterator row_it = p.getRows().iterator();
            while(row_it.hasNext()) {
                Row row = (Row)row_it.next();
                if(page.scaleHeight() - page.rowsHeight()>=row.height()) {
                    // ok, this page can contain this row:
                    page.addRow(row);
                }
                else {
                    // this page cannot contain row any more, 
                    // so we put it to the document:
                    document.addPage(page);
                    page.debug();
                    // and create a new page to accepte the row:
                    page = new Page(document);
                    page.addRow(row);
                }
            }
        }
        if(document.getPageCount()==0)
            document.addPage(page);
        else
            if(document.lastPage()!=page) document.addPage(page);

        if(document.getPageCount()!=org_pages) {
            document.getView().onDocumentSizeChanged();
            document.updateCaret();
        }
    }

    /* (non-Javadoc)
     * @see jexi.core.Compositor#setComposition(jexi.core.Composition)
     */
    public void setComposition(Composition composition) {
        Assert.checkTrue(composition instanceof Document);

        this.document = (Document)composition;
    }
}
