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
public interface Funkcyjny extends Obliczalny{
    int arność();
    int brakująceArgumenty();
    void dodajArgument(double arg) throws WyjątekONP;
}
