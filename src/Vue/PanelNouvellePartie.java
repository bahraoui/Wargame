package Vue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controleur.Jeu;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PanelNouvellePartie extends JPanel{
    private JComboBox<String> choixMap;
    private JComboBox<Integer> nbJoueursHumain;
    private JComboBox<Integer> nbJoueursIA;
    private JButton btnContinuer;
    private JButton btnQuitter;
    private JLabel[] nomJoueur;
    private JTextField[] txtNomJoueur;

    public PanelNouvellePartie() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        Integer[] nbJoueursH = {0,1,2,3,4};
        Integer[] nbJoueursIAInteger= {0,1,2,3,4};
        
        nbJoueursHumain = new JComboBox<Integer>(nbJoueursH);
        nbJoueursIA = new JComboBox<Integer>(nbJoueursIAInteger);
        choixMap = new JComboBox<String>();
        nomJoueur = new JLabel[4];
        txtNomJoueur = new JTextField[4];
        btnContinuer = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Continuer.png", btnContinuer);
        btnQuitter = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter2.png", btnQuitter);
        choixMap.setActionCommand("choixMap");
        nbJoueursHumain.setActionCommand("nbJoueursH");
        nbJoueursIA.setActionCommand("nbJoueursIA");
        btnContinuer.setActionCommand("nouvellePartieContinuer");
        btnQuitter.setActionCommand("retourMenu");

        contrainte.gridx=0;
        contrainte.gridy=0;
        this.add(new JLabel("Nom Map : "),contrainte);
        contrainte.gridx++;
        this.add(choixMap,contrainte);
        contrainte.gridx=0;
        contrainte.gridy++;
        this.add(new JLabel("Joueur(s) Huamin(s) : "),contrainte);
        contrainte.gridx++;
        this.add(nbJoueursHumain,contrainte);
        contrainte.gridx=0;
        contrainte.gridy++;
        this.add(new JLabel("Joueur IA : "),contrainte);
        contrainte.gridx++;
        this.add(nbJoueursIA,contrainte);
        contrainte.gridx=0;
        contrainte.gridy++;
        for (int i = 0; i < 4; i++) {
            contrainte.gridx=0;
            nomJoueur[i] = new JLabel("Nom Joueur "+(i+1));
            txtNomJoueur[i] = new JTextField(10);
            this.add(nomJoueur[i], contrainte);
            contrainte.gridx++;
            this.add(txtNomJoueur[i],contrainte);
            nomJoueur[i].setVisible(false);
            txtNomJoueur[i].setVisible(false);
            contrainte.gridy++;
        }
        this.add(btnContinuer,contrainte);
        contrainte.gridx++;
        this.add(btnQuitter,contrainte);
    }

    public boolean setAllNames(int max){
        if (max > 4) {
            max = 4;
        }
        for (int i = 0; i < max; i++) {
            if (txtNomJoueur[i].getText().equals("")){
                return false;
            }
        }
        return true;
    }

    public void initListeCartes(ArrayList<String> nomMap){
        nomMap.add("");
        File dossierCartes = new File("src"+File.separator+"data"+File.separator+"cartes"+File.separator);
        File[] listeNomCartes = dossierCartes.listFiles();

        for (int i = 0; i < listeNomCartes.length; i++) {
            if (listeNomCartes[i].isFile()) {
                nomMap.add(listeNomCartes[i].getName().replace(".txt", ""));
            }
        }
    }


    /**
     * setImageBouton permet d'afficher une image dans le bouton
     * @param filePathName String
     * @param btnAModifier JButton
     */
	private void setImageBouton(String filePathName,JButton btnAModifier){
        btnAModifier.setMargin(new Insets(0, 0, 0, 0));
        btnAModifier.setBorder(null);
        try {
            btnAModifier.setIcon(new ImageIcon(ImageIO.read(new File(filePathName))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enregistreEcouteur(Jeu controleur) {
        btnContinuer.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
        choixMap.addActionListener(controleur);
        nbJoueursHumain.addActionListener(controleur);
        nbJoueursIA.addActionListener(controleur);
    }
    public JLabel[] getNomJoueur() {
        return this.nomJoueur;
    }

    public JTextField[] getTxtNomJoueur() {
        return this.txtNomJoueur;
    }


    public JComboBox<String> getChoixMap() {
        return this.choixMap;
    }

    public void setChoixMap(ArrayList<String> listNomMap) {
        this.choixMap.removeAllItems();
        for (String nomMap : listNomMap) {
            this.choixMap.addItem(nomMap); 
        }
    }

    public JComboBox<Integer> getNbJoueursHumain() {
        return this.nbJoueursHumain;
    }

    public void setNbJoueursHumain(JComboBox<Integer> nbJoueursHumain) {
        this.nbJoueursHumain = nbJoueursHumain;
    }

    public JComboBox<Integer> getNbJoueursIA() {
        return this.nbJoueursIA;
    }

    public void setNbJoueursIA(JComboBox<Integer> nbJoueursIA) {
        this.nbJoueursIA = nbJoueursIA;
    }

    public JButton getBtnContinuer() {
        return this.btnContinuer;
    }

    public void setBtnContinuer(JButton btnContinuer) {
        this.btnContinuer = btnContinuer;
    }

    public JButton getBtnQuitter() {
        return this.btnQuitter;
    }

    public void setBtnQuitter(JButton btnQuitter) {
        this.btnQuitter = btnQuitter;
    }
    public void setNomJoueur(JLabel[] nomJoueur) {
        this.nomJoueur = nomJoueur;
    }
    public void setTxtNomJoueur(JTextField[] txtNomJoueur) {
        this.txtNomJoueur = txtNomJoueur;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"Fonds"+File.separator+"fond.jpg")), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
