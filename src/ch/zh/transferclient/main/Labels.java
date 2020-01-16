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

import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//The following packages reside in the classes.jar file of the JDK.
//https://stackoverflow.com/questions/18173731/what-is-the-jar-file-i-should-download-and-from-where
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ch.zh.transferclient.properties.Properties.Language;

/**
 * This class provides the labels depending on the selected language.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Labels
    
    {
    
    /** Map with the labels. */
    private static final HashMap<String, String> LABELS = new HashMap<String, String>();
    
    /**
     * Returns a specific label.
     *
     * @param  identifier Identifier of the label.
     * @return            Label which corresponds to the identifier.
     */
    public static String get(String identifier)
        {
        return LABELS.get(identifier);
        }
        
    /**
     * Fills up the labels.
     *
     * @param  lang      Language to be used.
     * @throws Exception If the labels cannot be filled up.
     */
    protected static void fillup(final Language lang) throws Exception
        {
        
        String       dir_root        = System.getProperty("user.dir");
        String       file_xml_labels = dir_root + "/labels/labels.xml";
        
        final String language        = lang.toString().toLowerCase();
        
        InputStream  inputStream     = new FileInputStream(file_xml_labels);
        Reader       reader          = new InputStreamReader(inputStream, "UTF-8");
        InputSource  is              = new InputSource(reader);
        is.setEncoding("UTF-8");
        
        DocumentBuilderFactory factory;
        DocumentBuilder        builder;
        Document               doc;
        
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        doc     = builder.parse(is);
        
        NodeList subnodes = doc.getChildNodes().item(0).getChildNodes();
        
        for (int i = 0; i < subnodes.getLength(); i++)
            {
            
            Node     node        = subnodes.item(i);
            
            NodeList subsubnodes = node.getChildNodes();
            
            String   identifier  = "";
            String   label       = "";
            
            for (int j = 0; j < subsubnodes.getLength(); j++)
                {
                Node node_target = subsubnodes.item(j);
                
                if (node_target.getNodeName().equals("identifier"))
                    {
                    identifier = node_target.getTextContent();
                    }
                    
                if (node_target.getNodeName().equals(language))
                    {
                    
                    label = node_target.getTextContent();
                    
                    }
                }
                
            LABELS.put(identifier, label);
            }
        }
    }
