package Vue;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * La classe HexagonalLayout heritant de LayoutManager permet de placer nos cases hexagonal 
 */
public class HexagonalLayout implements LayoutManager {
    
    private Insets insets;
    private int rows;
    private int cols;
    private int nbComposants;
    private boolean beginWithSmallRow;
    
    private Dimension minSize;
    private Dimension prefSize;
    
    /**
     * Le constructeur de HexagonalLayout permet d'intancier un HexagonalLayout avec :
     * - le nombre de colonnes
     * - l'ecart entre les cases
     * - un boolean qui permet de savoir si l'on commence par une ligne longue ou courte
     * - le nombre de cases total
     * @param cols int
     * @param i Insets
     * @param beginWithSmallRow boolean
     * @param nbComposants int
     */
    public HexagonalLayout(int cols, Insets i, boolean beginWithSmallRow, int nbComposants) {
        checkColInput(cols);
        insets = i;
        minSize = new Dimension(800, 600); // Taille par defaut du layout
        prefSize = new Dimension(800, 600);
        this.cols = cols; // le nombre de colonnes
        this.beginWithSmallRow = beginWithSmallRow; // booleen permettant de savoir si la premiere ligne est petite ou non
        this.rows = calculateRows(nbComposants); // nombre de lignes
        this.nbComposants = nbComposants; // nombre d'elements
    }
    
    /**
    * checkColInput permet de savoir si le nombre de colonne est invalide : cols > 0 
    * @param cols int
    */
    private void checkColInput(int cols) {
        if (cols <= 0) {
            throw new IllegalArgumentException("Columns can't be set to n < 0");
        }
    }
    
    /**
    * Calcul le nombre de lignes en fonctions du nombre de colonnes et du nombre total de cases
    * @param componentCount int
    * @return numberOfRows int
    */
    private int calculateRows(int componentCount) {
        int numberOfRows = 0;
        int bgRow = cols;
        int smRow = bgRow - 1;
        
        int placedItems = 0;
        if (beginWithSmallRow) {
            while (true) {
                if (placedItems >= componentCount) {
                    break;
                }
                placedItems += smRow;
                numberOfRows += 1;
                if (placedItems >= componentCount) {
                    break;
                }
                placedItems += bgRow;
                numberOfRows += 1;
            }
        } else {
            while (true) {
                if (placedItems >= componentCount) {
                    break;
                }
                placedItems += bgRow;
                numberOfRows += 1;
                if (placedItems >= componentCount) {
                    break;
                }
                placedItems += smRow;
                numberOfRows += 1;
            }
            
        }
        return numberOfRows;
    }
    
    /*
    * (non-Javadoc)
    * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
    */
    @Override
    public void layoutContainer(Container parent) {
        
        // recupere le nombre d'elements et verifie leur nombre si nul
        int componentCount = parent.getComponentCount();
        if (componentCount == 0) {
            return;
        }
        
        // Savoir si on commence par une petite ou grande ligne
        boolean smallRow = beginWithSmallRow;
        
        // bordures
        int leftOffset = insets.left;
        int rightOffset = insets.right;
        int topOffset = insets.top;
        int bottomOffset = insets.bottom;
        
        // espacer les elements
        int boxWidth = parent.getWidth() / cols;
        int boxHeight = (int) Math.round((parent.getHeight() / (1 + (0.75 * (rows - 1)))));
        double heightRatio = 0.75;
        
        // dimensions des elements
        int cWidth = (boxWidth - (leftOffset + rightOffset));
        int cHeight = (boxHeight - (topOffset + bottomOffset));
        
        
        int x;
        int y;
        if(smallRow)
        x = (int)Math.round((boxWidth / 2.0));
        else
        x = 0;
        y = 0;
        
        // Disposer tous les elements dans le conteneur
        for (Component c : parent.getComponents()) {
            if (x > parent.getWidth() - boxWidth) {
                smallRow = !smallRow;
                y += Math.round(boxHeight * heightRatio);
                if (smallRow)
                x = (int)Math.round(boxWidth / 2.0);
                else
                x = 0;
            }
            c.setBounds(x + leftOffset, y + topOffset, cWidth, cHeight);
            x += boxWidth;
        }
        
    }
    
    /*
    * (non-Javadoc)
    * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
    */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return minSize;
    }
    
    /*
    * (non-Javadoc)
    * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
    */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return prefSize;
    }
    
    /*
    * (non-Javadoc)
    * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
    */
    @Override
    public void removeLayoutComponent(Component comp) {
    }
    
    // Getters et setters : 

    public Insets getInsets() {
        return insets;
    }
    
    public void setInsets(Insets insets) {
        this.insets = insets;
    }
    
    public int getColumns() {
        return cols;
    }
    
    public void setColumns(int cols) {
        this.cols = cols;
    }
    
    public boolean isBeginWithSmallRow() {
        return beginWithSmallRow;
    }
    
    public void setBeginWithSmallRow(boolean beginWithSmallRow) {
        this.beginWithSmallRow = beginWithSmallRow;
    }
    
    public Dimension getMinimumSize() {
        return minSize;
    }
    
    public void setMinimumSize(Dimension minSize) {
        this.minSize = minSize;
    }
    
    public Dimension getPreferredSize() {
        return prefSize;
    }
    
    public void setPrefferedSize(Dimension prefSize) {
        this.prefSize = prefSize;
    }
    
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public int getNbComposants() {
        return this.nbComposants;
    }

    // FIN Getters et setters
    
    @Override
    public void addLayoutComponent(String arg0, Component arg1) {
        // TODO Auto-generated method stub
    }
}
