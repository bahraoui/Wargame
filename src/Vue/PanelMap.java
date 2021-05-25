package Vue;

import java.awt.Insets;
import java.io.IOException;

import javax.swing.JPanel;

import controleur.Jeu;

public class PanelMap extends JPanel {

	private Hexagone[][] cells;
	private Integer totalCells;
	private boolean petiteLigne;
    
    public PanelMap() throws IOException {
        super();
		petiteLigne = false;
		HexagonalLayout hex = new HexagonalLayout(20, new Insets(1,1,1,1), petiteLigne, 254);
		cells = new Hexagone[20][14]; // 20 dimension 1 ### 13 dimension 2
		totalCells = hex.getNbComposants();
		this.setLayout(hex);
		int col=0,ligne=0;
		System.out.println("totalcells : "+totalCells);
		for(int nbCellules = 0; nbCellules < totalCells; nbCellules++) {
			cells[col][ligne] = new Hexagone();
			this.add(cells[col][ligne]);
			col++;
			if (col%20==0 && !petiteLigne) {
				col=0;
				ligne++;
				petiteLigne = !petiteLigne;
			} else if (col%19==0 && petiteLigne) {
				col=0;
				ligne++;
				petiteLigne = !petiteLigne;
			}
		}
    }

    public Hexagone[][] getCells() {
		return cells;
	}

	public void setCells(Hexagone[][] parCells) throws IOException {
		cells = parCells;
		this.removeAll();
		int col=0,ligne=0;
		for(int nbCellules = 0; nbCellules < totalCells; nbCellules++) {
			Hexagone cell = new Hexagone();
			col++;
			if (col%20==0 && !petiteLigne) {
				col=0;
				ligne++;
				petiteLigne = !petiteLigne;
			} else if (col%19==0 && petiteLigne) {
				col=0;
				ligne++;
				petiteLigne = !petiteLigne;
			}
			cells[col][ligne] = cell;
			this.add(cells[col][ligne]);
		}
		this.updateUI();
	}

	public void enregistreEcouteur(Jeu controleur) {
		for (Hexagone[] hexagones : cells) {
			for (Hexagone hexagone : hexagones) {
				if (hexagone != null) {
					hexagone.addMouseListener(controleur);
				}
			}
		}
	}
}
