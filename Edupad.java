package edupad;//package for all work done
/**
    * class=> Edupad => Edupad class is the main class for design and most of the implementation part
    @Author: Sushil Jangid and BCA III Year Team 2019-20
    
*/



import java.awt.*;
import java.awt.dnd.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import java.io.IOException;
import java.net.*;
import javax.xml.bind.*;
import javax.xml.parsers.*;
import javax.swing.text.*;
import java.awt.datatransfer.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Date;
import java.text.DateFormat;
import edupad.*;


/**
    * Main class for creating GUI and implementing the functionalities of A GUI Text Editor
*/
public final class Edupad extends JFrame
{

    JMenuBar mb;

    JSplitPane jsplit,numberPaneSplit;

    JTabbedPane _tabbedPane,numberPane;

    JList<String> _list;

    DefaultListModel<String> listModel;

    JToolBar statusBar;

    JLabel readylabel;

    JLabel filenameLabel=new JLabel("");
    JLabel rowLabel=new JLabel("Row : ");
    JLabel colLabel=new JLabel("Col : ");
    JLabel characterLabel=new JLabel("Characters : ");
    JLabel wordLabel=new JLabel("Words : ");

    JToolBar _toolbar;
    JButton toolbar_new,toolbar_open,toolbar_save,toolbar_saveas,toolbar_cut,toolbar_copy,toolbar_paste,toolbar_goto,toolbar_font,toolbar_screenshot,toolbar_calculator;//toolbar options
    JMenu windowMenu;

    int count=1;
    static boolean isDarkTheme=false;

    DefaultListModel filesHoldListModel=new DefaultListModel<String>();

    JList<String> filesHoldList=new JList<String>(filesHoldListModel);


    UndoManager _undoManager = new UndoManager();
    Action undoAction = new PerformUndoAction(_undoManager);
    Action redoAction = new PerformRedoAction(_undoManager);


    Clipboard clip = getToolkit().getSystemClipboard();


    ButtonGroup buttonGroup;


    Toolkit toolkit=Toolkit.getDefaultToolkit();


    JPopupMenu _popupMenu;
    JMenu help;
    JMenuItem help_about,help_feedback,help_manualguide;
    JButton toolbar_help;
    JCheckBoxMenuItem view_wordwrap;
    int fontSize=16;
    Font applicationFont;
    
    /** 
       * returns the center point of the screen
       @return pt
    */
    public Point getCenterPoints()
    {
        Point pt=new Point(0,0);
        Dimension d=toolkit.getScreenSize();
        pt.x=d.width/3;
        pt.y=d.height/4;

        return pt;
    }



