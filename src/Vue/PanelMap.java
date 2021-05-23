package Vue;

import java.awt.Insets;
import java.io.IOException;

import javax.swing.JPanel;

import controleur.Jeu;

public class PanelMap extends JPanel {

	private static Hexagone[][] cells;
	private Integer totalCells;
	private boolean petiteLigne;
    
    public PanelMap() throws IOException {
        super();
		petiteLigne = false;
		HexagonalLayout hex = new HexagonalLayout(20, new Insets(1,1,1,1), petiteLigne, 254);
		System.out.println(hex.getRows());
		cells = new Hexagone[20][14]; // 20 colonnes 13 lignes
		totalCells = hex.getNbComposants();
		this.setLayout(hex);
		int ligne=0,col=0;
		MyMouseListener mListener = new MyMouseListener(this);
		for(int nbCellules = 0; nbCellules < totalCells; nbCellules++) {
			Hexagone cell = new Hexagone();
			ligne++;
			if (ligne%20==0 && !petiteLigne) {
				ligne=0;
				col++;
				petiteLigne = !petiteLigne;
			} else if (ligne%19==0 && petiteLigne) {
				ligne=0;
				col++;
				petiteLigne = !petiteLigne;
			}
			//System.out.println(String.valueOf(tmpSommeCellules)+","+String.valueOf(col));
			cells[ligne][col] = cell;
			//cell.setBackground(Color.blue);
			//cell.setForeground(Color.RED);
			cell.addMouseListener(mListener);
			this.add(cells[ligne][col]);
		}
    }

    public Hexagone[][] getCells() {
		return cells;
	}

	public void setCells(Hexagone[][] parCells) throws IOException {
		cells = parCells;
		this.removeAll();
		int ligne=0,col=0;
		MyMouseListener mListener = new MyMouseListener(this);
		for(int nbCellules = 0; nbCellules < totalCells; nbCellules++) {
			Hexagone cell = new Hexagone();
			ligne++;
			if (ligne%20==0 && !petiteLigne) {
				ligne=0;
				col++;
				petiteLigne = !petiteLigne;
			} else if (ligne%19==0 && petiteLigne) {
				ligne=0;
				col++;
				petiteLigne = !petiteLigne;
			}
			//System.out.println(String.valueOf(tmpSommeCellules)+","+String.valueOf(col));
			cells[ligne][col] = cell;
			//cell.setBackground(Color.blue);
			//cell.setForeground(Color.RED);
			cell.addMouseListener(mListener);
			this.add(cells[ligne][col]);
		}
		this.updateUI();
	}

	public void enregistreEcouteur(Jeu controleur) {
		MyMouseListener mListener = new MyMouseListener(controleur);
		for (Hexagone[] hexagones : cells) {
			for (Hexagone hexagone : hexagones) {
				hexagone.addMouseListener(mListener);
			}
		}
	}
}
