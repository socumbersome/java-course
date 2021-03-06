
import java.util.ArrayList;

public class LiczbyPierwsze
{
    protected final static int POTEGA2 = 21;
    protected final static int ZAKRES = (1<<POTEGA2);
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
    
    private static boolean czyPierwszaQuick(long x) // false -> z�o�ona; true -> niewiadomo
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
            
        int upper = (int)(Math.sqrt(x));
        for(long i = 3; i <= upper; i += 2)
            if(x % i == 0)
                return false;
        
        return true;
    }
    
    public static ArrayList<Long> naCzynnikiPierwsze(long x)
    {
        ArrayList<Long> rozklad = new ArrayList<Long>();
        
        if(x >= -1 && x <= 1)
        {
            rozklad.add(x);
            return rozklad;
        }
        if(x == -9223372036854775808L)
        {
            rozklad.add(-1L);
            for(int i = 1; i < 64; i++)
                rozklad.add(2L);
            return rozklad;
        }
        if(czyPierwsza(x))
        {
            rozklad.add(x);
            return rozklad;   
        }
        
        if(x < 0)
        {
            rozklad.add(-1L);
            x = -x;
        }
        while(x % 2 == 0)
        {
            rozklad.add(2L);
            x = x / 2;
        }

        boolean ponadPierw = false;
        for(int i = 3; i < ZAKRES && i <= Math.round(Math.sqrt(x)); i += 2)
        {
            if(i != SITO[i])
                continue;
            while(x % i == 0)
            {
                rozklad.add(new Long(i));
                x = x / i;
            }
            if( i > Math.round(Math.sqrt(x)) )
            {
                ponadPierw = true;
                break;
            }
            else if( i >= ZAKRES )
                break;
        }
        
        if(czyPierwsza(x))
        {
            rozklad.add(x);
            return rozklad;   
        }
        
        boolean zmniejszonyZakres = false;
        if(!ponadPierw && x >= ZAKRES)
        {
            for(long p = ZAKRES; p <= Math.round(Math.sqrt(x)); p+= 2)
            {             
                if(x % p == 0 && czyPierwsza(p))
                {
                    while(x % p == 0)
                    {
                        rozklad.add(p);
                        x = x / p;
                    }
                }
                if(x < ZAKRES)
                {   
                    zmniejszonyZakres = true;
                    break;
                }
            }
        }
        
        if(zmniejszonyZakres)
            while(x > 1)
            {
                rozklad.add(new Long(SITO[(int)x]));
                x = x / SITO[(int)x];
            }
        else if(czyPierwsza(x))
            rozklad.add(x);

        return rozklad;
    }
}
