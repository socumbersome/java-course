
package narzedzia.obliczenia;

public class Mniejsze extends Operator2Arg
{   
    public Mniejsze(Wyrazenie a1, Wyrazenie a2)
    {
        super(a1, a2);
    }
    
    public int oblicz()
    {
        if(arg1.oblicz() < arg2.oblicz())
            return 1;
        return 0;
    }
    
    public String toString()
    {
        return "(" + arg1 + " < " + arg2 + ")";
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
        return this.toString() == ((Mniejsze)obj).toString();
    }
}

