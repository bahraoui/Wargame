package modele.entite.unite;

import modele.entite.Entite;
/**
 * Unite
 */
public class Unite extends Entite{

    private int pointDeVieMax;
    private int deplacementMax;
    private int deplacementActuel;
    private boolean enRepos;
    private boolean aAttaque;
    private int cout;
    
    public Unite(int parPointDeVieActuel, int parAttaque, int parDefense, int parVision) {
        super(parPointDeVieActuel,parAttaque,parDefense,parVision);
        this.enRepos = true;
        this.aAttaque = false;
    }

    public String toString(){
        return "L'unite a pv : "+super.getPointDeVieActuel()+", pa: "+super.getAttaque()+", pdef: "+super.getDefense()+", pdeplacementMax: "+this.deplacementMax+", vision: "+super.getVision()+", cout: "+this.cout;
    }
   
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
}