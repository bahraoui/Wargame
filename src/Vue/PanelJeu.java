package Vue;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class PanelJeu extends JPanel{
    private BorderLayout bdl;

    public PanelJeu() {
        super();
        this.bdl = new BorderLayout();
        setLayout(bdl);
    }

}
