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

import java.nio.file.*;

import ch.zh.transferclient.main.*;
import ch.zh.transferclient.properties.Properties.Language;

/**
 * This class is used to construct the properties object.
 * 
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class PropertiesReader
    
    {
    
    /**
     * Returns the properties to be used.
     * 
     * @return           Properties to be used.
     * @throws Exception The properties cannot be extracted.
     */
    public static Properties get_properties() throws Exception
        
        {
        
        Extract extract                        = new Extract();
        
        String  zip_compression_string         = extract.get_zip_compression();
        
        String  log_overwrite_string           = extract.get_log_overwrite();
        String  log_suppress_debug_string      = extract.get_log_suppress_debug();
        String  archive_datafiles_string       = extract.get_archive_datafiles();
        
        String  language_string                = extract.get_language();
        
        String  sedex_sender_id                = extract.get_sedex_sender_id();
        String  sedex_recipient_id             = extract.get_sedex_recipient_id();
        String  sedex_dir                      = extract.get_sedex_dir();
        String  sedex_dir_outbox               = extract.get_sedex_dir_outbox();
        String  sedex_dir_receipts             = extract.get_sedex_dir_receipts();
        
        String  folder_results                 = extract.get_folder_results();
        String  target_time                    = extract.get_target_time();
        
        String  number_of_table_entries_string = extract.get_number_of_table_entries();
        
        // --------------------------------------//
        // Validierungen der extrahierten Werte //
        // --------------------------------------//
        
        // ZIP compression testen
        boolean zip_compression                = true;
        if (zip_compression_string.toLowerCase().equals("false"))
            {
            zip_compression = false;
            }
            
        // Log overwrite testen
        boolean log_overwrite = false;
        if (log_overwrite_string.toLowerCase().equals("true"))
            {
            log_overwrite = true;
            }
            
        // Log level testen
        boolean log_suppress_debug = true;
        if (log_suppress_debug_string.toLowerCase().equals("false"))
            {
            log_suppress_debug = false;
            }
            
        // Archive testen
        boolean archive_datafiles = true;
        if (archive_datafiles_string.toLowerCase().equals("false"))
            {
            archive_datafiles = false;
            }
            
        // Language testen
        Language language = Language.DE;
        if (language_string.toUpperCase().equals(Language.DE.toString()))
            {
            language = Language.DE;
            }
        else if (language_string.toUpperCase().equals(Language.FR.toString()))
            {
            language = Language.FR;
            }
        else if (language_string.toUpperCase().equals(Language.IT.toString()))
            {
            language = Language.IT;
            }
        else if (language_string.toUpperCase().equals(Language.RM.toString()))
            {
            language = Language.RM;
            }
            
        // Sedex Sender ID testen
        if (sedex_sender_id.equals(""))
            {
            throw new Exception("Es wurde keine Sedex Sender ID im Properties-File hinterlegt.");
            }
            
        // Sedex Sender ID testen
        if (sedex_recipient_id.equals(""))
            {
            throw new Exception("Es wurde keine Sedex Recipient ID im Properties-File hinterlegt.");
            }
            
        // -------------------------------------//
        // Bestimmung des Sedex Outbox Ordners //
        // -------------------------------------//
        // Falls der Sedex Outbox Ordner im properties-File
        // definiert ist, wird er sofort übernommen.
        if (!sedex_dir_outbox.equals(""))
            {
            try
                {
                Path path = Paths.get(sedex_dir_outbox); // Illegal char - Exception kann hier geworfen werden.
                if (!Files.exists(path))
                    {
                    throw new Exception("Es ist kein gültiger Sedex-Outbox-Ordner im Properties-File hinterlegt: "
                            + sedex_dir_outbox);
                    }
                }
            catch (Exception e)
                {
                throw new Exception("Es ist kein gültiger Sedex-Outbox-Ordner im Properties-File hinterlegt: "
                        + sedex_dir_outbox);
                }
            }
        // Falls dieser nicht im properties-File definiert ist, wird geschaut,
        // ob der "interface/outbox" (default) im allenfalls angegebenen
        // Sedex-Rootverzeichnis allenfalls vorhanden ist.
        else
            {
            
            if (!sedex_dir.equals(""))
                {
                
                try
                    {
                    Path path = Paths.get(sedex_dir + "/interface/outbox"); // Illegal char - Exception kann hier
                                                                            // geworfen werden.
                    if (!Files.exists(path))
                        {
                        throw new Exception("Es ist kein gültiger Sedex-Outbox-Ordner im Properties-File hinterlegt: "
                                + sedex_dir);
                        }
                    else
                        {
                        sedex_dir_outbox = sedex_dir + "/interface/outbox";
                        }
                    }
                catch (Exception e)
                    {
                    throw new Exception("Es ist kein gültiger Sedex-Outbox-Ordner im Properties-File hinterlegt: "
                            + sedex_dir);
                    }
                }
            }
            
        // --------------------------------------//
        // Bestimmung des Sedex Receipt Ordners //
        // --------------------------------------//
        // Falls der Sedex Receipt Ordner im properties-File
        // definiert ist, wird er sofort übernommen.
        if (!sedex_dir_receipts.equals(""))
            {
            try
                {
                Path path = Paths.get(sedex_dir_receipts); // Illegal char - Exception kann hier geworfen werden.
                if (!Files.exists(path))
                    {
                    throw new Exception("Es ist kein gültiger Sedex-Receipts-Ordner im Properties-File hinterlegt: "
                            + sedex_dir_outbox);
                    }
                }
            catch (Exception e)
                {
                throw new Exception("Es ist kein gültiger Sedex-Receipts-Ordner im Properties-File hinterlegt: "
                        + sedex_dir_outbox);
                }
            }
        // Falls dieser nicht im properties-File definiert ist, wird geschaut,
        // ob der "interface/receipts" (default) im allenfalls angegebenen
        // Sedex-Rootverzeichnis allenfalls vorhanden ist.
        else
            {
            
            if (!sedex_dir.equals(""))
                {
                
                try
                    {
                    Path path = Paths.get(sedex_dir + "/interface/receipts"); // Illegal char - Exception kann hier
                                                                              // geworfen werden.
                    if (!Files.exists(path))
                        {
                        throw new Exception("Es ist kein gültiger Sedex-Receipts-Ordner im Properties-File hinterlegt: "
                                + sedex_dir);
                        }
                    else
                        {
                        sedex_dir_receipts = sedex_dir + "/interface/receipts";
                        }
                    }
                catch (Exception e)
                    {
                    throw new Exception("Es ist kein gültiger Sedex-Receipts-Ordner im Properties-File hinterlegt: "
                            + sedex_dir);
                    }
                }
            }
            
        // ResultDir-Ordner testen
        if (folder_results != "")
            {
            try
                {
                Path path = Paths.get(folder_results); // Illegal char - Exception kann hier geworfen werden.
                if (!Files.exists(path))
                    {
                    folder_results = "";
                    }
                }
            catch (Exception e)
                {
                folder_results = "";
                }
            }
            
        // Zielzeit testen
        if (!target_time.equals(""))
            {
            
            if (!Conf.AUTOACTIVATION_TIMES.contains(target_time))
                {
                target_time = "12:00";
                }
            }
        else
            {
            target_time = "12:00";
            }
            
        // Number of table entries
        int number_of_table_entries = 1000;
        try
            {
            number_of_table_entries = Integer.valueOf(number_of_table_entries_string);
            }
        catch (Exception e)
            {
            number_of_table_entries = 1000;
            }
        if (number_of_table_entries > 1000)
            {
            number_of_table_entries = 1000;
            }
        else if (number_of_table_entries < 1)
            {
            number_of_table_entries = 1;
            }
            
        Properties properties = new Properties(zip_compression, log_overwrite, log_suppress_debug, archive_datafiles, language, sedex_sender_id, sedex_recipient_id, sedex_dir_outbox, sedex_dir_receipts, folder_results, target_time, number_of_table_entries);
        
        return properties;
        
        }
        
    }
