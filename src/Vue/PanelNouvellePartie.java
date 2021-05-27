package Vue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controleur.Jeu;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class PanelNouvellePartie extends JPanel{
    private JComboBox<String> choixMap;
    private JComboBox<Integer> nbJoueursHumain;
    private JComboBox<Integer> nbJoueursIA;
    private JButton btnContinuer;
    private JButton btnQuitter;
    private JLabel[] nomJoueur;
    private JTextField[] txtNomJoueur;

    public PanelNouvellePartie() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        ArrayList<String> nomMap = new ArrayList<String>();
        initListeCartes(nomMap);
        Integer[] nbJoueursH = {0,1,2,3,4};
        Integer[] nbJoueursIAInteger= {0,1,2,3,4};
        choixMap = new JComboBox<String>((String[])nomMap.toArray(new String[0]));
        nbJoueursHumain = new JComboBox<Integer>(nbJoueursH);
        nbJoueursIA = new JComboBox<Integer>(nbJoueursIAInteger);
        nomJoueur = new JLabel[4];
        txtNomJoueur = new JTextField[4];
        btnContinuer = new JButton("Continuer");
        btnQuitter = new JButton("Quitter");
        choixMap.setActionCommand("choixMap");
        nbJoueursHumain.setActionCommand("nbJoueursH");
        nbJoueursIA.setActionCommand("nbJoueursIA");
        btnContinuer.setActionCommand("nouvellePartieContinuer");
        btnQuitter.setActionCommand("retourMenu");

        contrainte.gridx=0;
        contrainte.gridy=0;
        this.add(new JLabel("Nom Map : "),contrainte);
        contrainte.gridx++;
        this.add(choixMap,contrainte);
        contrainte.gridx=0;
        contrainte.gridy++;
        this.add(new JLabel("Joueur(s) Huamin(s) : "),contrainte);
        contrainte.gridx++;
        this.add(nbJoueursHumain,contrainte);
        contrainte.gridx=0;
        contrainte.gridy++;
        this.add(new JLabel("Joueur IA : "),contrainte);
        contrainte.gridx++;
        this.add(nbJoueursIA,contrainte);
        contrainte.gridx=0;
        contrainte.gridy++;
        for (int i = 0; i < 4; i++) {
            contrainte.gridx=0;
            nomJoueur[i] = new JLabel("Nom Joueur "+(i+1));
            txtNomJoueur[i] = new JTextField(10);
            this.add(nomJoueur[i], contrainte);
            contrainte.gridx++;
            this.add(txtNomJoueur[i],contrainte);
            nomJoueur[i].setVisible(false);
            txtNomJoueur[i].setVisible(false);
            contrainte.gridy++;
        }
        this.add(btnContinuer,contrainte);
        contrainte.gridx++;
        this.add(btnQuitter,contrainte);
    }

    public boolean setAllNames(int max){
        if (max > 4) {
            max = 4;
        }
        for (int i = 0; i < max; i++) {
            if (txtNomJoueur[i].getText().equals("")){
                return false;
            }
        }
        return true;
    }

    public void initListeCartes(ArrayList<String> nomMap){
        nomMap.add("");
        File dossierDefault = new File("src"+File.separator+"data"+File.separator+"cartes"+File.separator+"default"+File.separator);
        File dossierSaves = new File("src"+File.separator+"data"+File.separator+"cartes"+File.separator+"saves"+File.separator);
        File[] listeNomCartesDefault = dossierDefault.listFiles();
        File[] listeNomCartesSaves = dossierSaves.listFiles();

        for (int i = 0; i < listeNomCartesDefault.length; i++) {
            if (listeNomCartesDefault[i].isFile()) {
                nomMap.add(listeNomCartesDefault[i].getName().replace(".txt", ""));
            }
        }
        for (int i = 0; i < listeNomCartesSaves.length; i++) {
            if (listeNomCartesSaves[i].isFile()) {
                nomMap.add(listeNomCartesSaves[i].getName().replace(".txt", ""));
            }
        }
    }


    public void enregistreEcouteur(Jeu controleur) {
        btnContinuer.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
        choixMap.addActionListener(controleur);
        nbJoueursHumain.addActionListener(controleur);
        nbJoueursIA.addActionListener(controleur);
    }
    public JLabel[] getNomJoueur() {
        return this.nomJoueur;
    }

    public JTextField[] getTxtNomJoueur() {
        return this.txtNomJoueur;
    }
}
