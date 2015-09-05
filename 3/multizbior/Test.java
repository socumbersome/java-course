
import narzedzia.obliczenia.*;
import narzedzia.strukt_danych.*;

public class Test
{
    public static void main (String[] args)
    {
        Program silnia = new Sekwencja(
                            new NowaZm("silnia"),
                            new Sekwencja(
                            new Przypisz("silnia", new Stala(1)),
                            new Sekwencja(
                            new NowaZm("n"),
                            new Sekwencja(
                            new Czytaj("n"),
                            new Sekwencja(
                            new Jezeli(
                            new Wieksze(new Zmienna("n"), new Stala(1)),
                            new Blok(
                                new Sekwencja(
                                new NowaZm("i"),
                                new Sekwencja(
                                new Przypisz("i", new Stala(2)),
                                new Sekwencja(
                                new Dopoki(
                                new MniejszeRowne(new Zmienna("i"), new Zmienna("n")),
                                new Blok(
                                    new Sekwencja(
                                    new Przypisz("silnia", new Mnoz(new Zmienna("silnia"), new Zmienna("i"))),
                                    new Sekwencja(
                                    new Przypisz("i", new Dodaj(new Zmienna("i"), new Stala(1))
                                    ))))
                                )))))
                            ),
                            new Sekwencja(
                            new Wypisz("silnia")
                            )))))
                        );
        System.out.println(silnia.toString());
        silnia.wykonaj();
        Program zaslanianie = new Sekwencja(
                                new NowaZm("x"),
                                new Sekwencja(
                                new NowaZm("jas"),
                                new Sekwencja(
                                new Wypisz("jas"),
                                new Sekwencja(
                                new Blok(
                                    new Sekwencja(
                                    new NowaZm("jas"),
                                    new Sekwencja(
                                    new Przypisz("jas", new Stala(5)),
                                    new Sekwencja(
                                    new Wypisz("jas"),
                                    new Sekwencja(
                                    new Przypisz("x", new Mnoz(new Stala(2), new Zmienna("jas"))),
                                    new Sekwencja(
                                    new Wypisz("x")
                                    )))))
                                ),
                                new Sekwencja(
                                new Wypisz("jas"),
                                new Sekwencja(
                                new Wypisz("x")
                                ))))));
        System.out.println(zaslanianie.toString());
        zaslanianie.wykonaj();
                                        
        /*Program simple = new Sekwencja(
                            new NowaZm("x"),
                            new Sekwencja(
                                new Przypisz("x", new Stala(5)),
                                new Sekwencja(
                                    new Przypisz("x", new Dodaj(new Zmienna("x"), new Stala(2))),
                                    new Sekwencja(
                                        new Wypisz("x")
                                    )
                                )
                            )
                        );
        System.out.println(simple.toString());
        simple.wykonaj();/*
        /*Program a = new Sekwencja(
                        new NowaZm("x"),
                        new Sekwencja(
                            new Wypisz("x")
                        )
                    );
        System.out.println(a.toString());
        a.wykonaj();*/
                                                                    
        
        /*Program.srodowisko.wstaw("x", -3);
        
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
            );*/
    }
}
