package modele.entite.unite;

public class Cavalerie extends Unite{

    public Cavalerie() {
        super(50,50,50,50);
        setPointDeVieMax(50);
        setDeplacementMax(50);
        setDeplacementActuel(50);
        setCout(1);
    }
    
}
