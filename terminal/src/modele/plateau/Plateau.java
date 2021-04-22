package modele.plateau;

import java.util.ArrayList;

import modele.terrain.Plaine;
import modele.terrain.Desert;

public class Plateau extends ArrayList<ArrayList<Case>> {
    private int cote = 16;
    
    public Plateau() {
        for (int i = 0; i <cote; i++) {
            ArrayList<Case> ligne = new ArrayList<Case>();
            for (int j = 0; j < cote ; j++) {
                if (j%2 == 0) {
                    Plaine caseTerrainP = new Plaine();
                    Case cellule = new Case(caseTerrainP);
                    ligne.add(cellule);
                }
                else {
                    Desert caseTerrainD = new Desert();
                    Case cellule = new Case(caseTerrainD);
                    ligne.add(cellule);
                }
                
            }
            this.add(ligne);
        }
    }

    public String toString(){
        String chaine = "";
        for (int i = 0; i <cote; i++) {
            for (int j = 0; j < cote ; j++) {
                chaine += this.get(i).get(j).toString()+"\n";
            }
            chaine+="\n";
        }
        return chaine;
    }

    /*

    recuperer un terrain predefini
        Exemple :
        - Fichier 1 : desert + plaine
        - Fichier 2 : mer + montagne
            - Recupere le fichier
            - Creer les cases
            - Creer le plateau
            - Connaitre le nombre de base par carte.
    
    sauvegarder un terrain
    */
}
