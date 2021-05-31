package Vue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.Jeu;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * PanelNouvellePartie contient tous les elements visuels permettant la creation d'une nouvelle partie.
 * 
 * Premierement, une liste de map est propose sous la forme d'une {@link JComboBox}, puis deux autres combobox afin de donner le nombre de joueurs humain/IA.
 * Ensuite, jusqu'a 4 {@link JTextField} pour inscrire les pseudos des joueurs.
 * Enfin, deux {@link JButton} pour retourner vers le menu {@link PanelMenu} ou pour continuer vers {@link PanelChargerScenario}.
 * 
 */
public class PanelNouvellePartie extends JPanel{
    private JComboBox<String> choixMap;
    private JComboBox<Integer> nbJoueursHumain;
    private JComboBox<Integer> nbJoueursIA;
    private JButton btnContinuer;
    private JButton btnQuitter;
    private JLabel[] nomJoueur;
    private JTextField[] txtNomJoueur;

    /**
     * Constructeur du panel.
     */
    public PanelNouvellePartie() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        Integer[] nbJoueursH = {0,1,2,3,4};
        Integer[] nbJoueursIAInteger= {0,1,2,3,4};
        
        /*
         * Boutons, listes et champs de formulaire d'une nouvelle partie
         */
        nbJoueursHumain = new JComboBox<Integer>(nbJoueursH);
        nbJoueursHumain.setRenderer(new MyComboboxRenderer());
        nbJoueursIA = new JComboBox<Integer>(nbJoueursIAInteger);
        nbJoueursIA.setRenderer(new MyComboboxRenderer());
        choixMap = new JComboBox<String>();
        choixMap.setRenderer(new MyComboboxRenderer());
        nomJoueur = new JLabel[4];
        txtNomJoueur = new JTextField[4];
        btnContinuer = new JButton();
        btnQuitter = new JButton();
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Continuer.png", btnContinuer);
        setImageBouton("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter2.png", btnQuitter);
        choixMap.setActionCommand("choixMap");
        nbJoueursHumain.setActionCommand("nbJoueursH");
        nbJoueursIA.setActionCommand("nbJoueursIA");
        btnContinuer.setActionCommand("nouvellePartieContinuer");
        btnQuitter.setActionCommand("retourMenu");

        /*
         * Placement des elements 
         */
        contrainte.gridx=0;
        contrainte.gridy=0;
        JLabel labelNomMap = new JLabel("Nom Map : ");
        labelNomMap.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
        labelNomMap.setForeground(new Color(109,7,26));
        this.add(labelNomMap,contrainte);
        contrainte.gridx++;
        this.add(choixMap,contrainte);
        contrainte.gridx=0;
        contrainte.gridy++;
        JLabel labelHumains = new JLabel("Joueur(s) Huamin(s) : ");
        labelHumains.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
        labelHumains.setForeground(new Color(109,7,26));
        this.add(labelHumains,contrainte);
        contrainte.gridx++;
        this.add(nbJoueursHumain,contrainte);
        contrainte.gridx=0;
        contrainte.gridy++;
        JLabel labelJoueursIA = new JLabel("Joueur IA : ");
        labelJoueursIA.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
        labelJoueursIA.setForeground(new Color(109,7,26));
        this.add(labelJoueursIA,contrainte);
        contrainte.gridx++;
        this.add(nbJoueursIA,contrainte);
        contrainte.gridx=0;
        contrainte.gridy++;
        for (int i = 0; i < 4; i++) {
            contrainte.gridx=0;
            JLabel labelNom = new JLabel("Nom Joueur "+(i+1));
            labelNom.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
            labelNom.setForeground(new Color(109,7,26));
            nomJoueur[i] = labelNom;
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

    /**
     * Verifie la saisie des pseudos.
     * @param max le nombre maximum de pseudos à vérifier.
     * @return Si saisie des pseudos bien faite.
     */
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

    /**
     * Initialise la liste des cartes à afficher.
     * @param nomMap la variable à initialiser.
     */
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
     * @param filePathName le chemin de l'image à utiliser
     * @param btnAModifier le bouton à modifier
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

    /**
	 * La methode enregistreEcouteur met a l'ecoute tous les elements du panel pour le controleur
	 * @param controleur controleur que l'on souhaite mettre a l'ecoute
	 */
    public void enregistreEcouteur(Jeu controleur) {
        btnContinuer.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
        choixMap.addActionListener(controleur);
        nbJoueursHumain.addActionListener(controleur);
        nbJoueursIA.addActionListener(controleur);
    }
    
    // Getters et setters

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

    // FIN getters et setters

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
