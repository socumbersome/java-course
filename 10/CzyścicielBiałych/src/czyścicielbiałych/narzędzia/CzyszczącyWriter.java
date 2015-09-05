/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package czyścicielbiałych.narzędzia;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author ja
 */
public class CzyszczącyWriter extends Writer {
    
    private Writer out;
    private CzyszczącyReader pośrednik;
    
    public CzyszczącyWriter(Writer out) {
        this.out = out;
    }
    
    @Override
    public void write(char[] buf, int od, int len) throws IOException {
        
        pośrednik = new CzyszczącyReader(new CharArrayReader(buf, od, len));
        char[] przetworzone = new char[len];
        int przepisane = pośrednik.read(przetworzone, 0, len);
        out.write(przetworzone, 0, przepisane);
        
        pośrednik.close();
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
    
}
