package ch.zh.transferclient.util;

public class ThreadAnalyzer
    
    {
    
    public static final boolean ACTIVE = false;
    
    public static void println(Thread thread, String thread_type, String task_type, String details)
        {
        
        if (ACTIVE)
            {
            System.out.printf("%-45s %-35s %-25s %s \n", thread, thread_type, task_type, details);
            }
        }
        
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
