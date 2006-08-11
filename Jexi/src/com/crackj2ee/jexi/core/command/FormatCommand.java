/*
 * Created on 2004-8-8
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.command;

import com.crackj2ee.jexi.core.*;
import com.crackj2ee.jexi.ui.*;


/**
 * Format the selected text. 
 * 
 * @author Xuefeng
 */
public class FormatCommand implements Command {

    // store the document reference:
    private Document document;

    // store the selection:
    private Selection selection = null;

    // the format:
    private String fontName;
    private Integer fontSize;
    private Boolean bold;
    private Boolean italic;
    private Boolean underlined;
    private Color color;

    /**
     * Create a new FormatCommand. 
     * 
     * @param doc The document object.
     * @param fontName The font name, or null if ignore.
     * @param fontSize The font size, or null if ignore.
     * @param bold The bold attribute, or null if ignore.
     * @param italic The italic attribute, or null if ignore.
     * @param underlined The underlined attribute, or null if ignore.
     * @param color The color, or null if ignore.
     */
    protected FormatCommand(Document document, String fontName, Integer fontSize, Boolean bold, Boolean italic, Boolean underlined, Color color) {
        this.document = document;
        this.selection = document.getSelection();
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.color = color;
    }

    /* (non-Javadoc)
     * @see jexi.core.command.Command#execute()
     */
    public boolean execute() {
        if(selection==null) return false;
        if(!selection.isSelected()) return false;

        // store the end position:
        AbsPosition endAbsPos = new AbsPosition(document, selection.getEndPosition());

        // find out all paragraphs that is in the selection:
        Paragraph pFirst = selection.getFirstSelectedParagraph();
        Paragraph pLast = selection.getLastSelectedParagraph();
        boolean bFirst = true;
        boolean bLast = false;
        int firstIndex = selection.getFirstSelectedIndex();
        int lastIndex = selection.getLastSelectedIndex();
        Paragraph p = pFirst;
        do {
            if(p==pLast) bLast = true;
            // ok, for this paragraph, we changed the format of its selected text:
            int start = bFirst?firstIndex:0;
            int end = bLast?lastIndex-1:p.getGlyphsCount()-1;
            if(end>=0)
                p.format(start, end, fontName, fontSize, bold, italic, underlined, color);
            // point to the next paragraph:
            p = document.nextParagraph(p);
            bFirst = false;
        }while(!bLast);
        document.compose();

        // when composed, need adjust the selection:
        selection.select(selection.getStartPosition(), new Position(document, endAbsPos));

        document.updateView();
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
        return "Format the selected text.";
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
