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

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import ch.zh.transferclient.gui.*;
import ch.zh.transferclient.util.ThreadAnalyzer;

/**
 * This class is used to update the STATUS INFO within the GUI tables.
 * 
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Processing_Receipts_GUIUpdate
    
    {
    
    /**
     * Updates the STATUS INFO within the GUI tables.
     * 
     * @param  gui                  The Gui object (or more precisely the value of the reference to the Gui object) to
     *                              be used.
     * @param  sedex_recipient_ids  The Sedex Recipient IDs (or more precisely the value of reference to the
     *                              corresponding String object).
     * @param  RECEIPTS             The Vector (or more precisely the value of the reference to the vector) containing
     *                              the receipts.
     * @throws InterruptedException Exception which can be thrown by SwingUtilities.invokeAndWait.
     * @throws                      java.lang.reflect.InvocationTargetException Exception which can be thrown by
     *                              SwingUtilities.invokeAndWait.
     */
    protected synchronized static void process_GUIUpdate(Gui gui, ArrayList<String> sedex_recipient_ids, Vector<Processing_Receipts_Record> RECEIPTS) throws InterruptedException, java.lang.reflect.InvocationTargetException
        {
        
        final int NUMBER_TOTAL_OF_RECEIVERS = sedex_recipient_ids.size();
        
        final String  STATUS_APPENDIX;
        if (NUMBER_TOTAL_OF_RECEIVERS < 2)
            {
            STATUS_APPENDIX = "of " + NUMBER_TOTAL_OF_RECEIVERS + " message successfully transmitted";
            }
        else
            {
            STATUS_APPENDIX = "of " + NUMBER_TOTAL_OF_RECEIVERS + " messages successfully transmitted";
            }
        
        // -------------------------------//
        // GUI-Aktualisierung: Protokolle //
        // -------------------------------//
        
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
        
        // Der Auftrag an den EDT-Thread wird mit "invokeAndWait" statt "invokeLater"
        // erteilt, um sicherzustellen, dass die GUI-Tabellen auf dem aktuellsten Stand
        // sind, wenn es danach wieder zur Input-Verarbeitung kommt.
        SwingUtilities.invokeAndWait(new Runnable()
            {
            /* (non-Javadoc)
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run()
                {
                
                // ------------------------------//
                // AKTUALISIERUNG DER PROTOKOLLE //
                // ------------------------------//

                    
                // Aktualisierung des Protokolls der Detailtabelle
                ThreadAnalyzer.println(Thread.currentThread(), "EventDispatchThread", "Processing Receipts", "Aktualisierung des Detail-Protokolls");
                
                // ------------------------//
                // QUITTUNGS-VERARBEITUNG //
                // ------------------------//
                final DefaultTableModel model_detail   = (DefaultTableModel) gui.get_table_protocol_detail().getModel();
                final DefaultTableModel model_overview = (DefaultTableModel) gui.get_table_protocol_overview().getModel();
                
                for (int receipt_index = RECEIPTS.size() - 1; receipt_index >= 0; receipt_index--)
                    {
                    
                    final Processing_Receipts_Record receipt                  = RECEIPTS.get(receipt_index);
                    
                    final String                     SEDEX_MESSAGE_ID         = receipt.get_sedex_message_id();
                    final String                     SEDEX_MESSAGE_ID_TRIMMED = receipt.get_sedex_message_id_trimmed();
                    final String                     STATUS_INFO              = receipt.get_status_info();
                    
                    // -------------------------------------//
                    // Aktualisierung des Detail-Protokolls //
                    // -------------------------------------//
                    for (int i = model_detail.getRowCount() - 1; i >= 0; i--)
                        {
                        
                        if (model_detail.getValueAt(i, 4).toString().equals(SEDEX_MESSAGE_ID))
                            {
                            model_detail.setValueAt(STATUS_INFO, i, 5);
                            // Da die ganze Row mit gruener Farbe upgedatet werden muss,
                            // wird die folgende Methode aufgerufen:
                            model_detail.fireTableRowsUpdated(i, i);
                            
                            // -------------------------------------//
                            // Loeschung der verarbeiteten Quittung //
                            // -------------------------------------//
                            RECEIPTS.remove(receipt);
                            break;
                            }
                        }
                    
                    // @formatter:off                                                                          //
                    // ----------------------------------------------------------------------------------------//
                    // Aktualisierung des Overview-Protokolls                                                  //
                    // Besteht die Moeglichkeit, dass der Datentransfer noch nicht in der                      //
                    // Overview-Tabelle eingetragen ist, obwohl er in der Detailtabelle schon vorhanden ist?   //
                    // Nein, diese Moeglichkeit besteht nicht, denn bei der Auffuellung der GUI-Tabelle        //
                    // wartet der Executor-Thread so lange, bis der EDT-Thread die Auffuellung                 //
                    // abgeschlossen hat.                                                                      //
                    // --------------------------------------------------------------------------------------- //
                    // @formatter:on                                                                           //
                    
                    if (STATUS_INFO.contains("successfully"))
                        {
                        for (int i = model_overview.getRowCount() - 1; i >= 0; i--)
                            {
                            if (model_overview.getValueAt(i, 2).toString().equals(SEDEX_MESSAGE_ID_TRIMMED))
                                {
                                final String status_old = (String) model_overview.getValueAt(i, 3);
                                final int    count_old;
                                if (status_old.contains("Waiting for receipts ..."))
                                    {
                                    count_old = 0;
                                    }
                                else
                                    {
                                    count_old = Integer.valueOf(status_old.replace(STATUS_APPENDIX, "").trim());
                                    }
                                
                                int count_new  = count_old + 1;
                                
                                //Der folgende Fall kann nur auftreten, wenn der Sedex-Client 
                                //fuer einen oder mehrere der Empfaenger mehr als eine
                                //Successfull-Quittung senden wuerde, was normalerweise nicht
                                //der Fall ist.
                                if (count_new>NUMBER_TOTAL_OF_RECEIVERS)
                                    {
                                    count_new = NUMBER_TOTAL_OF_RECEIVERS;
                                    }
                                
                                final String status_new = String.valueOf(count_new) + " " + STATUS_APPENDIX;
                                model_overview.setValueAt(status_new, i, 3);
                                // Da die ganze Row mit gruener Farbe upgedatet werden muss,
                                // wird die folgende Methode aufgerufen:
                                model_overview.fireTableRowsUpdated(i, i);
                                
                                break;
                                }
                            }
                        }
                        
                    }
                }
                
            });
                    
        }
        
    }
