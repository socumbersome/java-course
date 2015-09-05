/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

/**
 * Prosty wrapper Stringów: "prosto", "lewo", "prawo".
 * @author Patryk Kajdas
 */
public class Cel {
    private String cel;
    
    public Cel(String s) {
        setCel(s);
    }
    
    public void setCel(Cel c) {
        cel = c.getCel();
    }
    
    public void setCel(String s) {
        switch(s) {
            case "prosto":
            case "lewo":
            case "prawo": cel = s; break;
            default: throw new IllegalArgumentException("Nieznana nazwa celu " + s);
        }
    }
    
    public String getCel() {
        return cel;
    }
    
    @Override
    public String toString() {
        return cel;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this.getClass() != obj.getClass())
            return false;
        Cel drugi = (Cel)obj;
        return cel.equals(drugi.toString());
    }
    
    @Override
    public int hashCode() {
        return cel.hashCode();
    }
}
