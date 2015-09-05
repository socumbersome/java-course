
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
        /* 2 konwencje: albo chodzi o r�wno�� warto�ci
        ca�ego wyra�enia, albo o identyczno�� string�w
        reprezentuj�cych dane wyra�enie.
        Sk�aniam si� ku drugiemu, poniewa� od pierwszego
        jest publiczna(!) metoda oblicz */
        return nazwa == ((Zmienna)obj).toString();
    }
}

