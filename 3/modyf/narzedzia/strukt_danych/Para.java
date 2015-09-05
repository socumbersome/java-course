
package narzedzia.strukt_danych;

public class Para
{
    public final String klucz;
    private int warto��;
    
    Para(String kl, int wart)
    {
        klucz = kl;
        warto�� = wart;
    }
    
    public boolean set(int wart)
    {
        if(warto�� == wart)
            return false;
        warto�� = wart;
        return true;
    }
    
    public int get()
    {
        return warto��;
    }
    
    public String toString()
    {
        return "(" + klucz + " : " + warto�� + ")";
    }
    
    public boolean equals(Object obj)
    {
        if(this.getClass() != obj.getClass())
            return false;
        return (klucz == ((Para)obj).klucz /*&& warto�� == obj.get()*/);
    }
}