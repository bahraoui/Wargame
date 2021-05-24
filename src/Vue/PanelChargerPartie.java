package Vue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import controleur.Jeu;

import java.awt.*;

public class PanelChargerPartie extends JPanel{

    private JButton btnChargerSauvegarde;
    private JLabel lblCarteChosie;
    private JButton btnContinuerScenario;
    private JButton btnQuitter;
    private JFileChooser fileChoose;

    public PanelChargerPartie() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        JLabel lblParcourirFichier = new JLabel("Veuillez choisir une sauvegarde : ");
        btnChargerSauvegarde = new JButton("Parcourir");
        lblCarteChosie = new JLabel("Sauvegarde chosie : ");
        btnContinuerScenario = new JButton("Lancer la partie");
        btnQuitter = new JButton("Quitter");
        btnChargerSauvegarde.setActionCommand("chercherSauvegarde");
        btnContinuerScenario.setActionCommand("lancerPartieChargee");
        btnQuitter.setActionCommand("retourMenu");

        contrainte.gridx=0;
        contrainte.gridy=0;
        this.add(new JLabel("Charger une partie"),contrainte);
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

    public void enregistreEcouteur(Jeu controleur) {
        btnChargerSauvegarde.addActionListener(controleur);
        btnContinuerScenario.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
    }

    public JLabel getLblCarteChosie(){
        return lblCarteChosie;
    }

    
    
}
