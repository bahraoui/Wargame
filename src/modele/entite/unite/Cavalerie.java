package modele.entite.unite;

public class Cavalerie extends Unite{

    public Cavalerie() {
        super(25,50,20,2);
        setPointDeVieMax(50);
        setDeplacementMax(6);
        setDeplacementActuel(6);
        setCout(12);
    }
    
}
