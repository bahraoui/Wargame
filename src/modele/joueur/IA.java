package modele.joueur;

import java.util.Random;

import modele.entite.unite.Archer;
import modele.entite.unite.Cavalerie;
import modele.entite.unite.Infanterie;
import modele.entite.unite.InfanterieLourde;
import modele.entite.unite.Mage;

public class IA {
    private Joueur joueurIA;
    private Joueur joueurAAttaquer;

    public IA(Joueur parJoueurIA){
        this.joueurIA = parJoueurIA;
    }

    
    public Joueur getJoueurIA() {
        return joueurIA;
    }
}
