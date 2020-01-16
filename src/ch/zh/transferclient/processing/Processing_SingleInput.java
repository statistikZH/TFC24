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

package ch.zh.transferclient.processing;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import ch.zh.transferclient.gui.*;
import ch.zh.transferclient.properties.*;

/**
 * This class is used to process a single input file.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class Processing_SingleInput
    
    {
    
    /**
     * Processes a single input file.
     * 
     * @param  properties            The reference to the properties object to be used.
     * @param  gui                   The reference to the GUI object to be used.
     * @param  file                  The reference to the File object to be processed.
     * @param  sedex_message_id      The reference to the sedex message id String object to be used.
     * @throws FileNotFoundException File cannot be found.
     * @throws IOException           IO operation fails.
     */
    protected synchronized static void process
    /* @formatter:off */
        (
        final Properties        properties,
        final Gui               gui,
        final File              file,
        final String            sedex_message_id
        ) throws Exception
        /* @formatter:on */
        {
        
        //-----------------//
        // Updaten des GUI //
        //-----------------//
        // Das GUI wird aktualisiert, um anzuzeigen, dass
        // das File detektiert worden ist und die
        // Verarbeitung eingeleitet worden ist.
        Processing_SingleInput_GUIUpdate.process(properties, gui, file, sedex_message_id);
        
        //------------------------//
        // Abfrage der Properties //
        //------------------------//
        final boolean           zip_compression     = properties.get_zip_compression();
        final String            sedex_sender_id     = properties.get_sedex_sender_id();
        final ArrayList<String> sedex_recipient_ids = properties.get_sedex_recipient_ids();
        final String            sedex_dir_outbox    = properties.get_sedex_dir_outbox();
        
        // ----------------//
        // ZIP-Generierung //
        // ----------------//        
        ZipEntry                ze                  = new ZipEntry(file.getName());
        FileOutputStream        fos                 = new FileOutputStream("stage/data_" + sedex_message_id + ".zip");
        ZipOutputStream         zipout              = new ZipOutputStream(new BufferedOutputStream(fos));
        
        if (!zip_compression)
            {
            // Vgl. Seite 183 von Elliotte Rusty Harold:
            // Java I/O: Tips and Techniques for Putting I/O to Work
            zipout.setMethod(ZipEntry.STORED);
            
            // Zur Vermeidung des Fehlers
            // java.util.zip.ZipException: STORED entry missing size, compressed size, or
            // crc-32
            // (vgl.
            // https://stackoverflow.com/questions/1206970/how-to-create-uncompressed-zip-archive-in-java)
            // müssen size, compressedsize und crc32 des ZipEntrys vorgängig gesetzt werden:
            // vgl. hierzu
            // http://jcsnippets.atspace.com/java/input-output/create-zip-file.html
            
            ze.setSize(file.length());
            ze.setCompressedSize(file.length());
            ze.setTime(file.lastModified());
            
            CRC32           crc32 = new CRC32();
            FileInputStream fis   = new FileInputStream(file);
            byte[]          data  = new byte[4096];
            int             len;
            while ((len = fis.read(data)) > -1)
                {
                crc32.update(data, 0, len);
                }
            fis.close();
            ze.setCrc(crc32.getValue());
            
            // System.out.println("size="+file.length());
            // System.out.println("crc32="+crc32.getValue());
            }
            
        zipout.putNextEntry(ze);
        
        // Schreiben des Files
        FileInputStream fis  = new FileInputStream(file);
        byte[]          tmp  = new byte[4 * 1024];
        int             size = 0;
        while ((size = fis.read(tmp)) != -1)
            {
            zipout.write(tmp, 0, size);
            }
            
        zipout.closeEntry();
        
        zipout.flush();
        zipout.close();
        fos.flush();
        fos.close();
        
        fis.close();
        
        // Eine Kopie des ZIP-Files
        Path origin = Paths.get("stage/data_" + sedex_message_id + ".zip");
        
        // wird fuer jeden Empfaenger erstellt:
        for (int j = sedex_recipient_ids.size() - 1; j >= 0; j--)
            {
            final String fileDATA     = "data_" + sedex_message_id + "-E" + j + ".zip";
            final String fileENVELOPE = "envl_" + sedex_message_id + "-E" + j + ".xml";
            
            Files.copy(origin, Paths.get("stage/" + fileDATA), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            
            // Umschlaege hinzufuegen
            File   envelopefile               = new File("stage/" + fileENVELOPE);
            String sedex_message_id_completed = sedex_message_id + "-E" + j;
            String sedex_recipient_id         = sedex_recipient_ids.get(j);
            Processing_SingleInput_CreateEnvelope.process(envelopefile, sedex_message_id_completed, sedex_sender_id, sedex_recipient_id);
            
            // In Outbox kopieren
            
            // Die Reihenfolge des Verschiebens ist wichtig: es wird
            // zuerst das Datenfile verschoben und dann erst das Envelopefile.
            // Warum? Weil es zur Meldung "No payload found" kommt, wenn der
            // Sedex-Client nur ein Envelopefile ohne Datenfile antrifft. Denn
            // in Sedex ist es erlaubt, nur einen Umschlag ohne Datenfile zu
            // versenden. Natuerlich wuerde der "No payload found"-Fall nur
            // dann eintreten, wenn der Sedex-Client genau waehrend des
            // vorliegenden Schreibprozesses pollt.
            
            Files.move(Paths.get("stage/" + fileDATA), Paths.get(sedex_dir_outbox + "/"
                    + fileDATA), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            Files.move(Paths.get("stage/" + fileENVELOPE), Paths.get(sedex_dir_outbox + "/"
                    + fileENVELOPE), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            
            }
        
        // Loeschung des Vorlage ZIPs
        Files.delete(origin);
        
        // Loeschung des Original-Datenfiles
        file.delete();
        
        }
        
    }
