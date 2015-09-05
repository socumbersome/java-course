
package narzedzia.obliczenia;

import narzedzia.strukt_danych.*;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.*;

public class Blok extends Instrukcja
{
    private Sekwencja sekw;
    
    public Blok(Sekwencja sekw)
    {
        this.sekw = sekw;
    }
    
    public void wykonaj()
    {
        int deklaracjiPrzed = Program.liczbaDeklaracji;
        sekw.wykonaj();
        int deklaracjiPo = Program.liczbaDeklaracji;
        for(int i = deklaracjiPo - deklaracjiPrzed; i > 0; i--)
            Program.srodowisko.usunOstatni();
        Program.liczbaDeklaracji = deklaracjiPrzed; 
    }
    
    public String toString()
    {
        String print = "{\n";
        BufferedReader reader = new BufferedReader(new StringReader(sekw.toString()));
        String line = null;
        try{
            while((line = reader.readLine()) != null)
            {
                print += "    " + line + "\n";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
        return print + "}\n";
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
        return this.toString() == ((Blok)obj).toString();
    }
}

