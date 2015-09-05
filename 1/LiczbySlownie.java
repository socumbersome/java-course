
public class LiczbySlownie
{
    public static void main (String[] args)
    {
        int x;
        
        for(int i = 0; i < args.length; i++)
        {
            x = new Integer(args[i]);
            System.out.println(Konwersja.konwertuj(x));
        }
    }
}
