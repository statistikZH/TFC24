/*
 * Copyright 2018-2019 Statistisches Amt des Kantons Zürich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.zh.transferclient.controller;

import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.concurrent.*;

import javax.swing.SwingUtilities;

import ch.zh.transferclient.main.*;
import ch.zh.transferclient.processing.*;
import ch.zh.transferclient.util.ThreadAnalyzer;

/**
 * This class is used to activate the transfer client.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Controller_Activation
    
    {
    
    /**
     * Activates the transfer client.
     *
     * @param controller The controller to be used.
     * @param autoActivation Flag which indicates whether auto-activation is requested.
     */
    protected static void activation(final Controller controller,final boolean autoActivation)
        
        {
        
        // This method is invoked by the Mouse Listener "controller".
        // It is running on the EDT thread, i.e. the GUI elements 
        // can be accessed without violating thread safety.
        ThreadAnalyzer.println(Thread.currentThread(), "EDT-Thread", "Aktivierung", "Aktivierung des Transfer-Clients 2.4");
        
        controller.active = true;
        
        // @formatter:on
        //The following elements are accessed only by the EDT thread
        controller.gui.get_button_folder().setEnabled(false);           // Deaktivierung der Folder-Auswahl.
        controller.gui.get_textfield_folder().setEnabled(false);        // Deaktivierung der Folder-Auswahl.
        controller.gui.get_combobox_autoactivation().setEnabled(false); // Deaktivierung der Autoaktivierungs-Combobox.
        controller.gui.get_button_activation().setEnabled(false);       // Deaktivierung des Aktivierungs-Buttons.
        controller.gui.get_button_autoactivation().setEnabled(false);   // Deaktivierung des AutoAktivierungs-Buttons.
        controller.gui.get_button_deactivation().setEnabled(true);      // Aktivierung des Deaktivierungs-Buttons.
        // @formatter:off
        
        
        long initialDelay = 0;
        
        //-------------------------//
        // FALL 1: AUTOAKTIVIERUNG //
        //-------------------------//
        if (autoActivation)
            {
            // --------------------------------//
            // Ziel-Zeitpunkt am heutigen Tag //
            // --------------------------------//
            
            //Extraktion von Stunde und Minute
            String zeit = controller.gui.get_combobox_autoactivation().getSelectedItem().toString();
            zeit = zeit.replace(Labels.get("CONTROLLER_AUTOACTIVATION_TIMEELEMENT_1") + " ", "").trim();
            zeit = zeit.replace(" " + Labels.get("CONTROLLER_AUTOACTIVATION_TIMEELEMENT_2"), "").trim();
            String[]      elemente     = zeit.split(":");
            String        targethour   = elemente[0];
            String        targetminute = elemente[1];
            
            //Extraktion von Jahr, Monat und Tag
            LocalDateTime ldt          = LocalDateTime.now();
            int           year         = ldt.getYear();
            int           month        = ldt.getMonthValue();
            int           day          = ldt.getDayOfMonth();
            
            DateFormat    dateFormat   = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String        format       = "yyyy/MM/dd HH:mm";
            String        target       = format.replace("yyyy", String.valueOf(year));
            target = target.replace("MM", String.valueOf(month));
            target = target.replace("dd", String.valueOf(day));
            target = target.replace("HH", String.valueOf(targethour));
            target = target.replace("mm", String.valueOf(targetminute));
            
            long targettime_in_miliseconds = 0;
            try
                {
                targettime_in_miliseconds = dateFormat.parse(target).getTime();
                }
            catch(Exception e)
                {
                Logger.error(e);
                }
            
            //Berechnung des Delays 
            //vgl. https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ScheduledExecutorService.html
            //Es wird angenommen, dass von der Berechnung des Delays bis zur Ausfuehrung der
            //Methode scheduleWithFixedDelay() (vgl. unten) sehr wenig Zeit vergeht.
            initialDelay = targettime_in_miliseconds-System.currentTimeMillis();
            
            //Der initialDelay darf nicht kleiner gleich 0 sein.
            if (initialDelay<=0)
                {
                initialDelay=0;
                }
                
            
            if (initialDelay>0)
                {
                final String textelement_1 = Labels.get("CONTROLLER_AUTOACTIVATION_TEXTELEMENT_1");
                final String timeelement_1 = Labels.get("CONTROLLER_AUTOACTIVATION_TIMEELEMENT_1");
                final String textelement_2 = Labels.get("CONTROLLER_AUTOACTIVATION_TEXTELEMENT_2");
                final String timeelement_2 = Labels.get("CONTROLLER_AUTOACTIVATION_TIMEELEMENT_2");
            
                controller.gui.get_label_status().setText(textelement_1 + " " + timeelement_1 + " " + targethour + ":"
                    + targetminute + " " + timeelement_2 + " " + textelement_2);
                controller.gui.get_label_status().setFont(new Font("Sans Serif", Font.BOLD, 26));
                controller.gui.get_label_status().setForeground(Conf.RED);
            
                Logger.info("AUTOACTIVATION: EXECUTOR SERVICE WILL BE STARTED AT " + targethour + ":" + targetminute);
                }
            }
        //---------------------//
        // FALL 2: AKTIVIERUNG //
        //---------------------//
        else
            {
            initialDelay=0;
            }
        
        
        // --------------------------------------------------------------------//
        // ExecutorService fuer sich wiederholgende Aufgaben wird eingerichtet //
        // --------------------------------------------------------------------//
        controller.executor_service = Executors.newSingleThreadScheduledExecutor();
        controller.executor_service.scheduleWithFixedDelay(new Runnable()
            {
            @Override
            public void run()
                {
                Processing.process(controller.properties, controller.gui);
                }
            }, initialDelay, controller.properties.get_delay(), TimeUnit.MILLISECONDS);
        
        // --------------------------------------------------------------------------//
        // ExecutorService fuer sich nicht wiederholgende Aufgaben wird eingerichtet //
        // --------------------------------------------------------------------------//
        controller.executor_service_for_non_repeating_tasks = Executors.newSingleThreadScheduledExecutor();
        controller.executor_service_for_non_repeating_tasks.schedule(new Runnable()
            {
            @Override
            public void run()
                {
                
                // STEP 1: INFORMING USER
                // In order to guarantee thread safety, SwingUtilities.invokeLater()
                // is used for accessing the GUI elements.
                SwingUtilities.invokeLater(new Runnable()
                    {
                    @Override
                    public void run()
                        {
                        controller.gui.get_label_status().setText(Labels.get("CONTROLLER_ACTIVATION"));
                        controller.gui.get_label_status().setFont(new Font("Sans Serif", Font.BOLD, 26));
                        controller.gui.get_label_status().setForeground(Conf.GREEN);
                        Logger.info("ACTIVATION: EXECUTOR SERVICE HAS BEEN STARTED");
                        }
                    });
                
                }
            }, initialDelay, TimeUnit.MILLISECONDS);
        
        
        }
        
    }
