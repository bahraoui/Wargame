package terrain;

public class Terrain {
    private int ptsDeplacement;
    private int bonusDefense;

    public Terrain() {
    }

    public int getBonusDefense() {
        return bonusDefense;
    }
    public int getPtsDeplacement() {
        return ptsDeplacement;
    }

    public void setBonusDefense(int bonusDefense) {
        this.bonusDefense = bonusDefense;
    }
    public void setPtsDeplacement(int ptsDeplacement) {
        this.ptsDeplacement = ptsDeplacement;
    }
}
