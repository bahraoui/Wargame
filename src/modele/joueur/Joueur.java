//package
package modele.joueur;

//import
import java.util.ArrayList;
import modele.entite.batiment.Batiment;
import modele.entite.unite.Unite;

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
    private int identifiantCible;
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
        this.identifiantCible = -1;
    }

    public static boolean achatUniteArmee(Joueur joueur, Unite unite){
        if (joueur.getPieces() >= unite.getCout()){
            joueur.setPieces(joueur.getPieces()-unite.getCout());
            return true;
        }
        else {
            return false;
        }
    }

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

    public static void gainTourJoueur(Joueur joueur, int tour) {
        int pieceGain = (int) (tour * 0.2 + 4);
        joueur.setPieces(joueur.getPieces()+pieceGain);
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
