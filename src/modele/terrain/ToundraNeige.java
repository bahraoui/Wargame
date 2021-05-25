package modele.terrain;

public class ToundraNeige extends Terrain{

    public ToundraNeige() {
        super();
        setBonusDefense(0.75);
        setPtsDeplacement(4);
    }
    
    public String toString(){
        return "ToundraNeige : " +super.toString();
    }
}