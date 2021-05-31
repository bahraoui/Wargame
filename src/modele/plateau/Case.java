//package
package modele.plateau;

import java.util.Random;

import modele.entite.Entite;
//import
import modele.entite.batiment.Batiment;
import modele.terrain.Terrain;
import modele.entite.unite.Unite;

/**
 * La classe Case représente les cases du plateau du jeu "Wargame"
 * 
 * La classe Case posède 3 champs :
 * unite représente l'unité sur la case
 * batiment représente le batiment sur la case
 * terrain représente le type de terrain de la case
 * Elle possede une methode d'affichage pour terminal
 * Avec pour chaque champs des getters et des setters
 */
public class Case {
    private Unite unite;
    private Batiment batiment;
    private Terrain terrain;

    /**
     * Constructeur de la classe Case
     * @param parTerrain terrain de la case
     */
    public Case(Terrain parTerrain) {
        this.terrain = parTerrain;
    }

    /**
     * Affichage d'un batiment pour terminal
     */
    public String toString(){
        if (this.unite != null)
            return "Sur la case, il y a "+this.unite+" et "+this.terrain;
        else if (this.batiment != null)
            return "Sur la case, il y a "+this.batiment+" et " +this.terrain;
        return "Sur la case, il n'y a rien et " +this.terrain;
        
    }

    /**
     * Affichage d'un batiment pour terminal
     */
    public String afficher(){
        if (this.unite != null)
            return "U";
        else if (this.batiment != null)
            return "B";
        return "-";
        
    }

    /**
     * Permet de savoir si une case contient une unite ou un batiment ou aucun des deux
     * @return Retourne une unite, un batiment ou null 
     */
    public Object estOccupe(){
        if (this.unite != null)
            return this.unite;
        else if (this.batiment != null)
            return this.batiment;
        return null; 
    }

    /**
     * Permet d'effectuer l'action d'attaque entre deux cases (leurs entite), simule l'attaque et met a jour les pv restants des entitées
     * @param attaquant Case attaquante
     * @param defenseur Case qui defend
     */
    public static void attaquer(Case attaquant, Case defenseur){
        Entite def = new Entite();
        Entite att = new Entite();
        if (defenseur.getBatiment() != null) {
            def = defenseur.getBatiment();
        }
        else if (defenseur.getUnite() != null) {
            def = defenseur.getUnite();
            defenseur.getUnite().setEnRepos(false);
        }
        if (attaquant.getBatiment() != null) {
            att = attaquant.getBatiment();
        }
        else if (attaquant.getUnite() != null) {
            att = attaquant.getUnite();
            attaquant.getUnite().setEnRepos(false);
            attaquant.getUnite().setAAttaque(true);
        }
        int defense = (int)(def.getDefense() * defenseur.getTerrain().getBonusDefense());
        int degat = att.getAttaque() - defense;       
        if (degat >= defense){
            def.setPointDeVieActuel(def.getPointDeVieActuel() - degat);
        }
        else {
            Random random = new Random();
            def.setPointDeVieActuel(def.getPointDeVieActuel() - random.nextInt(5));
        }
    }

    //
    //Getters et Setters
    //
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
