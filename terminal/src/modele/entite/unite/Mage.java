package modele.entite.unite;

public class Mage extends Unite{

    public Mage() {
        super(35,65,30,3);
        setPointDeVieMax(super.getPointDeVieActuel());
        setDeplacementMax(3);
        setDeplacementActuel(3);
        setCout(20);
    }
    
}
