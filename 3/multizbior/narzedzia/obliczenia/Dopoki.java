
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;

public class Dopoki extends Instrukcja
{
    private Wyrazenie wyr;
    private Blok wewnatrz;
    
    public Dopoki(Wyrazenie wyr, Blok wewnatrz)
    {
        this.wyr = wyr;
        this.wewnatrz = wewnatrz;
    }
    
    public void wykonaj()
    {
        while(wyr.oblicz() != 0)
        {
            wewnatrz.wykonaj();
        }
    }
    
    public String toString()
    {
        return "while " + wyr.toString() + "\n" + wewnatrz.toString() /*+ "\n"*/;
    }
    
    public boolean equals(Object obj)
    {
        if(this.getClass() != obj.getClass())
            return false;
        /* 2 konwencje: albo chodzi o równość wartości
        całego wyrażenia, albo o identyczność stringów
        reprezentujących dane wyrażenie.
        Skłaniam się ku drugiemu, ponieważ od pierwszego
        jest publiczna(!) metoda oblicz */
        return this.toString() == ((Dopoki)obj).toString();
    }
}

