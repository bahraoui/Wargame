package Vue;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Jeu;

import java.awt.BorderLayout;
import java.io.IOException;

public class PanelChargerScenario extends JPanel{
    private PanelMap panelMap;
	private static Hexagone[][] cells;
	
	public PanelChargerScenario() throws IOException {
        super(new BorderLayout());
        panelMap = new PanelMap();
        JPanel panelGauche1 = new JPanel();
        
        panelGauche1.setLayout(new BoxLayout(panelGauche1, BoxLayout.LINE_AXIS));
        panelGauche1.add(new JLabel("test 1"));
        panelGauche1.add(new JLabel("test 2"));
        
        
        this.add(panelMap,BorderLayout.CENTER);
        this.add(panelGauche1,BorderLayout.WEST);
	}

    public void enregistreEcouteur(Jeu controleur) {
        panelMap.enregistreEcouteur(controleur);
    }
}
