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
public class ONP_NiepoprawnyArg extends WyjątekONP {
    public ONP_NiepoprawnyArg(String message) {
        super("Niepoprawny argument funkcji: " + message);
    }
}
