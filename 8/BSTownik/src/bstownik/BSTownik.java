/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bstownik;

import algorytmy.DrzewoBST;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author ja
 */
public class BSTownik {
    
    public static String parsujDane(DrzewoBST<Integer> itree, DrzewoBST<String> stree) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        String line;
        line = br.readLine();
        String które, komenda, argument;
        line = line.trim();
        if(line.equals("stop") || line.equals("exit"))
            return line;
        które = line.substring(0, 1);
        line = line.substring(1).trim();
        komenda = line.substring(0, 1);
        line = line.substring(1).trim();
        argument = line;
        int liczi = 0; int liczs = 0;
        switch(komenda) {
            case "s":
                if(które.equals("i"))
                    System.out.println(itree.szukaj(Integer.valueOf(argument)));
                else
                    System.out.println(stree.szukaj(argument));
                break;
            case "w":
                if(które.equals("i"))
                    itree.wstaw(Integer.valueOf(argument));
                else
                    stree.wstaw(argument);
                break;
            case "u":
                if(które.equals("i"))
                    itree.usuń(Integer.valueOf(argument));
                else
                    stree.usuń(argument);
                break;
            case "i":
                if(które.equals("i"))
                    System.out.println(itree.ile());
                else
                    System.out.println(stree.ile());
                break;
            case "p":
                if(które.equals("i"))
                    System.out.println(itree);
                else
                    System.out.println(stree);
                break;
            case "c":
                if(które.equals("i"))
                    itree.czyść();
                else
                    stree.czyść();
                break;
            case "t":
                if(które.equals("i")) {
                    for(Integer i : itree) {
                        try {
                            System.out.println(String.valueOf(liczi++) + " krok iteracji Integerowego drzewa");
                            String odp = parsujDane(itree, stree);
                            if(odp.equals("exit"))
                                return odp;
                            else if(odp.equals("skip"))
                                continue;
                            else if(odp.equals("stop"))
                                break;
                            System.out.println(i);
                        }
                        catch (IllegalStateException ex) {
                            System.err.println(ex.getMessage());
                            break;
                        }
                    }
                }
                else
                    for(String s : stree) {
                        try {
                            System.out.println(String.valueOf(liczs++) + " krok iteracji Stringowego drzewa");
                            String odp = parsujDane(itree, stree);
                            if(odp.equals("exit"))
                                return odp;
                            else if(odp.equals("skip"))
                                continue;
                            else if(odp.equals("stop"))
                                break;
                            System.out.println(s);
                        }
                        catch (IllegalStateException ex) {
                            System.err.println(ex.getMessage());
                            break;
                        }
                    }
                break;
        }
        return "";
    }
    
    public static void trybInteraktywny(DrzewoBST<Integer> itree, DrzewoBST<String> stree) throws IOException {
        
        /* składnia: (i|s|stop|exit|skip) komenda [argument]
        stop - wychodzi z aktualnej pętli
        (zauważmy, że pętlę mogą się zagnieżdżać w przypadku wielokrotnego
        wydawania komendy "iteruj" - wtedy tyleż "stopów" należy wpisać, by
        zakończyć program. Alternatywą jest "exit" - kończy program natychmiast)
        skip - instrukcja pusta (przydatna, gdy nic nie chcemy robić z drzewem
               podczas iterowania po nim
        i - dla Integer, s - dla String
        komendy: s x - szukaj x
                 w x - wstaw x
                 u x - usuń x
                 i   - podaj ile elementów jest w środku
                 p - wypisuje aktualne elementy
                 c - czyść
                 t - iteruj (wtedy iteruje po drzewie, wypisuje bieżący element,
                            a następnie pozwala wydać komendę przed kolejną iteracją)
        */
        while(true) {
            try {
                if(parsujDane(itree, stree).equals("exit"))
                    return;
            }
            catch (Exception ex) {
                System.err.println("Błąd: " + ex.getMessage());
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        DrzewoBST<Integer> itree = new DrzewoBST<>();
        DrzewoBST<String> stree = new DrzewoBST<>();
        
        trybInteraktywny(itree, stree);
    }
    
}