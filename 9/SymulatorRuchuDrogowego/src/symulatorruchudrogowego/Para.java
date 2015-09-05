/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symulatorruchudrogowego;

/**
 * Prosta implementacja generycznej pary.
 * @author Patryk Kajdas
 */
public class Para<T> {
    private final T x, y;
    
    public Para(T x, T y) {
        this.x = x;
        this.y = y;
    }
    
    public T getX() { return x; }
    public T getY() { return y; }
    
    @Override
    public boolean equals(Object obj) {
        if(this.getClass() != obj.getClass())
            return false;
        Para<T> druga = (Para<T>)obj;
        return x.equals(druga.getX()) && y.equals(druga.getY());
    }
    
    @Override
    public String toString() {
        return "<" + x + ", " + y + ">";
    }
    
    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode();
    }
    
}
