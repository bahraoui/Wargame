package controleur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Vue.FrameJeu;
import Vue.Hexagone;
import Vue.PanelActuel;
import Vue.PanelChargerPartie;
import Vue.PanelChargerScenario;
import Vue.PanelJeu;
import Vue.Point;
import Vue.TypeBatimentVue;
import Vue.TypeTerrain;
import Vue.TypeUnite;
import modele.entite.Entite;
import modele.entite.batiment.Batiment;
import modele.entite.batiment.TypeBatiment;
import modele.entite.unite.Archer;
import modele.entite.unite.Cavalerie;
import modele.entite.unite.Infanterie;
import modele.entite.unite.InfanterieLourde;
import modele.entite.unite.Mage;
import modele.entite.unite.Unite;
import modele.joueur.Joueur;
import modele.plateau.Case;
import modele.plateau.Plateau;
import modele.terrain.Desert;
import modele.terrain.Foret;
import modele.terrain.Mer;
import modele.terrain.Montagne;
import modele.terrain.Plaine;
import modele.terrain.Terrain;
import modele.terrain.ToundraNeige;

public class Jeu extends MouseAdapter implements ActionListener {
    private static Plateau plateau;
    private static ArrayList<ArrayList<Integer>> postionBaseJoueur;
    private static ArrayList<Joueur> listeJoueur;
    private static Joueur joueurActuel;
    private static int tour;
    private static boolean finpartie;
    private static FrameJeu FenetreJeu;
    private static Integer nbJoueursH;
    private static Integer nbJoueursIA;
    private static String carteChoisis;
    private static File sauvegardeChoisis;
    private static Cellule[][] cellulesCarte;
    private static PanelJeu pj;
    private static TypeTerrain terrainChoisi;
    private static boolean selectionMonument;
    private static PanelChargerScenario panelChargerScenario;

    private static final int cote = 16;


    
    public static void main(String[] args) throws IOException, InterruptedException {

        Jeu controleur = new Jeu();

        carteChoisis = "";

        finpartie = false;
        selectionMonument=false;
        cellulesCarte = new Cellule[16][16];
        terrainChoisi = TypeTerrain.NEIGE;
        nbJoueursH = nbJoueursIA = 0;
        listeJoueur = new ArrayList<Joueur>();
        postionBaseJoueur = new ArrayList<ArrayList<Integer>>();
        plateau = new Plateau();
        setCellulesMap();
        FenetreJeu = new FrameJeu(/*pj*/);
        FenetreJeu.enregistreEcouteur(controleur);

    }

