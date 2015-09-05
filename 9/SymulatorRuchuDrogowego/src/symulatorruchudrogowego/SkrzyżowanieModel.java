/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Klasa modelująca (tj. zawierająca logikę) skrzyżownaia dróg równorzędnych.
 * @author Patryk Kajdas
 */
public class SkrzyżowanieModel {
    private final int MAX_AUT = 10;
    private final int TOLER = 50;
    private final int TOLER_SKRĘT = 5;
    public int srodekNS = 300;
    public int srodekWE = 300;
    public int szerokośćPasa;
    
    public Set<Samochód> samochody;
    private List<Samochód> przySkrzyżowaniu;
    public Rectangle strefaSkrętu;
    public Rectangle strefaKoniecznościSygnalizowania;
    private Timer cierpliwośćKierowcy;
    private int licznikAut = 0;
    
    
    /**
     * Ważna uwaga - po wywołaniu konstruktora, zostaje powołana do życia
     * instancja javax.swing.Timer, która (w aktualnej implementacji) co 2,5
     * sekundy sprawdza, czy są jakieś auta przy skrzyżowaniu, i jeśli tak -
     * wyznacza te, które mogą aktualnie przez nie przejechać.
     */
    public SkrzyżowanieModel() {
        //przySkrzyżowaniu = new ArrayList<>();
        //samochody = new HashSet<>();
        przySkrzyżowaniu = Collections.synchronizedList(new ArrayList<Samochód>());
        samochody = Collections.newSetFromMap(Collections.synchronizedMap(new HashMap<Samochód, Boolean>()));
        
        
        szerokośćPasa = (int)(srodekNS / 5.2);//6;
        strefaSkrętu = new Rectangle(srodekNS - szerokośćPasa, srodekWE - szerokośćPasa,
                2 * szerokośćPasa, 2 * szerokośćPasa);
        
        strefaKoniecznościSygnalizowania = new Rectangle(srodekNS - 3*szerokośćPasa, srodekWE - 3*szerokośćPasa,
                6 * szerokośćPasa, 6 * szerokośćPasa);

        cierpliwośćKierowcy = new Timer(2500, pozwólPrzejechać);
        cierpliwośćKierowcy.start();
    }
    
    /**
     * Słuchacz głównego timera. Wyznacza i powiadamia samochody mogące
     * aktualnie przejechać przez skrzyżowanie.
     */
    ActionListener pozwólPrzejechać = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if(przySkrzyżowaniu.isEmpty())
                return;
            
            Set<Samochód> mogąPrzejechać;
            synchronized(przySkrzyżowaniu) {
                mogąPrzejechać = Przepisy.wyznaczMogącePrzejechać(przySkrzyżowaniu);
            }
            
