/*
 * Copyright 2018-2020 Statistisches Amt des Kantons Zürich
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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import ch.zh.transferclient.main.*;

/**
 * This class is used to show errors which occur at startup time. Labels are in english because in the case of startup
 * errors, the preferred language can typically not be determined (properties.txt (language parameter) or labels.xml
 * cannot be read).
 * 
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class GuiStartupError extends JFrame implements ActionListener, ComponentListener
    
    {
    
    /** A unique serial version identifier. */
    private static final long   serialVersionUID = 1L;
    
    // -----------------//
    // GUI-Abmessungen //
    // -----------------//
    
    /** The width of the dialog. */
    public static final int     WIDTH            = 1000;
    
    /** The height of the dialog. */
    public static final int     HEIGHT           = 700;
    
    /** The menu item used for showing contact details for support. */
    private final JMenuItem     menuitem0        = new JMenuItem("Support");
    /** The menu item used for terminating the application. */
    private final JMenuItem     menuitem1        = new JMenuItem("Terminate");
    /** The dialog which shows the contact details for support. */
    private final DialogSupport dialogsupport    = new DialogSupport(this, true);
    
    // ----------------//
    // Event-Handling //
    // ----------------//
    @Override
    public void actionPerformed(ActionEvent e)
        {
        
        if (e.getSource() == this.menuitem0)
            {
            this.dialogsupport.setVisible(true);
            }
        else if (e.getSource() == this.menuitem1)
            {
            System.exit(0);
            }
            
        }
        
    // Mitteilungsfenster ist immer am gleichen Ort fixiert (relativ zum
    // Hauptfenster)
    @Override
    public void componentMoved(ComponentEvent e)
        {
        final int TRANSLATION_X_DIALOG_SUPPORT = (WIDTH - DialogSupport.WIDTH) / 2;
        final int TRANSLATION_Y_DIALOG_SUPPORT = (HEIGHT - DialogSupport.HEIGHT) / 2;
        
        if (e.getSource() == this)
            {
            // Festlegung der neuen Lokation des Support-Dialogs
            this.dialogsupport.setLocation(this.getLocation().x + TRANSLATION_X_DIALOG_SUPPORT, this.getLocation().y
                    + TRANSLATION_Y_DIALOG_SUPPORT);
            }
        else if (e.getSource() == this.dialogsupport)
            {
            // Festlegung der neuen Lokation des GUIs
            this.setLocation(this.dialogsupport.getLocation().x
                    - TRANSLATION_X_DIALOG_SUPPORT, this.dialogsupport.getLocation().y - TRANSLATION_Y_DIALOG_SUPPORT);
            }
        }
        
    @Override
    public void componentHidden(ComponentEvent e)
        {
        };
        
    @Override
    public void componentResized(ComponentEvent e)
        {
        };
        
    @Override
    public void componentShown(ComponentEvent e)
        {
        };
        
    /**
     * Constructs a GuiStartupError object.
     * 
     * @param e Exception to be shown.
     */
    public GuiStartupError(Exception e)
        
        {
        
        super("Initialization Error");
        
        this.addComponentListener(this);
        this.dialogsupport.addComponentListener(this);
        
        // -----------------------------------------//
        // Icon, Frame-Zentrierung, Exit-Operation //
        // -----------------------------------------//
        
        this.setIconImage(Conf.IMAGE_FOR_ICON);
        
        // Frame-Zentrierung
        setSize(WIDTH, HEIGHT);
        Dimension frameSize  = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int       x          = (screenSize.width - frameSize.width) / 2;
        int       y          = (screenSize.height - frameSize.height) / 2;
        setLocation(x, y);
        
        // Exit-Operation definieren
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Panel
        JPanel panel = new JPanel();
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Menue
        JMenuBar menubar = new JMenuBar();
        JMenu    menu    = new JMenu("Menu");
        
        this.menuitem0.addActionListener(this);
        this.menuitem1.addActionListener(this);
        menu.add(this.menuitem0);
        menu.add(this.menuitem1);
        menubar.add(menu);
        this.setJMenuBar(menubar);
        
        // -------//
        // Title //
        // -------//
        JPanel panel_title = new JPanel();
        panel_title.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        JLabel label_titel_bild = new JLabel(Conf.IMAGE_ICON_LOGO);
        
        panel_title.add(label_titel_bild);
        
        JLabel label_titel_text = new JLabel("Transfer-Client: Error on Startup");
        label_titel_text.setFont(new Font("Sans Serif", Font.BOLD, 26));
        panel_title.add(label_titel_text);
        panel.add(panel_title);
        
        // Abstand
        panel.add(new JPanel());
        panel.add(new JPanel());
        
        // ---------------//
        // Hinweis-Texte //
        // ---------------//
        JPanel panel_hinweis = new JPanel();
        panel_hinweis.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel label_hinweis = new JLabel("The error message below can be copied by \"Selecting/Ctrl-C\"."); 
        label_hinweis.setAlignmentX(Component.LEFT_ALIGNMENT);
        label_hinweis.setFont(new Font("Sans Serif", Font.BOLD, 12));
        panel_hinweis.add(label_hinweis);
        panel.add(panel_hinweis);
        
        // Abstand
        panel.add(new JPanel());
        panel.add(new JPanel());
        
        // ---------------//
        // Fehlermeldung //
        // ---------------//
        JTextArea textarea = new JTextArea(100, 250);
        textarea.setColumns(50);
        textarea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        textarea.setEditable(false);
        textarea.setEnabled(true);
        textarea.setAlignmentX(Component.LEFT_ALIGNMENT);
        StringBuffer sb = new StringBuffer();
        
        sb.append("Exception: \n\n");
        sb.append("    " + e.toString() + "\n\n");
        sb.append("Stack-Trace: \n\n");
        StackTraceElement[] elements = e.getStackTrace();
        for (int i = 0; i < elements.length; i++)
            {
            sb.append("    " + elements[i].toString() + "\n");
            }
            
        textarea.setText(sb.toString());
        
        JScrollPane sp = new JScrollPane(textarea);
        sp.setMinimumSize(new Dimension(1500, 200));
        
        panel.add(sp);
        
        this.setContentPane(panel);
        this.setResizable(false);
        this.setVisible(true);
        
        }
        
    }
