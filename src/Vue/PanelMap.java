package Vue;

import java.awt.Insets;
import java.io.IOException;

import javax.swing.JPanel;

import controleur.Jeu;

public class PanelMap extends JPanel {

	private Hexagone[][] hexs;
	private Integer totalCells;
	private boolean petiteLigne;
	
    
    public PanelMap(Hexagone[][] parHexs) throws IOException {
        super();
		petiteLigne = false;
		HexagonalLayout hexLayout = new HexagonalLayout(16, new Insets(1,1,1,1), petiteLigne, 248);
		this.hexs = parHexs;
		totalCells = hexLayout.getNbComposants();
		this.setLayout(hexLayout);
		int col=0,ligne=0;
		System.out.println("totalcells : "+totalCells);
		for(int nbCellules = 0; nbCellules < totalCells; nbCellules++) {
			this.add(hexs[col][ligne]);
			col++;
			if (col%16==0 && !petiteLigne) {
				col=0;
				ligne++;
				petiteLigne = !petiteLigne;
			} else if (col%15==0 && petiteLigne) {
				col=0;
				ligne++;
				petiteLigne = !petiteLigne;
			}
		}
    }

    public Hexagone[][] getCells() {
		return hexs;
	}

	public void setCells(Hexagone[][] parCells) throws IOException {
		hexs = parCells;
		this.removeAll();
		int col=0,ligne=0;
		for(int nbCellules = 0; nbCellules < totalCells; nbCellules++) {
			Hexagone cell = new Hexagone();
			col++;
			if (col%16==0 && !petiteLigne) {
				col=0;
				ligne++;
				petiteLigne = !petiteLigne;
			} else if (col%15==0 && petiteLigne) {
				col=0;
				ligne++;
				petiteLigne = !petiteLigne;
			}
			hexs[col][ligne] = cell;
			this.add(hexs[col][ligne]);
		}
		this.updateUI();
	}

	public void enregistreEcouteur(Jeu controleur) {
		for (Hexagone[] hexagones : hexs) {
			for (Hexagone hexagone : hexagones) {
				if (hexagone != null) {
					hexagone.addMouseListener(controleur);
				}
			}
		}
	}
}
