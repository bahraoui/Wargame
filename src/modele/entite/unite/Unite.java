//package
package modele.entite.unite;

//import
import modele.entite.Entite;
import modele.plateau.Case;

/**
 * La classe Unite représente les unités de guerre du jeu "Wargame"
 * 
 * La classe Unite hérite de la classe {@link Entite}
 * Elle posède 6 champs :
 * pointDeVieMax représente les points de vie maximum d'une unité
 * deplacementMax représente les points de déplacement maximum d'une unité
 * deplacementActuel représente les points de déplacement actuel d'une unité
 * enRepos représente si une unité est en repos 
 * aAttaque représente si une unité a attaqué sur ce tour
 * cout représente le cout d'une unité
 * Elle possede une methode d'affichage pour terminal
 * Avec pour chaque champs des getters et des setters
 */
public class Unite extends Entite{
    private int pointDeVieMax;
    private int deplacementMax;
    private int deplacementActuel;
    private boolean enRepos;
    private boolean aAttaque;
    private int cout;
    
    /**
     * Constructeur de la classe
     * @param parPointDeVieActuel point de vie actuel d'une entite
     * @param parAttaque point d'attaque d'une entite
     * @param parDefense point de défense d'une entite
     * @param parVision champs de vision d'une entite
     */
    public Unite(int parPointDeVieActuel, int parAttaque, int parDefense, int parVision) {
        super(parPointDeVieActuel,parAttaque,parDefense,parVision);
        this.enRepos = true;
        this.aAttaque = false;
    }

    public static boolean deplacementUnite(Unite unite, Case caseInitial, Case caseFinal){
        int deplacementTerrain = caseInitial.getTerrain().getPtsDeplacement();
        if (caseInitial.getBatiment() == null && caseInitial.getUnite() != null && caseFinal.getBatiment() == null && caseFinal.getUnite() == null && unite.getDeplacementActuel() > 0 && unite.getDeplacementActuel() >= deplacementTerrain){
            unite.setDeplacementActuel(unite.getDeplacementActuel() - deplacementTerrain);
            caseFinal.setUnite(unite);
            caseInitial.setUnite(null);
            caseFinal.getUnite().setEnRepos(false);
            return true;
        }
        return false;
    }

    /**
     * Affichage d'un batiment pour terminal
     */
    public String toString(){
        return "L'unite a pv : "+super.getPointDeVieActuel()+", pa: "+super.getAttaque()+", pdef: "+super.getDefense()+", pdeplacementMax: "+this.deplacementMax+", vision: "+super.getVision()+", cout: "+this.cout;
    }
   
    //
    //Getters et Setters
    //
    public int getDeplacementActuel() {
        return deplacementActuel;
    }
    public int getDeplacementMax() {
        return deplacementMax;
    }
    public int getPointDeVieMax() {
        return pointDeVieMax;
    }
    public int getCout() {
        return cout;
    }
    public boolean getEnRepos() {
        return enRepos;
    }
    public boolean getAAttaque() {
        return aAttaque;
    }

    public void setDeplacementMax(int deplacementMax) {
        this.deplacementMax = deplacementMax;
    }
    public void setDeplacementActuel(int deplacementActuel) {
        this.deplacementActuel = deplacementActuel;
    }
    public void setPointDeVieMax(int pointDeVieMax) {
        this.pointDeVieMax = pointDeVieMax;
    }
    public void setCout(int cout) {
        this.cout = cout;
    }
    public void setEnRepos(boolean enRepos) {
        this.enRepos = enRepos;
    }
    public void setAAttaque(boolean aAttaque) {
        this.aAttaque = aAttaque;
    }

    public String afficherNomUnite() {
        if (this instanceof Archer){
            return "Archer";
        }
        else if (this instanceof Cavalerie){
            return "Cavalerie";
        }
        else if (this instanceof Infanterie){
            return "Infanterie";
        }
        else if (this instanceof InfanterieLourde){
            return "InfanterieLourde";
        }
        else if (this instanceof Mage){
            return "Mage";
        }
        return "";
    }
}