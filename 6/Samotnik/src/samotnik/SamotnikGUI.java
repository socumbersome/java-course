/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package samotnik;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import samotnik.Translator.TypPlanszy;

/**
 *
 * @author ja
 */
public class SamotnikGUI extends javax.swing.JFrame {
    private final PlanszaModel LogikaPlanszy;
    /**
     * Creates new form SamotnikGUI
     * @param LogikaPlanszy
     */
    public SamotnikGUI(PlanszaModel LogikaPlanszy) {
        this.LogikaPlanszy = LogikaPlanszy;
        initComponents();
    }
    
    private void initComponents() {
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Samotnik");
        setMinimumSize(new java.awt.Dimension(50, 50));
        getContentPane().setLayout(new java.awt.BorderLayout(1, 1));
        
        StanGryL = new javax.swing.JLabel();
        PozostałychPionówL = new javax.swing.JLabel();
        OGrzeDialog = new javax.swing.JDialog();
        OpisZasadGry = new javax.swing.JLabel();
        OAplikacjiDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        oknoGry = new samotnik.OknoGry(LogikaPlanszy, StanGryL, PozostałychPionówL);
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel2 = new javax.swing.JLabel();
        MenuGlowne = new javax.swing.JMenuBar();
        GraMenu = new javax.swing.JMenu();
        GraNowaM = new javax.swing.JMenuItem();
        GraKoniecM = new javax.swing.JMenuItem();
        RuchyMenu = new javax.swing.JMenu();
        RuchyZaznaczPoleM = new javax.swing.JMenu();
        ZaznaczPowyżejM = new javax.swing.JMenuItem();
        ZaznaczPoniżejM = new javax.swing.JMenuItem();
        ZaznaczNaLewoM = new javax.swing.JMenuItem();
        ZaznaczNaPrawoM = new javax.swing.JMenuItem();
        RuchyWykonajM = new javax.swing.JMenu();
        WykonajWGóręM = new javax.swing.JMenuItem();
        WykonajWDółM = new javax.swing.JMenuItem();
        WykonajWLewoM = new javax.swing.JMenuItem();
        WykonajWPrawoM = new javax.swing.JMenuItem();
        UstawieniaMenu = new javax.swing.JMenu();
        ButtonGroup TypPlanszyGroup = new ButtonGroup();
        TypBrytyjskaM = new javax.swing.JRadioButtonMenuItem();
        TypEuropejskaM = new javax.swing.JRadioButtonMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        KolorPlanszyM = new javax.swing.JMenuItem();
        KolorPionówM = new javax.swing.JMenuItem();
        KolorLosowyM = new javax.swing.JMenuItem();
        PomocMenu = new javax.swing.JMenu();
        OGrzeM = new javax.swing.JMenuItem();
        OAplikacjiM = new javax.swing.JMenuItem();
        
        getContentPane().add(oknoGry, java.awt.BorderLayout.CENTER);

        OGrzeDialog.setTitle("O grze");
        OGrzeDialog.setMinimumSize(new java.awt.Dimension(200, 200));
        OGrzeDialog.getContentPane().setLayout(new java.awt.GridBagLayout());

        OpisZasadGry.setText("Polecam Wikipedię...");
        OGrzeDialog.getContentPane().add(OpisZasadGry, new java.awt.GridBagConstraints());

        OAplikacjiDialog.setTitle("O aplikacji");
        OAplikacjiDialog.setMinimumSize(new java.awt.Dimension(200, 200));
        OAplikacjiDialog.getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("<html>Autor: Patryk Kajdas<br>Wersja: zadowalająco dojrzała<br>Data utworzenia: 26.11.2013r.</html>");
        OAplikacjiDialog.getContentPane().add(jLabel1, new java.awt.GridBagConstraints());

        jPanel3.setMinimumSize(new java.awt.Dimension(20, 20));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 20));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setText("Stan gry: ");
        jPanel3.add(jLabel3);
        jPanel3.add(StanGryL);
        jPanel3.add(filler1);

        jLabel2.setText("Pozostałych pionów: ");
        jPanel3.add(jLabel2);

        jPanel3.add(PozostałychPionówL);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        MenuGlowne.setToolTipText("");

        GraMenu.setMnemonic(KeyEvent.VK_G);
        GraMenu.setText("Gra");

        GraNowaM.setMnemonic(KeyEvent.VK_N);
        GraNowaM.setText("Nowa");
        GraNowaM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GraNowaMActionPerformed(evt);
            }
        });
        GraMenu.add(GraNowaM);

        GraKoniecM.setMnemonic(KeyEvent.VK_K);
        GraKoniecM.setText("Koniec");
        GraKoniecM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GraKoniecMActionPerformed(evt);
            }
        });
        GraMenu.add(GraKoniecM);

        MenuGlowne.add(GraMenu);

        RuchyMenu.setMnemonic(KeyEvent.VK_R);
        RuchyMenu.setText("Ruchy");

        RuchyZaznaczPoleM.setText("Zaznacz pole");

        ZaznaczPowyżejM.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, 0));
        ZaznaczPowyżejM.setText("Powyżej");
        ZaznaczPowyżejM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZaznaczPowyżejMActionPerformed(evt);
            }
        });
        RuchyZaznaczPoleM.add(ZaznaczPowyżejM);

        ZaznaczPoniżejM.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, 0));
        ZaznaczPoniżejM.setText("Poniżej");
        ZaznaczPoniżejM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZaznaczPoniżejMActionPerformed(evt);
            }
        });
        RuchyZaznaczPoleM.add(ZaznaczPoniżejM);

        ZaznaczNaLewoM.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, 0));
        ZaznaczNaLewoM.setText("Na lewo");
        ZaznaczNaLewoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZaznaczNaLewoMActionPerformed(evt);
            }
        });
        RuchyZaznaczPoleM.add(ZaznaczNaLewoM);

        ZaznaczNaPrawoM.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, 0));
        ZaznaczNaPrawoM.setText("Na prawo");
        ZaznaczNaPrawoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZaznaczNaPrawoMActionPerformed(evt);
            }
        });
        RuchyZaznaczPoleM.add(ZaznaczNaPrawoM);

        RuchyMenu.add(RuchyZaznaczPoleM);

        RuchyWykonajM.setText("Wykonaj");

        WykonajWGóręM.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_NUMPAD8, 0));
        WykonajWGóręM.setText("W górę");
        WykonajWGóręM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WykonajWGóręMActionPerformed(evt);
            }
        });
        RuchyWykonajM.add(WykonajWGóręM);

        WykonajWDółM.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_NUMPAD5, 0));
        WykonajWDółM.setText("W dół");
        WykonajWDółM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WykonajWDółMActionPerformed(evt);
            }
        });
        RuchyWykonajM.add(WykonajWDółM);

        WykonajWLewoM.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_NUMPAD4, 0));
        WykonajWLewoM.setText("W lewo");
        WykonajWLewoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WykonajWLewoMActionPerformed(evt);
            }
        });
        RuchyWykonajM.add(WykonajWLewoM);

        WykonajWPrawoM.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_NUMPAD6, 0));
        WykonajWPrawoM.setText("W prawo");
        WykonajWPrawoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WykonajWPrawoMActionPerformed(evt);
            }
        });
        RuchyWykonajM.add(WykonajWPrawoM);

        RuchyMenu.add(RuchyWykonajM);

        MenuGlowne.add(RuchyMenu);

        UstawieniaMenu.setMnemonic(KeyEvent.VK_U);
        UstawieniaMenu.setText("Ustawienia");

        TypBrytyjskaM.setSelected(true);
        TypBrytyjskaM.setText("Plansza Brytyjska");
        TypPlanszyGroup.add(TypBrytyjskaM);
        UstawieniaMenu.add(TypBrytyjskaM);
        
        TypEuropejskaM.setSelected(false);
        TypEuropejskaM.setText("Plansza Europejska");
        TypPlanszyGroup.add(TypEuropejskaM);
        UstawieniaMenu.add(TypEuropejskaM);
        
        UstawieniaMenu.add(jSeparator2);

        KolorPlanszyM.setText("Kolor planszy");
        KolorPlanszyM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KolorPlanszyMActionPerformed(evt);
            }
        });
        UstawieniaMenu.add(KolorPlanszyM);

        KolorPionówM.setText("Kolor pionów");
        KolorPionówM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KolorPionówMActionPerformed(evt);
            }
        });
        UstawieniaMenu.add(KolorPionówM);

        KolorLosowyM.setText("Kolor losowy");
        KolorLosowyM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KolorLosowyMActionPerformed(evt);
            }
        });
        UstawieniaMenu.add(KolorLosowyM);

        MenuGlowne.add(UstawieniaMenu);

        PomocMenu.setMnemonic(KeyEvent.VK_P);
        PomocMenu.setText("Pomoc");
        PomocMenu.setAlignmentX(0.0F);

        OGrzeM.setText("O grze");
        OGrzeM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                OGrzeMMouseReleased(evt);
            }
        });
        PomocMenu.add(OGrzeM);

        OAplikacjiM.setText("O aplikacji");
        OAplikacjiM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                OAplikacjiMMouseReleased(evt);
            }
        });
        PomocMenu.add(OAplikacjiM);

        MenuGlowne.add(Box.createHorizontalGlue());

        MenuGlowne.add(PomocMenu);

        setJMenuBar(MenuGlowne);

        pack();
    }                      
    
    private void OGrzeMMouseReleased(java.awt.event.MouseEvent evt) {                                     
        OGrzeDialog.setVisible(true);
    }                                    

    private void OAplikacjiMMouseReleased(java.awt.event.MouseEvent evt) {                                          
        OAplikacjiDialog.setVisible(true);
    }                                         

    private void WykonajWGóręMActionPerformed(java.awt.event.ActionEvent evt) {                                              
        oknoGry.WykonajRuch(new Kierunek("góra"));
    }                                             

    private void WykonajWDółMActionPerformed(java.awt.event.ActionEvent evt) {                                             
        oknoGry.WykonajRuch(new Kierunek("dół"));
    } 
    
    private void WykonajWLewoMActionPerformed(java.awt.event.ActionEvent evt) {                                              
        oknoGry.WykonajRuch(new Kierunek("lewo"));
    } 

    private void WykonajWPrawoMActionPerformed(java.awt.event.ActionEvent evt) {                                               
        oknoGry.WykonajRuch(new Kierunek("prawo"));
    }                                              

    private void ZaznaczPowyżejMActionPerformed(java.awt.event.ActionEvent evt) {                                                
        oknoGry.ZaznaczNowe(new Kierunek("góra"));
    }                                               

    private void ZaznaczPoniżejMActionPerformed(java.awt.event.ActionEvent evt) {                                                
        oknoGry.ZaznaczNowe(new Kierunek("dół"));
    }                                               

    private void ZaznaczNaLewoMActionPerformed(java.awt.event.ActionEvent evt) {                                               
        oknoGry.ZaznaczNowe(new Kierunek("lewo"));
    }                                              

    private void ZaznaczNaPrawoMActionPerformed(java.awt.event.ActionEvent evt) {                                                
        oknoGry.ZaznaczNowe(new Kierunek("prawo"));
    }
    
    private void KolorPlanszyMActionPerformed(java.awt.event.ActionEvent evt) {                                                
        Color c = wybierzKolor("Wybierz kolor planszy (tła)", Color.white);
        if(c != null)
            oknoGry.setTło(c);
    }
    
    private void KolorPionówMActionPerformed(java.awt.event.ActionEvent evt) {                                                
        Color c = wybierzKolor("Wybierz kolor wszystkich pionów", Color.green);
        if(c != null)
            oknoGry.przekolorujPiony(c);
    }
    
    private Color wybierzKolor(String tytuł, Color k) {
        return JColorChooser.showDialog(this, tytuł, k);
    }
    
    private void KolorLosowyMActionPerformed(java.awt.event.ActionEvent evt) {                                                
        oknoGry.kolorujLosowo();
    }

    private void GraNowaMActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(TypBrytyjskaM.isSelected())
            LogikaPlanszy.ZaładujPlanszę(TypPlanszy.BRYTYJSKA);
        else
            LogikaPlanszy.ZaładujPlanszę(TypPlanszy.EUROPEJSKA);
        oknoGry.NowaGra();
    }                                        

    private void GraKoniecMActionPerformed(java.awt.event.ActionEvent evt) {                                           
        this.dispose();
    }
                   
    private javax.swing.JMenuItem GraKoniecM;
    private javax.swing.JMenu GraMenu;
    private javax.swing.JMenuItem GraNowaM;
    private javax.swing.JMenuItem KolorLosowyM;
    private javax.swing.JMenuItem KolorPionówM;
    private javax.swing.JMenuItem KolorPlanszyM;
    private javax.swing.JMenuBar MenuGlowne;
    private javax.swing.JDialog OAplikacjiDialog;
    private javax.swing.JMenuItem OAplikacjiM;
    private javax.swing.JDialog OGrzeDialog;
    private javax.swing.JMenuItem OGrzeM;
    private javax.swing.JLabel OpisZasadGry;
    private javax.swing.JMenu PomocMenu;
    private javax.swing.JLabel PozostałychPionówL;
    private javax.swing.JMenu RuchyMenu;
    private javax.swing.JMenu RuchyWykonajM;
    private javax.swing.JMenu RuchyZaznaczPoleM;
    private javax.swing.JLabel StanGryL;
    private javax.swing.JRadioButtonMenuItem TypBrytyjskaM;
    private javax.swing.JRadioButtonMenuItem TypEuropejskaM;
    private javax.swing.JMenu UstawieniaMenu;
    private javax.swing.JMenuItem WykonajWDółM;
    private javax.swing.JMenuItem WykonajWGóręM;
    private javax.swing.JMenuItem WykonajWLewoM;
    private javax.swing.JMenuItem WykonajWPrawoM;
    private javax.swing.JMenuItem ZaznaczNaLewoM;
    private javax.swing.JMenuItem ZaznaczNaPrawoM;
    private javax.swing.JMenuItem ZaznaczPoniżejM;
    private javax.swing.JMenuItem ZaznaczPowyżejM;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private samotnik.OknoGry oknoGry;                  
}
