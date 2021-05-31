package Vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controleur.Jeu;

/**
 * La classe PanelJeu herite de JPanel et permet d'afficher :
 * - a gauche : toutes les informations relatives a la partie :
 * - au centre : le plateau de jeu 
 * - en bas : des boutons de commandes
 */
public class PanelJeu extends JPanel {
	private BorderLayout bdl;
	private PanelMap PanelCentrePlateau;
	private JPanel panelGaucheInfos,panelBoutons;
	private JPanel panelInfoTour;
	private JPanel panelInfoPartie;
	private JLabel labelNbTours;
	private JLabel labelNomJoueur;
	private int minute=2,seconde=0;
	private JLabel labelTypeTerrain;
	private JLabel labelBatimentUnite;
	private JPanel panelBoutique;
	private JLabel labelGolds;
	private JLabel labelArcher, labelCavalerie, labelInfanterie, labelInfanterieLourde, labelMage;
	private JButton boutonArcher, boutonCavalerie, boutonInfanterie, boutonInfanterieLourde, boutonMage;
	private JButton boutonFinDeTour, boutonAbandonner, boutonQuitter;
	private JLabel labelBonusTerrain;
	public static String str;
	private Timer timerTour;
	private Timer timerHorloge;
	private JButton boutonSauvegarder;
	private JLabel labelNomEntite;
	private JLabel labelPointVie;
	private JLabel labelAttaque;
	private JLabel labelDefense;
	private JLabel labelPointDeplacement;
	private JLabel labelPointDeplacementTerrain;

	/**
	 * Le contructeur PanelJeu permet d'instancier le JPanel avec comme parametre le tableau contenant les cases du plateau
	 * @param parHexs Hexagone[][]
	 * @throws IOException
	 */
	public PanelJeu(Hexagone[][] parHexs) throws IOException {
		super();
		this.bdl = new BorderLayout();
		this.setLayout(bdl);
		
		/* PANEL GAUCHE : toutes les inforamtions sur la partie */
		this.panelGaucheInfos = new JPanel();
		panelGaucheInfos.setPreferredSize(new Dimension(200,500));

		/////////////////////
		// PANEL INFO TOUR //
		/////////////////////
		panelInfoTour = new JPanel();
		panelInfoTour.setPreferredSize(new Dimension(200,60));
		panelInfoTour.setBounds(panelInfoTour.getBounds().x, panelInfoTour.getBounds().y, 400, 300);
		panelInfoTour.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelInfoTour.setLayout(new BoxLayout(panelInfoTour, BoxLayout.Y_AXIS));
		

		// Tours
		labelNbTours = new JLabel("Nombre de tour(s) : ");
		labelNbTours.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
        labelNbTours.setForeground(new Color(109,7,26));
		panelInfoTour.add(labelNbTours);

		// Nom du joueur
		labelNomJoueur = new JLabel("Tour de : ");
		labelNomJoueur.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
        labelNomJoueur.setForeground(new Color(109,7,26));
		panelInfoTour.add(labelNomJoueur);

				
		// Chrono
		JLabel labelChrono = new JLabel("02:00");
		labelChrono.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
        labelChrono.setForeground(new Color(109,7,26));
		str = new String("00:00");
		ActionListener tacheTimerHorloge = new ActionListener()
		{
			public void actionPerformed(ActionEvent e1)
			{
				if(seconde==0 && minute!=0)
				{
					seconde=60;
					minute--;
				}
				seconde--;
				str=minute+":";
				if(seconde<10)
					str+=("0");
				str+=seconde;
				labelChrono.setText(str);/* rafraichir le label */

			}
		};
		timerHorloge = new Timer(1000,tacheTimerHorloge);

		ActionListener tacheTimerTour = new ActionListener()
		{
			public void actionPerformed(ActionEvent e1)
			{	
				System.out.println("Changement de tour");
				str = "02:00";
				seconde = 0;
				minute = 2;
				labelChrono.setText(str);
				try {
					Jeu.nouveauTour();
				} catch (Exception e) {
					//TODO: handle exception
				}
			}
		};
		timerTour = new Timer(120000,tacheTimerTour);
		timerTour.start();
		timerHorloge.start();

		panelInfoTour.add(labelChrono);
		panelGaucheInfos.add(panelInfoTour);

		///////////////////////
		// PANEL INFO PARTIE //
		///////////////////////

		this.panelInfoPartie = new JPanel();
		panelInfoPartie.setPreferredSize(new Dimension(200,160));
		panelInfoPartie.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelInfoPartie.setLayout(new BoxLayout(panelInfoPartie, BoxLayout.Y_AXIS));
		
		// type de terrain
		labelTypeTerrain = new JLabel("");
        labelTypeTerrain.setForeground(new Color(109,7,26));
		labelTypeTerrain.setFont(new Font("",Font.PLAIN,20));
		panelInfoPartie.add(labelTypeTerrain);

		// Point de deplacement terrain
		labelPointDeplacementTerrain = new JLabel("");
		labelPointDeplacementTerrain.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelPointDeplacementTerrain);

