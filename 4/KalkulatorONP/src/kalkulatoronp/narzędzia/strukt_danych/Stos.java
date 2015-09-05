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
public class Stos {
    public class Węzeł {
        protected double wartość;
        protected Węzeł następnik;
        protected Węzeł poprzednik;
        
        private Węzeł(double wart, Węzeł poprz, Węzeł nast) {
            wartość = wart;
            następnik = nast;
            poprzednik = poprz;
        }
        public Węzeł(double wart) {
            wartość = wart;
            następnik = null;
            poprzednik = null;
        }
        
        public Węzeł wstaw(double wart) {
            następnik = new Węzeł(wart, this, null);
            return następnik;
        }
        
        public double pokaz() {
            if(this == null)
                throw new java.util.EmptyStackException();
            return wartość;
        }
        
        public Węzeł zdejmij() {
            if(this == null)
                throw new java.util.EmptyStackException();
            Węzeł temp = this.poprzednik;
            if(temp != null)
                temp.następnik = null;
            return temp;
        }
    }
    
    protected Węzeł stos;
    
    public void wstaw(double wart) {
        if(stos == null)
            stos = this.new Węzeł(wart);
        else
            stos = stos.wstaw(wart);
    }
    
    public double pokaz() {
        if(stos == null)
            throw new java.util.EmptyStackException();
        return stos.pokaz();
    }
    
    public void zdejmij() {
        if(stos == null)
            throw new java.util.EmptyStackException();
        stos = stos.zdejmij();
    }
    
    public boolean pusty() {
        return stos == null;
    }
    
}
