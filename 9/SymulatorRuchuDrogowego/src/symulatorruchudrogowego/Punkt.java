/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

/**
 * Prosta implementacja punktu na płaszyźnie o współrzędnych całkowitych.
 * @author Patryk Kajdas
 */
public class Punkt {
    private int x;
    private int y;
    public Punkt(int a, int b) {
        x = a;
        y = b;
    }
    public Punkt(Punkt p) {
        x = p.getX();
        y = p.getY();
    }
    
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    public static Punkt dodaj(Punkt a, Punkt b) {
        return new Punkt(a.getX() + b.getX(), a.getY() + b.getY());
    }
    public void dodaj(Punkt b) {
        x += b.getX();
        y += b.getY();
    }
    
    public void pomnóż(double f) {
        x *= f; y *= f;
    }
    
    /**
     * Przesuwa punkt w kierunku k na odległośc jednostkową.
     * @param k - Kierunek
     */
    public void przesuń(Kierunek k) {
        Punkt kierunkowy = k.zróbPunkt();
        dodaj(kierunkowy);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this.getClass() != obj.getClass())
            return false;
        Punkt drugi = (Punkt)obj;
        return (x == drugi.getX() && y == drugi.getY());
    }
}