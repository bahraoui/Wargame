package unite;
/**
 * Unite
 */
public class Unite {

    private int pointDeVie;
    private int attaque;
    private int defense;
    private int deplacement;
    private int vision;
    private int cout;
    
    public Unite() {
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

}