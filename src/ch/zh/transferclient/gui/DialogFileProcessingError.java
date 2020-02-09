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
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;

import ch.zh.transferclient.main.Conf;
import ch.zh.transferclient.main.Labels;
import ch.zh.transferclient.main.Logger;

/**
 * This class is used to show a dialog which informs the user that a error occurred while a file has been processed.
 * This error can occur when a file is open in another application.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class DialogFileProcessingError extends JDialog implements MouseListener
    
    {
    
    /** A unique serial version identifier. */
    private static final long  serialVersionUID  = 1L;
    
    /** The width of the dialog. */
    protected static final int WIDTH             = 700;
    /** The height of the dialog. */
    protected static final int HEIGHT            = 480;
    /** The OK button. */
    private final JButton      okbutton          = new JButton("OK");
    /** The text area for the error message. */
    private final JTextArea    errormessage_area = new JTextArea(15, 50);
    
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
     * Makes the dialog visible.
     * 
     * @param e The exception to be shown.
     */
    public void setvisible(Exception e)
        {
        
        Logger.error(e);
        
        StringWriter errors = new StringWriter();
        
        e.printStackTrace(new PrintWriter(errors));
        
        this.errormessage_area.setText(errors.toString());
        
        this.setVisible(true);
        
        }
        
    /**
     * Constructs a DialogFileMoveError object.
     * 
     * @param parent The parent frame to be used.
     */
    public DialogFileProcessingError(JFrame parent)
        
        {
        
        // Grundparameter
        super(parent, Labels.get("DIALOG_FILEPROCESSINGERROR_TITLE"));
        this.setSize(WIDTH, HEIGHT);
        this.setIconImage(Conf.IMAGE_FOR_ICON);
        this.setResizable(false);
        
        // EventHandler
        this.okbutton.addMouseListener(this);
        
        // Border
        EmptyBorder emptyborder = new EmptyBorder(20, 10, 20, 20);
        
        // JPanel/Layout
        JPanel      panel       = new JPanel();
        panel.setBorder(emptyborder);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Hinweis-Texte
        JLabel label_hinweis = new JLabel(Labels.get("DIALOG_FILEPROCESSINGERROR_TEXT_1"));
        label_hinweis.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label_hinweis);
        
        JLabel label_hinweis_2 = new JLabel(Labels.get("DIALOG_FILEPROCESSINGERROR_TEXT_2"));
        label_hinweis_2.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label_hinweis_2);
        
        JLabel label_hinweis_3 = new JLabel(Labels.get("DIALOG_FILEPROCESSINGERROR_TEXT_3"));
        label_hinweis_3.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label_hinweis_3);
        
        // Abstand
        panel.add(new JPanel());
        
        // Fehlermeldung
        this.errormessage_area.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        this.errormessage_area.setEditable(false);
        this.errormessage_area.setEnabled(true);
        this.errormessage_area.setAlignmentX(Component.LEFT_ALIGNMENT);
        JScrollPane sp = new JScrollPane(this.errormessage_area);
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel jp_errormessage = new JPanel();
        jp_errormessage.setLayout(new FlowLayout());
        jp_errormessage.add(sp);
        jp_errormessage.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(sp);
        
        // Abstand
        panel.add(new JPanel());
        
        // Knopf
        this.okbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel jp_button = new JPanel();
        jp_button.setAlignmentX(Component.LEFT_ALIGNMENT);
        jp_button.add(this.okbutton);
        panel.add(jp_button);
        
        setContentPane(panel);
        
        }
        
    }