    /*
        * constructor of Edupad class; This creates all the menu items,toolbars and tabbedpanes
    */
    public Edupad()
    {
        setTitle("Edupad");
        //setting no action for close button of JFrame
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        mb=new JMenuBar();


        isDarkTheme = getNodeTextContent("lookAndFeel").equals("GlobalDark");
        //setting default font size to the program
        applicationFont = new Font("Bell MT",Font.ITALIC,19);
        setUIFont (new javax.swing.plaf.FontUIResource("Bell MT",Font.ITALIC,19));
        UIManager.put("OptionPane.minimumSize",new Dimension(500,150));
        if(isDarkTheme)
        {
            UIManager.getLookAndFeelDefaults().put("MenuItem.acceleratorForeground", Color.yellow);
        }
        
        
        
        
        
        
        
        
        
        
        
        

        //*********************************************************************************
        // File menu
        //*********************************************************************************
        JMenu file=new JMenu("  File ");
        file.setMnemonic(KeyEvent.VK_F);

        // creating file menu itemas
        JMenuItem file_new=new JMenuItem("  New ");
        file_new.setIcon(new ImageIcon(this.getClass().getResource("resources/new.png")));

        file_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));

        JMenuItem file_open=new JMenuItem("  Open ");
        file_open.setIcon(new ImageIcon(this.getClass().getResource("resources/open.png")));
        file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));

        JMenuItem file_save=new JMenuItem("  Save ");
        file_save.setIcon(new ImageIcon(this.getClass().getResource("resources/save.png")));
        file_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));

        JMenuItem file_saveas=new JMenuItem("  Save As ");
        file_saveas.setIcon(new ImageIcon(this.getClass().getResource("resources/saveas.png")));
        file_saveas.setAccelerator(KeyStroke.getKeyStroke("F2"));

        JMenuItem file_saveall=new JMenuItem("  Save All ");
        file_saveall.setIcon(new ImageIcon(this.getClass().getResource("resources/saveall.png")));
        file_saveall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
        
        JMenuItem file_print=new JMenuItem("  Print ");
        file_print.setIcon(new ImageIcon(this.getClass().getResource("resources/print.png")));
        file_print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
        
        JMenuItem file_close=new JMenuItem("  Close ");
        file_close.setIcon(new ImageIcon(this.getClass().getResource("resources/close.png")));
        file_close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
        
        JMenuItem file_closeall=new JMenuItem("  Close All ");
        file_closeall.setIcon(new ImageIcon(this.getClass().getResource("resources/closeall.png")));
        file_closeall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
        
        JMenuItem file_openinsystemeditor=new JMenuItem("  Open In System Editor ");
        file_openinsystemeditor.setIcon(new ImageIcon(this.getClass().getResource("resources/systemeditor.png")));
        
        JMenuItem file_exit=new JMenuItem("  Exit ");
        file_exit.setIcon(new ImageIcon(this.getClass().getResource("resources/exit.png")));
        file_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));

        // adding actions to file menu items
        File_MenuItemsAction file_action=new File_MenuItemsAction();

        file_new.addActionListener(file_action);
        file_open.addActionListener(file_action);
        file_save.addActionListener(file_action);
        file_saveas.addActionListener(file_action);
        file_saveall.addActionListener(file_action);
        file_print.addActionListener(file_action);
        file_close.addActionListener(file_action);
        file_closeall.addActionListener(file_action);
        file_openinsystemeditor.addActionListener(file_action);
        file_exit.addActionListener(file_action);

        //add MenuListener to menu items
        JMenuItem[] filemenuitems={ file_save,file_saveas,file_saveall,file_print,file_close,file_closeall,file_openinsystemeditor };

        Menus_MenuListener fml=new Menus_MenuListener(filemenuitems);
        file.addMenuListener(fml);
        
        



        //****************************************************************************************
        // Edit menu
        //***************************************************************************************
        JMenu edit=new JMenu("  Edit  ");
        edit.setMnemonic(KeyEvent.VK_E);
        //creating edit menu items
        JMenuItem edit_cut=new JMenuItem("  Cut ");
        edit_cut.setIcon(new ImageIcon(this.getClass().getResource("resources/cut.png")));
        edit_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));

        JMenuItem edit_copy = new JMenuItem("  Copy ");
        edit_copy.setIcon(new ImageIcon(this.getClass().getResource("resources/copy.png")));
        edit_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));

        JMenuItem edit_paste = new JMenuItem("  Paste ");
        edit_paste.setIcon(new ImageIcon(this.getClass().getResource("resources/paste.png")));
        edit_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));

        JMenuItem edit_undo = new JMenuItem("  Undo ");
        edit_undo.setIcon(new ImageIcon(this.getClass().getResource("resources/undo.png")));
        edit_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));

        JMenuItem edit_redo = new JMenuItem("  Redo ");
        edit_redo.setIcon(new ImageIcon(this.getClass().getResource("resources/redo.png")));
        edit_redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.CTRL_MASK));


        JMenuItem edit_find = new JMenuItem("  Find ");
        edit_find.setIcon(new ImageIcon(this.getClass().getResource("resources/find.png")));
        edit_find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));

        JMenuItem edit_replace = new JMenuItem("  Replace ");
        edit_replace.setIcon(new ImageIcon(this.getClass().getResource("resources/replace.png"))); 

        JMenuItem edit_goto = new JMenuItem("  GoTo ");
        edit_goto.setIcon(new ImageIcon(this.getClass().getResource("resources/goto.png")));
        edit_goto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));

        JMenuItem edit_selectall = new JMenuItem("  Select All ");
        edit_selectall.setIcon(new ImageIcon(this.getClass().getResource("resources/selectall.png")));
        edit_selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
        
        JMenuItem edit_dateandtime = new JMenuItem("  Date and Time ");
        edit_dateandtime.setIcon(new ImageIcon(this.getClass().getResource("resources/dateTime.png")));
        edit_dateandtime.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));

        JMenu edit_changecase=new JMenu("  Change Case ");
        edit_changecase.setIcon(new ImageIcon(this.getClass().getResource("resources/changecase.png")));
        JMenuItem edit_changecase_upper=new JMenuItem("  Upper Case ");
        edit_changecase_upper.setIcon(new ImageIcon(this.getClass().getResource("resources/uppercase.png")));
        JMenuItem edit_changecase_lower = new JMenuItem("  Lower Case ");
        edit_changecase_lower.setIcon(new ImageIcon(this.getClass().getResource("resources/lowercase.png")));
        JMenuItem edit_changecase_sentence = new JMenuItem("  Sentence Case ");
        edit_changecase_sentence.setIcon(new ImageIcon(this.getClass().getResource("resources/sentencecase.png")));

        JMenuItem edit_nextdocument=new JMenuItem("  Next Document ");
        edit_nextdocument.setIcon(new ImageIcon(this.getClass().getResource("resources/next.png")));
        //edit_nextdocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP,ActionEvent.CTRL_MASK));
        
        JMenuItem edit_previousdocument = new JMenuItem("  Previous Document ");
        edit_previousdocument.setIcon(new ImageIcon(this.getClass().getResource("resources/previous.png")));
        //edit_previousdocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,ActionEvent.CTRL_MASK));

         // adding actions to edit menu items
        Edit_MenuItemsAction edit_action=new Edit_MenuItemsAction();

        edit_cut.addActionListener(edit_action);
        edit_copy.addActionListener(edit_action);
        edit_paste.addActionListener(edit_action);
        edit_goto.addActionListener(edit_action);
        edit_find.addActionListener(edit_action);
        edit_replace.addActionListener(edit_action);
        edit_selectall.addActionListener(edit_action);
        edit_dateandtime.addActionListener(edit_action);
        edit_changecase_upper.addActionListener(edit_action);
        edit_changecase_lower.addActionListener(edit_action);
        edit_changecase_sentence.addActionListener(edit_action);
        edit_nextdocument.addActionListener(edit_action);
        edit_previousdocument.addActionListener(edit_action);

        edit_undo.addActionListener(undoAction);
        edit_redo.addActionListener(redoAction);

        // add MenuListener to menu items
        JMenuItem[] editmenuitems={ edit_cut,edit_copy,edit_paste,edit_undo,edit_redo,edit_find,edit_replace,edit_goto,edit_selectall,
                                                      edit_changecase,edit_changecase_upper,edit_changecase_lower,edit_changecase_sentence,edit_nextdocument,edit_previousdocument,edit_dateandtime};

        Menus_MenuListener eml=new Menus_MenuListener(editmenuitems);
        edit.addMenuListener(eml);




        //***************************************************************************************
        // View menu
        //***************************************************************************************
        JMenu view=new JMenu("  View  ");
		view.setMnemonic(KeyEvent.VK_V);
        //creating view menu items
        JMenuItem view_font=new JMenuItem("  Font ");
        view_font.setIcon(new ImageIcon(this.getClass().getResource("resources/font.png")));

        JMenuItem view_forecolor=new JMenuItem("  Font Color ");
        view_forecolor.setIcon(new ImageIcon(this.getClass().getResource("resources/fontcolor.png")));
        
		JMenuItem view_backcolor=new JMenuItem("  Page Color ");
        view_backcolor.setIcon(new ImageIcon(this.getClass().getResource("resources/pagecolor.png")));

        JMenu view_tabsalign=new JMenu("  Tabs Alignment ");
        view_tabsalign.setIcon(new ImageIcon(this.getClass().getResource("resources/tabsalignment.png")));
        JRadioButtonMenuItem view_tabsalign_top=new JRadioButtonMenuItem("  Top ");
        view_tabsalign_top.setSelected(true);
        JRadioButtonMenuItem view_tabsalign_bottom = new JRadioButtonMenuItem("  Bottom ");
        JRadioButtonMenuItem view_tabsalign_left = new JRadioButtonMenuItem("  Left ");
        JRadioButtonMenuItem view_tabsalign_right = new JRadioButtonMenuItem("  Right ");

        buttonGroup=new ButtonGroup();
        buttonGroup.add(view_tabsalign_top);
        buttonGroup.add(view_tabsalign_bottom);
        buttonGroup.add(view_tabsalign_left);
        buttonGroup.add(view_tabsalign_right);


        JCheckBoxMenuItem view_toolbar=new JCheckBoxMenuItem("  Tool Bar ");
        view_toolbar.setSelected(true);

        JCheckBoxMenuItem view_statusstrip=new JCheckBoxMenuItem("  Status Strip ");
        view_statusstrip.setSelected(true);

        JCheckBoxMenuItem view_documentselector = new JCheckBoxMenuItem("  Document Selector ");
        view_documentselector.setSelected(true);
        
        view_wordwrap = new JCheckBoxMenuItem("  Word Wrap ");
        view_wordwrap.setSelected(true);
        
        JMenu view_zoom = new JMenu("  Zoom ");
		view_zoom.setIcon(new ImageIcon(this.getClass().getResource("resources/zoom.png")));
			//zoom menu items
			JMenuItem view_zoom_zoomin = new JMenuItem("  Zoom In ");
			view_zoom_zoomin.setIcon(new ImageIcon(this.getClass().getResource("resources/zoomin.png")));
			JMenuItem view_zoom_zoomout = new JMenuItem("  Zoom Out ");
			view_zoom_zoomout.setIcon(new ImageIcon(this.getClass().getResource("resources/zoomout.png")));
			JMenuItem view_zoom_defaultzoom = new JMenuItem("  Default Scale ");
			view_zoom_defaultzoom.setIcon(new ImageIcon(this.getClass().getResource("resources/defaultzoom.png")));
            //adding to zoom menu
            view_zoom.add(view_zoom_zoomin);
            view_zoom.add(view_zoom_zoomout);
            view_zoom.add(view_zoom_defaultzoom);
            //adding keystrokes for zoom options
            view_zoom_zoomout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD,ActionEvent.CTRL_MASK));
            view_zoom_zoomin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT,ActionEvent.CTRL_MASK));
            view_zoom_defaultzoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0,ActionEvent.CTRL_MASK));

        JMenu view_lookandfeel=new JMenu("  Look and Feel ");
        view_lookandfeel.setIcon(new ImageIcon(this.getClass().getResource("resources/lookandfeel.png")));

        JMenuItem view_lookandfeel_java=new JMenuItem("  Java ");
        view_lookandfeel_java.setIcon(new ImageIcon(this.getClass().getResource("resources/java.png")));

        JMenuItem view_lookandfeel_motif=new JMenuItem("  Motif ");
        view_lookandfeel_motif.setIcon(new ImageIcon(this.getClass().getResource("resources/motif.png")));

        JMenuItem view_lookandfeel_nimbus=new JMenuItem("  Nimbus ");
        view_lookandfeel_nimbus.setIcon(new ImageIcon(this.getClass().getResource("resources/nimbus.png")));

        JMenuItem view_lookandfeel_windows=new JMenuItem("  Windows ");
        view_lookandfeel_windows.setIcon(new ImageIcon(this.getClass().getResource("resources/windows.png")));

        JMenuItem view_lookandfeel_windowsclassic=new JMenuItem("  Windows Classic ");
        view_lookandfeel_windowsclassic.setIcon(new ImageIcon(this.getClass().getResource("resources/windowsclassic.png")));

        JMenuItem view_lookandfeel_globaldark = new JMenuItem("  Global Dark ");
        view_lookandfeel_globaldark.setIcon(new ImageIcon(this.getClass().getResource("resources/globaldark.png")));



        view_lookandfeel.add(view_lookandfeel_java);
        view_lookandfeel.add(view_lookandfeel_motif);
        view_lookandfeel.add(view_lookandfeel_nimbus);
        view_lookandfeel.add(view_lookandfeel_windows);
        view_lookandfeel.add(view_lookandfeel_windowsclassic);
        view_lookandfeel.add(view_lookandfeel_globaldark);

        //adding actions to view menu items
        View_MenuItemsAction view_action=new View_MenuItemsAction();

        view_font.addActionListener(view_action);
        view_forecolor.addActionListener(view_action);
        view_backcolor.addActionListener(view_action);
        view_tabsalign_top.addActionListener(view_action);
        view_tabsalign_bottom.addActionListener(view_action);
        view_tabsalign_left.addActionListener(view_action);
        view_tabsalign_right.addActionListener(view_action);
        view_lookandfeel_java.addActionListener(view_action);
        view_lookandfeel_motif.addActionListener(view_action);
        view_lookandfeel_nimbus.addActionListener(view_action);
        view_lookandfeel_windows.addActionListener(view_action);
        view_lookandfeel_windowsclassic.addActionListener(view_action);
        view_lookandfeel_globaldark.addActionListener(view_action);
        
        view_wordwrap.addActionListener(view_action);
        
		Zoom zoom = new Zoom();		//Zoom class is for zooming action
        view_zoom_zoomin.addActionListener(zoom);
        view_zoom_zoomout.addActionListener(zoom);
        view_zoom_defaultzoom.addActionListener(zoom);

        view_documentselector.addActionListener(new View_DocumentSelector_Action(view_documentselector));

        view_toolbar.addActionListener(new View_ToolBar_Action(view_toolbar));

        view_statusstrip.addActionListener(new View_StatusStrip_Action(view_statusstrip));

        JMenuItem[] viewmenuitems = {view_font,view_forecolor,view_backcolor,view_zoom,view_zoom_defaultzoom,view_zoom_zoomin,view_zoom_zoomout};

        Menus_MenuListener vml = new Menus_MenuListener(viewmenuitems);
        view.addMenuListener(vml);




        //****************************************************************************************
        // Run menu
        //****************************************************************************************
        JMenu run=new JMenu("  Run ");
        run.setMnemonic(KeyEvent.VK_R);
        //creating run menu items
        JMenuItem run_run=new JMenuItem("  Run ");
        run_run.setAccelerator(KeyStroke.getKeyStroke("CTRL+R"));
        run_run.setIcon(new ImageIcon(this.getClass().getResource("resources/run.png")));

        JMenuItem run_runinbrowser=new JMenuItem("  Run in Browser ");
        run_runinbrowser.setIcon(new ImageIcon(this.getClass().getResource("resources/runinbrowser.png")));

        JMenuItem run_googlesearch=new JMenuItem("  Google Search ");
        run_googlesearch.setIcon(new ImageIcon(this.getClass().getResource("resources/googlesearch.png")));

        // adding actions to run menu items
        Run_MenuItemsAction run_action=new Run_MenuItemsAction();

        run_run.addActionListener(run_action);
        run_run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
        run_runinbrowser.addActionListener(run_action);
        run_googlesearch.addActionListener(run_action);
        JMenuItem[] runmenuitems = {run_runinbrowser,run_googlesearch};
        Menus_MenuListener rml = new Menus_MenuListener(runmenuitems);
        run.addMenuListener(rml);
        //i wll also disble Window Menu and WordWrap CheckboxMenuItem when there is no tab opened in Menus_MenuListener


        //****************************************************************************************
        // Window menu
        //****************************************************************************************
        windowMenu=new JMenu("Window");
        windowMenu.addMenuListener(new WindowMenuAction());
        windowMenu.setMnemonic(KeyEvent.VK_W);



        //****************************************************************************************
        // help menu
        //****************************************************************************************
        help = new JMenu("  Help ");
        help.setMnemonic(KeyEvent.VK_H);
        help_manualguide = new JMenuItem("  Manual Guide ");
        help_manualguide.setIcon(new ImageIcon(this.getClass().getResource("resources/manualguide.png")));
        
        help_feedback = new JMenuItem("  Feedback ");
        help_feedback.setIcon(new ImageIcon(this.getClass().getResource("resources/feedback.png")));

        help_about = new JMenuItem("  About Us ");
        help_about.setIcon(new ImageIcon(this.getClass().getResource("resources/aboutus.png")));

        // adding actions to help menu items
        Help_ItemsAction help_action = new Help_ItemsAction();
        help_about.addActionListener(help_action);
        help_feedback.addActionListener(help_action);
        help_manualguide.addActionListener(help_action);




        //***********************************************************
        // adding file menuitems to file menu

        file.add(file_new);
        file.addSeparator();
        file.add(file_open);
        file.addSeparator();
        file.add(file_save);
        file.add(file_saveas);
        file.add(file_saveall);
        file.addSeparator();
        file.add(file_print);
        file.addSeparator();
        file.add(file_close);
        file.add(file_closeall);
        file.addSeparator();
        file.add(file_openinsystemeditor);
        file.addSeparator();
        file.add(file_exit);

        // add file menu to menu bar mb
        mb.add(file);


        // adding edit menuitems to edit menu
        edit.add(edit_cut);
        edit.add(edit_copy);
        edit.add(edit_paste);
        edit.addSeparator();
        edit.add(edit_undo);
        edit.add(edit_redo);
        edit.addSeparator();
        edit.add(edit_find);
        edit.add(edit_replace);
        edit.add(edit_goto);
        edit.addSeparator();
        edit.add(edit_selectall);
        edit.addSeparator();
        edit.add(edit_dateandtime);
        edit.addSeparator();
        edit_changecase.add(edit_changecase_upper);
        edit_changecase.add(edit_changecase_lower);
        edit_changecase.add(edit_changecase_sentence);
        edit.add(edit_changecase);
        edit.addSeparator();
        edit.add(edit_nextdocument);
        edit.add(edit_previousdocument);

        //add edit menu to mb
        mb.add(edit);


        //adding view menuitems to view menu
        view.add(view_font);
        view.addSeparator();
        view.add(view_forecolor);
        view.add(view_backcolor);
        view.addSeparator();
        view_tabsalign.add(view_tabsalign_top);
        view_tabsalign.add(view_tabsalign_bottom);
        view_tabsalign.add(view_tabsalign_left);
        view_tabsalign.add(view_tabsalign_right);
        view.add(view_tabsalign);
        view.addSeparator();
        view.add(view_zoom);
        view.addSeparator();
        view.add(view_wordwrap);
        view.add(view_toolbar);
        view.add(view_statusstrip);
        view.add(view_documentselector);
        view.addSeparator();
        view.add(view_lookandfeel);

        //add view menu to mb
        mb.add(view);


        // adding run menu items to run menu
        run.add(run_run);
        run.addSeparator();
        run.add(run_runinbrowser);
        run.addSeparator();
        run.add(run_googlesearch);

        //add run menu to mb
        mb.add(run);


        mb.add(windowMenu);


        //adding help menu items to help menu
        help.add(help_manualguide);
        help.add(help_feedback);
        help.add(help_about);

        // add help menu to mb
        mb.add(help);
        


        ////////////////////////////////////////////////////////////////////////
        //set visibility of Document Selector,Status Strip,ToolStrip
        // and Tabs Alignment when application starts by reading values from
        // a file files/viewsfile.xml
        String isDocumentSelect = getNodeTextContent("documentSelector");
        String isStatusStrip = getNodeTextContent("statusStrip");
        String isToolStrip = getNodeTextContent("toolStrip");
        String tabsAlign = getNodeTextContent("tabsAlignment");





        //***********************************************************************
        // create _tabbedPane object & adding ChangeListener interface to it
        //***********************************************************************
         _tabbedPane=new JTabbedPane();
         _tabbedPane.setFont(new Font("Calibri",Font.PLAIN,14));
         _tabbedPane.addChangeListener(new TabChanged());


         //setting tab placement
        switch(tabsAlign)
        {
             case "Top":
                 _tabbedPane.setTabPlacement(JTabbedPane.TOP);
                 view_tabsalign_top.setSelected(true);
                 break;

            case "Bottom":
                 _tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
                 view_tabsalign_bottom.setSelected(true);
                 break;

            case "Left":
                 _tabbedPane.setTabPlacement(JTabbedPane.LEFT);
                 view_tabsalign_left.setSelected(true);
                 break;

            case "Right":
                 _tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
                 view_tabsalign_right.setSelected(true);
                 break;
        }




         //***********************************************************************
         // create listModel object & _list object and adding
         // ListSelectionListener interface to _list
         //************************************************************************
         listModel=new DefaultListModel<String>();
         _list=new JList<String>(listModel);
         _list.setFont(new Font("Calibri",Font.PLAIN,14));

         if(isDarkTheme)
         {
             _list.setBackground(new Color(10, 10, 20));
         }

         _list.setMinimumSize(new Dimension(400,600));
         JScrollPane listpane=new JScrollPane(_list);
         _list.addListSelectionListener(new SelectTabFromListItem());


         // creating document tab pane & adding listpane object to it
         JTabbedPane documentPane=new JTabbedPane();
         documentPane.addTab(" Document Selector", listpane);



        //***********************************************************
        // create and add documentPane & _tabbedPane to jsplit
         //***********************************************************
        jsplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, documentPane,_tabbedPane);
        jsplit.setContinuousLayout(true);
        jsplit.setOneTouchExpandable(true);
        jsplit.setDividerLocation(210);
        


        //set visibility to document selector
        if(isDocumentSelect.equals("true"))
        {
            jsplit.getLeftComponent().setVisible(true);
            jsplit.setDividerLocation(210);
            view_documentselector.setSelected(true);
        }else
        {
            jsplit.getLeftComponent().setVisible(false);
            view_documentselector.setSelected(false);
        }





        ///////////////////////////////////////
        //set JMenubar
        setJMenuBar(mb);





        //************************************************************
        //creating & adding toolbar to north direction
        //************************************************************
        _toolbar=new JToolBar();
        _toolbar.setFloatable(false);
        _toolbar.setRequestFocusEnabled(false);

        // creating toolbar buttons
        toolbar_new=new JButton(new ImageIcon(this.getClass().getResource("resources/new.png")));
        toolbar_new.setToolTipText("New (CTRL+N)");
        toolbar_new.addActionListener(new ToolBarButtonsAction("new"));

        toolbar_open = new JButton(new ImageIcon(this.getClass().getResource("resources/open.png")));
        toolbar_open.setToolTipText("Open (CTRL+O)");
        toolbar_open.addActionListener(new ToolBarButtonsAction("open"));

        toolbar_save = new JButton(new ImageIcon(this.getClass().getResource("resources/save.png")));
        toolbar_save.setToolTipText("Save (CTRL+S)");
        toolbar_save.addActionListener(new ToolBarButtonsAction("save"));

        toolbar_saveas = new JButton(new ImageIcon(this.getClass().getResource("resources/saveas.png")));
        toolbar_saveas.setToolTipText("Save As (F2)");
        toolbar_saveas.addActionListener(new ToolBarButtonsAction("saveas"));

        toolbar_cut = new JButton(new ImageIcon(this.getClass().getResource("resources/cut.png")));
        toolbar_cut.setToolTipText("Cut (CTRL+X)");
        toolbar_cut.addActionListener(new ToolBarButtonsAction("cut"));

        toolbar_copy = new JButton(new ImageIcon(this.getClass().getResource("resources/copy.png")));
        toolbar_copy.setToolTipText("Copy (CTRL+C)");
        toolbar_copy.addActionListener(new ToolBarButtonsAction("copy"));

        toolbar_paste = new JButton(new ImageIcon(this.getClass().getResource("resources/paste.png")));
        toolbar_paste.setToolTipText("Paste (CTRL+V)");
        toolbar_paste.addActionListener(new ToolBarButtonsAction("paste"));

        toolbar_goto = new JButton(new ImageIcon(this.getClass().getResource("resources/goto.png")));
        toolbar_goto.setToolTipText("GoTo (CTRL+G)");
        toolbar_goto.addActionListener(new ToolBarButtonsAction("goto"));

        toolbar_font = new JButton(new ImageIcon(this.getClass().getResource("resources/font.png")));
        toolbar_font.setToolTipText("Set Font");
        toolbar_font.addActionListener(new ToolBarButtonsAction("font"));
        
        toolbar_screenshot = new JButton(new ImageIcon(this.getClass().getResource("resources/screenshot.png")));
        toolbar_screenshot.setToolTipText("Screen Shot");
        toolbar_screenshot.addActionListener(new ToolBarButtonsAction("screenshot"));
        
        toolbar_calculator = new JButton(new ImageIcon(this.getClass().getResource("resources/calculator.png")));
        toolbar_calculator.setToolTipText("Open Calculator");
        toolbar_calculator.addActionListener(new ToolBarButtonsAction("calculator"));

       toolbar_help = new JButton(new ImageIcon(this.getClass().getResource("resources/help.png")));
        toolbar_help.setToolTipText("Help");
        toolbar_help.addActionListener(help_action);
        
        if(isDarkTheme)
        {
            UIManager.put("ToolTip.foreground",Color.WHITE);
        }

        //adding toolbar buttons to _toolbar object
        _toolbar.add(toolbar_new);
        _toolbar.addSeparator(new Dimension(4,4));
        _toolbar.add(toolbar_open);
        _toolbar.addSeparator(new Dimension(4,4));
        _toolbar.add(toolbar_save);
        _toolbar.add(toolbar_saveas);
        _toolbar.addSeparator(new Dimension(6,6));
        _toolbar.add(toolbar_cut);
        _toolbar.add(toolbar_copy);
        _toolbar.add(toolbar_paste);
        _toolbar.add(toolbar_goto);
        _toolbar.addSeparator(new Dimension(6, 6));
        _toolbar.add(toolbar_font);
        _toolbar.add(toolbar_screenshot);
        _toolbar.add(toolbar_calculator);
        _toolbar.add(toolbar_help);

        

        ////////////////////////////////////////////////////////
        //set visibility to tool strip
        if (isToolStrip.equals("true"))
        {
            _toolbar.setVisible(true);
            view_toolbar.setSelected(true);
        }
        else
        {
            _toolbar.setVisible(false);
            view_toolbar.setSelected(false);
        }




        //********************************************************
        // creating & adding statusbar to south direction
        //*********************************************************
        statusBar=new JToolBar();
        statusBar.setFloatable(false);

        if(isDarkTheme)
        {
            statusBar.setBackground(new Color(10, 10, 10));
        }

        readylabel=new JLabel("Edupad");
        readylabel.setFont(new Font("Calibri",Font.PLAIN,15));
        filenameLabel.setFont(new Font("Calibri",Font.PLAIN,15));
        statusBar.add(readylabel);
        statusBar.add(new JLabel("                          "));
        statusBar.add(filenameLabel);
        statusBar.add(new JLabel("                                                            "));
        statusBar.add(rowLabel);
        statusBar.add(new JLabel("         "));
        statusBar.add(colLabel);
        statusBar.add(new JLabel("         "));
        statusBar.add(characterLabel);
        statusBar.add(new JLabel("         "));
        statusBar.add(wordLabel);

        if(isDarkTheme)
        {
            readylabel.setForeground(new Color(240,240,220));
            filenameLabel.setForeground(new Color(240, 240, 220));
            rowLabel.setForeground(new Color(240, 240, 220));
            colLabel.setForeground(new Color(240, 240, 220));
            characterLabel.setForeground(new Color(240, 240, 220));
            wordLabel.setForeground(new Color(240, 240, 220));
        }

        ///////////////////////////////////////////////////
        //set visibility to status strip
        if (isStatusStrip.equals("true"))
        {
            statusBar.setVisible(true);
            view_statusstrip.setSelected(true);
        }
        else
        {
            statusBar.setVisible(false);
            view_statusstrip.setSelected(false);
        }



        //************************************************************
        // creating popup menu
        //************************************************************
        _popupMenu=new JPopupMenu();

        JMenuItem popup_edit_cut = new JMenuItem("  Cut");
        popup_edit_cut.setIcon(new ImageIcon(this.getClass().getResource("resources/cut.png")));
        popup_edit_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        JMenuItem popup_edit_copy = new JMenuItem("  Copy");
        popup_edit_copy.setIcon(new ImageIcon(this.getClass().getResource("resources/copy.png")));
        popup_edit_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        JMenuItem popup_edit_paste = new JMenuItem("  Paste");
        popup_edit_paste.setIcon(new ImageIcon(this.getClass().getResource("resources/paste.png")));
        popup_edit_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        
        JMenuItem popup_edit_undo = new JMenuItem("  Undo");
        popup_edit_undo.setIcon(new ImageIcon(this.getClass().getResource("resources/undo.png")));
        popup_edit_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        
        JMenuItem popup_edit_redo = new JMenuItem("  Redo");
        popup_edit_redo.setIcon(new ImageIcon(this.getClass().getResource("resources/redo.png")));
        popup_edit_redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));

        JMenuItem popup_edit_selectall = new JMenuItem("  Select All");
        popup_edit_selectall.setIcon(new ImageIcon(this.getClass().getResource("resources/selectall.png")));
        popup_edit_selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        JMenu popup_edit_changecase = new JMenu("  Change Case");
        JMenuItem popup_edit_changecase_upper = new JMenuItem("  Upper Case   ");
        JMenuItem popup_edit_changecase_lower = new JMenuItem("  Lower Case   ");
        JMenuItem popup_edit_changecase_sentence = new JMenuItem("  Sentence Case   ");

        popup_edit_changecase.add(popup_edit_changecase_upper);
        popup_edit_changecase.add(popup_edit_changecase_lower);
        popup_edit_changecase.add(popup_edit_changecase_sentence);

        JMenuItem popup_view_font = new JMenuItem("  Font ");
        popup_view_font.setIcon(new ImageIcon(this.getClass().getResource("resources/font.png")));

        // add actions to popup menu items
        popup_edit_cut.addActionListener(edit_action);
        popup_edit_copy.addActionListener(edit_action);
        popup_edit_paste.addActionListener(edit_action);
        popup_edit_selectall.addActionListener(edit_action);
        popup_edit_changecase_upper.addActionListener(edit_action);
        popup_edit_changecase_lower.addActionListener(edit_action);
        popup_edit_changecase_sentence.addActionListener(edit_action);
        popup_view_font.addActionListener(view_action);
        
        //UndoManager _undoManager = new UndoManager();
        popup_edit_undo.addActionListener(new PerformUndoAction(_undoManager));
        popup_edit_redo.addActionListener(new PerformRedoAction(_undoManager));
        
        //adding popup menu items to _popupMenu
        _popupMenu.add(popup_edit_cut);
        _popupMenu.add(popup_edit_copy);
        _popupMenu.add(popup_edit_paste);
        _popupMenu.addSeparator();
        _popupMenu.add(popup_edit_undo);
        _popupMenu.add(popup_edit_redo);
        _popupMenu.addSeparator();
        _popupMenu.add(popup_edit_selectall);
        _popupMenu.addSeparator();
        _popupMenu.add(popup_edit_changecase);
        _popupMenu.addSeparator();
        _popupMenu.add(popup_view_font);



        //add window listener to Edupad frame
        addWindowListener(new Load_Close_Frame_Action());



        //**************************************************************
        //get content pane & adding toolbar,statusbar & jsplit to it
        //***************************************************************
        Container cp=getContentPane();
        cp.add(_toolbar,BorderLayout.NORTH);
        cp.add(statusBar,BorderLayout.SOUTH);
        cp.add(jsplit);


    }//constructor closed





