package modele.entite.batiment;

import modele.entite.Entite;

public class Batiment extends Entite{

    private int tresor;
    private TypeBatiment estBase;
    
    /// Base : 500 , 20 , 40 , 4 , Base
    /// Monument : 150 , 0 , 20 , 0 , Monument

    public Batiment(TypeBatiment parEstBase) {
        super(150,0,20,0);
        this.estBase = parEstBase;
        if (parEstBase == TypeBatiment.MONUMENT)
            this.tresor = 50; //aleatoire
        else 
            setPointDeVieActuel(500);
            setAttaque(20);
            setDefense(40);
            setVision(4);
            this.tresor = 0;

    }

    public String toString(){
        if (this.estBase == TypeBatiment.BASE)
            return "Base";
        else
            return "Monument";
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
