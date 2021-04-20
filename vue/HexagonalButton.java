package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

/**
 * La classe HexagonalButton permet de créer un bouton avec une forme hexagonale
 */
public class HexagonalButton extends JButton {
    private static final long serialVersionUID = -7142502695252118612L;
    Polygon hexagonalShape;
    Point coord;

    public HexagonalButton() {
	this.setOpaque(false);
	hexagonalShape = getHexPolygon();
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

    public void setEmplacement(Point p){
        this.coord = p;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#contains(java.awt.Point)
     */
    @Override
    public boolean contains(Point p) {
	return hexagonalShape.contains(p);
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
	g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
	g.fillRect(0, 0, getWidth(), getHeight());
	g.setColor(getBackground());
	g.drawPolygon(hexagonalShape);
	//g.fillPolygon(hexagonalShape);


    }

    /*
     * (non-Javadoc)
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
	// Does not print border
    }

    //public 
}
