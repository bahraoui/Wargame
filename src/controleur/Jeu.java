package controleur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import Vue.Hexagone;
import Vue.PanelActuel;
import Vue.FrameJeu;
import Vue.PanelJeu;
import modele.entite.Entite;
import modele.entite.batiment.Batiment;
import modele.entite.batiment.TypeBatiment;
import modele.entite.unite.Unite;
import modele.joueur.Joueur;
import modele.plateau.Case;
import modele.plateau.Plateau;


public class Jeu implements ActionListener {
    private static Plateau plateau;
    private static ArrayList<ArrayList<Integer>> postionBaseJoueur;
    private static ArrayList<Joueur> listeJoueur;
    private static Joueur joueurActuel;
    private static int tour;
    private static boolean finpartie;
    private static FrameJeu FenetreJeu;
    private static Integer nbJoueursH;
    private static Integer nbJoueursIA;

    /*public void Jeu() {

    }*/
    
    public static void main(String[] args) throws IOException, InterruptedException {

        Jeu controleur = new Jeu();

        finpartie = false;
        nbJoueursH = nbJoueursIA = 0;
        listeJoueur = new ArrayList<Joueur>();
        postionBaseJoueur = new ArrayList<ArrayList<Integer>>();
        plateau = new Plateau();
            
        /// Initialisation plateau joueur

        Joueur j1 = new Joueur(false);
        Joueur j2 = new Joueur(false);
        Joueur j3 = new Joueur(false);
        Joueur j4 = new Joueur(false);
        listeJoueur.add(j1);
        listeJoueur.add(j2);
        listeJoueur.add(j3);
        listeJoueur.add(j4);
        joueurActuel = j1;

        PanelJeu pj = new PanelJeu();
        Hexagone cells[][] = pj.getCells();
        FenetreJeu = new FrameJeu(pj);
        FenetreJeu.enregistreEcouteur(controleur);

        tour = 0;

        

        /// Placement base

        /*
        int x,y = 0;
        for (int i = 0; i < listeJoueur.size(); i++) {
            do {
                System.out.println("Placer votre base");
                Scanner sc = new Scanner(System.in);
                y = sc.nextInt();
                x = sc.nextInt();
                System.out.println("y : "+y+"x : "+x);
            } while (!testCoordBase(y, x));
            placerBaseJoueur(listeJoueur.get(i),y,x);
            System.out.println(plateau.affichage());
        }
        */

        /// Deroulement Tour

        ///do {
            
            regenerationUniteArmee(joueurActuel);
            gainTourJoueur(joueurActuel);

        ///} while (!finpartie || !conditionVictoire());

    }

    public static void combat(Case attaquant, Case defenseur){
        Entite def = new Entite();
        Entite att = new Entite();
        if (defenseur.getBatiment() != null) {
            def = defenseur.getBatiment();
        }
        else if (defenseur.getUnite() != null) {
            def = defenseur.getUnite();
            defenseur.getUnite().setEnRepos(false);
        }
        if (attaquant.getBatiment() != null) {
            att = attaquant.getBatiment();
        }
        else if (attaquant.getUnite() != null) {
            att = attaquant.getUnite();
            attaquant.getUnite().setEnRepos(false);
            attaquant.getUnite().setAAttaque(true);
        }
        int defense = (int)(def.getDefense() * defenseur.getTerrain().getBonusDefense());
        int degat = att.getAttaque() - defense;
        //System.out.println(defense);
        //System.out.println(degat);        
        if (degat >= defense){
            def.setPointDeVieActuel(def.getPointDeVieActuel() - degat);
        }
        else {
            Random random = new Random();
            def.setPointDeVieActuel(def.getPointDeVieActuel() - random.nextInt(5));
        }

        if (def.getPointDeVieActuel() <= 0){
            mortEntite(defenseur);
        }
    }


