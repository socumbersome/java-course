
package narzedzia.strukt_danych;

public class Para
{
    public final String klucz;
    private int warto뜻;
    
    Para(String kl, int wart)
    {
        klucz = kl;
        warto뜻 = wart;
    }
    
    public boolean set(int wart)
    {
        if(warto뜻 == wart)
            return false;
        warto뜻 = wart;
        return true;
    }
    
    public int get()
    {
        return warto뜻;
    }
    
    public String toString()
    {
        return "(" + klucz + " : " + warto뜻 + ")";
    }
    
    public boolean equals(Object obj)
    {
        if(this.getClass() != obj.getClass())
            return false;
        return (klucz == ((Para)obj).klucz /*&& warto뜻 == obj.get()*/);
    }
}
