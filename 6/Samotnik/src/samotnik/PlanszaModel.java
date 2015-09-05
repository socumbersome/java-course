/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package samotnik;

import static samotnik.Kierunek.przesuń;
import samotnik.Translator.StanGry;
import samotnik.Translator.TypPlanszy;
import samotnik.Translator.TypPola;

/**
 *
 * @author ja
 */
public class PlanszaModel {

    private static final TypPola[][] plansza_brytyjska;
    private static final TypPola[][] plansza_europejska;
    private TypPlanszy typ;
    private TypPola[][] plansza;
    
    private int pozostało_pionów;
    private Punkt aktualne_pole;
    
    static {
        int[][] pl = new int[][]{
            {0,0,1,1,1,0,0},
            {0,0,1,1,1,0,0},
            {1,1,1,1,1,1,1},
            {1,1,1,2,1,1,1},
            {1,1,1,1,1,1,1},
            {0,0,1,1,1,0,0},
            {0,0,1,1,1,0,0}
        };
        plansza_brytyjska = new TypPola[pl.length][pl.length];
        for(int i = 0; i < pl.length; i++)
            for(int j = 0; j < pl.length; j++)
                plansza_brytyjska[i][j] = Translator.mapujNaTypPola(pl[i][j]);
        
        pl[1][1] = 1; pl[1][5] = 1;
        pl[5][1] = 1; pl[5][5] = 1;
        plansza_europejska = new TypPola[pl.length][pl.length];
        for(int i = 0; i < pl.length; i++)
            for(int j = 0; j < pl.length; j++)
                plansza_europejska[i][j] = Translator.mapujNaTypPola(pl[i][j]);
    }
    
    private void kopiujPlanszę(TypPola[][] from, TypPola[][] to) {
        for(int i = 0; i < from.length; i++)
            for(int j = 0; j < from[0].length; j++)
                to[i][j] = from[i][j];
    }
    
    public PlanszaModel(TypPlanszy typ) {
        this.typ = typ;
        
        switch(typ) {
            case BRYTYJSKA:
                plansza = new TypPola [plansza_brytyjska.length][plansza_brytyjska.length];
                kopiujPlanszę(plansza_brytyjska, plansza);
                pozostało_pionów = 32;
                aktualne_pole = new Punkt(2,0);
                break;
            case EUROPEJSKA:
                plansza = new TypPola [plansza_europejska.length][plansza_europejska.length];
                kopiujPlanszę(plansza_europejska, plansza);
                pozostało_pionów = 36;
                aktualne_pole = new Punkt(1,1);
                break;
            default: throw new IllegalArgumentException("Nieznany typ planszy " + typ.toString());
        }
    }
    
    public void ZaładujPlanszę(TypPlanszy typ) {
        switch(typ) {
            case BRYTYJSKA: 
                kopiujPlanszę(plansza_brytyjska, plansza);
                aktualne_pole = new Punkt(2,0);
                pozostało_pionów = 32;
                break;
            case EUROPEJSKA:
                kopiujPlanszę(plansza_europejska, plansza);
                aktualne_pole = new Punkt(1,1);
                pozostało_pionów = 36;
                break;
        }
    }
    
    public TypPola getTypPola(Punkt p) {
        return plansza[p.getY()][p.getX()];
    }
    public TypPola getTypPola(int x, int y) {
        return getTypPola(new Punkt(x, y));
    }
    
    public int getRozmiarPlanszyX() {
        return plansza[0].length;
    }
    public int getRozmiarPlanszyY() {
        return plansza.length;
    }
    
    public Punkt getAktualnePole() {
        return aktualne_pole;
    }
    
    public boolean przesuńPole(Punkt p) {
        if(!PoleWewnątrz(p) || poleNiedozwolone(p))
            return false;
        aktualne_pole = p;
        return true;
    }
    
    public boolean przesuńPole(Kierunek kier) {
        Punkt przesunięte = przesuń(aktualne_pole, kier);
        return przesuńPole(przesunięte);
    }
    
    public boolean przesuńPion(Kierunek kier) {
        if(!poleZajęte(aktualne_pole) || !poprawnyRuch(aktualne_pole, kier))
            return false;
        Punkt zbity = przesuń(aktualne_pole, kier);
        Punkt nowe_miejsce = przesuń(zbity, kier);
        zmieńStan(aktualne_pole);
        zmieńStan(zbity);
        zmieńStan(nowe_miejsce);
        aktualne_pole = nowe_miejsce;
        pozostało_pionów--;
        return true;
    }
    
    private boolean zmieńStan(Punkt p){
        if(!PoleWewnątrz(p))
            return false;
        TypPola akt_wart = plansza[p.getY()][p.getX()];
        if(akt_wart == TypPola.NIEDOZWOLONE)
            return false;
        plansza[p.getY()][p.getX()] = Translator.odwróćStan(akt_wart);
        return true;
    }
    
    private boolean poprawnyRuch(Punkt p, Kierunek kier) {
        return kolejneZajęte(p, kier) && kolejneWolne(przesuń(p, kier), kier);
    }
    
    private boolean poleWolne(Punkt p) {
        return PoleWewnątrz(p) && plansza[p.getY()][p.getX()] == TypPola.WOLNE;
    }
    private boolean poleZajęte(Punkt p) {
        return PoleWewnątrz(p) && plansza[p.getY()][p.getX()] == TypPola.ZAJĘTE;
    }
    private boolean poleNiedozwolone(Punkt p) {
        return PoleWewnątrz(p) && plansza[p.getY()][p.getX()] == TypPola.NIEDOZWOLONE;
    }
    
    private boolean kolejneWolne(Punkt p, Kierunek kier) {
        Punkt przesunięty = przesuń(p, kier);
        return PoleWewnątrz(przesunięty) && plansza[przesunięty.getY()][przesunięty.getX()] == TypPola.WOLNE;
    }
    private boolean kolejneZajęte(Punkt p, Kierunek kier) {
        Punkt przesunięty = przesuń(p, kier);
        return PoleWewnątrz(przesunięty) && plansza[przesunięty.getY()][przesunięty.getX()] == TypPola.ZAJĘTE;
    }
    
    public int PozostałychPionów() {
        return pozostało_pionów;
    }
    
    private boolean PoleWewnątrz(int x, int y) {
        return x >= 0 && x < plansza[0].length && y >= 0 && y < plansza.length;
    }
    private boolean PoleWewnątrz(Punkt p) {
        return PoleWewnątrz(p.getX(), p.getY());
    }
    
    private boolean PlanszaZablokowana() {
        Kierunek kier = new Kierunek();
        Punkt rozpatrywany;
        for(int x = 0; x < plansza[0].length; x++) {
            for(int y = 0; y < plansza.length; y++) {
                if(plansza[y][x] != TypPola.ZAJĘTE)
                    continue;
                rozpatrywany = new Punkt(x, y);
                for(Kierunek k : kier) {
                    if(poprawnyRuch(rozpatrywany, k))
                        return false;
                }
            }
        }
        return true;
    }
    
    public StanGry stanGry() {
        if(pozostało_pionów == 1)
            return StanGry.WYGRANA;
        if(PlanszaZablokowana())
            return StanGry.PRZEGRANA;
        return StanGry.WTOKU;
    }
}
