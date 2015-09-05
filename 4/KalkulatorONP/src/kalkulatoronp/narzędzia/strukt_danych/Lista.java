/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kalkulatoronp.narzędzia.strukt_danych;

/**
 *
 * @author ja
 */

public class Lista {
    public class Węzeł {
        protected Para p;
        public Węzeł następnik;
        public Węzeł poprzednik;
        
        private Węzeł(Para p, Węzeł poprz, Węzeł nast) {
            this.p = p;
            następnik = nast;
            poprzednik = poprz;
        }
        public Węzeł(Para p) {
            this.p = p;
            następnik = null;
            poprzednik = null;
        }
        
        public Para wartość() {
            return p;
        }
        
        public Węzeł wstaw(Para s) {
            następnik = new Węzeł(s, this, null);
            return następnik;
        }
        
        public Węzeł szukaj(String kl) {
            if(this.p.klucz.equals(kl))
                return this;
            if(this.następnik == null)
                throw new IllegalArgumentException();
            return this.następnik.szukaj(kl);
        }
        
        public void aktualizuj(Para p) {
            this.p.set(p.get());
        }
    }
    
    protected Węzeł przod, tyl;
    
    public void wstaw(Para p) {
        if(przod == null) {
            przod = tyl = this.new Węzeł(p, null, null);
            return;
        }
        try {
            Węzeł takisam = przod.szukaj(p.klucz);
            takisam.aktualizuj(p);
        }
        catch (IllegalArgumentException ex) {
            tyl = tyl.wstaw(p);
        }
    }
    
    public double pokaz(String kl) {
        if(przod == null) {
            throw new java.util.EmptyStackException();
        }
        Węzeł szukany = przod.szukaj(kl);
        return szukany.wartość().get();
    }
    
    public void czyść() {
        przod = tyl = null;
    }
    
    public void usuń(String kl) {
        
        if(przod == null)
            throw new java.util.EmptyStackException();
        if(przod == tyl)
            if(przod.wartość().klucz.equals(kl))
                czyść();
            else
                throw new IllegalArgumentException();
        else {
            Węzeł szukany = przod.szukaj(kl);
            if(szukany.poprzednik == null) {
                przod = szukany.następnik;
                przod.poprzednik = null;
            }
            else if(szukany.następnik == null) {
                tyl = szukany.poprzednik;
                tyl.następnik = null;
            }
            else {
                szukany.poprzednik.następnik = szukany.następnik;
                szukany.następnik.poprzednik = szukany.poprzednik;
            }
        }
    }
}