//package
package modele.entite.batiment;

//import
import modele.entite.Entite;

/**
 * La classe Batiment représente un batiment du jeu "Wargame"
 * 
 * La classe Batiment hérite de la classe {@link Entite}
 * Elle posède deux champs : tresor et estBase
 * Avec pour chaque champs des getters et des setters
 * Elle possede une methode d'affichage pour terminal
 */
public class Batiment extends Entite{
    private int tresor;
    private TypeBatiment estBase;

    /**
     * Constructeur de la classe
     * Attribue un entier aléatoire pour le trésor d'un batiment de type Monument
     * Cette valeur est comprise entre 50 et 150
     * Une base a un tresor initialisé à 0
     * @param parEstBase booleen qui définit si le batiment est une base ou non
     */
    public Batiment(TypeBatiment parEstBase) {
        super(150,0,5,4);
        this.estBase = parEstBase;
        setPointDeVieActuel(150);
        if (parEstBase == TypeBatiment.MONUMENT)
            this.tresor = 50; //aleatoire
        else {
            //setAttaque(0);
            //setDefense(0);
            setVision(4);
            this.tresor = 0;
        }
    }

    /**
     * Affichage d'un batiment pour terminal
     */
    public String toString(){
        if (this.estBase == TypeBatiment.BASE)
            return "Base";
        else
            return "Monument";
    }

    //
    //Getters et Setters
    //
    public TypeBatiment getEstBase() {
        return estBase;
    }
    public int getTresor() {
        return tresor;
    }
     
    public void setEstBase(TypeBatiment estBase) {
        this.estBase = estBase;
    }
    
    public void setTresor(int tresor) {
        this.tresor = tresor;
    }    

}
