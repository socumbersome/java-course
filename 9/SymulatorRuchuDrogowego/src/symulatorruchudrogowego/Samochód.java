/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

/**
 * Instancja Samochodu jest również instancją wątku.
 * @author Patryk Kajdas
 */
public class Samochód extends Thread {
    private final int OFFSET = 15; // połowa szerokości samochodu
    private final int TOLER = 30; /* gdy samochód znajdzie się TOLER pikselów
                                     za ekranem, to go usuniemy */
    
    private double prędkość;
    private double prędkośćPoczątkowa;
    private Cel cel;
    private Kierunek kierunek;
    private final Color kolor;
    
    private SkrzyżowanieModel skrzyżowanie;
    
    private Punkt połKar; 
    private Rectangle karoseria;
    private BufferedImage migaczWł;
    private BufferedImage migaczWył;
    private BufferedImage migaczAktualnie;
    private AffineTransform caloscTr;
    private AffineTransform migaczTr;
    private AffineTransform saveAT;
    private AffineTransform układOdniesienia;
    
    public boolean chybaMożeszSkręcić = false;
    public boolean skręcający = false;
    private boolean jużPopatrzyłWLewoWPrawo = false;
    public Samochód niebezpiecznieBliski = null;
    
    private ScheduledExecutorService scheduler;
    private ScheduledFuture schedulerFuture;
    
    /**
     * Uwaga - układOdniesienia jest typu AffineTransform.
     * @param v
     * @param c
     * @param k
     * @param sm
     * @param układOdniesienia 
     * @throws IOException 
     */
    public Samochód(double v, Cel c, Kierunek k, SkrzyżowanieModel sm, AffineTransform układOdniesienia) throws IOException {
        super();
        skrzyżowanie = sm;
        this.układOdniesienia = układOdniesienia;
        prędkośćPoczątkowa = v;
        setPrędkość(v);
        setCel(c);
        setKierunek(k);
        Punkt p = new Punkt(sm.srodekNS + sm.szerokośćPasa / 2, 2 * sm.srodekWE);
        połKar = new Punkt(p.getX() - OFFSET, p.getY() - OFFSET);
        migaczWł = ImageIO.read(getClass().getResource("migacz.png"));
        migaczWył = ImageIO.read(getClass().getResource("migaczBlank.png"));
        migaczTr = new AffineTransform();
        caloscTr = new AffineTransform();
        
        Random random = new Random();
        kolor = new Color(random.nextInt(220), random.nextInt(220), random.nextInt(220));
        
        karoseria = new Rectangle(połKar.getX(), połKar.getY(), 30, 40);
        ustawOrientację();  
        
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }
    
    public void setPrędkość(double v) {
        if(v < 0 || v > 100)
            throw new IllegalArgumentException("Prędkość powinna być z przedziału [0, 100]");
        prędkość = v;
    }
    public double getPrędkość() { return prędkość; }
    public void setCel(Cel c) { cel = c; }
    public Cel getCel() { return cel; }
    public Kierunek getKierunek() { return kierunek; }
    public void setKierunek(Kierunek k) { kierunek = k; }
    
    /**
     * Ustawia wewnętrzne macierze transformacji zgodnie z jezdnią na której
     * samochód ma się znaleźć oraz celem jazdy.
     */
    private void ustawOrientację() {
        switch(cel.getCel()) {
            case "prosto":
                migaczTr.translate(połKar.getX(), połKar.getY() - migaczWł.getHeight() );
                break;
            case "lewo":
                migaczTr.translate(połKar.getX() - migaczWł.getHeight(), połKar.getY() + migaczWł.getWidth());
                migaczTr.rotate(Math.toRadians(-90));
                break;
            case "prawo":
                migaczTr.translate(połKar.getX() + migaczWł.getWidth() + migaczWł.getHeight(), połKar.getY());
                migaczTr.rotate(Math.toRadians(90));
                break;
        }
        
        String[] auxk = new String[] {"S", "E", "N", "W"};
        for(int i = 0; i < auxk.length; i++) {
            if(kierunek.toString().equals(auxk[i])) {
                caloscTr.rotate(Math.toRadians(-i*90), skrzyżowanie.srodekNS, skrzyżowanie.srodekWE);
            }
        }
    }
    
