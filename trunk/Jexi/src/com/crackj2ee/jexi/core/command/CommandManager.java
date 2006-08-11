/*
 * Created on 2004-8-4
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core.command;

import java.util.*;

import com.crackj2ee.jexi.core.*;
import com.crackj2ee.jexi.ui.*;


/**
 * Command manager manage all commands that can undo and redo. 
 * 
 * @author Xuefeng
 */
public final class CommandManager {

    private static CommandManager instance = new CommandManager();

    // store the command list:
    private List commands = new ArrayList();
    // store the position of the current command (just executed):
    private int current = (-1);

    // prevent the client to create instance:
    private CommandManager() {}

    /**
     * Get the only instance of the CommandManager. 
     * 
     * @return The instance of the CommandManager.
     */
    public static CommandManager instance() { return instance; }

    // add the command that just executed:
    private void addToCommandList(Command cmd) {
        Assert.checkTrue(cmd.canUndo()); // must support undo.

        commands.add(cmd);
        current++;
        System.out.println("added a new command: "+cmd.toString());
    }

    /**
     * Undo the last command. 
     */
    public void undo() {
        Assert.checkTrue(canUndo());
        // TODO: undo the last command:
    }

    /**
     * Can redo the last undo command? 
     * 
     * @return True if can redo.
     */
    public boolean canRedo() {
        return current<commands.size()-1;
    }

    /**
     * Redo the last cancelled command. 
     */
    public void redo() {
        Assert.checkTrue(canRedo());
        // TODO: redo the last cancelled command:
    }

    /**
     * Can undo the last command? 
     * 
     * @return True if can undo.
     */
    public boolean canUndo() {
        return current>=0;
    }

    /**
     * Create a new insert command and execute it. 
     * 
     * @param doc The document object.
     * @param c The char of the key.
     */
    public void newInsertCommand(Document doc, char c) {
        Command cmd = new InsertCommand(doc, c);
        if(cmd.execute() && cmd.canUndo()) {
            addToCommandList(cmd);
        }
    }

    /**
     * Create a new insert picture command and execute it. 
     * 
     * @param doc The document object.
     * @param filename The picture file name.
     */
    public void newInsertPictureCommand(Document doc, String filename) {
        Command cmd = new InsertPictureCommand(doc, filename);
        if(cmd.execute() && cmd.canUndo()) {
            addToCommandList(cmd);
        }
    }

    /**
     * Create a new format command and execute it. 
     * 
     * @param doc The document object.
     * @param fontName The font name, or null if ignore.
     * @param fontSize The font size, or null if ignore.
     * @param bold The bold attribute, or null if ignore.
     * @param italic The italic attribute, or null if ignore.
     * @param underlined The underlined attribute, or null if ignore.
     * @param color The color, or null if ignore.
     */
    public void newFormatCommand(Document doc, String fontName, Integer fontSize, Boolean bold, Boolean italic, Boolean underlined, Color color) {
        Command cmd = new FormatCommand(doc, fontName, fontSize, bold, italic, underlined, color);
        if(cmd.execute() && cmd.canUndo()) {
            addToCommandList(cmd);
        }
    }

    /**
     * Create a new delete command and execute it. 
     * 
     * @param doc The document object.
     */
    public void newDeleteCommand(Document doc) {
        Command cmd = new DeleteCommand(doc);
        if(cmd.execute() && cmd.canUndo()) {
            addToCommandList(cmd);
        }
    }

    /**
     * Create a new break command and execute it. 
     * 
     * @param doc The document object.
     */
    public void newSplitCommand(Document doc) {
        Command cmd = new SplitCommand(doc);
        if(cmd.execute() && cmd.canUndo()) {
            addToCommandList(cmd);
        }
    }
}
