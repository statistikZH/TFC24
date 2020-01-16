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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import ch.zh.transferclient.controller.*;
import ch.zh.transferclient.main.*;
import ch.zh.transferclient.properties.*;

/**
 * This class is used to show a dialog which informs the user that a received sedex receipt file is not well formed.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class DialogReceiptNotWellFormed extends JDialog implements MouseListener, WindowListener
    
    {
    
    /** A unique serial version identifier. */
    private static final long  serialVersionUID = 1L;
    
    /** The width of the dialog. */
    protected static final int WIDTH            = 400;
    
    /** The height of the dialog. */
    protected static final int HEIGHT           = 130;
    
    /** The graphical user interface object to be used. */
    private final Gui          gui;
    
    /** The properties to be used. */
    private final Properties   properties;
    
    /** The controller to be used. */
    private final Controller   controller;
    
    /** The button. */
    private final JButton      button           = new JButton(Labels.get("DIALOG_RECEIPTNOTWELLFORMED_BUTTON"));
    
    // MouseListener-Methoden
    @Override
    public void mouseClicked(MouseEvent e)
        {
        Exit.execute(this.properties, this.controller);
        }
        
    @Override
    public void mouseReleased(MouseEvent e)
        {
        }
        
    @Override
    public void mousePressed(MouseEvent e)
        {
        }
        
    @Override
    public void mouseExited(MouseEvent e)
        {
        }
        
    @Override
    public void mouseEntered(MouseEvent e)
        {
        }
        
    // WindowListener-Methoden
    @Override
    public void windowClosing(WindowEvent e)
        {
        Exit.execute(this.properties, this.controller);
        }
        
    @Override
    public void windowClosed(WindowEvent e)
        {
        }
        
    @Override
    public void windowActivated(WindowEvent e)
        {
        }
        
    @Override
    public void windowDeactivated(WindowEvent e)
        {
        }
        
    @Override
    public void windowDeiconified(WindowEvent e)
        {
        }
        
    @Override
    public void windowIconified(WindowEvent e)
        {
        }
        
    @Override
    public void windowOpened(WindowEvent e)
        {
        }
        
    /**
     * Makes the dialog visible.
     */
    public void setvisible()
        {
        this.gui.setEnabled(false);
        this.setVisible(true);
        }
        
    /**
     * Constructs a DialogReceiptNotWellFormed object.
     * 
     * @param parent     The parent frame to be used.
     * @param properties The properties to be used.
     * @param controller The controller to be used.
     */
    public DialogReceiptNotWellFormed(JFrame parent, Properties properties, Controller controller)
        
        {
        
        // Grundparameter
        super(parent, Labels.get("DIALOG_RECEIPTNOTWELLFORMED_TITLE"));
        this.gui        = (Gui) parent;
        this.properties = properties;
        this.controller = controller;
        this.setSize(WIDTH, HEIGHT);
        this.setIconImage(Conf.IMAGE_FOR_ICON);
        this.setResizable(false);
        
        // EventHandlers
        this.button.addMouseListener(this);
        this.addWindowListener(this);
        
        // Exit-Operation definieren
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        // OK-Knopf
        this.button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Border
        EmptyBorder emptyborder = new EmptyBorder(20, 20, 20, 20);
        
        // JPanel/Layout
        JPanel      panel       = new JPanel();
        panel.setBorder(emptyborder);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Hinweis-Text
        JPanel jp_hinweis = new JPanel();
        jp_hinweis.setLayout(new BoxLayout(jp_hinweis, BoxLayout.Y_AXIS));
        JLabel label_hinweis_1 = new JLabel(Labels.get("DIALOG_RECEIPTNOTWELLFORMED_TEXT_1"));
        JLabel label_hinweis_2 = new JLabel(Labels.get("DIALOG_RECEIPTNOTWELLFORMED_TEXT_2"));
        label_hinweis_1.setAlignmentX(Component.LEFT_ALIGNMENT);
        label_hinweis_2.setAlignmentX(Component.LEFT_ALIGNMENT);
        jp_hinweis.add(label_hinweis_1);
        jp_hinweis.add(label_hinweis_2);
        jp_hinweis.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(jp_hinweis);
        
        panel.add(new JPanel());
        
        // Knopf
        panel.add(this.button);
        
        setContentPane(panel);
        
        }
        
    }
