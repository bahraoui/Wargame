package Vue;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.*;

import controleur.Jeu;

/**
 * La classe PanelVictoire herite de la classe JPanel permet d'afficher le victoire d'un joueur
 */
public class PanelVictoire extends JPanel{

    private BorderLayout bdl;
    private JPanel panelInfosVictoire;
    private JLabel labelVictoire, labelNomVainqueur;
    private JButton boutonQuitter;

    /**
     * Le constructeur de PanelVictoire permet d'intancier ce JPanel
     */
    public PanelVictoire() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        contrainte.gridx=0;
        contrainte.gridy=0;

        /**
         * Informations sur le vainqueur
         */
        panelInfosVictoire = new JPanel();
        panelInfosVictoire.setOpaque(true);
        panelInfosVictoire.setBackground(new Color(255, 212, 164));
        panelInfosVictoire.setPreferredSize(new Dimension(350,150));
        panelInfosVictoire.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        panelInfosVictoire.setLayout(new GridBagLayout());
		GridBagConstraints contrainte2= new GridBagConstraints();
		contrainte2.gridx=0; contrainte2.gridy=0;
        
        labelVictoire = new JLabel("Victoire du joueur 1",SwingConstants.CENTER);
        labelVictoire.setFont(new Font("Tempus Sans ITC", Font.BOLD, 25));
		labelVictoire.setForeground(new Color(109,7,26));
        panelInfosVictoire.add(labelVictoire,contrainte2);

        labelNomVainqueur = new JLabel("pas Marwane",SwingConstants.CENTER);
        labelNomVainqueur.setFont(new Font("Tempus Sans ITC", Font.BOLD, 25));
		labelNomVainqueur.setForeground(new Color(109,7,26));
        contrainte2.gridy++;
        panelInfosVictoire.add(labelNomVainqueur,contrainte2);

        JLabel labelFelicitation = new JLabel("Felicitation !!",SwingConstants.CENTER);
        labelFelicitation.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		labelFelicitation.setForeground(new Color(109,7,26));
        contrainte2.gridy++;
        panelInfosVictoire.add(labelFelicitation,contrainte2);
        
    
        panelInfosVictoire.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0)));
        this.add(panelInfosVictoire,contrainte);

        /**
         * Bouton retour Menu
         * */ 
        boutonQuitter = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter.png", boutonQuitter);
        boutonQuitter.setActionCommand("retourMenu");
        contrainte.gridy++;

        this.add(boutonQuitter,contrainte);
    }

    /**
     * enregistreEcouteur permet de mettre a l'ecoute le boutonQuitter
     * @param controleur Jeu
     * */
    public void enregistreEcouteur(Jeu controleur) {
        boutonQuitter.addActionListener(controleur);
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

    /**
     * setImageBouton permet d'afficher une image dans le bouton
     * @param filePathName le chemin de l'image à utiliser
     * @param btnAModifier le bouton à modifier
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

    // Getters et setters
    
    public BorderLayout getBdl() {
        return this.bdl;
    }

    public void setBdl(BorderLayout bdl) {
        this.bdl = bdl;
    }

    public JPanel getPanelInfosVictoire() {
        return this.panelInfosVictoire;
    }

    public void setPanelInfosVictoire(JPanel panelInfosVictoire) {
        this.panelInfosVictoire = panelInfosVictoire;
    }

    public JLabel getLabelVictoire() {
        return this.labelVictoire;
    }

    public void setLabelVictoire(JLabel labelVictoire) {
        this.labelVictoire = labelVictoire;
    }

    public JLabel getLabelNomVainqueur() {
        return this.labelNomVainqueur;
    }

    public void setLabelNomVainqueur(JLabel labelNomVainqueur) {
        this.labelNomVainqueur = labelNomVainqueur;
    }

    public JButton getBoutonQuitter() {
        return this.boutonQuitter;
    }

    public void setBoutonQuitter(JButton boutonQuitter) {
        this.boutonQuitter = boutonQuitter;
    }
    
    // FIN Getters et setterss
    
}
