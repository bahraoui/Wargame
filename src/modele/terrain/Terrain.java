//package
package modele.terrain;

/**
 * La classe Terrain représente les types terrains du jeu "Wargame"
 * 
 * La classe Terrain est la classe mère de {@link Foret}, de {@link Desert} , de {@link Mer} , de {@link Montagne}, de {@link Plaine} et  {@link ToundraNeige}
 * Elle posède 2 champs :
 * ptsDeplacement représente les points de déplacement de coute ce terrain
 * bonusDefense représente les points de defense bonus qu'attribue ce terrain
 * Avec pour chaque champs des getters et des setters
 * Elle possede une methode d'affichage pour terminal
 */
public class Terrain {
    private int ptsDeplacement;
    private double bonusDefense;

    /**
     * Constructeur de la classe
     */
    public Terrain() {
    }

    /**
     * Affichage du plateau pour terminal
     */
    public String toString(){
        return "Point de placement " + this.ptsDeplacement+ " Point de defense  " + this.bonusDefense ;
    }

    //
    //Getter et Setters
    //
    public double getBonusDefense() {
        return bonusDefense;
    }
    public int getPtsDeplacement() {
        return ptsDeplacement;
    }

    public void setBonusDefense(double bonusDefense) {
        this.bonusDefense = bonusDefense;
    }
    public void setPtsDeplacement(int ptsDeplacement) {
        this.ptsDeplacement = ptsDeplacement;
    }
}
