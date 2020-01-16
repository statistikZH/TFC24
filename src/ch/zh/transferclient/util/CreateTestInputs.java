package ch.zh.transferclient.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import ch.zh.transferclient.main.Logger;

public class CreateTestInputs
    
    {
    
    public static void main(String[] args)
        
        {
        
        for (int i = 0; i < 1000; i++)
            {
            String id;
            if (i < 10)
                {
                id = "000" + i;
                }
            else if (i < 100)
                {
                id = "00" + i;
                }
            else if (i < 1000)
                {
                id = "0" + i;
                }
            else
                {
                id = "" + i;
                }
                
            write_file("TEST_TRANSFER_CLIENT_VERSION_2_4_FILE_" + id + ".txt");
            }
            
        }
        
    private static void write_file(String filename)
        {
        
        String dir = "c://0_tf_reliability//files_fuer_stresstest_5//";
        
        try
            {
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir + filename)));
            
            bw.write("---------------------------------------------------------");
            bw.newLine();
            bw.write(" BEIM VORLIEGENDEN FILE HANDELT ES SICH UM EIN TESTFILE, ");
            bw.newLine();
            bw.write(" DAS VERSENDET WORDEN IST, UM DIE NEUE VERSION 2.4       ");
            bw.newLine();
            bw.write(" DES TRANSFER-CLIENTS ZU TESTEN.                         ");
            bw.newLine();
            bw.write("---------------------------------------------------------");
            bw.newLine();
            bw.write(" Absender:                                               ");
            bw.newLine();
            bw.write(" Statistisches Amt des Kantons Zürich                    ");
            bw.newLine();
            bw.write(" Supportdienst Transfer-Client                           ");
            bw.newLine();
            bw.write(" Tel.: 043 259 75 23                                     ");
            bw.newLine();
            bw.write("---------------------------------------------------------");
            bw.newLine();
            
            bw.flush();
            bw.close();
            
            }
        catch (Exception e)
            {
            Logger.error(e);
            }
            
        }
        
    }
