package Vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;


public class HexTest extends JFrame{

    public static void main(String[] args) {
	JFrame f = new JFrame();
	f.setExtendedState(JFrame.MAXIMIZED_BOTH);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	



	f.setLayout(new HexagonalLayout(20, new Insets(1,1,1,1), false));
	ArrayList<HexagonalButton> tab = new ArrayList<HexagonalButton>();

	for (int i = 0; i < 254; i++) { // Change the number in the loop to get
					// more/less buttons

	    HexagonalButton b = new HexagonalButton();
		Point p = new Point(i);
		//b.setEmplacement(p);
		tab.add(b);
	    b.setBackground(Color.blue);
		b.setForeground(Color.RED);


	    //"Random" color actionlistener, just for fun.
	    b.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    JButton a = (JButton) e.getSource();
		    a.setBackground(Color.RED);
			a.setForeground(Color.BLUE);
            System.out.println(b.toString());
		}

	    });
	    f.add(b);
	}

	f.setVisible(true);
    f.setSize(1500,900);
    f.setLocation(50, 50);
    f.setResizable(false);
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    //System.out.println("Width = "+width+"\n Height = "+height);
    }
}