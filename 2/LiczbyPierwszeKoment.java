
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class LiczbyPierwsze
{
    protected final static int POTEGA2 = 21;
    protected final static int ZAKRES = (1<<POTEGA2) - 1;
    protected final static int[] SITO = new int[ZAKRES];
    protected final static int[] TESTOWE = new int[]{2, 3, 5, 7, 11, 13, 17};
    protected final static int TESTOWE_IL;
    
    static
    {
        int temp = 1;
        for(int i = 0; i < TESTOWE.length; i++)
            temp *= TESTOWE[i];
        TESTOWE_IL = temp;
        
        java.util.Arrays.fill(SITO, 0);
        SITO[1] = 1;
        for(int i = 2; i < ZAKRES; i++)
        {
            for(int j = i; j < ZAKRES; j += i)
            {
                if(SITO[j] == 0)
                    SITO[j] = i;
            }
        }
    }
    
    public static boolean czyPierwszaQuick(long x) // false -> z³o¿ona; true -> niewiadomo
    {
        int rem = (int)(x % TESTOWE_IL);
        for(int i = 0; i < TESTOWE.length; i++)
            if(rem % TESTOWE[i] == 0)
                return false;
        return true;
    }
    
    public static boolean czyPierwsza(long x)
    {
        if(x < 2)
            return false;
        if(x % 2 == 0)
            return (x==2);
        
        if(x < ZAKRES)
            return (x == SITO[(int)x]);
        
        if(!czyPierwszaQuick(x))
            return false;
       /* int rem = (int)(x % TESTOWE_IL);
        for(int i = 0; i < TESTOWE.length; i++)
            if(rem % TESTOWE[i] == 0)
                return false;*/
            
        int upper = (int)(Math.sqrt(x)) /*+ 1*/;
        //System.out.println("upper = " + upper);
        for(long i = 3; i <= upper /*&& i < x*/; i += 2)
        {
            if(i < ZAKRES)
            {
                if(i < 0)
                    System.out.println("i = " + i + " o kurwa!");
                if(i != SITO[(int)i])
                    continue;
                //int p = SITO[i];
                if(x % i == 0)
                    return false;
            }
            else
            {
                /*int rem = (int)(i % TESTOWE_IL);
                for(int j = 0; j < TESTOWE.length; j++)
                    if(rem % TESTOWE[j] == 0)
                        return false;*/
                if(!czyPierwszaQuick(i))
                    continue;
                if(x % i == 0)
                    return false;
            }
        }
        return true;
    }
    
    public static ArrayList<Long> naCzynnikiPierwsze(long x)
    {
        if(x >= -1 && x <= 1)
        {
            ArrayList<Long> temp = new ArrayList<Long>();
            temp.add(x);
            return temp;
            //return new Long[]{x};
        }
        if(x == -9223372036854775808L)
        {
            ArrayList<Long> temp = new ArrayList<Long>();
            //Long[] tempTab = new Long[64];
            //java.util.Arrays.fill(tempTab, 2L);
            temp.add(-1L);
            for(int i = 1; i < 64; i++)
                temp.add(2L);
            return temp;
        }
        
        boolean chocJedenDodany = false;
        ArrayList<Long> rozklad = new ArrayList<Long>();
        if(x < 0)
        {
            rozklad.add(-1L);
            x = -x;
        }
        
        //long upper = Math.round(Math.sqrt(x)) + 1;
        //System.out.println("pierwszy while");
        
        while(x % 2 == 0)
        {
            rozklad.add(2L);
            x = x / 2;
            chocJedenDodany = true;
        }
        //System.out.println("rozkl ma rozmiar " + rozklad.size());
        System.out.println("przed 1 faza");
        boolean koniec = false;
        for(int i = 3; i < ZAKRES && i <= Math.round(Math.sqrt(x)); i += 2)
        {
            if(i != SITO[i])
                continue;
            while(x % i == 0)
            {
                rozklad.add(new Long(i));
                x = x / i;
                chocJedenDodany = true;
            }
            if( i > Math.round(Math.sqrt(x)) )
            {
                koniec = true;
                break;
            }
            else if( i >= ZAKRES )
                break;
        }
        System.out.println("przed 2 faza");
        boolean zmniejszonyZakres = false;
        if(czyPierwsza(x))
        {
            rozklad.add(x);
            return rozklad;   
        }
        else if(!koniec && x >= ZAKRES)
        {
            for(long p = ZAKRES; p <= Math.round(Math.sqrt(x)); p+= 2)
            {
                if(p % 1000000000 == 0)
                    System.out.println("przekrecilismy " + p);
                
                if(!czyPierwsza(p))
                    continue;
                while(x % p == 0)
                {
                    rozklad.add(p);
                    x = x / p;
                    chocJedenDodany = true;
                }
                if(x < ZAKRES)
                {   
                    zmniejszonyZakres = true;
                    break;
                }
            }
        }
        System.out.println("przed 3 faza");
        if(zmniejszonyZakres)
            while(x > 1)
            {
                rozklad.add(new Long(SITO[(int)x]));
                x = x / SITO[(int)x];
                chocJedenDodany = true;
            }
        else if(/*!chocJedenDodany || x != 1*/ czyPierwsza(x))
            rozklad.add(x);
        //System.out.println("po drugim while");
        //System.out.println("rozkl ma rozmiar " + rozklad.size());
        return rozklad;
        //return rozklad.toArray(new Long[rozklad.size()]);
    }
}
