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
	private int NbTours;
	private JLabel labelNomJoueur;
	private String NomJoueur;
	private static int minute=0,seconde=0;
	private JLabel labelTypeTerrain;
	private JLabel labelBatimentUnite;
	private JPanel panelBoutique;
	private JLabel labelGolds;
	private int golds;
	private JLabel labelArcher, labelCavalerie, labelInfanterie, labelInfanterieLourde, labelMage;
	private JButton boutonArcher, boutonCavalerie, boutonInfanterie, boutonInfanterieLourde, boutonMage;
	private JButton boutonFinDeTour, boutonAbandonner, boutonQuitter;

	

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
		NbTours=1;
		labelNbTours = new JLabel("Nombre de tour(s) : "+NbTours+" / 30");
		panelInfoTour.add(labelNbTours);

		// Nom du joueur
		NomJoueur="Clement";
		labelNomJoueur = new JLabel("Tour de : "+NomJoueur);
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
		labelTypeTerrain = new JLabel("MONTAGNE (exemple)");
		panelInfoPartie.add(labelTypeTerrain);
		// unite batiment

		// if(batiment ou unite)
		labelBatimentUnite = new JLabel("Archer (exemple)");
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
		golds = 150; // exemple
		labelGolds = new JLabel(golds+" Golds");
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
		boutonArcher = new JButton("50 Golds");
		boutonArcher.setActionCommand("achatArcher");
		panelListeAchat.add(boutonArcher,contrainte);

			// Cavalerie
		contrainte.gridx=0; contrainte.gridy=1;
		labelCavalerie = new JLabel("Cavalerie");
		panelListeAchat.add(labelCavalerie,contrainte);
		contrainte.gridx=1;
		boutonCavalerie = new JButton("150 Golds");
		boutonCavalerie.setActionCommand("achatCavalerie");
		panelListeAchat.add(boutonCavalerie,contrainte);

			// Infanterie
		contrainte.gridx=0; contrainte.gridy=2;
		labelInfanterie = new JLabel("Infanterie");
		panelListeAchat.add(labelInfanterie,contrainte);
		contrainte.gridx=1;
		boutonInfanterie = new JButton("200 Golds");
		boutonInfanterie.setActionCommand("achatInfanterie");
		panelListeAchat.add(boutonInfanterie,contrainte);

			// Infanterie Lourde
		contrainte.gridx=0; contrainte.gridy=3;
		labelInfanterieLourde = new JLabel("Infanterie Lourde");
		panelListeAchat.add(labelInfanterieLourde,contrainte);
		contrainte.gridx=1;
		boutonInfanterieLourde = new JButton("200 Golds");
		boutonInfanterieLourde.setActionCommand("achatInfanterieLourde");
		panelListeAchat.add(boutonInfanterieLourde,contrainte);

			// Mage
		contrainte.gridx=0; contrainte.gridy=4;
		labelMage = new JLabel("Mage");
		panelListeAchat.add(labelMage,contrainte);
		contrainte.gridx=1;
		boutonMage = new JButton("300 Golds");
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
		boutonQuitter.setActionCommand("retourMenuSauvegarde");
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

	/**
	 * La methode tourPlusUn permet d'ajouter et de mettre a jour l'affichage du nombre de tours
	 */
	public void tourPlusUn(){
		this.NbTours++;
		labelNbTours = new JLabel("Nombre de tour(s) : +"+NbTours+" / 30");
	}


	
}
