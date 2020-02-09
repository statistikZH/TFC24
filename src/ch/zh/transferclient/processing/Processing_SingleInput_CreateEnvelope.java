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

package ch.zh.transferclient.processing;

import java.io.*;

import ch.zh.transferclient.main.Logger;
import ch.zh.transferclient.util.TimeStamp;

/**
 * This class is used to create envelope files.
 *
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class Processing_SingleInput_CreateEnvelope
    
    {
    
    /**
     * Constructs a Processing_SingleInput_CreateEnvelope object.
     */
    private Processing_SingleInput_CreateEnvelope()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Creates an envelope file.
     * 
     * @param envelopefile       File to write.
     * @param sedex_message_id   Sedex Message ID to be used.
     * @param sedex_sender_id    Sedex Sender ID to be used.
     * @param sedex_recipient_id Sedex_Recipient ID to be used.
     */
    protected synchronized static void process(final File envelopefile, final String sedex_message_id, final String sedex_sender_id, final String sedex_recipient_id)
        {
        
        try
            {
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(envelopefile));
            
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.newLine();
            bw.write("<envelope xmlns=\"http://www.ech.ch/xmlns/eCH-0090/1\" ");
            bw.write("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
            bw.write("xsi:schemaLocation=\"http://www.ech.ch/xmlns/eCH-0090/1 ");
            bw.write("http://www.ech.ch/xmlns/eCH-0090/1/eCH-0090-1-0.xsd\" ");
            bw.write("version=\"1.0\"> ");
            bw.newLine();
            bw.write("<messageId>" + sedex_message_id + "</messageId> ");
            bw.newLine();
            bw.write("<messageType>1055</messageType> ");
            bw.newLine();
            bw.write("<messageClass>0</messageClass> ");
            bw.newLine();
            
            bw.write("<senderId>" + sedex_sender_id + "</senderId> ");
            bw.newLine();
            
            bw.write("<recipientId>" + sedex_recipient_id + "</recipientId> ");
            bw.newLine();
            
            String timestamp = TimeStamp.getstamp_for_sedex_envelope();
            bw.write("<eventDate>" + timestamp + "</eventDate> ");
            bw.newLine();
            bw.write("<messageDate>" + timestamp + "</messageDate> ");
            bw.newLine();
            bw.write("</envelope> ");
            bw.newLine();
            
            bw.close();
            
            }
            
        catch (Exception e)
            {
            Logger.error(e);
            }
            
        }
        
    }
