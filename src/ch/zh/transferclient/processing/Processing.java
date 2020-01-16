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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.SwingUtilities;

import ch.zh.transferclient.gui.*;
import ch.zh.transferclient.main.Logger;
import ch.zh.transferclient.properties.Properties;
import ch.zh.transferclient.util.TimeStamp;

/**
 * This class is used to process the input and receipt files.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Processing
    
    {
    
    /**
     * Processes the input and receipt files.
     * 
     * @param properties The properties to be used.
     * @param gui        The graphical user interface to be used.
     */
    public synchronized static void process(final Properties properties, final Gui gui)
        
        {
        
        final String sedex_sender_id = properties.get_sedex_sender_id();
        final String dir_results     = properties.get_folder_results();
        
        // -------------------------------------------------//
        // Schlaufe ueber alle Files des Resultate-Ordners //
        // -------------------------------------------------//
        final File   dir             = new File(dir_results);
        
        //Identifizierung der Files
        //There is no guarantee that the name strings in the resulting array will appear in any specific order; 
        //they are not, in particular, guaranteed to appear in alphabetical order.
        //https://docs.oracle.com/javase/7/docs/api/java/io/File.html#listFiles()
        final File[] files           = dir.listFiles();
        
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
            if (files[i].isDirectory())
                {
                // Verzeichnisse werden nicht weggeschoben
                }
            else
                {
                final File   file             = files[i];
                final String datafile_path    = file.getAbsolutePath();
                final Path   path_source      = Paths.get(datafile_path);
                final String sedex_message_id = sedex_sender_id + "-" + TimeStamp.getstamp_for_sedex_message_id();
                
                // Einbau eines Delays von 100 Millisekunden zur Gewährleistung,
                // dass eine Message-ID nicht zweimal vergeben werden kann.
                // https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
                try
                    {
                    Thread.sleep(100);
                    }
                catch (Exception e)
                    {
                    Logger.error(e);
                    SwingUtilities.invokeLater(new Runnable()
                        {
                        @Override
                        public void run()
                            {
                            gui.get_dialog_fileprocessingerror().setvisible(e);
                            }
                        });
                    }
                    
                try
                    {
                    
                    // In der nachfolgenden Schlaufe wird so lange gewartet,
                    // bis das File wegkopiert werden kann.
                    // Exceptions koennen hier umso haeufiger auftreten,
                    // je groesser die Datenfiles sind.
                    // Wenn beispielsweise 100 MB grosse Files in den Resultateordner
                    // geschrieben werden, kann es gut sein, dass diese noch
                    // fuer eine laengere Zeit blockiert sind.
                    // Diese Schlaufe wurde mit der Version 2.2 des
                    // Transfer-Clients eingefuehrt.
                    while (true)
                        {
                        try
                            {
                            Files.copy(file.toPath(), new File("archive/data/data_" + sedex_message_id + "_"
                                    + file.getName()).toPath());
                            break;
                            }
                        catch (Exception e)
                            {
                            }
                        if (!files[i].exists())
                            break;
                        }
                        
                    // Falls die Datenfiles nicht archiviert werden sollen,
                    // wird das File nachfolgend wieder geloescht.
                    // Wieso wird das File nicht einfach nur dann wegkopiert,
                    // wenn es archiviert werden soll?
                    // Begruendung:
                    // Durch das gewaehlte Vorgehen ist sicher gestellt,
                    // dass bei der nachfolgenden Weiterverarbeitung das
                    // File vom externen System fertig geschrieben ist
                    // (man denke an groessere Files >100 MB).
                    boolean archive_datafiles = properties.get_archive_datafiles();
                    if (!archive_datafiles)
                        {
                        Files.deleteIfExists(new File("archive/data/data_" + sedex_message_id + "_"
                                + file.getName()).toPath());
                        }
                        
                    // ------------------------------//
                    // Verarbeitung des Input-Files //
                    // ------------------------------//
                    Processing_SingleInput.process(properties, gui, file, sedex_message_id);
                    
                    // ---------------------//
                    // Receipts-Processing //
                    // ---------------------//
                    Processing_Receipts.process_receipts(properties, gui);
                    
                    Logger.info("FILE PROCESSED: " + sedex_message_id + ": " + path_source.toString());
                    
                    }
                catch (Exception e)
                    {
                    Logger.error(e);
                    SwingUtilities.invokeLater(new Runnable()
                        {
                        @Override
                        public void run()
                            {
                            gui.get_dialog_fileprocessingerror().setvisible(e);
                            }
                        });
                    }
                    
                }
                
            }
            
        // @formatter:off                                                                    //
        // ----------------------------------------------------------------------------------//
        // Receipts-Processing                                                               //
        // ----------------------------------------------------------------------------------//
        // Wieso findet das Receipt-Processing hier nochmals statt, obwohl es in der         //
        // obigen Schlaufe ueber die Inputfiles bereits aufgefuehrt ist?                     //
        // Begruendung:                                                                      //
        // Falls keine Input-Files mehr vorhanden sind, wuerde das Receipts-Processing       //
        // nicht mehr durchgefuehrt werden.                                                  //
        // ----------------------------------------------------------------------------------//
        // Wieso findet das Receipt-Processing ueberhaupt auch noch in der Schleife          //
        // statt?                                                                            //
        // Begruendung:                                                                      //
        // Angenommen, ein Benutzer legt 1000 Files auf einmal ins Verzeichnis, dann         //
        // dauert es sehr lange, bis das Receipt-Processing einsetzt, naemlich erst nach     //
        // nach dem Versenden aller 1000 Files.                                              //
        // ----------------------------------------------------------------------------------//
        // @formatter:on                                                                     //
        try
            {
            Processing_Receipts.process_receipts(properties, gui);
            }
        catch (InterruptedException | java.lang.reflect.InvocationTargetException e)
            {
            Logger.error(e);
            SwingUtilities.invokeLater(new Runnable()
                {
                @Override
                public void run()
                    {
                    gui.get_dialog_fileprocessingerror().setvisible(e);
                    }
                });
            }
            
        }
        
    }
