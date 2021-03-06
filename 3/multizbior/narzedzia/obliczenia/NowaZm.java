
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;

public class NowaZm extends Instrukcja
{
    private String nazwa;
    
    public NowaZm(String nazwa)
    {
        this.nazwa = nazwa;
        // konwencja: nowo zadeklarowane zmienne są zerowane
    }
    
    public void wykonaj()
    {
        Program.srodowisko.wstaw(nazwa, 0);
        Program.liczbaDeklaracji++;
    }
    
    public String toString()
    {
        return "var " + nazwa + ";\n";
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
        return this.toString() == ((NowaZm)obj).toString();
    }
}

