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

package ch.zh.transferclient.processing;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Vector;

import ch.zh.transferclient.gui.*;
import ch.zh.transferclient.properties.Properties;

/**
 * This class is used to process the receipts: The receipt files are evaluated in order to inform the end user about the
 * transfer status.
 * 
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class Processing_Receipts
    
    {
    
    /**
     * Constructs a Processing_Receipts object.
     */
    private Processing_Receipts()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Vector for saving the receipts (vector is thread-safe).
     * 
     * Although the GUI protocol tables are always in newest state (because of using 
     * SwingUtilities.invokeAndWait() instead of SwingUtilities.invokeLater()),
     * a local (instead of a static) variable could not be used in the present context
     * because the end user could deactivate the Transfer-Client just in the moment 
     * after archiving a receipt file and before updating the GUI table. In such
     * a case the information from the receipt would be lost.
     * 
     * Can the problem illustrated in figure 5.1 of Goetz (2006) occur in our context?
     * 
     * No, this problem cannot occur because the executor thread is only adding receipts (and not removing receipts),
     * i.e. it cannot happen that the EDT thread requests a receipt which does not exist anymore.
     * 
     */
    private final static Vector<Processing_Receipts_Record> RECEIPTS = new Vector<Processing_Receipts_Record>();
    
    /**
     * Processes the receipts: The receipt files are evaluated in order to inform the end user about the transfer
     * status.
     * 
     * @param properties The properties to be used.
     * @param gui        The graphical user interface to be used.
     * @throws InterruptedException Exception which can be thrown by SwingUtilities.invokeAndWait.
     * @throws                      java.lang.reflect.InvocationTargetException Exception which can be thrown by
     *                              SwingUtilities.invokeAndWait.
     */
    protected synchronized static void process_receipts(final Properties properties, final Gui gui) throws InterruptedException, java.lang.reflect.InvocationTargetException
        {
        
        final String            dir_sedex_receipts  = properties.get_sedex_dir_receipts();
        final ArrayList<String> sedex_recipient_ids = properties.get_sedex_recipient_ids();
        
        final File              dir                 = new File(dir_sedex_receipts);
        final File[]            files               = dir.listFiles();
        
        //Damit der Executor-Thread bei der Deaktivierung
        //auch bei vielen gleichzeitigen Versandanfragen 
        //schnell unterbrochen wird, wird neu nur noch 
        //das erste File der Liste verarbeitet.
        //Die Verarbeitung der folgenden Files erfolgt
        //dann im folgenden Taskdurchlauf.
        int size = files.length;
        if (size>0)
            {
            size=1;
            }
        
        //for (int i = 0; i < files.length; i++)
        for (int i = 0; i < size; i++)
            {
            
            final File                 file_receipt     = files[i];
            
            // Verschieben des Quittungsfiles ins Archiv
            // Die Quittungsfiles werden ab Version 2.4
            // des Transfer-Clients fortwährend ins
            // Archiv verschoben. Dies hat den Vorteil,
            // dass bereits verarbeitete Quittungen nicht mehr
            // behandelt werden muessen.
            
            File file_receipt_copy = new File("archive/receipts/"+ file_receipt.getName());
            
            while (true)
                {
                try
                    {
                    
                    // Verschiebung der Quittung ins Archiv
                    Files.move(file_receipt.toPath(), file_receipt_copy.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    break;
                    }
                catch (Exception e)
                    {
                    }
                if (!files[i].exists())
                    {
                    break;
                    }
                }
            
            
            //Extraktion der Information aus dem verschobenen File.
            //Die Extraktion findet bewusst basierend auf dem verschobenen File statt,
            //um sicherzustellen, dass das File fertig geschrieben ist. 
            //Wuerde die Extraktion vor dem Verschieben stattfinden, koennte
            //das File noch nicht vollstaendig geschrieben sein, was in
            //Experimenten am 2019-12-20 bewiesen worden ist.
            final String SEDEX_MESSAGE_ID = Processing_Receipts_ExtractElement.extract_element(gui, file_receipt_copy, "messageId");
            final String STATUS_INFO      = Processing_Receipts_ExtractElement.extract_element(gui, file_receipt_copy, "statusInfo");
            
            Processing_Receipts_Record receipt = new Processing_Receipts_Record(SEDEX_MESSAGE_ID, STATUS_INFO);
            
            RECEIPTS.add(receipt);
                
            }
            
        Processing_Receipts_GUIUpdate.process_GUIUpdate(gui, sedex_recipient_ids, RECEIPTS);
        
        }
        
    }
