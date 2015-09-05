/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package samotnik;

/**
 *
 * @author ja
 */
public class Punkt {
    private final int x;
    private final int y;
    public Punkt(int a, int b) {
        x = a;
        y = b;
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
    
    public boolean equals(Object obj) {
        if(this.getClass() != obj.getClass())
            return false;
        Punkt drugi = (Punkt)obj;
        return (x == drugi.getX() && y == drugi.getY());
    }
}
