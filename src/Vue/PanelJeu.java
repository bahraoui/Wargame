package Vue;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JPanel;


public class PanelJeu extends JPanel{
    private BorderLayout bdl;
    private JPanel panelCentre;
    private HexagonalLayout hex;

    public PanelJeu(int nbColonnes, int nbCells) {
        super();
        this.bdl = new BorderLayout();
        setLayout(bdl);
        this.panelCentre = new JPanel();
		this.hex = new HexagonalLayout(nbColonnes, new Insets(0,0,0,0), true, nbCells);
        this.panelCentre.setLayout(hex);
    }

    public PanelJeu(int nbColonnes, int nbCells, boolean petiteLigne) {
        super();
        this.bdl = new BorderLayout();
        setLayout(bdl);
        this.panelCentre = new JPanel();
		this.hex = new HexagonalLayout(nbColonnes, new Insets(0,0,0,0), petiteLigne, nbCells);
        this.panelCentre.setLayout(hex);
    }

}
