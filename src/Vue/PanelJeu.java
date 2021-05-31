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
	public static String str = new String("00:00");
	private Timer timerTour;
	private Timer timerHorloge;
	private JButton boutonSauvegarder;

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
		panelInfoTour.add(labelNbTours);

		// Nom du joueur
		labelNomJoueur = new JLabel("Tour de : ");
		panelInfoTour.add(labelNomJoueur);

				
		// Chrono
		JLabel Label1 = new JLabel("02:00");
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
				Label1.setText(str);/* rafraichir le label */

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
				Label1.setText(str);
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

		panelInfoTour.add(Label1);
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
		//labelTitreInfoCase.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
        labelTitreInfoCase.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelTitreInfoCase);

		// type de terrain
		labelTypeTerrain = new JLabel("");
		//labelTypeTerrain.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
        labelTypeTerrain.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelTypeTerrain);
		// bonus terrain
		labelBonusTerrain = new JLabel("");
		//labelBonusTerrain.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
        labelBonusTerrain.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelBonusTerrain);

				
		// unite batiment
		labelBatimentUnite = new JLabel("");
		labelBatimentUnite.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
        labelBatimentUnite.setForeground(new Color(109,7,26));
		panelInfoPartie.add(labelBatimentUnite);

		panelGaucheInfos.add(panelInfoPartie);

		////////////////////
		// PANEL BOUTIQUE //
		////////////////////
		this.panelBoutique = new JPanel();
		panelBoutique.setPreferredSize(new Dimension(200,300));
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
		boutonArcher = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonArcher.png", boutonArcher);
		boutonArcher.setActionCommand("achatArcher");
		panelListeAchat.add(boutonArcher,contrainte);

			// Cavalerie
		contrainte.gridx=0; contrainte.gridy=1;
		labelCavalerie = new JLabel("Cavalerie");
		panelListeAchat.add(labelCavalerie,contrainte);
		contrainte.gridx=1;
		boutonCavalerie = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonCavalerie.png", boutonCavalerie);
		boutonCavalerie.setActionCommand("achatCavalerie");
		panelListeAchat.add(boutonCavalerie,contrainte);

			// Infanterie
		contrainte.gridx=0; contrainte.gridy=2;
		labelInfanterie = new JLabel("Infanterie");
		panelListeAchat.add(labelInfanterie,contrainte);
		contrainte.gridx=1;
		boutonInfanterie = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonInfanterie.png", boutonInfanterie);
		boutonInfanterie.setActionCommand("achatInfanterie");
		panelListeAchat.add(boutonInfanterie,contrainte);

			// Infanterie Lourde
		contrainte.gridx=0; contrainte.gridy=3;
		labelInfanterieLourde = new JLabel("Infanterie Lourde");
		panelListeAchat.add(labelInfanterieLourde,contrainte);
		contrainte.gridx=1;
		boutonInfanterieLourde = new JButton();
		setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"buttonInfanterieLourde.png", boutonInfanterieLourde);
		boutonInfanterieLourde.setActionCommand("achatInfanterieLourde");
		panelListeAchat.add(boutonInfanterieLourde,contrainte);

			// Mage
		contrainte.gridx=0; contrainte.gridy=4;
		labelMage = new JLabel("Mage");
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



	// FIN Getters et setters
	
}
