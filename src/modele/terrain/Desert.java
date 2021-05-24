package modele.terrain;

public class Desert extends Terrain{
    
    public Desert() {
        super();
        setBonusDefense(0.95);
        setPtsDeplacement(2);
    }

    public String toString(){
        return "Desert : " +super.toString();
    }
}
