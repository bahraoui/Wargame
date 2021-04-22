package modele.batiment;

public enum TypeBatiment {

    BASE(0), MONUMENT(1);

    private int value;

    private TypeBatiment( int value ) {
        this.value = value;
    }

    public int toInt() {
        return value;   
    }
    
    public static TypeBatiment fromInt( int value ) {
        switch( value ) {
            case 0: return BASE;
            default: return MONUMENT;
        }
    }
    
}