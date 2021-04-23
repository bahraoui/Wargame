import modele.plateau.Plateau;
import modele.terrain.Terrain;
import modele.unite.Archer;
import modele.unite.Infanterie;
import modele.unite.InfanterieLourde;
import modele.unite.Mage;
import modele.unite.Unite;
import modele.batiment.Batiment;
import modele.batiment.TypeBatiment;
import modele.joueur.Joueur;
import modele.plateau.Case;

import java.util.ArrayList;
import java.util.Random;


public class Jeu {
    private static Plateau plateau = new Plateau();
    private static ArrayList<ArrayList<Integer>> postionBaseJoueur = new ArrayList<ArrayList<Integer>>();
    private static ArrayList<Joueur> listeJoueur = new ArrayList<Joueur>();

    public void Jeu() {

    }
    
    public static void main(String[] args) {
        Joueur j1 = new Joueur(false);
        Joueur j2 = new Joueur(false);
        listeJoueur.add(j1);
        listeJoueur.add(j2);


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
        combat(coordToCase(1, 6), coordToCase(14, 12));
    }

    public static void combat(Case attaquant, Case defenseur){
        int defense = (int)(defenseur.getUnite().getDefense() * defenseur.getTerrain().getBonusDefense());
        int degat = attaquant.getUnite().getAttaque() - defense + 80;
        System.out.println(defense);
        System.out.println(degat);
        if (degat >= defense){
            defenseur.getUnite().setPointDeVie(defenseur.getUnite().getPointDeVie() - degat);
        }
        else {
            Random random = new Random();
            defenseur.getUnite().setPointDeVie(defenseur.getUnite().getPointDeVie() - random.nextInt(5));
        }

        if (defenseur.getUnite().getPointDeVie() <= 0){
            mortUnite(defenseur);
            System.out.println("Unite tue");
        }
    }

    public static void mortUnite(Case defenseur){
        for (int i = 0; i < listeJoueur.size(); i++) {
            for (int j = 0; j < listeJoueur.get(i).getArmee().size(); j++){
                if (defenseur.getUnite().getIdentifiant() == listeJoueur.get(i).getArmee().get(j).getIdentifiant()){
                    listeJoueur.get(i).getArmee().remove(j);
                    defenseur.setUnite(null);
                    return;
                }
            }
        } 
    }

    public static void placerBaseJoueur(Joueur joueur, int coordY, int coordX){
        Batiment base = new Batiment(100, 100, 100, 5, TypeBatiment.BASE);
        joueur.setBatiment(base);
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
        if (caseUnite.getBatiment() == null && caseUnite.getUnite() == null && Math.abs(calculVisionY) <= joueur.getBatiment().getVision() && Math.abs(calculVisionX) <= joueur.getBatiment().getVision() && achatUniteArmee(joueur,unite)) {
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

    /*
    public static int CasetoCoord(Case parCase){
        return 0;
    }
    */

    public static void deplacementUnite(Unite unite, Case caseInitial, Case caseFinal){
        int deplacementTerrain = caseInitial.getTerrain().getPtsDeplacement();
        if (caseInitial.getBatiment() == null && caseInitial.getUnite() != null && caseFinal.getBatiment() == null && caseFinal.getUnite() == null && unite.getDeplacement() > 0 && unite.getDeplacement() >= deplacementTerrain){
            unite.setDeplacement(unite.getDeplacement() - deplacementTerrain);
            caseFinal.setUnite(unite);
            caseInitial.setUnite(null);
            System.out.println("Unite deplace");
        }
        else {
            System.out.println("Case trop loin ou point de deplacement insufisant, ou case occupé");
        }
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
        calcul regeneration unite
        tour ia #analyse
        condition de victoire
            - destruction de l'armee
            - nombre de tours
            - destruction de base
        sauvegarde les resultats
    
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

