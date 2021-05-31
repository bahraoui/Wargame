package Vue;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import controleur.Jeu;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * PanelRegles contient toutes les règles nécéssaires afin que tout le monde puisse rapidement comprendre WarGame et pouvoir y jouer.
 * 
 * La partie centrale contient les règles.
 * En dessous de cette partie, se trouve un {@link JButton} permettant de retourner vers le menu {@link PanelMenu}.
 */
public class PanelRegles  extends JPanel{

    private JButton btnQuitter;

    /**
     * Constructeur du panel.
     */
    public PanelRegles() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();

        /*
         * 1ere partie : explication du jeu 
         */
        JLabel titre1Jeu = new JLabel("1. Le Jeu");
        String regles1Jeu = new String("Un wargame (jeu de guerre) est un jeu permettant à un ou plusieurs joueurs de simuler des batailles. Un wargame est composé d'un système de jeu (règles) et de scénarios. <br> Dans un wargame tactique, un scénario représente une bataille où évoluent des unités d'au maximum quelques dizaines de soldats (compagnie). L'univers dans lequel le jeu se déroule est libre. On peut citer par exemple : historique (Mémoire 44, War in the east), fantastique (Battlelore, Battle for Wesnoth), science-_ction (Full metal planet, Crimson _eld,), . . .");
        String html1 = "<html><body style='width: ";
        String html2 = "px'>";
        JLabel txtRegles1 = new JLabel(html1+"600"+html2+regles1Jeu);

        /*
         * 2eme partie : explication du plateau de jeu
         */
        JLabel titre2Plateau = new JLabel("2. Le plateau de jeu");
        String regles2Plateau = new String("Le jeu se déroule sur un espace de jeu découpé en cases hexagonales. Un hexagone représente l'unité de lieu et est d'un certain type (plaine, colline, montagne, . . .). Le jeu comporte au cinq types différents de terrain. Le type de terrain a un effet sur les unités qui s'y trouvent (bonus d'attaque ou de défense, points de déplacement, . . .). Vous êtes libre d'adapter les types de terrain et ajouter les monuments selon vos envies. Chaque hexagone ne peut accueillir qu'une seule entité.");
        JLabel txtRegles2 = new JLabel(html1+"600"+html2+regles2Plateau);

        /*
         * 3eme partie : explication des unites, sous la forme d'un tableau formatte en HTML
         */
        JLabel titre3Unite = new JLabel("3. Les unites");
        String regles3Unite = new String("Il existe 5 type d'unite possibles : <br/><ol><li>Archer:<br/>Un archer possede</li><li>Cavalerie:<br/>ue cavalerie est</li><li>Infanterie:<br/></li><li>InfanterieLourde:<br/></li><li>Mage:<br/></li></ol>");
        regles3Unite = new String("<table style='border-collapse: collapse;'> "+
                                    "<tr style='border: 1px solid black; text-align:center;' >"+
                                        "<td>Nom Unite</td>"+
                                        "<td>ATK</td>"+
                                        "<td>DEF</td>"+
                                        "<td>PV</td>"+
                                        "<td>Déplacement</td>"+
                                        "<td>Vision</td>"+
                                        "<td>Cout (en Golds)</td>"+
                                    "</tr>"+
                                    "<tr style='border: 1px solid black; text-align:center;' >"+
                                        "<td>Archer</td>"+
                                        "<td>40</td>"+
                                        "<td>20</td>"+
                                        "<td>20</td>"+
                                        "<td>3</td>"+
                                        "<td>4</td>"+
                                        "<td>5</td>"+
                                    "</tr>"+
                                    "<tr style='border: 1px solid black; text-align:center;' >"+
                                        "<td>Cavalerie</td>"+
                                        "<td>50</td>"+
                                        "<td>20</td>"+
                                        "<td>25</td>"+
                                        "<td>40</td>"+
                                        "<td>2</td>"+
                                        "<td>12</td>"+
                                    "</tr>"+
                                    "<tr style='border: 1px solid black; text-align:center;' >"+
                                        "<td>Infanterie</td>"+
                                        "<td>25</td>"+
                                        "<td>35</td>"+
                                        "<td>40</td>"+
                                        "<td>2</td>"+
                                        "<td>1</td>"+
                                        "<td>6</td>"+
                                    "</tr>"+
                                    "<tr style='border: 1px solid black; text-align:center;' >"+
                                        "<td>InfanterieLourde</td>"+
                                        "<td>15</td>"+
                                        "<td>45</td>"+
                                        "<td>120</td>"+
                                        "<td>2</td>"+
                                        "<td>2</td>"+
                                        "<td>30</td>"+
                                    "</tr>"+
                                    "<tr style='border: 1px solid black; text-align:center;' >"+
                                        "<td>Mage</td>"+
                                        "<td>65</td>"+
                                        "<td>30</td>"+
                                        "<td>35</td>"+
                                        "<td>3</td>"+
                                        "<td>3</td>"+
                                        "<td>20</td>"+
                                    "</tr>"+
                                "</table>");
        
