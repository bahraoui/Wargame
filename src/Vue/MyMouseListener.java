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
import java.io.IOException;

import javax.swing.JPanel;

import controleur.Jeu;

/**
* MyMouseListener
*/
public class MyMouseListener extends MouseAdapter {
   private JPanel jp;
   
   public MyMouseListener(JPanel jp) {
      super();
      this.jp = jp;
   }
   
   public MyMouseListener(Jeu controleur) {
      super();
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      System.out.println((Hexagone) e.getSource());
      Hexagone clic = (Hexagone) e.getSource();
      try {
         clic.setTerrain(Sol.NEIGE);
      } catch (IOException e1) {
         e1.printStackTrace();
      }
      // recuperer informations CASE/celulle/hexagone
      // solutions :
         // Hexagone doit garder POINT
         //
         //
      // fin Solutions
   }
}