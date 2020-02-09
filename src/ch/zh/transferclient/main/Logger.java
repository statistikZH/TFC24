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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import ch.zh.transferclient.util.*;

/**
 * This class is used for logging messages.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class Logger
    
    {
    
    /** Available log levels. */
    public enum LogLevel {
        DEBUG, INFO
    };
    
    /** Indicates whether the debug level messages should be suppressed or not. */
    private static boolean        suppress_debug = true;
    
    /** Writer which is used to write to the log. */
    private static BufferedWriter bw;
    
    /**
     * Constructs a Logger object.
     */
    private Logger()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Initializes the logger.
     *
     * @param overwrite Indicates whether the log shall be overwritten always or not.
     * @param suppress  Indicates whether the debug messages should be suppressed or not.
     * 
     */
    public static synchronized void initialize(boolean overwrite, boolean suppress)
        {
        
        suppress_debug = suppress;
        
        try
            {
            if (bw != null)
                {
                bw.flush();
                bw.close();
                }
                
            File log_folder = new File("log");
            if (!((log_folder.exists()) && (log_folder.isDirectory())))
                {
                log_folder.mkdirs();
                }
                
            if (overwrite)
                {
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log/log.log"), StandardCharsets.UTF_8));
                }
            else
                {
                String timestamp = TimeStamp.getstamp_for_logfile();
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log/log_" + timestamp
                        + ".log"), StandardCharsets.UTF_8));
                }
                
            }
        catch (Exception e)
            {
            e.printStackTrace();
            }
            
        }
        
    /**
     * Closes the logger.
     *
     */
    public static synchronized void close_bufferedwriter()
        {
        try
            {
            bw.flush();
            bw.close();
            }
        catch (Exception e)
            {
            error(e);
            }
        }
        
    /**
     * Logs a debug message.
     * 
     * @param message The String to be written to the log.
     */
    public static synchronized void debug(String message)
        {
        if (!suppress_debug)
            {
            write_to_log(LogLevel.DEBUG, message);
            }
        }
        
    /**
     * Logs a info message.
     * 
     * @param message The String to be written to the log.
     */
    public static synchronized void info(String message)
        {
        write_to_log(LogLevel.INFO, message);
        }
        
    /**
     * Writes to the log.
     * 
     * @param level   The level of the message.
     * @param message The String to be written to the log.
     */
    private static synchronized void write_to_log(LogLevel level, String message)
        {
        
        Date   date = new Date();
        
        String line = "[ " + date.toString() + " ][ " + level.toString() + " ] " + message;
        
        try
            {
            
            if (Conf.LOG_ALSO_TO_CONSOLE)
                {
                System.out.println(line);
                }
                
            bw.write(line);
            bw.newLine();
            bw.flush();
            }
        catch (Exception e)
            {
            e.printStackTrace();
            }
            
        }
        
    /**
     * Writes an error message to the log.
     * 
     * @param exception Exception to be written to the log.
     */
    public static synchronized void error(Exception exception)
        {
        
        Date date = new Date();
        try
            {
            
            if (Conf.LOG_ALSO_TO_CONSOLE)
                {
                
                System.out.println("");
                System.out.println("[ " + date.toString() + " ][ ERROR ][ EXCEPTION   ] " + exception.toString());
                for (int i = 0; i < exception.getStackTrace().length; i++)
                    {
                    System.out.println("[ " + date.toString() + " ][ ERROR ][ STACK TRACE ] "
                            + exception.getStackTrace()[i].toString());
                    }
                    
                }
                
            bw.newLine();
            bw.write("[ " + date.toString() + " ][ ERROR ][ EXCEPTION   ] " + exception.toString());
            bw.newLine();
            for (int i = 0; i < exception.getStackTrace().length; i++)
                {
                bw.write("[ " + date.toString() + " ][ ERROR ][ STACK TRACE ] "
                        + exception.getStackTrace()[i].toString());
                bw.newLine();
                }
            bw.flush();
            
            }
        catch (Exception e)
            {
            e.printStackTrace();
            }
        }
        
    }
