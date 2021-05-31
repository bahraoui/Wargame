package Vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;


/**
* La classe Hexagone permet de créer une case avec une forme hexagonale et des images à l'interireur
*/
public class Hexagone extends JLabel {
    private static final long serialVersionUID = -7142502695252118612L;
    private Polygon hexagonalShape;
    private TypeTerrain sol;
    private TypeUnite unite;
    private TypeBatimentVue batiment;
    private Point coord;


    /**
     * Le constructeur de Hexagone permet d'intancier un Hexagone avec :
     * - le sol qu'il contient
     * - une unite si la case en contient
     * - un batiment si la case en contient
     * - des coordonnees sur le plateau de jeu
     * @param sol TypeTerrain
     * @param unite TypeUnite
     * @param batiment TypeBatimentVue
     * @param coord Point
     * @throws IOException
     */
    public Hexagone(TypeTerrain sol, TypeUnite unite, TypeBatimentVue batiment, Point coord) throws IOException {
        super();
        hexagonalShape = getHexPolygon();
        this.sol = sol;
        this.unite = unite;
        this.batiment = batiment;
        this.coord = coord;
    }

    
    

    /**
    * Genere un bouton de forme hexagonale
    * @return Polygon avec les formes d'un bouton hexagonale
    */
    private Polygon getHexPolygon() {
        Polygon hex = new Polygon();
        int w = getWidth() - 1;
        int h = getHeight() - 1;
        int ratio = (int) (h * .25);
        
        hex.addPoint(w / 2, 0);
        hex.addPoint(w, ratio);
        hex.addPoint(w, h - ratio);
        hex.addPoint(w / 2, h);
        hex.addPoint(0, h - ratio);
        hex.addPoint(0, ratio);
        
        return hex;
    }

    
    // Getters et setters : 
    
    public Polygon getHexagonalShape() {
        return this.hexagonalShape;
    }
    public Point getCoord(){
        return this.coord;
    }
    
    // FIN Getters et setters

    /**
     * setTerrain permet d'afficher un sol sur la case
     * @param sol TypeTerrain
     * @throws IOException
     */
    public void setTerrain(TypeTerrain sol) throws IOException{
        Graphics g = getGraphics();
        g.setClip(hexagonalShape);
        placerSol(sol,g);
        if (batiment != null) {
            placerBatiment(batiment,g);      
        }
        else {
            try {
                g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Batiment"+File.separator+"VIDE.png")), 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.batiment=null;
        }

        if (unite != null) {
            placerUnite(unite,g);
        }
        else {
            try {
                g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Unite"+File.separator+"VIDE.png")), 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.unite=null;

            
        }
        this.sol = sol;
        this.paintChildren(g);
        
    }

    /**
     * setMonument permet d'afficher un monument sur la case si le parametre est vrai
     * @param Monument boolean
     * @throws IOException
     */
    public void setMonument(boolean Monument) throws IOException{
        if (Monument) {
            Graphics g = getGraphics();
            g.setClip(hexagonalShape);
            setTerrain(sol);
            g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Batiment"+File.separator+"MONUMENT.png")), 9, 6, null);
            this.batiment = TypeBatimentVue.MONUMENT;
            this.paintChildren(g);
        } else {
            this.batiment = null;
        }
    }

    /**
     * setBatiment permet d'afficher un baiment sur la case
     * @param typeBatimentVue
     * @throws IOException
     */
    public void setBatiment(TypeBatimentVue typeBatimentVue) throws IOException {
        Graphics g = getGraphics();
        g.setClip(hexagonalShape);
        if (typeBatimentVue != null) {
            placerBatiment(typeBatimentVue, g);
        }
        this.paintChildren(g);
    }


    /*
    * (non-Javadoc)
    * @see java.awt.Component#contains(java.awt.Point)
    */
    @Override
    public boolean contains(java.awt.Point point) {
        return hexagonalShape.contains(point);
    }
    
    /*
    * (non-Javadoc)
    * @see javax.swing.JComponent#contains(int, int)
    */
    @Override
    public boolean contains(int x, int y) {
        return hexagonalShape.contains(x, y);
    }
    
    /*
    * (non-Javadoc)
    * @see java.awt.Component#setSize(java.awt.Dimension)
    */
    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        hexagonalShape = getHexPolygon();
    }
    
    /*
    * (non-Javadoc)
    * @see java.awt.Component#setSize(int, int)
    */
    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        hexagonalShape = getHexPolygon();
    }
    
