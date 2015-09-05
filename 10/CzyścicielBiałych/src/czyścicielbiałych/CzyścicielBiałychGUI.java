/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package czyścicielbiałych;

import czyścicielbiałych.narzędzia.CzyszczącyReader;
import czyścicielbiałych.narzędzia.CzyszczącyWriter;
import czyścicielbiałych.narzędzia.Kodowania;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import static java.lang.Math.max;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

/**
 *
 * @author ja
 */
public class CzyścicielBiałychGUI extends JFrame {

    private void otwórzPlik() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        
        int returnVal = chooser.showOpenDialog(CzyścicielBiałychGUI.this);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            
           aktualnyPlik = chooser.getSelectedFile();
           
           StringBuilder opliku = new StringBuilder();
           String path = aktualnyPlik.getPath();
           path = path.substring(max(0, path.length() - 40));
           opliku.append("..." + path);
           Date d = new Date(aktualnyPlik.lastModified());
           String sformatowana = 
                   new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d);
           opliku.append(" | Długość: " + aktualnyPlik.length() 
                   + " | Zmodyf.: " + sformatowana);
           infoOPliku.setText(opliku.toString());
           
            try {
                BufferedReader cr = Files.newBufferedReader(
                        aktualnyPlik.toPath(), kodowanieIn);
                
                // może i brzydkie, ale nie umiałem inaczej...
                char[] buffer = new char[8192]; // 2^13
                StringBuilder builder = new StringBuilder();
                int read;
                
                while ((read = cr.read(buffer, 0, buffer.length)) > 0) {
                    builder.append(buffer, 0, read);
                }
                
                dokument.setText(builder.toString());
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CzyścicielBiałychGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }

    private void czyśćPlik() throws IOException {
        CzyszczącyReader cr = 
                new CzyszczącyReader(Files.newBufferedReader(
                        aktualnyPlik.toPath(), kodowanieOut));
        char[] buffer = new char[8192];
        StringBuilder builder = new StringBuilder();
        int read;

        while ((read = cr.read(buffer, 0, buffer.length)) > 0) {
            builder.append(buffer, 0, read);
        }
        
        dokument.setText(builder.toString());
    }

    private void ustawKodowanieIn(String kod) {
        kodowanieIn = Kodowania.str2charset(kod);
    }

    private void ustawKodowanieOut(String kod) {
        kodowanieOut = Kodowania.str2charset(kod);
    }
    
    private void zapiszPlik() throws IOException {
        String nazwa = aktualnyPlik.getName() + "_czysty";
        Writer przekodowany = new BufferedWriter(
            new OutputStreamWriter(
                    new FileOutputStream(nazwa),
                    kodowanieOut)
        );
        CzyszczącyWriter cw = new CzyszczącyWriter(przekodowany);
        
        cw.write(dokument.getText());
        cw.close();
    }
    
    private class PasekNarzędziowy extends JToolBar {
    
        public PasekNarzędziowy() {
            initComponents();
        }

        private void initComponents() {
            JButton button = null;
            button = new JButton("Otwórz plik");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        CzyścicielBiałychGUI.this.otwórzPlik();
                    } catch (IOException ex) {
                        Logger.getLogger(CzyścicielBiałychGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            add(button);

            button = new JButton("Czyść plik");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        CzyścicielBiałychGUI.this.czyśćPlik();
                    } catch (IOException ex) {
                        Logger.getLogger(CzyścicielBiałychGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            add(button);
            
            JLabel label = new JLabel("Kodowanie: we:");
            add(label);

            JComboBox kodowanieIn = new JComboBox(Kodowania.Nazwy);
            Dimension d = kodowanieIn.getPreferredSize();
            d.width = 140;
            kodowanieIn.setMaximumSize(d);
            kodowanieIn.setPreferredSize(d);
            kodowanieIn.setMinimumSize(d);
            kodowanieIn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    JComboBox cb = (JComboBox)ae.getSource();
                    String kod = (String)cb.getSelectedItem();
                    CzyścicielBiałychGUI.this.ustawKodowanieIn(kod);
                }
            });
            add(kodowanieIn);
            kodowanieIn.setSelectedItem("UTF-8");
            
            label = new JLabel("wy:");
            add(label);
            JComboBox kodowanieOut = new JComboBox(Kodowania.Nazwy);
            d = kodowanieOut.getPreferredSize();
            d.width = 140;
            kodowanieOut.setMaximumSize(d);
            kodowanieOut.setPreferredSize(d);
            kodowanieOut.setMinimumSize(d);
            kodowanieOut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    JComboBox cb = (JComboBox)ae.getSource();
                    String kod = (String)cb.getSelectedItem();
                    CzyścicielBiałychGUI.this.ustawKodowanieOut(kod);
                }
            });
            add(kodowanieOut);
            kodowanieOut.setSelectedItem("UTF-8");
            
            button = new JButton("Zapisz plik");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        CzyścicielBiałychGUI.this.zapiszPlik();
                    } catch (IOException ex) {
                        Logger.getLogger(CzyścicielBiałychGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            add(button);
        }
    }
    
    private class PasekInformacyjny extends JToolBar {
        public PasekInformacyjny() {
            JLabel a = new JLabel("Info: ");
            add(a);
            add(infoOPliku);
        }
    }
    
    public CzyścicielBiałychGUI() {
        initComponents();
    }
    
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Czyściciel redundantnych białych znaczków");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        pasekNarzędziowy = new PasekNarzędziowy();
        add(pasekNarzędziowy, BorderLayout.NORTH);
        
        dokument = new JTextArea();
        dokument.setEditable(true);
        dokument.setTabSize(4);
        opakowanieDokumentu = new JScrollPane(dokument);
        opakowanieDokumentu.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        opakowanieDokumentu.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(opakowanieDokumentu, BorderLayout.CENTER);
        
        pasekInformacyjny = new PasekInformacyjny();
        add(pasekInformacyjny, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private JLabel infoOPliku = new JLabel("");
    private Charset kodowanieIn;
    private Charset kodowanieOut;
    
    private File aktualnyPlik;
    private JTextArea dokument;
    private JScrollPane opakowanieDokumentu;
    private PasekNarzędziowy pasekNarzędziowy;
    private PasekInformacyjny pasekInformacyjny;
}
