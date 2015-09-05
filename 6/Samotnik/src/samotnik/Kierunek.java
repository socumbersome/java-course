/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package samotnik;

import java.util.Iterator;

/**
 *
 * @author ja
 */
public class Kierunek implements Iterable<Kierunek> {
    private int x, y;
    public final static String[] Kierunki = {"góra", "dół", "lewo", "prawo"};
    public final static String[] KierunkiOdmiana = {"górę", "dół", "lewo", "prawo"};
    public Kierunek() {
        x = 0; y = 0;
    }
    public Kierunek(String s) {
        switch(s) {
            case "góra": 
            case "górę": x = 0; y = -1; break;
            case "prawo": x = 1; y = 0; break;
            case "dół": x = 0; y = 1; break;
            case "lewo": x = -1; y = 0; break;
            default: throw new IllegalArgumentException("Nieznana nazwa kierunku " + s);
        }
    }
    private Kierunek NowyKierunek(int a, int b) {
        if(a == 0 && b < 0)
            return new Kierunek("góra");
        else if(a > 0 && b == 0)
            return new Kierunek("prawo");
        else if(a == 0 && b > 0)
            return new Kierunek("dół");
        return new Kierunek("lewo");
    }
    public int getX() { return x; }
    public int getY() { return y; }
    
    public Punkt zróbPunkt() {
        return new Punkt(x, y);
    }
    
    public static Punkt przesuń(Punkt p, Kierunek k) {
        Punkt kierunkowy = k.zróbPunkt();
        return Punkt.dodaj(p, kierunkowy);
    }
    
    private class KierunekIter implements Iterator<Kierunek> {
        private final int[] kierunkiX = {-1, 0, 1, 0};
        private final int[] kierunkiY = {0, -1, 0, 1}; 
        private int licz;
        public KierunekIter() {
            licz = 0;
        }

        @Override
        public boolean hasNext() {
            return licz < kierunkiX.length;
        }

        @Override
        public Kierunek next() {
            int temp = licz;
            licz++;
            return NowyKierunek(kierunkiX[temp], kierunkiY[temp]);
        }

        @Override
        public void remove() {
        }
    }
    
    @Override
    public Iterator<Kierunek> iterator() {
        return new KierunekIter();
    }
}
