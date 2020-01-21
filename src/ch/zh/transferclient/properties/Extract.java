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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * This class is used to extract the properties from the properties file.
 * 
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Extract
    
    {
    
    /** Delay in milliseconds between the termination of one execution and the commencement of the next. */
    private final String delay;
    
    /** Indicator which indicates whether the files should be compressed or not. */
    private final String zip_compression;
    
    /**
     * Indicator which indicates whether the log should be overwritten for every new session or not.
     */
    private final String log_overwrite;
    /**
     * Indicator which indicates whether debug messages in the log should be suppressed or not.
     */
    private final String log_suppress_debug;
    /**
     * Indicator which indicates whether transmitted files should be archived or not.
     */
    private final String archive_datafiles;
    
    /** Language to be used in the graphical user interface. */
    private final String language;
    
    /** Sedex ID of the sender. */
    private final String sedex_sender_id;
    /** Sedex IDs of the recipients. */
    private final String sedex_recipient_id;
    /** Sedex folder. */
    private final String sedex_dir;
    /** Sedex outbox folder. */
    private final String sedex_dir_outbox;
    /** Sedex receipts folder. */
    private final String sedex_dir_receipts;
    
    /** The folder of the result files. */
    private final String folder_results;
    /** The time when the transfer-client should be automatically activated. */
    private final String target_time;
    
    /** The number of table entries. */
    private final String number_of_table_entries;
    
    /**
     * @return Delay in milliseconds between the termination of one execution and the commencement of the next.
     */
    protected String get_delay()
        {
        return this.delay;
        }
    
    /**
     * @return Indicator which indicates whether the files should be compressed or not.
     */
    protected String get_zip_compression()
        {
        return this.zip_compression;
        }
        
    /**
     * @return Indicator which indicates whether the log should be overwritten for every new session or not.
     */
    protected String get_log_overwrite()
        {
        return this.log_overwrite;
        }
        
    /**
     * @return Indicator which indicates whether debug messages in the log should be suppressed or not.
     */
    protected String get_log_suppress_debug()
        {
        return this.log_suppress_debug;
        }
        
    /**
     * @return Indicator which indicates whether transmitted files should be archived or not.
     */
    protected String get_archive_datafiles()
        {
        return this.archive_datafiles;
        }
        
    /** @return Language to be used in the graphical user interface. */
    protected String get_language()
        {
        return this.language;
        }
        
    /** @return Sedex ID of the sender. */
    protected String get_sedex_sender_id()
        {
        return this.sedex_sender_id;
        }
        
    /** @return Sedex IDs of the recipients. */
    protected String get_sedex_recipient_id()
        {
        return this.sedex_recipient_id;
        }
        
    /** @return Sedex folder. */
    protected String get_sedex_dir()
        {
        return this.sedex_dir;
        }
        
    /** @return Sedex outbox folder. */
    protected String get_sedex_dir_outbox()
        {
        return this.sedex_dir_outbox;
        }
        
    /** @return Sedex receipts folder. */
    protected String get_sedex_dir_receipts()
        {
        return this.sedex_dir_receipts;
        }
        
    /** @return The folder of the result files. */
    protected String get_folder_results()
        {
        return this.folder_results;
        }
        
    /**
     * @return The time when the transfer-client should be automatically activated.
     */
    protected String get_target_time()
        {
        return this.target_time;
        }
        
    /** @return The number of table entries. */
    protected String get_number_of_table_entries()
        {
        return this.number_of_table_entries;
        }
        
    /**
     * Extracts the properties from the properties file.
     * 
     * @throws Exception Exception thrown if the properties cannot be extracted.
     */
    protected Extract() throws Exception
        {
        
        String              delay                           = "";
        String              zip_compression                 = "";
        
        String              log_overwrite                   = "";
        String              log_suppress_debug              = "";
        String              archive_datafiles               = "";
        
        String              language                        = "";
        
        String              sedex_sender_id                 = "";
        String              sedex_recipient_id              = "";
        String              sedex_dir                       = "";
        String              sedex_dir_outbox                = "";
        String              sedex_dir_receipts              = "";
        
        String              folder_results                  = "";
        String              target_time                     = "";
        
        String              number_of_table_entries         = "";
        
        // Identifizierung des Ausführungsverzeichnisses
        // Das Properties-File wir im gleichen Verzeichnis gesucht,
        // wo sich das zur Ausführung gebrachte JAR-File befindet.
        String              dir_ausfuehrung                 = System.getProperty("user.dir");
        
        BufferedInputStream stream                          = new BufferedInputStream(new FileInputStream(dir_ausfuehrung
                + "/properties.txt"));
        
        BufferedReader      r                               = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        
        String              line;
        while ((line = r.readLine()) != null)
            {
            
            if (!line.trim().startsWith("#"))
                {
                if (line.contains("="))
                    {
                    String[] paare     = line.split("=");
                    
                    String   parameter = paare[0].toLowerCase().trim();
                    
                    if (parameter.equals("delay"))
                        {
                        if (paare.length > 1)
                            {
                            delay = paare[1].trim();
                            }
                        }
                    else if (parameter.equals("zip_compression"))
                        {
                        if (paare.length > 1)
                            {
                            zip_compression = paare[1].trim();
                            }
                        }
                    else if (parameter.equals("log_overwrite"))
                        {
                        if (paare.length > 1)
                            {
                            log_overwrite = paare[1].trim();
                            }
                        }
                    else if (parameter.equals("log_suppress_debug"))
                        {
                        if (paare.length > 1)
                            {
                            log_suppress_debug = paare[1].trim();
                            }
                        }
                    else if (parameter.equals("archive_datafiles"))
                        {
                        if (paare.length > 1)
                            {
                            archive_datafiles = paare[1].trim();
                            }
                        }
                    else if (parameter.equals("language"))
                        {
                        if (paare.length > 1)
                            {
                            language = paare[1].trim();
                            }
                        }
                    else if (parameter.equals("sedex_sender_id"))
                        {
                        if (paare.length > 1)
                            {
                            sedex_sender_id = paare[1].trim();
                            }
                        }
                    else if (parameter.equals("sedex_recipient_id"))
                        {
                        if (paare.length > 1)
                            {
                            sedex_recipient_id = paare[1].trim();
                            }
                        }
                    else if (parameter.equals("sedex_dir"))
                        {
                        
                        if (paare.length > 1)
                            {
                            
                            // Warum nicht replaceAll()? in der folgenden Zeile?
                            // Grund:
                            // https://stackoverflow.com/questions/6805028/how-to-replace-backward-slash-to-forward-slash-using-java
                            sedex_dir = paare[1].replace("\\", "/").trim();
                            }
                        }
                        
                    else if (parameter.equals("sedex_dir_outbox"))
                        {
                        
                        if (paare.length > 1)
                            {
                            
                            // Warum nicht replaceAll()? in der folgenden Zeile?
                            // Grund:
                            // https://stackoverflow.com/questions/6805028/how-to-replace-backward-slash-to-forward-slash-using-java
                            sedex_dir_outbox = paare[1].replace("\\", "/").trim();
                            }
                        }
                        
                    else if (parameter.equals("sedex_dir_receipts"))
                        {
                        
                        if (paare.length > 1)
                            {
                            
                            // Warum nicht replaceAll()? in der folgenden Zeile?
                            // Grund:
                            // https://stackoverflow.com/questions/6805028/how-to-replace-backward-slash-to-forward-slash-using-java
                            sedex_dir_receipts = paare[1].replace("\\", "/").trim();
                            }
                        }
                        
                    else if (parameter.equals("folder_results"))
                        
                        {
                        if (paare.length > 1)
                            {
                            
                            // Warum nicht replaceAll()? in der folgenden Zeile?
                            // Grund:
                            // https://stackoverflow.com/questions/6805028/how-to-replace-backward-slash-to-forward-slash-using-java
                            folder_results = paare[1].replace("\\", "/").trim();
                            }
                        }
                        
                    else if (parameter.equals("target_time"))
                        {
                        if (paare.length > 1)
                            {
                            target_time = paare[1].trim();
                            }
                        }
                        
                    else if (parameter.equals("number_of_table_entries"))
                        {
                        if (paare.length > 1)
                            {
                            number_of_table_entries = paare[1].trim();
                            }
                        }
                        
                    }
                    
                }
                
            }
            
        r.close();
        stream.close();
        
        this.delay                           = delay;
        this.zip_compression                 = zip_compression;
        
        this.log_overwrite                   = log_overwrite;
        this.log_suppress_debug              = log_suppress_debug;
        this.archive_datafiles               = archive_datafiles;
        
        this.language                        = language;
        
        this.sedex_sender_id                 = sedex_sender_id;
        this.sedex_recipient_id              = sedex_recipient_id;
        this.sedex_dir                       = sedex_dir;
        this.sedex_dir_outbox                = sedex_dir_outbox;
        this.sedex_dir_receipts              = sedex_dir_receipts;
        
        this.folder_results                  = folder_results;
        this.target_time                     = target_time;
        
        this.number_of_table_entries         = number_of_table_entries;
        
        }
        
    }
