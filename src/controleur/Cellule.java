package controleur;

import Vue.Hexagone;
import modele.plateau.Case;

public class Cellule {
    private Hexagone hex;
    private Case caseModele;

    public Cellule(Hexagone hex, Case caseModele) {
        this.hex = hex;
        this.caseModele = caseModele;
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
