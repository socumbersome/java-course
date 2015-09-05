
package narzedzia.obliczenia;

import static java.lang.Math.abs;

public class WartBezwzgl extends Operator1Arg
{   
    public WartBezwzgl(Wyrazenie a1)
    {
        super(a1);
    }
    
    public int oblicz()
    {
        return abs(arg1.oblicz());
    }
    
    public String toString()
    {
        return "|" + arg1 + "|";
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
        return this.toString() == ((WartBezwzgl)obj).toString();
    }
}

