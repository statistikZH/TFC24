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

package ch.zh.transferclient.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.html.*;

import ch.zh.transferclient.main.*;

/**
 * This class is used to show a dialog which informs the user about the contact details for support.
 *
 * @author  Daniel Bierer (Statistical Office of the Canton of Zurich)
 * @version 2.4
 */
public class DialogSupport extends JDialog implements MouseListener
    
    {
    
    /** A unique serial version identifier. */
    private static final long  serialVersionUID = 1L;
    
    /** The width of the dialog. */
    protected static final int WIDTH            = 480;
    
    /** The height of the dialog. */
    protected static final int HEIGHT           = 390;
    
    /** The OK button. */
    private final JButton      okbutton         = new JButton("OK");
    
    @Override
    public void mouseClicked(MouseEvent e)
        {
        
        if (e.getSource() == this.okbutton)
            {
            this.setVisible(false);
            }
        }
        
    @Override
    public void mouseReleased(MouseEvent e)
        {
        }
        
    @Override
    public void mousePressed(MouseEvent e)
        {
        }
        
    @Override
    public void mouseExited(MouseEvent e)
        {
        }
        
    @Override
    public void mouseEntered(MouseEvent e)
        {
        }
        
    /**
     * Constructs a DialogSupport object.
     * 
     * @param parent  The parent frame to be used.
     * @param english Indicates whether the dialog should be in English or not.
     */
    public DialogSupport(final JFrame parent, final boolean english)
        
        {
        
        // Grundparameter
        super(parent, Labels.get("DIALOG_SUPPORT_TITLE"));
        this.setSize(WIDTH, HEIGHT);
        this.setIconImage(Conf.IMAGE_FOR_ICON);
        this.setResizable(false);
        
        // EventHandler
        this.okbutton.addMouseListener(this);
        
        // Border
        EmptyBorder emptyborder = new EmptyBorder(20, 10, 20, 20);
        
        // JPanel/Layout
        JPanel      panel       = new JPanel();
        panel.setBorder(emptyborder);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Hinweis-Texte
        String label_text = "";
        if (english)
            {
            label_text = "Contact information below can be copied by \"Selecting/Ctrl-C\".";
            }
        else
            {
            label_text = Labels.get("DIALOG_SUPPORT_TEXT");
            }
            
        JLabel label_hinweis = new JLabel(label_text); // aufgetreten (der Fehler kann markiert und mit Ctrl-C kopiert
                                                       // werden):");
        label_hinweis.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label_hinweis);
        
        // Abstand
        panel.add(new JPanel());
        
        // Fehlermeldung
        
        JTextPane textpane = new JTextPane();
        
        textpane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        textpane.setEditable(false);
        textpane.setEnabled(true);
        textpane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // HTML Editor
        HTMLEditorKit htmleditorkit = new HTMLEditorKit();
        textpane.setEditorKit(htmleditorkit);
        
        // HTML Dokument
        HTMLDocument htmldoc = new HTMLDocument();
        textpane.setDocument(htmldoc);
        
        JScrollPane sp = new JScrollPane(textpane);
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);
        ;
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        jp.add(sp);
        jp.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        StringBuffer contact = new StringBuffer();
        
        contact.append("<table>\n");
        
        // ---------------//
        // SEDEX-SUPPORT //
        // ---------------//
        
        String DIALOG_SUPPORT_SEDEX_CONTACT_INFO_1 = Labels.get("DIALOG_SUPPORT_SEDEX_CONTACT_INFO_1");
        String DIALOG_SUPPORT_SEDEX_CONTACT_INFO_2 = Labels.get("DIALOG_SUPPORT_SEDEX_CONTACT_INFO_2");
        String DIALOG_SUPPORT_SEDEX_CONTACT_INFO_3 = Labels.get("DIALOG_SUPPORT_SEDEX_CONTACT_INFO_3");
        String DIALOG_SUPPORT_SEDEX_CONTACT_INFO_4 = Labels.get("DIALOG_SUPPORT_SEDEX_CONTACT_INFO_4");
        if (english)
            {
            DIALOG_SUPPORT_SEDEX_CONTACT_INFO_1 = "Technical Support SEDEX-Adapter";
            DIALOG_SUPPORT_SEDEX_CONTACT_INFO_2 = "Federal Statistical Office";
            DIALOG_SUPPORT_SEDEX_CONTACT_INFO_3 = "Phone";
            DIALOG_SUPPORT_SEDEX_CONTACT_INFO_4 = "Email";
            }
            
        contact.append("<tr><td colspan=2><b>" + DIALOG_SUPPORT_SEDEX_CONTACT_INFO_1 + ":</b></td></tr>\n");
        contact.append("<tr><td colspan=2>" + DIALOG_SUPPORT_SEDEX_CONTACT_INFO_2 + "</td></tr>\n");
        contact.append("<tr><td>" + DIALOG_SUPPORT_SEDEX_CONTACT_INFO_3 + ":</td><td>0800 866 700</td></tr>\n");
        contact.append("<tr><td>" + DIALOG_SUPPORT_SEDEX_CONTACT_INFO_4
                + ":</td><td>sedexsupport@bfs.admin.ch</td></tr>\n");
        contact.append("<tr><td colspan=2>&nbsp;</td></tr>\n");
        
        // ------------------------//
        // TRANSFERCLIENT-SUPPORT //
        // ------------------------//
        String DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_1 = Labels.get("DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_1");
        String DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_2 = Labels.get("DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_2");
        String DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_3 = Labels.get("DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_3");
        String DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_4 = Labels.get("DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_4");
        if (english)
            {
            DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_1 = "Technical Support Transfer-Client";
            DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_2 = "Statistical Office Cantonof Zurich";
            DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_3 = "Phone";
            DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_4 = "Email";
            }
            
        contact.append("<tr><td colspan=2><b>" + DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_1 + ":</b></td></tr>\n");
        contact.append("<tr><td colspan=2>" + DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_2 + "</td></tr>\n");
        contact.append("<tr><td>" + DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_3
                + ":</td><td>043 259 75 72</td></tr>\n");
        contact.append("<tr><td>&nbsp;</td><td>043 259 75 23</td></tr>\n");
        contact.append("<tr><td>" + DIALOG_SUPPORT_TRANSFERCLIENT_CONTACT_INFO_4
                + ":</td><td>bruno.ledergerber@statistik.ji.zh.ch<td></tr>\n");
        contact.append("<tr><td>&nbsp;</td><td>daniel.bierer@statistik.ji.zh.ch<td></tr>\n");
        contact.append("</table>\n");
        contact.append("<br><br>\n");
        
        textpane.setText(contact.toString());
        
        panel.add(sp);
        
        // Abstand
        panel.add(new JPanel());
        
        // Knopf
        this.okbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel jp_button = new JPanel();
        jp_button.setAlignmentX(Component.LEFT_ALIGNMENT);
        jp_button.add(this.okbutton);
        panel.add(jp_button);
        
        setContentPane(panel);
        
        }
        
    }
