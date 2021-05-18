package Vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// a suppr
public class HexagonPattern extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int ROWS = 7;
    private static final int COLUMNS = 7;
    private HexagonButton[][] hexButton = new HexagonButton[ROWS][COLUMNS];
    
    
    public HexagonPattern() {
        setLayout(null);
        initGUI();
    }
    
    
    public void initGUI() {
        int offsetY = 42;
        int offsetX = 20;
        
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLUMNS; col++){
                hexButton[row][col] = new HexagonButton(row, col);
                hexButton[row][col].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        HexagonButton clickedButton = (HexagonButton) e.getSource();
                        System.out.println("Button clicked: [" + clickedButton.getRow() + "][" + clickedButton.getCol() + "]");
                        //Graphics g = clickedButton.getGraphics();
                    }
                });
                add(hexButton[row][col]);
                hexButton[row][col].setBounds(offsetX, offsetY, 105, 95);
                offsetY += 87;
            }
            if(row%2 == 0) {
                offsetY = 0;
            } else {
                offsetY = 42;
            }
            offsetX += 76;
        }
    }
    
    public static void main(String[] args) {
        HexagonPattern hexPattern = new HexagonPattern();
        JFrame frame = new JFrame();
        frame.setTitle("Hexagon Pattern");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocation(new Point(100, 100));
        frame.add(hexPattern);
        frame.setSize(1000, 1000);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    //Following class draws the Buttons
    class HexagonButton extends JButton {
        private static final long serialVersionUID = 1L;
        private static final int SIDES = 6;
        private static final int SIDE_LENGTH = 50;
        public static final int LENGTH = 95;
        public static final int WIDTH = 105;
        private int row = 0;
        private int col = 0;
        
        public HexagonButton(int row, int col) {
            setContentAreaFilled(false);
            setFocusPainted(true);
            setBorderPainted(false);
            setPreferredSize(new Dimension(WIDTH, LENGTH));
            this.row = row;
            this.col = col;
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Polygon hex = new Polygon();
            for (int i = 0; i < SIDES; i++) {
                hex.addPoint((int) (50 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                (int) (50 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
            }       
            g.drawPolygon(hex);
        }
        
        public int getRow() {
            return row;
        }
        
        public int getCol() {
            return col;
        }
    }
}