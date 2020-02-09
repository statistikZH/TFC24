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

package ch.zh.transferclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used to generate TimeStamps.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class TimeStamp
    
    {
    
    /** Date format for the sedex message id. */
    private static final SimpleDateFormat SDF_FOR_SEDEX_MESSAGE_ID = new SimpleDateFormat("yyyyMMdd-HHmm-ss-SSS");
    /** Date format for the sedex envelope. */
    private static final SimpleDateFormat SDF_FOR_SEDEX_ENVELOPE   = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    /** Date format for the log file. */
    // private static final SimpleDateFormat SDF_FOR_LOGFILE = new
    // SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    private static final SimpleDateFormat SDF_FOR_LOGFILE          = new SimpleDateFormat("yyyyMMdd-HHmm-ss-SSS");
    
    /**
     * Constructs a TimeStamp object.
     */
    private TimeStamp()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Returns the date format for the sedex message id.
     * 
     * @return Date format for the sedex message id.
     */
    public static String getstamp_for_sedex_message_id()
        {
        return SDF_FOR_SEDEX_MESSAGE_ID.format(new Date());
        }
        
    /**
     * Returns the date format for the sedex envelope.
     * 
     * @return Date format for the sedex envelope.
     */
    public static String getstamp_for_sedex_envelope()
        {
        return SDF_FOR_SEDEX_ENVELOPE.format(new Date());
        }
        
    /**
     * Returns the date format for the log file.
     * 
     * @return Date format for the log file.
     */
    public static String getstamp_for_logfile()
        {
        return SDF_FOR_LOGFILE.format(new Date());
        }
        
    }
