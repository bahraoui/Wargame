package modele.terrain;

public class Mer extends Terrain{

    public Mer() {
        super();
        setBonusDefense(1.5);
        setPtsDeplacement(50);
    }
    
    public String toString(){
        return "Terrain de type Mer : " +super.toString();
    }
}
