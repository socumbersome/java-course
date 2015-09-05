/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package samotnik;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import samotnik.Translator.TypPola;

/**
 *
 * @author ja
 */
public class OknoGry extends javax.swing.JPanel {
    
    private MouseListener PoleListen = new MouseAdapter() {
        public void mousePressed(MouseEvent evt) {
            if(evt.getButton() == MouseEvent.BUTTON1)
            {
                Pole kliknięte = (Pole)evt.getSource();
                Punkt kliknięty = new Punkt(kliknięte.getXNaPlanszy(), kliknięte.getYNaPlanszy());
                if(LogikaPlanszy.getTypPola(kliknięty) == TypPola.WOLNE) {
                    Punkt poprz = LogikaPlanszy.getAktualnePole();
                    Kierunek kier = new Kierunek();
                    Punkt przesunięty;
                    for(Kierunek k : kier) {
                        przesunięty = Kierunek.przesuń(poprz, k);
                        przesunięty = Kierunek.przesuń(przesunięty, k);
                        if(przesunięty.equals(kliknięty)) {
                            WykonajRuch(k);
                            break;
                        }
                    }
                }
                else {
                    ZaznaczNowe(kliknięty);
                }
            }
        }
    };
    
    private class Pole extends JPanel {
        
        private Color kolor = null;
        boolean wyr = false;
        private final int x, y;
        public Pole(int a, int b) {
            super();
            x = a;
            y = b;
        }
        public int getXNaPlanszy() { return x; }
        public int getYNaPlanszy() { return y; }
        public void setKolor(Color col) {
            kolor = col;
        }
        public Color getKolor() {
            return kolor;
        }
        public void setWyróżnienie(boolean wyr) {
            this.wyr = wyr;
        }
        public boolean Wyróżnione() {
            return wyr;
        }
        protected void paintComponent(Graphics g) {
            if(kolor == null)
                return;
            g.setColor(kolor);
            g.fillOval(0, 0, g.getClipBounds().width-1, g.getClipBounds().height-1);
            if(wyr) {
                g.setColor(Color.black);
                g.draw3DRect(0, 0, g.getClipBounds().width-1, g.getClipBounds().height-1, true);
            }
        }
    }
    
    Color ZajęteKolor = Color.green;
    Color WolneKolor = Color.black;
    Color Tło = Color.white;
    private JLabel stanGryL, pozostałychPionówL;
    private JPopupMenu PolePopup;
    private PlanszaModel LogikaPlanszy;
    private int rozmiarX, rozmiarY;
    private Pole[][] siatka;
    
