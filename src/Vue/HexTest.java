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
	private PanelJeu pj;
	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame(); // fenetre principale
		int ligne=0,col=0,totalCells=254;
		boolean petiteLigne = false;
		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		HexagonalLayout hex = new HexagonalLayout(COLUMNS, new Insets(0,0,0,0), petiteLigne, totalCells);
		BorderLayout bdl = new BorderLayout();
		JPanel jpan = new JPanel(hex);
		JPanel jpan2 = new JPanel();
		System.out.println("assets"+File.separator+"images"+File.separator+"Fonds"+File.separator+"oui.png");
		JLabel jz = new JLabel(new ImageIcon(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Fonds"+File.separator+"sea.jpg"))));
		jz.setSize(1024, 576);
		jpan.add(jz);
		jpan.setComponentZOrder(jz, 0);
		System.out.println("assets"+File.separator+"images"+File.separator+"Fonds"+File.separator+"oui.png");
		f.getContentPane().setBackground(Color.cyan);
		f.setLayout(bdl);
		f.add(jpan,BorderLayout.CENTER);
		f.add(jpan2,BorderLayout.SOUTH);
		
		Cellule[][] cells = new Cellule[COLUMNS][hex.getRows()+1];
		System.out.println("nombre de lignes : "+String.valueOf(hex.getRows()));
		for(int nbCells = 0; nbCells < totalCells; nbCells++) {
			Point p = new Point(ligne,col);
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
			Cellule cell;
			if (petiteLigne) {
				cell = new Cellule(p, Sol.PLAINE, Unite.ARCHER);
			} else {
				cell = new Cellule(p, Sol.PLAINE);
			}
			//System.out.println(String.valueOf(tmpSommeCellules)+","+String.valueOf(col));
			cells[ligne][col] = cell;
			cell.setBackground(Color.blue);
			cell.setForeground(Color.RED);
			
			MyMouseListener mListener = new MyMouseListener(jpan);
			cell.addMouseListener(mListener);
			
			jpan.add(cell);
			jpan.setComponentZOrder(cell, 0);
		}
		
		f.setVisible(true);
		f.setSize(1500,900);
		f.setLocation(50, 50);
		f.setResizable(true);
		/*
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		System.out.println("Width = "+width+"\n Height = "+height);
		*/
	}
}