/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorytmy;

import algorytmy.pomocnicze.Trójka;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author ja
 * @param <T>
 */
public class DrzewoBST<T extends Comparable<T>> extends Observable implements Słownik<T>, Iterable<T> {

    private class Węzeł <T extends Comparable<T>>
    {
        private Węzeł<T> lewy, prawy, ojciec;
        private T dane;
        private int krotność;
        
        public Węzeł(T dane) {
            init();
            this.dane = dane;
            krotność = 1;
        }
        
        private Węzeł(T dane, Węzeł<T> ojciec) {
            this(dane);
            this.ojciec = ojciec;
        }
        
        private void init() {
            lewy = prawy = ojciec = null;
            dane = null;
        }
        
        public T getDane() {
            return dane;
        }
        
        public int ile() {
            int wynik = krotność;
            if(lewy != null)
                wynik += lewy.ile();
            if(prawy != null)
                wynik += prawy.ile();
            return wynik;
        }
        
        public Węzeł<T> następnik() {
            if(prawy != null)
                return prawy.minimumZPoddrzewa();
            Węzeł<T> nizej = this;
            Węzeł<T> wyzej = ojciec;
            while(wyzej != null && nizej == wyzej.prawy) {
                nizej = wyzej;
                wyzej = wyzej.ojciec;
            }
            return wyzej;
        }
        
        public Węzeł<T> poprzednik() {
            if(lewy != null)
                return lewy.maksimumZPoddrzewa();
            Węzeł<T> nizej = this;
            Węzeł<T> wyzej = ojciec;
            while(wyzej != null && nizej == wyzej.lewy) {
                nizej = wyzej;
                wyzej = wyzej.ojciec;
            }
            return wyzej;
        }
        
        private Węzeł<T> minimumZPoddrzewa() {
            Węzeł<T> nast = this;
            while(nast.lewy != null)
                nast = nast.lewy;
            return nast;
        }
        
        private Węzeł<T> maksimumZPoddrzewa() {
            Węzeł<T> nast = this;
            while(nast.prawy != null)
                nast = nast.prawy;
            return nast;
        }
        
        private void podmieńDziecko(Węzeł<T> z, Węzeł<T> na) {
            if(z == lewy) {
                lewy = na;
                if(na != null)
                    na.ojciec = this;
            }
            else if(z == prawy) {
                prawy = na;
                if(na != null)
                    na.ojciec = this;
            }
        }
        
        private boolean szukaj(T element) {
            return znajdźWPoddrzewie(element).getX();
        }
        
        /* zwraca: Trójkę (czy znaleziono el. równy element;
        węzeł (lub null) jako miejsce, gdzie znalazłby się
        element, gdyby był w drzewie X; ojca (lub null) el. element, gdyby
        był w drzewie X, gdzie X to poddrzewo zaczepione w this) */
        
        /**
         * @param element
         * @return Trójka<czyIstnieje, szukany, rodzic>
         * Jeśli czyIstnieje==false, to szukany=null, ale semantyka rodzica
         * pozostaje w mocy, tj. rodzic to taki węzeł, który byłby rodzicem
         * szukanego elementu, gdyby on istniał (wbrew pozorom, przydatna własność)
         */
        private Trójka<Boolean, Węzeł, Węzeł> znajdźWPoddrzewie(T element) {
            Węzeł<T> spr = this;
            Węzeł<T> poprz = spr.ojciec;
            int elemaspr;
            while(spr != null) {
                elemaspr = element.compareTo(spr.dane);
                if(elemaspr == 0)
                    return new Trójka<Boolean, Węzeł, Węzeł>(true, spr, poprz);

                poprz = spr;
                if(elemaspr > 0)
                    spr = spr.prawy;
                else
                    spr = spr.lewy;
            }
            return new Trójka<Boolean, Węzeł, Węzeł>(false, null, poprz);
        }
        
        public void wstaw(T element) {
            Trójka<Boolean, Węzeł, Węzeł> poszukiwanie = znajdźWPoddrzewie(element);
            if(poszukiwanie.getX())
                poszukiwanie.getY().krotność++;
            else {
                Węzeł<T> rodzic = poszukiwanie.getZ();
                if(element.compareTo(rodzic.dane) > 0)
                    rodzic.prawy = new Węzeł<>(element, rodzic);
                else
                    rodzic.lewy = new Węzeł<>(element, rodzic);
            }   
        }
        
