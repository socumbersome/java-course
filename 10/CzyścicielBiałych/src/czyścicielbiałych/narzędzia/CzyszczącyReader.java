/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package czyścicielbiałych.narzędzia;

import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author ja
 */
public class CzyszczącyReader extends Reader {
    
    private enum Stan {
        POCZĄTEK, ŚRODEK;
    }
    
    private Stan stan;
    private char ostatniChar;
    private int liczbaSpacji;
    private int liczbaTabulacji;
    private int spacjiDoWypisania;
    private boolean niedokończonePoprz;
    private int przeczytane;
    
    private Reader in;
    private int tab2space = 4;
    
    public CzyszczącyReader(Reader in) {
        this.in = in;
        stan = Stan.POCZĄTEK;
        niedokończonePoprz = false;
        spacjiDoWypisania = 0;
        nowaLinia();
    }
    
    public CzyszczącyReader(Reader in, int tab2space) {
        this(in);
        if(tab2space < 0)
            throw new IllegalArgumentException("Rozmiar tabulacji nie może być ujemny.");
        this.tab2space = tab2space;
    }
    
    private void nowaLinia() {
        nowaPorcja();
        stan = Stan.POCZĄTEK;
    }
    
    private void nowaPorcja() {
        liczbaSpacji = 0;
        liczbaTabulacji = 0;
        spacjiDoWypisania = 0;
    }
    
    private int indeksZapisu(int od, int przeczytane) {
        return od + przeczytane;
    }
    
    private int jeszczeDoZobaczeniaWewn(int ind_do_zobaczenia_now_wewn, int przeczytane_wewn) {
        return przeczytane_wewn - ind_do_zobaczenia_now_wewn;
    }
    
    private int maxDoPrzeczytania(int od, int len) {
        return od + len - 1;
    }
    
    private void zapiszKolejny(char[] buf, int indeks, char znak) {
        buf[indeks] = znak;
        przeczytane++;
    }
    
    private int dokończ(char[] buf, int od, int len) {
        // możliwe wtw. gdy byliśmy w stanie POCZĄTEK i nie zdążyliśmy
        // wypisać wszystkich spacji wraz z ostatnim niebiałym znakiem
        przeczytane = 0;
        niedokończonePoprz = false; // optymizm
        przepiszZaległe(buf, od, len);
        
        return przeczytane;
    }
    
    private void przepiszZaległe(char[] buf, int od, int len) {
        
        int max_do_przeczytania = maxDoPrzeczytania(od, len);
        
        while(spacjiDoWypisania > 0) {
            if(przeczytane >= max_do_przeczytania) {
                niedokończonePoprz = true;
                return;
            }
            else {
                zapiszKolejny(buf, indeksZapisu(od, przeczytane), ' ');
                spacjiDoWypisania--;
            }
        }
        if(przeczytane >= max_do_przeczytania) {
            niedokończonePoprz = true;
        }
        else {
            zapiszKolejny(buf, indeksZapisu(od, przeczytane), ostatniChar);
            stan = Stan.ŚRODEK;
            nowaPorcja();
        }
    }
    
    @Override
    public int read(char[] buf, int od, int len) throws IOException {
        
        przeczytane = 0; // to zwrócimy na końcu
        
        if(niedokończonePoprz) {
            przeczytane = dokończ(buf, od, len);
            if(niedokończonePoprz) // mogło się znów nie udać...
                return przeczytane;
        }
        
        int przeczytane_wewn; // pomocnicza zm. bo wewnętrznie trzymamy readera,
        // który czyta "normalnie", a my potem to filtrujemy
        int ind_do_zobaczenia_now_wewn = 0; // nr aktualnie rozpatrywanego znaku ze strumienia wewnętrznego
        int max_do_przeczytania = maxDoPrzeczytania(od, len);
        char[] buf_wewn = new char[max_do_przeczytania];
   
        while( (przeczytane_wewn = in.read(buf_wewn, 0, max_do_przeczytania)) > 0 ) {
            ind_do_zobaczenia_now_wewn = 0;
            
            while(jeszczeDoZobaczeniaWewn(ind_do_zobaczenia_now_wewn, przeczytane_wewn) > 0) {
                ostatniChar = buf_wewn[ind_do_zobaczenia_now_wewn];
                switch(ostatniChar) {
                    case ' ': liczbaSpacji++; break;
                    case '\t': liczbaTabulacji++; break;
                    case '\n':
                    case '\r': 
                               zapiszKolejny(buf, indeksZapisu(od, przeczytane), ostatniChar);
                               nowaLinia(); break;
                    default:
                        if(stan == Stan.POCZĄTEK) {
                            spacjiDoWypisania = liczbaSpacji + tab2space * liczbaTabulacji;
                            przepiszZaległe(buf, od, len);
                            if(niedokończonePoprz)
                                return przeczytane;
                        }
                        else { // stan = Stan.ŚRODEK
                            if(liczbaSpacji + liczbaTabulacji == 0) {
                                zapiszKolejny(buf, indeksZapisu(od, przeczytane), ostatniChar);
                                nowaPorcja();
                            }
                            else { // w środku zawsze wypisujemy dokł. jedną spację
                                zapiszKolejny(buf, indeksZapisu(od, przeczytane), ' ');
                                if(przeczytane >= max_do_przeczytania) {
                                    niedokończonePoprz = true;
                                    return przeczytane;
                                }
                                else {
                                    zapiszKolejny(buf, indeksZapisu(od, przeczytane), ostatniChar);
                                    nowaPorcja();
                                }
                            }
                        }
                }
                ind_do_zobaczenia_now_wewn++;
            }
        }
        
        return przeczytane;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
    
}
