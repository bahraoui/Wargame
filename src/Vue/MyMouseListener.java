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
      System.out.println((Cellule) e.getSource());
   }
   
   public static BufferedImage getTexturedImage(BufferedImage src, Shape shp, int x, int y) {
      Rectangle r = shp.getBounds();
      // create a transparent image with 1 px padding.
      BufferedImage tmp = new BufferedImage(r.width+2,r.height+2,BufferedImage.TYPE_INT_ARGB);
      // get the graphics object
      Graphics2D g = tmp.createGraphics();
      // set some nice rendering hints
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
      // create a transform to center the shape in the image
      AffineTransform centerTransform = AffineTransform.getTranslateInstance(-r.x+1, -r.y+1);
      // set the transform to the graphics object
      g.setTransform(centerTransform);
      // set the shape as the clip
      g.setClip(shp);
      // draw the image
      g.drawImage(src, x, y, null);
      // clear the clip
      g.setClip(null);
      // draw the shape as an outline
      g.setColor(Color.RED);
      g.setStroke(new BasicStroke(1f));
      g.draw(shp);
      // dispose of any graphics object we explicitly create
      g.dispose();
      
      return tmp;
   }
}