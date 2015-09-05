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
public class Liczba extends Operand{
    protected double wartość;
    
    public Liczba(double x) {
        wartość = x;
    }

    @Override
    public double obliczWartość() throws WyjątekONP {
        return wartość;
    }
    
    public String toString() {
        return String.valueOf(wartość);
    }
}
