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
 * This class is used to handle menu events.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class EventHandlerActionListener implements ActionListener
    
    {
    
    /** The graphical user interface whose events should be listened to. */
    private final Gui        gui;
    /** The properties to be used. */
    private final Properties properties;
    /** The controller to be used. */
    private final Controller controller;
    
    /**
     * Constructs an EventHandlerActionListener object.
     * 
     * @param gui        The graphical user interface whose events should be listened to.
     * @param properties The properties to be used.
     * @param controller The controller to be used.
     */
    protected EventHandlerActionListener(Gui gui, Properties properties, Controller controller)
        {
        this.gui        = gui;
        this.properties = properties;
        this.controller = controller;
        }
        
    // ActionListener
    @Override
    public void actionPerformed(ActionEvent e)
        {
        if (e.getSource() == this.gui.get_menuitem0())
            {
            gui.get_dialog_support().setVisible(true);
            }
        if (e.getSource() == this.gui.get_menuitem1())
            {
            Exit.execute(this.properties, this.controller);
            }
        }
        
    }
