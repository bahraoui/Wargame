package Vue;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class HexTest extends JFrame{

	
	private static final int COLUMNS = 20;
	private static PanelJeu pj;
	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame("Wargame"); // fenetre principale
		int totalCells=254;
		
		BorderLayout bdl = new BorderLayout();
		boolean petiteLigne = false;
		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		HexagonalLayout hex = new HexagonalLayout(COLUMNS, new Insets(0,0,0,0), petiteLigne, totalCells);
		Cellule[][] cells = new Cellule[COLUMNS][hex.getRows()+1];
		pj = new PanelJeu(hex,cells);
		f.getContentPane().setBackground(Color.cyan);
		f.setLayout(bdl);
		f.add(pj,BorderLayout.CENTER);
		f.add(new JLabel("Nord"),BorderLayout.NORTH);
		f.add(new JLabel("Sud"),BorderLayout.SOUTH);
		f.add(new JLabel("Est"),BorderLayout.EAST);
		f.add(new JLabel("Ouest"),BorderLayout.WEST);
		f.setVisible(true);	f.setSize(1500,900);
		f.setLocation(50, 50);	f.setResizable(true);
		/*
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		System.out.println("Width = "+width+"\n Height = "+height);
		*/
	}
}