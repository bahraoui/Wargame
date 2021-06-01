//package
package modele.entite;

/**
 * La classe Entite représente les unites et les batiments du jeu "Wargame"
 * 
 * La classe Entite est la classe mère de {@link Batiment} et {@link Unite}
 * Elle posède 6 champs :
 * pointDeVieActuel représente les points de vie d'une entité
 * attaque représente les points d'attaque d'une entité
 * defense représente les points de défense d'une entité
 * vision représente le champ de vision d'une entité
 * identifiant représente le numéro d'identfiant d'une entité
 * compteur permet de générer des identifiants
 * Avec pour chaque champs des getters et des setters
 * Elle possede une methode d'affichage pour terminal
 */
public class Entite {
    private int pointDeVieActuel;
    private int attaque;
    private int defense;
    private int vision;
    private int identifiant;
    private static int compteur = 0;

    /**
     * Constructeur de la classe Entite
     * @param parPointDeVieActuel point de vie actuel d'une entite
     * @param parAttaque point d'attaque d'une entite
     * @param parDefense point de défense d'une entite
     * @param parVision champs de vision d'une entite
     */
    public Entite(int parPointDeVieActuel, int parAttaque, int parDefense, int parVision) {
        this.pointDeVieActuel = parPointDeVieActuel;
        this.attaque = parAttaque;
        this.defense = parDefense;
        this.vision = parVision;
        identifiant = compteur++;
    }

    /**
     * Constructeur vide
     */
    public Entite() {
    }

    //
    //Getters et Setters
    //
    public int getAttaque() {
        return attaque;
    }
    public int getDefense() {
        return defense;
    }
    public int getPointDeVieActuel() {
        return pointDeVieActuel;
    }
    public int getVision() {
        return vision;
    }
    public int getIdentifiant() {
        return identifiant;
    }
    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setPointDeVieActuel(int pointDeVieActuel) {
        this.pointDeVieActuel = pointDeVieActuel;
    }
    public void setVision(int vision) {
        this.vision = vision;
    }
    public void setIndentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
    public static int getCompteur(){
        return Entite.compteur ;
    }
    public static void setCompteur(int compt){
        Entite.compteur = compt;
    }
}
