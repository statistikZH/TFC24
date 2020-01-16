package ch.zh.transferclient.annotationservices;

/**
 * 
 * This class is used to modify the javadoc-author and javadoc-version of all source files of the Transfer-Client.
 * 
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 *
 */
public class AnnotationsMain
    {
    
    /**
     * Author to be used.
     */
    protected static final String AUTHOR  = "Daniel Bierer (Statistical Office of the Canton of Zurich)";
    
    /**
     * Version to be used.
     */
    protected static final String VERSION = "2.4";
    
    /**
     * Starts the application.
     * 
     * 
     * @param  args      Command-line arguments.
     * @throws Exception An exception can be thrown.
     */
    public static void main(String[] args) throws Exception
        {
        
        // Writing Backups
        AnnotationsBackupService.backup_to_src_old();
        
        AnnotationsUpdate.update("src/ch/zh/transferclient/controller");
        AnnotationsUpdate.update("src/ch/zh/transferclient/gui");
        AnnotationsUpdate.update("src/ch/zh/transferclient/main");
        AnnotationsUpdate.update("src/ch/zh/transferclient/processing");
        AnnotationsUpdate.update("src/ch/zh/transferclient/properties");
        AnnotationsUpdate.update("src/ch/zh/transferclient/test");
        AnnotationsUpdate.update("src/ch/zh/transferclient/trash");
        AnnotationsUpdate.update("src/ch/zh/transferclient/util");
        
        }
        
    }
