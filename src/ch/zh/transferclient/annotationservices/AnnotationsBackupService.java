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

package ch.zh.transferclient.annotationservices;

import java.io.File;
import java.nio.file.*;
import java.util.Iterator;

/**
 * This class is used to write backups of the source files of the Transfer-Client.
 * 
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 *
 */
public class AnnotationsBackupService
    {
    
    /**
     * Constructs a AnnotationsBackupService object.
     */
    private AnnotationsBackupService()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Source folder.
     */
    private static final String SOURCE_FOLDER = "src";
    
    /**
     * Backup folder.
     */
    private static final String TARGET_FOLDER = "src_old";
    
    /**
     * Writes backups of src to src_old.
     * 
     */
    protected static void backup_to_src_old()
        {
        
        try
            {
            Iterator<Path> it = Files.walk(Paths.get(SOURCE_FOLDER)).filter(p -> p.toString().endsWith(".java")).iterator();
            
            while (it.hasNext())
                {
                Path   source_path     = it.next();
                
                String target_filename = TARGET_FOLDER + "/" + AnnotationsTimeStamp.get_timestamp() + "_"
                        + source_path.toString().replaceAll("\\\\", "_");
                Path   target_path     = new File(target_filename).toPath();
                
                Files.copy(source_path, target_path, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        catch (Exception e)
            {
            e.printStackTrace();
            }
            
        }
        
    }
