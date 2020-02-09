package ch.zh.transferclient.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import ch.zh.transferclient.main.Logger;

/**
 * The class is used for simulating receipts.
 * 
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 *
 */
public class FillupReceiptsFolder
    
    {
    
    /**
     * Constructs a FillupReceiptsFolder object. 
     */
    private FillupReceiptsFolder()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    /**
     * Writes 1000 receipt files to the receipts folder.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args)
        
        {
        
        for (int i = 0; i < 1000; i++)
            {
            write_file("4-143849-0-20191112-1334-51-935", "E00");
            }
            
        }
        
    /**
     * Writes a receipt file.
     * 
     * @param sedex_message_id  Sedex Message ID to be used.
     * @param empfaenger        Receiver to be used.
     */
    private static void write_file(String sedex_message_id, String empfaenger)
        {
        
        /*----------------------------------------------*/
        /* Schreiben des Receipts (fuer Empfaenger E00) */
        /*----------------------------------------------*/
        
        String ts                 = ch.zh.transferclient.util.TimeStamp.getstamp_for_logfile();
        // String simulation_receipt =
        // "c://0_tf_reliability//sedex//receipts/simulation_receipt_"+ts+".xml";
        String simulation_receipt = "z://receipts/simulation_receipt_" + ts + ".xml";
        
        try
            {
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(simulation_receipt)));
            
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            bw.newLine();
            bw.write("<receipt xmlns=\"http://www.ech.ch/xmlns/eCH-0090/2\" version=\"2.0\">");
            bw.newLine();
            bw.write("<eventDate>2018-04-25T15:54:50.892+02:00</eventDate>");
            bw.newLine();
            bw.write("<statusCode>313</statusCode>");
            bw.newLine();
            bw.write("<statusInfo>Message successfully transmitted</statusInfo>");
            bw.newLine();
            bw.write("<messageId>" + sedex_message_id + "-" + empfaenger + "</messageId>");
            bw.newLine();
            bw.write("<messageType>1055</messageType>");
            bw.newLine();
            bw.write("<messageClass>0</messageClass>");
            bw.newLine();
            bw.write("<senderId>4-143849-0</senderId>");
            bw.newLine();
            bw.write("<recipientId>4-143849-0</recipientId>");
            bw.newLine();
            bw.write("</receipt>");
            bw.newLine();
            
            bw.flush();
            bw.close();
            
            }
        catch (Exception e)
            {
            Logger.error(e);
            }
            
        /*--------------------------------------------------------------*/
        /* Einlegen einer kleinen Plause, damit der gleiche Zeitstempel */
        /* (Dateiname) nicht zweimal vergeben wird. */
        /*--------------------------------------------------------------*/
        try
            {
            Thread.sleep(100);
            }
        catch (Exception e)
            {
            e.printStackTrace();
            }
            
        }
        
    }
