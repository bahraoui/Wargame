//package
package modele.joueur;

//import
import java.util.ArrayList;

import modele.entite.Entite;
import modele.entite.batiment.Batiment;
import modele.entite.batiment.TypeBatiment;
import modele.entite.unite.Unite;
import modele.plateau.Case;

/**
 * La classe Joueur représente les joueurs du jeu "Wargame"
 * 
 * La classe Joueur posède 9 champs :
 * base représente la base d'un joueur
 * armee représente l'armée d'un joueur
 * pieces représente les pieces d'un joueur
 * numeroJoueur représente le numéro d'un joueur
 * compteur permet de générer les numéros d'un joueur
 * estIa indique si le joueur est une IA
 * identifiantCible représente la cible que doit attaquer l'IA
 * enJeu représente si un joueur est toujours en jeu
 * pseudo représente le pseudo un joueur
 * Avec pour chaque champs des getters et des setters
 */
public class Joueur {
    private Batiment base;
    private ArrayList<Unite> armee;
    private int pieces;
    private int numeroJoueur;
    private static int compteur = 0;
    private boolean estIa;
    //private int identifiantCible;
    private boolean enJeu;
    private String pseudo;

    /**
     * Constructeur de la classe Joueur
     * @param pseudo le pseudo du joueur
     * @param parEstIa True si le joueur est une IA, False sinon
     */
    public Joueur(String pseudo,boolean parEstIa){
        this.pseudo = pseudo;
        this.numeroJoueur = compteur++;
        this.pieces = 40;
        this.estIa = parEstIa;
        this.enJeu = true;
        this.armee = new ArrayList<Unite>();
        //this.identifiantCible = -1;
    }

    public boolean achatUniteArmee(Unite unite){
        if (this.getPieces() >= unite.getCout()){
            this.setPieces(this.getPieces()-unite.getCout());
            return true;
        }
        else {
            return false;
        }
    }

    public void regenerationUniteArmee() {
        for(int i = 0; i<this.getArmee().size();i++) {
            if (this.getArmee().get(i).getEnRepos() == true) {
                int pointDeVieGagne = this.getArmee().get(i).getPointDeVieActuel() + (int)(this.getArmee().get(i).getPointDeVieMax() * 0.1);
                if (pointDeVieGagne > this.getArmee().get(i).getPointDeVieMax()) {
                    this.getArmee().get(i).setPointDeVieActuel(this.getArmee().get(i).getPointDeVieMax());
                }
                else {
                    this.getArmee().get(i).setPointDeVieActuel(pointDeVieGagne);
                }
            }
            else {
                this.getArmee().get(i).setEnRepos(true);
            }
            this.getArmee().get(i).setAAttaque(false);
            this.getArmee().get(i).setDeplacementActuel(this.getArmee().get(i).getDeplacementMax());
        }
    }

    public void gainTourJoueur(int tour) {
        int pieceGain = (int) (tour * 0.2 + 4);
        this.setPieces(this.getPieces()+pieceGain);
    }


    public boolean estMonEntite(Case caseClic1) {
        Entite entite = (Entite) caseClic1.estOccupe();
        if (entite instanceof Unite){
            for (int i = 0; i < this.armee.size(); i++) {
                if (entite.getIdentifiant() == armee.get(i).getIdentifiant())
                    return true;
            }
        }
        else if (entite instanceof Batiment){
            if (caseClic1.getBatiment().getEstBase() == TypeBatiment.BASE && this.getBase().getIdentifiant() == caseClic1.getBatiment().getIdentifiant()){
                return true;
            }
        }
        return false;
    }

    //
    //Getters & Setters
    //
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

    public void setNumeroJoueur(int numeroJoueur) {
        this.numeroJoueur = numeroJoueur;
    }

    public boolean isEstIa() {
        return this.estIa;
    }

    /*public int getIdentifiantCible() {
        return this.identifiantCible;
    }

    public void setIdentifiantCible(int identifiantCible) {
        this.identifiantCible = identifiantCible;
    }*/

    public boolean isEnJeu() {
        return this.enJeu;
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
