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
public abstract class Funkcja extends Symbol implements Funkcyjny {
    protected final double epsilon = 10e-14;
    Operand arg1 = null;
    Operand arg2 = null;
    
}
