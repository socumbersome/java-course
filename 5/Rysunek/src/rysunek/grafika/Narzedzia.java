/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rysunek.grafika;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Panel;
import java.util.HashMap;

/**
 * Klasa reprezentująca narzędzia dostępne do modyfikowania trybu kreślenia.
 * 
 * @author Patryk Kajdas
 */
public class Narzedzia extends Panel {
    
    private HashMap<String, Color> kolory;
    private List kolory_lista;
    
    /**
     * Zwraca aktualnie wybrany kolor.
     * @return kolor typu Color
     */
    public Color aktualny_kolor() {
        return kolory.get(kolory_lista.getSelectedItem());
    }
    
    public Narzedzia() {
        this.kolory = new HashMap<>();
        dodaj_kolory();
        kolory_lista =  new List(kolory.size(), false);
        setLayout(new GridLayout(1,1));
        for (String kolory_nazwa1 : kolory.keySet()) {
            kolory_lista.add(kolory_nazwa1);
        }
        add(kolory_lista);
        setVisible(true);
    }
    
    private void dodaj_kolory(){
        kolory.put("czarny", Color.black);
        kolory.put("czerwony", Color.red);
        kolory.put("zielony", Color.green);
        kolory.put("niebieski", Color.blue);
        kolory.put("pomaranczowy", Color.ORANGE);
        kolory.put("rozowy", Color.PINK);
    }
}
