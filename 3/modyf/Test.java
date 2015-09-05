
import narzedzia.obliczenia.*;
import narzedzia.strukt_danych.*;

public class Test
{
    public static void main (String[] args)
    {
        //Zbior vars = new Zbior();
        //vars.wstaw("x", -3);
        //Wyrazenie.setSrodowisko(vars);
        
        
        Wyrazenie w = new Dodaj(
                        new Stala(3),
                        new Stala(5)
                    );
        System.out.println(w+" = "+w.oblicz());
        
        w = new Dodaj(
                new Stala(2),
                new Mnoz(
                    new Zmienna("x"),
                    new Stala(7)
                )
            );
        System.out.println(w+" = "+w.oblicz());
        
        w = new Dziel(
                new Odejmij(
                    new Mnoz(
                        new Stala(3),
                        new Stala(11)
                    ),
                    new Stala(1)
                ),
                new Dodaj(
                    new Stala(7),
                    new Stala(5)
                )
            );
        System.out.println(w+" = "+w.oblicz());
        
        w = new Dziel(
                new Mnoz(
                    new Dodaj(
                        new Zmienna("x"),
                        new Stala(13)
                    ),
                    new Zmienna("x")
                ),
                new Stala(2)
            );
        System.out.println(w+" = "+w.oblicz());
        
        w = new Mniejsze(
                new Dodaj(
                    new Mnoz(
                        new Stala(17),
                        new Zmienna("x")
                    ),
                    new Stala(19)
                ),
                new Stala(0)
            );
        System.out.println(w+" = "+w.oblicz());
          /*Zbior set = new Zbior();
          set.wstaw("abc", 3);
          set.wstaw("ku", -54);
          System.out.println(set.ile());
          set.wstaw("kuw", 7);
          System.out.println(set.szukaj("ku"));
          System.out.println("sdjfskdhfh");
          //System.out.println(set.szukaj("kur"));
          System.out.println(set.ile());
          System.out.println(set);
          Zbior set2 = new Zbior();
          set2.wstaw("ku", 453);
          set2.wstaw("kuw", 7);
          System.out.println(set.equals(set2));
          set2.wstaw("abc", 65);
          System.out.println(set.equals(set2));
          System.out.println(set2);*/
    }
}
