package modele.entite.unite;

public class Infanterie extends Unite{

    public Infanterie() {
        super(50,50,50,50);
        setPointDeVieMax(50);
        setDeplacementMax(50);
        setDeplacementActuel(50);
        setCout(1);
    }
    
}
