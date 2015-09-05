/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biurownik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ja
 */
public class BiurownikGUI extends JFrame {
    
    private class PasekNarzędziowy extends JToolBar {
    
        public PasekNarzędziowy(/*BiurownikGUI BGUI*/) {
           // this.BGUI = BGUI;
            initComponents();
        }

        private void initComponents() {
            JButton button = null;
            button = new JButton("Nowy");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    BiurownikGUI.this.NowyDokument();
                }
            });
            add(button);

            button = new JButton("Wypisz na stdout");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    BiurownikGUI.this.WypiszTreść();
                }
            });
            add(button);

            button = new JButton("Zamknij bieżący");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    BiurownikGUI.this.ZamknijBieżący();
                }
            });
            add(button);

            dokumenty = new JComboBox(new String[] {});
            dokumenty.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    JComboBox cb = (JComboBox)ae.getSource();
                    if(cb == null) {
                        return;
                    }
                    String nazwa = (String)cb.getSelectedItem();
                    if(nazwa == null) { // w przypadku listy pustej
                        return;
                    }
                    BiurownikGUI.this.AktywujDokument(nazwa);
                }
            });
            add(dokumenty);

            JComboBox wygląd = new JComboBox(Wyglądy.Nazwa);
            wygląd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    JComboBox cb = (JComboBox)ae.getSource();
                    String laf = Wyglądy.NazwaToLAF((String)cb.getSelectedItem());
                    BiurownikGUI.this.ZmieńWygląd(laf);
                }
            });
            add(wygląd);

        }

        public void dodajDokument(String nazwa) {
            dokumenty.addItem(nazwa);
        }

        public void usuńDokument(String nazwa) {
            dokumenty.removeItem(nazwa);
        }

        private JComboBox dokumenty;
        //private final BiurownikGUI BGUI;
    }
    
    public BiurownikGUI() {
        initComponents();
    }
    
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Biurownik");
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        pasekNarzędziowy = new PasekNarzędziowy(/*this*/);
        add(pasekNarzędziowy, BorderLayout.NORTH);
        obszarRoboczy = new JDesktopPane();
        obszarRoboczy.setVisible(true);
        add(obszarRoboczy);
        
        oknaDokumentów = new HashMap<>();
        
        setVisible(true);
    }
    
    private void NowyDokument() {
        String nazwa = JOptionPane.showInputDialog(this, 
                "Podaj unikalną nazwę nowego dokumentu", "Nowy dokument", PLAIN_MESSAGE);
        if(nazwa == null)
            return;
        nazwa = nazwa.trim();
        if(nazwa.equals("") || duplikat(nazwa)) {
            return;
        }
        OknoDokumentu nowe = new OknoDokumentu(nazwa);
        obszarRoboczy.add(nowe);
        nowe.setVisible(true);
        try {
            nowe.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(BiurownikGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        oknaDokumentów.put(nazwa, nowe);
        pasekNarzędziowy.dodajDokument(nazwa);
    }
    
    private boolean duplikat(String nazwa) {
        return oknaDokumentów.containsKey(nazwa);
    }
    
    private void WypiszTreść() {
        if(oknaDokumentów.isEmpty())
            return;
        
        OknoDokumentu od = oknaDokumentów.get(NazwaAktywnego());
        System.out.println(od.TekstWewnątrz());
    }
    
    private void ZamknijBieżący() {
        if(oknaDokumentów.isEmpty())
            return;
        
        String nazwa = NazwaAktywnego();
        OknoDokumentu od = oknaDokumentów.get(nazwa);
        oknaDokumentów.remove(nazwa);
        pasekNarzędziowy.usuńDokument(nazwa);
        od.dispose();
    }
    
    private void AktywujDokument(String nazwa) {
        OknoDokumentu od = oknaDokumentów.get(nazwa);
        try {
            od.setLocation(0, 0);
            od.setVisible(true);
            od.setSelected(true);
            obszarRoboczy.getDesktopManager().activateFrame(od);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(BiurownikGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ZmieńWygląd(String laf) {
        try {
            UIManager.setLookAndFeel(laf);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BiurownikGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private String NazwaAktywnego() {
        for(String nazwa : oknaDokumentów.keySet()) {
            if(oknaDokumentów.get(nazwa).isSelected())
                return nazwa;
        }
        throw new RuntimeException("Brak aktywnego okna");
    }
    
    private JDesktopPane obszarRoboczy;
    private PasekNarzędziowy pasekNarzędziowy;
    private HashMap<String, OknoDokumentu> oknaDokumentów;
    
}
