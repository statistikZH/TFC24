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

package ch.zh.transferclient.main;

import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.*;

import ch.zh.transferclient.gui.*;
import ch.zh.transferclient.properties.*;
import ch.zh.transferclient.util.ThreadAnalyzer;

/**
 * This class is used for starting the application.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class Main
    
    {
    
    /**
     * Constructs a Main object.
     */
    private Main()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * 
     * Initialization Frame. The reference of the object is saved in a static variable and not in a local variable, so
     * that it can be accessed by the EDT thread. (see
     * https://stackoverflow.com/questions/10166521/the-final-local-variable-cannot-be-assigned)
     * 
     */
    private static GuiStart guistart;
    
    /**
     * Starts the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args)
        {
        try
            {
        
            // Aufruf des StartFrames
            // Es muss hier unbedingt invokeAndWait (statt invokeLater)
            // verwendet werden, um garantieren zu koennen, dass das Objekt
            // "guistart" auch existiert, wenn weiter unten (beim Verschieben der  
            // alten Quittungen ins Archiv) versucht wird, darauf zu zugreifen, 
            // da sonst eine null pointer exception geworfen werden kann.
            SwingUtilities.invokeAndWait(new Runnable()
                {
                @Override
                public void run()
                    {
                    guistart = new GuiStart();
                    }
                });
            
            Properties properties         = PropertiesReader.get_properties();
            
            // Logger einrichten
            boolean    log_overwrite      = properties.get_log_overwrite();
            boolean    log_suppress_debug = properties.get_log_suppress_debug();
            Logger.initialize(log_overwrite, log_suppress_debug);
            
            Logger.info("##### BEGIN SESSION #####");
            Logger.info("TRANSFER-CLIENT " + Conf.VERSION + " STARTED WITH THE FOLLOWING PROPERTIES:");
            Logger.info("   ---");
            Logger.info("   ZIP_COMPRESSION                 =" + properties.get_zip_compression());
            Logger.info("   ---");
            Logger.info("   LOG_OVERWRITE                   =" + properties.get_log_overwrite());
            Logger.info("   LOG_SUPPRESS_DEBUG              =" + properties.get_log_suppress_debug());
            Logger.info("   ARCHIVE_DATAFILES               =" + properties.get_archive_datafiles());
            Logger.info("   ---");
            Logger.info("   LANGUAGE                        =" + properties.get_language());
            Logger.info("   ---");
            Logger.info("   SEDEX_SENDER_ID                 =" + properties.get_sedex_sender_id());
            ArrayList<String> recipients = properties.get_sedex_recipient_ids();
            for (int i = 0; i < recipients.size(); i++)
                {
                Logger.info("   SEDEX_RECIPIENT_ID_" + i + "            =" + recipients.get(i));
                }
            Logger.info("   SEDEX_DIR_OUTBOX                =" + properties.get_sedex_dir_outbox());
            Logger.info("   SEDEX_DIR_RECEIPTS              =" + properties.get_sedex_dir_receipts());
            
            Logger.info("   ---");
            Logger.info("   FOLDER_RESULTS                  =" + properties.get_folder_results());
            Logger.info("   TARGET_TIME                     =" + properties.get_target_time());
            Logger.info("   ---");
            
            // Creating subfolders if they do no exist.
            CreateSubFolders.create_subfolders();
            
            // Treatment of old receipts
            update_statusfield(GuiStart.StatusField.STATUSFIELD1,"Archiving old receipts ... ");
            Archiving_Old_Receipts.execute(properties,guistart);
            update_statusfield(GuiStart.StatusField.STATUSFIELD1,"Archiving old receipts ... OK.");
            
            // Labels abfuellen
            update_statusfield(GuiStart.StatusField.STATUSFIELD2,"Filling up language labels ... ");
            Labels.fillup(properties.get_language());
            update_statusfield(GuiStart.StatusField.STATUSFIELD2,"Filling up language labels ... OK.");
                   
            
            // Aufruf des GUI
            update_statusfield(GuiStart.StatusField.STATUSFIELD3,"GUI will be started ... OK.");
            SwingUtilities.invokeAndWait(new Runnable()
                {
                @Override
                public void run()
                    {
                    // Gui gui = new Gui(properties);
                    Gui gui = Gui.get_newInstance(properties);
                    gui.setExtendedState(gui.getExtendedState() | Frame.MAXIMIZED_BOTH);
                    gui.setVisible(true);
                    
                    guistart.setVisible(false);
                    }
                });
            
            ThreadAnalyzer.println_head();
            ThreadAnalyzer.println(Thread.currentThread(), "Main-Thread", "Starten des TFC 24", "Main-Methode des Transfer-Clients 2.4");
            
            }
        catch (Exception e)
            {
            
            // Die Meldung wird mit GuiStartupError angezeigt, da nicht sicher ist,
            // ob der Logger eingerichtet werden konnte (dies haengt von der
            // Lesbarkeit des Properties-Files ab.)
            
            SwingUtilities.invokeLater(new Runnable()
                {
                @Override
                public void run()
                    {
                    new GuiStartupError(e);
                    guistart.setVisible(false);
                    }
                });
            }
            
        }
    
    private static void update_statusfield(GuiStart.StatusField field,String text) throws Exception
        {
        SwingUtilities.invokeAndWait(new Runnable()
            {
            @Override
            public void run()
                {
                guistart.update_statusfield(field,text);
                }
            });        
        }
    
    
        
    }
