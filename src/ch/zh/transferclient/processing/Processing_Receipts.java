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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

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
            
            final File file_receipt = files[i];
            
            File file_receipt_copy = new File("archive/receipts/"+ file_receipt.getName());
            
            try
                {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder        builder = factory.newDocumentBuilder();
                
                // Falls die folgende Zeile die Exception "premature end of file"
                // werfen sollte, wird das File nicht verschoben (da dann die 
                // nachfolgenden Zeilen nicht ausgefuehrt werden). Dies bedeutet,
                // dass der Executor-Thread das File beim naechsten Durchgang 
                // erneut im Sedex-Receipts-Ordner antreffen wird und erneut 
                // versuchen wird, das File auszuwerten.
                Document               doc     = builder.parse(file_receipt);
                
                // Falls der Executor die nachfolgende Zeile erreicht, heisst das,
                // dass das Parsen erfolgreich war und jetzt versucht werden kann,
                // das Receipt-File in den Archiv-Ordner zu verschieben.
                // Hinweis:
                // Die Quittungsfiles werden ab Version 2.4
                // des Transfer-Clients fortwährend ins
                // Archiv verschoben. Dies hat den Vorteil,
                // dass bereits verarbeitete Quittungen nicht mehr
                // behandelt werden muessen.
                
                try
                    {
                    
                    Files.copy(file_receipt.toPath(), file_receipt_copy.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    
                    // Falls beim Kopieren in den Archiv-Folder etwas schief gehen sollte (vorhergehende Zeile),
                    // wird noch nicht versucht werden, das File im Sedex-Receipts-Ordner zu loeschen, sondern 
                    // beim naechsten Executor-Thread-Durchgang erneut ausgewertet. Falls das Kopieren jedoch 
                    // erfolgreich war, wird versucht, das File im Sedex-Receipts-Ordner zu loeschen.
                    
                    try
                        {
                        Files.deleteIfExists(file_receipt.toPath());
                        
                        // Falls beim Loeschen des Receipt-Files im Sedex-Receipts-Ordner etwas
                        // schief gehen sollte (vorhergehende Zeile), wird das Receipt noch nicht mit
                        // den nachfolgenden zwei Zeilen registriert, sondern beim naechsten
                        // Executor-Thread-Durchgang erneut ausgewertet.
                        
                        // Falls die Loeschung ohne Fehler duchgefuehrt werden konnte,
                        // wird die Information des Receipts extrahiert und im 
                        // Arbeitsspeicher abgelegt.
                        Processing_Receipts_Record receipt = Processing_Receipts_ExtractElements.extract(doc,gui);
                        RECEIPTS.add(receipt);
                        }
                    catch(Exception e)
                        {
                        // Beim Loeschen des Receipt-Files ist etwas schief gegangen.
                        // Das Source-File bleibt in diesem Fall erhalten:
                        // https://stackoverflow.com/questions/54347494/java-nio-file-files-move-operation
                        // Beim naechsten Executor-Thread Durchlauf wird erneut versucht, 
                        // das Receipt-File zu loeschen.
                        }
                    
                    }
                catch(Exception e)
                    {
                    // Beim Versuch, das Receipt-File ins Archiv zu kopieren, ist etwas schief gelaufen.
                    // Beim naechsten Executor-Thread-Durchlauf wird erneut versucht, 
                    // das Receipt-File ins Archiv zu kopieren.
                    }
                }
            catch(Exception e)
                {
                // Beim Parsen des XML-Dokuments ist etwas schief gelaufen.
                // Beim naechsten Executor-Thread-Durchlauf wird erneut versucht, das XML-Dokument zu parsen.
                }
            
            }
            
        Processing_Receipts_GUIUpdate.process_GUIUpdate(gui, sedex_recipient_ids, RECEIPTS);
        
        }
    
    }
