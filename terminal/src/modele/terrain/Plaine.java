package modele.terrain;

public class Plaine extends Terrain{

    public Plaine() {
        super();
        setBonusDefense(500);
        setPtsDeplacement(50);
    }
    
    public String toString(){
        return "Terrain de type Plaine : " +super.toString();
    }
}
