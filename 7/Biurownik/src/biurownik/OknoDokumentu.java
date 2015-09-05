/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biurownik;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.text.Keymap;
import javax.swing.undo.UndoManager;

/**
 *
 * @author ja
 */
public class OknoDokumentu extends JInternalFrame {
    private final static boolean RESIZABLE, CLOSEABLE,
            MAXIMIZABLE, ICONIFIABLE;
    private static int OFFSET = 0;
    
    static {
        RESIZABLE = true;
        CLOSEABLE = false;
        MAXIMIZABLE = true;
        ICONIFIABLE = false;
    }
    
    private class MenuEdycji extends JMenuBar {
        public MenuEdycji() {
            initComponents();
        }
        
        private void initComponents() {
            JMenu edycja = new JMenu("Edycja");
            add(edycja);
            
            JMenuItem menuItem = new JMenuItem("Cofnij");
            menuItem.setAccelerator(skróty.get("cofnij"));
            menuItem.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    Cofnij();
                }
            });
            edycja.add(menuItem);
            menuItem = new JMenuItem("Powtórz");
            menuItem.setAccelerator(skróty.get("powtórz"));
            menuItem.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    Powtórz();
                }
            });
            edycja.add(menuItem);
            menuItem = new JMenuItem("Kopiuj");
            menuItem.setAccelerator(skróty.get("kopiuj"));
            menuItem.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    Kopiuj();
                }
            });
            edycja.add(menuItem);
            menuItem = new JMenuItem("Wklej");
            menuItem.setAccelerator(skróty.get("wklej"));
            menuItem.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    Wklej();
                }
            });
            edycja.add(menuItem);
            menuItem = new JMenuItem("Wytnij");
            menuItem.setAccelerator(skróty.get("wytnij"));
            menuItem.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    Wytnij();
                }
            });
            edycja.add(menuItem);
        }
    }
    
    public OknoDokumentu(String nazwa) {
        super(nazwa, RESIZABLE, CLOSEABLE, MAXIMIZABLE, ICONIFIABLE);
        this.nazwa = nazwa;
        setSize(200, 200);
        setLocation(OFFSET, OFFSET);
        OFFSET += 15;
        //setFocusable(false);
        MenuEdycji menu = new MenuEdycji();
        setMenuBar(menu);
        
        undomgr = new UndoManager();
        
        /* jeśli nie chcemy zawijania wierszy, lepiej użyć JTextArea
           - raczej bug JEditorPane'a...
        */
        edytor = new JEditorPane();
        edytor.setDragEnabled(true);
        edytor.getDocument().addUndoableEditListener(undomgr);
        scrollableEdytor = new JScrollPane(edytor);
        scrollableEdytor.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableEdytor.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        add(scrollableEdytor);
        
        Keymap km = edytor.getKeymap();
        // UWAGA! Semantyka addKeymap jest bez sensu!
        // Powinno być - createKeymap...
        Keymap newkm = JEditorPane.addKeymap(null, km);
        newkm.addActionForKeyStroke(skróty.get("kopiuj"), new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                Kopiuj();
            }
        });
        newkm.addActionForKeyStroke(skróty.get("wklej"), new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                Wklej();
            }
        });
        newkm.addActionForKeyStroke(skróty.get("wytnij"), new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                Wytnij();
            }
        });
        newkm.addActionForKeyStroke(skróty.get("powtórz"), new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                Powtórz();
            }
        });
        newkm.addActionForKeyStroke(skróty.get("cofnij"), new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                Cofnij();
            }
        });
        edytor.setKeymap(newkm);
    }
    
    private void Cofnij() {
        if(undomgr.canUndo())
            undomgr.undo();
    }
    
    private void Powtórz() {
        if(undomgr.canRedo())
            undomgr.redo();
    }
    
    private void Kopiuj() {
        edytor.copy();
    }
    
    private void Wklej() {
        edytor.paste();
    }
    
    private void Wytnij() {
        edytor.cut();
    }
    
    public String TekstWewnątrz() {
        return edytor.getText();
    }
    
    private String nazwa;
    private JEditorPane edytor;
    private JScrollPane scrollableEdytor;
    private UndoManager undomgr;
    private final static HashMap<String, KeyStroke> skróty;
    
    static {
        skróty = new HashMap<>();
        skróty.put("cofnij", KeyStroke.getKeyStroke("control Z"));
        skróty.put("powtórz", KeyStroke.getKeyStroke("control X"));
        skróty.put("kopiuj", KeyStroke.getKeyStroke("control C"));
        skróty.put("wklej", KeyStroke.getKeyStroke("control V"));
        skróty.put("wytnij", KeyStroke.getKeyStroke("control W"));
    }
}
