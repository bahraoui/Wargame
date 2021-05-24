package Vue;

import java.awt.BorderLayout;
import java.io.IOException;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.JPanel;


public class PanelJeu extends JPanel{
	private BorderLayout bdl;
	private Hexagone[][] cells;
	private JPanel panelCentre;
	
	public PanelJeu() throws IOException {
		super();
		this.bdl = new BorderLayout();
		setLayout(bdl);
		this.panelCentre = new JPanel();
		boolean petiteLigne = false;
		HexagonalLayout hex = new HexagonalLayout(20, new Insets(1,1,1,1), petiteLigne, 254);
		System.out.println(hex.getRows());
		cells = new Hexagone[20][14]; // 20 colonnes 13 lignes
		int totalCells = hex.getNbComposants();
		this.panelCentre.setLayout(hex);
		int ligne=0,col=0;
		MyMouseListener mListener = new MyMouseListener(this.panelCentre);
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
			cell.setBackground(Color.blue);
			cell.setForeground(Color.RED);
			cell.addMouseListener(mListener);
			this.panelCentre.add(cells[ligne][col]);
		}
		this.add(panelCentre,BorderLayout.CENTER);
	}



	public Hexagone[][] getCells() {
		return cells;
	}

	public void setCells(Hexagone[][] parCells) {
		cells = parCells;
	}

	
}
