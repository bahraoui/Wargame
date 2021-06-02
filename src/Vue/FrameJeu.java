package Vue;


import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controleur.Jeu;

/**
 * Fenêtre principale du jeu.
 * 
 * Cette classe est une fenêtre contient tous les menus ainsi que la carte du jeu.
 */
public class FrameJeu extends JFrame{

	private static PanelJeu panelJeu;
	private static PanelMenu panelMenu;
	private static PanelNouvellePartie panelNouvellePartie;
	private static PanelRegles panelRegles;
	private static PanelChargerPartie panelChargerPartie;
	private static PanelChangerScenario panelChargerScenario;
	private static PanelActuel panelActuel;
	private static PanelVictoire panelVictoire;

	/**
	 * Crée la fenêtre principale du jeu.
	 * 
	 * Instancie tous les panels necessaires au bon deroulement de l'interface graphique : <br/>
	 * <ul>
	 * <li> {@link PanelJeu} </li>
	 * <li> {@link PanelMenu} </li>
	 * <li> {@link PanelNouvellePartie} </li>
	 * <li> {@link PanelRegles} </li>
	 * <li> {@link PanelChargerPartie} </li>
	 * <li> {@link PanelChangerScenario} </li>
	 * <li> {@link PanelVictoire} </li>
	 * </ul>
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public FrameJeu(){
		super("Wargame");
		panelActuel = PanelActuel.MENU;

		// instantation des différents panels 
		panelMenu = new PanelMenu();
		panelNouvellePartie = new PanelNouvellePartie();
		panelChargerPartie = new PanelChargerPartie();
		panelRegles = new PanelRegles();
		panelVictoire = new PanelVictoire();

		// parametrage de la fenetre
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.cyan);
		this.add(panelMenu);
		this.setPreferredSize(new Dimension(1500,800));
		this.setVisible(true);	
		this.setSize(1500,800);
		this.setLocation(20, 15);	
		this.setResizable(false);
	}

	/**
	 * Enleve le panel donné en paramètre de la fenêtre.
	 * @param panelVoulu Le panel à enlever.
	 */
	private void enleverPanel(PanelActuel panelVoulu) {		
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

	/**
	 * Ajoute le panel donnée en paramètre à la fenêtre.
	 * @param panelVoulu Le panel à ajouter.
	 */
	public void changerPanel(PanelActuel panelVoulu) {
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

	/**
	 * enregistre_ecouteur met à l'écoute tous les panels de la fenêtre.
	 * @param controleur Jeu
	 */
	public void enregistreEcouteur(Jeu controleur) {
		panelMenu.enregistrerEcouteur(controleur);
		panelNouvellePartie.enregistrerEcouteur(controleur);
		panelChargerPartie.enregistrerEcouteur(controleur);
		panelRegles.enregistrerEcouteur(controleur);
		panelVictoire.enregistrerEcouteur(controleur);
	}

	// Getters et setters :

	public void setChoixTerrainTxt(String txt) {
        panelChargerScenario.setChoixTerrain_texte(txt);
    }

	public void setChoixMonumentTxt(String txt) {
        panelChargerScenario.setChoixMonumentTexte(txt);
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

	public PanelChangerScenario getPanelChangerScenario() {
		return FrameJeu.panelChargerScenario;
	}

	public void setPanelChangerScenario(PanelChangerScenario parPanelChargerScenario) {
		FrameJeu.panelChargerScenario = parPanelChargerScenario;
	}

    public PanelNouvellePartie getPanelNouvellePartie() {
        return FrameJeu.panelNouvellePartie;
    }
	public PanelJeu getPanelJeu(){
		return FrameJeu.panelJeu;
	}

	public PanelVictoire getPanelVictoire() {
        return FrameJeu.panelVictoire;
    }

	// FIN Getters et setters
}