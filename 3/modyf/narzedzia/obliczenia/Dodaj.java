
package narzedzia.obliczenia;

public class Dodaj extends Operator2Arg
{   
    public Dodaj(Wyrazenie a1, Wyrazenie a2)
    {
        super(a1, a2);
    }
    
    public int oblicz()
    {
        return arg1.oblicz() + arg2.oblicz();
    }
    
    public String toString()
    {
        return "(" + arg1 + " + " + arg2 + ")";
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
        return this.toString() == ((Dodaj)obj).toString();
    }
}

