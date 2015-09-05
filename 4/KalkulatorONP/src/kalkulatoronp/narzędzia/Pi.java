/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kalkulatoronp.narzędzia;

/**
 *
 * @author ja
 */
public class Pi extends Funkcja {
    private final double pi = 3.141592653589;
    @Override
    public int arność() {
        return 0;
    }

    @Override
    public int brakująceArgumenty() {
        return 0;
    }

    @Override
    public void dodajArgument(double arg) throws WyjątekONP {
        throw new ONP_ZbytWieleArg(this.toString());
    }

    @Override
    public double obliczWartość() {
        return pi;
    }
    
    public String toString() {
        return "Pi";
    }
}
