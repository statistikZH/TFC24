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

import java.awt.event.*;

import ch.zh.transferclient.controller.*;
import ch.zh.transferclient.main.Exit;
import ch.zh.transferclient.properties.*;

/**
 * This class is used to handle the window closing event.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class EventHandlerWindowListener implements WindowListener
    
    {
    
    /** The properties to be used. */
    private final Properties properties;
    
    /** The controller to be used. */
    private final Controller controller;
    
    /**
     * Constructs the EventHandlerWindowListener
     * 
     * @param properties The properties to be used.
     * @param controller The controller to be used.
     */
    protected EventHandlerWindowListener(Properties properties, Controller controller)
        {
        this.properties = properties;
        this.controller = controller;
        }
        
    // WindowListener
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
        
    }
