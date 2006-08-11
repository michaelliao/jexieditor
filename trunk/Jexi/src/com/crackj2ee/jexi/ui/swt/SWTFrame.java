/*
 * Created on 2004-7-24
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui.swt;

import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.program.*;

/**
 * The implementation of Frame. And this is the real window 
 * displayed on the screen! <br>
 * <b>NOTE</b>: The UI was created by ui-design-tool, the swt-designer, 
 * (see: <a href="www.swt-designer.com">www.swt-designer.com</a>), 
 * so modify carefully! 
 * 
 * @author Xuefeng
 */
public class SWTFrame implements com.crackj2ee.jexi.ui.Frame {

    // store the view:
    private com.crackj2ee.jexi.ui.View view = null;

    // the device:
    private Display display = null;
    // the main window:
    private Shell shell = null;

    // the default graphics:
    private SWTGraphics defaultGraphics = null;

    private ToolBar toolBarCommon;
    private ToolBar toolBarFormat;

    private Combo cmbFontName;
    private Combo cmbFontSize;

    private Composite ScrollableView;
    private Canvas textView;

    private Slider sliderV;
    private Slider sliderH;

    // caret:
    private Caret caret;

    // color-select menu:
    private Menu mnuColorSelect;
    private MenuItem[] mnuColor = new MenuItem[16];

    // image factory:
    private SWTImageFactory imageFactory;

    /**
     * Get the display object. 
     * 
     * @return The display object.
     */
    public Display getDisplay() {
        return this.display;
    }

    /**
     * Get the shell object.
     * 
     * @return The shell object.
     */
    public Shell getShell() {
        return this.shell;
    }

