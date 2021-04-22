package modele.unite;
/**
 * Unite
 */
public class Unite {

    private int pointDeVie;
    private int attaque;
    private int defense;
    private int deplacement;
    private boolean aBouge;
    private int vision;
    private int tauxDeRecuperation;
    private int enRepos;
    private int cout;
    
    public Unite() {
    }

    public String toString(){
        return "L'unite a pv : "+this.pointDeVie+", pa: "+this.attaque+", pdef: "+this.defense+", pdeplacement: "+this.deplacement+", vision: "+this.vision+", cout: "+this.cout;
    }

    

    public int getAttaque() {
        return attaque;
    }

    public int getDefense() {
        return defense;
    }
    public int getDeplacement() {
        return deplacement;
    }
    public int getPointDeVie() {
        return pointDeVie;
    }
    public int getVision() {
        return vision;
    }
    public int getCout() {
        return cout;
    }
    public int getTauxDeRecuperation() {
        return tauxDeRecuperation;
    }
    public int getEnRepos() {
        return enRepos;
    }


    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }
    public void setPointDeVie(int pointDeVie) {
        this.pointDeVie = pointDeVie;
    }
    public void setVision(int vision) {
        this.vision = vision;
    }
    public void setCout(int cout) {
        this.cout = cout;
    }
    public void setTauxDeRecuperation(int tauxDeRecuperation) {
        this.tauxDeRecuperation = tauxDeRecuperation;
    }
    public void setEnRepos(int enRepos) {
        this.enRepos = enRepos;
    }

}