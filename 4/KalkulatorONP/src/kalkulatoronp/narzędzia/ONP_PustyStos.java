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
public class ONP_PustyStos extends WyjątekONP {
    public ONP_PustyStos(String message) {
        super("Próba zdjęcia elementu z pustego stosu");
    }
    public ONP_PustyStos() {
        this("");
    }
}
