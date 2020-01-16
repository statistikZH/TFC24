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

import java.awt.*;
import java.net.URL;
import java.util.*;

import javax.swing.ImageIcon;

/**
 * This class is used to specify parameters which are not accessible by the external configuration file.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Conf
    
    {
    
    /** Version of the Transfer-Client. */
    public static final String            VERSION                     = "2.4";
    
    /**
     * Indicator which indicates whether the log should be written also to the console.
     */
    public static final boolean           LOG_ALSO_TO_CONSOLE         = false;
    
    
    // GUI
    
    /** Icon: URL of the icon. */
    public static final URL               IMAGE_URL                   = ClassLoader.getSystemResource("ch/zh/transferclient/gui/icon.jpg");
    
    /** Icon: ImageIcon object. */
    public static final ImageIcon         IMAGE_ICON                  = new ImageIcon(IMAGE_URL);
    
    /** Icon: Image object. */
    public static final Image             IMAGE_FOR_ICON              = IMAGE_ICON.getImage();
    
    /** Logo: URL of the logo. */
    public static final URL               IMAGE_URL_LOGO              = ClassLoader.getSystemResource("ch/zh/transferclient/gui/logo.jpg");
    
    /** Logo: ImagIcon object. */
    public static final ImageIcon         IMAGE_ICON_LOGO             = new ImageIcon(IMAGE_URL_LOGO);
    
    /** GREEN-Color object. */
    public static final Color             GREEN                       = new Color(0, 153, 51);
    
    /** RED-Color object. */
    public static final Color             RED                         = Color.RED;
    
    /**
     * Indicator which indicates whether the process of deactivation should be signaled by a dialog (only under the
     * deprecated NIO_WATCHSERVICE approach).
     */
    public static final boolean           DEACTIVATION_WITH_DIALOG    = true;
    
    // Zeitauswahl bei Autoaktivierung
    /** Configuration parameter of the auto-activation menu: start hour. */
    private static final int              AUTOACTIVATION_HOUR_START   = 9;                                                                 // Default-Wert:
                                                                                                                                           // 9
    /** Configuration parameter of the auto-activation menu: end hour. */
    private static final int              AUTOACTIVATION_HOUR_END     = 13;                                                                // Default-Wert:
                                                                                                                                           // 13
    /** Configuration parameter of the auto-activation menu: start minute. */
    private static final int              AUTOACTIVATION_MINUTE_START = 0;                                                                 // Default-Wert:
                                                                                                                                           // 0
    /** Configuration parameter of the auto-activation menu: end minute. */
    private static final int              AUTOACTIVATION_MINUTE_END   = 55;                                                                // Default-Wert:
                                                                                                                                           // 55
    /**
     * Configuration parameter of the auto-activation menu: frequency in minutes.
     */
    private static final int              AUTOACTIVATION_MINUTE_FREQ  = 5;                                                                 // Default-Wert:
                                                                                                                                           // 5
    /** Configuration parameter of the auto-activation menu: points of time. */
    public static final ArrayList<String> AUTOACTIVATION_TIMES        = new ArrayList<String>();
    
    static
        {
        
        for (int i = AUTOACTIVATION_HOUR_START; i <= AUTOACTIVATION_HOUR_END; i++)
            {
            
            if (i == AUTOACTIVATION_HOUR_END)
                {
                AUTOACTIVATION_TIMES.add(i + ":00");
                }
            else
                {
                
                for (int j = AUTOACTIVATION_MINUTE_START; j <= AUTOACTIVATION_MINUTE_END; j = j
                        + AUTOACTIVATION_MINUTE_FREQ)
                    {
                    String hour   = "";
                    String minute = "";
                    if (i < 9)
                        {
                        hour = "0" + i;
                        }
                    else
                        {
                        hour = "" + i;
                        }
                    if (j < 9)
                        {
                        minute = "0" + j;
                        }
                    else
                        {
                        minute = "" + j;
                        }
                        
                    AUTOACTIVATION_TIMES.add(hour + ":" + minute);
                    }
                }
            }
            
        }
        
    }
