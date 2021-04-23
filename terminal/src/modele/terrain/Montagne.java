package modele.terrain;

public class Montagne extends Terrain{

    public Montagne() {
        super();
        setBonusDefense(3.0);
        setPtsDeplacement(50);
    }
    
    public String toString(){
        return "Terrain de type Montagne : " +super.toString();
    }
}
