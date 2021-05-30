package Vue;

import java.awt.Insets;
import java.io.IOException;

import javax.swing.JPanel;

import controleur.Jeu;

/**
 * La classe PanelMap herite de JPanel et permet d'afficher le plateau de jeu avec :
 * - des cases Hexagonales
 * - le Layout prenu a cette effet (HexagonalLayout)
 */
public class PanelMap extends JPanel {

	private Hexagone[][] hexs;
	private Integer totalCells;
	private boolean petiteLigne;
	
    /**
	 * Le contructeur PanelMap permet d'instancier le JPanel avec comme parametre le tableau contenant les cases du plateau
	 * @param parHexs Hexagone[][]
	 * @throws IOException
	 */
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
			this.add(hexs[ligne][col]);
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

	/**
	 * Le getter getCells permet de recuperer toute les cases du plateau
	 * @return
	 */
    public Hexagone[][] getCells() {
		return hexs;
	}

	/**
	 * La methode enregistreEcouteur met a l'ecoute toutes les cases du panel pour le controleur
	 * @param controleur controleur que l'on souhaite mettre a l'ecoute
	 */
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
