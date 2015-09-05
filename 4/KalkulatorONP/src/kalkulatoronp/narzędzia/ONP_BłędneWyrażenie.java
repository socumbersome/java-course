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
public class ONP_BłędneWyrażenie extends WyjątekONP {
    public ONP_BłędneWyrażenie(String message) {
        super("Błędne wyrażenie: " + message);
    }
}
