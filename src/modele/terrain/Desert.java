//package
package modele.terrain;

/**
 * La classe Desert représente le type terrain desert du jeu "Wargame"
 * 
 * La classe Desert est une classe qui hérite de la classe {@link Terrain}
 */
public class Desert extends Terrain{
    
    /**
     * Constructeur de la classe Desert
     */
    public Desert() {
        super();
        setBonusDefense(0.95);
        setPtsDeplacement(2);
    }

    /**
     * Affichage d'une case Desert pour terminal
     */
    public String toString(){
        return "Desert : " +super.toString();
    }
}
