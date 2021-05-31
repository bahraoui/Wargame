package Vue;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import controleur.Jeu;

/**
 * PanelChargerScenario contient la vue d'une modification de scénario.
 * 
 * A droite, se trouve la carte a modifier. A gauche, se trouve les elements pour pouvoir modifier la carte.
 */
public class PanelChargerScenario extends JPanel{
    private PanelMap panelMap;
    private JPanel panelGauche;
    private JComboBox<TypeTerrain> listeTerrains;
    private JLabel terrainChoisi, monumentChoisi, nbMonumentLabel;
    private JButton btnChoixMonument, btnLancerPartie, btnQuitter,btnSauvegarderPartie;
    private Integer nbMonumentsRestants;
    private JTextField nomSauvergardeCarte;
	
    /**
     * Contructeur du panel.
     * @param parHexs le tableau contenant les cases du plateau
     * @throws IOException
     */
	public PanelChargerScenario(Hexagone[][] parHexs) throws IOException {
        super(new BorderLayout());
        /*
         * Initialise tous les panels inclus dans la classe 
         */
        panelMap = new PanelMap(parHexs); // le panel de la carte
        panelGauche = new JPanel();
        nbMonumentsRestants = 6; // le nombre de monuments restants
        nbMonumentLabel = new JLabel(nbMonumentsRestants+" monuments restants"); // indique le nombre de monuments restants
        nbMonumentLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 13));
        nbMonumentLabel.setForeground(new Color(109,7,26));
        JPanel panelTerrains = new JPanel();
        JPanel panelMonuments = new JPanel();
        JPanel panelActions = new JPanel();

        /*
         *  Boutons du panel
         */
        btnChoixMonument = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Monument.png", btnChoixMonument);
        btnLancerPartie = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"LancerPartie.png", btnLancerPartie);
        nomSauvergardeCarte = new JTextField(10);
        btnSauvegarderPartie = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"SauvegarderCarte.png", btnSauvegarderPartie);
        btnQuitter = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter2.png", btnQuitter);

        /*
         * Liste des terrains disponibles a selectionner 
         */
        TypeTerrain[] listeTerrainsNoms = {TypeTerrain.NEIGE,TypeTerrain.DESERT,TypeTerrain.FORET,TypeTerrain.MONTAGNE,TypeTerrain.PLAINE,TypeTerrain.MER};
        listeTerrains = new JComboBox<TypeTerrain>(listeTerrainsNoms);
        listeTerrains.setRenderer(new MyComboboxRenderer());
        listeTerrains.setActionCommand("listeTerrains");
        terrainChoisi = new JLabel("<html><br/>"+listeTerrainsNoms[0].toString()+" <br/>selectionné</html>");
        terrainChoisi.setFont(new Font("Tempus Sans ITC", Font.BOLD, 13));
        terrainChoisi.setForeground(new Color(109,7,26));
        terrainChoisi.setIcon(new ImageIcon(new ImageIcon("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+"NEIGE.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        panelTerrains.add(listeTerrains);
        panelTerrains.add(terrainChoisi);
        panelTerrains.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0)));
        Border border = panelTerrains.getBorder();
        Border margin = new EmptyBorder(3,3,3,3);
        panelTerrains.setBorder(new CompoundBorder(margin, border));

        /*
         * Choix d'un monument 
         */
        btnChoixMonument.setActionCommand("choixMonument");
        monumentChoisi = new JLabel("");
        monumentChoisi.setFont(new Font("Tempus Sans ITC", Font.BOLD, 13));
        monumentChoisi.setForeground(new Color(109,7,26));
        panelMonuments.add(btnChoixMonument);
        panelMonuments.add(monumentChoisi);
        panelMonuments.add(nbMonumentLabel);
        panelMonuments.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0)));
        border = panelMonuments.getBorder();
        margin = new EmptyBorder(3,3,3,3);
        panelMonuments.setBorder(new CompoundBorder(margin, border));
        
        /*
         * placement des elements a gauche 
         */
        panelGauche.setBorder(new CompoundBorder(margin, border));
        panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.Y_AXIS));
        panelGauche.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0,0,0)));
        panelGauche.setPreferredSize(new Dimension(150,100));
        border = panelGauche.getBorder();
        margin = new EmptyBorder(3,3,3,1);
        panelGauche.setBorder(new CompoundBorder(margin, border));


        /*
         * placement des boutons 
         */
        btnLancerPartie.setActionCommand("lancerPartieApresScenario");
        btnSauvegarderPartie.setActionCommand("sauvegarderCarte");
        btnQuitter.setActionCommand("retourMenu");
        panelActions.add(btnLancerPartie);
        panelActions.add(nomSauvergardeCarte);
        panelActions.add(btnSauvegarderPartie);
        panelActions.add(btnQuitter);
        
        /*
         * placement de tous les panels 
         */
        panelGauche.add(panelTerrains);
        panelGauche.add(panelMonuments);
        panelGauche.add(panelActions);
        this.add(panelMap,BorderLayout.CENTER);
        this.add(panelGauche,BorderLayout.WEST);
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

    /**
	 * La methode enregistreEcouteur met a l'ecoute tous les elements du panel pour le controleur
	 * @param controleur controleur que l'on souhaite mettre a l'ecoute
	 */
    public void enregistreEcouteur(Jeu controleur) {
        panelMap.enregistreEcouteur(controleur);
        listeTerrains.addActionListener(controleur);
        btnChoixMonument.addActionListener(controleur);
        btnLancerPartie.addActionListener(controleur);
        btnSauvegarderPartie.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
    }

    /**
     * Modifie le texte du label associé au changement de terrrain.
     * 
     * Ce texte est formaté sous html afin de le rendre plus ergonomique et agréable.
     * @param txt le nouveau texte à inscrire
     */
    public void setChoixTerrainTxt(String txt) {
        this.terrainChoisi.setText("<html><br/>"+txt+" <br/>selectionné</html>");
        terrainChoisi.setIcon(new ImageIcon(new ImageIcon("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+txt+".jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));        
        this.monumentChoisi.setText("");
    }

    /**
     * Modifie le texte du label associé au changement de monument.
     * 
     * Ce texte est formaté sous html afin de le rendre plus ergonomique et agréable.
     * @param txt le nouveau texte à inscrire
     */
    public void setChoixMonumentTxt(String txt) {
        this.monumentChoisi.setText(txt);
        this.terrainChoisi.setText("");
    }

    /**
     * Incremente ou decremente le nombre de monuments placables en fonction du paramètre.
     * @param diminuer Decrementer le nombre ?
     */
    public void setMonumentNb(boolean diminuer) {
        if (diminuer)          
            this.nbMonumentsRestants--;
        else 
            this.nbMonumentsRestants++;
        this.nbMonumentLabel.setText(nbMonumentsRestants+" monuments restants");
    }

    public void setMonumentNb(int nbMonuments) {
        this.nbMonumentsRestants = nbMonuments;
        this.nbMonumentLabel.setText(nbMonumentsRestants+" monuments restants");
    }

    // Getters et setters

    public Integer getNbMonumentsRestants(){
        return this.nbMonumentsRestants;
    }

    public JTextField getNomCarte(){
        return this.nomSauvergardeCarte;
    }
    
    // Fin getters et setters

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
