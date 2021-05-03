package Vue;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class FrameJeu extends JFrame{
	
	
	private static final int COLUMNS = 20;
	private PanelJeu pj;
	
	public FrameJeu() throws IOException {
		super("Wargame");
		int totalCells=254;
		
		BorderLayout bdl = new BorderLayout();
		boolean petiteLigne = false;
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		HexagonalLayout hex = new HexagonalLayout(COLUMNS, new Insets(1,1,1,1), petiteLigne, totalCells);
		Cellule[][] cells = new Cellule[COLUMNS][hex.getRows()+1];
		pj = new PanelJeu(hex,cells);
		this.getContentPane().setBackground(Color.cyan);
		this.setLayout(bdl);
		this.add(pj,BorderLayout.CENTER);
		this.add(new JLabel("Nord"),BorderLayout.NORTH);
		this.add(new JLabel("Sud"),BorderLayout.SOUTH);
		this.add(new JLabel("Est"),BorderLayout.EAST);
		this.add(new JLabel("Ouest"),BorderLayout.WEST);
		this.setVisible(true);	this.setSize(1500,900);
		this.setLocation(50, 50);	this.setResizable(true);
	}
	public static void main(String[] args) throws IOException {
		new FrameJeu();
	}
	
	// public initGUI() {}
}