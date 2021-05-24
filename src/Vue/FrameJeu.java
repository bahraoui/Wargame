package Vue;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Jeu;


public class FrameJeu extends JFrame{

	private static PanelJeu panelJeu;
	private static PanelMenu panelMenu;
	private static PanelNouvellePartie panelNouvellePartie;
	private static PanelRegles panelRegles;
	private static PanelChargerPartie panelChargerPartie;
	private static PanelChargerScenario panelChargerScenario;
	private static PanelActuel panelActuel;
	//REGLES,CHARGERPARTIE,CHANGERSCENARIO

	public FrameJeu(PanelJeu parPj) throws IOException, InterruptedException {
		super("Wargame");		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelActuel = PanelActuel.MENU;

		
		panelJeu = parPj;
		panelMenu = new PanelMenu();
		panelNouvellePartie = new PanelNouvellePartie();
		panelChargerPartie = new PanelChargerPartie();
		panelRegles = new PanelRegles();
		this.getContentPane().setBackground(Color.cyan);
		this.add(panelMenu);
		this.setPreferredSize(new Dimension(1500,900));
		this.setVisible(true);	this.setSize(1500,900);
		this.setLocation(50, 50);	this.setResizable(true);
	}

	public void enleverPanel(PanelActuel panelVoulu) {		
		switch (panelVoulu) {
			case MENU:
				this.remove(panelMenu);
				break;
			case CHANGERSCENARIO:
				this.remove(panelChargerScenario);
				break;
			case NOUVELLEPARTIE:
				this.remove(panelNouvellePartie);
				break;
			case CHARGERPARTIE:
				this.remove(panelChargerPartie);
				break;
			case JEU:
				this.remove(panelJeu);
				break;
			case REGLES:
				this.remove(panelRegles);
				break;
		
			default:
				break;
		}
	}

	public void changePanel(PanelActuel panelVoulu) {
		System.out.println(panelVoulu.toString());
		System.out.println(panelActuel.toString());
		enleverPanel(panelActuel);
		switch (panelVoulu) {
			case MENU:
				this.add(panelMenu);
				break;
			case CHANGERSCENARIO:
				this.add(panelChargerScenario);
				break;
			case NOUVELLEPARTIE:
				this.add(panelNouvellePartie);
				break;
			case CHARGERPARTIE:
				this.add(panelChargerPartie);
				break;
			case JEU:
				this.add(panelJeu);
				break;
			case REGLES:
				this.add(panelRegles);
				break;
		
			default:
				break;
		}
		panelActuel = panelVoulu;
		this.pack();
		this.repaint();
		
	}

	public void enregistreEcouteur(Jeu controleur) {
		panelMenu.enregistreEcouteur(controleur);
		panelNouvellePartie.enregistreEcouteur(controleur);
		panelChargerPartie.enregistreEcouteur(controleur);
		panelRegles.enregistreEcouteur(controleur);
	}
	
	public JPanel getPanelChargerPartie(){
        return panelChargerPartie;
    }
}