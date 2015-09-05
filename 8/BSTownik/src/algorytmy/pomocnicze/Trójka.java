/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorytmy.pomocnicze;

/**
 *
 * @author ja
 */
public class Trójka<X, Y, Z>
{
    private final X x;
    private final Y y;
    private final Z z;

    public Trójka(X a, Y b, Z c) {
        x = a;
        y = b;
        z = c;
    }

    public X getX() {
        return x;
    }
    public Y getY() {
        return y;
    }
    public Z getZ() {
        return z;
    }
}