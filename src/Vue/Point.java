package Vue;

import java.util.ArrayList;

public class Point {
    int x;
    int y;

    public Point(int parX, int parY){
        this.x = parX;
        this.y = parY;
    }

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

    public String toString() {
        return "["+String.valueOf(x)+","+String.valueOf(y)+"]";
    }


    public ArrayList<Point> voisins(){
        ArrayList<Point> voisins = new ArrayList<Point>();
        if (y%2==0) {
            if(x!=20 && y>0)
                voisins.add(new Point(x,y-1));
            if(x>0 && y>0)
                voisins.add(new Point(x-1,y-1));
            /*if(x!=20 && y<0)
                voisins.add(new Point(x,y-1));
            if(x>0 && y>0)
                voisins.add(new Point(x-1,y-1));*/
        }
        return voisins;
    }
}

