//package
package modele.entite.unite;

/**
 * La classe InfanterieLourde représente les infanteries lourdes du jeu "Wargame"
 * 
 * La classe InfanterieLourde hérite de la classe {@link Unite}
 * Elle utlise le constructeur de sa classe mère
 * Elle possede une methode d'affichage pour terminal
 */
public class InfanterieLourde extends Unite{

    /**
     * Constructeur de la classe
     * Attribue pour un archer ses points de vie, son attaque, sa defense,sa vision, son cout, ses deplacement
     */
    public InfanterieLourde() {
        super(50,45,10,1);
        setPointDeVieMax(super.getPointDeVieActuel());
        setDeplacementMax(2);
        setDeplacementActuel(2);
        setCout(30);
    }
    
    /**
     * Affichage d'un archer pour terminal
     */
    public String toString(){
        return "InfanterieLourde";
    }
}
