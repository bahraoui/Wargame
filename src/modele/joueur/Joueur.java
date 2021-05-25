package modele.joueur;

import java.util.ArrayList;

import modele.entite.batiment.Batiment;
import modele.entite.unite.Unite;


public class Joueur {
    private Batiment base;
    private ArrayList<Unite> armee;
    private int pieces;
    private int numeroJoueur;
    private static int compteur = 0;
    private boolean estIa;
    private boolean enJeu;
    private String pseudo;

    public Joueur(String pseudo,boolean parEstIa){
        this.pseudo = pseudo;
        this.numeroJoueur = compteur++;
        this.pieces = 40;
        this.estIa = parEstIa;
        this.enJeu = true;
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
    public Batiment getBase() {
        return base;
    }
    public boolean getEnJeu() {
        return enJeu;
    }
    public String getPseudo() {
        return pseudo;
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
    public void setBase(Batiment base) {
        this.base = base;
    }
    public void setEnJeu(boolean enJeu) {
        this.enJeu = enJeu;
    }
    public void setPseudo(String pseudo){
        this.pseudo=pseudo;
    }
}
