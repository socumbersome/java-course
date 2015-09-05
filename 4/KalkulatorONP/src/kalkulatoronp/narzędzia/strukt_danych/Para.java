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
public class Para {
    public final String klucz;
    private double wartość;
    
    public Para(String kl, double wart)
    {
        klucz = kl;
        wartość = wart;
    }
    
    public boolean set(double wart)
    {
        if(wartość == wart)
            return false;
        wartość = wart;
        return true;
    }
    
    public double get()
    {
        return wartość;
    }
    
    public String toString()
    {
        return "(" + klucz + " : " + wartość + ")";
    }
    
    public boolean equals(Object obj)
    {
        if(this.getClass() != obj.getClass())
            return false;
        return (klucz.equals(((Para)obj).klucz) /*&& wartość == obj.get()*/);
    }
}
