package modele.terrain;

public class Desert extends Terrain{
    
    public Desert() {
        super();
        setBonusDefense(50);
        setPtsDeplacement(50);
    }

    public String toString(){
        return "Terrain de type Desert : " +super.toString();
    }
}
