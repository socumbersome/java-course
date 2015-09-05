/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rysunek.grafika;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * Klasa reprezentująca płótno (Canvas), na którym można rysować.
 * 
 * Użytkownik rysuje odcinki przy pomocy myszy. Polega to na
 * naciśnięciu przycisku myszy na powierzchni kreślarskiej
 * (wyznaczenie punktu początkowego), przeciągnięciu kursora
 * myszy i puszczeniu przycisku (wyznaczenie punktu końcowego).
 * 
 * Jeśli puszczenie przycisku nastąpi, gdy kursor znajduje się
 * poza powierzchnią kreślarską, odcinek nie zostanie namalowany.
 * 
 * @author Patryk Kajdas
 */
public class MyCanvas extends Canvas {
    private KeyListener keyListen = new KeyAdapter()
    {
        @Override
        public void keyPressed (KeyEvent ev)
        {
            switch (ev.getKeyCode())
            {
            case KeyEvent.VK_F:
                try {
                    kreski.remove(0);
                }
                catch(IndexOutOfBoundsException e) {
                    System.err.println("Brak kresek do usuniecia");
                }
                MyCanvas.this.update(getGraphics());
                break;
            case KeyEvent.VK_L:
                try {
                    kreski.remove(kreski.size() - 1);
                }
                catch(IndexOutOfBoundsException e) {
                    System.err.println("Brak kresek do usuniecia");
                }
                MyCanvas.this.update(getGraphics());
                break;
            case KeyEvent.VK_BACK_SPACE:
                kreski.clear();
                MyCanvas.this.update(getGraphics());
                break;
            default: // inne klawisze
                break;
            }
        }
    };
    
    private MouseListener mouseListen = new MouseAdapter()
    {
        public void mousePressed(MouseEvent ev) {
            if(ev.getButton() != MouseEvent.BUTTON1)
                return;
            pocz = ev.getPoint();
            pocz_nowej_kreski = getGraphics();
        }
        
        public void mouseReleased(MouseEvent ev) {
            if(ev.getButton() != MouseEvent.BUTTON1)
                return;
            kon = ev.getPoint();
            if(MyCanvas.this.contains(kon)) {
                kreski.add(new Kreska(pocz, kon, narzedzia.aktualny_kolor()));
                MyCanvas.this.rysuj_ost_kreske();
            }
            else
                MyCanvas.this.repaint();
                //MyCanvas.this.update(pocz_nowej_kreski);
        }
    };
    
    private MouseMotionListener mouseMotionListen = new MouseMotionAdapter()
    {
        public void mouseDragged(MouseEvent ev) {
            if((ev.getModifiers() & InputEvent.BUTTON1_MASK) != InputEvent.BUTTON1_MASK){
                return;
            }
            MyCanvas.this.update(pocz_nowej_kreski);
            Point temp_kon = ev.getPoint();
            Kreska temp_k = new Kreska(pocz, temp_kon, Color.BLACK);
            MyCanvas.this.rysuj_kreske(temp_k);
        }
    };
    
    private void rysuj_kreske(Kreska k) {
        Graphics gr = getGraphics();
        gr.setColor(k.kolor);
        gr.drawLine(k.get_poczatek().x, k.get_poczatek().y, 
                k.get_koniec().x, k.get_koniec().y);
    }
    
    private void rysuj_kreske(int i) {
        Graphics gr = getGraphics();
        try {
            Kreska ktora = kreski.get(i);
            gr.setColor(ktora.kolor);
            gr.drawLine(ktora.get_poczatek().x, ktora.get_poczatek().y, 
                ktora.get_koniec().x, ktora.get_koniec().y);
        }
        catch(IndexOutOfBoundsException e) {
            System.err.println("Rysowanie kreski " + Integer.toString(i) +
                    " sposrod " + Integer.toString(kreski.size() - 1));
        }
    }
    
    private void rysuj_ost_kreske() {
        rysuj_kreske(kreski.size() - 1);
    }
    
    private Narzedzia narzedzia;
    private Point pocz, kon;
    private Graphics pocz_nowej_kreski;
    private ArrayList<Kreska> kreski = new ArrayList<>();
    
    /**
     * Konstruktor obszaru kreślarskiego.
     * 
     * Potrebuje obiektu typu Narzedzia, aby móc z niego
     * pobierać informacje o aktualnie wybranym kolorze.
     * @param narz typu Narzedzia 
     */
    public MyCanvas(Narzedzia narz) {
        super();
        narzedzia = narz;
        addMouseListener(mouseListen);
        addMouseMotionListener(mouseMotionListen);
        addKeyListener(keyListen);
        setFocusable(true);
        requestFocus();
    }
    
    @Override
    public void paint(Graphics g) {
        for(int i = 0; i < kreski.size(); i++) {
            rysuj_kreske(i);
        }
    }
    
    //@Override
    /*public void update(Graphics g) {
        super.update(g);
        paint(g);
    }*/
}
