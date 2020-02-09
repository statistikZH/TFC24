/*
 * Copyright 2018-2020 Statistisches Amt des Kantons Zürich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
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

import ch.zh.transferclient.main.Conf;
import ch.zh.transferclient.main.Labels;

/**
 * This class is used to show a dialog which informs the user that a result folder has to be chosen before the the
 * transfer-client can be activated.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class DialogDir extends JDialog implements MouseListener
    
    {
    
    /** A unique serial version identifier. */
    private static final long  serialVersionUID = 1L;
    
    /** The width of the dialog. */
    protected static final int WIDTH            = 400;
    
    /** The height of the dialog. */
    protected static final int HEIGHT           = 120;
    
    /** The OK button. */
    private final JButton      okbutton         = new JButton("OK");
    
    @Override
    public void mouseClicked(MouseEvent e)
        {
        
        if (e.getSource() == this.okbutton)
            {
            this.setVisible(false);
            }
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
        
    /**
     * Constructs a DialogDir object.
     * 
     * @param parent The parent frame to be used.
     */
    public DialogDir(JFrame parent)
        
        {
        
        // Grundparameter
        super(parent, Labels.get("DIALOG_DIR_TITLE"));
        this.setSize(WIDTH, HEIGHT);
        this.setIconImage(Conf.IMAGE_FOR_ICON);
        this.setResizable(false);
        
        // Event-Handler
        this.okbutton.addMouseListener(this);
        
        // Ok-Button
        this.okbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Border
        EmptyBorder emptyborder = new EmptyBorder(20, 20, 20, 20);
        
        // JPanel/Layout
        JPanel      panel       = new JPanel();
        panel.setBorder(emptyborder);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Hinweis-Text
        JLabel label_hinweis = new JLabel(Labels.get("DIALOG_DIR_TEXT"));
        label_hinweis.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label_hinweis);
        
        panel.add(new JPanel());
        
        // Knopf
        panel.add(this.okbutton);
        
        setContentPane(panel);
        
        }
        
    }