    /**
     * Initialize the frame. 
     * 
     * @see com.crackj2ee.jexi.ui.Frame#init()
     */
    public void init(){
        // create device:
        this.display = new Display();
        this.imageFactory = new SWTImageFactory(this.display);
        // create window:
        this.shell = new Shell(display);
        this.shell.setText("Jexi 1.0 beta");

        // create default graphics:
        org.eclipse.swt.graphics.GC gc = new org.eclipse.swt.graphics.GC(shell);
        this.defaultGraphics = new com.crackj2ee.jexi.ui.swt.SWTGraphics(gc);

        // now layout:
        final GridLayout gridLayoutForShell = new GridLayout();
        gridLayoutForShell.verticalSpacing = 1;
        gridLayoutForShell.marginWidth = 0;
        gridLayoutForShell.marginHeight = 0;
        gridLayoutForShell.makeColumnsEqualWidth = true;
        shell.setLayout(gridLayoutForShell);

        // create menu bar:
        createMenu();

        // create pop-up menu of "select color":
        createColorSelectMenu();

        // create tool bar - Common:
        createToolBarCommon();

        // create tool bar - Format:
        createToolBarFormat();

        // init fonts:
        initFont();

        // create "view":
        createComposite();

        ///////////////////////////////////////////////////////////////////////
        // ok, create the view:
        //this.view = new SWTTextView(this.textView);
        this.view = new SWTScrollableViewDecorator(new SWTTextView(this.textView),
            this.sliderH, this.sliderV);
        com.crackj2ee.jexi.core.Document document = com.crackj2ee.jexi.core.Document.createEmptyDocument(view);
        view.init(document);
        //this.document.updateCaret();
        ///////////////////////////////////////////////////////////////////////

        // notify view when size changed:
        textView.addControlListener(new ControlAdapter () {
            public void controlResized(ControlEvent e) {
                // NOTE: the size is textView(Canvas):
                org.eclipse.swt.graphics.Rectangle r = textView.getBounds();
                view.onSizeChanged(r.width, r.height);
            }
        });

        // notify view when need repaint:
        textView.addPaintListener(new PaintListener () {
            public void paintControl(PaintEvent e) {
                view.update();
            }
        });

        // notify view when mouse moved:
        textView.addMouseMoveListener(new MouseMoveListener() {
            public void mouseMove(MouseEvent e) {
                view.onMouseMove(e.x, e.y);
            }
        });

        // nofity view when mouse clicked:
        textView.addMouseListener(new MouseListener() {
            public void mouseDown(MouseEvent e) {
                if(e.button==1)
                    view.onLButtonDown(e.x, e.y);
                if(e.button==3)
                    view.onRButtonDown(e.x, e.y);
            }
            public void mouseUp(MouseEvent e) {
                if(e.button==1)
                    view.onLButtonUp(e.x, e.y);
                if(e.button==3)
                    view.onRButtonUp(e.x, e.y);
            }
            public void mouseDoubleClick(MouseEvent e) {
                if(e.button==1)
                    view.onLButtonDblClick(e.x, e.y);
            }
        });

        // nofity view when key pressed:
        textView.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                System.out.println(e.toString());
                boolean shift = (e.stateMask & SWT.SHIFT) == SWT.SHIFT;
                boolean ctrl = (e.stateMask & SWT.CTRL) == SWT.CTRL;
                boolean alt = (e.stateMask & SWT.ALT) == SWT.ALT;
                if(e.character!='\0') {
                    if(e.character==8 || e.character==13 || e.character==127)
                        view.onFunctionKeyPressed(e.character, shift, ctrl, alt);
                    else if(!ctrl && !alt)
                        view.onKeyPressed(e.character);
                }
                else
                    view.onFunctionKeyPressed(e.keyCode, shift, ctrl, alt);
            }
            public void keyReleased(KeyEvent e) {
                //System.out.println(e.toString());
            }
        });

        // nofity view when select font, size and color:
        cmbFontName.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                System.out.println(e.toString());
                view.onFormatChanged(cmbFontName.getText(),
                    null, null, null, null, null
                );
            }
        });
        cmbFontSize.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                System.out.println(e.toString());
                view.onFormatChanged(null,
                    Integer.valueOf(cmbFontSize.getText()),
                    null, null, null, null
                );
            }
        });

        for(int i=0; i<16; i++) {
            mnuColor[i].addSelectionListener(new SelectColorHandler(view, i));
        }
    }


    /**
     * Ready to show the window and run message loop. 
     * 
     * @see com.crackj2ee.jexi.ui.Frame#show()
     */
    public void show() {
        shell.pack();
        shell.open();
        this.textView.setFocus();

        while(!shell.isDisposed())
            if(!display.readAndDispatch())
                display.sleep();
    }

    /**
     * Clean up. 
     * 
     * @see com.crackj2ee.jexi.ui.Frame#dispose()
     */
    public void dispose() {
        // dispose fonts & colors:
        com.crackj2ee.jexi.ui.FontFactory.instance().clearAllFonts();
        com.crackj2ee.jexi.ui.ColorFactory.instance().clearAllColors();

        // dispose the display:
        this.display.dispose();
    }

    // create menu bar:
    private void createMenu() {
        Menu menubar = new Menu(shell, SWT.BAR);

        // File menu:
        final MenuItem mnuFile = new MenuItem(menubar, SWT.CASCADE);
        mnuFile.setText("&File");

        Menu popupmenu = new Menu(mnuFile);
        mnuFile.setMenu(popupmenu);

        final MenuItem mnuFileNew = new MenuItem(popupmenu, SWT.NONE);
        mnuFileNew.setEnabled(false);
        mnuFileNew.setText("&New");

        final MenuItem mnuFileOpen = new MenuItem(popupmenu, SWT.NONE);
        mnuFileOpen.setEnabled(false);
        mnuFileOpen.setText("&Open...\tCtrl+O");

        final MenuItem mnuFileClose = new MenuItem(popupmenu, SWT.NONE);
        mnuFileClose.setEnabled(false);
        mnuFileClose.setText("&Close");

        new MenuItem(popupmenu, SWT.SEPARATOR);

        final MenuItem mnuFileSave = new MenuItem(popupmenu, SWT.NONE);
        mnuFileSave.setEnabled(false);
        mnuFileSave.setText("&Save\tCtrl+S");

        final MenuItem mnuFileSaveAs = new MenuItem(popupmenu, SWT.NONE);
        mnuFileSaveAs.setEnabled(false);
        mnuFileSaveAs.setText("Save &As...");

        new MenuItem(popupmenu, SWT.SEPARATOR);

        final MenuItem mnuFilePageSetup = new MenuItem(popupmenu, SWT.NONE);
        mnuFilePageSetup.setEnabled(false);
        mnuFilePageSetup.setText("Page Set&up...");

        final MenuItem mnuFilePrintPreview = new MenuItem(popupmenu, SWT.NONE);
        mnuFilePrintPreview.setEnabled(false);
        mnuFilePrintPreview.setText("Print Pre&view");

        final MenuItem mnuFilePrint = new MenuItem(popupmenu, SWT.NONE);
        mnuFilePrint.setEnabled(false);
        mnuFilePrint.setText("&Print...\tCtrl+P");

        new MenuItem(popupmenu, SWT.SEPARATOR);

        final MenuItem mnuFileProperties = new MenuItem(popupmenu, SWT.NONE);
        mnuFileProperties.setEnabled(false);
        mnuFileProperties.setText("Propert&ies...");

        new MenuItem(popupmenu, SWT.SEPARATOR);

        final MenuItem mnuFileRecent0 = new MenuItem(popupmenu, SWT.NONE);
        mnuFileRecent0.setEnabled(false);
        mnuFileRecent0.setText("(Recent File)");

        final MenuItem mnuFileRecent1 = new MenuItem(popupmenu, SWT.NONE);
        mnuFileRecent1.setEnabled(false);
        mnuFileRecent1.setText("(Recent File)");

        final MenuItem mnuFileRecent2 = new MenuItem(popupmenu, SWT.NONE);
        mnuFileRecent2.setEnabled(false);
        mnuFileRecent2.setText("(Recent File)");

        final MenuItem mnuFileRecent3 = new MenuItem(popupmenu, SWT.NONE);
        mnuFileRecent3.setEnabled(false);
        mnuFileRecent3.setText("(Recent File)");

        final MenuItem mnuFileRecent4 = new MenuItem(popupmenu, SWT.NONE);
        mnuFileRecent4.setEnabled(false);
        mnuFileRecent4.setText("(Recent File)");

        new MenuItem(popupmenu, SWT.SEPARATOR);

        final MenuItem mnuFileExit = new MenuItem(popupmenu, SWT.NONE);
        mnuFileExit.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                shell.close();
            }
        });
        mnuFileExit.setText("E&xit\tAlt+F4");

        // Edit menu:
        final MenuItem mnuEdit = new MenuItem(menubar, SWT.CASCADE);
        mnuEdit.setText("&Edit");

        Menu popupmenu_1 = new Menu(mnuEdit);
        mnuEdit.setMenu(popupmenu_1);

        final MenuItem mnuEditUndo = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditUndo.setEnabled(false);
        mnuEditUndo.setText("&Undo\tCtrl+Z");

        final MenuItem mnuEditRedo = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditRedo.setEnabled(false);
        mnuEditRedo.setText("&Redo\tCtrl+Y");

        new MenuItem(popupmenu_1, SWT.SEPARATOR);

        final MenuItem mnuEditCut = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditCut.setEnabled(false);
        mnuEditCut.setText("Cu&t\tCtrl+X");

        final MenuItem mnuEditCopy = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditCopy.setEnabled(false);
        mnuEditCopy.setText("&Copy\tCtrl+C");

        final MenuItem mnuEditPaste = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditPaste.setEnabled(false);
        mnuEditPaste.setText("&Paste\tCtrl+V");

        new MenuItem(popupmenu_1, SWT.SEPARATOR);

        final MenuItem mnuEditDelete = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditDelete.setEnabled(false);
        mnuEditDelete.setText("&Delete");

        new MenuItem(popupmenu_1, SWT.SEPARATOR);

        final MenuItem mnuEditSelectAll = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditSelectAll.setEnabled(false);
        mnuEditSelectAll.setText("Select &All\tCtrl+A");

        new MenuItem(popupmenu_1, SWT.SEPARATOR);

        final MenuItem mnuEditFind = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditFind.setEnabled(false);
        mnuEditFind.setText("&Find...\tCtrl+F");

        final MenuItem mnuEditReplace = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditReplace.setEnabled(false);
        mnuEditReplace.setText("&Replace...\tCtrl+H");

        final MenuItem mnuEditGoto = new MenuItem(popupmenu_1, SWT.NONE);
        mnuEditGoto.setEnabled(false);
        mnuEditGoto.setText("&Goto...\tCtrl+G");

        // View menu:
        final MenuItem mnuView = new MenuItem(menubar, SWT.CASCADE);
        mnuView.setText("&View");

        Menu popupmenu_2 = new Menu(mnuView);
        mnuView.setMenu(popupmenu_2);

        final MenuItem mnuViewToolBar = new MenuItem(popupmenu_2, SWT.CASCADE);
        mnuViewToolBar.setText("&Tool Bar");

        Menu popupmenu_5 = new Menu(mnuViewToolBar);
        mnuViewToolBar.setMenu(popupmenu_5);

        final MenuItem mnuViewToolBarCommon = new MenuItem(popupmenu_5, SWT.CHECK);
        mnuViewToolBarCommon.setEnabled(false);
        mnuViewToolBarCommon.setSelection(true);
        mnuViewToolBarCommon.setText("&Common");

        final MenuItem mnuViewToolBarFormat = new MenuItem(popupmenu_5, SWT.CHECK);
        mnuViewToolBarFormat.setEnabled(false);
        mnuViewToolBarFormat.setSelection(true);
        mnuViewToolBarFormat.setText("&Format");

        final MenuItem mnuViewStatusBar = new MenuItem(popupmenu_2, SWT.CHECK);
        mnuViewStatusBar.setEnabled(false);
        mnuViewStatusBar.setText("&Status Bar");

        new MenuItem(popupmenu_2, SWT.SEPARATOR);

        final MenuItem mnuViewRuler = new MenuItem(popupmenu_2, SWT.CHECK);
        mnuViewRuler.setEnabled(false);
        mnuViewRuler.setText("&Ruler");

        final MenuItem mnuViewParagraphTag = new MenuItem(popupmenu_2, SWT.CHECK);
        mnuViewParagraphTag.setEnabled(false);
        mnuViewParagraphTag.setText("Paragraph &Tag");

        // Insert menu:
        final MenuItem mnuInsert = new MenuItem(menubar, SWT.CASCADE);
        mnuInsert.setText("&Insert");

        Menu popupmenu_3 = new Menu(mnuInsert);
        mnuInsert.setMenu(popupmenu_3);

        final MenuItem mnuInsertDateAndTime = new MenuItem(popupmenu_3, SWT.NONE);
        mnuInsertDateAndTime.setEnabled(false);
        mnuInsertDateAndTime.setText("&Date And Time...");

        final MenuItem mnuInsertSymbol = new MenuItem(popupmenu_3, SWT.NONE);
        mnuInsertSymbol.setEnabled(false);
        mnuInsertSymbol.setText("&Symbol...");

        new MenuItem(popupmenu_3, SWT.SEPARATOR);

        final MenuItem mnuInsertPicture = new MenuItem(popupmenu_3, SWT.CASCADE);
        mnuInsertPicture.setText("&Picture");

        Menu popupmenu_6 = new Menu(mnuInsertPicture);
        mnuInsertPicture.setMenu(popupmenu_6);

        final MenuItem mnuInsertPictureFromFile = new MenuItem(popupmenu_6, SWT.NONE);
        mnuInsertPictureFromFile.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                FileDialog dialog = new FileDialog (shell, SWT.OPEN);
            	dialog.setFilterNames (new String [] {"Picture Files", "All Files (*.*)"});
            	dialog.setFilterExtensions (new String [] {"*.bmp", "*.*"});
            	String filename = dialog.open();
            	if(filename!=null)
            	    view.onInsertPictureFromFile(filename);
            }
        });
        mnuInsertPictureFromFile.setText("From &File...");

        final MenuItem mnuInsertPictureBox = new MenuItem(popupmenu_6, SWT.NONE);
        mnuInsertPictureBox.setEnabled(false);
        mnuInsertPictureBox.setText("Picture &Box");

        final MenuItem mnuInsertTextBox = new MenuItem(popupmenu_3, SWT.NONE);
        mnuInsertTextBox.setEnabled(false);
        mnuInsertTextBox.setText("&Text Box");

        final MenuItem mnuInsertHyperlink = new MenuItem(popupmenu_3, SWT.NONE);
        mnuInsertHyperlink.setEnabled(false);
        mnuInsertHyperlink.setText("&Hyperlink");

        // Format menu:
        final MenuItem mnuFormat = new MenuItem(menubar, SWT.CASCADE);
        mnuFormat.setText("&Format");

        Menu popupmenu_4 = new Menu(mnuFormat);
        mnuFormat.setMenu(popupmenu_4);

        final MenuItem mnuFormatFont = new MenuItem(popupmenu_4, SWT.NONE);
        mnuFormatFont.setEnabled(false);
        mnuFormatFont.setText("&Font...");

        final MenuItem mnuFormatParagraph = new MenuItem(popupmenu_4, SWT.NONE);
        mnuFormatParagraph.setEnabled(false);
        mnuFormatParagraph.setText("&Paragraph...");

        new MenuItem(popupmenu_4, SWT.SEPARATOR);

        final MenuItem mnuFormatBackColor = new MenuItem(popupmenu_4, SWT.NONE);
        mnuFormatBackColor.setEnabled(false);
        mnuFormatBackColor.setText("&Back Color...");

        final MenuItem mnuFormatForeColor = new MenuItem(popupmenu_4, SWT.NONE);
        mnuFormatForeColor.setEnabled(false);
        mnuFormatForeColor.setText("Fore &Color...");

        // Tools menu:
        final MenuItem mnuTools = new MenuItem(menubar, SWT.CASCADE);
        mnuTools.setText("&Tools");

        Menu popupmenu_7 = new Menu(mnuTools);
        mnuTools.setMenu(popupmenu_7);

        final MenuItem mnuToolsSpellingCheck = new MenuItem(popupmenu_7, SWT.NONE);
        mnuToolsSpellingCheck.setEnabled(false);
        mnuToolsSpellingCheck.setText("&Spelling Check");

        final MenuItem mnuToolsWordsCount = new MenuItem(popupmenu_7, SWT.NONE);
        mnuToolsWordsCount.setEnabled(false);
        mnuToolsWordsCount.setText("&Words Count...");

        final MenuItem mnuToolsProtectDocument = new MenuItem(popupmenu_7, SWT.NONE);
        mnuToolsProtectDocument.setEnabled(false);
        mnuToolsProtectDocument.setText("&Protect Document...");

        new MenuItem(popupmenu_7, SWT.SEPARATOR);

        final MenuItem mnuToolsOptions = new MenuItem(popupmenu_7, SWT.NONE);
        mnuToolsOptions.setEnabled(false);
        mnuToolsOptions.setText("&Options...");

        // Help menu:
        final MenuItem mnuHelp = new MenuItem(menubar, SWT.CASCADE);
        mnuHelp.setText("&Help");

        Menu popupmenu_8 = new Menu(mnuHelp);
        mnuHelp.setMenu(popupmenu_8);

        final MenuItem mnuHelpJexiHelp = new MenuItem(popupmenu_8, SWT.NONE);
        mnuHelpJexiHelp.setEnabled(false);
        mnuHelpJexiHelp.setText("Jexi &Help\tF1");

        final MenuItem mnuHelpJexiOnline = new MenuItem(popupmenu_8, SWT.NONE);
        mnuHelpJexiOnline.setText("Jexi &Online...");
        mnuHelpJexiOnline.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Program p = Program.findProgram (".htm");
                if (p != null) p.execute ("http://xuefengl.nease.net/jexi.htm");
            }
        });

        new MenuItem(popupmenu_8, SWT.SEPARATOR);

        final MenuItem mnuHelpContactAuthor = new MenuItem(popupmenu_8, SWT.NONE);
        mnuHelpContactAuthor.setText("&Contact Author...");

        final MenuItem mnuHelpCheckUpdate = new MenuItem(popupmenu_8, SWT.NONE);
        mnuHelpCheckUpdate.setEnabled(false);
        mnuHelpCheckUpdate.setText("Check &Update...");

        new MenuItem(popupmenu_8, SWT.SEPARATOR);

        final MenuItem mnuHelpAbout = new MenuItem(popupmenu_8, SWT.NONE);
        mnuHelpAbout.setText("&About Jexi...");
        mnuHelpAbout.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Shell dialog = new Shell (shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
                dialog.setText ("About Jexi");
                dialog.setSize (200, 200);
                dialog.open ();
            }
        });

        shell.setMenuBar(menubar);
    }

    // create tool bar - Common:
    private void createToolBarCommon() {
        Image img = null;

        toolBarCommon = new ToolBar(shell, SWT.FLAT);
        toolBarCommon.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        final ToolItem tbNew = new ToolItem(toolBarCommon, SWT.PUSH);
        tbNew.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                System.out.println(e.toString());
            }
        });
        tbNew.setToolTipText("Create a new document.");
        img = this.imageFactory.loadToolbarImage("new");
        if(img!=null) tbNew.setImage(img);
        else tbNew.setText("New");

        final ToolItem tbOpen = new ToolItem(toolBarCommon, SWT.PUSH);
        tbOpen.setToolTipText("Open an existing document.");
        img = this.imageFactory.loadToolbarImage("open");
        if(img!=null) tbOpen.setImage(img);
        else tbOpen.setText("Open");

        final ToolItem tbSave = new ToolItem(toolBarCommon, SWT.PUSH);
        tbSave.setToolTipText("Save current document.");
        img = this.imageFactory.loadToolbarImage("save");
        if(img!=null) tbSave.setImage(img);
        else tbSave.setText("Save");

        final ToolItem toolItem_2 = new ToolItem(toolBarCommon, SWT.SEPARATOR);

        final ToolItem tbCut = new ToolItem(toolBarCommon, SWT.NONE);
        tbCut.setToolTipText("Cut the selected glyphs.");
        img = this.imageFactory.loadToolbarImage("cut");
        if(img!=null) tbCut.setImage(img);
        else tbCut.setText("Cut");

        final ToolItem tbCopy = new ToolItem(toolBarCommon, SWT.PUSH);
        tbCopy.setToolTipText("Copy the selected glyphs.");
        img = this.imageFactory.loadToolbarImage("copy");
        if(img!=null) tbCopy.setImage(img);
        else tbCopy.setText("Copy");

        final ToolItem tbPaste = new ToolItem(toolBarCommon, SWT.PUSH);
        tbPaste.setToolTipText("Paste from the clipboard.");
        img = this.imageFactory.loadToolbarImage("paste");
        if(img!=null) tbPaste.setImage(img);
        else tbPaste.setText("Paste");

        final ToolItem toolItem_5 = new ToolItem(toolBarCommon, SWT.SEPARATOR);

        final ToolItem tbUndo = new ToolItem(toolBarCommon, SWT.PUSH);
        tbUndo.setToolTipText("Undo the last command.");
        img = this.imageFactory.loadToolbarImage("undo");
        if(img!=null) tbUndo.setImage(img);
        else tbUndo.setText("Undo");

        final ToolItem tbRedo = new ToolItem(toolBarCommon, SWT.PUSH);
        tbRedo.setToolTipText("Redo the previous command.");
        img = this.imageFactory.loadToolbarImage("redo");
        if(img!=null) tbRedo.setImage(img);
        else tbRedo.setText("Redo");
    }

    // create pop-up menu of "select color":
    private void createColorSelectMenu() {
        String[] color_names = {
            "Black",  "Maroon",  "Green", "Olive",
            "Navy",   "Purple",  "Teal",  "Gray",
            "Silver", "Red",     "Lime",  "Yellow",
            "Blue",   "Fuchsia", "Aqua",  "White"
        };

        mnuColorSelect = new Menu (shell, SWT.POP_UP);
        Image image = null;

        for(int i=0; i<16; i++) {
            mnuColor[i] = new MenuItem(mnuColorSelect, SWT.PUSH);
            mnuColor[i].setText(color_names[i]);
            image = imageFactory.loadMenuColorImage(""+i);
            if(image!=null)
                mnuColor[i].setImage(image);
        }
    }

    // create tool bar - Format:
    private void createToolBarFormat() {
        Image img = null;

        toolBarFormat = new ToolBar(shell, SWT.FLAT);
        toolBarFormat.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        final ToolItem tbFontName = new ToolItem(toolBarFormat, SWT.SEPARATOR);

        final ToolItem tbFontSize = new ToolItem(toolBarFormat, SWT.SEPARATOR);

        final ToolItem tbBold = new ToolItem(toolBarFormat, SWT.CHECK);
        tbBold.addListener (SWT.Selection, new Listener () {
            public void handleEvent (Event event) {
                view.onFormatChanged(null, null,
                    tbBold.getSelection()? Boolean.TRUE : Boolean.FALSE,
                    null, null, null);
            }
        });
        img = this.imageFactory.loadToolbarImage("bold");
        if(img!=null) tbBold.setImage(img);
        else tbBold.setText("B");

        final ToolItem tbItalic = new ToolItem(toolBarFormat, SWT.CHECK);
        tbItalic.setEnabled(false);
        img = this.imageFactory.loadToolbarImage("italic");
        if(img!=null) tbItalic.setImage(img);
        else tbItalic.setText("I");

        final ToolItem tbUnderlined = new ToolItem(toolBarFormat, SWT.CHECK);
        tbUnderlined.setEnabled(false);
        img = this.imageFactory.loadToolbarImage("underlined");
        if(img!=null) tbUnderlined.setImage(img);
        else tbUnderlined.setText("U");

        new ToolItem(toolBarFormat, SWT.SEPARATOR);

        final ToolItem tbAlignLeft = new ToolItem(toolBarFormat, SWT.RADIO);
        tbAlignLeft.setSelection(true);
        img = this.imageFactory.loadToolbarImage("left");
        if(img!=null) tbAlignLeft.setImage(img);
        else tbAlignLeft.setText("left");

        final ToolItem tbAlignCenter = new ToolItem(toolBarFormat, SWT.RADIO);
        tbAlignCenter.setEnabled(false);
        img = this.imageFactory.loadToolbarImage("center");
        if(img!=null) tbAlignCenter.setImage(img);
        else tbAlignCenter.setText("center");

        final ToolItem tbAlignRight = new ToolItem(toolBarFormat, SWT.RADIO);
        tbAlignRight.setEnabled(false);
        img = this.imageFactory.loadToolbarImage("right");
        if(img!=null) tbAlignRight.setImage(img);
        else tbAlignRight.setText("right");

        new ToolItem(toolBarFormat, SWT.SEPARATOR);

        final ToolItem tbIndentLeft = new ToolItem(toolBarFormat, SWT.PUSH);
        tbIndentLeft.setEnabled(false);
        img = this.imageFactory.loadToolbarImage("indent_left");
        if(img!=null) tbIndentLeft.setImage(img);
        else tbIndentLeft.setText("<-");

        final ToolItem tbIndentRight = new ToolItem(toolBarFormat, SWT.PUSH);
        tbIndentRight.setEnabled(false);
        img = this.imageFactory.loadToolbarImage("indent_right");
        if(img!=null) tbIndentRight.setImage(img);
        else tbIndentRight.setText("->");

        new ToolItem(toolBarFormat, SWT.SEPARATOR);

        final ToolItem tbColor = new ToolItem(toolBarFormat, SWT.DROP_DOWN);
        img = this.imageFactory.loadToolbarImage("color");
        if(img!=null) tbColor.setImage(img);
        else tbColor.setText("Color");
        tbColor.addListener (SWT.Selection, new Listener () {
            public void handleEvent (Event event) {
                if (event.detail == SWT.ARROW) {
                    Rectangle rect = tbColor.getBounds ();
                    Point pt = new Point (rect.x, rect.y + rect.height);
                    pt = toolBarFormat.toDisplay (pt);
                    mnuColorSelect.setLocation (pt.x, pt.y);
                    mnuColorSelect.setVisible (true);
                }
            }
        });

        // font name list:
        cmbFontName = new Combo(toolBarFormat, SWT.READ_ONLY);

        // place it on the tool bar:
        cmbFontName.pack();
        tbFontName.setWidth(120);
        tbFontName.setControl(cmbFontName);

        // font size list:
        cmbFontSize = new Combo(toolBarFormat, SWT.READ_ONLY);
        cmbFontSize.setItems(new String[] { "6", "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72" });
        cmbFontSize.select(5);

        // place it on the tool bar:
        cmbFontSize.pack();
        tbFontSize.setWidth(50);
        tbFontSize.setControl(cmbFontSize);

        toolBarFormat.pack();
    }

    // enumerate all fonts:
    private void initFont() {
        ArrayList all_fonts = new ArrayList(10);
        // TODO: enumerate all fonts:
        all_fonts.add("Arial");
        all_fonts.add("Comic Sans MS");
        all_fonts.add("Courier New");
        all_fonts.add("MS Sans Serif");
        all_fonts.add("Tahoma");
        all_fonts.add("Times New Roman");
        String[] fonts = (String[])all_fonts.toArray(new String[0]);
        cmbFontName.setItems(fonts);
    }

    // create "view":
    private void createComposite() {
        // scrollable view:
        ScrollableView = new Composite(shell, SWT.NONE);
        final GridLayout gridLayoutForScrollableView = new GridLayout();
        gridLayoutForScrollableView.horizontalSpacing = 0;
        gridLayoutForScrollableView.verticalSpacing = 0;
        gridLayoutForScrollableView.marginWidth = 0;
        gridLayoutForScrollableView.marginHeight = 0;
        gridLayoutForScrollableView.numColumns = 2;
        ScrollableView.setLayout(gridLayoutForScrollableView);
        ScrollableView.setLayoutData(new GridData(GridData.FILL_BOTH));

        // text view:
        textView = new Canvas(ScrollableView, SWT.NONE);
        textView.setLayoutData(new GridData(GridData.FILL_BOTH));
        textView.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));

        sliderV = new Slider(ScrollableView, SWT.VERTICAL);
        sliderV.setLayoutData(new GridData(GridData.FILL_VERTICAL));

        sliderH = new Slider(ScrollableView, SWT.NONE);
        sliderH.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));

        // IMPORTANT:
        // add listener:
        textView.addListener(SWT.KeyDown, new Listener () {
            public void handleEvent(Event event) {
                //
            }
        });
        textView.addListener (SWT.MouseDown, new Listener () {
            public void handleEvent(Event event) {
            }
        });

        // and set caret:
        caret = new Caret(textView, 0);
        caret.setBounds(1,1,2,24); // TODO:
        textView.setCaret(caret);
    }

    /**
     * Get the default graphics. DO NOT dispose it after use!!! 
     * 
     * @see com.crackj2ee.jexi.ui.Frame#getDefaultGraphics()
     */
    public com.crackj2ee.jexi.ui.Graphics getDefaultGraphics() {
        return this.defaultGraphics;
    }

    /**
     * Get the view. 
     * 
     * @see com.crackj2ee.jexi.ui.Frame#getView()
     */
    public com.crackj2ee.jexi.ui.View getView() {
        return this.view;
    }
}

class SelectColorHandler extends SelectionAdapter {

    private static int[] COLORS = {
        0x000000, 0x800000, 0x008000, 0x808000,
        0x000080, 0x800080, 0x008080, 0x808080,
        0xc0c0c0, 0xff0000, 0x00ff00, 0xffff00,
        0x0000ff, 0xff00ff, 0x00ffff, 0xffffff
    };

    private com.crackj2ee.jexi.ui.View view;
    private int index;
    private com.crackj2ee.jexi.ui.Color color;

    public SelectColorHandler(com.crackj2ee.jexi.ui.View view, int index) {
        this.view = view;
        this.index = index;
        this.color = com.crackj2ee.jexi.ui.ColorFactory.instance().createColor(COLORS[index]);
    }

    // when select color:
    public void widgetSelected(SelectionEvent e) {
        view.onFormatChanged(null, null, null, null, null, color);
    }
}
