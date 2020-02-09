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
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.*;

import ch.zh.transferclient.main.*;
import ch.zh.transferclient.properties.*;

/**
 * This class is used to produce a protocol table.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class FactoryTableProtocol_Overview
    
    {
    
    /**
     * Constructs a FactoryTableProtocol_Overview object.
     */
    private FactoryTableProtocol_Overview()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Produces the overview protocol table.
     * 
     * @param  properties The properties to be used.
     * @return            Protocol table.
     */
    protected static JTable get_table(Properties properties)
        
        {
        
        DefaultTableModel model = get_model();
        
        JTable            table = new JTable(model);
        
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        
        // Spaltenbreite der Report-ID Spalte festlegen
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        
        // Setzen der Selektions-Eigenschaften
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        
        // Setzen des ZellenRenderers
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
            {
            
            /** A unique serial version identifier. */
            private static final long serialVersionUID = 1L;
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
                {
                
                final Component c                    = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                final Integer   NUMBER_OF_RECIPIENTS = properties.get_sedex_recipient_ids().size();
                final String    KEY_TEXT             = NUMBER_OF_RECIPIENTS + " of " + NUMBER_OF_RECIPIENTS;
                
                if (table.getValueAt(row, 3).toString().contains(KEY_TEXT))
                    {
                    c.setForeground(Conf.GREEN);
                    // c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD));
                    }
                else
                    {
                    c.setForeground(Color.BLACK);
                    // c.setFont(c.getFont().deriveFont(java.awt.Font.PLAIN));
                    }
                    
                if (column == 0)
                    {
                    setHorizontalAlignment(SwingConstants.CENTER);
                    }
                else
                    {
                    setHorizontalAlignment(SwingConstants.LEFT);
                    }
                    
                return c;
                }
            });
        
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        
        return table;
        
        }
        
    /**
     * Produces the table model.
     * 
     * @return The table model.
     */
    private static DefaultTableModel get_model()
        {
        
        DefaultTableModel model = new DefaultTableModel()
            {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int row, int column)
                {
                return false;
                }
                
            };
        
        model.addColumn(Labels.get("FACTORYTABLEPROTOCOL_COLUMN_0")); // Heure
        model.addColumn(Labels.get("FACTORYTABLEPROTOCOL_COLUMN_1")); // Données diffusées
        model.addColumn(Labels.get("FACTORYTABLEPROTOCOL_COLUMN_4")); // ID Message
        model.addColumn(Labels.get("FACTORYTABLEPROTOCOL_COLUMN_5")); // Status
        
        return model;
        
        }
        
    }
