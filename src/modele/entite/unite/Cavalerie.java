//package
package modele.entite.unite;

/**
 * La classe Cavalerie représente les cavaleries du jeu "Wargame"
 * 
 * La classe Cavalerie hérite de la classe {@link Unite}
 * Elle utlise le constructeur de sa classe mère
 * Elle possede une methode d'affichage pour terminal
 */
public class Cavalerie extends Unite{

    /**
     * Constructeur de la classe
     * Attribue pour un archer ses points de vie, son attaque, sa defense,sa vision, son cout, ses deplacement
     */
    public Cavalerie() {
        super(30,50,8,2);
        setPointDeVieMax(50);
        setDeplacementMax(8);
        setDeplacementActuel(8);
        setCout(12);
    }
    
    /**
     * Affichage d'un archer pour terminal
     */
    public String toString(){
        return "Cavalerie";
    }
}
