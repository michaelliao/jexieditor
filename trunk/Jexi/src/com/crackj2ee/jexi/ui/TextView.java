/*
 * Created on 2004-7-23
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui;

import com.crackj2ee.jexi.core.*;

/**
 * The display of the Jexi editing region. 
 * 
 * @author Xuefeng
 */
public abstract class TextView implements View{

    // store the document:
    protected Document document;

    /**
     * Get the document. 
     * 
     * @see com.crackj2ee.jexi.ui.View#getDocument()
     */
    public Document getDocument() {
        return this.document;
    }

    /**
     * Set the document. 
     * 
     * @see com.crackj2ee.jexi.ui.View#setDocument(com.crackj2ee.jexi.core.Document)
     */
    public void setDocument(Document document) {
        this.document = document;
        onDocumentSizeChanged();
    }

}