    /*
    * (non-Javadoc)
    * @see java.awt.Component#setBounds(int, int, int, int)
    */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        hexagonalShape = getHexPolygon();
    }
    
    /*
    * (non-Javadoc)
    * @see java.awt.Component#setBounds(java.awt.Rectangle)
    */
    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        hexagonalShape = getHexPolygon();
    }
    
    /*
    * (non-Javadoc)
    * @see javax.swing.JComponent#processMouseEvent(java.awt.event.MouseEvent)
    */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (contains(e.getPoint()))
        super.processMouseEvent(e);
    }
    

    /*
    * (non-Javadoc)
    * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
    */
    @Override
    protected void paintComponent(Graphics g) {
        g.setClip(hexagonalShape);
        if (sol != null) {
            placerSol(sol, g);
        } else {
            try {
                g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+"VIDE.png")), 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (batiment != null) {
            placerBatiment(batiment, g);
        }
        else {
            try {
                g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Batiment"+File.separator+"VIDE.png")), 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (unite != null) {
            placerUnite(unite, g);
        }
        else {
            try {
                g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Unite"+File.separator+"VIDE.png")), 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    /*
    * (non-Javadoc)
    * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
    */
    @Override
    protected void paintBorder(Graphics g) {
        // N'affiche pas de bordure
    }


    /**
     * setUnite permet d'afficher un unite sur la case si le parametre n'est pas vide
     * @param parUnite TypeUnite
     */
    public void setUnite(TypeUnite parUnite) {
        Graphics g = getGraphics();
        g.setClip(hexagonalShape);
        if (parUnite != null) {
                placerUnite(parUnite, g);
        }
        this.unite = parUnite;
        this.paintChildren(g);
    }

    /**
     * getAll est une methode de debugage qui renvoie une chaine de caractere qui donne les informations sur la case
     * @return String
     */
    public String getAll(){
        if (unite != null)
            return "Hexagone : \n\tUnite : "+unite.toString();
        if (batiment != null)
            return "Hexagone :Batiment: "+batiment.toString();
        if (sol != null)
            return "Hexagone :Sol: "+sol.toString();
        return "Rien";
    }

    /**
     * placerSol permet de dessiner le sol en fonction du type de terrain
     * @param sol TypeTerrain
     * @param g Graphics
     */
    private void placerSol(TypeTerrain sol, Graphics g){
        try {
            switch (sol) {
                case MER:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+"MER.jpg")), 0, 0, null);
                    break;
                case DESERT:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+"DESERT.jpg")), 0, 0, null);
                    break;
                case FORET:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+"FORET.jpg")), 0, 0, null);
                    break;
                case MONTAGNE:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+"MONTAGNE.jpg")), 0, 0, null);
                    break;
                case PLAINE:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+"PLAINE.jpg")), 0, 0, null);
                    break;
                case NEIGE:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Terrain"+File.separator+"NEIGE.jpg")), 0, 0, null);
                    break;
            
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    /**
     * placerBatiment permet de dessiner le batiment en fonction du type de batiment si la case posede un batiment
     * @param bat TypeBatimentVue
     * @param g Graphics
     */
    private void placerBatiment(TypeBatimentVue bat, Graphics g){
        try {
            switch (batiment) {
                case BASE:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Batiment"+File.separator+"BASE.png")), 10, 0, null);
                    break;
                case MONUMENT:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Batiment"+File.separator+"MONUMENT.png")), 9, 6, null);
                    break;
            
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * placerUnite permet de dessiner une unite en fonction du type d'unite si la case posede une unite
     * @param unite TypeUnite
     * @param g Graphics
     */
    private void placerUnite(TypeUnite unite, Graphics g){
        try {
            switch (unite) {
                case ARCHER:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Unite"+File.separator+"ARCHER.png")), 15, 0, null);
                    break;
                case CAVALERIE:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Unite"+File.separator+"CAVALERIE.png")), 12, 0, null);
                    break;
                case INFANTERIE:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Unite"+File.separator+"INFANTERIE.png")), 17, 1, null);
                    break;
                case INFANTERIELOURDE:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Unite"+File.separator+"INFANTERIELOURDE.png")), 8, 5, null);
                    break;
                case MAGE:
                    g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Unite"+File.separator+"MAGE.png")), 8, 0, null);
                    break;
                default:
                    break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
