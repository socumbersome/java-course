/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa zawierająca scenariusze testujące symulator ruchu drogowego.
 * @author Patryk Kajdas
 * @see RuchDrogowyGUI
 */
public class Testy {
    SkrzyżowanieModel sm;
    SkrzyżowanieGUI sg;
    AffineTransform aft;
    public Testy(SkrzyżowanieModel sm, AffineTransform aft) {
        this.sm = sm;
        this.aft = aft;
    }
    
    public void prosto4() throws IOException {
        sm.nowySamochód("W", "prosto", 40, aft);
        sm.nowySamochód("S", "prosto", 39, aft);
        sm.nowySamochód("E", "prosto", 38, aft);
        sm.nowySamochód("N", "prosto", 25, aft);
    }
    
    public void przeciwwprzeciw() throws IOException {
        sm.nowySamochód("W", "lewo", 30, aft);
        sm.nowySamochód("E", "lewo", 26, aft);
    }
    
    public void sameskręty() throws IOException {
        sm.nowySamochód("W", "prawo", 30, aft);
        sm.nowySamochód("S", "prawo", 26, aft);
        sm.nowySamochód("E", "lewo", 26, aft);
        sm.nowySamochód("N", "lewo", 26, aft);
    }
    
    TimerTask dogoń = new TimerTask() {
        @Override
        public void run() {
            try { 
                sm.nowySamochód("E", "prawo", 30, aft);
            } catch (IOException ex) {
                Logger.getLogger(Testy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    public void goniący() throws IOException {
        sm.nowySamochód("E", "lewo", 18, aft);
        sm.nowySamochód("S", "prosto", 24, aft);
        Timer t = new Timer();
        t.schedule(dogoń, 2000);
    }
}
