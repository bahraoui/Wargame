package modele.joueur;

import java.util.ArrayList;

import modele.batiment.Batiment;
import modele.unite.Unite;


public class Joueur {
    private Batiment batiment;
    private ArrayList<Unite> armee;
    private int pieces;
    private int numeroJoueur;
    private static int compteur = 0;
    private boolean estIa;

    public Joueur(boolean parEstIa){
        this.numeroJoueur = compteur++;
        this.pieces = 50;
        this.estIa = parEstIa;
        this.armee = new ArrayList<Unite>();
    }

    /*
    sauvegarde les donnes joueurs
    choisir unite de l'armee
        - manuellement/aleatoire pour le joueur
        - aleatoire pour IA
    */

    public boolean getEstIa() {
        return estIa;
    }
    public int getNumeroJoueur() {
        return numeroJoueur;
    }
    public ArrayList<Unite> getArmee() {
        return armee;
    }
    public int getPieces() {
        return pieces;
    }
    public Batiment getBatiment() {
        return batiment;
    }

    public void setEstIa(boolean estIa) {
        this.estIa = estIa;
    }
    public void setArmee(ArrayList<Unite> armee) {
        this.armee = armee;
    }
    public void setPieces(int pieces) {
        this.pieces = pieces;
    }
    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }
}
