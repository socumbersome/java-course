
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;

public class Przypisz extends Instrukcja
{
    private String zm;
    private Wyrazenie wyr;
    
    public Przypisz(String zm, Wyrazenie wyr)
    {
        this.zm = zm;
        this.wyr = wyr;
    }
    
    public void wykonaj()
    {
        // wersja ciut leniwa. Gorliwa liczyłaby wynik już w konstruktorze.
        Program.srodowisko.aktualizuj(zm, wyr.oblicz());
    }
    
    public String toString()
    {
        return zm + " = " + wyr.toString() + ";\n";
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
        return this.toString() == ((Przypisz)obj).toString();
    }
}

