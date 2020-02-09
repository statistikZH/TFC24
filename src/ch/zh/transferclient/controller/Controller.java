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

package ch.zh.transferclient.controller;

import java.io.*;
import java.awt.event.*;
import java.util.concurrent.*;

import javax.swing.*;

import ch.zh.transferclient.gui.*;
import ch.zh.transferclient.properties.*;

/**
 * This class is used to control the transfer-client.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class Controller implements MouseListener
    
    {
    
    /** The graphical user interface. */
    protected final Gui                gui;
    
    /** The properties. */
    protected final Properties         properties;
    
    /** Flag which indicates whether the transfer-client is active or not. */
    protected boolean                  active = false;
    
    /** Executor Services */
    protected ScheduledExecutorService executor_service;
    protected ScheduledExecutorService executor_service_for_non_repeating_tasks;
    
    
    /**
     * Constructs a SuperController object.
     * 
     * @param gui        The graphical user interface to be used.
     * @param properties The properties to be used.
     */
    public Controller(Gui gui, Properties properties)
        {
        this.gui        = gui;
        this.properties = properties;
        }
        
    /**
     * Deactivates the transfer client at start time (without mouse click).
     *
     */
    public void deactivation()
        {
        Controller_Deactivation.deactivation(this);
        }
        
    // MouseListener
    // https://stackoverflow.com/questions/11453240/mouselistener-in-java-swing-sometimes-not-respond
    @Override
    public void mouseClicked(MouseEvent e)
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
        
    @Override
    public void mouseReleased(MouseEvent e)
        {
        
        // All methods of the present mouse listener are running on the EDT thread
        // (boolean isedt = javax.swing.SwingUtilities.isEventDispatchThread();), 
        // i.e. the GUI elements can be accessed without violating thread safety.
        
        if (e.getSource() == this.gui.get_button_activation())
            {
            if (!this.active)
                {
                
                File file = new File(this.properties.get_folder_results());
                if (file.isDirectory())
                    {
                    Controller_Activation.activation(this,false);
                    }
                else
                    {
                    this.gui.get_dialog_dir().setVisible(true);
                    }
                    
                }
            }
        else if (e.getSource() == this.gui.get_button_autoactivation())
            {
            if (!this.active)
                {
                
                File file = new File(this.properties.get_folder_results());
                if (file.isDirectory())
                    {
                    Controller_Activation.activation(this,true);
                    }
                else
                    {
                    this.gui.get_dialog_dir().setVisible(true);
                    }
                }
            }
        else if (e.getSource() == this.gui.get_button_deactivation())
            {
            if (this.active)
                {
                Controller_Deactivation.deactivation(this);
                }
            }
            
        if ((e.getSource() == this.gui.get_button_folder()) || (e.getSource() == this.gui.get_textfield_folder()))
            {
            
            if (!this.active)
                {
                
                int returnVal = this.gui.show_opendialog_fc_folder();
                if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                    File file = this.gui.get_selectedfile_fc_folder();
                    this.gui.get_textfield_folder().setText(file.getPath());
                    this.properties.set_folder_results(file.getPath().toString());
                    }
                else if (returnVal == JFileChooser.CANCEL_OPTION)
                    {
                    this.gui.get_textfield_folder().setText(this.properties.get_folder_results());
                    }
                    
                }
                
            }
            
        }
        
    }
