package controleur;

import Vue.Hexagone;
import modele.entite.Entite;
import modele.entite.batiment.Batiment;
import modele.entite.unite.Unite;
import modele.plateau.Case;
import modele.terrain.Terrain;

public class Cellule {
    private Hexagone hex;
    private Case caseModele;

    public Cellule(Hexagone hex, Case caseModele) {
        this.hex = hex;
        this.caseModele = caseModele;
    }

    public void clear() {
        Case caseEntiteSupprime = this.getCase();
        Hexagone hexagoneEntiteSupprime = this.getHex();
        Entite  entiteSupprime = (Entite) caseEntiteSupprime.estOccupe();
        if (entiteSupprime instanceof Unite) {
            caseEntiteSupprime.setUnite(null);
            hexagoneEntiteSupprime.setUnite(null);
        }
        else if (entiteSupprime instanceof Batiment) {
            caseEntiteSupprime.setBatiment(null);
            hexagoneEntiteSupprime.setBatiment(null);
        }
        hexagoneEntiteSupprime.setTerrain(Outils.terrainModeleToVue(caseEntiteSupprime.getTerrain()));
    }

    public void setUnite(Unite unite){
        this.getHex().setUnite(Outils.uniteModelToVue(unite));
        this.getCase().setUnite(unite);
    }

    public void setTerrain(Terrain terrain){
        this.getHex().setTerrain(Outils.terrainModeleToVue(terrain));
        this.getCase().setTerrain(terrain);
    }

    public void setBatiment(Batiment batiment){
        if (batiment != null)
            this.getHex().setBatiment(Outils.batimentModeleToVue(batiment.getEstBase()));
        else 
            this.getHex().setBatiment(null);
        this.getCase().setBatiment(batiment);
    }
    
    
    // Getters et setters 
    public Hexagone getHex() {
        return this.hex;
    }

    public void setHex(Hexagone hex) {
        this.hex = hex;
    }

    public Case getCase() {
        return this.caseModele;
    }

    public void setCase(Case caseModele) {
        this.caseModele = caseModele;
    }
    // fin Getters et setters 

    
}
