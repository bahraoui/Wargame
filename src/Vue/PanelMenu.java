package Vue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Jeu;

import java.awt.*;

public class PanelMenu extends JPanel{
    private JButton btnNvllePartie;
    private JButton btnRegles;
    private JButton btnQuitter;
    private JButton btnChargerPartie;

    public PanelMenu() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        btnNvllePartie = new JButton("Nouvelle Partie");
        btnChargerPartie = new JButton("Charger Partie");
        btnRegles = new JButton("Règles");
        btnQuitter = new JButton("Quitter");
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

    public void enregistreEcouteur(Jeu controleur) {
        btnNvllePartie.addActionListener(controleur);
        btnChargerPartie.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
        btnRegles.addActionListener(controleur);
    }
}
