/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kalkulatoronp.narzędzia.strukt_danych;

import kalkulatoronp.narzędzia.Symbol;

/**
 *
 * @author ja
 */
public class Kolejka {
    public class Węzeł {
        protected Symbol symb;
        protected Węzeł następnik;
        protected Węzeł poprzednik;
        
        private Węzeł(Symbol s, Węzeł poprz, Węzeł nast) {
            symb = s;
            następnik = nast;
            poprzednik = poprz;
        }
        public Węzeł(Symbol s) {
            symb = s;
            następnik = null;
            poprzednik = null;
        }
        
        public Węzeł wstaw(Symbol s) {
            następnik = new Węzeł(s, this, null);
            return następnik;
        }
        
        public Symbol pokaz() {
            if(this == null)
                throw new java.util.EmptyStackException();
            return symb;
        }
        
        public Węzeł zdejmij() {
            if(this == null)
                throw new java.util.EmptyStackException();
            Węzeł temp = this.następnik;
            temp.poprzednik = null;
            return temp;
        }
    }
    
    protected Węzeł przod, tyl;
    
    public void wstaw(Symbol s) {
        if(przod == null)
            przod = tyl = this.new Węzeł(s, null, null);
        else
            tyl = tyl.wstaw(s);
    }
    
    public Symbol pokaz() {
        if(this.pusta())
            throw new java.util.EmptyStackException();
        return przod.pokaz();
    }
    
    public void zdejmij() {
        if(przod == null)
            throw new java.util.EmptyStackException();
        if(przod == tyl)
            przod = tyl = null;
        else
            przod = przod.zdejmij();
    }
    
    public boolean pusta() {
        return przod == null;
    }
}
