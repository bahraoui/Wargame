package Vue;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class HexTest extends JFrame{
	
	public static BufferedImage getTexturedImage(BufferedImage src, Shape shp, int x, int y) {
		Rectangle r = shp.getBounds();
		// create a transparent image with 1 px padding.
		BufferedImage tmp = new BufferedImage(r.width+2,r.height+2,BufferedImage.TYPE_INT_ARGB);
		// get the graphics object
		Graphics2D g = tmp.createGraphics();
		// set some nice rendering hints
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		// create a transform to center the shape in the image
		AffineTransform centerTransform = AffineTransform.getTranslateInstance(-r.x+1, -r.y+1);
		// set the transform to the graphics object
		g.setTransform(centerTransform);
		// set the shape as the clip
		g.setClip(shp);
		// draw the image
		g.drawImage(src, x, y, null);
		// clear the clip
		g.setClip(null);
		// draw the shape as an outline
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(1f));
		g.draw(shp);
		// dispose of any graphics object we explicitly create
		g.dispose();
		
		return tmp;
	}
	
	private static final int COLUMNS = 20;
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
		jpan.add(new JLabel(new ImageIcon(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Fonds"+File.separator+"MER.png")))));
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