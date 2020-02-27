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

import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ch.zh.transferclient.gui.*;
import ch.zh.transferclient.main.Logger;

/**
 * This class is used to extract parameter values from an envelope file. The envelope files are evaluated in order to
 * inform the end user about the transfer status of a transmission.
 * 
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class Processing_Receipts_ExtractElements_OLD
    
    {
    
    /**
     * Constructs a Processing_Receipts_ExtractElement object.
     */
    private Processing_Receipts_ExtractElements_OLD()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    
    /**
     * Extracts parameter values from an envelope file.
     *
     * @param  gui          The graphical user interface to be used.
     * @param  file_receipt The receipt file to be used.
     * @return              The value of the parameter.
     */
    protected synchronized static Processing_Receipts_Record extract(final Gui gui, final File file_receipt)
        {
        String                 messageId  = "Not available";
        String                 statusInfo = "Not available";
        
        DocumentBuilderFactory factory;
        DocumentBuilder        builder;
        Document               doc;
        
        try
            {
            
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc     = builder.parse(file_receipt);
            
            NodeList subnodes = doc.getChildNodes().item(0).getChildNodes();
            
            for (int i = 0; i < subnodes.getLength(); i++)
                {
                
                Node node = subnodes.item(i);
                
                // In der folgenden Zeile wurde "equals" durch "contains"
                // ersetzt, damit auch das alte Quittungsformat der
                // Kantone Nidwalden und Obwalden verarbeitet werden kann.
                if (node.getNodeName().contains("messageId"))
                    {                    
                    messageId   = node.getTextContent(); 
                    }
                if (node.getNodeName().contains("statusInfo"))
                    {                    
                    statusInfo  = node.getTextContent(); 
                    }
                }
                
            }
        catch (Exception e)
            {
            Logger.error(e);
            
            SwingUtilities.invokeLater(new Runnable()
                {
                @Override
                public void run()
                    {
                    gui.get_dialog_fileprocessingerror().setvisible(e);
                    //gui.get_dialog_receiptnotwellformed().setvisible();
                    }
                });
            }
            
        Processing_Receipts_Record record = new Processing_Receipts_Record(messageId,statusInfo); 
        
        return record;
        }
        
    }
