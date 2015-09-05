
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;

public abstract class Program
{
    protected static Zbior srodowisko;
    
    public abstract void wykonaj();
    
    public static void setSrodowisko(Zbior zmienne)
    {
        srodowisko = zmienne;
    }
}

