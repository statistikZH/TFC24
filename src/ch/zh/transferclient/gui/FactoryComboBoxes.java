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

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * This class is used to produce combo boxes.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class FactoryComboBoxes
    
    {
    
    /**
     * Constructs a FactoryComboBoxes object.
     */
    private FactoryComboBoxes()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Produces a combo box.
     *
     * @param  items Items to be contained within the combo box.
     * @return       A combo box with the required items.
     */
    protected static JComboBox<String> get_combobox(String[] items)
        
        {
        JComboBox<String> cb = new JComboBox<String>(items);
        
        cb.setFont(new Font("Sanserif", Font.PLAIN, 12)); // Keine Fettschrift in ComBox
        
        for (int i = 0; i < cb.getComponentCount(); i++)
            {
            if (cb.getComponent(i) instanceof JComponent)
                {
                ((JComponent) cb.getComponent(i)).setBorder(new EmptyBorder(0, 0, 0, 0));
                }
            if (cb.getComponent(i) instanceof AbstractButton)
                {
                ((AbstractButton) cb.getComponent(i)).setBorderPainted(false);
                }
            }
            
        // cb.setEditable(true);
        // cb.setBorder(BorderFactory.createEmptyBorder());
        cb.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        return cb;
        
        }
        
    }
