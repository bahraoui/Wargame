package modele.entite.unite;

public class Infanterie extends Unite{

    public Infanterie() {
        super(40,25,35,1);
        setPointDeVieMax(super.getPointDeVieActuel());
        setDeplacementMax(2);
        setDeplacementActuel(2);
        setCout(6);
    }
    
}
