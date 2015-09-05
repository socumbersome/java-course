
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;

public class Sekwencja extends Instrukcja
{
    private Instrukcja pierwsza;
    private Sekwencja dalsze;
    
    public Sekwencja(Instrukcja pierwsza, Sekwencja dalsze)
    {
        this.pierwsza = pierwsza;
        this.dalsze = dalsze;
    }
    public Sekwencja(Instrukcja jedyna)
    {
        this.pierwsza = jedyna;
        dalsze = null;
    }
    
    public void wykonaj()
    {
        pierwsza.wykonaj();
        if(dalsze != null)
            dalsze.wykonaj();
    }
    
    public String toString()
    {
        String print = pierwsza.toString() /*+ "\n"*/;
        if(dalsze != null)
            print += /*"\n" +*/ dalsze.toString();
        return print;
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
        return this.toString() == ((Sekwencja)obj).toString();
    }
}

