/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kalkulatoronp.narzędzia;

import static java.lang.Math.cos;

/**
 *
 * @author ja
 */
public class Cos extends Funkcja {
    @Override
    public int arność() {
        return 1;
    }

    @Override
    public int brakująceArgumenty() {
        if(arg1 == null)
            return 1;
        return 0;
    }

    @Override
    public void dodajArgument(double arg) throws WyjątekONP {
        if(arg1 == null)
            arg1 = new Liczba(arg);
        else
            throw new ONP_ZbytWieleArg(this.toString());
    }

    @Override
    public double obliczWartość() throws WyjątekONP {
        if(brakująceArgumenty() == 0) {
            return cos(arg1.obliczWartość());
        }
        throw new ONP_ZbytMałoArg(this.toString());
    }
    
    public String toString() {
        return "Cos";
    }
}
