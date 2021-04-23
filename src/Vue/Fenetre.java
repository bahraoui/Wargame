package Vue;

import java.io.File;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.scene.image.Image;

public class Fenetre  extends JFrame{
    private JLabel labelImage = new JLabel("Photos");
    private JComboBox<String> combo;
    private JButton bouton = new JButton("Clique");
    private JLabel ii;

    public Fenetre() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        File f = new File("assets/images");
        String[] images = f.list();
        //System.out.println(images.toString());
        combo = new JComboBox<>(images);
        JPanel jPan = new JPanel();
        ii = new JLabel("img");
        add(ii);
        jPan.setLayout(new FlowLayout());
        jPan.add(labelImage);
        jPan.add(combo);
        jPan.add(bouton);
        this.add(jPan,BorderLayout.NORTH);
        this.add(ii,BorderLayout.SOUTH);
        bouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(ii);
                ImageIcon img = new ImageIcon("assets/images/"+combo.getSelectedItem().toString());
                ii = new JLabel(img);
                //ii.setSize(150,150);
                add(ii);
                repaint();
                //System.out.println(combo.getSelectedItem().toString());
            }
        });
        this.setBounds(10, 10, 800, 600);
        this.setLocation(50, 50);
        this.setVisible(true);
        //this.setResizable(false);
    }

    public static void main(String [] args) {
        new Fenetre();
    }
}
