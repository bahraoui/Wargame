package Vue;

import java.awt.BorderLayout;
import java.io.IOException;
import java.awt.Color;

import javax.swing.JPanel;


public class PanelJeu extends JPanel{
	private BorderLayout bdl;
	private JPanel panelCentre;
	private HexagonalLayout hex;
	
	public PanelJeu(HexagonalLayout parHex, Cellule[][] cells) throws IOException {
		super();
		this.bdl = new BorderLayout();
		setLayout(bdl);
		this.panelCentre = new JPanel();
		this.hex = parHex;
		boolean petiteLigne = this.hex.isBeginWithSmallRow();
		int totalCells = this.hex.getNbComposants();
		this.panelCentre.setLayout(hex);
		int ligne=0,col=0;
		MyMouseListener mListener = new MyMouseListener(this.panelCentre);
		for(int nbCellules = 0; nbCellules < totalCells; nbCellules++) {
			Cellule cell;
			if (petiteLigne)
			cell = new Cellule(new Point(ligne,col), Sol.PLAINE, Unite.ARCHER);
			else
			cell = new Cellule(new Point(ligne,col), Sol.PLAINE);
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
			
			this.panelCentre.add(cell);
		}
		this.add(panelCentre,BorderLayout.CENTER);
	}
	
}
