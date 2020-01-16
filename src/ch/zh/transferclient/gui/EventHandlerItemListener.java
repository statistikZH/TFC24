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

import ch.zh.transferclient.main.Labels;
import ch.zh.transferclient.properties.*;

/**
 * This class is used to handle change events of the autoactivation combobox.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class EventHandlerItemListener implements ItemListener
    
    {
    
    /** The graphical user interface whose events should be listened to. */
    private final Gui        gui;
    
    /** The properties to be used. */
    private final Properties properties;
    
    /**
     * Constructs an EventHandlerItemListener
     * 
     * @param gui        GUI to be used.
     * @param properties Properties to be used.
     */
    protected EventHandlerItemListener(Gui gui, Properties properties)
        {
        this.gui        = gui;
        this.properties = properties;
        }
        
    // ItemListener
    @Override
    public void itemStateChanged(ItemEvent e)
        {
        if (e.getStateChange() == ItemEvent.SELECTED)
            {
            String item       = (String) this.gui.get_combobox_autoactivation().getSelectedItem();
            String targettime = item.replace(Labels.get("GUI_COMBOBOX_AUTOACTIVATION_PART_1") + " ", "").replace(" "
                    + Labels.get("GUI_COMBOBOX_AUTOACTIVATION_PART_2"), "");
            this.properties.set_target_time(targettime);
            }
        }
        
    }
