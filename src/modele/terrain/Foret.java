//package
package modele.terrain;

/**
 * La classe Foret représente le type terrain foret du jeu "Wargame"
 * 
 * La classe Foret est une classe qui hérite de la classe {@link Terrain}
 */
public class Foret extends Terrain{

    /**
     * Constructeur de la classe Foret
     */
    public Foret() {
        super();
        setBonusDefense(1.5);
        setPtsDeplacement(2);
    }

    /**
     * Affichage d'une case foret pour terminal
     */
    public String toString(){
        return "Foret : " +super.toString();
    }
}
