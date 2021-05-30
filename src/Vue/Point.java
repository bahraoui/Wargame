package Vue;

import java.util.ArrayList;

/**
 * Cette classe permet de gérer un point correspondant à un élément d'une matrice 2D.
 * 
 * Pour pouvoir gérer plus facilement les coordonnées d'un élément et récupérer la position d'un élément,
 * on utilise cette 
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
    
    /**
     * 
     * @param valeur
     */
    public Point(int valeur) {
        int i;
        for(i=0;valeur>20;i++){
            if(i%2==0)
            valeur-=20;
            else 
            valeur-=19;
        }   
        this.x=valeur;
        this.y=i;
    }
    
    
    public ArrayList<Point> voisins(){
        ArrayList<Point> voisins = new ArrayList<Point>();
        if (y%2==0) {
            if(x!=20 && y>0)
            voisins.add(new Point(x,y-1));
            if(x>0 && y>0)
            voisins.add(new Point(x-1,y-1));
        }
        return voisins;
    }
}

