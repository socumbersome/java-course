/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Klasa oferująca metody (aktualnie jedną publiczną) pomocne w wyznaczaniu
 * podzbioru samochodów mających pierwszeństwo.
 * 
 * @author Patryk Kajdas
 * @see Samochód
 */
public class Przepisy {
    
    private final static Set< Para< Para<Kierunek> > > kursyKolizyjne;
    
    static {
        kursyKolizyjne = new HashSet<>();
        for(String sk : Kierunek.Kierunki) {
            Kierunek k = new Kierunek(sk);
            Kierunek prawy = k.poPrawej();
            Kierunek lewy = k.poLewej();
            Kierunek przeciwny = k.przeciwny();
            
            Para<Kierunek> prawo = new Para<>(k, prawy);
            kursyKolizyjne.add(new Para<>(prawo, new Para<>(lewy, prawy)));
            kursyKolizyjne.add(new Para<>(prawo, new Para<>(przeciwny, prawy)));
            
            Para<Kierunek> lewo = new Para<>(k, lewy);
            kursyKolizyjne.add(new Para<>(lewo, new Para<>(prawy, lewy)));
            kursyKolizyjne.add(new Para<>(lewo, new Para<>(przeciwny, lewy)));
            kursyKolizyjne.add(new Para<>(lewo, new Para<>(prawy, k)));
            
            Para<Kierunek> prosto = new Para<>(k, przeciwny);
            kursyKolizyjne.add(new Para<>(prosto, new Para<>(lewy, prawy)));
            kursyKolizyjne.add(new Para<>(prosto, new Para<>(lewy, przeciwny)));
            kursyKolizyjne.add(new Para<>(prosto, new Para<>(prawy, lewy)));
            kursyKolizyjne.add(new Para<>(prosto, new Para<>(prawy, przeciwny)));
            kursyKolizyjne.add(new Para<>(prosto, new Para<>(prawy, k)));
            kursyKolizyjne.add(new Para<>(prosto, new Para<>(przeciwny, prawy)));
        }
    }
    
    /**
     * Zwraca zbiór tych samochodów z podanej listy, które mogą jednocześnie
     * przejechać bezkolizyjnie przez skrzyżowanie.
     * @param przySkrzyżowaniu - lista samochodów
     * @return zbiór samochodów
     */
    public static Set<Samochód> wyznaczMogącePrzejechać(List<Samochód> przySkrzyżowaniu) {
        int size = przySkrzyżowaniu.size();
        if(size <= 0 || size > 4) {
            throw new IllegalArgumentException(String.valueOf(size) + " aut!");
        }
        
        Set<Samochód> kolidujące = new HashSet<>();
        for(Samochód s1 : przySkrzyżowaniu)
            for(Samochód s2 : przySkrzyżowaniu) {
                if(s1 == s2 || !naKursieKolizyjnym(s1, s2))
                    continue;
                kolidujące.add(s1);
                kolidujące.add(s2);
            }
        
        Set<Samochód> niekolidujące = new HashSet<>();
        for(Samochód s : przySkrzyżowaniu) {
            if(!kolidujące.contains(s))
                niekolidujące.add(s);
        }
        
        Set<Samochód> zKolizyjnychKtóreMogą = wybierzMogąceJechaćRegPrRęki(kolidujące);
        for(Samochód s : zKolizyjnychKtóreMogą)
            niekolidujące.add(s);
        
        if(niekolidujące.isEmpty()) {
            Set<Samochód> aux = new HashSet<>();
            aux.add(przySkrzyżowaniu.get(size - 1)); // ostatni ma pierwszeństwo w przypadku zakleszczenia
            return aux;
        }
        else
            return niekolidujące;
    }
    
    /**
     * Zwraca true wtw. gdy samochody przesłane jako argumenty wywołania
     * znajdują się na kursie kolizyjnym.
     * @return boolean
     */
    private static boolean naKursieKolizyjnym(Samochód s1, Samochód s2) {
        Para<Kierunek> k1 = new Para<>(s1.getKierunek(), s1.getKierunek().uwzględnijCel(s1.getCel()));
        Para<Kierunek> k2 = new Para<>(s2.getKierunek(), s2.getKierunek().uwzględnijCel(s2.getCel()));
        return kursyKolizyjne.contains(new Para<>(k1, k2));
    }
    
    /**
     * Zwraca zbiór samochodów, które mogą bezkolizyjnie przejechać przez
     * skrzyżowanie zgodnie z regułą prawej ręki.
     * @param sam - zbiór samochodów
     */
    private static Set<Samochód> wybierzMogąceJechaćRegPrRęki(Set<Samochód> sam) {
        Set<Samochód> wygrani = new HashSet<>();
        Cel cellewo = new Cel("lewo");
        for(Samochód s : sam) {
            boolean wolnaPrawaStrona = true;
            for(Samochód s1 : sam) {
                if(s == s1)
                    continue;
                
                if( (s.getKierunek().poPrawej().equals(s1.getKierunek()) &&
                        naKursieKolizyjnym(s, s1))
                        ||
                    (  s.getKierunek().przeciwny().equals(s1.getKierunek()) &&
                       s.getCel().equals(cellewo) 
                    ) 
                  ) {
                    wolnaPrawaStrona = false;
                    break;
                }
            }
            if(wolnaPrawaStrona)
                wygrani.add(s);
        }
        return wygrani;
    }
}