        public void usuń(T element) {
            Trójka<Boolean, Węzeł, Węzeł> poszukiwanie = znajdźWPoddrzewie(element);
            if(!poszukiwanie.getX())
                throw new IllegalArgumentException("Element " + element + " nie znajduje się w drzewie");

            Węzeł<T> dousunięcia = poszukiwanie.getY();
            if(dousunięcia.krotność > 1) {
                dousunięcia.krotność--;
                return;
            }
            
            Węzeł<T> rodzic = poszukiwanie.getZ();
            
            if(dousunięcia.lewy == null && dousunięcia.prawy == null) {
                if(rodzic == null) // poddrzewo jednoelementowe bez ojca =>
                    korzeń = null; // całe drzewo jest jednoelementowe
                else {
                    rodzic.podmieńDziecko(dousunięcia, null);
                }
            }
            else if(dousunięcia.lewy == null) {
                if(rodzic == null) {
                    dousunięcia.dane = dousunięcia.prawy.dane;
                    dousunięcia.lewy = dousunięcia.prawy.lewy;
                    dousunięcia.prawy = dousunięcia.prawy.prawy;
                    if(dousunięcia.lewy != null)
                       dousunięcia.lewy.ojciec = dousunięcia;
                    if(dousunięcia.prawy != null)
                    dousunięcia.prawy.ojciec = dousunięcia;
                }
                else {
                    rodzic.podmieńDziecko(dousunięcia, dousunięcia.prawy);
                }
            }
            else if(dousunięcia.prawy == null) {
                if(rodzic == null) {
                    dousunięcia.dane = dousunięcia.lewy.dane;
                    dousunięcia.lewy = dousunięcia.lewy.lewy;
                    dousunięcia.prawy = dousunięcia.lewy.prawy;
                    if(dousunięcia.lewy != null)
                        dousunięcia.lewy.ojciec = dousunięcia;
                    if(dousunięcia.prawy != null)
                    dousunięcia.prawy.ojciec = dousunięcia;
                }
                else {
                    rodzic.podmieńDziecko(dousunięcia, dousunięcia.lewy);
                }
            }
            else { // ma dwoje dzieci
                  // łatwo uzasadnić, że następnik wtedy istnieje!
                Węzeł<T> następnik = dousunięcia.następnik();
                dousunięcia.dane = następnik.dane;
                następnik.usuń(następnik.dane);
            }
        }
        
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < krotność; i++)
                sb.append(dane.toString() + " ");
            String s = new String(sb);
            s = s.trim();
            return s;
        }
        
        public String wypiszInOrder() {
            String s = "";
            s += (lewy == null ? "" : lewy.wypiszInOrder());
            s += this + " ";
            s += (prawy == null ? "" : prawy.wypiszInOrder());
            return s;
        }
    }
    
    private Węzeł<T> korzeń;
    
    public DrzewoBST() {
        super();
        korzeń = null;
    }
    
    public boolean szukaj(T element) {
        if(pusty())
            return false;
        return korzeń.szukaj(element);
    }

    public void wstaw(T element) {
        if(element == null)
            throw new NullPointerException("Próba wstawienia nulla do drzewa");
        if(pusty())
            korzeń = new Węzeł<>(element);
        else
            korzeń.wstaw(element);
        
        setChanged();
        notifyObservers("Element " + element + " został wstawiony do drzewa");
    }

    public void usuń(T element) {
        if(pusty())
            throw new IllegalStateException("Próba usunięcia z pustego drzewa");
        if(element == null)
            throw new IllegalArgumentException("Nie sposób usunąć nulla z drzewa");
        
        korzeń.usuń(element);
        setChanged();
        notifyObservers("Element " + element + " został usunięty z drzewa");
    }
    
    public boolean pusty() {
        return korzeń == null;
    }
    
    public int ile() {
        if(pusty())
            return 0;
        return korzeń.ile();
    }
    
    public void czyść() {
        korzeń = null;
        setChanged();
        notifyObservers("Drzewo zostało wyczyszczone");
    }
    
    public String toString() {
        if(pusty())
            return "Puste drzewo";
        return korzeń.wypiszInOrder();
    }
    
    public Iterator<T> iterator() {
        if(pusty())
            throw new IllegalStateException("Nie istnieje iterator pustego drzewa");
        Węzeł<T> minimum = korzeń.minimumZPoddrzewa();
        IteratorBST<T> it = new IteratorBST<>(minimum);
        addObserver(it);
        return it;
    }
    
    private class IteratorBST <T extends Comparable<T>> implements Iterator<T>, Observer {
    
        private Węzeł<T> aktualny;
        private boolean gotowy = true;
        public IteratorBST(Węzeł<T> węzeł) {
            aktualny = węzeł;
        }

        @Override
        public boolean hasNext() {
            if(!gotowy)
                throw new IllegalStateException("Zmiana stanu podczas iteracji");
            return aktualny != null;
        }

        @Override
        public T next() {
            if(!gotowy)
                throw new IllegalStateException("Zmiana stanu podczas iteracji");
            T wart = aktualny.getDane();
            aktualny = aktualny.następnik();
            return wart;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void update(Observable o, Object o1) {
            o.deleteObserver(this);
            gotowy = false;
            //aktualny = null;
    //throw new IllegalStateException(o1.toString() + " podczas iteracji");
        }

    }
    
}
