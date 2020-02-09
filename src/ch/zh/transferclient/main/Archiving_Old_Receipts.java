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

import java.io.File;
import java.nio.file.Files;

import javax.swing.SwingUtilities;

import static java.nio.file.StandardCopyOption.*;

import ch.zh.transferclient.gui.*;
import ch.zh.transferclient.properties.*;


/**
 * This class is used to delete old receipts.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class Archiving_Old_Receipts
    
    {
    
    /**
     * Constructs a Archiving_Old_Receipts object.
     */
    private Archiving_Old_Receipts()
        {
      //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Deletes all files within the sedex receipts folder.
     *
     * @param  properties Properties to be used.
     * @param  guistart   The guistart object to be used.
     * @throws Exception  The treatment of old properties failed.
     */
    protected static void execute(Properties properties,GuiStart guistart) throws Exception
        {
        
        String       sedex_dir_receipts = properties.get_sedex_dir_receipts();
        
        final File   dir                = new File(sedex_dir_receipts);
        
        final File[] files              = dir.listFiles();
        
        if (files.length==0)
            {
            SwingUtilities.invokeAndWait(new Runnable()
                {
                @Override
                public void run()
                    {
                    try
                        {
                        String update_text = "0 of 0 old receipts archived.";
                        guistart.update_statusfield(GuiStart.StatusField.STATUSFIELD1,update_text);
                        }
                    catch(Exception e)
                        {
                        // Hier koennte hoechstens eine null pointer exception geworfen werden,
                        // wenn das guistart-Objekt noch nicht durch den EDT-Thread fertig 
                        // konstruiert ist (vgl. main-Methode). Durch Verwendung von invokeAndWait 
                        // in der main-Methode ist jedoch sichergestellt, dass das 
                        // guistart-Objekt bereits durch den EDT-Thread erstellt worden ist. 
                        
                        // Damit die null pointer exception auch geworfen wird, muss sich
                        // der try-catch-Block innerhalb des Runnable Objekts befinden,
                        // da sonst nur eine nichtssagende  
                        // java.lang.reflect.InvocationTargetException Ausnahme geworfen
                        // werden wuerde.
                        }
                    }
                });
            }
        
       
        for (int i = 0; i < files.length; i++)
            {
            
            Files.move(files[i].toPath(), new File("archive/receipts/"
                    + files[i].getName()).toPath(), REPLACE_EXISTING);
            
            final int i_final = i;
            
            SwingUtilities.invokeAndWait(new Runnable()
                {
                @Override
                public void run()
                    {
                    try
                        {
                        String update_text = i_final + " of " + files.length + " old receipts archived.";
                        guistart.update_statusfield(GuiStart.StatusField.STATUSFIELD1,update_text);
                        }
                    catch(Exception e)
                        {
                        // Hier koennte hoechstens eine null pointer exception geworfen werden,
                        // wenn das guistart-Objekt noch nicht durch den EDT-Thread fertig 
                        // konstruiert ist (vgl. main-Methode). Durch Verwendung von invokeAndWait 
                        // in der main-Methode ist jedoch sichergestellt, dass das 
                        // guistart-Objekt bereits durch den EDT-Thread erstellt worden ist. 
                        
                        // Damit die null pointer exception auch geworfen wird, muss sich
                        // der try-catch-Block innerhalb des Runnable Objekts befinden,
                        // da sonst nur eine nichtssagende  
                        // java.lang.reflect.InvocationTargetException Ausnahme geworfen
                        // werden wuerde.
                        }
                    
                    }
                });
            
            
            Logger.info("OLD RECEIPT MOVED TO archive/receipts: " + files[i].getName());
            
            }
        
        //Nachfolgend werden Files ins Archiv verschoben, 
        //die aufgrund von ausserordentlichen Unterbruechen 
        //im Stage-Folder zurueckgeblieben sind.
        File dir_stage = new File("stage/");
        final File[] files_stage = dir_stage.listFiles();
        
        for (int i = 0; i < files_stage.length; i++)
            {
            
            Files.move(files_stage[i].toPath(), new File("archive/stage/"
                    + files_stage[i].getName()).toPath(), REPLACE_EXISTING);
            
            Logger.info("UNPROCESSED FILE MOVED FROM stage TO archive/stage: " + files[i].getName());
            
            }
            
        }
        
    }
