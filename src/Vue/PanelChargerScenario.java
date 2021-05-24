package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import controleur.Jeu;

public class PanelChargerScenario extends JPanel{
    private PanelMap panelMap;
    private Hexagone[] terrains;
    private JPanel panelGauche;
    private JPanel panelTerrains;
	
	public PanelChargerScenario() throws IOException {
        super(new BorderLayout());
        panelMap = new PanelMap();
        panelGauche = new JPanel();
        panelTerrains = new JPanel();
        JPanel panelMonuments = new JPanel();

        HexagonalLayout hex = new HexagonalLayout(2, new Insets(1,1,1,1), false, 6);
        hex.setRows(6);
        terrains = new Hexagone[6];
        panelTerrains.setLayout(hex);
        Sol[] sols = {Sol.DESERT,Sol.FORET,Sol.MONTAGNE,Sol.NEIGE,Sol.PLAINE,Sol.MER};
        for (int i = 0; i < terrains.length; i++) {
            terrains[i] = new Hexagone();
            panelTerrains.add(terrains[i]);
        }

        panelGauche.setPreferredSize(new Dimension(150,100));
        panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.PAGE_AXIS));
        panelGauche.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0)));
        
        panelGauche.add(panelTerrains);
        this.add(panelMap,BorderLayout.CENTER);
        this.add(panelGauche,BorderLayout.WEST);
	}

    public void enregistreEcouteur(Jeu controleur) {
        panelMap.enregistreEcouteur(controleur);
    }
}
