package batiment;

public class Batiment {

    private int pointDeVie;
    private int attaque;
    private int defense;
    private int vision;
    private int tresor;
    private TypeBatiment estBase;
    
    public Batiment(int parPointDeVie,  int parAttaque,  int parDefense,  int parVision, TypeBatiment parEstBase) {
        this.pointDeVie = parAttaque;
        this.attaque = parAttaque;
        this.defense = parDefense;
        this.vision = parVision;
        this.estBase = parEstBase;

        if (parEstBase == TypeBatiment.MONUMENT)
            this.tresor = 50; //aleatoire
        else 
            this.tresor = 0;

    }

    public int getAttaque() {
        return attaque;
    }
    public int getDefense() {
        return defense;
    }
    public TypeBatiment getEstBase() {
        return estBase;
    }
    public int getPointDeVie() {
        return pointDeVie;
    }
    public int getTresor() {
        return tresor;
    }
    public int getVision() {
        return vision;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setEstBase(TypeBatiment estBase) {
        this.estBase = estBase;
    }
    public void setPointDeVie(int pointDeVie) {
        this.pointDeVie = pointDeVie;
    }
    public void setTresor(int tresor) {
        this.tresor = tresor;
    }
    public void setVision(int vision) {
        this.vision = vision;
    }

}
