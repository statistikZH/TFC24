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

package ch.zh.transferclient.gui;

import java.awt.event.*;

/**
 * This class is used to guarantee that components (windows) remain well arranged when components (windows) are moved.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class EventHandlerComponentListener implements ComponentListener
    
    {
    
    /** Translation of the DialogDir-dialog in x-direction. */
    private static final int TRANSLATION_X_DIALOG_DIR;
    /** Translation of the DialogDir-dialog in y-direction. */
    private static final int TRANSLATION_Y_DIALOG_DIR;
    /** Translation of the FileMoveError-dialog in x-direction. */
    private static final int TRANSLATION_X_DIALOG_FILEMOVEERROR;
    /** Translation of the FileMoveError-dialog in y-direction. */
    private static final int TRANSLATION_Y_DIALOG_FILEMOVEERROR;
    /** Translation of the ReceiptNotWellFormed-dialog in x-direction. */
    private static final int TRANSLATION_X_DIALOG_RECEIPTNOTWELLFORMED;
    /** Translation of the ReceiptNotWellFormed-dialog in y-direction. */
    private static final int TRANSLATION_Y_DIALOG_RECEIPTNOTWELLFORMED;
    /** Translation of the DialogSupport-dialog in x-direction. */
    private static final int TRANSLATION_X_DIALOG_SUPPORT;
    /** Translation of the DialogSupport-dialog in y-direction. */
    private static final int TRANSLATION_Y_DIALOG_SUPPORT;
    /** Translation of the DialogWaitingForDeactivation-dialog in x-direction. */
    private static final int TRANSLATION_X_DIALOG_WAITINGFORDEACTIVATION;
    /** Translation of the DialogWaitingForDeactivation-dialog in y-direction. */
    private static final int TRANSLATION_Y_DIALOG_WAITINGFORDEACTIVATION;
    
    static
        {
        TRANSLATION_X_DIALOG_DIR                    = (Gui.WIDTH - DialogDir.WIDTH) / 2;
        TRANSLATION_Y_DIALOG_DIR                    = (Gui.HEIGHT - DialogDir.HEIGHT) / 2;
        TRANSLATION_X_DIALOG_FILEMOVEERROR          = (Gui.WIDTH - DialogFileProcessingError.WIDTH) / 2;
        TRANSLATION_Y_DIALOG_FILEMOVEERROR          = (Gui.HEIGHT - DialogFileProcessingError.HEIGHT) / 2;
        TRANSLATION_X_DIALOG_RECEIPTNOTWELLFORMED   = (Gui.WIDTH - DialogReceiptNotWellFormed.WIDTH) / 2;
        TRANSLATION_Y_DIALOG_RECEIPTNOTWELLFORMED   = (Gui.HEIGHT - DialogReceiptNotWellFormed.HEIGHT) / 2;
        TRANSLATION_X_DIALOG_SUPPORT                = (Gui.WIDTH - DialogSupport.WIDTH) / 2;
        TRANSLATION_Y_DIALOG_SUPPORT                = (Gui.HEIGHT - DialogSupport.HEIGHT) / 2;
        TRANSLATION_X_DIALOG_WAITINGFORDEACTIVATION = (Gui.WIDTH - DialogWaitingForDeactivation.WIDTH) / 2;
        TRANSLATION_Y_DIALOG_WAITINGFORDEACTIVATION = (Gui.HEIGHT - DialogWaitingForDeactivation.HEIGHT) / 2;
        
        }
        
    /** The graphical user interface whose events should be listened to. */
    private final Gui gui;
    
    /**
     * Constructs an EventHandlerComponentListener object.
     * 
     * @param gui The graphical user interface whose events should be listened to.
     */
    protected EventHandlerComponentListener(Gui gui)
        {
        this.gui = gui;
        }
        
    // Mitteilungsfenster sind immer am gleichen Ort fixiert (relativ zum
    // Hauptfenster)
    @Override
    public void componentMoved(ComponentEvent e)
        {
        
        if (e.getSource() == this.gui)
            {
            
            // Festlegung der neuen Lokation des Dir-Dialogs
            this.gui.get_dialog_dir().setLocation(this.gui.getLocation().x
                    + TRANSLATION_X_DIALOG_DIR, this.gui.getLocation().y + TRANSLATION_Y_DIALOG_DIR);
            
            // Festlegung der neuen Lokation des FileMoveError-Dialogs
            this.gui.get_dialog_fileprocessingerror().setLocation(this.gui.getLocation().x
                    + TRANSLATION_X_DIALOG_FILEMOVEERROR, this.gui.getLocation().y
                            + TRANSLATION_Y_DIALOG_FILEMOVEERROR);
            
            // Festlegung der neuen Lokation des ReceiptNotWllFormed-Dialogs
            this.gui.get_dialog_receiptnotwellformed().setLocation(this.gui.getLocation().x
                    + TRANSLATION_X_DIALOG_RECEIPTNOTWELLFORMED, this.gui.getLocation().y
                            + TRANSLATION_Y_DIALOG_RECEIPTNOTWELLFORMED);
            
            // Festlegung der neuen Lokation des Support-Dialogs
            this.gui.get_dialog_support().setLocation(this.gui.getLocation().x
                    + TRANSLATION_X_DIALOG_SUPPORT, this.gui.getLocation().y + TRANSLATION_Y_DIALOG_SUPPORT);
            
            // Festlegung der neuen Lokation des WaitingForDeactivation-Dialogs
            this.gui.get_dialog_waitingfordeactivation().setLocation(this.gui.getLocation().x
                    + TRANSLATION_X_DIALOG_WAITINGFORDEACTIVATION, this.gui.getLocation().y
                            + TRANSLATION_Y_DIALOG_WAITINGFORDEACTIVATION);
            
            }
        else if (e.getSource() == this.gui.get_dialog_dir())
            {
            // Festlegung der neuen Lokation des GUIs
            this.gui.setLocation(this.gui.get_dialog_dir().getLocation().x
                    - TRANSLATION_X_DIALOG_DIR, this.gui.get_dialog_dir().getLocation().y - TRANSLATION_Y_DIALOG_DIR);
            }
        else if (e.getSource() == this.gui.get_dialog_fileprocessingerror())
            {
            // Festlegung der neuen Lokation des GUIs
            this.gui.setLocation(this.gui.get_dialog_fileprocessingerror().getLocation().x
                    - TRANSLATION_X_DIALOG_FILEMOVEERROR, this.gui.get_dialog_fileprocessingerror().getLocation().y
                            - TRANSLATION_Y_DIALOG_FILEMOVEERROR);
            }
            
        else if (e.getSource() == this.gui.get_dialog_support())
            {
            // Festlegung der neuen Lokation des GUIs
            this.gui.setLocation(this.gui.get_dialog_support().getLocation().x
                    - TRANSLATION_X_DIALOG_SUPPORT, this.gui.get_dialog_support().getLocation().y
                            - TRANSLATION_Y_DIALOG_SUPPORT);
            }
        else if (e.getSource() == this.gui.get_dialog_receiptnotwellformed())
            {
            // Festlegung der neuen Lokation des GUIs
            this.gui.setLocation(this.gui.get_dialog_receiptnotwellformed().getLocation().x
                    - TRANSLATION_X_DIALOG_RECEIPTNOTWELLFORMED, this.gui.get_dialog_receiptnotwellformed().getLocation().y
                            - TRANSLATION_Y_DIALOG_RECEIPTNOTWELLFORMED);
            }
        else if (e.getSource() == this.gui.get_dialog_waitingfordeactivation())
            {
            // Festlegung der neuen Lokation des GUIs
            this.gui.setLocation(this.gui.get_dialog_waitingfordeactivation().getLocation().x
                    - TRANSLATION_X_DIALOG_WAITINGFORDEACTIVATION, this.gui.get_dialog_waitingfordeactivation().getLocation().y
                            - TRANSLATION_Y_DIALOG_WAITINGFORDEACTIVATION);
            }
            
        }
        
    @Override
    public void componentHidden(ComponentEvent e)
        {
        };
        
    @Override
    public void componentResized(ComponentEvent e)
        {
        };
        
    @Override
    public void componentShown(ComponentEvent e)
        {
        };
        
    }
