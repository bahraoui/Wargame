package modele.entite.unite;

public class InfanterieLourde extends Unite{

    public InfanterieLourde() {
        super(120,15,45,2);
        setPointDeVieMax(super.getPointDeVieActuel());
        setDeplacementMax(2);
        setDeplacementActuel(2);
        setCout(30);
    }
    
}
