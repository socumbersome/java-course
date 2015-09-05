/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kalkulatoronp.narzędzia;

import kalkulatoronp.narzędzia.strukt_danych.Lista;

/**
 *
 * @author ja
 */
public class Zmienna extends Operand{
    protected String nazwa;
    public static Lista środowisko;
    public Zmienna(String zm) {
        nazwa = zm;
    }

    @Override
    public double obliczWartość() throws WyjątekONP {
        try {
            //System.err.println("chcemy zmienna " + nazwa);
            double wart = środowisko.pokaz(nazwa);
            return wart;
        }
        catch (Exception e) {
            throw new ONP_NieznanySymbol("zmienna: " + nazwa);
        }
    }
    
    public String toString() {
        return nazwa;
    }
}
