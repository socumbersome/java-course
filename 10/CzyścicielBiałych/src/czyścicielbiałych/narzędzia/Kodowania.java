/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package czyścicielbiałych.narzędzia;

import java.nio.charset.Charset;
import java.util.Map;

/**
 *
 * @author ja
 */
public class Kodowania {
    public final static String[] Nazwy;
    
    static {
        Map<String, Charset> dostępne = Charset.availableCharsets();
        Nazwy = new String[dostępne.size()];
        int i = 0;
        for(String s : dostępne.keySet()) {
            Nazwy[i] = s;
            i++;
        }  
    }
    
    public static Charset str2charset(String s) {
        return Charset.availableCharsets().get(s);
    }
}
