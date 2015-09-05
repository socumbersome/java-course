/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kalkulatoronp.narzędzia;

import kalkulatoronp.narzędzia.strukt_danych.*;

/**
 *
 * @author ja
 */
public class Wyrażenie {
    private Kolejka kolejka;
    private Stos stos;
    private Lista zmienne;
    
    public Wyrażenie(String onp, Lista zm) throws WyjątekONP {
        zmienne = zm;
        String tokeny[] = onp.split("\\s+");
        kolejka = new Kolejka();
        Operand op; Funkcja fun;
        String lb_pattern = "-?[0-9]+((\\.|,)[0-9]+)?";
        for (String token : tokeny) {
            op = null;
            fun = null;
            switch (token) {
                case "e":
                case "E": fun = new E(); break;
                case "pi":
                case "Pi": fun = new Pi(); break;
                case "+": fun = new Dodaj(); break;
                case "-": fun = new Odejmij(); break;
                case "*": fun = new Mnóż(); break;
                case "/": fun = new Dziel(); break;
                case "min":
                case "Min": fun = new Min(); break;
                case "max":
                case "Max": fun = new Max(); break;
                case "log":
                case "Log": fun = new Log(); break;
                case "pow":
                case "Pow": fun = new Pow(); break;
                case "abs":
                case "Abs": fun = new Abs(); break;
                case "sgn":
                case "Sgn": fun = new Sgn(); break;
                case "floor":
                case "Floor": fun = new Floor(); break;
                case "frac":
                case "Frac": fun = new Frac(); break;
                case "ceil":
                case "Ceil": fun = new Ceil(); break;
                case "sin":
                case "Sin": fun = new Sin(); break;
                case "cos":
                case "Cos": fun = new Cos(); break;
                case "atan":
                case "Atan":
                case "arctan":
                case "Arctan": fun = new Atan(); break;
                case "acot":
                case "Acot":
                case "arccot":
                case "Arccot": fun = new Acot(); break;
                case "ln":
                case "Ln": fun = new Ln(); break;
                case "exp":
                case "Exp": fun = new Exp(); break;
                default:
                {
                    if (token.matches(lb_pattern)) {
                        op = new Liczba(Double.parseDouble(token.replaceAll(",", ".")));
                    } else {
                        op = new Zmienna(token);
                    }
                }
            }
            if(fun != null)
                kolejka.wstaw(fun);
            else if(op != null)
                kolejka.wstaw(op);
            else
                throw new ONP_NieznanySymbol(token);
        }
    }
    
    public double Oblicz() throws WyjątekONP {
        Zmienna.środowisko = zmienne;
        stos = new Stos();
        Stos aux = new Stos();
        Symbol sym = null;
        Funkcja ostatnia_f = null;
        while(!kolejka.pusta()) {
            sym = kolejka.pokaz();
            kolejka.zdejmij();
            if(sym instanceof Operand) {
                Operand op = (Operand)sym;
                stos.wstaw(op.obliczWartość());
            }
            else if(sym instanceof Funkcja) {
                Funkcja fun = (Funkcja)sym;
                ostatnia_f = fun;
                for(int i = 0; i < fun.brakująceArgumenty(); i++) {
                    try {
                        aux.wstaw(stos.pokaz());
                    }
                    catch (java.util.EmptyStackException ex) {
                        throw new ONP_ZbytMałoArg(fun.toString());
                    }
                    try {
                        stos.zdejmij();
                    }
                    catch (java.util.EmptyStackException ex) {
                        throw new ONP_PustyStos();
                    }
                }
                while(fun.brakująceArgumenty() > 0) {
                    fun.dodajArgument(aux.pokaz());
                    assert !aux.pusty();
                    aux.zdejmij();
                }
                if(!aux.pusty()) {
                    throw new ONP_ZbytWieleArg(fun.toString());
                }
                
                stos.wstaw(fun.obliczWartość());
            }
            else {
                throw new ONP_NieznanySymbol(sym.toString());
            }
        }
        
        double wynik = stos.pokaz();
        stos.zdejmij();
        if(!stos.pusty())
            throw new ONP_BłędneWyrażenie("Brakuje funkcji mogącej przyjąć pozostały argument " + String.valueOf(wynik));
        
        return wynik;
    }
}
