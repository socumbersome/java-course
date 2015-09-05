/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package czyścicielbiałych.narzędzia;

import java.io.IOException;
import java.io.Reader;

/**
 * Wersja stara - działająca, ale sam kod jest brzydszy niż w nowszej!
 * @author ja
 */
public class CzyszczącyReader2 extends Reader {
    
    private enum Stan {
        POCZĄTEK, ŚRODEK;
    }
    
    private Stan stan;
    private char ostatniChar;
    private int liczbaSpacji;
    private int liczbaTabulacji;
    private int spacjiDoWypisania;
    private boolean niedokończonePoprz;
    
    private Reader in;
    private int tab2space = 8;
    
    public CzyszczącyReader2(Reader in) {
        this.in = in;
        stan = Stan.POCZĄTEK;
        niedokończonePoprz = false;
        spacjiDoWypisania = 0;
        nowaLinia();
    }
    
    public CzyszczącyReader2(Reader in, int tab2space) {
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
    
    private int dokończ(char[] buf, int od, int len) {
        // możliwe wtw. gdy byliśmy w stanie POCZĄTEK i nie zdążyliśmy
        // wypisać wszystkich spacji wraz z ostatnim niebiałym znakiem
        int przeczytane = 0;
        int max_do_przeczytania = maxDoPrzeczytania(od, len);
        niedokończonePoprz = false; // optymizm
        
        while(spacjiDoWypisania > 0) {
            if(przeczytane >= max_do_przeczytania) {
                niedokończonePoprz = true;
                return przeczytane;
            }
            else {
                buf[indeksZapisu(od, przeczytane)] = ' ';
                przeczytane++;
                spacjiDoWypisania--;
            }
        }
        if(przeczytane >= max_do_przeczytania) {
            niedokończonePoprz = true;
            return przeczytane;
        }
        else {
            buf[indeksZapisu(od, przeczytane)] = ostatniChar;
            przeczytane++;
            stan = Stan.ŚRODEK;
            nowaPorcja();
        }
        return przeczytane;
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
    
    @Override
    public int read(char[] buf, int od, int len) throws IOException {
        
        int przeczytane = 0; // to zwrócimy na końcu
        
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
                    case '\r': buf[indeksZapisu(od, przeczytane)] = ostatniChar;
                               przeczytane++; nowaLinia(); break;
                    default:
                        if(stan == Stan.POCZĄTEK) {
                            spacjiDoWypisania = liczbaSpacji + tab2space * liczbaTabulacji;
                            while(spacjiDoWypisania > 0) {
                                if(przeczytane >= max_do_przeczytania) {
                                    niedokończonePoprz = true;
                                    return przeczytane;
                                }
                                else {
                                    buf[indeksZapisu(od, przeczytane)] = ' ';
                                    przeczytane++;
                                    spacjiDoWypisania--;
                                }    
                            }
                            if(przeczytane >= max_do_przeczytania) {
                                    niedokończonePoprz = true;
                                    return przeczytane;
                            }
                            else {
                                buf[indeksZapisu(od, przeczytane)] = ostatniChar;
                                przeczytane++;
                                stan = Stan.ŚRODEK;
                                nowaPorcja();
                            } 
                        }
                        else { // stan = Stan.ŚRODEK
                            if(liczbaSpacji + liczbaTabulacji == 0) {
                                buf[indeksZapisu(od, przeczytane)] = ostatniChar;
                                przeczytane++;
                                nowaPorcja();
                            }
                            else { // w środku zawsze wypisujemy dokł. jedną spację
                                buf[indeksZapisu(od, przeczytane)] = ' ';
                                przeczytane++;
                                if(przeczytane >= max_do_przeczytania) {
                                    niedokończonePoprz = true;
                                    return przeczytane;
                                }
                                else {
                                    buf[indeksZapisu(od, przeczytane)] = ostatniChar;
                                    przeczytane++;
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
