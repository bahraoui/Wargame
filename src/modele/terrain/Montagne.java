//package
package modele.terrain;

/**
 * La classe Montagne représente le type terrain montagne du jeu "Wargame"
 * 
 * La classe Montagne est une classe qui hérite de la classe {@link Terrain}
 */
public class Montagne extends Terrain{

    /**
     * Constructeur de la classe Montagne
     */
    public Montagne() {
        super();
        setBonusDefense(2);
        setPtsDeplacement(3);
    }
    
    /**
     * Affichage d'une case montagne pour terminal
     */
    public String toString(){
        return "Montagne : " +super.toString();
    }
}
