package Vue;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
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
	private static PanelVictoire panelVictoire;
	//REGLES,CHARGERPARTIE,CHANGERSCENARIO

	public FrameJeu() throws IOException, InterruptedException {
		super("Wargame");		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelActuel = PanelActuel.MENU;

		
		panelMenu = new PanelMenu();
		panelNouvellePartie = new PanelNouvellePartie();
		panelChargerPartie = new PanelChargerPartie();
		//panelChargerScenario = new PanelChargerScenario();
		panelRegles = new PanelRegles();
		panelVictoire = new PanelVictoire();
		this.getContentPane().setBackground(Color.cyan);
		this.add(panelMenu);
		this.setPreferredSize(new Dimension(1500,800));
		this.setVisible(true);	this.setSize(1500,800);
		this.setLocation(20, 15);	this.setResizable(true);
		
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
			case VICTOIRE:
				this.remove(panelVictoire);
				break;
		
			default:
				break;
		}
	}

	public void changePanel(PanelActuel panelVoulu) {
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
			case VICTOIRE:
				this.add(panelVictoire);
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
		//panelChargerScenario.enregistreEcouteur(controleur);
		panelRegles.enregistreEcouteur(controleur);
		panelVictoire.enregistreEcouteur(controleur);
	}


	public void setChoixTerrainTxt(String txt) {
        panelChargerScenario.setChoixTerrainTxt(txt);
    }

	public void setChoixMonumentTxt(String txt) {
        panelChargerScenario.setChoixMonumentTxt(txt);
    }
	
	public JPanel getPanelChargerPartie(){
        return panelChargerPartie;
    }

    public void setPanelJeu(PanelJeu pj) {
		FrameJeu.panelJeu = pj;
    }
	public PanelActuel getPanelActuel(){
		return FrameJeu.panelActuel;
	}

	public PanelChargerScenario getPanelChangerScenario() {
		return FrameJeu.panelChargerScenario;
	}

	public void setPanelChangerScenario(PanelChargerScenario parPanelChargerScenario) {
		FrameJeu.panelChargerScenario = parPanelChargerScenario;
	}

    public PanelNouvellePartie getPanelNouvellePartie() {
        return FrameJeu.panelNouvellePartie;
    }
	public PanelJeu getPanelJeu(){
		return FrameJeu.panelJeu;
	}

}