            for(Samochód s : mogąPrzejechać) {
                synchronized(s) {                    
                    s.chybaMożeszSkręcić = true;  
                    s.notifyAll();
                    synchronized(przySkrzyżowaniu) {
                        przySkrzyżowaniu.remove(s);
                    }
                    
                }
            }
        }
    };
    
    /**
     * Uwaga - samochód powinien wywołać tę metodę z argumentem jako on sam,
     * gdy chce on przejechać przez skrzyżowanie oraz znajduje się tuż przy nim.
     * Konwencja ta została przyjęta, gdyż gdyby skrzyżowanie miało śledzić
     * każdy samochód i często(!) sprawdzać, czy nie zbliża się czasem jakieś
     * do skrzyżowania, byłoby to bardzo nieefektywne.
     * @param s - Samochód
     */
    public void chcePrzejechać(Samochód s) {
        cierpliwośćKierowcy.restart();
        
        synchronized(przySkrzyżowaniu) {
            przySkrzyżowaniu.add(s);
        }        
        synchronized(s) {
            while(!s.chybaMożeszSkręcić) {
                try {
                    s.wait();
                } catch (InterruptedException ex) { }
            }
            s.chybaMożeszSkręcić = false;
        }
    }
    
    /**
     * Metoda ta szuka par samochodów znajdujących znajdujących się 
     * niebezpiecznie blisko siebie i, aby uniknąć kolizji, wyrównuje 
     * prędkość samochodu jadącego szybciej do prędkośći drugiego samochodu.
     */
    public void sprCzyBliskoKolizji() {
        double pr1, pr2;
        for(Samochód s1 : samochody) {
            for(Samochód s2 : samochody) {
                if(s1 == s2)
                    continue;

                if( samochodyNiebezpiecznieBlisko(s1, s2) ) {
                    pr1 = s1.getPrędkość();
                    pr2 = s2.getPrędkość();
                    if(pr1 > pr2 /*&& s2.niebezpiecznieBliski != s1*/) {
                        s1.setPrędkość(pr2);
                        s1.niebezpiecznieBliski = s2;
                    }
                    else if(pr1 < pr2 /*&& s1.niebezpiecznieBliski != s2*/) {
                        s2.setPrędkość(pr1);
                        s2.niebezpiecznieBliski = s1;
                    }
                }
                    
            }
        }
    }
    
    /**
     * Zwraca true wtw. gdy samochody przesłane jako argumenty znajdują
     * się niebiezpiecznie blisko siebie.
     * @param s1 - Samochód
     * @param s2 - Samochód
     * @return boolean
     */
    public boolean samochodyNiebezpiecznieBlisko(Samochód s1, Samochód s2) {
        Punkt ps1 = s1.współrzędne();
        Punkt ps2 = s2.współrzędne();
        int ABS_TOLER;
        if(s1.skręcający ^ s2.skręcający) // albo, tj. xor
            //return false;
            ABS_TOLER = TOLER_SKRĘT;
        else
            ABS_TOLER = TOLER;
        return  abs(ps1.getX() - ps2.getX()) < ABS_TOLER && 
                abs(ps1.getY() - ps2.getY()) < ABS_TOLER  && 
                (s1.getKierunek().equals(s2.getKierunek()) ||
                 s1.getKierunek().uwzględnijCel(s1.getCel()).equals(
                 s2.getKierunek().uwzględnijCel(s2.getCel())));
    }
    
    /**
     * Tworzy i ustawia na odpowiedniej jezdni nowy samochód z prędkością
     * losową.
     * @param jezdnia - String: "N", "E", "S" lub "W"
     * @param cel - String: "prosto", "lewo" lub "prawo"
     * @param układOdniesienia 
     * @throws IOException 
     */
    public void nowySamochód(String jezdnia, String cel, AffineTransform układOdniesienia) throws IOException {
        Random random = new Random();
        double pr = random.nextDouble() * 50.0;
        nowySamochód(jezdnia, cel, pr, układOdniesienia);
    }
    
    /**
     * Tworzy i ustawia na odpowiedniej jezdni nowy samochód z prędkością
     * ustaloną.
     * @param jezdnia - String: "N", "E", "S" lub "W"
     * @param cel - String: "prosto", "lewo" lub "prawo"
     * @param pr - początkowa prędkość samochodu, typu double.
     * @param układOdniesienia 
     * @throws IOException 
     */
    public void nowySamochód(String jezdnia, String cel, double pr, AffineTransform układOdniesienia) throws IOException {
        if(samochody.size() >= MAX_AUT) {
            JOptionPane.showMessageDialog(null, "Maksymalna liczba samochodów to "
                    + String.valueOf(MAX_AUT));
            return;
        }
        
        Kierunek kier = new Kierunek(jezdnia);
        Samochód nowy = new Samochód(pr + 10.0, new Cel(cel), kier, this, układOdniesienia);
        samochody.add(nowy);
        nowy.setName("auto" + nowy.getKierunek() + nowy.getCel() + String.valueOf(licznikAut++));
        nowy.start();
    }
    
    /**
     * Usuwa samochód z jezdni.
     * @param s - Samochód 
     */
    public void usuńSamochód(Samochód s) {
        Iterator<Samochód> iter = samochody.iterator();
        while(iter.hasNext()) {
            if(iter.next() == s) {
                iter.remove();
                break;
            }     
        }
    }
}
