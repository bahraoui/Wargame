//package
package modele.entite.unite;

/**
 * La classe Archer représente les archers du jeu "Wargame"
 * 
 * La classe Archer hérite de la classe {@link Unite}
 * Elle utlise le constructeur de sa classe mère
 * Elle possede une methode d'affichage pour terminal
 */
public class Archer extends Unite{

    /**
     * Constructeur de la classe
     * Attribue pour un archer ses points de vie, son attaque, sa defense,sa vision, son cout, ses deplacement
     */
    public Archer() {
        super(20,40,20,4);
        setPointDeVieMax(super.getPointDeVieActuel());
        setDeplacementMax(3);
        setDeplacementActuel(3);
        setCout(5);
    }

    /**
     * Affichage d'un archer pour terminal
     */
    public String toString(){
        return "Archer";
    }
    
}
