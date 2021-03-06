
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;

public class Wypisz extends Instrukcja
{
    private String zm;
    
    public Wypisz(String zm)
    {
        this.zm = zm;
    }
    
    public void wykonaj() throws RuntimeException
    {
        int wart;
        try
        {
            wart = Program.srodowisko.szukaj(zm);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Zmienna " + zm +
                "nie jest w zasiegu aktualnego bloku");
        }
        System.out.println(wart);
    }
    
    public String toString()
    {
        return "write " + zm + ";\n";
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
        return this.toString() == ((Wypisz)obj).toString();
    }
}

