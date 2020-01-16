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

import ch.zh.transferclient.controller.*;
import ch.zh.transferclient.properties.*;

/**
 * This class is used to exit the application.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Exit
    
    {
    
    /**
     * Exits the application.
     *
     * @param properties The reference to the properties object to be used.
     * @param controller The reference to the controller object to be used.
     */
    public static void execute(Properties properties, Controller controller)
        {
        
        // --------------------------------------------------//
        // WRITING THE PROPERTIES TO THE PROPERTIES-FILE //
        // --------------------------------------------------//
        
        PropertiesWriter.write(properties);
        
        // --------------------------------------------------//
        // SHUTDOWN THE EXECUTOR-SERVICE //
        // --------------------------------------------------//
        //
        // The following text originates from
        // Source:
        // http://tutorials.jenkov.com/java-util-concurrent/executorservice.html#executorservice-shutdown
        //
        // Shutdown the Executor Service
        // When you are done using the Java ExecutorService you should
        // shut it down, so the threads do not keep running.
        // If your application is started via a main() method and
        // your main thread exits your application, the application will
        // keep running if you have an active ExexutorService in
        // your application.
        // The active threads inside this ExecutorService prevents
        // the JVM from shutting down.
        
        controller.deactivation();
        
        // --------------------------------------------------//
        // CLOSING THE LOGFILE //
        // --------------------------------------------------//
        Logger.info("##### END SESSION #####");
        Logger.close_bufferedwriter();
        
        // --------------------------------------------------//
        // SYSTEM EXIT //
        // --------------------------------------------------//
        
        // Zu System.exit(0) (vgl. nachfolgender Befehl) ist folgendes zu beachten:
        // https://stackoverflow.com/questions/12210972/setdefaultcloseoperation-to-show-a-jframe-instead
        // https://stackoverflow.com/questions/12572456/what-are-the-differences-between-calling-system-exit0-and-thread-currentthread
        
        System.exit(0);
        }
        
    }
