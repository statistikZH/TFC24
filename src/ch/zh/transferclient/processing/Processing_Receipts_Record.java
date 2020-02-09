package ch.zh.transferclient.processing;

/**
 * This class is used to construct receipts.
 * 
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 */
public class Processing_Receipts_Record
    {
    
    /**
     * Sedex Message ID.
     */
    private final String sedex_message_id;
    
    /**
     * Trimmed Sedex Message ID, i.e. the Sedex Message ID without the recipient information (for example without
     * "-E0").
     */
    private final String sedex_message_id_trimmed;
    
    /**
     * Status Info of the transmission.
     */
    private final String status_info;
    
    /**
     * Constructs a receipt.
     * @param sedex_message_id Sedex Message ID.
     * @param status_info      Status Info of the transmission
     */
    protected Processing_Receipts_Record(String sedex_message_id, String status_info)
        {
        this.sedex_message_id         = sedex_message_id;
        this.sedex_message_id_trimmed = sedex_message_id.substring(0, sedex_message_id.indexOf("-E"));
        this.status_info              = status_info;
        
        }
        
    /**
     * @return The Sedex Message ID.
     */
    protected String get_sedex_message_id()
        {
        return this.sedex_message_id;
        }
        
    /**
     * @return The trimmed Sedex Message ID, i.e. the Sedex Message ID without the recipient information (for example
     *         without "-E0").
     */
    protected String get_sedex_message_id_trimmed()
        {
        return this.sedex_message_id_trimmed;
        }
        
    /**
     * @return The status info of the transmission.
     */
    protected String get_status_info()
        {
        return this.status_info;
        }
        
    }