//**************************************************************************
// class for action of Window menu
//**************************************************************************
class WindowMenuAction implements MenuListener
{
    @Override
    public void menuSelected(MenuEvent me)
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            windowMenu.setVisible(true);
            windowMenu.removeAll();

            JMenuItem window_restart=new JMenuItem(" Restart ");
            window_restart.addActionListener(new WindowRestartAction());
            window_restart.setIcon(new ImageIcon(this.getClass().getResource("resources/restart.png")));

            windowMenu.add(window_restart);

            windowMenu.addSeparator();

            int tabcount = _tabbedPane.getTabCount();
            String tabtext=_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex());
            for (int i = 0; i < tabcount; i++)
            {
                String title = _tabbedPane.getTitleAt(i);
                JCheckBoxMenuItem witem=new JCheckBoxMenuItem(title);
                witem.addActionListener(new Window_MenuItemsAction());
                if(tabtext.equals(title)){
                    witem.setSelected(true);
                }
                windowMenu.add(witem);
            }
        }
        else
        {
            windowMenu.setVisible(false);
        }
    }

    @Override
    public void menuDeselected(MenuEvent me)
    {
    }

    @Override
    public void menuCanceled(MenuEvent me)
    {
    }
}//WindowMenuAction class closed



//Window Restart action class
class WindowRestartAction implements ActionListener
{
        @Override
        public void actionPerformed(ActionEvent ae) {
            File_CloseAll_Action();
            dispose();
            count = 1;
            LookAndFeelAction.setBasicLookAndFeel();
        }
}//WindowRestartAction class closed



