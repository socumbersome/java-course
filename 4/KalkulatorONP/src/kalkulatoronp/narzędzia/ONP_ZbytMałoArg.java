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
public class ONP_ZbytMałoArg extends ONP_BłędneWyrażenie {
    public ONP_ZbytMałoArg(String message) {
        super("Zbyt mało argumentów zaaplikowanych do operatora/funkcji: " + message);
    }
}
