
package narzedzia.strukt_danych;

public class Zbior
{
    private Para[] par;
    private int wolny_indeks;
    private final int MIN_SIZE = 2;
    private final int WSP_ROSN = 2;
    
    public Zbior()
    {
        czy¶æ();
    }
    
    private int indeksElementu(String kl)
    {
        for(int i = 0; i < wolny_indeks; i++)
        {
            if(par[i].klucz == kl)
                return i;
        }
        return -1;
    }
    
    public void wstaw(String kl, int wart) throws IllegalArgumentException
    {
        int id = indeksElementu(kl);
        if(id != -1)
            throw new IllegalArgumentException("Klucz " + kl +
            " ju¿ znajduje siê w zbiorze");
        
        Para nowa = new Para(kl, wart);
        
        if(wolny_indeks < par.length)
            par[wolny_indeks] = nowa;
        else
        {
            Para[] parNowa = new Para[WSP_ROSN * par.length];
            for(int i = 0; i < wolny_indeks; i++)
                parNowa[i] = par[i];
            parNowa[wolny_indeks] = nowa;
            par = parNowa;
        }
        wolny_indeks++;
    }
    
    public int szukaj(String kl) throws IllegalArgumentException
    {
        int id = indeksElementu(kl);
        if(id != -1)
            return par[id].get();
        throw new IllegalArgumentException("Klucz " + kl +
        " nie znajduje siê w zbiorze");
    }
    
    public int szukaj(Para p) throws IllegalArgumentException
    {
        return szukaj(p.klucz);
    }
    
    public int ile()
    {
        return wolny_indeks;
    }
    
    public void czy¶æ()
    {
        par = new Para[MIN_SIZE];
        wolny_indeks = 0;
    }
    
    public String toString()
    {
        StringBuilder wyn = new StringBuilder("{");
        for(int i = 0; i < wolny_indeks; i++)
        {
            wyn.append(par[i].toString() + ", ");
        }
        if(ile() > 0)
        {
            wyn.delete(wyn.length() - 2, wyn.length());
        }
        wyn.append("}");
        return wyn.toString();
    }
    
    public boolean equals(Object obj)
    {
    /* czy lepszym wyj¶ciem nie by³oby zaimplementowanie
        iteratora po zbiorze tak, by móc korzystaæ wtedy
        z zaimplementowaniej metody equals dla obiektów Para?
        Aktualnie jest to niemo¿liwe...
    */
        if(this.getClass() != obj.getClass())
            return false;
        Zbior zb = (Zbior)obj;
        if(this.ile() != zb.ile())
            return false;
        for(int i = 0; i < this.ile(); i++)
        {
            try
            {
                zb.szukaj(this.par[i]);
            }
            catch (IllegalArgumentException e)
            {
                return false;
            }
        }
        return true;
    }
}
