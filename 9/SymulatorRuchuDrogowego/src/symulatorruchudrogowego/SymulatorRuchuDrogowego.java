/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

/**
 * Inicjalizuje symulację ruchu drogowego w technologii SWING.
 * @author Patryk Kajdas
 */
public class SymulatorRuchuDrogowego {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RuchDrogowyGUI().setVisible(true);
            }
        });
    }
    
}
