/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rysunek.grafika;

import java.awt.Color;
import java.awt.Point;

/**
 * Klasa reprezentująca Kreskę jako parę dwóch punktów oraz określony kolor.
 * 
 * @author Patryk Kajdas
 */
public class Kreska {
    protected Point poczatek, koniec;
    public final Color kolor;
    
    public Kreska(Point pocz, Point kon, Color kol) {
        poczatek = pocz;
        koniec = kon;
        kolor = kol;
    }
    /**
     * 
     * @return początek Kreski jako punkt
     */
    public Point get_poczatek() {
        return poczatek;
    }
    /**
     * 
     * @return koniec Kreski jako punkt
     */
    public Point get_koniec() {
        return koniec;
    }
    
    /**
     *
     * @param obj dowolny obiekt
     * @return true wtedy i tylko wtedy, gdy obj równy kresce this
     */
    @Override
    public boolean equals(Object obj) {
        if(this.getClass() != obj.getClass())
            return false;
        Kreska druga = (Kreska)obj;
        return (poczatek.equals(druga.get_poczatek()) && 
                koniec.equals(druga.get_koniec()) &&
                kolor.equals(druga.kolor));
    }
}
