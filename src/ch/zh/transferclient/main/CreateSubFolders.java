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

package ch.zh.transferclient.main;

import java.io.File;

/**
 * The class is used to create the needed subfolders if they do not exist.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class CreateSubFolders
    
    {
    
    /**
     * Creates the needed subfolders if they do not exist.
     *
     */
    protected static void create_subfolders()
        
        {
        
        File folder_stage = new File("stage");
        if (!((folder_stage.exists()) && (folder_stage.isDirectory())))
            {
            folder_stage.mkdirs();
            Logger.info("SUBFOLDER HAS BEEN CREATED: stage");
            }
            
        File folder_archive_data = new File("archive/data/");
        if (!((folder_archive_data.exists()) && (folder_archive_data.isDirectory())))
            {
            folder_archive_data.mkdirs();
            Logger.info("SUBFOLDER HAS BEEN CREATED: archive/data");
            }
            
        File folder_archive_receipts = new File("archive/receipts/");
        if (!folder_archive_receipts.exists())
            {
            folder_archive_receipts.mkdirs();
            Logger.info("SUBFOLDER HAS BEEN CREATED: archive/receipts");
            }
        
        //Im folgenden Subordner werden Files aufbewahrt,
        //die aufgrund von ausserordentlichen Unterbruechen
        //im Stage-Folder zurueckgeblieben sind.
        File folder_archive_stage = new File("archive/stage/");
        if (!folder_archive_stage.exists())
            {
            folder_archive_stage.mkdirs();
            Logger.info("SUBFOLDER HAS BEEN CREATED: archive/stage");
            }
            
        }
        
    }
