package modele.terrain;

public class Mer extends Terrain{

    public Mer() {
        super();
        setBonusDefense(1);
        setPtsDeplacement(4);
    }
    
    public String toString(){
        return "Mer : " +super.toString();
    }
}
