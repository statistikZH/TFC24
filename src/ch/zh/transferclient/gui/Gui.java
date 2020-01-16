/*
 * Copyright 2018-2019 Statistisches Amt des Kantons Zürich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.zh.transferclient.gui;

import java.io.*;
import java.util.Locale;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import ch.zh.transferclient.controller.*;
import ch.zh.transferclient.main.*;
import ch.zh.transferclient.properties.*;
import ch.zh.transferclient.properties.Properties.Language;

/**
 * This class is used to produce a graphical user interface.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Gui extends JFrame
    
    {
    
    /** A unique serial version identifier. */
    private static final long            serialVersionUID = 1L;
    
    /** The width of the dialog. */
    public static final int              WIDTH            = 1000;
    
    /** The height of the dialog. */
    public static final int              HEIGHT           = 700;
    
    // --------------//
    // GUI-Elemente //
    // --------------//
    /**
     * The panel which serves as a content pane for the frame of the application.
     */
    private final JPanel                 panel            = new JPanel();
    /** The menu bar. */
    private final JMenuBar               menubar          = new JMenuBar();
    /** The menu. */
    private final JMenu                  menu             = new JMenu(Labels.get("GUI_MENU_MENUE"));
    /** The menu item used for showing contact details for support. */
    private final JMenuItem              menuitem0        = new JMenuItem(Labels.get("GUI_MENU_SUPPORT"));
    /** The menu item used for terminating the application. */
    private final JMenuItem              menuitem1        = new JMenuItem(Labels.get("GUI_MENU_TERMINATE"));
    
    // Auswahl des Ordners
    /** The file chooser used for selecting the result file folder. */
    private final JFileChooser           fc_folder        = new JFileChooser();
    /** The text field that shows the selected result file folder. */
    private final JTextField             textfield_folder;
    /** The button for selecting the result file folder. */
    private final JButton                button_folder;
    
    // Steuerung
    /** The label which indicates the status of the transfer-client. */
    private final JLabel                 label_status;
    /** The button which activates the transfer-client. */
    private final JButton                button_activation;
    /** The button which auto-activates the transfer-client. */
    private final JButton                button_autoactivation;
    /** The button which deactivates the transfer-client. */
    private final JButton                button_deactivation;
    /** The combo box for selecting the target time for the auto-activation. */
    private final JComboBox<String>      combobox_autoactivation;
    
    // Protokoll
    
    /**
     * The table which shows the protocol of the transfers in a summarized manner.
     */
    private final JTable                 table_protocol_overview;                                           // date of
                                                                                                            // introduction:
                                                                                                            // 2019-11-06
    
    /** The table which shows the protocol of the transfers. */
    private final JTable                 table_protocol_detail;
    
    // Dialoge
    
    // Zur Konstruktion der folgenden Dialoge ist eine Referenz auf das GUI-Objekt
    // notwendig.
    // Diese Objekte werden darum erst nach der Konstruktion GUI-Objekts ueber
    // SETTER-Methoden in der Factory-Methode gesetzt (vgl. weiter unten).
    
    /**
     * The dialog which is shown when the user activates the transfer-client without having selected the folder for the
     * result files.
     */
    private DialogDir                    dialog_dir;
    /** The dialog which is shown when an error occurs during the file moving process. */
    private DialogFileProcessingError    dialog_fileprocessingerror;
    /** The dialog which is shown when a receipt file is not well-formed. */
    private DialogReceiptNotWellFormed   dialog_receiptnotwellformed;
    /** The dialog which shows the contact details for support. */
    private DialogSupport                dialog_support;
    /** The dialog which can be shown when the transfer-client will be deactivated. */
    private DialogWaitingForDeactivation dialog_waitingfordeactivation;
    
    // --------------------------//
    // Zugriffsmethoden: GETTERS //
    // --------------------------//
    // @formatter:off
    
    // Menue 
    /** @return The reference to the menu bar. */
    public JMenuBar get_menubar()                                        {return this.menubar;}
        
    /** @return The reference to the menu. */
    public JMenu get_menu()                                              {return this.menu;}
        
    /** @return The reference to the support menu item. */
    public JMenuItem get_menuitem0()                                     {return this.menuitem0;}
        
    /** @return The reference to the terminate menu item. */
    public JMenuItem get_menuitem1()                                     {return this.menuitem1;}
        
    // Folder
    /**
     * Shows the open dialog.
     * @return The return state of the filechooser on popdown.
     */
    public int show_opendialog_fc_folder()                               {return this.fc_folder.showOpenDialog(this);}
        
    /** @return The selected file. */
    public File get_selectedfile_fc_folder()                             {return this.fc_folder.getSelectedFile();}
        
    /** @return The button for selecting the result files folder. */
    public JButton get_button_folder()                                   {return this.button_folder;}
        
    /** @return The button for selecting the result files folder. */
    public JTextField get_textfield_folder()                             {return this.textfield_folder;}
        
    // Steuerung 
    /** @return Table label status. */
    public JLabel get_label_status()                                     {return this.label_status;}
        
    /** @return Activation button. */
    public JButton get_button_activation()                               {return this.button_activation;}
        
    /** @return Deactivation button. */
    public JButton get_button_deactivation()                             {return this.button_deactivation;}
        
    /** @return Autoactivation button. */
    public JButton get_button_autoactivation()                           {return this.button_autoactivation;}
        
    /** @return Combobox 'Autoactivation'. */
    public JComboBox<String> get_combobox_autoactivation()               {return this.combobox_autoactivation;}
        
    /** @return Table Overview Protocol. */
    public JTable get_table_protocol_overview()                          {return this.table_protocol_overview;}
        
    /** @return Table Detail Protocol. */
    public JTable get_table_protocol_detail()                            {return this.table_protocol_detail;}
        
    // Dialoge 
    /** @return Dialog dor directory. */
    public DialogDir get_dialog_dir()                                           {return this.dialog_dir;}
        
    /** @return Dialog for informing about file processing errors. */
    public DialogFileProcessingError get_dialog_fileprocessingerror()           {return this.dialog_fileprocessingerror;}
        
    /** @return Dialog for informing about the fact that the receipt is not well formed. */
    public DialogReceiptNotWellFormed get_dialog_receiptnotwellformed()         {return this.dialog_receiptnotwellformed;}
        
    /** @return Dialog for informing about support services. */
    public DialogSupport get_dialog_support()                                   {return this.dialog_support;}
        
    /** @return Dialog for waiting for deactivation. */
    public DialogWaitingForDeactivation get_dialog_waitingfordeactivation()     {return this.dialog_waitingfordeactivation;}
    
    // -------------------------------------------------------------------------//
    // Zugriffsmethoden: SETTERS
    // Da fuer die Konstruktion der Dialoge eine Referenz auf das GUI-Objekt
    // gebraucht wird, werden diese erst nach der Konstruktion
    // in der Factory-Methode mit den folgenden Setter-Methoden hinzugefuegt.
    // -------------------------------------------------------------------------//
    /** @param dialog Dialog "Directory" to be used. */
    private void set_dialog_dir(DialogDir dialog)                                        {this.dialog_dir = dialog;}
    /** @param dialog Dialog "File Processing Error" to be used. */    
    private void set_dialog_fileprocessingerror(DialogFileProcessingError dialog)        {this.dialog_fileprocessingerror = dialog;}
    /** @param dialog Dialog "Receipt not well formed" to be used. */  
    private void set_dialog_receiptnotwellformed(DialogReceiptNotWellFormed dialog)      {this.dialog_receiptnotwellformed = dialog;}
    /** @param dialog Dialog "Support" to be used. */
    private void set_dialog_support(DialogSupport dialog)                                {this.dialog_support = dialog;}
    /** @param dialog Dialog "Waiting for Deactivation" to be used. */
    private void set_dialog_waitingfordeactivation(DialogWaitingForDeactivation dialog)  {this.dialog_waitingfordeactivation = dialog;}
    
    // @formatter:on
    
    /**
     * Constructs a Gui object.
     * 
     * The constructor is private because GUI objects are only offered by the factory method of the GUI class. Why? See
     * the explanations to the factory method below.
     * 
     * @param properties Reference of properties object.
     * 
     */
    private Gui(Properties properties)
        
        {
        
        super(Labels.get("GUI_JFRAME_TITLE") + " " + Conf.VERSION);
        
        // -----------------------------------------
        // Icon, Frame-Zentrierung, Exit-Operation
        // -----------------------------------------
        this.setIconImage(Conf.IMAGE_FOR_ICON);
        
        // Frame-Zentrierung
        this.setSize(WIDTH, HEIGHT);
        Dimension frameSize  = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int       x          = (screenSize.width - frameSize.width) / 2;
        int       y          = (screenSize.height - frameSize.height) / 2;
        this.setLocation(x, y);
        
        // Exit-Operation definieren
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        this.panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // --------------------
        // Einrichtung des GUI
        // --------------------
        final Border GREYLINE = BorderFactory.createStrokeBorder(new BasicStroke(3.0f), new Color(173, 173, 173));
        
        // -------------
        // Menue-Balken
        // -------------
        this.menu.add(menuitem0);
        this.menu.add(menuitem1);
        this.menubar.add(menu);
        this.setJMenuBar(menubar);
        
        // ------
        // Title
        // ------
        JPanel panel_title = new JPanel();
        panel_title.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        JLabel label_titel_bild = new JLabel(Conf.IMAGE_ICON_LOGO);
        
        panel_title.add(label_titel_bild);
        
        JLabel label_titel_text = new JLabel(Labels.get("GUI_TITLE"));
        label_titel_text.setFont(new Font("Sans Serif", Font.BOLD, 26));
        panel_title.add(label_titel_text);
        
        this.panel.add(panel_title);
        
        // -------
        // Folder
        // -------
        // JPanel folder_panel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        // JPanel folder_panel = new JPanel(new GridLayout(2,1));
        JPanel folder_panel = new JPanel();
        folder_panel.setLayout(new javax.swing.BoxLayout(folder_panel, javax.swing.BoxLayout.Y_AXIS));
        
        // Textfield
        JTextField jtf = new JTextField("", 50);
        jtf.setEditable(false);
        jtf.setBackground(Color.WHITE);
        jtf.setText(properties.get_folder_results());
        JPanel jp_textfield_folder = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        jp_textfield_folder.add(jtf);
        folder_panel.add(jp_textfield_folder);
        this.textfield_folder = jtf;
        
        // Button
        JButton jb               = new JButton(Labels.get("GUI_BUTTON_FOLDER"));
        JPanel  jp_button_folder = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        jp_button_folder.add(jb);
        folder_panel.add(jp_button_folder);
        this.button_folder = jb;
        
        TitledBorder tb_folder = new TitledBorder(Labels.get("GUI_TITLEDBORDER_FOLDER"));
        tb_folder.setBorder(GREYLINE);
        
        // folder_panel.setBorder(new CompoundBorder(tb_folder,new
        // EmptyBorder(20,20,40,20)));
        folder_panel.setBorder(new CompoundBorder(tb_folder, new EmptyBorder(20, 20, 20, 20)));
        this.panel.add(folder_panel);
        
        // Zwischenraum
        this.panel.add(new JLabel(""));
        
        // ----------
        // Steuerung
        // ----------
        Border  RAND                  = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        
        JPanel  panel_buttons         = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JButton button_activation     = new JButton(Labels.get("GUI_BUTTON_ACTIVATION"));
        JButton button_autoactivation = new JButton(Labels.get("GUI_BUTTON_AUTOACTIVATION"));
        JButton button_deactivation   = new JButton(Labels.get("GUI_BUTTON_DEACTIVATION"));
        
        button_activation.setPreferredSize(new Dimension(140, 25));
        button_autoactivation.setPreferredSize(new Dimension(140, 25));
        button_deactivation.setPreferredSize(new Dimension(140, 25));
        
        // Activation
        JPanel jp_activation = new JPanel();
        jp_activation.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        jp_activation.setPreferredSize(new Dimension(170, 40));
        jp_activation.setBorder(RAND);
        jp_activation.add(button_activation);
        panel_buttons.add(jp_activation);
        this.button_activation = button_activation;
        
        // Autoactivation
        JPanel   jp_autoactivation = new JPanel();
        String[] combobox_werte    = new String[Conf.AUTOACTIVATION_TIMES.size()];
        String   part1             = Labels.get("GUI_COMBOBOX_AUTOACTIVATION_PART_1");
        String   part2             = Labels.get("GUI_COMBOBOX_AUTOACTIVATION_PART_2");
        for (int i = 0; i < Conf.AUTOACTIVATION_TIMES.size(); i++)
            {
            combobox_werte[i] = part1 + " " + Conf.AUTOACTIVATION_TIMES.get(i) + " " + part2;
            }
            
        JComboBox<String> combobox_autoactivation = FactoryComboBoxes.get_combobox(combobox_werte);
        combobox_autoactivation.setPreferredSize(new Dimension(110, 25));
        combobox_autoactivation.setSelectedItem(part1 + " " + properties.get_target_time() + " " + part2);
        
        jp_autoactivation.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        jp_autoactivation.setPreferredSize(new Dimension(350, 40));
        jp_autoactivation.setBorder(RAND);
        jp_autoactivation.add(button_autoactivation);
        jp_autoactivation.add(combobox_autoactivation);
        panel_buttons.add(jp_autoactivation);
        this.button_autoactivation   = button_autoactivation;
        this.combobox_autoactivation = combobox_autoactivation;
        
        // Deactivation
        JPanel jp_deactivation = new JPanel();
        jp_deactivation.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        jp_deactivation.setPreferredSize(new Dimension(170, 40));
        jp_deactivation.add(button_deactivation);
        jp_deactivation.setBorder(RAND);
        panel_buttons.add(jp_deactivation);
        this.button_deactivation = button_deactivation;
        
        // Status
        JLabel label_status = new JLabel("");
        JPanel panel_status = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
        this.label_status = label_status;
        
        // Steuerungsfeld
        JPanel steuerungsfeld = new JPanel();
        steuerungsfeld.setLayout(new BoxLayout(steuerungsfeld, BoxLayout.Y_AXIS));
        panel_status.add(label_status);
        steuerungsfeld.add(panel_status);
        steuerungsfeld.add(panel_buttons);
        
        TitledBorder tb_steuerungsfeld = new TitledBorder(Labels.get("GUI_TITLEDBORDER_CONTROL"));
        tb_steuerungsfeld.setBorder(GREYLINE);
        
        steuerungsfeld.setBorder(new CompoundBorder(tb_steuerungsfeld, new EmptyBorder(20, 20, 20, 20)));
        
        this.panel.add(steuerungsfeld);
        
        // Zwischenraum
        this.panel.add(new JLabel(""));
        
        // ------------------
        // Protocol Overview
        // ------------------
        JTable            table_protocol_overview = FactoryTableProtocol_Overview.get_table(properties);
        DefaultTableModel model_overview          = (DefaultTableModel) table_protocol_overview.getModel();
        model_overview.getDataVector().removeAllElements();
        table_protocol_overview.setVisible(true);
        JScrollPane scroll_table_protocol_overview = new JScrollPane(table_protocol_overview, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.table_protocol_overview = table_protocol_overview;
        
        // -----------------
        // Protocol Details
        // -----------------
        JTable            table_protocol_detail = FactoryTableProtocol_Detail.get_table(properties);
        DefaultTableModel model                 = (DefaultTableModel) table_protocol_detail.getModel();
        model.getDataVector().removeAllElements();
        table_protocol_detail.setVisible(true);
        JScrollPane scroll_table_protocol = new JScrollPane(table_protocol_detail, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.table_protocol_detail = table_protocol_detail;
        
        // -----------------
        // Protocols Border
        // -----------------
        TitledBorder tb_protokoll = new TitledBorder(Labels.get("GUI_TITLEDBORDER_PROTOCOL"));
        tb_protokoll.setBorder(GREYLINE);
        // Im Zusammenhang mit der Einfuehrung der tabbedPane wurde vor die
        // folgenden drei Zeilen am 2019-11-06 Koemmntarzeichen gessetzt.
        // CompoundBorder cb = new CompoundBorder(tb_protokoll,new
        // EmptyBorder(20,20,20,20));
        // scroll_table_protocol.setBorder(cb);
        // this.panel.add(scroll_table_protocol);
        CompoundBorder cb         = new CompoundBorder(tb_protokoll, new EmptyBorder(3, 3, 3, 3));
        
        // -------------------------
        // TabbedPane for Protocols
        // -------------------------
        JTabbedPane    tabbedPane = new JTabbedPane();
        
        tabbedPane.setBorder(cb);
        tabbedPane.addTab(Labels.get("GUI_TABBEDPANE_TAB_OVERVIEW"), scroll_table_protocol_overview);
        tabbedPane.addTab(Labels.get("GUI_TABBEDPANE_TAB_DETAILS"), scroll_table_protocol);
        this.panel.add(tabbedPane);
        
        // -------------
        // File-Chooser
        // -------------
        if (properties.get_language() == Language.DE)
            {
            fc_folder.setLocale(Locale.GERMAN);
            }
        else if (properties.get_language() == Language.FR)
            {
            fc_folder.setLocale(Locale.FRENCH);
            }
        else if (properties.get_language() == Language.IT)
            {
            fc_folder.setLocale(Locale.ITALIAN);
            }
        SwingUtilities.updateComponentTreeUI(fc_folder);
        
        // Identifizierung des Ausführungsverzeichnisses
        // Das Properties-File wir im gleichen Verzeichnis gesucht,
        // wo sich das zur Ausführung gebrachte JAR-File befindet.
        String dir_ausfuehrung = System.getProperty("user.dir").replace("\\", "/") + "/";
        File   dir             = new File(dir_ausfuehrung);
        this.fc_folder.setCurrentDirectory(dir);
        this.fc_folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        // -----------
        // GUI-Objekt
        // -----------
        this.setContentPane(panel);
        this.setResizable(true);
        this.setVisible(false);
        
        }
        
    /**
     * 
     * A factory method is used to offer GUI objects to the client (please note that the constructor has been declared
     * as private).
     * 
     * What is a factory method? See Goetz (2006): Java Concurrency in Practice, Chapter 3.2.1, see page 42 for details.
     * 
     * Why are the GUI objects not offered to the client the normal way, i.e. by a public constructor?
     * 
     * If a GUI object would be offered directly by the constructor to the client, it would be necessary to register the
     * listeners inside the constructor. However, in this case, the "this"-reference would escape during construction
     * because the listeners need a reference to the GUI object: Example code of a constructor with escaping
     * this-reference:
     * 
     * <pre>
     * Controller controller = new Controller(this, properties); // this-reference is escaping
     * this.button_activation.addMouseListener(controller); // registering listener
     * this.button_deactivation.addMouseListener(controller); // registering listener
     * this.button_autoactivation.addMouseListener(controller); // registering listener
     * this.textfield_folder.addMouseListener(controller); // registering listener
     * this.button_folder.addMouseListener(controller); // registering listener
     * </pre>
     * 
     * If the this-reference escapes during construction, the object is considered not properly constructed (see page 41
     * in Goetz (2006)).
     * 
     * Therefore, the factory method constructs firstly the GUI object and after construction has finished, it registers
     * the event listeners.
     * 
     * Book References: Goetz (2006): Java Concurrency in Practice, Chapter 3.2.1, see page 42 for details.
     * 
     * Internet References: https://stackoverflow.com/questions/3705425/java-reference-escape
     * https://stackoverflow.com/questions/27453105/call-a-method-after-the-constructor-has-ended
     * https://stackoverflow.com/questions/13717603/guice-way-to-register-a-listener-after-calling-constructor
     * 
     * 
     * An alternative (very common) approach would be to use inner classes for the event handlers. However, this
     * approach would have inflated the GUI class too much.
     *
     * Book References: Sierra, Bates: Head First Java (A Brain Friendly Guide), Chapter 12, Page 374ff. Horstmann: Java
     * for Everyone, Chapter 11.6: Using Inner Classes for Listeners
     *
     * Internet References: https://docs.oracle.com/javase/tutorial/uiswing/events/generalrules.html#innerClasses
     * https://stackoverflow.com/questions/1894181/standards-for-using-inner-classes-for-gui
     * https://stackoverflow.com/questions/15960950/better-way-to-implement-inner-class-with-listeners
     * http://www.fredosaurus.com/notes-java/GUI/events/inner_class_listener.html
     * 
     *
     * @author             Daniel Bierer (Statistical Office of the Canton of Zurich)
     * @version            2.4
     * 
     * @param   properties Reference of the properties object to be used.
     * @return             The manufactured GUI object.
     * 
     */
    public static Gui get_newInstance(Properties properties)
        {
        
        // ---------------------------------------------------------------------------------
        // SCHRITT 0: KONSTRUKTION DES GUI OBJEKTS
        // ---------------------------------------------------------------------------------
        Gui        gui        = new Gui(properties);
        
        // ---------------------------------------------------------------------------------
        // SCHRITT 1: KONSTRUKTION DES CONTROLLERS
        // ---------------------------------------------------------------------------------
        // Bemerkungen:
        // a) Der Controller kann erst nach der Konstruktion des GUI-Objekts
        // konstruiert werden, da zu dessen Konstruktion eine Referenz
        // auf das GUI-Objekt benoetigt wird.
        // b) Der Controller wird als Mouse-Listener fuer gewisse
        // GUI-Objekte verwendet (vgl. Source Code unten).
        // c) Der Controller wird zur Konstruktion von Dialogen und weiteren
        // Listenern benoetigt.
        // ---------------------------------------------------------------------------------
        Controller controller = new Controller(gui, properties);
        
        // ---------------------------------------------------------------------------------
        // SCHRITT 2: REGISTRIERUNG DES CONTROLLERS ALS MOUSE_LISTENER FUER GUI-OBJEKTE
        // ---------------------------------------------------------------------------------
        gui.get_button_activation().addMouseListener(controller);
        gui.get_button_deactivation().addMouseListener(controller);
        gui.get_button_autoactivation().addMouseListener(controller);
        gui.get_textfield_folder().addMouseListener(controller);
        gui.get_button_folder().addMouseListener(controller);
        controller.deactivation();
        
        // ---------------------------------------------------------------------------------
        // SCHRITT 3: KONSTRUKTION DER DIAGLOGE FUER DAS GUI-OBJEKT
        // ---------------------------------------------------------------------------------
        // Die Dialoge werden nicht im Rahmen der Konstruktion des GUI-Objekts konstruiert,
        // da sonst eine this-reference fluechten koennte, wenn das GUI-Objekt noch
        // nicht fertig konstruiert ist.
        // ---------------------------------------------------------------------------------
        
        DialogDir                    dialog_dir                    = new DialogDir(gui);
        DialogFileProcessingError    dialog_fileprocessingerror    = new DialogFileProcessingError(gui);
        DialogReceiptNotWellFormed   dialog_receiptnotwellformed   = new DialogReceiptNotWellFormed(gui, properties, controller);
        DialogSupport                dialog_support                = new DialogSupport(gui, false);
        DialogWaitingForDeactivation dialog_waitingfordeactivation = new DialogWaitingForDeactivation(gui);
        dialog_dir.setVisible(false);
        dialog_fileprocessingerror.setVisible(false);
        dialog_receiptnotwellformed.setVisible(false);
        dialog_support.setVisible(false);
        dialog_waitingfordeactivation.setVisible(false);
        gui.set_dialog_dir(dialog_dir);
        gui.set_dialog_fileprocessingerror(dialog_fileprocessingerror);
        gui.set_dialog_receiptnotwellformed(dialog_receiptnotwellformed);
        gui.set_dialog_support(dialog_support);
        gui.set_dialog_waitingfordeactivation(dialog_waitingfordeactivation);
        
        // ---------------------------------------------------------------------------------
        // SCHRITT 4: KONSTRUKTION VON WEITERERN LISTERNERN
        // ---------------------------------------------------------------------------------
        // Die Konstruktion der folgenden weiteren EventListener wird nicht im Rahmen
        // der Konstruktion des GUI-Objekts vollzogen, da zu deren Konstruktion eine
        // Referenz auf das GUI-Objekt benoetigt wird.
        // ---------------------------------------------------------------------------------
        
        // ActionListener
        // Steuerung des Balkenmenüs
        EventHandlerActionListener actionlistener = new EventHandlerActionListener(gui, properties, controller);
        gui.get_menuitem0().addActionListener(actionlistener);
        gui.get_menuitem1().addActionListener(actionlistener);
        
        // ComponentListener
        // Beim Verschieben des Hauptfensters werden die Unterfenster auch
        // mitverschoben.
        // Beim Verschieben von Unterfenstern wird das Hauptfesnter auch mitverschoben.
        EventHandlerComponentListener componentlistener = new EventHandlerComponentListener(gui);
        gui.addComponentListener(componentlistener);
        gui.get_dialog_dir().addComponentListener(componentlistener);
        gui.get_dialog_fileprocessingerror().addComponentListener(componentlistener);
        gui.get_dialog_support().addComponentListener(componentlistener);
        gui.get_dialog_receiptnotwellformed().addComponentListener(componentlistener);
        gui.get_dialog_waitingfordeactivation().addComponentListener(componentlistener);
        
        // ItemListener
        // Wir brauchen fuer die ComoBox einen ItemListener und keinen ActionListener.
        // https://stackoverflow.com/questions/32752072/jcombobox-and-itemlistener-actionlistener
        EventHandlerItemListener itemlistener = new EventHandlerItemListener(gui, properties);
        gui.get_combobox_autoactivation().addItemListener(itemlistener);
        
        // ---------------------------------------------------------------------------------
        // SCHRITT 5: KONSTRUKTION VON WEITERERN LISTERNERN
        // ---------------------------------------------------------------------------------
        // Zur Konstruktion des folgenden Listeners wird zwar keine this-Referenz
        // benoetigt, jedoch eine Referenz auf das Controller-Objekt, welches
        // seinerseits
        // eine Referenz auf das GUI-Objekt benoetigt. Aus diesem Grund muss auch der
        // folgende Listener nach der Konstruktion des GUI-Objekts konstruiert werden.
        //
        //
        // Festlegung derjenigen EventHandler, die keine
        // this-reference zur Konstruktion benoetigen
        // ----------------------------------------------------------------------------------
        // WindowListener
        // Festlegung der Exit-Aktion
        EventHandlerWindowListener windowlistener = new EventHandlerWindowListener(properties, controller);
        gui.addWindowListener(windowlistener);
        
        // -------------------------------------
        // Returning the properly constructed
        // GUI object to the client
        // -------------------------------------
        return gui;
        
        }
        
    }
