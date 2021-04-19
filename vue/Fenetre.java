//package Wargame;

import java.io.File;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre  extends JFrame{
    private JLabel labelImage = new JLabel("Photos");
    private JComboBox<String> combo;
    private JButton bouton = new JButton("Clique");

    public Fenetre() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        File f = new File("assets/images");
        String[] images = f.list();
        //System.out.println(images.toString());
        combo = new JComboBox<>(images);
        JPanel jPan = new JPanel();
        jPan.setLayout(new FlowLayout());
        jPan.add(labelImage);
        jPan.add(combo);
        jPan.add(bouton);
        this.add(jPan,BorderLayout.NORTH);
        this.setBounds(10, 10, 800, 600);
        this.setLocation(50, 50);
        this.setVisible(true);
    }

    public static void main(String [] args) {
        new Fenetre();
    }
}
