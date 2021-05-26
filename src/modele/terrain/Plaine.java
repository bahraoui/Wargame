//package
package modele.terrain;

/**
 * La classe Plaine représente le type terrain plaine du jeu "Wargame"
 * 
 * La classe Plaine est une classe qui hérite de la classe {@link Terrain}
 */
public class Plaine extends Terrain{

    /**
     * Constructeur de la classe Plaine
     */
    public Plaine() {
        super();
        setBonusDefense(1.2);
        setPtsDeplacement(1);
    }
    
    /**
     * Affichage d'une case plaine pour terminal
     */
    public String toString(){
        return "Plaine : " +super.toString();
    }
}
