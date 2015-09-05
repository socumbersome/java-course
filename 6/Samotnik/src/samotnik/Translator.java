/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package samotnik;

/**
 *
 * @author ja
 */
public class Translator {
    public enum TypPlanszy {
        BRYTYJSKA,
        EUROPEJSKA
    }
    
    public enum StanGry {
        WTOKU,
        PRZEGRANA,
        WYGRANA
    }
    
    public enum TypPola {
        NIEDOZWOLONE,
        ZAJĘTE,
        WOLNE
    }
    
    public static TypPola mapujNaTypPola(int a) {
        if(a == 0)
            return TypPola.NIEDOZWOLONE;
        if(a == 1)
            return TypPola.ZAJĘTE;
        if(a == 2)
            return TypPola.WOLNE;
        throw new IllegalArgumentException("Nie ma takiego typu pola");
    }
    
    public static TypPola odwróćStan(TypPola tp) {
        if(tp == TypPola.WOLNE)
            return TypPola.ZAJĘTE;
        if(tp == TypPola.ZAJĘTE)
            return TypPola.WOLNE;
        throw new IllegalArgumentException("Niedozwolone pole nie ma odwrotnego stanu");
    }
}
