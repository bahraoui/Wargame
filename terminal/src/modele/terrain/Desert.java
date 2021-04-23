package modele.terrain;

public class Desert extends Terrain{
    
    public Desert() {
        super();
        setBonusDefense(0.8);
        setPtsDeplacement(50);
    }

    public String toString(){
        return "Terrain de type Desert : " +super.toString();
    }
}
