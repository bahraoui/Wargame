//package
package modele.terrain;

/**
 * La classe Mer représente le type terrain mer du jeu "Wargame"
 * 
 * La classe Mer est une classe qui hérite de la classe {@link Terrain}
 */
public class Mer extends Terrain{

    /**
     * Constructeur de la classe Mer
     */
    public Mer() {
        super();
        setBonusDefense(1);
        setPtsDeplacement(4);
    }
    
    /**
     * Affichage d'une case mer  pour terminal
     */
    public String toString(){
        return "Mer : " +super.toString();
    }
}
