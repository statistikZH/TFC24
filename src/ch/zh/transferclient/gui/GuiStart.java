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

import javax.swing.*;
import javax.swing.border.*;

import ch.zh.transferclient.main.*;

/**
 * This class is used to show an initial frame, informing that the transfer-client is starting up.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class GuiStart extends JFrame
    
    {
    
    /**
     * Status fields of the GuiStart that can be used for informing the end user about the current status.
     */
    public static enum StatusField {
        STATUSFIELD1,
        STATUSFIELD2,
        STATUSFIELD3
        }
    
    /** A unique serial version identifier. */
    private static final long serialVersionUID = 1L;
    
    /** The width of the dialog. */
    public static final int   WIDTH            = 400;
    
    /** The height of the dialog. */
    public static final int   HEIGHT           = 180;
    
    /** The status field 1. */
    private final JLabel statusfield1;
    
    /** The status field 2. */
    private final JLabel statusfield2;
    
    /** The status field 3. */
    private final JLabel statusfield3;
    
    /**
     * Updates the status field.
     * @param field The field to update.
     * @param text The new text.
     */
    public void update_statusfield(StatusField field,String text)
        {
        JLabel targetfield;
        if (field==StatusField.STATUSFIELD1)        {targetfield=this.statusfield1;}
        else if (field==StatusField.STATUSFIELD2)   {targetfield=this.statusfield2;}
        else                                        {targetfield=this.statusfield3;}
        
        targetfield.setText(text);
        targetfield.repaint();
        }
    
    /**
     * Constructs a GuiStart object.
     */
    public GuiStart()
        
        {
        
        super("Transfer-Client " + Conf.VERSION);
        
        // -----------------------------------------//
        // Icon, Frame-Zentrierung, Exit-Operation //
        // -----------------------------------------//
        
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
        
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // --------------//
        // Inneres Panel //
        // --------------//
        JPanel innerpanel = new JPanel();
        FlowLayout flowlayout = new FlowLayout(FlowLayout.LEFT,5,5);
        innerpanel.setLayout(flowlayout);
        
        // ---------------------------------------------//
        // Inneres Panel: Linke Seite: Bild hinzufuegen //
        // ---------------------------------------------//
        JLabel bild = new JLabel(Conf.IMAGE_ICON_LOGO);
        innerpanel.add(bild);
        
        
        // ----------------------------------------------//
        // Inneres Panel: Rechte Seite: Text hinzufuegen //
        // ----------------------------------------------//
        //Panel fuer Text1 und Text2
        JPanel textpanel = new JPanel();
        GridLayout gridlayout = new GridLayout(4,1); //Es sind 4 Texte untereinander.
        textpanel.setLayout(gridlayout);
        
        //Titelzeile
        JLabel titel = new JLabel("Initialization");
        Font font_titel = new Font("Sans Serif", Font.BOLD, 16);
        titel.setFont(font_titel);
        textpanel.add(titel);
        
        //Erste Textzeile: Statusfeld 1
        Font font_status = new Font("Sans Serif", Font.PLAIN, 12);
        this.statusfield1 = new JLabel("");        
        this.statusfield1.setFont(font_status);
        textpanel.add(this.statusfield1);
        
        //Zweite Textzeile: Statusfeld 2
        this.statusfield2 = new JLabel("");
        this.statusfield2.setFont(font_status);
        textpanel.add(this.statusfield2);
        
        //Dritte Textzeile: Statusfeld 3
        this.statusfield3 = new JLabel("");
        this.statusfield3.setFont(font_status);
        textpanel.add(this.statusfield3);
        
        innerpanel.add(textpanel);
        
        // ----------------------------------------------//
        // Inneres Panel dem aeusseren Panel hinzufuegen //
        // ----------------------------------------------//
        panel.add(innerpanel);
        
        // Zwischenraum
        JLabel empty = new JLabel("");
        panel.add(empty);
        
        // ------------//
        // GUI-Objekt //
        // ------------//
        this.setContentPane(panel);
        this.setResizable(false);
        this.setVisible(true);
        
        }
        
    }
