package modele.terrain;

public class Foret extends Terrain{
    public Foret() {
        super();
        setBonusDefense(2.5);
        setPtsDeplacement(50);
    }

    public String toString(){
        return "Terrain de type Foret : " +super.toString();
    }
}
