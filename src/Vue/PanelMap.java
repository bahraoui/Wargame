package Vue;

import java.awt.Insets;
import java.io.IOException;

import javax.swing.JPanel;

public class PanelMap extends JPanel {

	private static Hexagone[][] cells;
    
    public PanelMap() throws IOException {
        super();
		boolean petiteLigne = false;
		HexagonalLayout hex = new HexagonalLayout(20, new Insets(1,1,1,1), petiteLigne, 390);
		System.out.println(hex.getRows());
		cells = new Hexagone[20][21]; // 20 colonnes 13 lignes
		int totalCells = hex.getNbComposants();
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

	public void setCells(Hexagone[][] parCells) {
		cells = parCells;
	}
}
