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
import javax.swing.table.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 * This class is used to produce a TableCellRendererComboBox.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class TableCellRendererComboBox extends JComboBox<String> implements TableCellRenderer
    {
    
    /** A unique serial version identifier. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a TableCellRendererComboBox object.
     * 
     * @param items Items to be used.
     */
    public TableCellRendererComboBox(String[] items)
        {
        super(items);
        super.setFont(new Font("Sanserif", Font.PLAIN, 12)); // Das durch die ComboBox ausgewaehlte Item soll nicht fett
                                                             // sein.
        
        // Super Variante zur Lösung des Border-Problems
        super.setUI(new BasicComboBoxUI());
        
        // Weniger gute Variante zur Lösung des Border-Problems
        /*
         * for (int i=0; i<this.getComponentCount(); i++) { if (this.getComponent(i) instanceof JComponent) {
         * ((JComponent) this.getComponent(i)).setBorder(new EmptyBorder(0,0,0,0)); //((JComponent)
         * this.getComponent(i)).setBorder(new MatteBorder(-2,-2,-2,-2,Color.RED)); } if (this.getComponent(i)
         * instanceof AbstractButton) { ((AbstractButton) this.getComponent(i)).setBorderPainted(false); } }
         */
        // this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        }
        
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
        if (isSelected)
            {
            super.setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
            }
        else
            {
            super.setForeground(table.getForeground());
            super.setBackground(table.getBackground());
            }
        super.setSelectedItem(value);
        
        return this;
        }
        
    }
