package Vue;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Jeu;

import java.awt.*;

public class PanelRegles  extends JPanel{

    private JButton btnQuitter;

    public PanelRegles() {
        super(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        JLabel titre1Jeu = new JLabel("1. Le Jeu");
        String regles1Jeu = new String("Un wargame (jeu de guerre) est un jeu permettant à un ou plusieurs joueurs de simuler des batailles. Un wargame est composé d'un système de jeu (règles) et de scénarios. <br> Dans un wargame tactique, un scénario représente une bataille où évoluent des unités d'au maximum quelques dizaines de soldats (compagnie). L'univers dans lequel le jeu se déroule est libre. On peut citer par exemple : historique (Mémoire 44, War in the east), fantastique (Battlelore, Battle for Wesnoth), science-_ction (Full metal planet, Crimson _eld,), . . .");
        String html1 = "<html><body style='width: ";
        String html2 = "px'>";
        JLabel txtRegles1 = new JLabel(html1+"600"+html2+regles1Jeu);

        JLabel titre2Plateau = new JLabel("2. Le plateau de jeu");
        String regles2Plateau = new String("Le jeu se déroule sur un espace de jeu découpé en cases hexagonales (voir la figure 1). Un hexagone représente l'unité de lieu et est d'un certain type (plaine, colline, montagne, . . .). Le jeu devra comporter au minimum cinq types différents de terrain. Le type de terrain a un effet sur les unités qui s'y trouvent (bonus d'attaque ou de défense, points de déplacement, . . .) (voir par exemple le tableau 1). Vous êtes libre d'adapter les types de terrain et les effets à votre contexte. Les effets peuvent varier en fonction du type d'unité (le type d'unité « elfe » se déplacera par exemple plus vite dans la forêt). Chaque hexagone ne peut accueillir qu'une seule unité.");
        JLabel txtRegles2 = new JLabel(html1+"600"+html2+regles2Plateau);

        JLabel titre3Unite = new JLabel("3. Les unites");
        String regles3Unite = new String("##############################################");
        JLabel txtRegles3 = new JLabel(html1+"600"+html2+regles3Unite);

        JLabel titre4Combat = new JLabel("4. Système de combat");
        String regles4Combat = new String("#############################################");
        JLabel txtRegles4 = new JLabel(html1+"600"+html2+regles4Combat);

        JLabel titre5Victoire = new JLabel("5. Comment gagner ?");
        String regles5Victoire  = new String("Dans un scénario, chaque adversaire peut avoir des conditions de victoire différentes. Deux conditions de victoire sont possibles : Destruction complète de l'armée adverse : cela représente la réussite de l'attaque d'un objectif, ou - Atteindre un numéro de tour sans avoir été détruit, i.e. Après un nombre donné de tours, un joueur possède encore au moins une unité : cela correspond à la réussite de la défense d'un objectif.");
        JLabel txtRegles5 = new JLabel(html1+"600"+html2+regles5Victoire);

        JLabel titre6Jouer = new JLabel("6. Lancer une partie");
        String regles6Jouer  = new String("##############################################");
        JLabel txtRegles6 = new JLabel(html1+"600"+html2+regles6Jouer);
        
        btnQuitter = new JButton("Quitter");
        btnQuitter.setActionCommand("retourMenu");
        

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

        contrainte.gridy++;
        this.add(btnQuitter,contrainte);

    }

    public void enregistreEcouteur(Jeu controleur) {
        btnQuitter.addActionListener(controleur);;
    }
}
