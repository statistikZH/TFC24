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

package ch.zh.transferclient.processing;

import java.io.*;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import ch.zh.transferclient.gui.*;
import ch.zh.transferclient.properties.Properties;
import ch.zh.transferclient.util.ThreadAnalyzer;

/**
 * This class is used to update the GUI after an input file has been processed.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Processing_SingleInput_GUIUpdate
    
    {
    
    /**
     * Updates the GUI after an input file has been processed.
     * 
     * @param     properties           Properties to be used.
     * @param     gui                  GUI to be used.
     * @param     file                 Input file in question.
     * @param     sedex_message_id     Sedex message id to be used.
     * @exception InterruptedException This exception can be thrown by SwingUtilities.invokeAndWait.
     * @exception                      java.lang.reflect.InvocationTargetException This exception can be thrown by
     *                                 SwingUtilities.invokeAndWait.
     * 
     */
    protected synchronized static void process
    /* @formatter:off */
        (
        final Properties properties,
        final Gui gui,
        final File file,
        final String sedex_message_id
        ) throws Exception
    /* @formatter:on */
        
        {
        
        final ArrayList<String> sedex_recipient_ids     = properties.get_sedex_recipient_ids();
        final ArrayList<String> sedex_recipient_labels  = properties.get_sedex_recipient_labels();
        final int               number_of_table_entries = properties.get_number_of_table_entries();
        
        // ---------------------------------------------//
        // GUI-Aktualisierung: Generelle Vorbemerkungen //
        // ---------------------------------------------//
        
        // Kann es bei der Aktualisierung nicht zu Thread-Problemen kommen,
        // da die Protokolle nicht nur beim hier vorliegenden
        // Input-Processing sondern auch beim Receipts-Processing
        // aktualisiert werden? Nein, denn die Protokoll-Aktualisierung
        // erfolgt in beiden Faellen durch ein und denselben Thread,
        // naemlich den EDT-Thread. Die Auftraege an den EDT-Thread
        // werden per FIFO-Queue abgearbeitet.
        
        // Das bedeutet, dass falls zum gegebenen Zeitpunkt noch eine vorgaengige
        // Protokoll-Aktualisierung haengig waere, dass dann diese pendente
        // Aktualisierung zuerst noch abgeschlossen werden wuerde (das trifft auf
        // alle Auftraege zu, die noch in der Event-Queue sind).
        
        // -------------------------------------//
        // GUI-Aktualisierung: Detail-Protokoll //
        // -------------------------------------//
        // Schlaufe ueber alle Empfaenger
        for (int j = sedex_recipient_ids.size() - 1; j >= 0; j--)
            {
            
            // Vorbereitung der Zeile fuer das Detail-Protokoll
            final String[] table_protocol_detail_record = new String[6];
            
            String         receiverid                   = String.valueOf(j);
            
            table_protocol_detail_record[0] = sedex_message_id.substring(20, 22) + ":"
                    + sedex_message_id.substring(22, 24);
            table_protocol_detail_record[1] = file.getName();
            table_protocol_detail_record[2] = sedex_recipient_labels.get(j);
            table_protocol_detail_record[3] = sedex_recipient_ids.get(j);
            table_protocol_detail_record[4] = sedex_message_id + "-E" + receiverid;
            table_protocol_detail_record[5] = "Waiting for receipt ...";
            
            // Der Auftrag an den EDT-Thread wird mit "invokeAndWait" statt "invokeLater"
            // erteilt, um sicherzustellen, dass die GUI-Tabellen auf dem aktuellsten Stand
            // sind, wenn es zur Receipts-Verarbeitung kommt.
            //
            SwingUtilities.invokeAndWait(new Runnable()
                {
                @Override
                public void run()
                    {
                    
                    // Aktualisierung des Detail-Protokolls
                    ThreadAnalyzer.println(Thread.currentThread(), "EventDispatchThread", "Processing Inputs", "Aktualisierung des Detail-Protokolls");
                    
                    DefaultTableModel model = (DefaultTableModel) gui.get_table_protocol_detail().getModel();
                    // model.addRow(table_protocol_zeile); //Auskommentiert am 2019-11-04 und durch
                    // die folgende Zeile ersetzt.
                    
                    // Neue zeilen werden neu oben eingefuegt.
                    // https://stackoverflow.com/questions/44488353/how-to-add-new-rows-at-top-of-the-jtable-and-change-the-color-of-newly-inserted?rq=1
                    model.insertRow(0, table_protocol_detail_record);
                    //model.addRow(table_protocol_detail_record);
                    
                    }
                });
            } // Ende des Loops ueber alle Empfaenger
            
        // ---------------------------------------//
        // GUI-Aktualisierung: Overview-Protokoll //
        // ---------------------------------------//
        
        // Vorbereitung der Zeile fuer das Overview-Protokoll
        final String[] table_protocol_overview_record = new String[4];
        
        table_protocol_overview_record[0] = sedex_message_id.substring(20, 22) + ":"
                + sedex_message_id.substring(22, 24);
        table_protocol_overview_record[1] = file.getName();
        table_protocol_overview_record[2] = sedex_message_id;
        table_protocol_overview_record[3] = "Waiting for receipts ...";
        
        // Der Auftrag an den EDT-Thread wird mit "invokeAndWait" statt "invokeLater"
        // erteilt, um sicherzustellen, dass die GUI-Tabellen auf dem aktuellsten Stand
        // sind, wenn es zur Receipts-Verarbeitung kommt.
        
        SwingUtilities.invokeAndWait(new Runnable()
            {
            @Override
            public void run()
                {
                
                // Aktualisierung des Overview-Protokolls durch
                // Einfuehrung neuer Zeilen.
                
                // Kann das Einfuegen von Zeilen negative Auswirkungen
                // auf die STATUS-Aktualisierung bei der
                // Quittungsverarbeitung (ProcessReceipts) haben?
                // Nein, denn sowohl das Input-Processing wie auch
                // das Receipts-Proecessing wird durch den gleichen
                // Thread (EDT-Thread) erledigt und die Auftraege
                // werden per FIFO-Queue abgearbeitet.
                // Zudem wird mit "invokeAndWait"
                // gearbeitet (vgl. oben).
                
                ThreadAnalyzer.println(Thread.currentThread(), "EventDispatchThread", "Processing Inputs", "Aktualisierung des Overview-Protokolls");
                DefaultTableModel model_overview = (DefaultTableModel) gui.get_table_protocol_overview().getModel();
                model_overview.insertRow(0, table_protocol_overview_record);
                //model_overview.addRow(table_protocol_overview_record);
                
                }
            });
        
        // ---------------------------------------------//
        // GUI-Aktualisierung: Stutzung der Protokolle //
        // ---------------------------------------------//
        
        // Achtung! Nachdem oben neue Zeilen eingefuegt worden sind,
        // muss jetzt ueberprueft werden, ob die Protokolle dadurch nicht
        // zu lang geworden sind. Gegebenenfalls muss eine Stutzung vorgenommen
        // werden, denn Informationen von zu lange zurueckliegenden
        // Dateiuebertragungen werden im Protokoll nicht mehr angezeigt.
        
        // Der Auftrag an den EDT-Thread wird mit "invokeAndWait" statt "invokeLater"
        // erteilt, um sicherzustellen, dass die GUI-Tabellen auf dem aktuellsten Stand
        // sind, wenn es zur Receipts-Verarbeitung kommt.
        SwingUtilities.invokeAndWait(new Runnable()
            {
            @Override
            public void run()
                {
                
                // ------------------------------------------------------//
                // GUI-Aktualisierung: Stutzung des Overview-Protokolls //
                // ------------------------------------------------------//
                DefaultTableModel model_overview = (DefaultTableModel) gui.get_table_protocol_overview().getModel();
                if (model_overview.getRowCount() > number_of_table_entries)
                    {
                    // Bei der folgenden Index-Festlegung muss beachtet werden,
                    // dass die Zaehlung der Zeilen bei 0 beginnt.
                    int index_start = number_of_table_entries;          // Beispiel: 3 Entries: [0], [1],
                    // [2]
                    // koennen
                    // stehenbleiben, d.h. die Loeschung
                    // beginnt
                    // bei [3].
                    int index_ende  = model_overview.getRowCount() - 1; // Beispiel: Count=19: [18] ist der
                    // letzte Index, der geloescht
                    // werden muss.
                    
                    // Wichtige Bemerkung zur folgenden for-Schleife:
                    // Die Stutzung muss von oben nach unten erfolgen, weil
                    // sich sonst bei einer Loeschung die Indizes verschieben wuerden
                    // und versucht werden wuerde, der Wert zu einem Index zu loeschen,
                    // der gar nicht mehr im Modell existiert.
                    
                    // Im vorliegenden spezifischen Fall der Overview-Tabelle wuerde
                    // auch die Entfernung von unten nach oben funktionieren. Dies
                    // deshalb, weil wir uns in einem Loop ueber die Files befinden
                    // und deshalb immer nur eine Ueberschreitung des Maximalwertes
                    // um 1 vorliegt.
                    
                    // Demgegenueber muss das Detail-Protokoll immer von oben nach
                    // unten gestutzt werden.
                    
                    for (int i = index_ende; i >= index_start; i--)
                        {
                        model_overview.removeRow(i);
                        }
                        
                    }
                    
                // ----------------------------------------------------//
                // GUI-Aktualisierung: Stutzung des Detail-Protokolls //
                // ----------------------------------------------------//
                DefaultTableModel model_details = (DefaultTableModel) gui.get_table_protocol_detail().getModel();
                int               max_entries   = number_of_table_entries * sedex_recipient_ids.size();
                
                if (model_details.getRowCount() > max_entries)
                    {
                    // Bei der folgenden Index-Festlegung muss beachtet werden,
                    // dass die Zaehlung der Zeilen bei 0 beginnt.
                    int index_start = max_entries;                     // Beispiel: 3 Entries: [0], [1], [2]
                    // koennen
                    // stehenbleiben, d.h. die Loeschung
                    // beginnt bei [3].
                    int index_ende  = model_details.getRowCount() - 1; // Beispiel: Count=19: [18] ist der
                    // letzte Index, der geloescht
                    // werden muss.
                    
                    // Wichtige Bemerkung zur folgenden for-Schleife:
                    // Die Stutzung muss von oben nach unten erfolgen, weil
                    // sich sonst bei einer Loeschung die Indizes verschieben wuerden
                    // und versucht werden wuerde, der Wert zu einem Index zu loeschen,
                    // der gar nicht mehr im Modell existiert.
                    for (int i = index_ende; i >= index_start; i--)
                        {
                        model_details.removeRow(i);
                        }
                    }
                    
                }
            });
        
        }
        
    }
