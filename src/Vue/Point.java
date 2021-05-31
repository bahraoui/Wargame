package Vue;

/**
 * Cette classe permet de gérer un point correspondant à un élément d'une matrice.
 * 
 * Pour pouvoir gérer plus facilement les coordonnées d'un élément et récupérer la position d'un élément,
 * on utilise cette classe Point.
 */
public class Point {
    private int x;
    private int y;
    

    public Point(int parX, int parY){
        this.x = parX;
        this.y = parY;
    }


    // Getters et setters :

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // FIN getters et setters
}

