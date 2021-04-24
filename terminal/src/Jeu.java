import modele.plateau.Plateau;
import modele.terrain.Terrain;
import modele.entite.Entite;
import modele.entite.unite.Archer;
import modele.entite.unite.Infanterie;
import modele.entite.unite.InfanterieLourde;
import modele.entite.unite.Mage;
import modele.entite.unite.Unite;
import modele.entite.Entite;
import modele.entite.batiment.Batiment;
import modele.entite.batiment.TypeBatiment;
import modele.joueur.Joueur;
import modele.plateau.Case;

import java.util.ArrayList;
import java.util.Random;


public class Jeu {
    private static Plateau plateau = new Plateau();
    private static ArrayList<ArrayList<Integer>> postionBaseJoueur = new ArrayList<ArrayList<Integer>>();
    private static ArrayList<Joueur> listeJoueur = new ArrayList<Joueur>();
    private static Joueur joueurActuel;

    public void Jeu() {

    }
    
    public static void main(String[] args) {
        Joueur j1 = new Joueur(false);
        Joueur j2 = new Joueur(false);
        listeJoueur.add(j1);
        listeJoueur.add(j2);
        joueurActuel = j1;


        Infanterie inf = new Infanterie();
        InfanterieLourde infLourde = new InfanterieLourde();
        Archer archer = new Archer();
        Mage mage = new Mage();

        placerBaseJoueur(j1,0,1);        
        placerUniteJoueur(j1,inf,1,6);
        placerUniteJoueur(j1,infLourde,1,5);

        placerBaseJoueur(j2,14,14);        
        placerUniteJoueur(j2,mage,14,12);
        placerUniteJoueur(j2,archer,13,15);
        //mage.setPointDeVieActuel(30);
        //regenerationUniteArmee(j2);
        //combat(coordToCase(1, 6), coordToCase(14, 12));
        //System.out.println(mage);
        //System.out.println(archer);
        //System.out.println(plateau.affichage());
        //j2.getBase().setPointDeVieActuel(1);
        //j2.getBase().setDefense(0);
        //System.out.println(conditionVictoire());
        //combat(coordToCase(1, 6), coordToCase(14, 14));
        //j2.getArmee().remove(0);
        //j2.getArmee().remove(0);
        //j2.setPieces(5);
        //System.out.println(conditionVictoire());
        System.out.println(plateau.affichage());
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
        /*
        1<x<15
        y<15
        */
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
        }
    }

    public static boolean conditionVictoire(){
        if (conditionBase() || conditionPiece()) {
            return true;
        }
        return false;

        
    }

    public static boolean conditionBase(){
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


    /*
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

