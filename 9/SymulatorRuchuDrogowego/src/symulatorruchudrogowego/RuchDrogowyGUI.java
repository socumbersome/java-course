/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * GUI zawierające okno wyświetlające symulację oraz pasek narzędziowy
 * pozwalający na dodanie nowego samochodu do symulacji.
 * @author Patryk Kajdas
 */
public class RuchDrogowyGUI extends javax.swing.JFrame {
    
    private PasekNarzędziowy pasekNarzędziowy;
    private SkrzyżowanieGUI skrzyżowanieGUI;
    private SkrzyżowanieModel skrzyżowanieModel;
    
    private class PasekNarzędziowy extends JToolBar {
        private JPanel samochódDane;
        private JComboBox jezdnia;
        private JComboBox cel;
        private HashMap<String, String> jezdniaTranslator;
        private HashMap<String, String> celTranslator;
        
        public PasekNarzędziowy() {
            jezdniaTranslator = new HashMap<>();
            celTranslator = new HashMap<>();
            jezdniaTranslator.put("Północna (N)", "N");
            jezdniaTranslator.put("Wschodnia (E)", "E");
            jezdniaTranslator.put("Południowa (S)", "S");
            jezdniaTranslator.put("Zachodnia (W)", "W");
            celTranslator.put("Prosto", "prosto");
            celTranslator.put("W lewo", "lewo");
            celTranslator.put("W prawo", "prawo");
            
            samochódDane = new JPanel();
            samochódDane.add(new JLabel("Jezdnia początkowa: "));
            jezdnia = new JComboBox(jezdniaTranslator.keySet().toArray());
            samochódDane.add(jezdnia);
            samochódDane.add(new JLabel("Kierunek jazdy: "));
            cel = new JComboBox(celTranslator.keySet().toArray());
            samochódDane.add(cel);
            
            JButton button = new JButton("Nowy samochód");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    int odp = JOptionPane.showConfirmDialog(null, samochódDane, 
                        "Podaj specyfikację samochodu", JOptionPane.OK_CANCEL_OPTION);
                    if (odp == JOptionPane.OK_OPTION) {
                        try {
                            Graphics2D g2d = ((Graphics2D)skrzyżowanieGUI.getGraphics());
                            skrzyżowanieModel.nowySamochód(jezdniaTranslator.get(jezdnia.getSelectedItem()),
                                    celTranslator.get(cel.getSelectedItem()), g2d.getTransform());
                        } catch (IOException ex) {
                            Logger.getLogger(RuchDrogowyGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            add(button);
        }
    }
    
    public RuchDrogowyGUI() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Symulator ruchu drogowego");
        setLayout(new BorderLayout());
        setMinimumSize(new java.awt.Dimension(600, 650));
        setResizable(false);
        getContentPane().setLayout(new java.awt.BorderLayout(1, 1));
        
        skrzyżowanieModel = new SkrzyżowanieModel();
        skrzyżowanieGUI = new SkrzyżowanieGUI(skrzyżowanieModel);
        getContentPane().add(skrzyżowanieGUI, java.awt.BorderLayout.CENTER);
        pasekNarzędziowy = new PasekNarzędziowy();
        add(pasekNarzędziowy, BorderLayout.NORTH);
        
        setLocationRelativeTo(null);
        pack();
        
        testuj();
    }
    
    /**
     * Wykonuje przygotowanie scenariusze jazdy.
     * @see Testy
     */
    public void testuj() {
        Graphics2D g2d = ((Graphics2D)skrzyżowanieGUI.getGraphics());
        Testy testy = new Testy(skrzyżowanieModel, g2d.getTransform());
        try {
           // testy.prosto4();
            //testy.sameskręty();
            testy.goniący();
        } catch (IOException ex) {
            Logger.getLogger(RuchDrogowyGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
