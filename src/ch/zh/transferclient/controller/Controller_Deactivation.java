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

import ch.zh.transferclient.main.*;

/**
 * This class is used to deactivate the transfer client.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Controller_Deactivation
    
    {
    
    /**
     * Deactivates the transfer client.
     * 
     * @param controller The controller to be used.
     */
    protected static void deactivation(Controller controller)
        {
        
        controller.active = false;
        
        controller.gui.get_button_deactivation().setEnabled(false);
        controller.gui.get_button_activation().setEnabled(true);
        controller.gui.get_button_autoactivation().setEnabled(true);
        controller.gui.get_button_folder().setEnabled(true);
        controller.gui.get_textfield_folder().setEnabled(true);
        controller.gui.get_combobox_autoactivation().setEnabled(true);
        
        controller.gui.get_label_status().setText(Labels.get("CONTROLLER_DEACTIVATION"));
        controller.gui.get_label_status().setFont(new Font("Sans Serif", Font.BOLD, 26));
        controller.gui.get_label_status().setForeground(Conf.RED);
        
        if (controller.executor_service != null)
            {
            try
                {
                controller.executor_service.shutdown();
                controller.executor_service_for_non_repeating_tasks.shutdown();
                
                
                /*
                controller.executor_service.shutdownNow();
                controller.executor_service_for_non_repeating_tasks.shutdownNow();
                
                controller.executor_service.awaitTermination(500,TimeUnit.MILLISECONDS);
                
                if (!controller.executor_service.isTerminated())
                    {
                    controller.gui.get_dialog_waitingfordeactivation().setVisible(true);
                    while (!controller.executor_service.isTerminated()) 
                        {
                        System.out.println("isTerminated = "+controller.executor_service.isTerminated());
                        };
                        
                        
                        
                    controller.gui.get_dialog_waitingfordeactivation().setVisible(false);
                    }
                */
                
                
                
                }
            catch (Exception e)
                {
                Logger.error(e);
                }
                
            Logger.info("DEACTIVATION: EXECUTOR SERVICE HAS BEEN SHUTDOWN");
            }
            
        }
        
    }
