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

import java.awt.Component;

import javax.swing.*;
import javax.swing.border.*;

import ch.zh.transferclient.main.*;

/**
 * This class is used to show a dialog which informs the user that the transfer-client will be deactivated.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class DialogWaitingForDeactivation extends JDialog
    
    {
    
    /** A unique serial version identifier. */
    private static final long  serialVersionUID = 1L;
    
    /** The width of the dialog. */
    protected static final int WIDTH            = 320;
    
    /** The height of the dialog. */
    protected static final int HEIGHT           = 130;
    
    /** The OK button. */
    private final Gui          gui;
    
    /**
     * Makes the dialog visible.
     *
     */
    public void setvisible()
        {
        this.gui.setEnabled(false);
        this.setVisible(true);
        }
        
    /**
     * Makes the dialog unvisible.
     * 
     */
    public void setunvisible()
        {
        this.gui.setEnabled(true);
        this.setVisible(false);
        }
        
    /**
     * Constructs a DialogWaitingForDeactivation object.
     * 
     * @param parent The parent frame to be used.
     */
    public DialogWaitingForDeactivation(JFrame parent)
        
        {
        
        // Grundparameter
        super(parent, Labels.get("DIALOG_WAITINGFORDEACTIVATION_TITLE"));
        this.gui = (Gui) parent;
        this.setSize(WIDTH, HEIGHT);
        this.setIconImage(Conf.IMAGE_FOR_ICON);
        this.setResizable(false);
        
        // Exit-Operation definieren
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        // Panel
        JPanel      panel       = new JPanel();
        EmptyBorder emptyborder = new EmptyBorder(20, 20, 20, 20);
        panel.setBorder(emptyborder);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Label
        JLabel label_hinweis = new JLabel(Labels.get("DIALOG_WAITINGFORDEACTIVATION_TEXT"));
        label_hinweis.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label_hinweis);
        
        panel.add(new JPanel());
        
        setContentPane(panel);
        
        }
        
    }
