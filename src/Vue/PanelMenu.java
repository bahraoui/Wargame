package Vue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Jeu;

import java.awt.*;
import java.io.File;
import java.io.IOException;


/**
 * La classe PanelMenu herite de JPanel et permet d'afficher les boutons suivants :
 * - bouton Start : début de partie
 * - bouton Regles : afficher les regles 
 * - bouton Charger : qui permet de reprendre une partie en cours
 * - bouton Quitter : quitte la fenetre
 */
public class PanelMenu extends JPanel{
    private JButton btnNvllePartie;
    private JButton btnRegles;
    private JButton btnQuitter;
    private JButton btnChargerPartie;


    /**
     * Le constructeur PanelMenu permet d'instancier un JPanel avec les 4 boutons
     */
    public PanelMenu() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        btnNvllePartie = new JButton();
        btnRegles = new JButton();
        btnChargerPartie = new JButton();
        btnQuitter = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonStart.png", btnNvllePartie);
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Regles.png", btnRegles);
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"ChargerPartie.png", btnChargerPartie);
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter.png", btnQuitter);
        //btnChargerPartie = new JButton("Charger Partie");
        btnNvllePartie.setActionCommand("nouvellePartie");
        btnChargerPartie.setActionCommand("chargerPartie");
        btnRegles.setActionCommand("afficherRegles");
        btnQuitter.setActionCommand("quit");

        contrainte.gridx=0;
        contrainte.gridy=0;
        this.add(new JLabel("Menu"),contrainte);
        contrainte.gridy++;
        this.add(btnNvllePartie,contrainte);
        contrainte.gridy++;
        this.add(btnChargerPartie,contrainte);
        contrainte.gridy++;
        this.add(btnRegles,contrainte);
        contrainte.gridy++;
        this.add(btnQuitter,contrainte);
    }

    /**
	 * La methode enregistreEcouteur met a l'ecoute tous les elements du panel pour le controleur
	 * @param controleur controleur que l'on souhaite mettre a l'ecoute
	 */
    public void enregistreEcouteur(Jeu controleur) {
        btnNvllePartie.addActionListener(controleur);
        btnChargerPartie.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
        btnRegles.addActionListener(controleur);
    }

    /**
     * setImageBouton permet d'afficher une image dans le bouton
     * @param filePathName String
     * @param btnAModifier JButton
     */
    private void setImageBouton(String filePathName,JButton btnAModifier){
        btnAModifier.setMargin(new Insets(0, 0, 0, 0));
        btnAModifier.setBorder(null);
        try {
            btnAModifier.setIcon(new ImageIcon(ImageIO.read(new File(filePathName))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Fonds"+File.separator+"fondMedieval.jpg")), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
