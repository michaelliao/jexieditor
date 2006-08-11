/*
 * Created on 2004-8-9
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.command;

import com.crackj2ee.jexi.core.*;

/**
 * When user press ENTER, a SplitCommand will break the current 
 * paragraph into 2 parts. 
 * 
 * @author Xuefeng
 */
public class SplitCommand implements Command {

    private Document document;
    // the caret position:
    private Position position;

    /**
     * Create a break command. 
     * 
     * @param document The document.
     */
    public SplitCommand(Document document) {
        this.document = document;
        this.position = document.getCaret().getPosition();
    }

    /* (non-Javadoc)
     * @see jexi.core.command.Command#execute()
     */
    public boolean execute() {
        Paragraph p = document.getCaret().getPargraph();
        Paragraph p2 = p.split(document.getCaret().getInsertIndex());
        document.addParagraph(document.getParagraphIndex(p)+1, p2);
        p.debug();
        p2.debug();

        // then compose:
        this.document.compose();
        document.getCaret().moveRight();
        document.updateCaret();

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
        return "Typed ENTER.";
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
