package modele.entite.unite;

public class Archer extends Unite{

    public Archer() {
        super(20,40,20,4);
        setPointDeVieMax(super.getPointDeVieActuel());
        setDeplacementMax(3);
        setDeplacementActuel(3);
        setCout(5);
    }

    public String toString(){
        return "Archer";
    }
    
}
