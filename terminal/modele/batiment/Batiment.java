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
