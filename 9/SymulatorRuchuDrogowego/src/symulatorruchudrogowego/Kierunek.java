/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

/**
 * Obsługuje cztery kierunki Świata: N, E, S, W.
 * Wewnętrznie trzymane również jako wektory jednostkowe.
 * @author Patryk Kajdas
 */
public class Kierunek {
    private int x, y;
    private int id;
    public final static String[] Kierunki = {"N", "E", "S", "W"};
    public final static int lbKierunków = Kierunki.length;
    private final static int[] kierunkiX = {0, 1, 0, -1};
    private final static int[] kierunkiY = {-1, 0, 1, 0};
    
    public Kierunek(String s) {
        for(int i = 0; i < Kierunki.length; i++) {
            if(Kierunki[i].equals(s)) {
                x = kierunkiX[i];
                y = kierunkiY[i];
                id = i;
                return;
            }
        }
        throw new IllegalArgumentException("Nieznana nazwa kierunku " + s);
    }
    
    @Override
    public String toString() {
        return Kierunki[id];
    }
    /**
     * Współrzędna x wektora jednostkowego reprezentującego dany kierunek.
     * @return 
     */
    public int getX() { return x; }
    /**
     * Współrzędna y wektora jednostkowego reprezentującego dany kierunek.
     * @return 
     */
    public int getY() { return y; }
    
    /**
     * Zamienia wektor jednostkowy reprezentujący dany kierunek na Punkt.
     * @return 
     */
    public Punkt zróbPunkt() {
        return new Punkt(x, y);
    }
    
    public Kierunek przeciwny() {
        return new Kierunek(Kierunki[(id + 2) % lbKierunków]);
    }
    
    public Kierunek poLewej() {
        return new Kierunek(Kierunki[(id + 1) % lbKierunków]);
    }
    
    public Kierunek poPrawej() {
        return new Kierunek(Kierunki[(id - 1 + lbKierunków) % lbKierunków]);
    }
    
    /**
     * Zwraca kierunek w którym będziemy zwróceni, jeśli pójdziemy tam,
     * gdzie każe nam Cel c.
     * @param c - Cel
     * @return 
     */
    public Kierunek uwzględnijCel(Cel c) {
        switch(c.getCel()) {
            case "prosto": return przeciwny();
            case "prawo": return poPrawej();
            case "lewo": return poLewej();
        }
        throw new IllegalArgumentException("Nieznany cel " + c);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this.getClass() != obj.getClass())
            return false;
        Kierunek drugi = (Kierunek)obj;
        return toString().equals(drugi.toString());
    }
    
    @Override
    public int hashCode() {
        return Kierunki[id].hashCode();
    }

}