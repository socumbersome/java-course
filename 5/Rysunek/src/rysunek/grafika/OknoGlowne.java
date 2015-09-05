/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rysunek.grafika;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Klasa reprezentująca główne okno aplikacji Rysunek.
 * 
 * @author Patryk Kajdas
 */
public class OknoGlowne extends Frame {
    private final WindowListener oknoGlowneListen = new WindowAdapter()
    {
        public void windowClosing(WindowEvent ev) {
            OknoGlowne.this.dispose();
        }
    };
    
    private final Narzedzia narzedzia;
    private final MyCanvas canvas;
    
    public OknoGlowne() {
        super("Rysunek");
        setSize(400, 300);
        setLocationRelativeTo(null);
        narzedzia = new Narzedzia();
        canvas = new MyCanvas(narzedzia);
        add(narzedzia, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
        addWindowListener(oknoGlowneListen);
        setVisible(true);
    }
}