        JLabel txtRegles3 = new JLabel(html1+"600"+html2+regles3Unite+"</html>");

        /*
         * 4eme partie : explication du systeme de combat
         */
        JLabel titre4Combat = new JLabel("4. Système de combat");
        String regles4Combat = new String("Les entités de type \"unité\" comme vu ci-dessus peuvent attaquer toutes les autres entités. En effet, un unité peut en attaquer une autre, et dans ce cas l'unité attaquée reçoit (ATK unité attaquante - DEF unité attaqué) PV en moins.");
        JLabel txtRegles4 = new JLabel(html1+"600"+html2+regles4Combat);

        /*
         * 5eme partie : explication des conditions de victoire
         */
        JLabel titre5Victoire = new JLabel("5. Comment gagner ?");
        String regles5Victoire  = new String("Dans un scénario, chaque adversaire peut avoir des conditions de victoire différentes. Deux conditions de victoire sont possibles : Destruction complète de l'armée adverse : cela représente la réussite de l'attaque d'un objectif, ou - Atteindre un numéro de tour sans avoir été détruit, i.e. Après un nombre donné de tours, un joueur possède encore au moins une unité : cela correspond à la réussite de la défense d'un objectif.");
        JLabel txtRegles5 = new JLabel(html1+"600"+html2+regles5Victoire);

        /*
         * 6eme partie : explication d'un lancement de partie
         */
        JLabel titre6Jouer = new JLabel("6. Lancer une partie");
        String regles6Jouer  = new String("Pour pouvoir lancer une partie, deux choix s'offrent à vous : reprendre une partie sauvegardée ou en créer une nouvelle.<br/>Lors de la création d'une nouvelle partie, il vous est possible de changer le scénario du jeu : placement de terrains & placement limité de monument. Après cela vous pouvez sauvegarder le scénario pour pouvoir le réutiliser comme bsae plus tard lors de la création d'un nouveau scénario. <br/>Puis, pour chargez une partie sauvegardée, il suffit de choisir le fichier contenant votre sauvegarde puis de lancer.");
        JLabel txtRegles6 = new JLabel(html1+"600"+html2+regles6Jouer);
        
        /*
         * Bouton "Quitter" 
         */
        btnQuitter = new JButton();
        btnQuitter.setMargin(new Insets(0, 0, 0, 0));
        btnQuitter.setBorder(null);
        try {
            btnQuitter.setIcon(new ImageIcon(ImageIO.read(new File("assets"+File.separator+"images"+File.separator+"boutons"+File.separator+"Quitter2.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnQuitter.setActionCommand("retourMenu");
        

        /*
         * Placement des differentes parties 
         */
        contrainte.gridx=0;
        contrainte.gridy=0;
        this.add(new JLabel("Règles"),contrainte);
        contrainte.gridy++;
        this.add(titre1Jeu,contrainte);
        contrainte.gridy++;
        this.add(txtRegles1,contrainte);

        contrainte.gridy++;
        this.add(titre2Plateau,contrainte);
        contrainte.gridy++;
        this.add(txtRegles2,contrainte);

        contrainte.gridy++;
        this.add(titre3Unite,contrainte);
        contrainte.gridy++;
        this.add(txtRegles3,contrainte);

        contrainte.gridy++;
        this.add(titre4Combat,contrainte);
        contrainte.gridy++;
        this.add(txtRegles4,contrainte);

        contrainte.gridy++;
        this.add(titre5Victoire,contrainte);
        contrainte.gridy++;
        this.add(txtRegles5,contrainte);

        contrainte.gridy++;
        this.add(titre6Jouer,contrainte);
        contrainte.gridy++;
        this.add(txtRegles6,contrainte);

        /*
         * placement du bouton "quitter"
         */
        contrainte.gridy++;
        this.add(btnQuitter,contrainte);

    }

    /**
	 * La methode enregistreEcouteur met a l'ecoute tous les elements du panel pour le controleur
	 * @param controleur controleur que l'on souhaite mettre a l'ecoute
	 */
    public void enregistreEcouteur(Jeu controleur) {
        btnQuitter.addActionListener(controleur);;
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
