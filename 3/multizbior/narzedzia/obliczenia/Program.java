
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;

public abstract class Program
{
    protected static Multizbior srodowisko = new Multizbior();
    protected static int liczbaDeklaracji = 0;
    
    public abstract void wykonaj();
}

