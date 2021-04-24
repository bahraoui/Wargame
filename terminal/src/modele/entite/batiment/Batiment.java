package modele.entite.batiment;

import modele.entite.Entite;

public class Batiment extends Entite{

    private int tresor;
    private TypeBatiment estBase;
    
    public Batiment(int parPointDeVieActuel,  int parAttaque,  int parDefense,  int parVision, TypeBatiment parEstBase) {
        super(parPointDeVieActuel,parAttaque,parDefense,parVision);
        this.estBase = parEstBase;

        if (parEstBase == TypeBatiment.MONUMENT)
            this.tresor = 50; //aleatoire
        else 
            this.tresor = 0;

    }

    public String toString(){
        if (this.estBase == TypeBatiment.BASE)
            return "Le batiment est une base";
        else
            return "Le batiment est un monument";
    }

    
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