    public static void mortEntite(Case defenseur){
        if (defenseur.getUnite() != null) {
            for (int i = 0; i < listeJoueur.size(); i++) {
                for (int j = 0; j < listeJoueur.get(i).getArmee().size(); j++){
                    if (defenseur.getUnite().getIdentifiant() == listeJoueur.get(i).getArmee().get(j).getIdentifiant()){
                        listeJoueur.get(i).getArmee().remove(j); //armee
                        defenseur.setUnite(null); //plateau
                        System.out.println("Unite tue");
                        if (listeJoueur.get(i).getArmee().size() > 0 || listeJoueur.get(i).getPieces() > 10){
                            listeJoueur.get(i).setEnJeu(false);
                            System.out.println("Armee aneanti joueur eliminé");
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
                        plateau.get(coordBase.get(0)).get(coordBase.get(1)-1).setBatiment(null);
                        plateau.get(coordBase.get(0)).get(coordBase.get(1)+1).setBatiment(null);
                        plateau.get(coordBase.get(0)+1).get(coordBase.get(1)).setBatiment(null);
                        System.out.println("Base detruite");
                    }
                }
            }
            else {
                joueurActuel.setPieces(joueurActuel.getPieces() + defenseur.getBatiment().getTresor());
                defenseur.setBatiment(null); //plateau
                System.out.println("Batiment detruit");
            }
        }
    }

    public static void placerBaseJoueur(Joueur joueur, int coordY, int coordX){
        Batiment base = new Batiment(100, 100, 100, 5, TypeBatiment.BASE);
        joueur.setBase(base);
        plateau.get(coordY).get(coordX).setBatiment(base);
        plateau.get(coordY).get(coordX-1).setBatiment(base);
        plateau.get(coordY).get(coordX+1).setBatiment(base);
        plateau.get(coordY+1).get(coordX).setBatiment(base);
        ArrayList<Integer> baseJ1 = new ArrayList<Integer>();
        baseJ1.add(0,coordY);
        baseJ1.add(1,coordX);
        postionBaseJoueur.add(baseJ1);
    }

    public static boolean testCoordBase(int coordY, int coordX){
        if (coordX >= 1 && coordX <= 14 && coordY <=14 && plateau.get(coordY).get(coordX).getBatiment() == null && plateau.get(coordY).get(coordX-1).getBatiment() == null && plateau.get(coordY).get(coordX+1).getBatiment() == null && plateau.get(coordY+1).get(coordX).getBatiment() == null){
            return true;
        }
        return false;
    }

    public static void placerUniteJoueur(Joueur joueur, Unite unite, int coordY, int coordX){
        Case caseUnite = plateau.get(coordY).get(coordX);
        int coordYBase = postionBaseJoueur.get(joueur.getNumeroJoueur()).get(0);
        int coordXBase = postionBaseJoueur.get(joueur.getNumeroJoueur()).get(1);
        int calculVisionY = coordY - coordYBase;
        int calculVisionX = coordX - coordXBase;
        if (caseUnite.getBatiment() == null && caseUnite.getUnite() == null && Math.abs(calculVisionY) <= joueur.getBase().getVision() && Math.abs(calculVisionX) <= joueur.getBase().getVision() && achatUniteArmee(joueur,unite)) {
            joueur.getArmee().add(unite);
            System.out.println("Unite ajoute a l'armee");
            plateau.get(coordY).get(coordX).setUnite(unite);
            System.out.println("Unite ajoute au plateau");
        }
        else {
            System.out.println("Unite trop loin ou sur une case occupe ");
        }

    }

    public static boolean achatUniteArmee(Joueur joueur, Unite unite){
        if (joueur.getPieces() >= unite.getCout()){
            joueur.setPieces(joueur.getPieces()-unite.getCout());
            return true;
        }
        else {
            System.out.println("Vous n'avez pas assez de piece pour acheter l'unite");
            return false;
        }
    }

    public static Case coordToCase(int coordY, int coordX){
        return plateau.get(coordY).get(coordX);
    }


    public static void deplacementUnite(Unite unite, Case caseInitial, Case caseFinal){
        int deplacementTerrain = caseInitial.getTerrain().getPtsDeplacement();
        if (caseInitial.getBatiment() == null && caseInitial.getUnite() != null && caseFinal.getBatiment() == null && caseFinal.getUnite() == null && unite.getDeplacementActuel() > 0 && unite.getDeplacementActuel() >= deplacementTerrain){
            unite.setDeplacementActuel(unite.getDeplacementActuel() - deplacementTerrain);
            caseFinal.setUnite(unite);
            caseInitial.setUnite(null);
            caseFinal.getUnite().setEnRepos(false);
            System.out.println("Unite deplace");
        }
        else {
            System.out.println("Case trop loin ou point de deplacement insufisant, ou case occupé");
        }
    }

    //DebutTour
    public static void regenerationUniteArmee(Joueur joueur) {
        for(int i = 0; i<joueur.getArmee().size();i++) {
            if (joueur.getArmee().get(i).getEnRepos() == true) {
                int pointDeVieGagne = joueur.getArmee().get(i).getPointDeVieActuel() + (int)(joueur.getArmee().get(i).getPointDeVieMax() * 0.1);
                if (pointDeVieGagne > joueur.getArmee().get(i).getPointDeVieMax()) {
                    joueur.getArmee().get(i).setPointDeVieActuel(joueur.getArmee().get(i).getPointDeVieMax());
                }
                else {
                    joueur.getArmee().get(i).setPointDeVieActuel(pointDeVieGagne);
                }
            }
            else {
                joueur.getArmee().get(i).setEnRepos(true);
            }
            joueur.getArmee().get(i).setAAttaque(false);
        }
    }

    public static void gainTourJoueur(Joueur joueur) {
        int pieceGain = (int) (tour * 0.2 + 4);
        joueur.setPieces(joueur.getPieces()+pieceGain);
    }

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
            		combat(postionOpportunite, positionFinalJoueur);
            	}  
	        }
	        else if (postionOpportunite.getUnite() != null && listeJoueur.get(i).getArmee().get(i).getEnRepos() == true) {
	            att = postionOpportunite.getUnite();
	            if(positionFinalJoueur.getUnite() != att && (calculVisionOppX <= att.getVision() && calculVisionOppY <= att.getVision())){
	            	deplacementUnite(postionOpportunite.getUnite(), postionOpportunite, caseAttaque);
	            	combat(postionOpportunite, positionFinalJoueur);
            	}
	        }
        }
    }

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
                System.out.println("Nouvelle partie !");
                FenetreJeu.changePanel(PanelActuel.NOUVELLEPARTIE);
            }
            /**
             * Bouton "Charger Partie"
             */
            else if (evt.getActionCommand().equals("chargerPartie")) {
                System.out.println("Charger partie !");
                FenetreJeu.changePanel(PanelActuel.CHARGERPARTIE);
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
                // TEST combo box nbjoueursH et nbJoueursIA =>
                //System.out.println("nb joueurs humain : " + String.valueOf(nbJoueursH) + "\n###\nb joueurs IA : " + String.valueOf(nbJoueursIA));
                FenetreJeu.changePanel(PanelActuel.JEU);
            }
        }
        /**
         * Clic sur une liste
         */
        else if (evt.getSource() instanceof JComboBox) {
            if (evt.getActionCommand().equals("nbJoueursH")) {
                JComboBox<Integer> nbH = (JComboBox<Integer>) evt.getSource();
                nbJoueursH = (Integer) nbH.getSelectedItem();
            } 
            else if (evt.getActionCommand().equals("nbJoueursIA")) {
                JComboBox<Integer> nbIA = (JComboBox<Integer>) evt.getSource();
                nbJoueursIA = (Integer) nbIA.getSelectedItem();
            }
        }
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

