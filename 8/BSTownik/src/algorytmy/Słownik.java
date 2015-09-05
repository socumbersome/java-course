/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorytmy;

/**
 *
 * @author ja
 */
public interface Słownik<T> {
    boolean szukaj(T element);
    void wstaw(T element);
    void usuń(T element);
    
    int ile();
    void czyść();
    boolean pusty();
}
