package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
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

public class PanelChargerScenario extends JPanel{
    private PanelMap panelMap;
    private JPanel panelGauche;
    private JComboBox<TypeTerrain> listeTerrains;
    private JLabel terrainChoisi, monumentChoisi, nbMonumentLabel;
    private JButton btnChoixMonument, btnLancerPartie, btnQuitter;
    private Integer nbMonumentsRestants;
    private JButton btnSauvegarderPartie;
    private JTextField nomSauvergardeCarte;
	
	public PanelChargerScenario(Hexagone[][] parHexs) throws IOException {
        super(new BorderLayout());
        panelMap = new PanelMap(parHexs);
        panelGauche = new JPanel();
        nbMonumentsRestants = 6;
        nbMonumentLabel = new JLabel(nbMonumentsRestants+" monuments restants");
        JPanel panelTerrains = new JPanel();
        JPanel panelMonuments = new JPanel();
        JPanel panelActions = new JPanel();
        btnChoixMonument = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Monument.png", btnChoixMonument);
        btnLancerPartie = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"LancerPartie.png", btnLancerPartie);
        nomSauvergardeCarte = new JTextField(10);
        btnSauvegarderPartie = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"SauvegarderCarte.png", btnSauvegarderPartie);
        btnQuitter = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter2.png", btnQuitter);


        TypeTerrain[] listeTerrainsNoms = {TypeTerrain.NEIGE,TypeTerrain.DESERT,TypeTerrain.FORET,TypeTerrain.MONTAGNE,TypeTerrain.PLAINE,TypeTerrain.MER};
        listeTerrains = new JComboBox<TypeTerrain>(listeTerrainsNoms);
        listeTerrains.setActionCommand("listeTerrains");
        terrainChoisi = new JLabel("<html><br/>"+listeTerrainsNoms[0].toString()+" <br/>selectionné</html>");
        //terrainChoisi.setIcon(new ImageIcon(new ImageIcon("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+"MER.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        panelTerrains.add(listeTerrains);
        panelTerrains.add(terrainChoisi);
        panelTerrains.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0)));
        Border border = panelTerrains.getBorder();
        Border margin = new EmptyBorder(3,3,3,3);
        panelTerrains.setBorder(new CompoundBorder(margin, border));

        btnChoixMonument.setActionCommand("choixMonument");
        monumentChoisi = new JLabel("");
        panelMonuments.add(btnChoixMonument);
        panelMonuments.add(monumentChoisi);
        panelMonuments.add(nbMonumentLabel);
        panelMonuments.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0)));
        border = panelMonuments.getBorder();
        margin = new EmptyBorder(3,3,3,3);
        panelMonuments.setBorder(new CompoundBorder(margin, border));
        
        panelGauche.setBorder(new CompoundBorder(margin, border));
        panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.Y_AXIS));
        panelGauche.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0,0,0)));
        panelGauche.setPreferredSize(new Dimension(150,100));
        border = panelGauche.getBorder();
        margin = new EmptyBorder(3,3,3,1);
        panelGauche.setBorder(new CompoundBorder(margin, border));


        btnLancerPartie.setActionCommand("lancerPartieApresScenario");
        btnSauvegarderPartie.setActionCommand("sauvegarderCarte");
        btnQuitter.setActionCommand("retourMenu");
        panelActions.add(btnLancerPartie);
        panelActions.add(nomSauvergardeCarte);
        panelActions.add(btnSauvegarderPartie);
        panelActions.add(btnQuitter);
        
        panelGauche.add(panelTerrains);
        panelGauche.add(panelMonuments);
        panelGauche.add(panelActions);
        this.add(panelMap,BorderLayout.CENTER);
        this.add(panelGauche,BorderLayout.WEST);
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
        panelMap.enregistreEcouteur(controleur);
        listeTerrains.addActionListener(controleur);
        btnChoixMonument.addActionListener(controleur);
        btnLancerPartie.addActionListener(controleur);
        btnSauvegarderPartie.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
    }

    public void setChoixTerrainTxt(String txt) {
        this.terrainChoisi.setText("<html><br/>"+txt+" <br/>selectionné</html>");
        this.monumentChoisi.setText("");
    }

    public void setChoixMonumentTxt(String txt) {
        this.monumentChoisi.setText(txt);
        this.terrainChoisi.setText("");
    }

    public void setMonumentNb(boolean diminuer) {
        if (diminuer)          
            this.nbMonumentsRestants--;
        else 
            this.nbMonumentsRestants++;
        this.nbMonumentLabel.setText(nbMonumentsRestants+" monuments restants");
    }

    public Integer getNbMonumentsRestants(){
        return this.nbMonumentsRestants;
    }

    public JTextField getNomCarte(){
        return this.nomSauvergardeCarte;
    }
}