		// bonus terrain
		labelBonusTerrain = new JLabel("");
        labelBonusTerrain.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelBonusTerrain);	
		// unite batiment
		labelBatimentUnite = new JLabel("");
		labelBatimentUnite.setFont(new Font("Tempus Sans ITC", Font.BOLD, 10));
        labelBatimentUnite.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelBatimentUnite);


		// nom entite
		labelNomEntite = new JLabel("");
		labelNomEntite.setFont(new Font("",Font.PLAIN,20));
		labelNomEntite.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelNomEntite);

		// point de vue
		labelPointVie = new JLabel("");
		labelPointVie.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelPointVie);

		// attaque
		labelAttaque = new JLabel("");
		labelAttaque.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelAttaque);

		// defense
		labelDefense = new JLabel("");
		labelDefense.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelDefense);

		// Point Deplacement
		labelPointDeplacement = new JLabel("");
		labelPointDeplacement.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelPointDeplacement);


		panelGaucheInfos.add(panelInfoPartie);

		////////////////////
		// PANEL BOUTIQUE //
		////////////////////
		this.panelBoutique = new JPanel();
		panelBoutique.setPreferredSize(new Dimension(200,270));
		panelBoutique.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		//panelBoutique.setLayout(new BoxLayout(panelBoutique, BoxLayout.Y_AXIS));

		// titre
		JLabel labelTitreBoutique = new JLabel("Boutique d'achats :");
		labelTitreBoutique.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		labelTitreBoutique.setForeground(new Color(109,7,26));
		panelBoutique.add(labelTitreBoutique);

		// golds
		labelGolds = new JLabel(" Golds");
		labelGolds.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		labelGolds.setForeground(new Color(109,7,26));
		panelBoutique.add(labelGolds);

		// liste d'achat
		JPanel panelListeAchat = new JPanel();
		panelListeAchat.setLayout(new GridBagLayout());
		GridBagConstraints contrainte= new GridBagConstraints();
		contrainte.gridx=0; contrainte.gridy=0;

			// Archer
		labelArcher = new JLabel("Archer");
		labelArcher.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		labelArcher.setForeground(new Color(109,7,26));
		panelListeAchat.add(labelArcher,contrainte);
		contrainte.gridx=1;
		boutonArcher = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonArcher.png", boutonArcher);
		boutonArcher.setActionCommand("achatArcher");
		panelListeAchat.add(boutonArcher,contrainte);

			// Cavalerie
		contrainte.gridx=0; contrainte.gridy=1;
		labelCavalerie = new JLabel("Cavalerie");
		labelCavalerie.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		labelCavalerie.setForeground(new Color(109,7,26));
		panelListeAchat.add(labelCavalerie,contrainte);
		contrainte.gridx=1;
		boutonCavalerie = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonCavalerie.png", boutonCavalerie);
		boutonCavalerie.setActionCommand("achatCavalerie");
		panelListeAchat.add(boutonCavalerie,contrainte);

			// Infanterie
		contrainte.gridx=0; contrainte.gridy=2;
		labelInfanterie = new JLabel("Infanterie");
		labelInfanterie.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		labelInfanterie.setForeground(new Color(109,7,26));
		panelListeAchat.add(labelInfanterie,contrainte);
		contrainte.gridx=1;
		boutonInfanterie = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonInfanterie.png", boutonInfanterie);
		boutonInfanterie.setActionCommand("achatInfanterie");
		panelListeAchat.add(boutonInfanterie,contrainte);

			// Infanterie Lourde
		contrainte.gridx=0; contrainte.gridy=3;
		labelInfanterieLourde = new JLabel("Infanterie Lourde");
		labelInfanterieLourde.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		labelInfanterieLourde.setForeground(new Color(109,7,26));
		panelListeAchat.add(labelInfanterieLourde,contrainte);
		contrainte.gridx=1;
		boutonInfanterieLourde = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonInfanterieLourde.png", boutonInfanterieLourde);
		boutonInfanterieLourde.setActionCommand("achatInfanterieLourde");
		panelListeAchat.add(boutonInfanterieLourde,contrainte);

			// Mage
		contrainte.gridx=0; contrainte.gridy=4;
		labelMage = new JLabel("Mage");
		labelMage.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		labelMage.setForeground(new Color(109,7,26));
		panelListeAchat.add(labelMage,contrainte);
		contrainte.gridx=1;
		boutonMage = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonMage.png", boutonMage);
		boutonMage.setActionCommand("achatMage");
		panelListeAchat.add(boutonMage,contrainte);

		panelBoutique.add(panelListeAchat);

		panelGaucheInfos.add(panelBoutique);

		////////////////////
		// PANEL BOUTIQUE //
		////////////////////
		panelBoutons = new JPanel();
		panelBoutons.setPreferredSize(new Dimension(200,300));		
			// fin de tour
		boutonFinDeTour = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"FinDeTour.png", boutonFinDeTour);
		boutonFinDeTour.setActionCommand("finTour");
		panelBoutons.add(boutonFinDeTour);

			// abandonner
		boutonAbandonner = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Abandonner.png", boutonAbandonner);
		boutonAbandonner.setActionCommand("abandonner");
		panelBoutons.add(boutonAbandonner);

			// sauvegarder
		boutonSauvegarder = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Sauvegarder.png", boutonSauvegarder);
		boutonSauvegarder.setActionCommand("sauvegarderPartie");
		panelBoutons.add(boutonSauvegarder);
		
			// quitter
		boutonQuitter = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter2.png", boutonQuitter);
		boutonQuitter.setActionCommand("retourMenu");
		panelBoutons.add(boutonQuitter);

		panelGaucheInfos.add(panelBoutons);

		this.add(panelGaucheInfos,BorderLayout.WEST);

		/* PANEL CENTRE : plateau de jeu */
		this.PanelCentrePlateau = new PanelMap(parHexs);
		this.add(PanelCentrePlateau,BorderLayout.CENTER);
	}

	/**
	 * La methode enregistreEcouteur met a l'ecoute tous les elements du panel pour le controleur
	 * @param controleur controleur que l'on souhaite mettre a l'ecoute
	 */
	public void enregistreEcouteur(Jeu controleur) {
        boutonFinDeTour.addActionListener(controleur);
		boutonAbandonner.addActionListener(controleur);
		boutonSauvegarder.addActionListener(controleur);
		boutonArcher.addActionListener(controleur);
		boutonCavalerie.addActionListener(controleur);
		boutonInfanterie.addActionListener(controleur);
		boutonInfanterieLourde.addActionListener(controleur);
		boutonMage.addActionListener(controleur);
		boutonQuitter.addActionListener(controleur);
	}


	/**
     * setImageBouton permet d'afficher une image dans le bouton
     * @param filePathName le chemin de l'image à utiliser
     * @param btnAModifier le bouton à modifier
     */
	private void setImageBouton(String filePathName,JButton btnAModifier){
        btnAModifier.setMargin(new Insets(0, 0, 0, 0));
        btnAModifier.setBorder(null);
        try {
            btnAModifier.setIcon(new ImageIcon(ImageIO.read(new File(filePathName))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * updateGoldJoueurAffichage permet de mettre a jour le nombre de golds du joueur sur le labelGolds
	 * @param nbGold
	 */
	public void updateGoldJoueurAffichage(int nbGold) {
        labelGolds.setText(nbGold+" Golds");
    }


	// Getters et setters

	public PanelMap getPanelCentrePlateau() {
		return this.PanelCentrePlateau;
	}

	public void setPanelCentrePlateau(PanelMap PanelCentrePlateau) {
		this.PanelCentrePlateau = PanelCentrePlateau;
	}

	public JLabel getLabelNbTours() {
		return this.labelNbTours;
	}

	public void setLabelNbTours(JLabel labelNbTours) {
		this.labelNbTours = labelNbTours;
	}

	public JLabel getLabelNomJoueur() {
		return this.labelNomJoueur;
	}

	public void setLabelNomJoueur(JLabel labelNomJoueur) {
		this.labelNomJoueur = labelNomJoueur;
	}

	public Timer getTimerTour() {
		return this.timerTour;
	}

	public void setTimerTour(Timer timerTour) {
		this.timerTour = timerTour;
	}

	public Timer getTimerHorloge() {
		return this.timerHorloge;
	}

	public void setTimerHorloge(Timer timerHorloge) {
		this.timerHorloge = timerHorloge;
	}

	public int getMinute() {
		return this.minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSeconde() {
		return this.seconde;
	}

	public void setSeconde(int seconde) {
		this.seconde = seconde;
	}

	public JLabel getLabelTypeTerrain() {
		return this.labelTypeTerrain;
	}

	public void setLabelTypeTerrain(JLabel labelTypeTerrain) {
		this.labelTypeTerrain = labelTypeTerrain;
	}
	
	public JLabel getLabelBonusTerrain() {
		return this.labelBonusTerrain;
	}

	public void setLabelBonusTerrain(JLabel labelBonusTerrain) {
		this.labelBonusTerrain = labelBonusTerrain;
	}

	public JLabel getLabelBatimentUnite() {
		return this.labelBatimentUnite;
	}

	public void setLabelBatimentUnite(JLabel labelBatimentUnite) {
		this.labelBatimentUnite = labelBatimentUnite;
	}

	public JLabel getLabelGolds() {
		return this.labelGolds;
	}

	public void setLabelGolds(JLabel labelGolds) {
		this.labelGolds = labelGolds;
	}


	public BorderLayout getBdl() {
		return this.bdl;
	}

	public void setBdl(BorderLayout bdl) {
		this.bdl = bdl;
	}

	public JPanel getPanelGaucheInfos() {
		return this.panelGaucheInfos;
	}

	public void setPanelGaucheInfos(JPanel panelGaucheInfos) {
		this.panelGaucheInfos = panelGaucheInfos;
	}

	public JPanel getPanelBoutons() {
		return this.panelBoutons;
	}

	public void setPanelBoutons(JPanel panelBoutons) {
		this.panelBoutons = panelBoutons;
	}

	public JPanel getPanelInfoTour() {
		return this.panelInfoTour;
	}

	public void setPanelInfoTour(JPanel panelInfoTour) {
		this.panelInfoTour = panelInfoTour;
	}

	public JPanel getPanelInfoPartie() {
		return this.panelInfoPartie;
	}

	public void setPanelInfoPartie(JPanel panelInfoPartie) {
		this.panelInfoPartie = panelInfoPartie;
	}

	public JPanel getPanelBoutique() {
		return this.panelBoutique;
	}

	public void setPanelBoutique(JPanel panelBoutique) {
		this.panelBoutique = panelBoutique;
	}

	public JLabel getLabelArcher() {
		return this.labelArcher;
	}

	public void setLabelArcher(JLabel labelArcher) {
		this.labelArcher = labelArcher;
	}

	public JLabel getLabelCavalerie() {
		return this.labelCavalerie;
	}

	public void setLabelCavalerie(JLabel labelCavalerie) {
		this.labelCavalerie = labelCavalerie;
	}

	public JLabel getLabelInfanterie() {
		return this.labelInfanterie;
	}

	public void setLabelInfanterie(JLabel labelInfanterie) {
		this.labelInfanterie = labelInfanterie;
	}

	public JLabel getLabelInfanterieLourde() {
		return this.labelInfanterieLourde;
	}

	public void setLabelInfanterieLourde(JLabel labelInfanterieLourde) {
		this.labelInfanterieLourde = labelInfanterieLourde;
	}

	public JLabel getLabelMage() {
		return this.labelMage;
	}

	public void setLabelMage(JLabel labelMage) {
		this.labelMage = labelMage;
	}

	public JButton getBoutonArcher() {
		return this.boutonArcher;
	}

	public void setBoutonArcher(JButton boutonArcher) {
		this.boutonArcher = boutonArcher;
	}

	public JButton getBoutonCavalerie() {
		return this.boutonCavalerie;
	}

	public void setBoutonCavalerie(JButton boutonCavalerie) {
		this.boutonCavalerie = boutonCavalerie;
	}

	public JButton getBoutonInfanterie() {
		return this.boutonInfanterie;
	}

	public void setBoutonInfanterie(JButton boutonInfanterie) {
		this.boutonInfanterie = boutonInfanterie;
	}

	public JButton getBoutonInfanterieLourde() {
		return this.boutonInfanterieLourde;
	}

	public void setBoutonInfanterieLourde(JButton boutonInfanterieLourde) {
		this.boutonInfanterieLourde = boutonInfanterieLourde;
	}

	public JButton getBoutonMage() {
		return this.boutonMage;
	}

	public void setBoutonMage(JButton boutonMage) {
		this.boutonMage = boutonMage;
	}

	public JButton getBoutonFinDeTour() {
		return this.boutonFinDeTour;
	}

	public void setBoutonFinDeTour(JButton boutonFinDeTour) {
		this.boutonFinDeTour = boutonFinDeTour;
	}

	public JButton getBoutonAbandonner() {
		return this.boutonAbandonner;
	}

	public void setBoutonAbandonner(JButton boutonAbandonner) {
		this.boutonAbandonner = boutonAbandonner;
	}

	public JButton getBoutonQuitter() {
		return this.boutonQuitter;
	}

	public void setBoutonQuitter(JButton boutonQuitter) {
		this.boutonQuitter = boutonQuitter;
	}

	public JButton getBoutonSauvegarder() {
		return this.boutonSauvegarder;
	}

	public void setBoutonSauvegarder(JButton boutonSauvegarder) {
		this.boutonSauvegarder = boutonSauvegarder;
	}

	public JLabel getLabelNomEntite() {
		return this.labelNomEntite;
	}

	public void setLabelNomEntite(JLabel labelNomEntite) {
		this.labelNomEntite = labelNomEntite;
	}

	public JLabel getLabelPointVie() {
		return this.labelPointVie;
	}

	public void setLabelPointVie(JLabel labelPointVie) {
		this.labelPointVie = labelPointVie;
	}

	public JLabel getLabelAttaque() {
		return this.labelAttaque;
	}

	public void setLabelAttaque(JLabel labelAttaque) {
		this.labelAttaque = labelAttaque;
	}

	public JLabel getLabelDefense() {
		return this.labelDefense;
	}

	public void setLabelDefense(JLabel labelDefense) {
		this.labelDefense = labelDefense;
	}

	public JLabel getLabelPointDeplacement() {
		return this.labelPointDeplacement;
	}

	public void setLabelPointDeplacement(JLabel labelPointDeplacement) {
		this.labelPointDeplacement = labelPointDeplacement;
	}



	public JLabel getLabelPointDeplacementTerrain() {
		return this.labelPointDeplacementTerrain;
	}

	public void setLabelPointDeplacementTerrain(JLabel labelPointDeplacementTerrain) {
		this.labelPointDeplacementTerrain = labelPointDeplacementTerrain;
	}


	// FIN Getters et setters

}
