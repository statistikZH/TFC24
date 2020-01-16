package ch.zh.transferclient.annotationservices;

import java.io.File;
import java.nio.file.*;
import java.util.Iterator;

/**
 * This class is used to write backups of the source files of the Transfer-Client.
 * 
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 *
 */
public class AnnotationsBackupService
    {
    
    /**
     * Source folder.
     */
    private static final String SOURCE_FOLDER = "src";
    
    /**
     * Backup folder.
     */
    private static final String TARGET_FOLDER = "src_old";
    
    /**
     * Writes backups of src to src_old.
     * 
     */
    protected static void backup_to_src_old()
        {
        
        try
            {
            Iterator<Path> it = Files.walk(Paths.get(SOURCE_FOLDER)).filter(p -> p.toString().endsWith(".java")).iterator();
            
            while (it.hasNext())
                {
                Path   source_path     = it.next();
                
                String target_filename = TARGET_FOLDER + "/" + AnnotationsTimeStamp.get_timestamp() + "_"
                        + source_path.toString().replaceAll("\\\\", "_");
                Path   target_path     = new File(target_filename).toPath();
                
                Files.copy(source_path, target_path, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        catch (Exception e)
            {
            e.printStackTrace();
            }
            
        }
        
    }
