package modele.joueur;

public class IA {
    private Joueur joueurIA;
    private Joueur joueurAAttaquer;

    public IA(Joueur parJoueurIA){
        this.joueurIA = parJoueurIA;
    }

    public void joueurAAttaquerIA() {
        //verfie si encore in game
        //sinon
        //random
        //assigner
    }

    public void achatTroupesIA(){
        //get argent
        //cmb on depense
        //achat random
        //placement random
    }

    public void deplacementUniteIA() {
        // rechercher plus court chemin entre troupe et base
        // 
    }

    public void tourIA(){
        //achat
        //joueurAAttaquer
        //pour chaque unite attaque
            //trouver deplacement jusqua base
    }

    public Joueur getJoueurIA() {
        return joueurIA;
    }
}
