package Vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controleur.Cellule;
import controleur.Jeu;

/**
 * La classe PanelJeu herite de JPanel et permet d'afficher toutes les informations relatives a la partie (a gauche), le plateau de jeu (au centre) et des boutons de commandes (en bas)
 */
public class PanelJeu extends JPanel {
	private BorderLayout bdl;
	private PanelMap PanelCentrePlateau;
	private JPanel panelGaucheInfos,panelBasBoutons;
	private JPanel panelInfoTour;
	private JPanel panelInfoPartie;
	private JLabel labelNbTours;
	private JLabel labelNomJoueur;
	private static int minute=0,seconde=0;
	private JLabel labelTypeTerrain;
	private JLabel labelBatimentUnite;
	private JPanel panelBoutique;
	private JLabel labelGolds;
	private JLabel labelArcher, labelCavalerie, labelInfanterie, labelInfanterieLourde, labelMage;
	private JButton boutonArcher, boutonCavalerie, boutonInfanterie, boutonInfanterieLourde, boutonMage;
	private JButton boutonFinDeTour, boutonAbandonner, boutonQuitter;
	private JLabel labelBonusTerrain;

	

	public PanelJeu(Hexagone[][] parHexs) throws IOException {
		super();
		this.bdl = new BorderLayout();
		this.setLayout(bdl);
		
		/* PANEL GAUCHE : toutes les inforamtions sur la partie */
		this.panelGaucheInfos = new JPanel();
		panelGaucheInfos.setPreferredSize(new Dimension(200,500));
		//panelGaucheInfos.setLayout(new BoxLayout(panelGaucheInfos, BoxLayout.Y_AXIS));
		
		

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
		panelInfoTour.add(labelNbTours);

		// Nom du joueur
		labelNomJoueur = new JLabel("Tour de : ");
		panelInfoTour.add(labelNomJoueur);

		// Chrono
		int delais=1000;
		ActionListener tache_timer;
		JLabel Label1 = new JLabel("00:00");
		tache_timer= new ActionListener()
		{
			public void actionPerformed(ActionEvent e1)
			{
				seconde++;
				if(seconde==60)
				{
					seconde=0;
					minute++;
				}
				String str = new String();
				if(minute<10)
					str+=("0");
				str+=minute+":";
				if(seconde<10)
					str+=("0");
				str+=seconde;
				Label1.setText(str);/* rafraichir le label */
			}
		};
		final Timer timer= new Timer(delais,tache_timer);
		panelInfoTour.add(Label1);
		timer.start();
		panelGaucheInfos.add(panelInfoTour);

		///////////////////////
		// PANEL INFO PARTIE //
		///////////////////////

		this.panelInfoPartie = new JPanel();
		panelInfoPartie.setPreferredSize(new Dimension(200,100));
		panelInfoPartie.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelInfoPartie.setLayout(new BoxLayout(panelInfoPartie, BoxLayout.Y_AXIS));
		
		// titre
		JLabel labelTitreInfoCase = new JLabel("Infomartions de la case");
		panelInfoPartie.add(labelTitreInfoCase);

		// type de terrain
		labelTypeTerrain = new JLabel("");
		panelInfoPartie.add(labelTypeTerrain);
		// bonus terrain
		labelBonusTerrain = new JLabel("");
		panelInfoPartie.add(labelBonusTerrain);

				
		// unite batiment
		labelBatimentUnite = new JLabel("");
		panelInfoPartie.add(labelBatimentUnite);

		panelGaucheInfos.add(panelInfoPartie);

		////////////////////
		// PANEL BOUTIQUE //
		////////////////////
		this.panelBoutique = new JPanel();
		panelBoutique.setPreferredSize(new Dimension(200,250));
		panelBoutique.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		//panelBoutique.setLayout(new BoxLayout(panelBoutique, BoxLayout.Y_AXIS));

		// titre
		JLabel labelTitreBoutique = new JLabel("Boutique d'achats :");
		panelBoutique.add(labelTitreBoutique);

		// golds
		labelGolds = new JLabel(" Golds");
		panelBoutique.add(labelGolds);

		// liste d'achat
		JPanel panelListeAchat = new JPanel();
		panelListeAchat.setLayout(new GridBagLayout());
		GridBagConstraints contrainte= new GridBagConstraints();
		contrainte.gridx=0; contrainte.gridy=0;

			// Archer
		labelArcher = new JLabel("Archer");
		panelListeAchat.add(labelArcher,contrainte);
		contrainte.gridx=1;
		boutonArcher = new JButton("5 Golds");
		boutonArcher.setActionCommand("achatArcher");
		panelListeAchat.add(boutonArcher,contrainte);

			// Cavalerie
		contrainte.gridx=0; contrainte.gridy=1;
		labelCavalerie = new JLabel("Cavalerie");
		panelListeAchat.add(labelCavalerie,contrainte);
		contrainte.gridx=1;
		boutonCavalerie = new JButton("12 Golds");
		boutonCavalerie.setActionCommand("achatCavalerie");
		panelListeAchat.add(boutonCavalerie,contrainte);

			// Infanterie
		contrainte.gridx=0; contrainte.gridy=2;
		labelInfanterie = new JLabel("Infanterie");
		panelListeAchat.add(labelInfanterie,contrainte);
		contrainte.gridx=1;
		boutonInfanterie = new JButton("6 Golds");
		boutonInfanterie.setActionCommand("achatInfanterie");
		panelListeAchat.add(boutonInfanterie,contrainte);

			// Infanterie Lourde
		contrainte.gridx=0; contrainte.gridy=3;
		labelInfanterieLourde = new JLabel("Infanterie Lourde");
		panelListeAchat.add(labelInfanterieLourde,contrainte);
		contrainte.gridx=1;
		boutonInfanterieLourde = new JButton("30 Golds");
		boutonInfanterieLourde.setActionCommand("achatInfanterieLourde");
		panelListeAchat.add(boutonInfanterieLourde,contrainte);

			// Mage
		contrainte.gridx=0; contrainte.gridy=4;
		labelMage = new JLabel("Mage");
		panelListeAchat.add(labelMage,contrainte);
		contrainte.gridx=1;
		boutonMage = new JButton("20 Golds");
		boutonMage.setActionCommand("achatMage");
		panelListeAchat.add(boutonMage,contrainte);

		panelBoutique.add(panelListeAchat);

		panelGaucheInfos.add(panelBoutique);

		this.add(panelGaucheInfos,BorderLayout.WEST);

		/* PANEL CENTRE : plateau de jeu */
		this.PanelCentrePlateau = new PanelMap(parHexs);
		this.add(PanelCentrePlateau,BorderLayout.CENTER);

		/* PANEL BAS : boutons */
		panelBasBoutons = new JPanel();
		panelBasBoutons.setLayout(new BoxLayout(panelBasBoutons, BoxLayout.X_AXIS));
		boutonFinDeTour = new JButton("Fin de Tour");
		boutonAbandonner = new JButton("Abandonner");
		boutonQuitter = new JButton("Quitter");
		boutonFinDeTour.setActionCommand("finTour");
		boutonQuitter.setActionCommand("retourMenu");
		boutonAbandonner.setActionCommand("abandonner");

		panelBasBoutons.add(boutonFinDeTour);
		panelBasBoutons.add(boutonAbandonner);
		panelBasBoutons.add(boutonQuitter);

		this.add(panelBasBoutons,BorderLayout.SOUTH);
		
	}

