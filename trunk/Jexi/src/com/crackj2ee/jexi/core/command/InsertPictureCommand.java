/*
 * Created on 2004-8-14
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.command;

import com.crackj2ee.jexi.core.*;

/**
 * Insert a picture. 
 * 
 * @author Xuefeng
 */
public class InsertPictureCommand implements Command {

    // store the document reference:
    private Document document;

    // store the filename:
    private String filename;
    // store the caret position:
    private Position position;

    protected InsertPictureCommand(Document document, String filename) {
        this.document = document;
        this.filename = filename;
        this.position = document.getCaret().getPosition();
    }

    /* (non-Javadoc)
     * @see jexi.core.command.Command#execute()
     */
    public boolean execute() {
        Picture pic = null;
        try {
            pic = PictureFactory.instance().createPicture(filename);
        }
        catch(java.io.IOException e) {
            // TODO: MessageBox...
            System.out.println("Load picture failed, filename=" + filename);
            return false;
        }

        // check if the selection should be removed:
        if(document.getSelection().isSelected())
            CommandManager.instance().newDeleteCommand(document);
        // insert the picture:
        Caret caret = this.document.getCaret();
        caret.getPargraph().add(caret.getInsertIndex(), pic);
        // then compose:
        this.document.compose();

        // move the caret:
        this.document.getCaret().moveRight();
        // notify view:
        this.document.updateCaret();
        return true;
    }

    /* (non-Javadoc)
     * @see jexi.core.command.Command#unexecute()
     */
    public void unexecute() {
        // TODO Auto-generated method stub
    }

    /**
     * To get the description of this command.
     */
    public String toString() {
        return "Insert picture.";
    }

    /**
     * This command can support undo or not. 
     * 
     * @return True if this command supports undo.
     */
    public boolean canUndo() {
        return true;
    }
}
