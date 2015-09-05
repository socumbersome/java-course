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
public class ONP_ZbytWieleArg extends ONP_BłędneWyrażenie {
    public ONP_ZbytWieleArg(String message) {
        super("Zbyt wiele argumentów zaaplikowanych do operatora/funkcji: " + message);
    }
}
