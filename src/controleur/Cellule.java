package controleur;

import Vue.Hexagone;
import modele.entite.Entite;
import modele.entite.batiment.Batiment;
import modele.entite.unite.Unite;
import modele.plateau.Case;

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
