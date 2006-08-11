/*
 * Created on 2004-7-20
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import com.crackj2ee.jexi.ui.*;

/**
 * ParagraphCompositor can format a Paragraph object when needed. 
 */
public final class ParagraphCompositor implements Compositor {

    private static char[] BREAK_CHAR = {' ', ',', '.', ';', ':', '\r', '\t', '?', '!'};

    private Paragraph paragraph = null;

    // if it can break after the glyph 'g':
    private boolean isBreakGlyph(Glyph g) {
        if(g instanceof Char) {
            char c = ((Char)g).charValue();
            for(int i=0; i<BREAK_CHAR.length; i++) {
                if(BREAK_CHAR[i]==c)
                    return true;
            }
            return false;
        }
        return true;
    }

    /**
     * To format the paragraph from start to end completely. 
     * This is always used in the first time the paragraph is loaded, 
     * or created by pasting. 
     */
    public void compose() {
        paragraph.clearAllRows();

        Graphics g = Application.instance().getFrame().getDefaultGraphics();

        int startIndex = 0;
        int endIndex;
        boolean first = true;
        do {
            endIndex = clip(startIndex, first, g);
            first = false;
            paragraph.appendRow(new Row(paragraph, startIndex, endIndex));
            startIndex = endIndex+1;
        } while(endIndex!=paragraph.getGlyphsCount()-1);

        paragraph.setFormatted(true);
    }

    // clip the new row, from glyph index,
    // return the last index of the glyph.
    private int clip(int startIndex, boolean firstRow, Graphics g) {
        int glyph_width = 0;
        // get the row width:
        int row_width = paragraph.getParagraphFormat().scaleWidth(firstRow);
        int acc_width = 0;

        for(int i=startIndex; i<paragraph.getGlyphsCount(); i++) {
            Glyph glyph = (Glyph)paragraph.child(i);
            if(glyph instanceof Char) {
                g.setFont(paragraph.getStringFormat(i).getFont());
                glyph_width = g.getCharWidth(((Char)glyph).charValue());
            }
            else
                glyph_width = glyph.width();
            acc_width += glyph_width;
            if(acc_width>row_width) {
                int endIndex = i-1;
                if(endIndex<startIndex) // if a glyph's width is larger than row's width
                    endIndex++;
                // ok, now test the break:
                int breakAt = endIndex;
                // search the nearest break glyph:
                while(breakAt>=startIndex) {
                    if(isBreakGlyph((Glyph)paragraph.child(breakAt)))
                        break;
                    breakAt--;
                }
                if(breakAt>=startIndex)
                    return breakAt;
                return endIndex;
            }
        }
        return paragraph.getGlyphsCount()-1;
    }

    /**
     * Set which paragraph to be formatted.
     * 
     * @param composition The composition to be formatted, must be "Paragraph".
     */
    public void setComposition(Composition composition) {
        Assert.checkNull(composition);
        Assert.checkTrue(composition instanceof Paragraph);
        this.paragraph = (Paragraph)composition;
    }

}
