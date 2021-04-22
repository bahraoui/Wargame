import modele.plateau.Plateau;
import modele.unite.Infanterie;
import modele.unite.Unite;
import modele.batiment.Batiment;
import modele.batiment.TypeBatiment;
import modele.joueur.Joueur;
import modele.plateau.Case;

import java.util.ArrayList;


public class Jeu {
    private static Plateau plateau = new Plateau();
    private static ArrayList<ArrayList<Integer>> postionBaseJoueur = new ArrayList<ArrayList<Integer>>();

    public void Jeu() {

    }
    
    public static void main(String[] args) {
        Joueur j1 = new Joueur(0,false);
        Infanterie inf = new Infanterie();
        placerBaseJoueur(j1,0,1);        
        placerUniteJoueur(j1,inf,1,6);
        //System.out.println(plateau);
        
    }

    public void combat(Case attaquant, Case defenseur){
        int defense = defenseur.getUnite().getDefense() * defenseur.getTerrain().getBonusDefense();
        int attaque = attaquant.getUnite().getAttaque() - defense;
        //test positif
        //aleatoire
        defenseur.getUnite().setPointDeVie(defenseur.getUnite().getPointDeVie() - attaque);
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
        if (caseUnite.getBatiment() == null && caseUnite.getUnite() == null && Math.abs(calculVisionY) <= joueur.getBatiment().getVision() && Math.abs(calculVisionX) <= joueur.getBatiment().getVision()) {
            System.out.println("Unite ajoute a l'armee ");
            joueur.getArmee().add(unite);
        }
        else {
            System.out.println("Unite trop loin ou sur une case occupe ");
        }

    }



    /*
    Fonction 

    Joueur
        placer la base
        placer l'armee + conditions de placement en fonction de la base
        Acheter des unites

    Tour de jeu
        deplacement des unite
        combat
        calcul regeneration unite
        tour ia #analyse
        condition de victoire
            - destruction de l'armee
            - nombre de tours
            - destruction de base
        sauvegarde les resultats
    

    Extentions facultatives
        - Mode de jeu campagne - Editeur de scenario => Lucas
        - Evenement exterieur => Reda
        - Action d'opportunité => Triomphante 1
        - Ligne de tir calcul de case et de l'occupation des cases => Reda
        - timer : X secondes de jeu a chaque tour => Triomphante 2



    Main
        Deroulement du jeu
        Menu
        Tour de joueur 
    */
}

