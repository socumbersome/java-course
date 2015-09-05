
package narzedzia.obliczenia;

public class Dziel extends Operator2Arg
{   
    public Dziel(Wyrazenie a1, Wyrazenie a2)
    {
        super(a1, a2);
    }
    
    public int oblicz()
    {
        int w2 = arg2.oblicz();
        if(w2 == 0)
        {
            throw new IllegalArgumentException("Niedozwolone dzielenie przez zero");
        }
        int w1 = arg1.oblicz();
        return w1 / w2;
    }
    
    public String toString()
    {
        return "(" + arg1 + " / " + arg2 + ")";
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
        return this.toString() == ((Dziel)obj).toString();
    }
}

