package ch.zh.transferclient.annotationservices;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * The class is used to update systematically the author and version of all source files.
 * 
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 *
 */
public class AnnotationsUpdate
    {
    
    /**
     * Updates the author and version of all source files.
     * 
     * @param rootfolder The root folder to be used.
     * 
     */
    protected static void update(String rootfolder)
        {
        
        try
            {
            Iterator<Path> it = Files.walk(Paths.get(rootfolder)).filter(p -> p.toString().endsWith(".java")).iterator();
            
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
                    if (line.contains("@author"))
                        {
                        line = line.substring(0, line.indexOf("@author ") + 9) + AnnotationsMain.AUTHOR;
                        }
                    if (line.contains("@version"))
                        {
                        line = line.substring(0, line.indexOf("@version ") + 9) + AnnotationsMain.VERSION;
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
