/*
 * Created on 2004-8-4
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.command;

import com.crackj2ee.jexi.core.*;

/**
 * User typed a new character. 
 * 
 * @author Xuefeng
 */
public class InsertCommand implements Command {

    private Char c;

    // store the document reference:
    private Document document;

    // store the caret position:
    private Position position;

    protected InsertCommand(Document document, char c) {
        this.document = document;
        this.c = CharFactory.instance().createChar(c);
        this.position = document.getCaret().getPosition();
    }

    /* (non-Javadoc)
     * @see jexi.core.command.Command#execute()
     */
    public boolean execute() {
        // check if the selection should be removed:
        if(document.getSelection().isSelected())
            CommandManager.instance().newDeleteCommand(document);

        // insert the character:
        Caret caret = this.document.getCaret();
        caret.getPargraph().add(caret.getInsertIndex(), this.c);
        // store the position:
        AbsPosition caretAbsPos = new AbsPosition(document, caret.getPosition());
        // then compose:
        this.document.compose();

        // move the caret:
        caret.moveTo(new Position(document, caretAbsPos));
        caret.moveRight();

        System.out.println("Move next:");
        Position p = caret.getPosition();
        if(p.getColumnIndex()==0) // if just move to the next row:
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
        return "Type " + c;
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
