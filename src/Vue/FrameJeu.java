package Vue;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class FrameJeu extends JFrame{
	
	
	private static final int COLUMNS = 20;
	private static PanelJeu pj;

	public FrameJeu(PanelJeu parPj) throws IOException, InterruptedException {
		super("Wargame");		
		BorderLayout bdl = new BorderLayout();		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pj = parPj;
		this.getContentPane().setBackground(Color.cyan);
		this.setLayout(bdl);
		this.add(pj,BorderLayout.CENTER);
		this.add(new JLabel("fleche Nord"),BorderLayout.NORTH);
		this.add(new JLabel("fleche Sud"),BorderLayout.SOUTH);
		this.add(new JLabel("fleche Est"),BorderLayout.EAST);
		this.add(new JLabel("fleche Ouest"),BorderLayout.WEST);
		this.setVisible(true);	this.setSize(1500,900);
		this.setLocation(50, 50);	this.setResizable(true);
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		new FrameJeu(new PanelJeu());
	}
	
	// public initGUI() {}
}