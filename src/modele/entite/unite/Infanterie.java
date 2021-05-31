//package
package modele.entite.unite;

/**
 * La classe Infanterie représente les infanteries du jeu "Wargame"
 * 
 * La classe Infanterie hérite de la classe {@link Unite}
 * Elle utlise le constructeur de sa classe mère
 * Elle possede une methode d'affichage pour terminal
 */
public class Infanterie extends Unite{

    /**
     * Constructeur de la classe
     * Attribue pour un archer ses points de vie, son attaque, sa defense,sa vision, son cout, ses deplacement
     */
    public Infanterie() {
        super(35,10,7,1);
        setPointDeVieMax(super.getPointDeVieActuel());
        setDeplacementMax(2);
        setDeplacementActuel(2);
        setCout(6);
    }
    
    /**
     * Affichage d'un archer pour terminal
     */
    public String toString(){
        return "Infanterie";
    }
}