	/**
	 * La methode enregistreEcouteur met a l'ecoute les elements du Panel pour le controleur
	 * @param controleur controleur que l'on souhaite mettre a l'ecoute
	 */
	public void enregistreEcouteur(Jeu controleur) {
        boutonFinDeTour.addActionListener(controleur);
		boutonAbandonner.addActionListener(controleur);
		boutonArcher.addActionListener(controleur);
		boutonCavalerie.addActionListener(controleur);
		boutonInfanterie.addActionListener(controleur);
		boutonInfanterieLourde.addActionListener(controleur);
		boutonMage.addActionListener(controleur);
		boutonQuitter.addActionListener(controleur);
	}

	public void updateGoldJoueurAffichage(int nbGold) {
        labelGolds.setText(nbGold+" Golds");
    }


	public BorderLayout getBdl() {
		return this.bdl;
	}

	public void setBdl(BorderLayout bdl) {
		this.bdl = bdl;
	}

	public PanelMap getPanelCentrePlateau() {
		return this.PanelCentrePlateau;
	}

	public void setPanelCentrePlateau(PanelMap PanelCentrePlateau) {
		this.PanelCentrePlateau = PanelCentrePlateau;
	}

	public JPanel getPanelGaucheInfos() {
		return this.panelGaucheInfos;
	}

	public void setPanelGaucheInfos(JPanel panelGaucheInfos) {
		this.panelGaucheInfos = panelGaucheInfos;
	}

	public JPanel getPanelBasBoutons() {
		return this.panelBasBoutons;
	}

	public void setPanelBasBoutons(JPanel panelBasBoutons) {
		this.panelBasBoutons = panelBasBoutons;
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

	public JPanel getPanelBoutique() {
		return this.panelBoutique;
	}

	public void setPanelBoutique(JPanel panelBoutique) {
		this.panelBoutique = panelBoutique;
	}

	public JLabel getLabelGolds() {
		return this.labelGolds;
	}

	public void setLabelGolds(JLabel labelGolds) {
		this.labelGolds = labelGolds;
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
	
}
