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

package ch.zh.transferclient.properties;

import java.io.*;
import java.nio.charset.StandardCharsets;

import ch.zh.transferclient.main.Logger;
import ch.zh.transferclient.properties.Properties;
import ch.zh.transferclient.properties.Properties.Language;

/**
 * This class is used to write the properties to the properties file when the application will be terminated by the end
 * user.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class PropertiesWriter
    
    {
    
    /**
     * Writes the properties to the properties file.
     * 
     * @param properties Properties to be written to the properties file.
     */
    public static void write(Properties properties)
        {
        
        try
            {
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("properties.txt"), StandardCharsets.UTF_8));
            
            bw.write("# ################################# #");
            bw.newLine();
            bw.write("#  SECTION 1: TECHNICAL PARAMETERS  #");
            bw.newLine();
            bw.write("# ################################# #");
            bw.newLine();
            bw.newLine();
            
            bw.write("# ZIP_COMPRESSION ");
            bw.newLine();
            bw.write("# Default value: TRUE");
            bw.newLine();
            if (properties.get_zip_compression())
                {
                bw.write("ZIP_COMPRESSION=TRUE");
                }
            else
                {
                bw.write("ZIP_COMPRESSION=FALSE");
                }
                
            bw.newLine();
            bw.newLine();
            bw.newLine();
            
            bw.write("# ################################## #");
            bw.newLine();
            bw.write("#  SECTION 2: LOGGING AND ARCHIVING  #");
            bw.newLine();
            bw.write("# ################################## #");
            bw.newLine();
            bw.newLine();
            bw.write("# LOG_OVERWRITE ");
            bw.newLine();
            bw.write("# Valid values: TRUE, FALSE");
            bw.newLine();
            bw.write("# Default value: FALSE");
            bw.newLine();
            bw.write("# TRUE means the following:  Log files are     overwritten every time the transfer client is started. ");
            bw.newLine();
            bw.write("# FALSE means the following: Log files are not overwritten every time the transfer client is started. ");
            bw.newLine();
            if (properties.get_log_overwrite())
                {
                bw.write("LOG_OVERWRITE=TRUE");
                bw.newLine();
                }
            else
                {
                bw.write("LOG_OVERWRITE=FALSE");
                bw.newLine();
                }
                
            bw.newLine();
            bw.write("# LOG_SUPPRESS_DEBUG ");
            bw.newLine();
            bw.write("# Valid values:  TRUE, FALSE");
            bw.newLine();
            bw.write("# Default value: TRUE");
            bw.newLine();
            bw.write("# TRUE means the following:  Log messages which are only of interest for debugging purposes are suppressed. ");
            bw.newLine();
            bw.write("# FALSE means the following: Log messages which are only of interest for debugging purposes are not suppressed. ");
            bw.newLine();
            if (properties.get_log_suppress_debug())
                {
                bw.write("LOG_SUPPRESS_DEBUG=TRUE");
                bw.newLine();
                }
            else
                {
                bw.write("LOG_SUPPRESS_DEBUG=FALSE");
                bw.newLine();
                }
                
            bw.newLine();
            
            bw.write("# ARCHIVE_DATAFILES ");
            bw.newLine();
            bw.write("# Valid values:  TRUE, FALSE");
            bw.newLine();
            bw.write("# Default value: TRUE");
            bw.newLine();
            bw.write("# Explanation:");
            bw.newLine();
            bw.write("# TRUE means the following:  Processed data files will be archived under \"$TRANSFERCLIENT_FOLDER/archive/data/\". ");
            bw.newLine();
            bw.write("# FALSE means the following: Processed data files will not be archived by the transfer client. ");
            bw.newLine();
            if (properties.get_archive_datafiles())
                {
                bw.write("ARCHIVE_DATAFILES=TRUE");
                bw.newLine();
                }
            else
                {
                bw.write("ARCHIVE_DATAFILES=FALSE");
                bw.newLine();
                }
                
            bw.newLine();
            bw.newLine();
            
            bw.write("# ##################### #");
            bw.newLine();
            bw.write("#  SECTION 3: LANGUAGE  #");
            bw.newLine();
            bw.write("# ##################### #");
            bw.newLine();
            bw.newLine();
            bw.write("# Valid values: " + Language.DE.toString() + ", " + Language.FR.toString() + ", "
                    + Language.IT.toString()/*
                                             * +", "+ Language.RM.toString()
                                             */);
            bw.newLine();
            bw.write("LANGUAGE=" + properties.get_language().toString());
            bw.newLine();
            
            bw.newLine();
            bw.newLine();
            
            bw.write("# ############################# #");
            bw.newLine();
            bw.write("#  SECTION 4: SEDEX PARAMETERS  #");
            bw.newLine();
            bw.write("# ############################# #");
            bw.newLine();
            bw.newLine();
            
            bw.write("# SEDEX SENDER ID");
            bw.newLine();
            // bw.write("# Example: 2-ZH-3");
            // bw.newLine();
            bw.write("SEDEX_SENDER_ID=" + properties.get_sedex_sender_id());
            bw.newLine();
            bw.newLine();
            
            bw.write("# SEDEX RECIPIENT ID ");
            bw.newLine();
            bw.write("# ");
            bw.newLine();
            bw.write("# RECIPIENTS                             SEDEX-ID         ");
            bw.newLine();
            bw.write("# ");
            bw.newLine();
            bw.write("# OFS/BFS Madeleine Schneider            4-010502-1       ");
            bw.newLine();
            bw.write("# OFS/BFS Corinne Straub                 4-925020-5       ");
            bw.newLine();
            bw.write("# OFS/BFS Julie Silberstein              4-460754-7       ");
            bw.newLine();
            bw.write("# OFS/BFS Alain Herzig                   4-346040-2       ");
            bw.newLine();
            bw.write("# OFS/BFS Oliver Zumbrunnen              4-960533-6       ");
            bw.newLine();
            bw.write("# ChF/BK                                 4-760515-6       ");
            bw.newLine();
            bw.write("# ChF/BK 2                               4-720361-0       ");
            bw.newLine();
            bw.write("# ChF/BK 3                               4-563119-5       ");
            bw.newLine();
            bw.write("# ChF/BK 4                               4-031888-0       ");
            bw.newLine();
            bw.write("# ChF/BK 5                               4-697032-9       ");
            bw.newLine();
            bw.write("# ChF/BK 6                               4-986237-5       ");
            bw.newLine();
            bw.write("# Support Transfer-Client                4-143849-0       ");
            bw.newLine();
            bw.write("# ");
            bw.newLine();
            bw.write("# It is important to note that the SEDEX-ID is followed   ");
            bw.newLine();
            bw.write("# by a label (separated from the SEDEX-ID by a % symbol). ");
            bw.newLine();
            bw.write("# These labels are not permitted to contain commas.       ");
            bw.newLine();
            bw.write("# ");
            bw.newLine();
            
            //Example configuration 1
            bw.write("# Example I of configuration:");
            bw.newLine();
            bw.write("# SEDEX_RECIPIENT_ID=");
            bw.write("4-010502-1%OFS/BFS Madeleine Schneider,");
            bw.write("4-925020-5%OFS/BFS Corinne Straub,");
            bw.write("4-460754-7%OFS/BFS Julie Silberstein,");
            bw.write("4-346040-2%OFS/BFS Alain Herzig,");
            bw.write("4-960533-6%OFS/BFS Oliver Zumbrunnen,");
            bw.write("4-760515-6%ChF/BK,");
            bw.write("4-720361-0%ChF/BK 2,");
            bw.write("4-563119-5%ChF/BK 3,");
            bw.write("4-031888-0%ChF/BK 4,");
            bw.write("4-697032-9%ChF/BK 5,");
            bw.write("4-143849-0%Support Transfer-Client");
            bw.newLine();
            bw.write("#");
            bw.newLine();
            
            //Example configuration 2
            bw.write("# Example II of configuration:");
            bw.newLine();
            bw.write("# SEDEX_RECIPIENT_ID=");
            bw.write("4-925020-5%OFS/BFS Corinne Straub,");
            bw.write("4-346040-2%OFS/BFS Alain Herzig,");
            bw.write("4-960533-6%OFS/BFS Oliver Zumbrunnen,");
            bw.write("4-143849-0%Support Transfer-Client");
            bw.newLine();
            bw.write("# ");
            bw.newLine();
            
            bw.write("SEDEX_RECIPIENT_ID=");
            for (int i = 0; i < properties.get_sedex_recipient_ids().size(); i++)
                {
                String element = properties.get_sedex_recipient_ids().get(i) + "%"
                        + properties.get_sedex_recipient_labels().get(i);
                bw.write(element + ",");
                }
            bw.newLine();
            bw.newLine();
            
            bw.write("# SEDEX DIRECTORIES");
            bw.newLine();
            bw.write("# Example values (sedex adapter is running on the client):");
            bw.newLine();
            bw.write("# SEDEX_DIR_OUTBOX   = c:/0_tfc_reliability/sedex/outbox");
            bw.newLine();
            bw.write("# SEDEX_DIR_RECEIPTS = c:/0_tfc_reliability/sedex/receipts");
            bw.newLine();
            bw.write("# Example values (sedex adapter is running on a server):");
            bw.newLine();
            bw.write("# SEDEX_DIR_OUTBOX   = //10.73.147.218/interface$/outbox");
            bw.newLine();
            bw.write("# SEDEX_DIR_RECEIPTS = //10.73.147.218/interface$/receipts");
            bw.newLine();
            bw.write("SEDEX_DIR_OUTBOX=" + properties.get_sedex_dir_outbox().replace("\\", "/"));
            bw.newLine();
            bw.write("SEDEX_DIR_RECEIPTS=" + properties.get_sedex_dir_receipts().replace("\\", "/"));
            bw.newLine();
            
            bw.newLine();
            bw.newLine();
            
            bw.write("# ################################ #");
            bw.newLine();
            bw.write("#  SECTION 5: END USER PARAMETERS  #");
            bw.newLine();
            bw.write("# ################################ #");
            bw.newLine();
            bw.newLine();
            bw.write("# FOLDER RESULTS ");
            bw.newLine();
            bw.write("# Example value: E:/results");
            bw.newLine();
            bw.write("FOLDER_RESULTS=" + properties.get_folder_results().replace("\\", "/"));
            bw.newLine();
            bw.newLine();
            
            bw.write("# TARGET TIME");
            bw.newLine();
            bw.write("# Valid values: 9:00, 9:05, 9:10, ..., 12:45, 12:50, 12:55, 13:00 ");
            bw.newLine();
            bw.write("TARGET_TIME=" + properties.get_target_time());
            bw.newLine();
            bw.newLine();
            bw.newLine();
            
            bw.write("# ############################################# #");
            bw.newLine();
            bw.write("#  SECTION 6: TRUNCATION OF THE PROTOCOL TABLE  #");
            bw.newLine();
            bw.write("# ############################################# #");
            bw.newLine();
            bw.newLine();
            bw.write("# Number of entries of the overview protocol ");
            bw.newLine();
            bw.write("# Min value:     1 ");
            bw.newLine();
            bw.write("# Max value:     1000 ");
            bw.newLine();
            bw.write("# Default value: 1000 ");
            bw.newLine();
            bw.write("NUMBER_OF_TABLE_ENTRIES=" + properties.get_number_of_table_entries());
            bw.newLine();
            
            bw.newLine();
            bw.newLine();
            
            bw.flush();
            
            bw.close();
            }
        catch (Exception e)
            {
            Logger.error(e);
            }
            
        }
        
    }
