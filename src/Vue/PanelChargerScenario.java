package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import controleur.Jeu;

public class PanelChargerScenario extends JPanel{
    private PanelMap panelMap;
    private JPanel panelGauche;
    private JComboBox<String> listeTerrains;
    private JLabel terrainChoisi;
	
	public PanelChargerScenario() throws IOException {
        super(new BorderLayout());
        panelMap = new PanelMap();
        panelGauche = new JPanel();
        JPanel panelTerrains = new JPanel();
        JPanel panelMonuments = new JPanel();

        String[] listeTerrainsNoms = {"ToundraNeige","Désert","Forêt","Montagne","Plaine","Mer"};
        listeTerrains = new JComboBox<String>(listeTerrainsNoms);
        listeTerrains.setActionCommand("listeTerrains");
        terrainChoisi = new JLabel(listeTerrainsNoms[0]);
        panelTerrains.add(listeTerrains);
        panelTerrains.add(terrainChoisi);
        panelTerrains.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0)));
        Border border = panelTerrains.getBorder();
        Border margin = new EmptyBorder(3,3,3,3);
        panelTerrains.setBorder(new CompoundBorder(margin, border));
        
        panelGauche.setBorder(new CompoundBorder(margin, border));
        panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.PAGE_AXIS));
        panelGauche.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0,0,0)));
        panelGauche.setPreferredSize(new Dimension(150,100));
        border = panelGauche.getBorder();
        margin = new EmptyBorder(3,3,3,1);
        panelGauche.setBorder(new CompoundBorder(margin, border));
        
        panelGauche.add(panelTerrains);
        panelGauche.add(panelMonuments);
        this.add(panelMap,BorderLayout.CENTER);
        this.add(panelGauche,BorderLayout.WEST);
	}

    public void enregistreEcouteur(Jeu controleur) {
        panelMap.enregistreEcouteur(controleur);
        listeTerrains.addActionListener(controleur);
    }

    public void setChoixTerrainTxt(String txt) {
        this.terrainChoisi.setText(txt);
    }
}
