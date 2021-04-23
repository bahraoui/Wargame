package modele.terrain;

public class ToundraNeige extends Terrain{

    public ToundraNeige() {
        super();
        setBonusDefense(0.5);
        setPtsDeplacement(50);
    }
    
    public String toString(){
        return "Terrain de type ToundraNeige : " +super.toString();
    }
}