    /**
     * Rysuje się na podanej powierzchni przesłanej jako argument.
     * @param g2d typu Graphics2D
     */
    protected void paint(Graphics2D g2d) {
        g2d.setColor(kolor);
        saveAT = g2d.getTransform();
        g2d.transform(caloscTr);
        g2d.fill(karoseria);
        g2d.drawImage(migaczAktualnie, migaczTr, null);
        g2d.setTransform(saveAT);
    }
    
    /**
     * Co 40ms aktualizuje położenie samochodu, tj. imituje jechanie.
     */
    @Override
    public void run() {
        schedulerFuture = scheduler.scheduleWithFixedDelay(jedź, 0, 40, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Imituje jechanie. Oprócz aktualizacji odpowiednich macierzy
     * transformacji, sprawdza również czy znajduje się w strefie skrętu
     * skrzyżowania i podejmuje odpowiednie akcje.
     */
    private Runnable jedź = new Runnable() {
        public void run() {
            if(niebezpiecznieBliski != null && getPrędkość() < 1 && niebezpiecznieBliski.getPrędkość() > 1)
                setPrędkość(prędkośćPoczątkowa);
            
            if(skrzyżowanie.strefaKoniecznościSygnalizowania.contains(połKar.getX(), połKar.getY()))
                migaczAktualnie = migaczWł;
            else
                migaczAktualnie = migaczWył;
            
            Punkt p = new Kierunek("N").zróbPunkt();
            p.pomnóż(prędkość/10);
            połKar.dodaj(p);
            AffineTransform aux = new AffineTransform();
            aux.setToTranslation(/*p.getX()*/0, p.getY());
            migaczTr.preConcatenate(aux);
            karoseria.setLocation(połKar.getX(), połKar.getY());
            
            double kąt = p.getY() / skrzyżowanie.strefaSkrętu.getHeight() * 90.0;
            switch(cel.getCel()) {
                case "lewo": break;
                case "prawo": kąt = -kąt; break;
                default: kąt = 0;
            }
            if( skrzyżowanie.strefaSkrętu.contains(połKar.getX(), połKar.getY()) ) {
                if(!jużPopatrzyłWLewoWPrawo) {
                    setPrędkość(0.0);
                    skrzyżowanie.chcePrzejechać(Samochód.this);
                    skręcający = true;
                    jużPopatrzyłWLewoWPrawo = true;
                    setPrędkość(prędkośćPoczątkowa);
                }
                skręcaj(kąt);
            }
            
            usuńJeśliPoza();
        }
    };
    
    /**
     * Aktualizuje macierz transformacji tak, by odzwierciedlała skręcenie
     * o kąt podany w parametrze.
     * @param kąt 
     */
    private void skręcaj(double kąt) {
        AffineTransform aux = new AffineTransform();
        aux.rotate(Math.toRadians(kąt), skrzyżowanie.srodekNS, skrzyżowanie.srodekWE);
        caloscTr.preConcatenate(aux);
    }
    
    /**
     * Niszczy aktualną instancję, gdy stwierdzi, że jest odpowiednio
     * daleko od centrum skrzyżowania.
     */
    private void usuńJeśliPoza() {
        if(połKar.getX() < -TOLER || połKar.getY() < -TOLER ||
            połKar.getX() > 2*skrzyżowanie.srodekNS + TOLER ||
            połKar.getY() > 2*skrzyżowanie.srodekWE + TOLER) {
            
            skrzyżowanie.usuńSamochód(this);
            scheduler.shutdown();
        }
    }
    
    /**
     * Podaje aktualne współrzędne samochodu w układzie współrzędnych
     * układu odniesienia.
     * @return Punkt 
     */
    public Punkt współrzędne() {
        AffineTransform at = (AffineTransform)układOdniesienia.clone();
        at.concatenate(caloscTr);
        Point2D pp = at.transform(new Point2D.Double(połKar.getX(), połKar.getY()), null);
        Punkt p = new Punkt((int)pp.getX(), (int)pp.getY());
        return p;
    }
    
    /**
     * Zakłada, że każdy samochód jest unikalny.
     * @param obj
     * @return 
     */
    public boolean equals(Object obj) {
        return (this == obj);
    }
    
}
