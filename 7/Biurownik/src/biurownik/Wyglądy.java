/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biurownik;

import javax.swing.UIManager;

/**
 *
 * @author ja
 */
public class Wyglądy {
    private final static UIManager.LookAndFeelInfo[] LAFS;
    public final static String[] Nazwa;

    static {
        LAFS = UIManager.getInstalledLookAndFeels();
        Nazwa = new String[LAFS.length];
        for(int i = 0; i < LAFS.length; i++)
            Nazwa[i] = LAFS[i].getName();
    }
    
    public static String NazwaToLAF(String s) {
        for(int i = 0; i < Nazwa.length; i++) {
            if(Nazwa[i].equals(s))
                return LAFS[i].getClassName();
        }
        throw new IllegalArgumentException("Nieznany wygląd: " + s);
    }
}
