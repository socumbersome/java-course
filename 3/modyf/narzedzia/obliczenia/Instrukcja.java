
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;

public abstract class Instrukcja extends Program
{
    protected static ArrayList<Para> srodowisko = new ArrayList<Para>();
    
    public abstract void wykonaj();
    
    /*public static void setSrodowisko(Zbior zmienne)
    {
        srodowisko = zmienne;
    }*/
}

