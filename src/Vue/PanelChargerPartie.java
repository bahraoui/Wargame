package Vue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import controleur.Jeu;

import java.awt.*;
import java.io.File;
import java.io.IOException;

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
        lblParcourirFichier.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
        lblParcourirFichier.setForeground(new Color(109,7,26));
        btnChargerSauvegarde = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Parcourir.png", btnChargerSauvegarde);
        lblCarteChosie = new JLabel("Sauvegarde choisie : ");
        lblCarteChosie.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
        lblCarteChosie.setForeground(new Color(109,7,26));
        btnContinuerScenario = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"LancerPartie.png", btnContinuerScenario);
        btnQuitter = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter2.png", btnQuitter);
        btnChargerSauvegarde.setActionCommand("chercherSauvegarde");
        btnContinuerScenario.setActionCommand("lancerPartieChargee");
        btnQuitter.setActionCommand("retourMenu");

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

    public void enregistreEcouteur(Jeu controleur) {
        btnChargerSauvegarde.addActionListener(controleur);
        btnContinuerScenario.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
    }

    public JLabel getLblCarteChosie(){
        return lblCarteChosie;
    }

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
