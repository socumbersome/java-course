/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kalkulatoronp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EmptyStackException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kalkulatoronp.narzędzia.*;
import kalkulatoronp.narzędzia.strukt_danych.Lista;
import kalkulatoronp.narzędzia.strukt_danych.Para;

/**
 *
 * @author ja
 */
public class KalkulatorONP {

    /**
     * @param args the command line arguments
     * @throws kalkulatoronp.narzędzia.WyjątekONP
     */
    public static void main(String[] args) throws WyjątekONP {
        Lista zmienne = new Lista();
        Wyrażenie w;
        //Pattern deklaracja_zm = Pattern.compile("([\\w&&\\D]\\w*)\\s?=");
        Pattern deklaracja_zm = Pattern.compile("(\\p{Alpha}\\p{Alnum}*)\\s?=");
        //Pattern nazwa_zm = Pattern.compile("([\\w&&\\D]\\w*)");
        Pattern nazwa_zm = Pattern.compile("(\\p{Alpha}\\p{Alnum}*)");
        Matcher matcher;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        String line;
        try {
            while((line = br.readLine()) != null) {
                try {
                    line = line.trim();
                    if(line.length() < 4) {
                        throw new ONP_BłędneWyrażenie(line);
                    }
                    if(line.equals("exit"))
                        break;
                    if(line.substring(0, 4).equals("calc")) {
                        line = line.substring(4);
                        if(line.length() == 0)
                            throw new ONP_BłędneWyrażenie("Brak argumentów");
                        matcher = deklaracja_zm.matcher(line);
                        Double wynik = null;
                        if(matcher.find()) {
                            int pierwsze_wyst = matcher.start();
                            String wyr = line.substring(0, pierwsze_wyst);
                            wyr = wyr.trim();

                            w = new Wyrażenie(wyr, zmienne);
                            wynik = w.Oblicz();
                            System.out.println(wynik);

                            matcher.reset();
                            while(matcher.find()) {
                                String zm = matcher.group();
                                zm = zm.substring(0, zm.length() - 1);
                                zm = zm.trim();
                                assert wynik != null;
                                zmienne.wstaw(new Para(zm, wynik));
                            }
                        }
                        else {
                            line = line.trim();
                            w = new Wyrażenie(line, zmienne);
                            System.out.println(w.Oblicz());
                        }
                    }
                    else if(line.length() < 5)
                        throw new ONP_NieznanySymbol(line);
                    else if(line.substring(0, 5).equals("clear")) {
                        line = line.substring(5);
                        line = line.trim();
                        if(line.length() == 0)
                            zmienne.czyść();
                        else {
                            matcher = nazwa_zm.matcher(line);
                            while(matcher.find()) {
                                try {
                                    zmienne.usuń(matcher.group());
                                }
                                catch (IllegalArgumentException|EmptyStackException ex) {
                                    throw new ONP_NieznanySymbol("zmienna: " + matcher.group());
                                }
                            }
                        }
                    }
                    else
                        throw new ONP_BłędneWyrażenie(line);
                }
                catch(WyjątekONP ex) {
                    System.err.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(KalkulatorONP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
