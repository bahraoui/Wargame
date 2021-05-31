package Vue;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import controleur.Jeu;

/**
 * La classe PanelVictoire herite de la classe JPanel permet d'afficher le victoire d'un joueur
 */
public class PanelVictoire extends JPanel{

    private BorderLayout bdl;
    private JPanel panelInfosVictoire;
    private JLabel labelVictoire;
    private JLabel labelNomVainqueur;
    private JButton boutonQuitter;

    /**
     * Le constructeur de PanelVictoire permet d'intancier ce JPanel
     */
    public PanelVictoire() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Informations sur le vainqueur
        panelInfosVictoire = new JPanel();
        panelInfosVictoire.setOpaque(false);
        
        panelInfosVictoire.setLayout(new BoxLayout(panelInfosVictoire, BoxLayout.Y_AXIS));
        panelInfosVictoire.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        
        labelVictoire = new JLabel("Victoire du joueur ", SwingConstants.CENTER);
        panelInfosVictoire.add(labelVictoire);

        labelNomVainqueur = new JLabel("", SwingConstants.CENTER);
        panelInfosVictoire.add(labelNomVainqueur);

        JLabel labelFelicitation = new JLabel("Felicitation !!", SwingConstants.CENTER);
        panelInfosVictoire.add(labelFelicitation);
        
        // placement au centre de l'ecran
        panelInfosVictoire.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0)));
        Border border = panelInfosVictoire.getBorder();
        Border margin = new EmptyBorder(370,450,0,0); // N W S E
        panelInfosVictoire.setBorder(new CompoundBorder(margin, border));
        this.add(panelInfosVictoire);

        // Bouton retour Menu
        JPanel panelBas = new JPanel();
        panelBas.setOpaque(false);
        boutonQuitter = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter.png", boutonQuitter);
        boutonQuitter.setActionCommand("retourMenu");
        panelBas.add(boutonQuitter);
        this.add(panelBas);
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
