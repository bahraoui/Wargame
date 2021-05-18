package Vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
* MyMouseListener
*/
public class MyMouseListener extends MouseAdapter {
   private JPanel jp;
   
   public MyMouseListener(JPanel jp) {
      super();
      this.jp = jp;
   }
   
   @Override
   public void mouseClicked(MouseEvent e) {
      System.out.println((Hexagone) e.getSource());
   }
}