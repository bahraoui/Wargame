package Vue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Jeu;

import java.awt.*;

public class PanelNouvellePartie extends JPanel{
    private JComboBox<String> choixMap;
    private JComboBox<Integer> nbJoueursHumain;
    private JComboBox<Integer> nbJoueursIA;
    private JButton btnContinuer;
    private JButton btnQuitter;

    public PanelNouvellePartie() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        String[] nomMap = {"","Map desertique","Map artique"};
        Integer[] nbJoueursH = {0,1,2,3,4};
        Integer[] nbJoueursIAInteger= {0,1,2,3,4};
        choixMap = new JComboBox<String>(nomMap);
        nbJoueursHumain = new JComboBox<Integer>(nbJoueursH);
        nbJoueursIA = new JComboBox<Integer>(nbJoueursIAInteger);
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
        this.add(btnContinuer,contrainte);
        contrainte.gridx++;
        this.add(btnQuitter,contrainte);
    }

    public void enregistreEcouteur(Jeu controleur) {
        btnContinuer.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
        choixMap.addActionListener(controleur);
        nbJoueursHumain.addActionListener(controleur);
        nbJoueursIA.addActionListener(controleur);
    }
}