    public OknoGry(PlanszaModel LogikaPlanszy, JLabel stanGryL, JLabel pozostałychPionówL) {
        super();
        setPreferredSize(new Dimension(400,400));
        this.stanGryL = stanGryL;
        this.pozostałychPionówL = pozostałychPionówL;
        this.LogikaPlanszy = LogikaPlanszy;
        rozmiarX = LogikaPlanszy.getRozmiarPlanszyX();
        rozmiarY = LogikaPlanszy.getRozmiarPlanszyY();
        setLayout(new GridLayout(rozmiarY, rozmiarX));
        siatka = new Pole[rozmiarY][rozmiarX];
        PolePopup = new JPopupMenu();
        JMenuItem skok;
        for(final String kier : Kierunek.KierunkiOdmiana) {
            skok = new JMenuItem("Skok w " + kier);
            skok.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Pole kliknięte = (Pole)PolePopup.getInvoker();
                    Punkt kliknięty = new Punkt(kliknięte.getXNaPlanszy(), kliknięte.getYNaPlanszy());
                    ZaznaczNowe(kliknięty);
                    WykonajRuch(new Kierunek(kier));
                }
            });
            PolePopup.add(skok);
        }
        JMenuItem kolor = new JMenuItem("Zmień kolor");
        kolor.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                Pole kliknięte = (Pole)PolePopup.getInvoker();
                if(kliknięte.getKolor() == null)
                    return;
                Color c = JColorChooser.showDialog(PolePopup.getInvoker(),
                        "Wybierz kolor piona", kliknięte.getKolor());
                if(c != null)
                    kliknięte.setKolor(c);
            }
        });
        PolePopup.add(kolor);
        
        for(int i = 0; i < rozmiarY; i++)
            for(int j = 0; j < rozmiarX; j++) {
                siatka[i][j] = new Pole(j, i);
                add(siatka[i][j]);
                siatka[i][j].addMouseListener(PoleListen);
                siatka[i][j].setComponentPopupMenu(PolePopup);
            }
        
        MalujPlanszę();
    }
    
    private void MalujPlanszę() {
        setBackground(Color.red);//Tło);
        TypPola akttyp;
        Color aktkol;
        for(int i = 0; i < rozmiarY; i++)
            for(int j = 0; j < rozmiarX; j++) {
                akttyp = LogikaPlanszy.getTypPola(j, i);
                if(akttyp == TypPola.ZAJĘTE)
                    aktkol = ZajęteKolor;
                else if(akttyp == TypPola.WOLNE)
                    aktkol = WolneKolor;
                else
                    aktkol = null;
                siatka[i][j].setKolor(aktkol);
                siatka[i][j].setWyróżnienie(false);
            }
        Punkt akt = LogikaPlanszy.getAktualnePole();
        siatka[akt.getY()][akt.getX()].setWyróżnienie(true);
        
        this.stanGryL.setText("w toku");
        aktualizujLiczbęPionów();
        
        repaint();
    }
    
    public void przekolorujPiony(Color k) {
        TypPola akttyp;
        for(int i = 0; i < rozmiarY; i++)
            for(int j = 0; j < rozmiarX; j++) {
                akttyp = LogikaPlanszy.getTypPola(j, i);
                if(akttyp == TypPola.ZAJĘTE)
                    siatka[i][j].setKolor(k);
            }
        repaint();
    }
    
    public void kolorujLosowo() {
        TypPola akttyp;
        Random random = new Random();
        for(int i = 0; i < rozmiarY; i++)
            for(int j = 0; j < rozmiarX; j++) {
                akttyp = LogikaPlanszy.getTypPola(j, i);
                if(akttyp == TypPola.ZAJĘTE)
                    siatka[i][j].setKolor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            }
        repaint();
    }
    
    public void setTło(Color k) {
        Tło = k;
        setBackground(Tło);
        repaint();
    }
    
    private void aktualizujLiczbęPionów() {
        pozostałychPionówL.setText(String.valueOf(this.LogikaPlanszy.PozostałychPionów()) + " ");
    }
    
    private void setWyróżnienie(Punkt p, boolean b){
        siatka[p.getY()][p.getX()].setWyróżnienie(b);
    }
    
    private void setKolor(Punkt p, Color k) {
        siatka[p.getY()][p.getX()].setKolor(k);
    }
    private Color getKolor(Punkt p) {
        return siatka[p.getY()][p.getX()].getKolor();
    }
    
    public void WykonajRuch(Kierunek kier) {
        Punkt poprz = LogikaPlanszy.getAktualnePole();
        Color poprzedniego = getKolor(poprz);
        if(LogikaPlanszy.przesuńPion(kier)) {
            Punkt zbity = Kierunek.przesuń(poprz, kier);
            Punkt nowe = LogikaPlanszy.getAktualnePole();
            setKolor(poprz, WolneKolor);
            setKolor(zbity, WolneKolor);
            setKolor(nowe, poprzedniego);
            setWyróżnienie(poprz, false);
            setWyróżnienie(nowe, true);
            aktualizujLiczbęPionów();
            sprawdźStanGry();
            repaint();
        }
    }
    
    private void sprawdźStanGry() {
        switch(LogikaPlanszy.stanGry()) {
            case WTOKU: break;
            case PRZEGRANA:
                stanGryL.setText("Nie można wykonać ruchu - przegrałeś.");
                break;
            case WYGRANA:
                stanGryL.setText("Brawo! Wygrałeś!");
        }
    }
    public void ZaznaczNowe(Punkt p) {
        Punkt poprz = LogikaPlanszy.getAktualnePole();
        if(LogikaPlanszy.przesuńPole(p)) {
            setWyróżnienie(poprz, false);
            setWyróżnienie(LogikaPlanszy.getAktualnePole(), true);
            repaint();
        }
    }
    public void ZaznaczNowe(Kierunek kier) {
        ZaznaczNowe(Kierunek.przesuń(LogikaPlanszy.getAktualnePole(), kier));
    }
    
    public void NowaGra() {
        MalujPlanszę();
    }
    
    /*public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }*/
}
