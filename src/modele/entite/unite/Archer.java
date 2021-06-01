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
        super(20,20,5,2);
        setPointDeVieMax(super.getPointDeVieActuel());
        setDeplacementMax(5);
        setDeplacementActuel(5);
        setCout(5);
    }

    /**
     * Affichage d'un archer pour terminal
     */
    public String toString(){
        return "Archer";
    }
    
}
