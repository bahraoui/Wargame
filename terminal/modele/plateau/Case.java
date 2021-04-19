package plateau;

import java.util.Set;

import batiment.Batiment;
import terrain.Terrain;
import unite.Unite;

public class Case {
    private Unite unite;
    private Batiment batiment;
    private Terrain terrain;

    public Case(Terrain parTerrain) {
        this.terrain = parTerrain;
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
