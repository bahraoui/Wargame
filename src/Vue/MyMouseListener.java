package Vue;

import java.awt.event.*;

import javax.swing.*;

/**
 * MyMouseListener
 */
public class MyMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
         Cellule l = (Cellule) e.getSource();

         if(l.getName().equals("name01"))
             doSomething01();
         else if(l.getName().equals("name02"))
             doSomething02();
    }
}