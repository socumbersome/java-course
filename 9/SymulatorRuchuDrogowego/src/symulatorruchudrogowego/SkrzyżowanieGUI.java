/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Okno do wyświetlania symulacji. Bazuje na logice dostarczanej przez
 * model typu SkrzyżowanieModel.
 * @see SkrzyżowanieModel
 * @author Patryk Kajdas
 */
public class SkrzyżowanieGUI extends JPanel {
    private SkrzyżowanieModel skrzyżowanieModel;
    
    ActionListener odświeżanie = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
          SkrzyżowanieGUI.this.repaint();
          skrzyżowanieModel.sprCzyBliskoKolizji();
      }
    };
    
    /**
     * Uwaga - GUI potrzebuje modelu typu SkrzyżowanieModel
     * @param sm 
     */
    public SkrzyżowanieGUI(SkrzyżowanieModel sm) {
        super();
        skrzyżowanieModel = sm;
        setSize(2 * skrzyżowanieModel.srodekNS, 2 * skrzyżowanieModel.srodekWE);
        setLayout(null);
        setDoubleBuffered(true);
        setOpaque(true);
        setBackground(new Color(20,120,20));
        new Timer(40, odświeżanie).start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.gray);
        g.fillRect(0, skrzyżowanieModel.srodekWE - skrzyżowanieModel.szerokośćPasa, this.getWidth(), 2 * skrzyżowanieModel.szerokośćPasa);
        g.fillRect(skrzyżowanieModel.srodekNS - skrzyżowanieModel.szerokośćPasa, 0, 2 * skrzyżowanieModel.szerokośćPasa, this.getHeight());
        Graphics2D g2d = (Graphics2D) g.create();
        for (Samochód s : skrzyżowanieModel.samochody) {
            s.paint(g2d);
        }
        g2d.dispose();
    }
}
