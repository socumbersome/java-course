
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;
import java.util.ArrayList;

public abstract class Instrukcja
{
    protected static ArrayList<Para> srodowisko = new ArrayList<Para>();
    
    public abstract void wykonaj();
    
    /*public static void setSrodowisko(Zbior zmienne)
    {
        srodowisko = zmienne;
    }*/
}

