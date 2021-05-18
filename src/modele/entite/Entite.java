package modele.entite;

public class Entite {
    private int pointDeVieActuel;
    private int attaque;
    private int defense;
    private int vision;
    private int identifiant;
    private static int compteur = 0;

    public Entite(int parPointDeVieActuel, int parAttaque, int parDefense, int parVision) {
        this.pointDeVieActuel = parPointDeVieActuel;
        this.attaque = parAttaque;
        this.defense = parDefense;
        this.vision = parVision;
        identifiant = compteur++;
    }

    public Entite() {
    }



    public int getAttaque() {
        return attaque;
    }
    public int getDefense() {
        return defense;
    }
    public int getPointDeVieActuel() {
        return pointDeVieActuel;
    }
    public int getVision() {
        return vision;
    }
    public int getIdentifiant() {
        return identifiant;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setPointDeVieActuel(int pointDeVieActuel) {
        this.pointDeVieActuel = pointDeVieActuel;
    }
    public void setVision(int vision) {
        this.vision = vision;
    }
}
