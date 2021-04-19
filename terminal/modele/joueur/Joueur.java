package joueur;

import java.util.ArrayList;

import batiment.Batiment;
import unite.Unite;

public class Joueur {
    private Batiment batiment;
    private ArrayList<Unite> armee;
    private int banque;
    private boolean estIa;

    public Joueur(boolean parEstIa){
        this.banque = 50;
        this.estIa = parEstIa;
    }

    public ArrayList<Unite> getArmee() {
        return armee;
    }
    public int getBanque() {
        return banque;
    }
    public Batiment getBatiment() {
        return batiment;
    }


    public void setArmee(ArrayList<Unite> armee) {
        this.armee = armee;
    }
    public void setBanque(int banque) {
        this.banque = banque;
    }
    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }
}
