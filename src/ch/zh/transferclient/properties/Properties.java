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

import java.util.*;

/**
 * This class is used to construct the properties object.
 * 
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Properties
    
    {
    
    /** Possible languages. */
    public static enum Language {
        /** German language. */
        DE,
        /** French language. */
        FR,
        /** Italian language. */
        IT,
        /** Romansh language. */
        RM
    };
    
    /** Indicator which indicates whether the files should be compressed or not. */
    private final boolean           zip_compression;          // Konfigurierbar im Konfigurationsfile
    
    /**
     * Indicator which indicates whether the log should be overwritten for every new session or not.
     */
    private final boolean           log_overwrite;            // Konfigurierbar im Konfigurationsfile
    /**
     * Indicator which indicates whether debug messages in the log should be suppressed or not.
     */
    private final boolean           log_suppress_debug;       // Konfigurierbar im Konfigurationsfile
    /**
     * Indicator which indicates whether transmitted files should be archived or not.
     */
    private final boolean           archive_datafiles;        // Konfigurierbar im Konfigurationsfile
    
    /** Language to be used in the graphical user interface. */
    private final Language          language;                 // Konfigurierbar im Konfigurationsfile
    
    /** Sedex ID of the sender. */
    private final String            sedex_sender_id;          // Konfigurierbar im Konfigurationsfile
    /** Sedex IDs of the recipients. */
    private final ArrayList<String> sedex_recipient_ids;      // Abgeleitet aus sedex_recipient_id
    /** Labels of the recipients. */
    private final ArrayList<String> sedex_recipient_labels;   // Abgeleitet aus sedex_recipient_id
    /** Outbox folder of the sedex adapter. */
    private final String            sedex_dir_outbox;         // Konfigurierbar im Konfigurationsfile
    /** Receipts folder of the sedex adapter. */
    private final String            sedex_dir_receipts;       // Konfigurierbar im Konfigurationsfile
    /**
     * Folder in which the end user puts the files with the results. Note: It is not a final field because it can be
     * changed by the end user within the graphical user interface.
     */
    private String                  folder_results = "";      // Konfigurierbar im Konfigurationsfile und im GUI
    /**
     * Time when the transfer client will be activated under the autoactivation mode. Note: It is not a final field
     * because it can be changed by the end user within the graphical user interface.
     * 
     */
    private String                  target_time    = "12:00"; // Konfigurierbar im Konfigurationsfile und im GUI
    
    /** Number of table entries. */
    private final int               number_of_table_entries;  // Konfigurierbar im Konfigurationsfile
    
    // ------------------------//
    // Zugriff auf Konstanten //
    // ------------------------//
    public boolean get_zip_compression()
        {
        return this.zip_compression;
        }
        
    /**
     * @return Indicator which indicates whether the log should be overwritten for every new session or not.
     */
    public boolean get_log_overwrite()
        {
        return this.log_overwrite;
        }
        
    /**
     * @return Indicator which indicates whether debug messages in the log should be suppressed or not.
     */
    public boolean get_log_suppress_debug()
        {
        return this.log_suppress_debug;
        }
        
    /**
     * @return Indicator which indicates whether transmitted files should be archived or not.
     */
    public boolean get_archive_datafiles()
        {
        return this.archive_datafiles;
        }
        
    /** @return Language to be used in the graphical user interface. */
    public Language get_language()
        {
        return this.language;
        }
        
    /** @return Sedex ID of the sender. */
    public String get_sedex_sender_id()
        {
        return this.sedex_sender_id;
        }
        
    /** @return Sedex IDs of the recipients. */
    public ArrayList<String> get_sedex_recipient_ids()
        {
        return this.sedex_recipient_ids;
        }
        
    /** @return Labels of the recipients. */
    public ArrayList<String> get_sedex_recipient_labels()
        {
        return this.sedex_recipient_labels;
        }
        
    /** @return Sedex outbox folder. */
    public String get_sedex_dir_outbox()
        {
        return this.sedex_dir_outbox;
        }
        
    /** @return Sedex receipts folder. */
    public String get_sedex_dir_receipts()
        {
        return this.sedex_dir_receipts;
        }
        
    /** @return Number of table entries. */
    public int get_number_of_table_entries()
        {
        return this.number_of_table_entries;
        }
        
    // ---------------------------------//
    // Zugriff auf veraenderbare Werte //
    // ---------------------------------//
    /** @return The folder of the result files. */
    public synchronized String get_folder_results()
        {
        return this.folder_results;
        }
        
    /**
     * @return The time when the transfer-client should be automatically activated.
     */
    public synchronized String get_target_time()
        {
        return this.target_time;
        }
        
    /** @param value The folder of the result files. */
    public synchronized void set_folder_results(String value)
        {
        this.folder_results = value;
        }
        
    /**
     * @param value The time when the transfer-client should be automatically activated.
     */
    public synchronized void set_target_time(String value)
        {
        this.target_time = value;
        }
        
    // -------------//
    // Konstruktor //
    // -------------//
    /**
     * Constructs a new properties object.
     * 
     * @param zip_compression         Indicator which indicates whether the files should be compressed or not.
     * 
     * @param log_overwrite           Indicator which indicates whether the log should be overwritten for every new
     *                                session or not.
     * @param log_suppress_debug      Indicator which indicates whether debug messages in the log should be suppressed
     *                                or not.
     * @param archive_datafiles       Indicator which indicates whether transmitted files should be archived or not.
     * 
     * @param language                Language to be used in the graphical user interface.
     * 
     * @param sedex_sender_id         Sedex ID of the sender.
     * @param sedex_recipient_id      Sedex IDs of the recipients.
     * @param sedex_dir_outbox        Sedex outbox folder.
     * @param sedex_dir_receipts      Sedex receipts folder.
     * 
     * @param dir_results             The folder of the result files.
     * @param target_time             The time when the transfer-client should be automatically activated.
     * 
     * @param number_of_table_entries Number of table entries.
     */
    protected Properties
    /* @formatter:off */
        (
        boolean zip_compression,
        boolean log_overwrite,
        boolean log_suppress_debug,
        boolean archive_datafiles,
        Language language,
        String sedex_sender_id,
        String sedex_recipient_id,
        String sedex_dir_outbox,
        String sedex_dir_receipts,
        String dir_results,
        String target_time,
        int number_of_table_entries
        )
        /* @formatter:on */
        {
        
        this.zip_compression        = zip_compression;
        
        this.log_overwrite          = log_overwrite;
        this.log_suppress_debug     = log_suppress_debug;
        this.archive_datafiles      = archive_datafiles;
        
        this.language               = language;
        
        this.sedex_sender_id        = sedex_sender_id;
        this.sedex_recipient_ids    = new ArrayList<String>();
        this.sedex_recipient_labels = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(sedex_recipient_id, ",");
        while (st.hasMoreTokens())
            {
            
            String token = st.nextToken();
            
            if (token.contains("%"))
                {
                
                String id;
                String label;
                
                if (token.split("%").length==2)
                    {
                    id    = token.split("%")[0];
                    label = token.split("%")[1];
                    }
                else
                    {
                    id    = token.split("%")[0];
                    label = "Missing Label";
                    }
                
                this.sedex_recipient_ids.add(id);
                this.sedex_recipient_labels.add(label);
                }
            else
                {
                String id    = token;
                String label = "Missing Label";
                
                this.sedex_recipient_ids.add(id);
                this.sedex_recipient_labels.add(label);
                }
            
            }
        
            
        this.sedex_dir_outbox        = sedex_dir_outbox;
        this.sedex_dir_receipts      = sedex_dir_receipts;
        this.folder_results          = dir_results;
        this.target_time             = target_time;
        this.number_of_table_entries = number_of_table_entries;
        }
        
    }