//******************************************************
// Window menu item action
//******************************************************
class Window_MenuItemsAction implements ActionListener
{
        @Override
        public void actionPerformed(ActionEvent ae) {

            String menutext=ae.getActionCommand().trim();

            if (_tabbedPane.getTabCount() > 0) {
                int tabcount = _tabbedPane.getTabCount();
                for (int i = 0; i < tabcount; i++) {
                    String title = _tabbedPane.getTitleAt(i).trim();
                    if (title.contains("*")) {
                        title = title.replace("*", "").trim();
                    }

                    if (title.equals(menutext)) {
                        _tabbedPane.setSelectedIndex(i);
                        setTitle("Edupad - [ " + _tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex()) + " ]");
                    }
                }
            }
        }

}//Window_MenuItemsAction class closed




    //********************************************************
    // class for defining actions of file menu items
    //********************************************************
    class File_MenuItemsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource() instanceof JMenuItem)
            {
                String item=evt.getActionCommand().trim();

                switch(item)
                {
                    case "New" : File_New_Action(); break;
                    case "Open" : File_Open_Action();break;
                    case "Save" : File_Save_Action();break;
                    case "Save As" : File_SaveAs_Action();break;
                    case "Save All" : File_SaveAll_Action();break;
                    case "Print" : File_Print_Action();break;
                    case "Close" : File_Close_Action();break;
                    case "Close All" : File_CloseAll_Action();break;
                    case"Open In System Editor" : File_OpenInSystemEditor_Action();break;
                    case "Exit" : File_Exit_Action();break;
                    
                }
            }
        }
    }//File_MenuItemsAction class closed
    class Help_ItemsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent help_evt)
        {
            if(help_evt.getSource()== help_feedback)
            {
                final String email = "sushiljangid69@gmail.com";
                try
                {
                    Desktop d = Desktop.getDesktop();
                    d.browse(new URI("mailto:"+email));
                }catch(Exception e2)
                {
                    e2.printStackTrace();
                }
            }
            if(help_evt.getSource()==help_manualguide || help_evt.getSource()==toolbar_help)
            {
               HelpContentsAction hca=new HelpContentsAction();
            }
            // adding Help -> About action here
            if(help_evt.getSource() == help_about)
            {
                final String message = "Designer and Programmer:Sushil Jangid\nStudent Team:Sushil Jangid,Rajaram Prajapat and Ajay Singh\n\t(Student of BCA Final Year)\n\tSession:2019-20";
                JOptionPane.showMessageDialog(null,message,"About Us",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }




    //********************************************************
    // class for defining actions of edit menu items
    //********************************************************
    class Edit_MenuItemsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (evt.getSource() instanceof JMenuItem)
            {
                String item = evt.getActionCommand().trim();
                switch (item)
                {
                    case "Cut" : Edit_Cut_Action();break;
                    case "Copy" : Edit_Copy_Action();break;
                    case "Paste" : Edit_Paste_Action();break;
                    case "GoTo" : Edit_GoTo_Action(); break;
                    case "Find": Edit_Find_Action();break;
                    case "Replace": Edit_Replace_Action();break;
                    case "Select All" : Edit_SelectAll_Action();break;
                    case "Date and Time" : Edit_DateAndTime_Action();break;
                    case "Upper Case" : Edit_ChangeCase_UpperCase_Action();break;
                    case "Lower Case" : Edit_ChangeCase_LowerCase_Action();break;
                    case "Sentence Case" : Edit_ChangeCase_SentenceCase_Action();break;
                    case "Next Document" : Edit_NextDocument_Action();break;
                    case "Previous Document" : Edit_PreviousDocument_Action();break;
                }
            }
        }
    }





    //********************************************************
    // class for defining actions of view menu items
    //********************************************************
    class View_MenuItemsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (evt.getSource() instanceof JMenuItem)
            {
                String item = evt.getActionCommand().trim();
                switch (item)
                {
                    case "Font" : View_Font_Action(); break;
                    case "Font Color" : View_ForeColor_Action();break;
                    case "Page Color" : View_BackColor_Action();break;
                    case "Top" : View_TabsAlignment_Action("top");break;
                    case "Bottom" : View_TabsAlignment_Action("bottom");break;
                    case "Left" : View_TabsAlignment_Action("left");break;
                    case "Right" : View_TabsAlignment_Action("right");break;
                    case "Java" : View_SetLookAndFeel_Action("java");break;
                    case "Motif" : View_SetLookAndFeel_Action("motif");break;
                    case "Nimbus" : View_SetLookAndFeel_Action("nimbus");break;
                    case "Windows" : View_SetLookAndFeel_Action("windows");break;
                    case "Windows Classic" : View_SetLookAndFeel_Action("windowsclassic");break;
                    case "Global Dark" : View_SetLookAndFeel_Action("globaldark");break;
                }
            }
            if(evt.getSource() instanceof JCheckBoxMenuItem)
            {
                int sel = _tabbedPane.getSelectedIndex();
                JTextArea txt = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
                if(evt.getSource()== view_wordwrap)
                {
                    if(view_wordwrap.isSelected())
                    {
                        txt.setLineWrap(true);
                    }
                    else
                    {
                        txt.setLineWrap(false);
                    }
                }
            }
        }
    }

    


    //********************************************************
    // class for defining actions of Run menu items
    //********************************************************
    class Run_MenuItemsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (evt.getSource() instanceof JMenuItem)
            {
                String item = evt.getActionCommand().trim();
                switch (item)
                {
                    case "Run" :
                        JDialog ra=new RunAction(isDarkTheme);
                        ra.setTitle("Run");
                        ra.setModal(true);
                        ra.setSize(450,200);
                        ra.setResizable(false);
                        ra.setLocation(getCenterPoints().x+100,getCenterPoints().y+80);
                        ra.setVisible(true);
                        break;

                    case "Run in Browser" :
                        if(_tabbedPane.getTabCount()>0)
                        {
                            int sel = _tabbedPane.getSelectedIndex();
                            String tabtext = _tabbedPane.getTitleAt(sel);
                        if((filenameLabel.getText().contains("\\") || filenameLabel.getText().contains("/")) && !tabtext.contains("*") && (filenameLabel.getText().contains(".xml") || filenameLabel.getText().contains(".html") || filenameLabel.getText().contains(".htm") || filenameLabel.getText().contains(".pdf")))
                            {
                                String file=filenameLabel.getText();
                                file=file.replace("\\", "/");
                                file="file:///"+file;
                                try
                                {
                                  Desktop dt=Desktop.getDesktop();
                                  dt.browse(new URI(file));
                                }
                                catch(URISyntaxException | IOException e) {}
                            }
                            else if((filenameLabel.getText().contains("\\") || filenameLabel.getText().contains("/")) && !tabtext.contains("*") && !(filenameLabel.getText().contains(".xml") || filenameLabel.getText().contains(".html") || filenameLabel.getText().contains(".htm") || filenameLabel.getText().contains(".pdf")))
                            {
                                JOptionPane.showMessageDialog(null,"File Extension should be .xml,.htm or .html, .pdf to run in browser","Error",JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"Please Save the file first","Error",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"!!No Document is Opened!!","Error",JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    case "Google Search" :
                        if(_tabbedPane.getTabCount()>0)
                        {
                            int sel = _tabbedPane.getSelectedIndex();
                            JTextArea ta = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
                            if(ta.getSelectedText()!=null)
                            {
                                String searchTerm = ta.getSelectedText().toString().trim();
                                String noSpaceStr = searchTerm.replaceAll("\\s", "+"); // using built in method to remove all spaces 
                                if(noSpaceStr.length()<=36)
                                {
                                    try 
                                    {
                                        String search = "#q="+noSpaceStr;
                                        String url = "http:////www.google.com//"+search;
                                        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                                    }
                                    catch (java.io.IOException ioex)
                                    {
                                        System.out.println(ioex.getMessage());
                                    }
                                }
                                else
                                {
                                    String message = "Can't Search\n"+"("+"Google only support 36 characters long query"+")"+"\n Please enter query less than 36 characters";
                                    JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            else
                            {
                                String message = "Please Select text to search\n";
                                JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"!!No Document is Opened!!","Error",JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                }
            }
        }
    }//Run_MenuItemsAction class closed




    //********************************************************
    // tool abr button action class
    //********************************************************
    class ToolBarButtonsAction implements ActionListener
    {
        String type="";
        public ToolBarButtonsAction(String s)
        {
            type=s;
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            switch(type)
            {
                case "new" : File_New_Action();break;
                case "open" : File_Open_Action();break;
                case "save" : File_Save_Action();break;
                case "saveas" : File_SaveAs_Action();break;
                case "cut" : Edit_Cut_Action();break;
                case "copy" : Edit_Copy_Action();break;
                case "paste" : Edit_Paste_Action();break;
                case "goto" : Edit_GoTo_Action();break;
                case "font" : View_Font_Action();break;
                case "screenshot" : 
                    try
                    {
                        Robot robot = new Robot();

                        robot.keyPress(KeyEvent.VK_PRINTSCREEN);
                        robot.keyRelease(KeyEvent.VK_PRINTSCREEN);

                    }
                    catch(Exception e222){System.out.println(e222);}
                break;
                case "calculator" :
                    try
                    {
                        Runtime.getRuntime().exec("calc");
                    }
                    catch(Exception except){}
                break;
            }
        }
    }




    //************************************************************
    // set items enable to false if tabcount=0 to menu items
    //************************************************************
    public class Menus_MenuListener implements MenuListener
    {
        JMenuItem[] list;

        Menus_MenuListener(JMenuItem[] lst)
        {
            list=lst;
        }

        @Override
        public void menuCanceled(MenuEvent ev){}
        @Override
        public void menuDeselected(MenuEvent ev){}
        @Override
        public void menuSelected(MenuEvent evt)
        {
            if(_tabbedPane.getTabCount()>0)
            {
                for(JMenuItem item : list)
                {
                    item.setEnabled(true);
                }
            }
            else
            {
                for (JMenuItem item : list)
                {
                    item.setEnabled(false);
                }
                
            }
        }
    }




    //********************************************************
    // showing popupMenu on textpane
    //********************************************************
    class TextPane_MouseAction extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent evt)
        {
            if(evt.isPopupTrigger())
            {
                _popupMenu.show(evt.getComponent(),evt.getX(),evt.getY());
            }
        }
        
    }




    //********************************************************
    // display row & col
    //********************************************************
    class CaretAction implements CaretListener
    {
        public int getRow(int pos,JTextArea textpane)
        {
            int rn=(pos==0) ? 1:0;
            try
            {
                int offs=pos;
                while(offs>0)
                {
                    offs=Utilities.getRowStart(textpane, offs)-1;
                    rn++;
                }
            }
            catch(BadLocationException e){ e.printStackTrace();}

            return rn;
        }

        public int getColumn(int pos,JTextArea textpane)
        {
            try
            {
                return pos-Utilities.getRowStart(textpane, pos)+1;
            }
            catch (BadLocationException e) {e.printStackTrace();  }

            return -1;
        }

        @Override
        public void caretUpdate(CaretEvent evt)
        {
            JTextArea textpane=(JTextArea)evt.getSource();
            int row = getRow(evt.getDot(), textpane);
            int col = getColumn(evt.getDot(), textpane);
            String text=textpane.getText();
            String words[]=text.split("\\s");
            //int character = text.length();
            //int word = words.length;
            rowLabel.setText("Row : "+row);
            colLabel.setText("Col : "+col);
            characterLabel.setText("Characters : "+text.length());
            wordLabel.setText("Words : "+words.length);
        }
    }
    
    /**
        * This class adds indent to the text written
    */
    /*
    public class NewLineFilter extends DocumentFilter
    {
        public void insertString(FilterBypass fb,int offs,String str,AttributeSet a)throws BadLocationException
        {
            if("\n".equals(str))
            {
                str = addWhiteSpace(fb.getDocument(),offs);
            }
            super.insertString(fb,offs,str,a);
        }
        public void replace(FilterBypass fb,int offs,int length,String str,AttributeSet a)
            throws BadLocationException
        {
            if("\n".equals(str))
            {
                str = addWhiteSpace(fb.getDocument(),offs);
            }
            super.replace(fb,offs,length,str,a);
        }
        private String addWhiteSpace(Document doc,int offset) throws BadLocationException
        {
            StringBuilder whiteSpace = new StringBuilder("\n");
            Element rootElement = doc.getDefaultRootElement();
            int line = rootElement.getElementIndex(offset);
            int i = rootElement.getElement(line).getStartOffset();
            while(true)
            {
                String temp = doc.getText(i,1);
                if(temp.equals(" ") || temp.equals("\t"))
                {
                    whiteSpace.append(temp);
                    i++;
                }
                else
                {
                    break;
                }
            }
            return whiteSpace.toString();
        }
    }
    */

    /////////////////////////////////////////////////////////////////////////////
    //**************************************************************************
    // functions
    //***************************************************************************
    /////////////////////////////////////////////////////////////////////////////


    //********************************************************
    // File -> New action
    //********************************************************
    public void File_New_Action()
    {
        //crerate textpane object
        JTextArea _textPane=new JTextArea();
        _textPane.setEditable(true);
        //AbstractDocument doc= (AbstractDocument)_textPane.getDocument();
        //doc.setDocumentFilter(new NewLineFilter());
        //_textPane.setMargin( new Insets(0,20,0,0) );
        
        //new LineNumbers(_textPane,this);
        //adding mouse listener to _textPane so that zoom in and zoom out action can occur in mouse wheel movement or touchpad's action
        _textPane.addMouseWheelListener(new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent mwe)
			{
				if (mwe.isControlDown())
				{
					if (mwe.getWheelRotation() < 0)
					{
						//System.out.println("mouse wheel Up");
						zoom(2);	//it will zoom out 
					} 
					else
					{
						//System.out.println("mouse wheel Down");
						zoom(0);	//it will zoom in
					}
    			}
		
			}
		});
        /********************************************************************************************************************
        JScrollPane jsp = new JScrollPane(_textPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JTextArea lines = new JTextArea("1  ");
		lines.setFont(new Font("Arial",Font.PLAIN,16));
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setForeground(Color.BLUE);
		lines.setEditable(false);
		//  Code to implement line numbers inside the JTextArea
		_textPane.getDocument().addDocumentListener(new DocumentListener()
		{
			public String getText()
			{
				int caretPosition = _textPane.getDocument().getLength();
				Element root = _textPane.getDocumentElement();
				String text = "1  " + System.getProperty("line.separator");
				for(int i = 2; i < root.getElementIndex(caretPosition) + 2; i++)
				{
					text += i + System.getProperty("line.separator");
				}
				return text;
			}
			@Override
			public void changedUpdate(DocumentEvent de)
			{
				lines.setText(getText()+"  ");
			}
			@Override
			public void insertUpdate(DocumentEvent de)
			{
				lines.setText(getText()+"  ");
			}
			@Override
			public void removeUpdate(DocumentEvent de)
			{
				lines.setText(getText()+"  ");
			}
		});
		//jsp.getViewport().add(ta);
		jsp.setRowHeaderView(lines);
        
        *//////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //for applying drag and drop feature to the JTextArea
        
        dropAction(_textPane);
        
        
        
        
        
        
        
        
        
        
        
        
        
        //for setting line wrap to JTextArea
        _textPane.setLineWrap(true);
        //for adding line numbers to frame
            
        _textPane.setFont(new Font("Calibri",Font.PLAIN,20));

        if(isDarkTheme)
        {
            _textPane.setBackground(new Color(10, 10, 20));
            _textPane.setForeground(new Color(250, 250, 250));
        }

        JScrollPane jsp=new JScrollPane(_textPane);
        // add key listener & Undoable edit listener to text pane
        _textPane.addKeyListener(new KeyTypedAction());
        _textPane.getDocument().addUndoableEditListener(_undoManager);
        //add tab to _tabbedPane with control textpane
        _tabbedPane.addTab("Document "+count+" ",jsp);
        //add caret listener & mouse listener to text pane
        _textPane.addCaretListener(new CaretAction());
        _textPane.addMouseListener(new TextPane_MouseAction());
        int index=_tabbedPane.getTabCount()-1;

        _tabbedPane.setSelectedIndex(index);

        // set save icon to added tab
        _tabbedPane.setIconAt(index, new ImageIcon(this.getClass().getResource("resources/save.png")));
        listModel.addElement("Document "+count+" ");
        _tabbedPane.setFont(applicationFont);

        _list.setSelectedIndex(index);
        _list.setFont(applicationFont);

        //change the title
        setTitle("Edupad - [ Document "+count+" ]");
        setFont(applicationFont);
        filenameLabel.setText("Document "+count);
        _textPane.requestFocus();
        count++;
        windowMenu.setVisible(true);
        view_wordwrap.setVisible(true);
        toolbarButtonStatus(true);
        

    }
    /*
        * this method accepts the drag and drop feature to the JTextArea
        @param _textPane JTextArea from the caller
    */
    public void dropAction(JTextArea _textPane)
    {
        DropTarget target=new DropTarget(_textPane,new DropTargetListener()
		{
            public void dragEnter(DropTargetDragEvent e)
            {
            }
            
            public void dragExit(DropTargetEvent e)
            {
            }
            
            public void dragOver(DropTargetDragEvent e)
            {
            }
            
            public void dropActionChanged(DropTargetDragEvent e)
            {
            
            }
            
            public void drop(DropTargetDropEvent e)
            {
                try
                {
                    // Accept the drop first, important!
                    e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    
                    // Get the files that are dropped as java.util.List
                    java.util.List list=(java.util.List) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    JTextArea _textPane=new JTextArea();
                    _textPane.setFont(new Font("Calibri",Font.PLAIN,20));
                    dropAction(_textPane);
                    // Now get the first file from the list,
                    for(int i=0;i<list.size();i++)
                    {
                        File files=(File)list.get(i);
                        String  filename = files.toString();
                        String file=filename;
                        if(filename.contains("\\"))
                        {
                            file = filename.substring(filename.lastIndexOf("\\") + 1);
                        }
                        else if(filename.contains("/"))
                        {
                            file = filename.substring(filename.lastIndexOf("/") + 1);
                        }

                        int count=_tabbedPane.getTabCount();
                        _textPane.read(new FileReader(files),null);
                        if (isDarkTheme)
                        {
                            _textPane.setBackground(new Color(10, 10, 20));
                            _textPane.setForeground(new Color(250, 250, 250));
                        }

                       JScrollPane jsp=new JScrollPane(_textPane);
                       _textPane.addKeyListener(new KeyTypedAction());
                        _textPane.getDocument().addUndoableEditListener(_undoManager);
                        _textPane.addCaretListener(new CaretAction());
                        _textPane.addMouseListener(new TextPane_MouseAction());
                       _tabbedPane.addTab(file,jsp);
                       _tabbedPane.setSelectedIndex(count);
                       _tabbedPane.setIconAt(count, new ImageIcon(this.getClass().getResource("resources/save.png")));
                       listModel.addElement(file);
                       _list.setSelectedIndex(count);

                       setTitle("Edupad - [ "+file+" ]");
                       filenameLabel.setText(filename);
                       filesHoldListModel.addElement(filename);
                       _textPane.addMouseWheelListener(new MouseWheelListener()
                        {
                            public void mouseWheelMoved(MouseWheelEvent mwe)
                            {
                                if (mwe.isControlDown())
                                {
                                    if (mwe.getWheelRotation() < 0)
                                    {
                                        //System.out.println("mouse wheel Up");
                                        zoom(2);	//it will zoom out 
                                    } 
                                    else
                                    {
                                        //System.out.println("mouse wheel Down");
                                        zoom(0);	//it will zoom in
                                    }
                                }
                        
                            }
                        });
                    }
                }catch(Exception ex){}
            }
        });
    }



    //********************************************************
    // File -> Open action
    //********************************************************
    public void File_Open_Action()
    {
        JFileChooser fd = new JFileChooser();
        if(isDarkTheme)
        {
            setFileChooserColors(fd,Color.WHITE,Color.BLACK);
        }
        fd.setMultiSelectionEnabled(true);
        File workingDirectory = new File(System.getProperty("user.dir"));
        fd.setCurrentDirectory(workingDirectory);
         fd.showOpenDialog(Edupad.this);
         if (fd.getSelectedFiles()!=null)
         {
            File[] files=fd.getSelectedFiles();
            for(File item : files)
            {
               String  filename = item.toString();
               String file=filename;
               if(filename.contains("\\")){
                   file = filename.substring(filename.lastIndexOf("\\") + 1);
               }
               else if(filename.contains("/")){
                   file = filename.substring(filename.lastIndexOf("/") + 1);
               }

               int count=_tabbedPane.getTabCount();

               JTextArea _textPane=new JTextArea();
               _textPane.setFont(new Font("Calibri",Font.PLAIN,20));

               if (isDarkTheme)
               {
                    _textPane.setBackground(new Color(10, 10, 20));
                    _textPane.setForeground(new Color(250, 250, 250));
                }

               JScrollPane jsp=new JScrollPane(_textPane);
               _textPane.addKeyListener(new KeyTypedAction());
                _textPane.getDocument().addUndoableEditListener(_undoManager);
                _textPane.addCaretListener(new CaretAction());
                _textPane.addMouseListener(new TextPane_MouseAction());
               _tabbedPane.addTab(file,jsp);
               _tabbedPane.setSelectedIndex(count);
               _tabbedPane.setIconAt(count, new ImageIcon(this.getClass().getResource("resources/save.png")));
               listModel.addElement(file);
               _list.setSelectedIndex(count);

               setTitle("Edupad - [ "+file+" ]");
               filenameLabel.setText(filename);
               filesHoldListModel.addElement(filename);

               BufferedReader d;
               StringBuffer sb = new StringBuffer();
               try
                {
                  d = new BufferedReader(new FileReader(filename));
                  String line;
                  while((line=d.readLine())!=null)
                           sb.append(line + "\n");
                           _textPane.setText(sb.toString());
                           windowMenu.setVisible(true);
                            view_wordwrap.setVisible(true);
                            toolbarButtonStatus(true);
                  d.close();
                }
               catch(FileNotFoundException fe)
                {
                   System.out.println("File not Found");
                }
                 catch(IOException ioe){}

                  _textPane.requestFocus();

               }
           }

    }




    //********************************************************
    // File -> Save action
    //********************************************************
    public void File_Save_Action()
    {
         if(_tabbedPane.getTabCount()>0)
         {
            String filename=filenameLabel.getText();
            int sel=_tabbedPane.getSelectedIndex();
            JTextArea textPane=(JTextArea)(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            if(filename.contains("\\")||filename.contains("/"))
            {
              File f=new File(filename);
              if(f.exists())
              {
                  try
                  {
                       DataOutputStream d = new DataOutputStream(new FileOutputStream(filename));
                       String line = textPane.getText();
                       d.writeBytes(line);
                       d.close();

                       String tabtext=_tabbedPane.getTitleAt(sel);
                       if(tabtext.contains("*"))
                       {
                           tabtext=tabtext.replace("*", "");
                           _tabbedPane.setTitleAt(sel, tabtext);
                           setTitle("Edupad - [ "+tabtext+" ]");
                           _tabbedPane.setIconAt(sel,new ImageIcon(this.getClass().getResource("resources/save.png")));
                       }

                  }
                 catch(Exception ex)
                  {
                        System.out.println("File not found");
                  }
                  textPane.requestFocus();
                }
           }

            else if(filename.contains("Document "))
            {
                int a=File_SaveAs_Action();
            }

         }
    }




    //********************************************************
    // File -> Save As action
    //********************************************************
    public int File_SaveAs_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            FileDialog fd = new FileDialog(new JFrame(), "Save As", FileDialog.SAVE);
            fd.setFilenameFilter(new ExtensionFilter("txt"));
            fd.setFile("Untitled.txt");//setting the default file name

            if(isDarkTheme)
            {
                fd.setForeground(Color.WHITE);
                fd.setBackground(Color.BLACK);
            }
            fd.setVisible(true);
            if (fd.getFile() != null)
            {
                String filename = fd.getDirectory() + fd.getFile();
                int sel = _tabbedPane.getSelectedIndex();
                JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
                try
                {
                    DataOutputStream d = new DataOutputStream(new FileOutputStream(filename));
                    String line = textPane.getText();
                    d.writeBytes(line);
                    d.close();

                    filesHoldListModel.addElement(filename);
                    filenameLabel.setText(filename);

                    String file = filename.substring(filename.lastIndexOf("\\") + 1);
                    _tabbedPane.setTitleAt(sel, file);

                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/save.png")));

                    setTitle("Edupad - [ " + file + " ]");

                }
                catch (Exception ex)
                {
                    System.out.println("File not found");
                }
                textPane.requestFocus();
                return 1;//1 is for file is saved

            }
            else
            {
                return 0;//0 is for file is not saved and user cancel the option
            }
        }
        else
        {
            return 0;
        }
    }
    //class for setting default file extension
    class ExtensionFilter implements FilenameFilter
    {
      String extension;
      public ExtensionFilter(String extension)
      {
        this.extension = "." + extension;
      }
      public boolean accept(File dir, String name)
      {
        return name.endsWith(extension);
      }
    }



    //********************************************************
    // File -> Save All action
    //********************************************************
    public void File_SaveAll_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int maxindex = _tabbedPane.getTabCount() - 1;
            for (int i = 0; i <= maxindex; i++)
            {
                _tabbedPane.setSelectedIndex(i);
                String filename = filenameLabel.getText();
                int sel = _tabbedPane.getSelectedIndex();
                JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
                if (filename.contains("\\")||filename.contains("/"))
                {
                    File f = new File(filename);
                    if (f.exists())
                    {
                        try
                        {
                            DataOutputStream d = new DataOutputStream(new FileOutputStream(filename));
                            String line = textPane.getText();
                            d.writeBytes(line);
                            d.close();

                            String tabtext = _tabbedPane.getTitleAt(sel);
                            if (tabtext.contains("*")) {
                                tabtext = tabtext.replace("*", "");
                                _tabbedPane.setTitleAt(sel, tabtext);
                                setTitle("Edupad - [ " + tabtext + " ]");
                                _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/save.png")));
                            }

                        }
                        catch (Exception ex)
                        {
                            System.out.println("File not found");
                        }
                        textPane.requestFocus();
                    }
                    
                }
                else
                {
                    int a=File_SaveAs_Action();
                }
            }
        }
    }
    
    public void File_Print_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea ta = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            
            try
			{
				ta.print();
			}catch(Exception evt)
			{
				JOptionPane.showMessageDialog(null, evt.getMessage());
			}
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No document to print","Error",JOptionPane.ERROR_MESSAGE);
        }
    }




    //********************************************************
    // File -> Close action
    //********************************************************
    public void File_Close_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            String tabtext = _tabbedPane.getTitleAt(sel);

            if (tabtext.contains("*"))
            {
                int n = JOptionPane.showConfirmDialog(null, "Do you want to save " + tabtext + " before close?",
                        "Save or Not", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                tabtext.replace("*", "");

                if (n == 0)//Yes option
                {
                    String filename = filenameLabel.getText();
                    JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

                    if (filename.contains("\\")||filename.contains("/"))    //means document is already existed
                    {
                        File_Save_Action();
                        //System.out.println("file is already");

                        _tabbedPane.remove(sel);
                        listModel.removeAllElements();

                        //adding all elements to list after removing the tab
                        for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                        {
                            String item = _tabbedPane.getTitleAt(i);
                            if (item.contains("*"))
                            {
                                item = item.replace("*", "").trim();
                            }

                            listModel.addElement(item);
                        }

                        _list.setSelectedIndex(_tabbedPane.getTabCount()-1);

                        rowLabel.setText("Row :");
                        colLabel.setText("Col :");
                        characterLabel.setText("Characters :");
                        wordLabel.setText("Words :");

                        if(_tabbedPane.getTabCount()==0)
                        {
                            setTitle("Edupad");
                            filenameLabel.setText("");
                            rowLabel.setText("Row :");
                            colLabel.setText("Col :");
                            characterLabel.setText("Characters :");
                            wordLabel.setText("Words :");
                            //setting the status of toolbar buttons
                            toolbarButtonStatus(false);
                            count=1;
                        }

                    }

                    else if (filename.contains("Document "))        //means file is new
                    {
                        int a=File_SaveAs_Action();
                        if(a==1)
                        {
                            //System.out.println("file is not already");

                            _tabbedPane.remove(sel);
                            listModel.removeAllElements();

                            //adding all elements to list after removing the tab
                            for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                            {
                                String item = _tabbedPane.getTitleAt(i);
                                if (item.contains("*"))
                                {
                                    item = item.replace("*", "").trim();
                                }

                                listModel.addElement(item);
                            }

                            _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                            rowLabel.setText("Row :");
                            colLabel.setText("Col :");
                            characterLabel.setText("Characters :");
                            wordLabel.setText("Words :");

                            if (_tabbedPane.getTabCount() == 0)
                            {
                                setTitle("Edupad");
                                filenameLabel.setText("");
                                rowLabel.setText("Row :");
                                colLabel.setText("Col :");
                                characterLabel.setText("Characters :");
                                wordLabel.setText("Words :");
                                windowMenu.setVisible(false);
                                view_wordwrap.setVisible(false);
                                toolbarButtonStatus(false);
                                count=1;
                            }
                        }
                    }

                }

                if (n == 1)//No option
                {
                    _tabbedPane.remove(sel);
                    listModel.removeAllElements();

                    //adding all elements to list after removing the tab
                    for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                    {
                        String item = _tabbedPane.getTitleAt(i);
                        if (item.contains("*"))
                        {
                            item = item.replace("*", "").trim();
                        }

                        listModel.addElement(item);
                    }

                    _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                    rowLabel.setText("Row :");
                    colLabel.setText("Col :");
                    characterLabel.setText("Characters :");
                    wordLabel.setText("Words :");
                    count--;

                    if (_tabbedPane.getTabCount() == 0)
                    {
                        setTitle("Edupad");
                        filenameLabel.setText("");
                        rowLabel.setText("Row :");
                        colLabel.setText("Col :");
                        characterLabel.setText("Characters :");
                        wordLabel.setText("Words :");
                        windowMenu.setVisible(false);
                        view_wordwrap.setVisible(false);
                        toolbarButtonStatus(false);
                        count=1;
                    }
                }
                if(n==JOptionPane.CANCEL_OPTION || n==-1)
                {
                    JOptionPane.getRootFrame().dispose();
                }
            }

            else
            {
                _tabbedPane.remove(sel);
                listModel.removeAllElements();

                //adding all elements to list after removing the tab
                for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                {
                    String item = _tabbedPane.getTitleAt(i);
                    if (item.contains("*"))
                    {
                        item = item.replace("*", "").trim();
                    }

                    listModel.addElement(item);
                }

                _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                rowLabel.setText("Row :");
                colLabel.setText("Col :");
                characterLabel.setText("Characters :");
                wordLabel.setText("Words :");
                count--;

                if (_tabbedPane.getTabCount() == 0)
                {
                    setTitle("Edupad");
                    filenameLabel.setText("");
                    rowLabel.setText("Row :");
                    colLabel.setText("Col :");
                    characterLabel.setText("Characters :");
                    wordLabel.setText("Words :");
                    windowMenu.setVisible(false);
                    view_wordwrap.setVisible(false);
                    toolbarButtonStatus(false);
                    count=1;
                }

            }
        }

        else
        {
            setTitle("Edupad");
            filenameLabel.setText("");
            rowLabel.setText("Row :");
            colLabel.setText("Col :");
            characterLabel.setText("Characters :");
            wordLabel.setText("Words :");
            windowMenu.setVisible(false);
            view_wordwrap.setVisible(false);
            toolbarButtonStatus(false);
            count=1;
        }
    }






    //********************************************************
    // File -> Close All action
    //********************************************************
    public void File_CloseAll_Action() throws IndexOutOfBoundsException
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            for(int j=0;j<_tabbedPane.getTabCount();j++)
            {
                _tabbedPane.setSelectedIndex(j);
                int sel=_tabbedPane.getSelectedIndex();
                String tabtext = _tabbedPane.getTitleAt(sel);

                if (tabtext.contains("*"))
                {
                    int n = JOptionPane.showConfirmDialog(null, "Do you want to save " + tabtext + " before close?",
                            "Save or Not", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                    tabtext.replace("*", "");

                    if (n == 0)//Yes option
                    {
                        String filename = filenameLabel.getText();
                        JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

                        if (filename.contains("\\")||filename.contains("/"))
                        {
                            File_Save_Action();

                            _tabbedPane.remove(sel);
                            listModel.removeAllElements();

                            //adding all elements to list after removing the tab
                            for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                            {
                                String item = _tabbedPane.getTitleAt(i);
                                if (item.contains("*"))
                                {
                                    item = item.replace("*", "").trim();
                                }

                                listModel.addElement(item);
                            }

                            _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                            File_CloseAll_Action();

                            rowLabel.setText("Row :");
                            colLabel.setText("Col :");
                            characterLabel.setText("Characters :");
                            wordLabel.setText("Words :");
                            count=1;
                            windowMenu.setVisible(false);
                            view_wordwrap.setVisible(false);
                            toolbarButtonStatus(false);
                        }
                        else if (filename.contains("Document "))
                        {
                            int a=File_SaveAs_Action();
                            if(a==1)
                            {
                                _tabbedPane.remove(sel);
                                listModel.removeAllElements();

                                //adding all elements to list after removing the tab
                                for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                                {
                                    String item = _tabbedPane.getTitleAt(i);
                                    if (item.contains("*"))
                                    {
                                        item = item.replace("*", "").trim();
                                    }

                                    listModel.addElement(item);
                                }

                                _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                                File_CloseAll_Action();

                                rowLabel.setText("Row :");
                                colLabel.setText("Col :");
                                characterLabel.setText("Characters :");
                                wordLabel.setText("Words :");
                                count=1;
                                windowMenu.setVisible(false);
                                view_wordwrap.setVisible(false);
                                toolbarButtonStatus(false);
                            }
                        }

                    }

                    if (n == 1)//No option
                    {
                        _tabbedPane.remove(sel);
                        listModel.removeAllElements();

                        //adding all elements to list after removing the tab
                        for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                        {
                            String item = _tabbedPane.getTitleAt(i);
                            if (item.contains("*"))
                            {
                                item = item.replace("*", "").trim();
                            }

                            listModel.addElement(item);
                        }

                        _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                        File_CloseAll_Action();

                        rowLabel.setText("Row :");
                        colLabel.setText("Col :");
                        characterLabel.setText("Characters :");
                        wordLabel.setText("Words :");
                        windowMenu.setVisible(false);
                        view_wordwrap.setVisible(false);
                        toolbarButtonStatus(false);
                    }
                    if(n==-1)// x button action of JOptionPane
                    {
                        JOptionPane.getRootFrame().dispose();
                        break;
                    }
                    if(n==JOptionPane.CANCEL_OPTION)
                    {
                        JOptionPane.getRootFrame().dispose();
                        break;
                    }
                }

                else
                {
                    _tabbedPane.remove(sel);
                    listModel.removeAllElements();

                    //adding all elements to list after removing the tab
                    for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                    {
                        String item = _tabbedPane.getTitleAt(i);
                        if (item.contains("*"))
                        {
                            item = item.replace("*", "").trim();
                        }

                        listModel.addElement(item);
                    }

                   // _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                    File_CloseAll_Action();

                    rowLabel.setText("Row :");
                    colLabel.setText("Col :");
                    characterLabel.setText("Characters :");
                    wordLabel.setText("Words :");
                    windowMenu.setVisible(false);
                    view_wordwrap.setVisible(false);
                    toolbarButtonStatus(false);
                }
            }
        }

        else
        {
            setTitle("Edupad");
            filenameLabel.setText("");

            rowLabel.setText("Row :");
            colLabel.setText("Col :");
            characterLabel.setText("Characters :");
            wordLabel.setText("Words :");
            windowMenu.setVisible(false);
            view_wordwrap.setVisible(false);
            toolbarButtonStatus(false);
        }
    }




    //********************************************************
    // File -> Open in System Editor
    //********************************************************
    public void File_OpenInSystemEditor_Action()
    {
        if(_tabbedPane.getTabCount()>0)
        {
            String filename=filenameLabel.getText();
            int sel = _tabbedPane.getSelectedIndex();
            String tabtext = _tabbedPane.getTitleAt(sel);
            
            if((filename.contains("\\")||filename.contains("/")) && !tabtext.contains("*") )
            {
                try
                {
                 Desktop d=Desktop.getDesktop();
                 d.open(new File(filename));
                }
                catch(Exception e){e.printStackTrace();}
            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,"Please save the file first","Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }




    //********************************************************
    // File -> Exit
    //********************************************************
    public void File_Exit_Action()
    {
        File_CloseAll_Action();
        if (_tabbedPane.getTabCount() == 0)
        {
            System.exit(0);
        }
    }




    //********************************************************
    // Edit -> Cut
    //********************************************************
    public void Edit_Cut_Action()
    {
        if(_tabbedPane.getTabCount()>0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            String selected_text = textPane.getSelectedText();
            StringSelection ss = new StringSelection(selected_text);
            clip.setContents(ss, ss);
            textPane.replaceSelection("");

            String tabtext = _tabbedPane.getTitleAt(sel);
            if (tabtext.contains("*"))
            {  }
            else
            {
                _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
            }
        }
    }



    //********************************************************
    // Edit -> Copy
    //********************************************************
    public void Edit_Copy_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            String selected_text = textPane.getSelectedText();
            StringSelection ss = new StringSelection(selected_text);
            clip.setContents(ss, ss);

        }
    }



    //********************************************************
    // Edit -> Paste
    //********************************************************
    public void Edit_Paste_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            Transferable cliptran = clip.getContents(Edupad.this);
            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);	
            if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) 
            {
                try
                {
                    String selected_text = (String) cliptran.getTransferData(DataFlavor.stringFlavor);
                    textPane.replaceSelection(selected_text);

                    // here you can direct use textPane.paste();

                    String tabtext = _tabbedPane.getTitleAt(sel);
                    if (tabtext.contains("*")) { }
                    else
                    {
                        _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                        _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                    }
                }
                catch (Exception exc)
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,"!!Only Simple Text can be pasted here!!","Error While Pasting",JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Nothing to Paste on Clipboard\n\tClipboard is Empty","Paste",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }




    //********************************************************
    // Edit -> GoTo
    //********************************************************
    public void Edit_GoTo_Action()
    {
        if(isDarkTheme)
        {
            UIManager.put("TextField.background",Color.BLACK);
        }
        if(_tabbedPane.getTabCount()>0)
        {
            do
            {
                try
                {
                    int sel = _tabbedPane.getSelectedIndex();
                    JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
                    String str = (String) JOptionPane.showInputDialog(null,"Enter Line number :  "+"(1 - "+getLineCount(textPane)+" )", "GoTo Line",JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if (str == null)
                    {
                        break;
                    }
                    int lineNumber = Integer.parseInt(str);
                    _lineCount = getLineCount(textPane);
                    if (lineNumber > _lineCount)
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null,"Line number out of range", "Error....",JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    textPane.setCaretPosition(0);
                    textPane.setCaretPosition(SetCursor(lineNumber,textPane));
                    return;
                }
                catch (Exception e) { }
            }
            while (true);
            
        }
    }

    int _lineCount;
    public int getLineCount(JTextArea textPane)
    {
        _lineCount = 0;
        Scanner scanner = new Scanner(textPane.getText());
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            _lineCount++;
        }
        return _lineCount;
    }

    public int SetCursor(int newlineno,JTextArea textPane)
    {
        int pos = 0;
        int i = 0;
        String line = "";
        Scanner scanner = new Scanner(textPane.getText());
        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            i++;
            if (newlineno > i)
            {
                pos = pos + line.length() + 1;
            }
        }
        return pos;
    }




    //********************************************************
    // Edit -> Find
    //********************************************************
    public void Edit_Find_Action()
    {
        int sel = _tabbedPane.getSelectedIndex();
        JTextArea ta = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
        if (_tabbedPane.getTabCount() > 0)
        {
            String s=null;
            boolean isDark=false;
            if(ta.getSelectedText()!=null)
            {
                s = ta.getSelectedText();
            }
            if(isDarkTheme)
            {
                isDark=true;
            }
            new Find(ta,s,isDark);
        }
    }




    //********************************************************
    // Edit -> Replace
    //********************************************************
    public void Edit_Replace_Action()
    {
       Edit_Find_Action();
    }


    //********************************************************
    // Edit -> Select All
    //********************************************************
    public void Edit_SelectAll_Action()
    {
        if(_tabbedPane.getTabCount()>0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            textPane.selectAll();
        }
    }
    
    //********************************************************
    // Edit -> Date and Time
    //********************************************************
    public void Edit_DateAndTime_Action()
    {
        if(_tabbedPane.getTabCount()>0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea ta = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            ta.setText(ta.getText() + DateFormat.getDateTimeInstance().format(new Date()));
            String tabtext=_tabbedPane.getTitleAt(sel);
            if(tabtext.contains("*")){}
            else
            {
                _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel)+"*");
                _tabbedPane.setIconAt(sel,new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
            }
        }
    }



    //********************************************************
    // Edit -> Change Case -> Upper Case
    //********************************************************
    public void Edit_ChangeCase_UpperCase_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            if(textPane.getSelectedText()!=null)
            {
               textPane.replaceSelection(textPane.getSelectedText().toUpperCase());

                String tabtext = _tabbedPane.getTitleAt(sel);
                if (tabtext.contains("*"))
                {  }
                else
                {
                    _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                }
            }
        }
    }



    //********************************************************
    // Edit -> Change Case -> Lower Case
    //********************************************************
    public void Edit_ChangeCase_LowerCase_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            if (textPane.getSelectedText() != null)
            {
                textPane.replaceSelection(textPane.getSelectedText().toLowerCase());

                String tabtext = _tabbedPane.getTitleAt(sel);
                if (tabtext.contains("*"))
                { }
                else
                {
                    _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                }
            }
        }
    }



    //********************************************************
    // Edit -> Change Case -> Sentence Case
    //********************************************************
    public void Edit_ChangeCase_SentenceCase_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            if (textPane.getSelectedText() != null)
            {
                String s=textPane.getSelectedText();
                char ch=s.charAt(0);
                String ss=String.valueOf(ch).toUpperCase();
                String str=s.substring(1);
                str=ss+str;
                textPane.replaceSelection(str);

                String tabtext = _tabbedPane.getTitleAt(sel);
                if (tabtext.contains("*")) { }
                else
                {
                    _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                }
            }
        }
    }



    //********************************************************
    // Edit -> Next Document
    //********************************************************
    public void Edit_NextDocument_Action() throws IndexOutOfBoundsException
    {
        if(_tabbedPane.getTabCount()>0)
        {
            int tabindex=_tabbedPane.getTabCount()-1;
            if(_tabbedPane.getSelectedIndex()==tabindex)
            { }
           else if(_tabbedPane.getSelectedIndex()<tabindex)
            {
                _tabbedPane.setSelectedIndex(_tabbedPane.getSelectedIndex()+1);
            }
        }
    }




    //********************************************************
    // Edit -> Previous Document
    //********************************************************
    public void Edit_PreviousDocument_Action() throws IndexOutOfBoundsException
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int tabcount = _tabbedPane.getTabCount();
            if(_tabbedPane.getSelectedIndex()==0){ }
            else
            {
                _tabbedPane.setSelectedIndex(_tabbedPane.getSelectedIndex()-1);
            }
        }
    }





    //********************************************************
    // View -> Font
    //********************************************************
    public void View_Font_Action()
    {
        int sel = _tabbedPane.getSelectedIndex();
        JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
        Font taFont = textPane.getFont();
        
        FontAction fontaction = new FontAction(textPane,taFont.getFontName(),taFont.getStyle(),taFont.getSize());;
        //System.out.println(taFont.getFontName());
        //System.out.println(taFont.getStyle());
        //System.out.println(taFont.getSize());
        if(_tabbedPane.getTabCount()>0)
        {
            JDialog fa = fontaction;
            fa.setTitle("Set Font");
            fa.setSize(450,600);
            fa.setModal(true);
            fa.setLocation(300,200);
            fa.setResizable(false);
            fa.setAlwaysOnTop(true);
            fa.setVisible(true);
        }
        fontSize = fontaction.getFontSize();        
    }



    //********************************************************
    // View -> Fore Color or Font Color
    //********************************************************
    public void View_ForeColor_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea ta = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            Color initialcolor = Color.BLACK;
			//color chooser Dialog Box
			Color color = JColorChooser.showDialog(this,"Font Color",initialcolor);
			//set foreground color of font
			ta.setForeground(color);
        }
    }




    //********************************************************
    // View -> Back Color or Page Color
    //********************************************************
    public void View_BackColor_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea ta = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            Color initialcolor = Color.WHITE;
			// color chooser Dialog Box
			Color color = JColorChooser.showDialog(this,"Page Color",initialcolor);
			// set Background color of the textarea (ta)
			ta.setBackground(color);
        }
    }



    //********************************************************
    // View -> Tabs alignment action
    //********************************************************
    public void View_TabsAlignment_Action(String type)
    {
        switch (type) 
		{
            case "top":
                _tabbedPane.setTabPlacement(JTabbedPane.TOP);
                ReplaceViewsFileNodeText("tabsAlignment", getNodeTextContent("tabsAlignment"), "Top");
                break;
            case "bottom":
                _tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
                ReplaceViewsFileNodeText("tabsAlignment", getNodeTextContent("tabsAlignment"), "Bottom");
                break;
            case "left":
                _tabbedPane.setTabPlacement(JTabbedPane.LEFT);
                ReplaceViewsFileNodeText("tabsAlignment", getNodeTextContent("tabsAlignment"), "Left");
                break;
            case "right":
                _tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
                ReplaceViewsFileNodeText("tabsAlignment", getNodeTextContent("tabsAlignment"), "Right");
                break;
        }
    }




    //********************************************************
    // View -> Document selector action
    //********************************************************
    public class View_DocumentSelector_Action implements ActionListener
    {
        JCheckBoxMenuItem jcbmi;
        public View_DocumentSelector_Action(JCheckBoxMenuItem jcm)
        {
            jcbmi=jcm;
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if(jcbmi.isSelected())
            {
                JTabbedPane jtb = (JTabbedPane) jsplit.getLeftComponent();
                jtb.setVisible(true);
                jsplit.setDividerLocation(210);

                ReplaceViewsFileNodeText("documentSelector","false","true");
            }
            else
            {
                JTabbedPane jtb = (JTabbedPane) jsplit.getLeftComponent();
                jtb.setVisible(false);
                ReplaceViewsFileNodeText("documentSelector","true","false");
            }
        }
    }

    //*******************************************************
    // View -> Zoom class and zoom method
    //******************************************************
    class Zoom implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource() instanceof JMenuItem)
            {
                String item=evt.getActionCommand().trim();

                switch(item)
                {
                    case "Zoom In" : 
                        zoom(0);
                     break;
                    case "Zoom Out" : 
                        zoom(2);
                    break;
                    case "Default Scale" : 
                        zoom(1);
                    break;
                    
                }
            }
        }
    }
    public void zoom(int n)
	{
        int sel = _tabbedPane.getSelectedIndex();
        JTextArea ta = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
		Font f = ta.getFont();
        int pre_size=f.getSize();
		if(n==0)//zoom in 
		{
			if(f.getSize()>=2)
			{
				Font f1 = new Font(f.getFontName(),f.getStyle(),f.getSize()-2);
				ta.setFont(f1);
				//lines.setFont(new Font("Arial",Font.PLAIN,f1.getSize()));
			}
		}
		else if(n==2)//zoom out
		{
			if(f.getSize()<=256)
			{
				Font f2 = new Font(f.getFontName(),f.getStyle(),f.getSize()+2);
				ta.setFont(f2);
				//lines.setFont(new Font("Arial",Font.PLAIN,f2.getSize()));
			}
		}
		else//defaultzoom
		{
			Font f3 = new Font(f.getFontName(),f.getStyle(),fontSize);
			ta.setFont(f3);
			//lines.setFont(new Font("Arial",Font.PLAIN,pre_size));		
		}
	}


    //********************************************************
    // View -> Tool bar action
    //********************************************************
    public class View_ToolBar_Action implements ActionListener
    {
        JCheckBoxMenuItem jcbmi;

        public View_ToolBar_Action(JCheckBoxMenuItem jcm)
        {
            jcbmi = jcm;
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (jcbmi.isSelected())
            {
                _toolbar.setVisible(true);
                ReplaceViewsFileNodeText("toolStrip", "false", "true");
            }
            else
            {
                _toolbar.setVisible(false);
                ReplaceViewsFileNodeText("toolStrip", "true", "false");
            }
        }
    }




    //********************************************************
    // View -> Status strip action
    //********************************************************
    public class View_StatusStrip_Action implements ActionListener
    {
        JCheckBoxMenuItem jcbmi;

        public View_StatusStrip_Action(JCheckBoxMenuItem jcm)
        {
            jcbmi = jcm;
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (jcbmi.isSelected())
            {
               statusBar.setVisible(true);
                ReplaceViewsFileNodeText("statusStrip", "false", "true");
            }
            else
            {
                statusBar.setVisible(false);
                ReplaceViewsFileNodeText("statusStrip", "true", "false");
            }
        }
    }



    //********************************************************
    // View -> set look and feels
    //********************************************************
    public void View_SetLookAndFeel_Action(String type)
    {
        switch (type)
		{
            case "java":
                {
                    int n = JOptionPane.showConfirmDialog(null, "Click OK to restart the application & to set Basic Java Metal look and feel theme",
                            "Set Basic Java Metal Look and feel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (n == 0)
                    {
                        File_CloseAll_Action();
                        dispose();
                        count = 1;
                        isDarkTheme=false;
                        ReplaceViewsFileNodeText("lookAndFeel", getNodeTextContent("lookAndFeel"), "Java");
                        LookAndFeelAction.setBasicLookAndFeel();
                    }       break;
                }
            case "motif":
            {
                int n = JOptionPane.showConfirmDialog(null, "Click OK to restart the application & to set Motif look and feel theme",
                        "Set Motif Look and feel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (n == 0)
                {
                    File_CloseAll_Action();
                    dispose();
                    count=1;
                    isDarkTheme=false;

                    ReplaceViewsFileNodeText("lookAndFeel", getNodeTextContent("lookAndFeel"), "Motif");

                    LookAndFeelAction.setMotifLookAndFeel();

                }       break;
            }
            case "nimbus":
            {
                int n = JOptionPane.showConfirmDialog(null, "Click OK to restart the application & to set Nimbus look and feel theme",
                        "Set Nimbus Look and feel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (n == 0)
                {
                    File_CloseAll_Action();
                    dispose();
                    count=1;
                    isDarkTheme=false;

                    ReplaceViewsFileNodeText("lookAndFeel", getNodeTextContent("lookAndFeel"), "Nimbus");

                    LookAndFeelAction.setNimbusLookAndFeel();

                }     break;
            }
            case "windows":
            {
                int n = JOptionPane.showConfirmDialog(null, "Click OK to restart the application & to set Windows look and feel theme",
                        "Set Windows Look and feel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (n == 0)
                {
                    File_CloseAll_Action();
                    dispose();
                    count=1;
                    isDarkTheme=false;

                    ReplaceViewsFileNodeText("lookAndFeel", getNodeTextContent("lookAndFeel"), "Windows");

                    LookAndFeelAction.setWindowsLookAndFeel();

                }     break;
            }
            case "windowsclassic":
            {
                int n = JOptionPane.showConfirmDialog(null, "Click OK to restart the application & to set Windows Classic look and feel theme",
                        "Set Windows Classic Look and feel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (n == 0)
                {
                    File_CloseAll_Action();
                    dispose();
                    count=1;
                    isDarkTheme=false;

                    ReplaceViewsFileNodeText("lookAndFeel", getNodeTextContent("lookAndFeel"), "WindowsClassic");

                    LookAndFeelAction.setWindowsClassicLookAndFeel();

                }    break;
            }
            case "globaldark":
            {
                int n = JOptionPane.showConfirmDialog(null, "Click OK to restart the application & to set Global Dark look and feel theme",
                        "Set Global Dark Look and feel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (n == 0)
				{
                    File_CloseAll_Action();
                    dispose();
                    count = 1;
                        isDarkTheme = true;
                        ReplaceViewsFileNodeText("lookAndFeel", getNodeTextContent("lookAndFeel"), "GlobalDark");
                        LookAndFeelAction.setGlobalDarkLookAndFeel();

				}
                    break;
			}
        }
    }



    //********************************************************
    // Edit -> Undo Action class
    //********************************************************
    public class PerformUndoAction extends AbstractAction
    {
		UndoManager _manager;

        public PerformUndoAction(UndoManager manager)
        {
            this._manager = manager;
        }

        public void actionPerformed(ActionEvent evt)
        {
            try
            {
				_manager.undo();
            }
            catch (CannotUndoException e)
            {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    //********************************************************
    // Edit -> Redo Action class
    //********************************************************
    public class PerformRedoAction extends AbstractAction
    {
		UndoManager _manager;
		public PerformRedoAction(UndoManager manager)
		{
			this._manager = manager;
		}

		public void actionPerformed(ActionEvent evt)
		{
			try
			{
				_manager.redo();
			}
			catch (CannotRedoException e)
			{
				Toolkit.getDefaultToolkit().beep();
			}
		}
    }




	//********************************************************
	// KeyTypedAction
	// if key is typed then add * to tab
	//********************************************************
	class KeyTypedAction implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent evt)
		{
			int keycode=evt.getKeyCode();
			boolean is_ControlDown=evt.isControlDown();
            if(keycode==KeyEvent.VK_A && is_ControlDown)
            {
                Edit_SelectAll_Action();
            }
			if(keycode==KeyEvent.VK_X && is_ControlDown)
			{
				Edit_Cut_Action();
			}
			if(keycode==KeyEvent.VK_C && is_ControlDown)
			{
				Edit_Copy_Action();
			}
			if(keycode==KeyEvent.VK_V && is_ControlDown)
			{
				int sel=_tabbedPane.getSelectedIndex();
				String tabtext = _tabbedPane.getTitleAt(sel);
				if (tabtext.contains("*")){}
				else
				{
					_tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
					_tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
				}
			}
            
			/*else if(keycode==KeyEvent.VK_S && is_ControlDown)
			{
				File_Save_Action();
			}*/
			
		}

		@Override
		public void keyReleased(KeyEvent evt)
        {
            
        }

		@Override
		public void keyTyped(KeyEvent evt)
		{
			if(!evt.isControlDown())
			{
				int sel=_tabbedPane.getSelectedIndex();
				String tabtext=_tabbedPane.getTitleAt(sel);
				if(tabtext.contains("*")){}
				else
				{
					_tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel)+"*");
					_tabbedPane.setIconAt(sel,new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
				}
			}
		}
	}

    //*********************************************************
    //
    //*********************************************************
    public void toolbarButtonStatus(boolean b)
    {
        if(b)
        {
           toolbar_copy.setVisible(true);
           toolbar_cut.setVisible(true);
           toolbar_font.setVisible(true);
           toolbar_goto.setVisible(true);
           toolbar_paste.setVisible(true);
           toolbar_save.setVisible(true);
           toolbar_saveas.setVisible(true);
        }
        else
        {
            toolbar_copy.setVisible(false);
           toolbar_cut.setVisible(false);
           toolbar_font.setVisible(false);
           toolbar_goto.setVisible(false);
           toolbar_paste.setVisible(false);
           toolbar_save.setVisible(false);
           toolbar_saveas.setVisible(false);
        }
    }


	//********************************************************
	// actions when frame is loading & closing
	//********************************************************
	class Load_Close_Frame_Action extends WindowAdapter
	{
		@Override
		public void windowOpened(WindowEvent evt)
		{
			File_New_Action();
		}

		@Override
		public void windowClosing(WindowEvent evt)
		{
			File_CloseAll_Action();
			if(_tabbedPane.getTabCount() == 0)
			{
				System.exit(0);
			}
		}
	}




	//********************************************************
	// select tab from list after clicking on item in the list
	//********************************************************
	class SelectTabFromListItem implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent evt)
		{
			if(_list.getSelectedValue()!=null)
			{
				String list_item=_list.getSelectedValue().toString().trim();

				if(_tabbedPane.getTabCount() >0)
				{
					int tabcount=_tabbedPane.getTabCount();
					for(int i=0;i<tabcount;i++)
					{
						String title=_tabbedPane.getTitleAt(i).trim();
						if (title.contains("*"))
						{
							title = title.replace("*", "").trim();
						}

						if(title.equals(list_item))
						{
							_tabbedPane.setSelectedIndex(i);
							setTitle("Edupad - [ "+_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex())+" ]");
						}
					}
				}
			}
		}
	}


	//********************************************************
	// selected tab changed
	//********************************************************
	class TabChanged implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent evt)
		{
			if(_tabbedPane.getTabCount()>0)
			{
				Object[] files=filesHoldListModel.toArray();
				String tabtext=_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex()).trim();
				if(tabtext.contains("*"))
				{
					tabtext=tabtext.replace("*", "");
				}
				for(Object filename : files)
				{
					String file=filename.toString().substring(filename.toString().lastIndexOf("\\")+1);
					if(file.equals(tabtext))
					{
					filenameLabel.setText(filename.toString());
					setTitle("Edupad - [ "+_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex())+" ]");
					}
                    
				}
				if(tabtext.contains("Document "))
				{
					filenameLabel.setText(tabtext);
					setTitle("Edupad - [ "+_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex())+" ]");
				}
                _list.setSelectedIndex(_tabbedPane.getSelectedIndex()); //for selecting the document list item as tab is selected
			}
		}
	}




	/**
        * writing contents to files/viewsfile.xml file
        @param documentSelector String which takes the document selector action ie. show or hide
        @param lookFeel String which takes the name of look and feel selected
        @param statusStrip String which takes the status bar action ie. show or hide
        @param tabsAlign String which takes the selected document alignment name ie.top,bottom,left,right
        @param toolStrip String which takes the too bar strip action ie. show or hide
	*/
	void WriteXMLFile_ViewContents(String documentSelector,String lookFeel,String statusStrip,String tabsAlign,String toolStrip)
	{
		Views views = new Views();
		views.setDocumentSelector(documentSelector);
		views.setLookAndFeel(lookFeel);
		views.setStatusStrip(statusStrip);
		views.setTabsAlignment(tabsAlign);
		views.setToolStrip(toolStrip);

		try
		{

			File file = new File("files/viewsfile.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Views.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//writing to a file
			marshaller.marshal(views, file);

		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}
	}



	/**
        * read files/viewsfile.xml file & replace its contents with newValue
        @param notetag String Class object used for taking the name of node
        @param oldValue String Class object used for taking old value of node
        @param newValue String Class object userd for taking new value for the node which is selected by user in application
	*/
	void ReplaceViewsFileNodeText(String nodetag,String oldValue,String newValue)
	{
		try 
		{
			File fXmlFile = new File("files/viewsfile.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("views");

			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);
				Element eElement = (Element) nNode;
				if(eElement.getElementsByTagName(nodetag).item(0).getTextContent().equals(oldValue))
				{
					eElement.getElementsByTagName(nodetag).item(0).setTextContent(newValue);
				}
				//writing all contents to a file
				WriteXMLFile_ViewContents(eElement.getElementsByTagName("documentSelector").item(0).getTextContent(),
				eElement.getElementsByTagName("lookAndFeel").item(0).getTextContent(),
				eElement.getElementsByTagName("statusStrip").item(0).getTextContent(),
				eElement.getElementsByTagName("tabsAlignment").item(0).getTextContent(),
				eElement.getElementsByTagName("toolStrip").item(0).getTextContent());
			}
		}
		catch (Exception e)
		{
		  e.printStackTrace();
		}
	}
	
	/**
        * returns content of node
        @param nodetag String Class object which takes the name of the node 
        @return content text content of node
	*/
	public static String getNodeTextContent(String nodetag)
	{
		String content="";
		try 
		{
			File fXmlFile = new File("files/viewsfile.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("views");
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);
				Element eElement = (Element) nNode;
				content = eElement.getElementsByTagName(nodetag).item(0).getTextContent();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return content;
	}
    /**
        * sets font for all swing components
        @param f FontUIResource Class object which sets the font for all components
    */
    public static void setUIFont (javax.swing.plaf.FontUIResource f)
    {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements())
        {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value instanceof javax.swing.plaf.FontUIResource)
          {
            UIManager.put (key, f);
            UIManager.put("OptionPane.messageFont",f);
          }
        }
    }
    /**
        * sets the foreground and background colors for JFileChooser object
        @param jfc JFileChooser object
        @param f foreground Color object
        @param b background Color object
    */
    public void setFileChooserColors(JFileChooser jfc, Color f, Color b)
    {

        for (int index1 = 0; index1 < jfc.getAccessibleContext().getAccessibleChildrenCount(); index1++)
        {
            jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleComponent().setForeground(f);
            jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleComponent().setBackground(b);
            for (int index2 = 0; index2 < jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChildrenCount(); index2++)
            {
                jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleComponent().setForeground(f);
                jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleComponent().setBackground(b);
                for (int index3 = 0; index3 < jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChildrenCount(); index3++)
                {
                    jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleComponent().setBackground(b);
                    jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleComponent().setForeground(f);
                    for (int index4 = 0; index4 < jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChildrenCount(); index4++)
                    {
                        jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleComponent().setBackground(b);
                        jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleComponent().setForeground(f);
                        for (int index5 = 0; index5 < jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChildrenCount(); index5++)
                        {
                            jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleComponent().setBackground(b);
                            jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleComponent().setForeground(f);
                            for (int index6 = 0; index6 < jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleChildrenCount(); index6++)
                            {                             
                                jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleChild(index6).getAccessibleContext().getAccessibleComponent().setBackground(b);
                                jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleChild(index6).getAccessibleContext().getAccessibleComponent().setForeground(f);
                                for (int index7 = 0; index7 < jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleChild(index6).getAccessibleContext().getAccessibleChildrenCount(); index7++)
                                {
                                    jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleChild(index6).getAccessibleContext().getAccessibleChild(index7).getAccessibleContext().getAccessibleComponent().setBackground(b);
                                    jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleChild(index6).getAccessibleContext().getAccessibleChild(index7).getAccessibleContext().getAccessibleComponent().setForeground(f);
                                    for (int index8 = 0; index8 < jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleChild(index6).getAccessibleContext().getAccessibleChild(index7).getAccessibleContext().getAccessibleChildrenCount(); index8++)
                                    {
                                        jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleChild(index6).getAccessibleContext().getAccessibleChild(index7).getAccessibleContext().getAccessibleChild(index8).getAccessibleContext().getAccessibleComponent().setBackground(b);
                                        jfc.getAccessibleContext().getAccessibleChild(index1).getAccessibleContext().getAccessibleChild(index2).getAccessibleContext().getAccessibleChild(index3).getAccessibleContext().getAccessibleChild(index4).getAccessibleContext().getAccessibleChild(index5).getAccessibleContext().getAccessibleChild(index6).getAccessibleContext().getAccessibleChild(index7).getAccessibleContext().getAccessibleChild(index8).getAccessibleContext().getAccessibleComponent().setForeground(f);                                       
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
	/**
        * main method which starts the application and used by Splash.java after showing splash screen
	*/
    public static void start()
    {
        //start application with theme by reading nodetag lookAndFeel from file viewsfile.xml
        String themetype = getNodeTextContent("lookAndFeel");
        switch(themetype)
        {
           case "Java" :
               isDarkTheme = false;
               LookAndFeelAction.setBasicLookAndFeel();
               break;

            case "Motif":
               isDarkTheme = false;
               LookAndFeelAction.setMotifLookAndFeel();
               break;

            case "Nimbus":
               isDarkTheme = false;
               LookAndFeelAction.setNimbusLookAndFeel();
               break;

            case "Windows":
               isDarkTheme = false;
               LookAndFeelAction.setWindowsLookAndFeel();
               break;

            case "WindowsClassic":
               isDarkTheme = false;
               LookAndFeelAction.setWindowsClassicLookAndFeel();
               break;

            case "GlobalDark":
               isDarkTheme = true;
               LookAndFeelAction.setGlobalDarkLookAndFeel();
               break;
        }
    }
}
