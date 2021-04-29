package modele.terrain;

public class Montagne extends Terrain{

    public Montagne() {
        super();
        setBonusDefense(2);
        setPtsDeplacement(3);
    }
    
    public String toString(){
        return "Terrain de type Montagne : " +super.toString();
    }
}
