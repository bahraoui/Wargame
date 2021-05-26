//package
package modele.entite.batiment;

/**
 * La classe TypeBatiment représente le type des batiments du jeu "Wargame"
 * 
 * La classe TypeBatiment est une classe de type énuméré
 * On assigne 0 pour une base
 * On assigne 1 pour un monument
 */
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