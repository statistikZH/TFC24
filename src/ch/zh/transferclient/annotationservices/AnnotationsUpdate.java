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

package ch.zh.transferclient.annotationservices;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * The class is used to update systematically the author and version of all source files.
 * 
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 *
 */
public class AnnotationsUpdate
    {
    
    /**
     * Constructs an AnnotationsUpdate object.
     */
    private AnnotationsUpdate()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    
    /**
     * Updates the author and version of all source code files.
     * 
     * @param rootfolder The root folder to be used.
     * 
     */
    protected static void update(String rootfolder)
        {
        
        try
            {
            Iterator<Path> it = Files.walk(Paths.get(rootfolder)).filter(p -> (p.toString().endsWith(".java") || p.toString().endsWith("package.html") )).iterator();
            
            while (it.hasNext())
                {
                Path              path  = it.next();
                File              file  = path.toFile();
                
                // ------------------------------//
                // Einlesen des bisherigen Files //
                // ------------------------------//
                BufferedReader    br    = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                ArrayList<String> lines = new ArrayList<String>();
                
                String            line  = br.readLine();
                while ((line != null))
                    {
                    if (file.getName().endsWith(".java"))
                        {
                        if ((line.contains("* Copyright ")) && ((line.contains("Statistisches Amt des Kantons Zürich"))))
                            {
                            line = AnnotationsMain.LICENCEINFO_1FSTLINE;
                            }
                        if (line.contains("@author"))
                            {
                            line = AnnotationsMain.AUTHOR;
                            }
                        if (line.contains("@version"))
                            {
                            line = AnnotationsMain.VERSION;
                            }
                        }
                    else if (file.getName().equals("package.html"))
                        {
                        if (line.contains("@author"))
                            {
                            line = AnnotationsMain.PACKAGES_HTML_AUTHOR;
                            }
                        if (line.contains("@version"))
                            {
                            line = AnnotationsMain.PACKAGES_HTML_VERSION;
                            }
                        }
                        
                    
                    lines.add(line);
                    
                    line = br.readLine();
                    }
                    
                br.close();
                
                // ------------------------------------//
                // Ueberschreiben des bisherigen Files //
                // ------------------------------------//
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8));
                
                for (int i = 0; i < lines.size(); i++)
                    {
                    bw.write(lines.get(i));
                    bw.newLine();
                    }
                    
                bw.flush();
                bw.close();
                
                System.out.println("FILE UPDATED: " + file.getAbsolutePath());
                
                }
                
            }
        catch (Exception e)
            {
            e.printStackTrace();
            }
            
        }
    
    }
