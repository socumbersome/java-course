
package narzedzia.obliczenia;

public class Stala extends Wyrazenie
{
    protected final int stala;
    
    public Stala(int c)
    {
        stala = c;
    }
    
    public int oblicz()
    {
        return stala;
    }
    
    public String toString()
    {
        return String.valueOf(stala);
    }
    
    public boolean equals(Object obj)
    {
        if(this.getClass() != obj.getClass())
            return false;
        /* 2 konwencje: albo chodzi o równo¶æ warto¶ci
        ca³ego wyra¿enia, albo o identyczno¶æ stringów
        reprezentuj±cych dane wyra¿enie.
        Sk³aniam siê ku drugiemu, poniewa¿ od pierwszego
        jest publiczna(!) metoda oblicz */
        return this.toString() == ((Stala)obj).toString();
    }
}