    public static void setCellulesMap() throws IOException {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                TypeTerrain sol = null;
                TypeUnite unite = null;
                TypeBatimentVue batiment = null;
                if (plateau.get(i).get(j).getTerrain() instanceof Plaine)
                    sol = TypeTerrain.PLAINE;
                else if (plateau.get(i).get(j).getTerrain() instanceof Desert)
                    sol = TypeTerrain.DESERT;
                else if (plateau.get(i).get(j).getTerrain() instanceof Foret)
                    sol = TypeTerrain.FORET;
                else if (plateau.get(i).get(j).getTerrain() instanceof Mer)
                    sol = TypeTerrain.MER;
                else if (plateau.get(i).get(j).getTerrain() instanceof Montagne)
                    sol = TypeTerrain.MONTAGNE;
                else if (plateau.get(i).get(j).getTerrain() instanceof ToundraNeige)
                    sol = TypeTerrain.NEIGE;

                if (plateau.get(i).get(j).getUnite() instanceof Archer)
                    unite = TypeUnite.ARCHER;
                else if (plateau.get(i).get(j).getUnite() instanceof Cavalerie)
                    unite = TypeUnite.CAVALERIE;
                else if (plateau.get(i).get(j).getUnite() instanceof Infanterie)
                    unite = TypeUnite.INFANTERIE;
                else if (plateau.get(i).get(j).getUnite() instanceof InfanterieLourde)
                    unite = TypeUnite.INFANTERIELOURDE;
                else if (plateau.get(i).get(j).getUnite() instanceof Mage)
                    unite = TypeUnite.MAGE;
                
                
                if (plateau.get(i).get(j).getBatiment() != null && plateau.get(i).get(j).getBatiment().getEstBase() == TypeBatiment.BASE){
                    batiment = TypeBatimentVue.BASE_HAUT;
                }
                else if (plateau.get(i).get(j).getBatiment() != null && plateau.get(i).get(j).getBatiment().getEstBase() == TypeBatiment.MONUMENT)
                    batiment = TypeBatimentVue.MONUMENT;

                Cellule cell = new Cellule(new Hexagone(sol, unite, batiment,new Point(i, j)), plateau.get(i).get(j));
                cellulesCarte[i][j] = cell;
            }
        }
    }

    public static Hexagone[][] cellulesToHexagones() throws IOException {
        Hexagone[][] hexs = new Hexagone[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                hexs[i][j] = cellulesCarte[i][j].getHex();
            }
        }
        return hexs;
    }

    //donne en parametre uniquement une case pas null
    public static void combat(Case attaquant, Case defenseur) {
        Case.attaquer(attaquant,defenseur);
        if (((Entite) defenseur.estOccupe()).getPointDeVieActuel() <= 0){
            mortEntite(defenseur);
        }
    }

    //revoir
    public static void mortEntite(Case defenseur){
        if (defenseur.getUnite() != null) {
            for (int i = 0; i < listeJoueur.size(); i++) {
                for (int j = 0; j < listeJoueur.get(i).getArmee().size(); j++){
                    if (defenseur.getUnite().getIdentifiant() == listeJoueur.get(i).getArmee().get(j).getIdentifiant()){
                        listeJoueur.get(i).getArmee().remove(j); //armee
                        defenseur.setUnite(null); //plateau
                        if (listeJoueur.get(i).getArmee().size() > 0 || listeJoueur.get(i).getPieces() > 10){
                            listeJoueur.get(i).setEnJeu(false);
                        }
                        return;
                    }
                }
            }
        }
        else if (defenseur.getBatiment() != null) {
            if (defenseur.getBatiment().getEstBase() == TypeBatiment.BASE) {
                for (int i = 0; i < listeJoueur.size(); i++) {
                    if (listeJoueur.get(i).getBase().getIdentifiant() == defenseur.getBatiment().getIdentifiant()) {
                        listeJoueur.get(i).setEnJeu(false);
                        ArrayList<Integer> coordBase = postionBaseJoueur.get(listeJoueur.get(i).getNumeroJoueur());
                        plateau.get(coordBase.get(0)).get(coordBase.get(1)).setBatiment(null);
                    }
                }
            }
            else {
                joueurActuel.setPieces(joueurActuel.getPieces() + defenseur.getBatiment().getTresor());
                defenseur.setBatiment(null); //plateau
            }
        }
    }
    

    public static void placerBaseJoueur(Joueur joueur, int coordY, int coordX){
        Batiment base = new Batiment(TypeBatiment.BASE);
        joueur.setBase(base);
        plateau.get(coordY).get(coordX).setBatiment(base);
        ArrayList<Integer> baseJ1 = new ArrayList<Integer>();
        baseJ1.add(0,coordY);
        baseJ1.add(1,coordX);
        postionBaseJoueur.add(baseJ1);
    }

    public static boolean placerUniteJoueur(Joueur joueur, Unite unite, int coordY, int coordX){
        Case caseUnite = plateau.get(coordY).get(coordX);
        int coordYBase = postionBaseJoueur.get(joueur.getNumeroJoueur()).get(0);
        int coordXBase = postionBaseJoueur.get(joueur.getNumeroJoueur()).get(1);
        int calculVisionY = coordY - coordYBase;
        int calculVisionX = coordX - coordXBase;
        if (caseUnite.getBatiment() == null && caseUnite.getUnite() == null && Math.abs(calculVisionY) <= joueur.getBase().getVision() && Math.abs(calculVisionX) <= joueur.getBase().getVision() && Joueur.achatUniteArmee(joueur,unite)) {
            joueur.getArmee().add(unite);
            plateau.get(coordY).get(coordX).setUnite(unite);
            return true;
        }
        return false;
    }

    
    public static Case coordToCase(int coordY, int coordX){
        return plateau.get(coordY).get(coordX);
    }
    
    //DebutTour    

    public static boolean conditionVictoire(){
        /*if (conditionBase() || conditionPiece()) {
            return true;
        }
        return false;*/
        for (int i = 0; i < listeJoueur.size(); i++) {
            if (listeJoueur.get(i).getNumeroJoueur() != joueurActuel.getNumeroJoueur() && listeJoueur.get(i).getEnJeu() == true) {
                return false;
            }
        }
        return true;
    }

    public static void chronometre() {
    	Timer chrono =  new Timer();
    	chrono.schedule(new TimerTask(){
            int index = 0,  i = 0;
			@Override
			public void run() {
                while(finpartie == false){
                    i++;
                    if(i == listeJoueur.size()){
                        i = 0;
                    }else if(index == listeJoueur.size()-1){
                        index = 0;
                    }else if(index < listeJoueur.size() && i < listeJoueur.size()){   
                        System.out.println(joueurActuel);
                        joueurActuel = listeJoueur.get(index+1);
                        System.out.println(joueurActuel);
                        index += 1; 
                    }       
                } 

			}
    	}, 120000 , 120000);
    }

    public static void actionDopportunite(Joueur joueur, int coordYFinalJoueur, int coordXFinalJoueur, int coordYOpportunite, int coordXOpportunite, Case caseAttaque) {
    	Case positionFinalJoueur = plateau.get(coordYFinalJoueur).get(coordXFinalJoueur); 
    	Case postionOpportunite = plateau.get(coordYOpportunite).get(coordXOpportunite); 
    	int calculVisionOppX = Math.abs(coordXFinalJoueur - coordXOpportunite);
    	int calculVisionOppY = Math.abs(coordYFinalJoueur - coordYOpportunite);
    	for(int i = 0; i<joueur.getArmee().size();i++) {
    		Entite att = new Entite(); 
	        if (postionOpportunite.getBatiment() != null) {
	            att = postionOpportunite.getBatiment();
	            if(calculVisionOppX <= att.getVision() && calculVisionOppY <= att.getVision()){
            		Case.attaquer(postionOpportunite, positionFinalJoueur);
            	}  
	        }
	        else if (postionOpportunite.getUnite() != null && listeJoueur.get(i).getArmee().get(i).getEnRepos() == true) {
	            att = postionOpportunite.getUnite();
	            if(positionFinalJoueur.getUnite() != att && (calculVisionOppX <= att.getVision() && calculVisionOppY <= att.getVision())){
	            	Unite.deplacementUnite(postionOpportunite.getUnite(), postionOpportunite, caseAttaque);
	            	Case.attaquer(postionOpportunite, positionFinalJoueur);
            	}
	        }
        }
    }

    /*
    
    PARTIE IA
    
    */

    public static void joueurAAttaquerIA() {
        
        //verfie si encore in game
        //sinon
        //random
        //assigner
    }

    //Par rapport à la base placé vers le centre de la map en prio
    public static void placementUnite(Joueur ia, Unite unite) {
        int coordX = postionBaseJoueur.get(ia.getNumeroJoueur()).get(0);
        int coordY = postionBaseJoueur.get(ia.getNumeroJoueur()).get(1);
        for (int i = coordX - 1 ; i < coordX + 3; i++) {
            for (int j = coordY - 2; j < coordY + 3; j++) {
                //Hors quatre coins
                if (placerUniteJoueur(ia, unite, i,j)){
                    return;
                }
            }
        }
    }

    public static void achatTroupesIA(Joueur ia) throws InterruptedException{
        int depense = new Random().nextInt(ia.getPieces()/2);
        System.out.println("Initial : "+depense);
        while (depense >= new Archer().getCout()) {
            System.out.println("Nouvelle : "+depense);
            if (depense >= new InfanterieLourde().getCout()) {
                InfanterieLourde infanterieLourde = new InfanterieLourde();
                placementUnite(ia,infanterieLourde);
                depense -= infanterieLourde.getCout();
                System.out.println("IF ACHETE");
                //placerUniteJoueur(ia.getJoueurIA(), infanterieLourde, coordY, coordX);
            }
            else if (depense >= new Mage().getCout()) {
                Mage mage = new Mage();
                placementUnite(ia,mage);
                System.out.println("Mage ACHETE");
                depense -= mage.getCout();
                //placerUniteJoueur(ia.getJoueurIA(), mage, coordY, coordX);
            }
            else if (depense >= new Cavalerie().getCout()) {
                Cavalerie cavalerie = new Cavalerie();
                placementUnite(ia,cavalerie);
                System.out.println("Cavalerie ACHETE");
                depense -= cavalerie.getCout();
                //placerUniteJoueur(ia.getJoueurIA(), cavalerie, coordY, coordX);
            }
            else if (depense >= new Infanterie().getCout()) {
                Infanterie infanterie = new Infanterie();
                placementUnite(ia,infanterie);
                System.out.println("Infanterie ACHETE");
                depense -= infanterie.getCout();
                //placerUniteJoueur(ia.getJoueurIA(), infanterie, coordY, coordX);
            }
            else if (depense >= new Archer().getCout()) {
                Archer archer = new Archer();
                placementUnite(ia,archer);
                System.out.println("Archer ACHETE");
                depense -= archer.getCout();
                //placerUniteJoueur(ia.getJoueurIA(), archer, coordY, coordX);
            }
            //Thread.sleep(2000);
        }
        System.out.println("Fin de depense");        
    }

    public static void deplacementUniteIA() {
        // rechercher plus court chemin entre troupe et base
        // 
    }

    public static void tourIA(){
        //achat
        //joueurAAttaquer
        //pour chaque unite attaque
            //trouver deplacement jusqua base
    }


    /*
    
    FIN PARTIE IA
    
    */


    /**
     * 
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        /**
         * Clic sur un bouton
         */
        if (evt.getSource() instanceof JButton) {
            /**
             * Bouton "Quitter"
             */
            if (evt.getActionCommand().equals("quit")) {
                int reponse = JOptionPane.showConfirmDialog(FenetreJeu, "Voulez-vous quitter l'application ?", "Êtes-vous sur ?",0, 0);
                if (reponse == 0) {
                    FenetreJeu.dispose();
                    System.exit(0);
                }
            }
            /**
             * Bouton "Nouvelle Partie"
             */
            else if (evt.getActionCommand().equals("nouvellePartie")) {
                //System.out.println("Nouvelle partie !");
                FenetreJeu.changePanel(PanelActuel.NOUVELLEPARTIE);                
            }
            /**
             * Bouton "Choix Monument"
             */
            else if (evt.getActionCommand().equals("choixMonument")) {
                selectionMonument=true;
                FenetreJeu.setChoixMonumentTxt("Monument selctionne");
            }
            /**
             * Bouton "Charger Partie"
             */
            else if (evt.getActionCommand().equals("chargerPartie")) {
                System.out.println("Charger partie !");
                FenetreJeu.changePanel(PanelActuel.CHARGERPARTIE);
            }
            /**
             * Bouton "Charger une partie sauvegardée"
             */
            else if (evt.getActionCommand().equals("chercherSauvegarde")) {
                System.out.println("Récupere le fichier de sauvegarde !");
                JFileChooser choose = new JFileChooser(
                    FileSystemView
                    .getFileSystemView()
                    .getHomeDirectory()
                );
                choose.setDialogTitle("Selectionnez une fichier de sauvegarde");
                choose.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Documents de type TXT", "txt");
                choose.addChoosableFileFilter(filter);
                // Ouvrez le fichier
                int res = choose.showOpenDialog(null);
                // Enregistrez le fichier
                if (res == JFileChooser.APPROVE_OPTION) {
                  sauvegardeChoisis = choose.getSelectedFile();
                  ((PanelChargerPartie) FenetreJeu.getPanelChargerPartie()).getLblCarteChosie().setText("Sauvegarde chosie : " + sauvegardeChoisis.getName());
                  System.out.println(sauvegardeChoisis.getAbsolutePath());
                }
            }
            /**
             * Bouton "Lancer la partie sauvegardée"
             */
            else if (evt.getActionCommand().equals("lancerPartieChargee")) {
                if (sauvegardeChoisis != null) {
                    try {
                        // Generer un action pane si fichier pas bon
                        chargePartie(new FileInputStream(sauvegardeChoisis));
                        System.out.println("File bien chargee");
                        System.out.println(plateau.affichage());
                        setCellulesMap();
                        pj = new PanelJeu(cellulesToHexagones());
                        FenetreJeu.setPanelJeu(pj);
                        pj.enregistreEcouteur(this);
                        FenetreJeu.changePanel(PanelActuel.JEU);  
                    } catch (IOException e) {
                        e.printStackTrace();                   
                    }
                }
                else {
                    JOptionPane.showMessageDialog(FenetreJeu, "Vous devez choisir une sauvegarde de partie ! ");
                }
                
            }
            else if (evt.getActionCommand().equals("retourMenuSauvegarde")) {
                System.out.println("Retour Menu Sauvegarde ");
                FenetreJeu.changePanel(PanelActuel.MENU);
            }
            /**
             * Bouton "Règles"
             */
            else if (evt.getActionCommand().equals("afficherRegles")) {
                System.out.println("Voici les regles : Il n'y a pas de regles !");
                FenetreJeu.changePanel(PanelActuel.REGLES);
            }
            /**
             * Bouton "Continuer" -- Fenetre nouvelle partie
             */
            else if (evt.getActionCommand().equals("nouvellePartieContinuer")) {
                if (carteChoisis.equals("")){
                    JOptionPane.showMessageDialog(FenetreJeu, "Veuillez choisir une carte ! ");
                }
                else if (nbJoueursH + nbJoueursIA < 2  || nbJoueursH + nbJoueursIA > 4 )
                    JOptionPane.showMessageDialog(FenetreJeu, "Vous devez choisir entre 2 et 4 joueurs en tout ! ");
                else if (!FenetreJeu.getPanelNouvellePartie().setAllNames(nbJoueursH+nbJoueursIA))
                    JOptionPane.showMessageDialog(FenetreJeu, "Vous devez entrer les noms des joueurs ! ");
                else {
                    try {
                        System.out.println(nbJoueursH+nbJoueursIA);
                        chargerCarte(new FileInputStream("src\\data\\cartes\\default\\"+carteChoisis+".txt"));
                        for (int i = 0; i < nbJoueursH+nbJoueursIA; i++) {
                            if (i < nbJoueursH)
                                listeJoueur.add(new Joueur(FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].getText(),false));
                            else 
                                listeJoueur.add(new Joueur(FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].getText(),true));
                            int coordX = ((i+1)*3);
                            int coordY = ((i+1)*3);
                            System.out.println("COORDONNEES BASE : "+coordX + " - "+ coordY);
                            placerBaseJoueur(listeJoueur.get(i), coordX, coordY);
                            System.out.println(plateau.affichage());
                        }
                        setCellulesMap();
                        panelChargerScenario = new PanelChargerScenario(cellulesToHexagones());
                        FenetreJeu.setPanelChangerScenario(panelChargerScenario);
                        panelChargerScenario.enregistreEcouteur(this);
                        terrainChoisi = TypeTerrain.NEIGE;
                        tour = 0;
                        joueurActuel = listeJoueur.get(0);
                        System.out.println(listeJoueur);
                        FenetreJeu.changePanel(PanelActuel.CHANGERSCENARIO);  
                    } catch (IOException e) {
                        e.printStackTrace();
                    }  
                }
            }
            /**
             * Bouton "Lancer Partie" -- Fenetre nouvelle partie apres scénario
             */
            else if (evt.getActionCommand().equals("lancerPartieApresScenario")) {
                try {
                    //charger
                    pj = new PanelJeu(cellulesToHexagones());
                    FenetreJeu.setPanelJeu(pj);
                    pj.enregistreEcouteur(this);
                    nouveauTour();
                    FenetreJeu.changePanel(PanelActuel.JEU);  
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  
            }
            /**
             * 
             * ACHAT UNITE
             * 
             */
            else if (evt.getActionCommand().equals("achatArcher")) {
                System.out.println("Achat archer");
                Archer archerAchete = new Archer();
                Joueur.achatUniteArmee(joueurActuel, archerAchete);
                placementUnite(joueurActuel,archerAchete);
                System.out.println(plateau.affichage());
                FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
            }
            else if (evt.getActionCommand().equals("achatCavalerie")) {
                System.out.println("Achat calvalerie");
                Cavalerie cavalerieAchete = new Cavalerie();
                Joueur.achatUniteArmee(joueurActuel, cavalerieAchete);
                placementUnite(joueurActuel,cavalerieAchete);
                System.out.println(plateau.affichage());
                FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
            }
            else if (evt.getActionCommand().equals("achatInfanterie")) {
                System.out.println("Achat infanterie");
                Infanterie infanterieAchete = new Infanterie();
                Joueur.achatUniteArmee(joueurActuel, infanterieAchete);
                placementUnite(joueurActuel,infanterieAchete);
                System.out.println(plateau.affichage());
                FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
            }
            else if (evt.getActionCommand().equals("achatInfanterieLourde")) {
                System.out.println("Achat infanterie lourde");
                InfanterieLourde infanterieLourdeAchete = new InfanterieLourde();
                Joueur.achatUniteArmee(joueurActuel, infanterieLourdeAchete);
                placementUnite(joueurActuel,infanterieLourdeAchete);
                System.out.println(plateau.affichage());
                FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
            }
            else if (evt.getActionCommand().equals("achatMage")) {
                System.out.println("Achat mage");
                Mage archerMage = new Mage();
                Joueur.achatUniteArmee(joueurActuel, archerMage);
                placementUnite(joueurActuel,archerMage);
                System.out.println(plateau.affichage());
                FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
            }

            /**
             * Bouton "Fin de tour" -- Fenetre en jeu
             */
            else if (evt.getActionCommand().equals("finTour")) {
                try {
                    nouveauTour();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            /**
             * Bouton "Abandonner" -- Fenetre en jeu
             */
            else if (evt.getActionCommand().equals("abandonner")) {
                try {
                    joueurActuel.setEnJeu(false);
                    nouveauTour();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            /**
             * Bouton "Retour"
             */
            else if (evt.getActionCommand().equals("retourMenu")) {
                System.out.println("Retour Menu!");
                FenetreJeu.changePanel(PanelActuel.MENU);
            }
        }
        /**
         * Clic sur une liste
         */
        else if (evt.getSource() instanceof JComboBox<?>) {
            if (evt.getActionCommand().equals("nbJoueursH")) {
                JComboBox<Integer> nbH = (JComboBox<Integer>) evt.getSource();
                nbJoueursH = (Integer) nbH.getSelectedItem();
                int nomJ = nbJoueursH + nbJoueursIA;
                if (nomJ > 4)
                    nomJ = 4;
                for (int i = 0; i < 4; i++) {
                    if(i < nomJ) {
                        FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(true);
                        FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(true);
                    }
                    else {
                        FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(false);
                        FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(false);
                    }
                }
            } 
            else if (evt.getActionCommand().equals("nbJoueursIA")) {
                JComboBox<Integer> nbIA = (JComboBox<Integer>) evt.getSource();
                nbJoueursIA = (Integer) nbIA.getSelectedItem();
                int nomJ = nbJoueursH + nbJoueursIA;
                if (nomJ > 4)
                    nomJ = 4;
                for (int i = 0; i < 4; i++) {
                    if(i < nomJ) {
                        FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(true);
                        FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(true);
                    }
                    else {
                        FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(false);
                        FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(false);
                    }
                }
            }
            else if (evt.getActionCommand().equals("choixMap")) {
                JComboBox<String> nomCarte = (JComboBox<String>) evt.getSource();
                carteChoisis = (String) nomCarte.getSelectedItem();
            }
            else if (evt.getActionCommand().equals("listeTerrains")) {
                selectionMonument = false;
                JComboBox<TypeTerrain> choixTerrain = (JComboBox<TypeTerrain>) evt.getSource();
                Jeu.terrainChoisi = (TypeTerrain) choixTerrain.getSelectedItem();
                FenetreJeu.setChoixTerrainTxt((String) choixTerrain.getSelectedItem().toString());
            }
        }
        
    }

    


    private static void nouveauTour() throws InterruptedException {
        if (true) { //condition de victoire
            if (tour != 0) {
                do {
                    joueurActuel = listeJoueur.get((joueurActuel.getNumeroJoueur()+1)%(nbJoueursH+nbJoueursIA));
                }while(joueurActuel.getEnJeu() == false);
            }
            tour++;
            FenetreJeu.getPanelJeu().getLabelNomJoueur().setText("Tour de : "+joueurActuel.getPseudo());
            FenetreJeu.getPanelJeu().getLabelNbTours().setText("Nombre de tours : "+tour);;
            FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
            if (joueurActuel.getEstIa()){
                tourIA();
                Thread.sleep(1000);
            }
            System.out.println(joueurActuel.getPseudo() +" - "+joueurActuel.getArmee().size());
        }
        
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof Hexagone) {
            Hexagone clic = (Hexagone) e.getSource();
            switch (FenetreJeu.getPanelActuel()) {
                case CHANGERSCENARIO:
                    if (terrainChoisi != null && selectionMonument == false) {
                        try {
                            clic.setTerrain(terrainChoisi);
                            cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase().setTerrain(terrainVueToModele(terrainChoisi));
                            if (cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase().getBatiment() != null) {
                                clic.setBatiment(batimentModeleToVue(cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase().getBatiment().getEstBase()));
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else if (selectionMonument == true) {
                        try {
                            if (cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase().getBatiment() != null) {
                                switch (batimentModeleToVue(cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase().getBatiment().getEstBase())) {
                                    case MONUMENT:
                                        clic.setTerrain(terrainModeleToVue(cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase().getTerrain()));
                                        panelChargerScenario.setMonumentNb(false);
                                        cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase().setBatiment(null);                              
                                        break;
                                    
                                    default:
                                        JOptionPane.showMessageDialog(FenetreJeu, "Il y a déjà une base placé ici.");         
                                        break;
                                }
                            }
                            else if(panelChargerScenario.getNbMonumentsRestants() == 0)
                                JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez plus placer de monuments.");
                            else if (cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase().getUnite() != null)
                                JOptionPane.showMessageDialog(FenetreJeu, "Il y a déjà une unité placé ici.");
                            else {
                                panelChargerScenario.setMonumentNb(true);
                                clic.setMonument();
                                cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase().setBatiment(new Batiment(TypeBatiment.MONUMENT));
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                case JEU:
                    System.out.println(cellulesCarte[clic.getCoord().getX()][clic.getCoord().getY()].getCase());
                    System.out.println(clic.getCoord().getX()+" - "+clic.getCoord().getY());
                    break;
                default:
                    break;
            }
        }
    }

    private TypeTerrain terrainModeleToVue(Terrain terrain) {
        TypeTerrain typeTerrain = null;
        if (terrain instanceof Plaine)
            typeTerrain = TypeTerrain.PLAINE;
        else if (terrain instanceof Desert)
            typeTerrain = TypeTerrain.DESERT;
        else if (terrain instanceof Foret)
            typeTerrain = TypeTerrain.FORET;
        else if (terrain instanceof Mer)
            typeTerrain = TypeTerrain.MER;
        else if (terrain instanceof Montagne)
            typeTerrain = TypeTerrain.MONTAGNE;
        else if (terrain instanceof ToundraNeige)
            typeTerrain = TypeTerrain.NEIGE;
        return typeTerrain;
    }


    public Terrain terrainVueToModele(TypeTerrain ter) {
        Terrain terrain = null;
        switch (ter) {
            case MER:
                terrain = new Mer();
                break;
            case DESERT:
                terrain = new Desert();
                break;
            case FORET:
                terrain = new Foret();
                break;
            case MONTAGNE:
                terrain = new Montagne();
                break;
            case PLAINE:
                terrain = new Plaine();
                break;
            case NEIGE:
                terrain = new ToundraNeige();
                break;
            default:
                System.err.println("Erreur : la conversion de terrains s'est mal passe, "+ter+" indefini.");
                break;
        }
        return terrain;
    }
    
    public TypeBatimentVue batimentModeleToVue(TypeBatiment batiment) {
        TypeBatimentVue typeBatimentVue = null;
        switch (batiment) {
            case MONUMENT:
                typeBatimentVue = TypeBatimentVue.MONUMENT;
                break;
            case BASE:
                typeBatimentVue = TypeBatimentVue.BASE_HAUT;
                break;
        
            default:
                break;
        }
        return typeBatimentVue;
    }
   
    public Unite unteVueToModele(TypeUnite typeUnite){
        Unite unite = null;
        switch (typeUnite) {
            case ARCHER:
                unite = new Archer();
                break;
            case CAVALERIE:
                unite = new Cavalerie();
                break;
            case INFANTERIE:
                unite = new Infanterie();
                break;
            case INFANTERIELOURDE:
                unite = new InfanterieLourde();
                break;
            case MAGE:
                unite = new Mage();
                break;
        
            default:
                break;
        }
        return unite;
    }

    /*public static boolean conditionBase(){
        for (int i = 0; i < listeJoueur.size(); i++) {
            if (listeJoueur.get(i).getNumeroJoueur() != joueurActuel.getNumeroJoueur() && listeJoueur.get(i).getEnJeu() == true) {
                return false;
            }
        }
        //System.out.println("BASE CONDITION");
        return true;
    }

    public static boolean conditionPiece(){
        for (int i = 0; i < listeJoueur.size(); i++) {
            if (listeJoueur.get(i).getNumeroJoueur() != joueurActuel.getNumeroJoueur() && (listeJoueur.get(i).getArmee().size() > 0 || listeJoueur.get(i).getPieces() > 10)){
                return false;
            }
        }
        //System.out.println("PIECE CONDITION");
        return true;
    }

*/
    
    public static Batiment analyseSplBatiment(String spl) {

        String [] spl1 = new String[25];
        spl1 = spl.split(",");
        if (spl1.length == 3) {
            Batiment bat = new Batiment(TypeBatiment.MONUMENT);
            bat.setIndentifiant(Integer.parseInt(spl1[1]));
            bat.setPointDeVieActuel(Integer.parseInt(spl1[2]));
        return bat;
        }
        return null;
    }

    public static Unite analyseSplUnite(String spl, int [][] listeUnite) {
        String [] spl1 = new String[25];
        if (spl.length()>2) {
            spl1 = spl.split(",");
            Unite unite = new Unite(0,0,0,0);
            switch (spl1[0]) {
                case "Archer":
                    unite = new Archer();
                    break;

                case "Cavalerie":
                    unite = new Cavalerie();
                    break;
                    
                case "Infanterie":
                    unite = new Infanterie();
                    break;
                    
                case "InfanterirLourde":
                    unite = new InfanterieLourde();
                    break;
                    
                case "Mage":
                    unite = new Mage();
                    break;
            
                default:
                    break;
            }
            unite.setIndentifiant(Integer.parseInt(spl1[1]));
            unite.setPointDeVieActuel(Integer.parseInt(spl1[2]));
            unite.setDeplacementActuel(Integer.parseInt(spl1[3]));
            unite.setAAttaque(Boolean.parseBoolean(spl1[4]));
            unite.setEnRepos(Boolean.parseBoolean(spl1[5]));
            for (int i = 0; i < listeUnite.length; i++) {
                for (int j = 0; j < listeUnite.length; j++) {
                    if (unite.getIdentifiant() == listeUnite[i][j]) {
                        listeJoueur.get(i).getArmee().add(unite);
                    }
                }
            }
            return unite;
        }
        return null;
    }
    
    public static void sauvegardePartie() {
        try {
			
			File file = new File("src\\data\\partie\\savePartie1.txt");
			
			if (!file.exists()) {
			    file.createNewFile();
			   }
		
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			
			String chaine = new String();
            for (int i = 0; i < listeJoueur.size(); i++) {
                chaine +="[";
                chaine +=listeJoueur.get(i).getPseudo()+","+listeJoueur.get(i).getEstIa()+","+listeJoueur.get(i).getPieces()+",{";
                for (int j = 0; j < listeJoueur.get(i).getArmee().size(); j++) {
                    chaine += listeJoueur.get(i).getArmee().get(j).getIdentifiant();
                    if (j<listeJoueur.get(i).getArmee().size()-1) {
                        chaine+=".";
                    }
                }
                chaine +="}";
                //chaine += listeJoueur.get(i).getBase().getIdentifiant();
                if (i<listeJoueur.size()-1) {
                    chaine+="];";
                }
            }
            chaine +="]\n";
			chaine += sauvegardeStringMap(chaine);
            chaine += tour+"\n";
            chaine += joueurActuel.getNumeroJoueur()+"\n";
            for (int i = 0; i < postionBaseJoueur.size(); i++) {
                chaine +="[";
                chaine += postionBaseJoueur.get(i).get(0)+","+ postionBaseJoueur.get(i).get(1)+","+plateau.get(postionBaseJoueur.get(i).get(0)).get(postionBaseJoueur.get(i).get(1)).getBatiment().getPointDeVieActuel();
                if (i<postionBaseJoueur.size()-1) {
                    chaine+="];";
                }
            }
            chaine +="]\n";
            chaine += finpartie;
			
			fw.write(chaine);
			fw.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erreur ecriture");
		}

    }

    public void chargerCarte(FileInputStream file){
        String line = new String();
        Scanner scanner = new Scanner(file); 
        String [] strValues1;
        int[][] listeUnite = new int[50][50];
        for (int i = 0; i < plateau.size(); i++) {
            line = scanner.nextLine();
            strValues1 = line.split(",");
            chargeLineMap(line,i,listeUnite);
        }
    }


    public static void chargePartie(FileInputStream file) {
        
        //try {

            String line = new String();
		    String [] strValues1;
            String [] strValues2;
            String [] strValues3;
			Scanner scanner = new Scanner(file);  
            

            line = scanner.nextLine();
            strValues1 = line.split(";");

            int[][] listeUnite = new int[50][50];

            for (int i = 0; i < strValues1.length; i++) {
                strValues1[i] = strValues1[i].replace("[", "");
                strValues1[i] = strValues1[i].replace("]", "");
                strValues2 = strValues1[i].split(",");
                Joueur joueur = new Joueur(strValues2[0],Boolean.parseBoolean(strValues2[1]));
                listeJoueur.add(joueur);
                joueur.setPieces(Integer.parseInt(strValues2[2]));

                strValues2[3] = strValues2[3].replace("{", "");
                strValues2[3] = strValues2[3].replace("}", "");
                if(strValues2[3].length() > 0){
                    strValues3 = strValues2[3].split(".");
                    for (int j = 0; j < strValues3.length; j++) {
                        listeUnite[i][j]=Integer.parseInt(strValues3[j]);
                    }
                }
                else{
                    listeUnite[i][0] = -1 ;
                }
            }
            
            for (int i = 0; i < plateau.size(); i++) {
                line = scanner.nextLine();
                strValues1 = line.split(",");
                chargeLineMap(line,i,listeUnite);
            }
            
            line = scanner.nextLine();
            tour = Integer.parseInt(line);
            line = scanner.nextLine();
            joueurActuel = listeJoueur.get(Integer.parseInt(line));

            line = scanner.nextLine();
            strValues1 = line.split(";");
            
            for (int i = 0; i < strValues1.length; i++) {
                strValues1[i] = strValues1[i].replace("[", "");  
                strValues1[i] = strValues1[i].replace("]", "");
                strValues2 = strValues1[i].split(",");
                placerBaseJoueur(listeJoueur.get(i), Integer.parseInt(strValues2[0]), Integer.parseInt(strValues2[1]));
                
            }

            line = scanner.nextLine();
            finpartie = Boolean.parseBoolean(line);

	        scanner.close();    
        //} catch (Exception e) {
            //TODO: handle exception
            //System.out.println("Erreur lecture");
        //}
    }

    public static void sauvegardeMap(){
        
        try {
            System.out.println("Tapez la map a sauvegarder");
            Scanner sc = new Scanner(System.in);
            int saisie = sc.nextInt();
            String fichier;

            switch (saisie) {
                case 1:
                fichier = "Desert";
                plateau.replace(new Desert());
                    break;
                case 2:
                fichier = "Foret";
                plateau.replace(new Foret());
                    break;
                case 3:
                fichier = "Mer";
                plateau.replace(new Mer());
                    break;
                case 4:
                fichier = "Montagne";
                plateau.replace(new Montagne());
                    break;
                case 5:
                fichier = "ToundraNeige";
                plateau.replace(new ToundraNeige());
                    break;
                default:
                fichier = "Plaine";
                plateau.replace(new Plaine());
                    break;
            }
            sc.close();
            File file = new File("src\\data\\cartes\\default\\"+fichier+".txt");
			
			if (!file.exists()) {
			    file.createNewFile();
			   }
		
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			String chaine = new String();
            chaine = sauvegardeStringMap(chaine);
            fw.write(chaine);
            fw.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public static String sauvegardeStringMap(String chaine) {
        for (int i = 0; i < plateau.size(); i++) {
            for (int j = 0; j < plateau.size(); j++) {
                    /// Ajout du premier [ pour chaque case
                    chaine+="[";

                    /// Déclaration des variables utiles
                    Terrain terrain = plateau.get(i).get(j).getTerrain();
                    Unite unite = plateau.get(i).get(j).getUnite();
                    Batiment batiment = plateau.get(i).get(j).getBatiment();
                    String [] splStrings;
                    String chaineTerrain = new String();
                    String chaineUnite = new String();
                    String chaineBatiment = new String();

                    /// Recuperation du type de Terrain
                    chaineTerrain = terrain.toString();
                    splStrings = chaineTerrain.split(":");
                    chaineTerrain = splStrings[0];
                    splStrings = chaineTerrain.split(" ");
                    chaine += splStrings[0]+":";

                    /// Unite
                    if (!(unite == null)) {
                        chaineUnite = unite.toString()+","+unite.getIdentifiant()+","+unite.getPointDeVieActuel()+","+unite.getDeplacementActuel()+","+unite.getAAttaque()+","+unite.getEnRepos();
                    }
                    chaine += chaineUnite+":";

                    /// Batiment
                    if (!(batiment == null) && (batiment.getEstBase() != TypeBatiment.BASE)){
                        chaineBatiment = batiment.toString()+","+batiment.getIdentifiant()+","+batiment.getPointDeVieActuel();
                    }
                    chaine += chaineBatiment;

                    /// Ajout de fin
                    chaine+="]";
                    if (j<plateau.size()-1) {
                    chaine+=";";
                }
            }
            chaine+="\n";
        }
        return chaine;
    }

    public static void chargeLineMap(String line,int iline,int [][] listeUnite) {
        
        String [] spl1;
        String [] spl2;

        spl1 = line.split(";");

        for (int i = 0; i < spl1.length; i++) {
            spl1[i] = spl1[i].replace("[", "");
            spl1[i] = spl1[i].replace("]", "");
            spl2 = spl1[i].split(":");   
            switch (spl2[0]) {
                case "Plaine":
                    plateau.get(iline).get(i).setTerrain(new Plaine());
                    break;
                case "Desert":
                    plateau.get(iline).get(i).setTerrain(new Desert());
                    break; 
                case "Foret":
                    plateau.get(iline).get(i).setTerrain(new Foret());
                    break; 
                case "Mer":
                    plateau.get(iline).get(i).setTerrain(new Mer());
                    break; 
                case "Montagne":
                    plateau.get(iline).get(i).setTerrain(new Montagne());
                    break; 
                case "ToundraNeige":
                    plateau.get(iline).get(i).setTerrain(new ToundraNeige());
                    break; 
                default:
                    plateau.get(iline).get(i).setTerrain(new Plaine());
                    break;
            }
            if(spl2.length == 2){
                plateau.get(iline).get(i).setUnite(analyseSplUnite(spl2[1],listeUnite));
            } 
            else if (spl2.length == 3){
                plateau.get(iline).get(i).setBatiment(analyseSplBatiment(spl2[2]));
            }
        }
    }
/*
            Debut Jeu

                Plateau
                Joueur (2 ou 4)

                A- Debut Jeu

                    1- Choix base joueur
                        Pour chaque joueur place la base
                    
                B- Deroulement Tour - Boucle partie : (tant que fin partie ou conditionVictoire())

                Si joueur.getEnJeu() == true

                    1- Regeneration Unite Armee + gain banque joueur

                    2- Achat unité

                        2.a- Boucle de choix : (tant que fin d'achat)
                                Choix unité
                        
                    3- Deplacement / Combat

                        3.a- Boucle d'action : (tant que fin de tour)
                                Selection 2 case : 

                                    Test choix case initial
                                    Test choix case final

                                        Selon choix : 
                                                - Deplacement (1er : case par case ; 2eme : algo plus cours chemin entre 2 case + verifie si libre)
                                                    Si la case initial est une unité
                                                    Si la case final est vide

                                                - Combat (+ fin combat verifier conditionVictoire() -> finpartie)
                                                    Si la case initial est une unité ou la base
                                                    Si la case final est une unité ou un batiment           

                                        Retour debut boucle 3.a
                    
                Retour debut boucle B (avec changement joueur)

    Fonction 



    Joueur
        * placer la base 
        * placer l'armee + conditions de placement en fonction de la base
        * Acheter des unites

    Tour de jeu
        * deplacement des unite
        * combat
        * attaque batiment
        * calcul regeneration unite => Debut de tour
            - si tu ne te deplace pas
            - si tu n'attaque pas
            - si tu n'a pas defendu
         condition de victoire
            - * destruction de l'armee
            - nombre de tours //while
            - * destruction de base
        
        
        sauvegarde les resultats
        tour ia #analyse
    
    Outil
        * Coord to Case
        //Case to Coord

    Extentions facultatives
        - Mode de jeu campagne - Editeur de scenario => Lucas
        - Evenement exterieur => Reda
        - Action d'opportunité => Triomphante 1
        - Ligne de tir calcul de case et de l'occupation des cases => Reda
        - timer : X secondes de jeu a chaque tour => Triomphante 2

    Sauvegarde
        Partie:
        - Plateau
            - Case
                - Terrain
                - Unite/Batiment
        -Joueur
            - Armee
            - Piece
        
        JSON FILE      

    Main
        Deroulement du jeu
        Menu
        Tour de joueur 
    */

}

