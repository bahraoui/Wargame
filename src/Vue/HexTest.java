package Vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class HexTest extends JFrame{

    private static final int COLUMNS = 20;
    public static void main(String[] args) {
		JFrame f = new JFrame();
		Point p;
		int tmpSommeCellules=0,col=0,totalCells=254;
		boolean petiteLigne = false;
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		HexagonalLayout hex = new HexagonalLayout(COLUMNS, new Insets(1,1,1,1), petiteLigne, totalCells);
		f.getContentPane().setBackground(Color.cyan);
		f.setLayout(hex);
		Cellule[][] cells = new Cellule[COLUMNS][hex.getRows()+1];
		System.out.println(String.valueOf(hex.getRows()));
		/*
		for(int ligne = 0; ligne < hex.getRows(); ligne++) {
			for(int colonne = 0; colonne < COLUMNS; colonne++){
				tmpSommeCellules++;
				if (tmpSommeCellules%19==0 && !petiteLigne) {
					tmpSommeCellules=1;
					petiteLigne = !petiteLigne;
				} else if (tmpSommeCellules%18==0 && petiteLigne) {
					tmpSommeCellules=1;
					petiteLigne = !petiteLigne;
				}
				p = new Point(colonne,ligne);
				Cellule cell = new Cellule(p, Sol.PLAINE);
				cells[ligne][colonne] = cell;
				cell.setBackground(Color.blue);
				cell.setForeground(Color.RED);


				//"Random" color actionlistener, just for fun.
				cell.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton a = (JButton) e.getSource();
					a.setBackground(Color.RED);
					a.setForeground(Color.BLUE);
					System.out.println(cell.toString());
				}

				});
				f.add(cell);
			}
		}
		*/
		for(int nbCells = 0; nbCells < totalCells; nbCells++) {
				p = new Point(tmpSommeCellules,col);
				tmpSommeCellules++;
				if (tmpSommeCellules%20==0 && !petiteLigne) {
					tmpSommeCellules=0;
					col++;
					petiteLigne = !petiteLigne;
				} else if (tmpSommeCellules%19==0 && petiteLigne) {
					tmpSommeCellules=0;
					col++;
					petiteLigne = !petiteLigne;
				}
				Cellule cell = new Cellule(p, Sol.PLAINE);
				System.out.println(String.valueOf(tmpSommeCellules)+","+String.valueOf(col));
				cells[tmpSommeCellules][col] = cell;
				cell.setBackground(Color.blue);
				cell.setForeground(Color.RED);


				//"Random" color actionlistener, just for fun.
				cell.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton a = (JButton) e.getSource();
					a.setBackground(Color.RED);
					a.setForeground(Color.BLUE);
					System.out.println(cell.toString());
				}

				});
				f.add(cell);
		}

/*
		for (int i = 0; i < 254; i++) {
			if (i%2==0) {
				p = new Point(i%COLUMNS,y);
			} else {
				p = new Point((i%COLUMNS)-1,y);
			}
			
			Cellule b = new Cellule(p, Sol.PLAINE);
			//b.setEmplacement(p);
			tab.add(b);
			b.setBackground(Color.blue);
			b.setForeground(Color.RED);


			//"Random" color actionlistener, just for fun.
			b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cellule a = (Cellule) e.getSource();
				a.setBackground(Color.RED);
				a.setForeground(Color.BLUE);
				System.out.println(b.toString());
			}

			});
			f.add(b);
		}
*/
		f.setVisible(true);
		f.setSize(1500,900);
		f.setLocation(50, 50);
		f.setResizable(true);/*
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		System.out.println("Width = "+width+"\n Height = "+height);*/
		// V2 :
		/*
		Cellule[][] cells = new Cellule[ROWS][COLUMNS];
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLUMNS; col++){

			}
		}*/
    }
}