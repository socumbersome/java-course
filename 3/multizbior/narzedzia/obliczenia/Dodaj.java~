
package narzedzia.obliczenia;

public class PrzeciwnyZnak extends Operator1Arg
{   
    public PrzeciwnyZnak(Wyrazenie a1)
    {
        super(a1);
    }
    
    public int oblicz()
    {
        return -arg1.oblicz();
    }
    
    public String toString()
    {
        return "-" + arg1;
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
        return this.toString() == ((PrzeciwnyZnak)obj).toString();
    }
}

