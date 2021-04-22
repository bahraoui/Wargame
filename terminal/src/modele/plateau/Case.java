package modele.plateau;

import java.util.Set;

import modele.batiment.Batiment;
import modele.batiment.TypeBatiment;
import modele.terrain.Terrain;
import modele.unite.Unite;



public class Case {
    private Unite unite;
    private Batiment batiment;
    private Terrain terrain;

    public Case(Terrain parTerrain) {
        this.terrain = parTerrain;
    }


    public String toString(){
        if (this.unite != null)
            return "Sur la case, il y a "+this.unite+" et "+this.terrain;
        else if (this.batiment != null)
            return "Sur la case, il y a "+this.batiment+" et " +this.terrain;
        return "Sur la case, il n'y a rien et " +this.terrain;
        
    }

    public Object estOccupe(){
        if (this.unite != null)
            return this.unite;
        else if (this.batiment != null)
            return this.batiment;
        return null; 
    }

    public Batiment getBatiment() {
        return batiment;
    }
    public Terrain getTerrain() {
        return terrain;
    }
    public Unite getUnite() {
        return unite;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
    public void setUnite(Unite unite) {
        this.unite = unite;
    }
}
