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
public class ONP_NieznanySymbol extends WyjątekONP {
    public ONP_NieznanySymbol(String message) {
        super("Nieznany symbol: " + message);
    }
}
