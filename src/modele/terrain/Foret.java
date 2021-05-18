package modele.terrain;

public class Foret extends Terrain{
    public Foret() {
        super();
        setBonusDefense(1.5);
        setPtsDeplacement(2);
    }

    public String toString(){
        return "Terrain de type Foret : " +super.toString();
    }
}
