//package
package modele.plateau;

//import
import java.util.ArrayList;

import modele.terrain.Plaine;
import modele.terrain.Terrain;

/**
 * La classe Plateau représente le plateau du jeu "Wargame"
 * 
 * La classe Plateau hérite de la classe {@link ArrayList}
 * Elle posède un champs : cote, la taille du cote du plateau
 * Elle possede une methode d'affichage pour terminal
 */
public class Plateau extends ArrayList<ArrayList<Case>> {
    private int cote = 16;
    
    /**
     * Constructeur de la classe plateau
     */
    public Plateau() {
        for (int i = 0; i <cote; i++) {
            ArrayList<Case> ligne = new ArrayList<Case>();
            for (int j = 0; j < cote ; j++) {
                Case cellule = new Case(new Plaine());
                ligne.add(cellule);
                cellule.setBatiment(null);
                cellule.setUnite(null);
            }
            this.add(ligne);
        }
    }

    /**
     * Affichage du plateau pour terminal
     */
    public String toString(){
        String chaine = "";
        for (int i = 0; i <cote; i++) {
            for (int j = 0; j < cote ; j++) {
                chaine += i+" - "+j+" => "+this.get(i).get(j).toString()+"\n";
            }
            chaine+="\n";
        }
        return chaine;
    }

    /**
     * Affichage du plateau pour terminal
     */
    public String affichage(){
        String chaine = "";
        for (int i = 0; i <cote; i++) {
            chaine +="[";
            for (int j = 0; j < cote ; j++) {
                chaine += this.get(i).get(j).afficher()+" ";
            }
            chaine+="]\n";
        }
        return chaine;
    }

    /**
     * Change tout les types de terrain de chaque case du plateau par un terrain donné en paramètre
     * @param terrain nouveau type de terrain pour le plateau
     */
    public void replace(Terrain terrain){
        for (int i = 0; i < cote; i++) {
            for (int j = 0; j < cote; j++) {
                this.get(i).get(j).setTerrain(terrain);
            }
        }
    }
}
