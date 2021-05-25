package modele.terrain;

public class Plaine extends Terrain{

    public Plaine() {
        super();
        setBonusDefense(1.2);
        setPtsDeplacement(1);
    }
    
    public String toString(){
        return "Plaine : " +super.toString();
    }
}
