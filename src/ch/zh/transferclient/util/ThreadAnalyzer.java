package ch.zh.transferclient.util;

/**
 * The class is used for thread analyzing purposes.
 * 
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 *
 */
public class ThreadAnalyzer
    
    {
    
    /**
     * Constructs a ThreadAnalyzer object.
     */
    private ThreadAnalyzer()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Flag which indicates whether the thread analyzer is active or not.
     */
    private static final boolean ACTIVE = false;
    
    /**
     * Prints out information about a thread and the corresponding task on which the thread is working.
     * 
     * @param thread        Thread which is under observation.
     * @param thread_type   Type of the thread.
     * @param task_type     Type of the task.
     * @param details       Additional information.
     */
    public static void println(Thread thread, String thread_type, String task_type, String details)
        {
        
        if (ACTIVE)
            {
            System.out.printf("%-45s %-35s %-25s %s \n", thread, thread_type, task_type, details);
            }
        }
        
    /**
     *  Prints out the header of the protocol.
     */
    public static void println_head()
        {
        if (ACTIVE)
            {
            System.out.printf("-----------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("%-45s %-35s %-25s %s \n", "Thread", "Thread Type", "Task Type", "Details");
            System.out.printf("-----------------------------------------------------------------------------------------------------------------------------------------\n");
            }
        }
        
    }
