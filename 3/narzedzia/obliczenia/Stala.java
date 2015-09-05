
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
        /* 2 konwencje: albo chodzi o r�wno�� warto�ci
        ca�ego wyra�enia, albo o identyczno�� string�w
        reprezentuj�cych dane wyra�enie.
        Sk�aniam si� ku drugiemu, poniewa� od pierwszego
        jest publiczna(!) metoda oblicz */
        return this.toString() == ((Stala)obj).toString();
    }
}

