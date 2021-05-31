//package
package modele.entite.unite;

/**
 * La classe Mage représente les mages du jeu "Wargame"
 * 
 * La classe Mage hérite de la classe {@link Unite}
 * Elle utlise le constructeur de sa classe mère
 * Elle possede une methode d'affichage pour terminal
 */
public class Mage extends Unite{

    /**
     * Constructeur de la classe
     * Attribue pour un archer ses points de vie, son attaque, sa defense,sa vision, son cout, ses deplacement
     */
    public Mage() {
        super(35,65,30,3);
        setPointDeVieMax(super.getPointDeVieActuel());
        setDeplacementMax(5);
        setDeplacementActuel(5);
        setCout(20);
    }
    
    /**
     * Affichage d'un archer pour terminal
     */
    public String toString(){
        return "Mage";
    }
}
