package Vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Jeu;

/**
 * La classe PanelChargerPartie herite de JPanel et permet de selectionner une sauvegarde pour jouer
 */
public class PanelChargerPartie extends JPanel{

    private JButton btnChargerSauvegarde;
    private JLabel lblCarteChosie;
    private JButton btnContinuerScenario;
    private JButton btnQuitter;

    /**
     * Le contructeur de PanelChargerPartie permet d'instancier le panel
     */
    public PanelChargerPartie() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        JLabel lblParcourirFichier = new JLabel("Veuillez choisir une sauvegarde : ");
        lblParcourirFichier.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
        lblParcourirFichier.setForeground(new Color(109,7,26));

        // bouton sauvegarde
        btnChargerSauvegarde = new JButton();
        OutilsVue.setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Parcourir.png", btnChargerSauvegarde);
        lblCarteChosie = new JLabel("Sauvegarde choisie : ");
        lblCarteChosie.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
        lblCarteChosie.setForeground(new Color(109,7,26));

        // bouton Lancer la partie
        btnContinuerScenario = new JButton();
        OutilsVue.setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"LancerPartie.png", btnContinuerScenario);

        // bouton Quitter
        btnQuitter = new JButton();
        OutilsVue.setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter2.png", btnQuitter);
        btnChargerSauvegarde.setActionCommand("chercherSauvegarde");
        btnContinuerScenario.setActionCommand("lancerPartieChargee");
        btnQuitter.setActionCommand("retourMenu");

        // ajout des elements avecGridBagLayout
        contrainte.gridx=0;
        contrainte.gridy=0;
        JLabel chargerPartie = new JLabel("Charger une partie");
        chargerPartie.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
        chargerPartie.setForeground(new Color(109,7,26));
        this.add(chargerPartie,contrainte);
        contrainte.gridy++;
        this.add(lblParcourirFichier,contrainte);
        contrainte.gridx++;
        this.add(btnChargerSauvegarde,contrainte);
        contrainte.gridx--;
        contrainte.gridy++;
        this.add(lblCarteChosie,contrainte);
        contrainte.gridy++;
        this.add(btnContinuerScenario,contrainte);
        contrainte.gridx++;
        this.add(btnQuitter,contrainte);
    }



     /**
	 * La methode enregistre_ecouteur met a l'ecoute tous les elements du panel pour le controleur
	 * @param controleur controleur que l'on souhaite mettre a l'ecoute
	 */
    public void enregistrerEcouteur(Jeu controleur) {
        btnChargerSauvegarde.addActionListener(controleur);
        btnContinuerScenario.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
    }

    // Getter et setters
    
    /**
     * Le getter getLblCarteChosie permet de recuperer le JLabel getLblCarteChosie
     * @return JLabel
     */
    public JLabel getLabelCarteChosie(){
        return lblCarteChosie;
    }

    // FIN Getter et setters

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Fonds"+File.separator+"fond.jpg")), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
