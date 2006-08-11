/*
 * Created on 2004-8-5
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.command;

import java.util.*;

import com.crackj2ee.jexi.core.*;


/**
 * Delete a glyph. 
 * 
 * @author Xuefeng
 */
public final class DeleteCommand implements Command {

    private Document document;
    private Position position;

    /**
     * Create a new delete command. 
     * 
     * @param document The document object.
     */
    public DeleteCommand(Document document) {
        this.document = document;
        this.position = document.getCaret().getPosition();
    }

    /* (non-Javadoc)
     * @see jexi.core.command.Command#execute()
     */
    public boolean execute() {
        Selection sel = document.getSelection();
        if(sel.isSelected()) {
            // should delete the selection:
            if(position.getColumnIndex()==0)
                this.document.getCaret().moveLeft(); // locate the caret.
            document.getCaret().moveTo(sel.getStartPosition());

            // find out all paragraphs that is in the selection:
            Paragraph pFirst = sel.getFirstSelectedParagraph();
            Paragraph pLast = sel.getLastSelectedParagraph();
            boolean bFirst = true;
            boolean bLast = false;
            int firstIndex = sel.getFirstSelectedIndex();
            int lastIndex = sel.getLastSelectedIndex();
            Paragraph p = pFirst;
            List deleted = new ArrayList(5);
            boolean needCombine = false;
            do {
                if(p==pLast) bLast = true;

                if(!bFirst && !bLast) {
                    // should delete the whole paragraph:
                    deleted.add(p);
                }
                else if(bFirst && bLast) {
                    // only one paragraph:
                    p.removeGlyphs(firstIndex, lastIndex-1);
                }
                else if(bFirst && !bLast) {
                    // first paragraph:
                    if(firstIndex==0) // detect if the first paragraph should be removed
                        deleted.add(p);
                    else {
                        needCombine = true;
                        p.removeGlyphs(firstIndex, p.getGlyphsCount()-2);
                    }
                }
                else if (!bFirst && bLast) {
                    // last paragraph:
                    p.removeGlyphs(0, lastIndex-1);
                }

                // point to the next paragraph:
                p = document.nextParagraph(p);
                bFirst = false;
            }while(!bLast);
            // delete paragraphs:
            Iterator it=deleted.iterator();
            while(it.hasNext())
                this.document.removeParagraph((Paragraph)it.next());
            if(needCombine) {
                Paragraph next = document.nextParagraph(pFirst);
                pFirst.combine(next);
                document.removeParagraph(next);
            }

            // then compose:
            this.document.getSelection().unselect();
            this.document.compose();
            document.updateCaret();
            return true;
        }

        // only delete the current glyph:
        Caret caret = this.document.getCaret();
        AbsPosition caretAbsPos = new AbsPosition(document, caret.getPosition()); 
        // first find out which paragraph:
        Row row = caret.getRow();
        if(!row.isFirst() && caret.getPosition().getColumnIndex()==0)
            caret.moveLeft();
        Paragraph p = row.getParagraph();

        int index = caret.getInsertIndex();
        if(index==p.getGlyphsCount()-1) {
            // cannot delete '\r'
            // but if there is the following paragraph,
            // should combine the two:
            Paragraph next = document.nextParagraph(p);
            if(next==null) {
                System.out.println("No next paragraph.");
            }
            else {
                System.out.println("Found the next paragraph.");
                // we should put these to a new CombineCommand to support 'undo',
                // but we want to simplify the code:
                p.combine(next);
                document.removeParagraph(next);
                p.debug();
                document.compose();
                document.updateView();
            }
            // but still return false:
            return false;
        }
        p.remove(index);
        // then compose:
        this.document.compose();
        caret.moveTo(new Position(document, caretAbsPos));

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
        return "Delete.";
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
