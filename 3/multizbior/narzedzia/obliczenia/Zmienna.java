
package narzedzia.obliczenia;

public class Zmienna extends Wyrazenie
{
    private String nazwa;
    
    public Zmienna(String zm)
    {
        nazwa = zm;
    }
    
    public int oblicz()
    {
        return Program.srodowisko.szukaj(nazwa);
    }
    
    public String toString()
    {
        return nazwa;
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
        return nazwa == ((Zmienna)obj).toString();
    }
}

