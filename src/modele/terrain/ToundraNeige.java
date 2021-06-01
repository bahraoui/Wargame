//package
package modele.terrain;

/**
 * La classe ToundraNeige représente le type terrain neige du jeu "Wargame"
 * 
 * La classe ToundraNeige est une classe qui hérite de la classe {@link Terrain}
 */
public class ToundraNeige extends Terrain{

    /**
     * Constructeur de la classe ToundraNeige
     */
    public ToundraNeige() {
        super();
        setBonusDefense(0.75);
        setPtsDeplacement(3);
    }
    
    /**
     * Affichage d'une case ToundraNeige pour terminal
     */
    public String toString(){
        return "ToundraNeige : " +super.toString();
    }
}