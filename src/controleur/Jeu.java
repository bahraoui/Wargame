package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Vue.FrameJeu;
import Vue.Hexagone;
import Vue.PanelActuel;
import Vue.PanelChangerScenario;
import Vue.PanelChargerPartie;
import Vue.PanelJeu;
import Vue.Point;
import Vue.TypeBatimentVue;
import Vue.TypeTerrain;
import Vue.TypeUnite;

import modele.Node;
import modele.entite.Entite;
import modele.entite.batiment.Batiment;
import modele.entite.batiment.TypeBatiment;
import modele.entite.unite.Archer;
import modele.entite.unite.Cavalerie;
import modele.entite.unite.Infanterie;
import modele.entite.unite.InfanterieLourde;
import modele.entite.unite.Mage;
import modele.entite.unite.Unite;
import modele.joueur.Joueur;
import modele.plateau.Case;
import modele.plateau.Plateau;
import modele.terrain.Desert;
import modele.terrain.Foret;
import modele.terrain.Mer;
import modele.terrain.Montagne;
import modele.terrain.Plaine;
import modele.terrain.Terrain;
import modele.terrain.ToundraNeige;

public class Jeu extends MouseAdapter implements ActionListener {
    private static Plateau plateau;
    private static ArrayList<ArrayList<Integer>> postionBaseJoueur;
    private static ArrayList<Joueur> listeJoueur;
    private static Joueur joueurActuel;
    private static int tour;
    private static boolean finpartie;
    private static FrameJeu FenetreJeu;
    private static Integer nbJoueursH;
    private static Integer nbJoueursIA;
    private static String carteChoisis;
    private static File sauvegardeChoisis;
    private static Cellule[][] cellulesCarte;
    private static PanelJeu pj;
    private static TypeTerrain terrainChoisi;
    private static boolean selectionMonument;
    private static PanelChangerScenario panelChangerScenario;
    private static TypeUnite uniteAchete;
    private static Case caseClic1, caseClic2;
    private static Hexagone hexCaseClic;
    private static Joueur joueurGagnant;
    private static boolean initPanelJeu;
    private static boolean chercheAttaque;

    private static final int cote = 16;

    //
    // MAIN
    //
    public static void main(String[] args) {

        Jeu controleur = new Jeu();

        carteChoisis = "";
        finpartie = false;
        selectionMonument = false;
        uniteAchete = null;

        caseClic1 = null;
        caseClic2 = null;
        initPanelJeu = false;
        joueurGagnant = null;
        chercheAttaque = false;

        terrainChoisi = TypeTerrain.NEIGE;

        nbJoueursH = nbJoueursIA = 0;
        listeJoueur = new ArrayList<Joueur>();
        postionBaseJoueur = new ArrayList<ArrayList<Integer>>();
        plateau = new Plateau();

        cellulesCarte = new Cellule[16][16];
        setCellulesMap();

        FenetreJeu = new FrameJeu();
        FenetreJeu.enregistreEcouteur(controleur);
    }
    //
    // FONCTION
    //

    //
    // DONNES
    //

    /**
     * La fonction combat permet de générer un combat entre deux entités du terrain.
     * Elle prend prend en paramètre les deux hexagones du combat et la distances
     * entre les deux hexagones sur la carte La fonction renvoie true si le combat
     * s'est bien passé, false sinon
     * 
     * @param attaquantHex  hexagone qui est à l'origine de l'attaque
     * @param defenseurHex  hexagone qui est attaqué
     * @param distanceCases distante entre les deux hexagones
     * @return
     */
    public static boolean combattre(Hexagone attaquantHex, Hexagone defenseurHex, int distanceCases) {
        int rangeAttaque = 0;
        // on récupérer les cases où se trouve l'attaquant et le défenseur grace aux
        // hexagones donnés en paramètre
        Case attaquantCase = plateau.get(attaquantHex.getCoord().getX()).get(attaquantHex.getCoord().getY());
        Case defenseCase = plateau.get(defenseurHex.getCoord().getX()).get(defenseurHex.getCoord().getY());

        Cellule cellTest;

        // on récupere la distance a laquelle l'unité peut frapper
        if (attaquantCase.getUnite() != null) {
            rangeAttaque = attaquantCase.getUnite().getVision();
        }

        // si l'unité est assez proche
        if (rangeAttaque >= distanceCases) {
            // si l'unité n'a pas déjà attaqué
            if (attaquantCase.getUnite() != null && attaquantCase.getUnite().getAAttaque() == false) {
                // on lance l'attaque
                Case.attaquer(attaquantCase, defenseCase);

                // si l'entité est tué
                if (defenseCase.estOccupe() != null && ((Entite) defenseCase.estOccupe()).getPointDeVieActuel() <= 0) {
                    // on supprime l'entité

                    // on lance la fonction qui enleve l'entité du plateau
                    mortEntite(defenseCase);
                    cellTest = new Cellule(defenseurHex, defenseCase);
                    cellTest.clear();

                    // on verifie si la partie est terminé
                    calculVitoire();

                }
                return true;
            }
            // si le joueur a déjà attaqué
            else {
                //affichage pour les joueurs humains
                if (!joueurActuel.getEstIa())
                    JOptionPane.showMessageDialog(FenetreJeu, "Votre unité a déjà attaqué !");
            }
        }
        return false;
    }

    /**
     * Cette fonction est utilisé lors de la mort d'une entité La fonction enlève
     * l'entité du plateau dans la structure de données non à l'affichage
     * @param caseEntiteMorte case sur laquelle l'entité est morte
     */
    public static void mortEntite(Case caseEntiteMorte) {
        // si l'entité est une unité
        if (caseEntiteMorte.getUnite() != null) {
            // on enleve l'unité de l'armée du joueur et du plateau
            for (int i = 0; i < listeJoueur.size(); i++) {
                // on récupère un joueur de la partie
                Joueur joueur = listeJoueur.get(i);
                for (int j = 0; j < joueur.getArmee().size(); j++) {
                    // recherche de l'unité par rapport à son identifiant
                    if (caseEntiteMorte.getUnite().getIdentifiant() == joueur.getArmee().get(j).getIdentifiant()) {
                        // on enlève l'unité de l'armée
                        joueur.getArmee().remove(j);
 
                        // si après cette perte, l'armée n'a plus d'unité dans son armée et qu'il n'a
                        // pas plus de pieces alors on change son statut
                        if (joueur.getArmee().size() == 0 && joueur.getPieces() < 6) {
                            joueur.setEnJeu(false);
                        }
                        return;
                    }
                }
            }
        }
        // si l'entité est un batiment
        else if (caseEntiteMorte.getBatiment() != null) {
            // si l'entité est une base
            if (caseEntiteMorte.getBatiment().getEstBase() == TypeBatiment.BASE) {
                for (int i = 0; i < listeJoueur.size(); i++) {
                    // on récupère un joueur de la partie
                    Joueur joueur = listeJoueur.get(i);
                    // on regarde si l'entité morte correspond à la base du joueur
                    if (joueur.getBase().getIdentifiant() == caseEntiteMorte.getBatiment().getIdentifiant()) {
                        // on change le statut du joueur car il a perdu sa base et donc la partie
                        joueur.setEnJeu(false);
                        // on recupère les coordonnées de la base du joueur et on l'enlève du plateau
                        ArrayList<Integer> coordBase = postionBaseJoueur.get(joueur.getNumeroJoueur());
                        plateau.get(coordBase.get(0)).get(coordBase.get(1)).setBatiment(null);
                        supprimerArmee(joueur);
                        JOptionPane.showMessageDialog(FenetreJeu,joueur.getPseudo()+" est hors d'état de nuire !");
                        return;
                    }
                }
            }
            // si l'entité est un monument
            else {
                // on supprime le monument du plateau et on ajoute le trésor à la banque du joueur
                joueurActuel.setPieces(joueurActuel.getPieces() + caseEntiteMorte.getBatiment().getTresor());
                FenetreJeu.getPanelJeu().updateGoldAffichage(joueurActuel.getPieces());
            }   
        }
    }

    /**
     * Cette fonction place les base de tous les joueurs
     */
    public static void placerBasesJoueurs() {
        int nbJoueurs = listeJoueur.size();
        placerBase(listeJoueur.get(0), 0, 0);
        placerBase(listeJoueur.get(1), cote - 1, cote - 2);
        if (nbJoueurs >= 3) {
            placerBase(listeJoueur.get(2), 0, cote - 1);
        }
        if (nbJoueurs == 4) {
            placerBase(listeJoueur.get(3), cote - 1, 0);
        }
    }

    /**
     * Cette fonction place la base du joueur a des coordonnées données en paramètre
     * 
     * @param joueur Joueur a qui appartient la base du joueur a placé
     * @param coordY Coordonnées en Y de la base
     * @param coordX Coordonnées en X de la base
     */
    public static void placerBase(Joueur joueur, int coordY, int coordX) {
        // On crée la base du joueur
        Batiment base = new Batiment(TypeBatiment.BASE);
        // On assigne la base au joueur
        joueur.setBase(base);
        // On place dans le plateau la base
        plateau.get(coordY).get(coordX).setBatiment(base);
        // On garde en mémoire la position de la base du joueur
        ArrayList<Integer> baseJ1 = new ArrayList<Integer>();
        baseJ1.add(0, coordY);
        baseJ1.add(1, coordX);
        postionBaseJoueur.add(baseJ1);
    }

    /**
     * Cette fonction est utilisé pour placer l'unité d'un joueur sur le plateau Le
     * joueur peut placer une unité qu'autour de sa base La fonction renvoie True si
     * le placement s'est bien effectué, False sinon
     * 
     * @param joueur Joueur qui veut placer une unité sur le terrain
     * @param unite  Unite que le joueur veut placer
     * @param coordY Coordonnées en Y pour le placement du jeu
     * @param coordX Coordonnées en X pour le placement du jeu
     * @return Booleen indique si le placement s'est bien fait
     */
    public static boolean placerUniteJoueur(Joueur joueur, Unite unite, int coordY, int coordX) {
        // On récupère la case ou le joueur veut placer son unité
        Case caseUnite = plateau.get(coordY).get(coordX);
        // On récupère les coordonnées de la base du joueur
        int coordYBase = postionBaseJoueur.get(joueur.getNumeroJoueur()).get(0);
        int coordXBase = postionBaseJoueur.get(joueur.getNumeroJoueur()).get(1);
        // On calcule la distance entre la base et le placement de l'unité
        int calculDistanceY = coordY - coordYBase;
        int calculDistanceX = coordX - coordXBase;
        // Si il n'y a aucune entité sur la case et que le placement est assez proche de
        // la base
        if (caseUnite.estOccupe() == null && Math.abs(calculDistanceY) <= joueur.getBase().getVision()
                && Math.abs(calculDistanceX) <= joueur.getBase().getVision() && joueur.acheterUnite(unite)) {
            // On ajoute l'unité à l'armée et ob l'ajoute sur le plateau
            joueur.getArmee().add(unite);
            plateau.get(coordY).get(coordX).setUnite(unite);
            return true;
        }
        return false;
    }

    /**
     * Cette fonction crée une matrice du plateau avec pour chaque élèment du
     * plateau les points de déplacement que coute le terrain
     * 
     * @param matrice Matrice d'entier qui va contenir les informations des points
     *                de déplacements des terrains du plateau
     */
    public static void plateauToMatice(int[][] matrice) {
        boolean petiteLigne = false;
        int totalCells = 248;
        int col = 0, ligne = 0;
        for (int nbCellules = 0; nbCellules < totalCells; nbCellules++) {
            matrice[ligne][col] = plateau.get(ligne).get(col).getTerrain().getPtsDeplacement();
            col++;
            if (col % cote == 0 && !petiteLigne) {
                col = 0;
                ligne++;
                petiteLigne = !petiteLigne;
            } else if (col % (cote - 1) == 0 && petiteLigne) {
                col = 0;
                ligne++;
                petiteLigne = !petiteLigne;
            }
        }

    }

    /**
     * Regarde a des coordonnées données si aucune entité est sur la case du plateau
     * Renvoie true si l'emplacement est vide, false sinon
     * 
     * @param ligneTest   coordonnées Y de l'emplacement a verifier
     * @param colonneTest coordonnées X de l'emplacement a verifier
     * @return Booleen indique si l' emplacement est bien vide
     */
    public static boolean estEmplacementVide(int ligneTest, int colonneTest) {
        if (ligneTest >= 0 && ligneTest < cote && colonneTest >= 0 && colonneTest < cote){
            if (!chercheAttaque && (plateau.get(ligneTest).get(colonneTest).estOccupe() == null))
                return true;
            else if (chercheAttaque )
                return true;
        }
        return false;
    }
    

    /**
     * Cette fonction recherche le chemin le plus court en terme de case plateau
     * Elle renvoie un noeud racine qui est l'arbre de déplacement a effectuer entre la source et la destination
     * C'est un parcours en profondeur pour trouver le chemin 
     * @param mat Matrice d'entiers qui contient les déplacements de chaque terrain du plateau
     * @param srcX Coordonnées en X de la case source
     * @param srcY Coordonnées en Y de la case source
     * @param destX Coordonnées en X de la destination
     * @param destY Coordonnées en Y de la destination
     * @return Renvoie un noeud avec le chemin complet a effectué
     */
    public static Node trouverChemin(int mat[][], int srcX, int srcY, int destX, int destY) {
        //On regarde autour de chaque cellules
        int coordRowColGL [][] = {{-1,0}, {0,-1} , {1,0}, {1,1}, {0,1}, {-1,1}};
        int coordRowColPL [][] = {{-1,-1}, {0,-1} , {1,-1}, {1,0}, {0,1}, {-1,0}};

        if (mat[srcY][srcX] == 0 || mat[destY][destX] == 0)
            return null;

        boolean[][] visited = new boolean[cote][cote];

        visited[srcY][srcX] = true;

        Queue<Node> q = new LinkedList<>();

        Node s = new Node(srcY, srcX, 0, null);
        q.add(s);

        while (!q.isEmpty()) {
            Node curr = q.peek();
            int ptX = curr.getX();
            int ptY = curr.getY();

            if (ptX == destX && ptY == destY) 
                return curr;
            
            q.remove();
            for (int i = 0; i < 6; i++) {
                int row = 0;
                int col = 0;
                if (ptX%2 == 0) {
                    row = ptX + coordRowColPL[i][0];
                    col = ptY + coordRowColPL[i][1];
                }
                else {
                    row = ptX + coordRowColGL[i][0];
                    col = ptY + coordRowColGL[i][1];
                }
                if (estEmplacementVide(row, col) && mat[row][col] != 0 && !visited[row][col]) {
                    visited[row][col] = true;
                    Node Adjcell = new Node(col, row, curr.getDist() + 1, curr);
                    q.add(Adjcell);
                }
            }
        }
        return null;
    }

    /**
     * Cette fonction permet de déplacer une unité selon une liste de coordonnées.
     * L'unité suit le "chemin" de coordonnées et se déplace case par case dans le plateau et sur la carte
     * @param unite Unite qui se déplace sur le plateau
     * @param chemin Chemin a suivre par l'unité
     * 
     */
    private static void faireDeplacement(Unite unite, ArrayList<ArrayList<Integer>> chemin) {
        int coordYSource, coordYDestination;
        int pointDeplacementConsomme;
        Case caseSource,caseDestination; Hexagone hexSource,hexDestination;  Cellule cellSource, cellDestination;
        Deplacement: 
        for (int i = 1; i < chemin.size(); i++) {
            //On récupère les coordonnées de la case source et de la case destination pour les deplacement case par case du chemin
            coordYSource = chemin.get(i - 1).get(0); int coordXSource = chemin.get(i - 1).get(1); 
            coordYDestination = chemin.get(i).get(0); int coordXDestination = chemin.get(i).get(1);           

            //On récupère les case sources et destinations
            caseSource = plateau.get(coordXSource).get(coordYSource);
            caseDestination = plateau.get(coordXDestination).get(coordYDestination);

            //On récupère les hexagones sources et destinations
            hexSource = cellulesCarte[coordXSource][coordYSource].getHex();
            hexDestination  = cellulesCarte[coordXDestination][coordYDestination].getHex();

            pointDeplacementConsomme = caseSource.getTerrain().getPtsDeplacement();

            //Si on a assez de point de deplacement pour sortir de la case
            if (unite.getDeplacementActuel() >= pointDeplacementConsomme) {                

                //On enleve l'unité à la source
                cellSource = new Cellule(hexSource, caseSource);
                cellSource.clear();

                //On met l'unité à la destination
                cellDestination = new Cellule(hexDestination, caseDestination);
                cellDestination.setUnite(unite);

                //On enlève les points de déplacement de l'unité
                unite.setDeplacementActuel(unite.getDeplacementActuel() - pointDeplacementConsomme);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            //Plus de point de deplacement on arrete le deplacement
            else
                break Deplacement;
        }
    }

    /**
     * Cette fonction vérifie les conditions qui indiquent la fin d'une partie
     * Renvoie true si la partie est fini, false sinon
     * @return un booléen qui indique si la partie est fini ou non
     */
    public static boolean estFinPartie() {
        //Si la partie a atteint 90 tour sans gagnant
        if (tour == 90) {
            return true;
        }
        //Regarde si tous les autres joueurs sont hors-jeu
        for (int i = 0; i < listeJoueur.size(); i++) {
            if (listeJoueur.get(i).getNumeroJoueur() != joueurActuel.getNumeroJoueur() && listeJoueur.get(i).getEnJeu() == true) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cette fonction change le statut du joueur actuel s'il ne peut plus jouer
     * Si son armée est vide et qu'il n'a plus assez de pièces pour acheter une troupe ou si sa base est détruite alors on change son état
     */
    public static void estEnJeu() {
        if ((joueurActuel.getArmee().size() == 0 && joueurActuel.getPieces() < 6) || joueurActuel.getBase() == null) {
            joueurActuel.setEnJeu(false);
        }
    }

    /**
     * Cette fonction calcul qui est le gagnant si on atteint le nombre de tour limite ou si il ne reste qu'un joueur sur le champ de bataille
     * Elle change la variable joueurGagnant avec le joueur qui a gagné la partie
     * @return
     */
    public static boolean calculVitoire() {
        int[] value;
        int max = 0, indice = 0;
        //si une condition de fin de partie est respecté
        if (estFinPartie()) {
            //Si la condition de fin de partie est le nombre de tour
            if (tour == 90) {

                //On va enregistrer la valeur de chaque joueur en fonction de son nombre de pièces et de la valeur de son armée
                value = new int[listeJoueur.size()];
                for (int i = 0; i < value.length; i++) {
                    //On calcule la valeur de chaque joueur qui sont encore en jeu
                    if (listeJoueur.get(i).getEnJeu()) {
                        value[i] = joueurActuel.getPieces();
                        for (int j = 0; j < listeJoueur.get(i).getArmee().size(); j++)
                            value[i] = value[i] + listeJoueur.get(i).getArmee().get(j).getCout();
                    } else
                        value[i] = -1;
                }
                //On trouve le joueur qui la plus grosse valeur sur le champ de bataille
                //On ne regarde pas les égalités
                for (int i = 0; i < value.length; i++) {
                    if (max < value[i]) {
                        max = value[i];
                        indice = i;
                    }
                }
                joueurGagnant = listeJoueur.get(indice);
            }
            //Si il reste qu'un seul joueur sur le champ de bataille alors on le donne en joueur gagant  
            else {
                joueurGagnant = joueurActuel;
            }
            FenetreJeu.changerPanel(PanelActuel.VICTOIRE);
            FenetreJeu.getPanelVictoire().getLabelNomVainqueur().setText(joueurGagnant.getPseudo());
            FenetreJeu.getPanelVictoire().getLabelVictoire().setText("Victoire du joueur "+(joueurGagnant.getNumeroJoueur()+1));
            return true;
        }
        return false;
    }


    /**
     * Cette fonction calcule le nombre de monument sur le plateau
     * @return Renvoie un entier qui est le nombre de monument sur le plateau
     */
    public static int calculerNombreMonument() {
        int nbMonument = 0;
        //Parcours du plateau a la recherche des monuments
        for (int i = 0; i < cote; i++) {
            for (int j = 0; j < cote; j++) {
                //On récupère une case du plateau et l'entité sur la case
                Case caseTest = plateau.get(i).get(j);
                Entite entiteCase = (Entite) caseTest.estOccupe();
                //Si l'entité est un monument on incrémente le compteur
                if (entiteCase != null && entiteCase instanceof Batiment
                        && caseTest.getBatiment().getEstBase() == TypeBatiment.MONUMENT) {
                    nbMonument++;
                }
            }
        }
        return nbMonument;
    }

    /**
     * Cette fonction génère des evenemnts aléatoires durant la partie de jeu
     * Ces evenements peuvent être :
     *  La perte d'une unité du joueur actuel
     *  Un gain/perte de piece du joueur actuel
     *  Une regeneration des unités de l'armée du joueur actuel
     *  Une augmentation/diminution de pièce des trésors des monuments
     *  Des changements de types de terrains sur le plateau
     */
    public static void genererEvenementExterieur() {
        //On génère un nombre alétoire qui nous indique si il y a aura un evenement qui va pertuber la partie lors de ce tour
        int evenement = new Random().nextInt(101);
        int uniteMalade, gainPerteGold,gainPointDeVie;
        boolean presenceMonument,gainPerteTresor;
        int[] coordUniteMalade;
        Unite uniteTest; 
        Case caseTest; Hexagone hexagoneTest; Entite entiteTest; Terrain terrainTempete;
        Cellule cellTest;

        //Perte d'une unité
        if (evenement > 95 && evenement < 100) {
            //Si le joueur possède au moins 2 unités dans son armée
            if (joueurActuel.getArmee().size() > 1) {
                //on récupère les coordonées d'une unité du joueur actuel
                uniteMalade = new Random().nextInt(joueurActuel.getArmee().size());
                uniteTest = joueurActuel.getArmee().get(uniteMalade);
                coordUniteMalade = rechercherCoordonneUnite(uniteTest);
                
                //On récupère la case du plateau et l'hexagone pour l'affichage
                caseTest = plateau.get(coordUniteMalade[0]).get(coordUniteMalade[1]);
                hexagoneTest = cellulesCarte[coordUniteMalade[0]][coordUniteMalade[1]].getHex();
                
                //On retire l'unité du plateau
                cellTest = new Cellule(hexagoneTest, caseTest);
                cellTest.clear();

                //On retire l'unité de l'armée
                joueurActuel.getArmee().remove(uniteMalade);

                //On affiche pour les joueurs humains l'action effectuée
                if (!joueurActuel.getEstIa())
                    JOptionPane.showMessageDialog(FenetreJeu,"Une de vos unités est tombé malade, cette dernière est morte de maladie...");
            }
        }
        //GainPerte de piece
        else if (evenement > 85 && evenement < 95) {
            //On génère aléatoirement le gain de piece
            if (evenement%2 == 0) {
                gainPerteGold = 4 + (int) (Math.random() * joueurActuel.getPieces() / 2);
                //On affiche pour les joueurs humains l'action effectuée
                if (!joueurActuel.getEstIa())
                JOptionPane.showMessageDialog(FenetreJeu,
                        "L'Etat a décidé de vous aider dans cette guerre elle vous ajoute " + gainPerteGold
                                + " a votre banque !");
            }
            //On génère aléatoirement la perte de piece
            else {
                gainPerteGold = ((int) (joueurActuel.getPieces() * 0.1))*-1;
                //On affiche pour les joueurs humains l'action effectuée
                if (!joueurActuel.getEstIa())
                JOptionPane.showMessageDialog(FenetreJeu,
                        "Les impots touchent tout le monde, vous n'y échapperez pas ! L'Etat récupère 10% de votre banque...");
            }
            //On modifie le montant de la banque du joueur 
            joueurActuel.setPieces(joueurActuel.getPieces() + gainPerteGold);
            FenetreJeu.getPanelJeu().updateGoldAffichage(joueurActuel.getPieces());
            //On affiche pour les joueurs humains l'action effectué  
        }
        //Regeneration des unités
        else if (evenement > 80 && evenement < 95) {
            //Pour chaque unité de l'armée on régénère un montant de leur point de vie
            for (int i = 0; i < joueurActuel.getArmee().size(); i++) {
                uniteTest = joueurActuel.getArmee().get(i);
                gainPointDeVie = uniteTest.getPointDeVieActuel() + 10;
                if (gainPointDeVie > uniteTest.getDeplacementMax())
                    gainPointDeVie = uniteTest.getPointDeVieMax();
                uniteTest.setPointDeVieActuel(gainPointDeVie);
            }
            //On affiche pour les joueurs humains l'action effectuée
            if (!joueurActuel.getEstIa())
                JOptionPane.showMessageDialog(FenetreJeu,
                        "Un voile de magie eclaire le champ de bataille... Toutes vos unités régénère 10 point de vie");
        }
        //GainPerte tresor monument
        else if (evenement > 70 && evenement < 80) {
            presenceMonument = false;
            if (evenement%2 == 0)
                gainPerteTresor = true;
            else 
                gainPerteTresor = false;
            for (int i = 0; i < cote; i++) {
                for (int j = 0; j < cote; j++) {
                    entiteTest = (Entite) plateau.get(i).get(j).estOccupe();
                    if (entiteTest != null && entiteTest instanceof Batiment && ((Batiment) entiteTest).getEstBase() == TypeBatiment.MONUMENT) {
                        presenceMonument = true;
                        if (gainPerteTresor)
                            ((Batiment) entiteTest) .setTresor((int) (((Batiment) entiteTest).getTresor() * 1.2));
                        else
                            ((Batiment) entiteTest).setTresor((int) (((Batiment) entiteTest).getTresor() * 0.8));
                    }
                }
            }
            //On affiche pour les joueurs humains l'action effectuée
            if (presenceMonument && !joueurActuel.getEstIa() && gainPerteTresor)
                JOptionPane.showMessageDialog(FenetreJeu,
                        "D'après quelques sources, les monuments présents sur le champ de batille possèderait plus de pièces...");
            else if (presenceMonument && !joueurActuel.getEstIa() && !gainPerteTresor)
                JOptionPane.showMessageDialog(FenetreJeu,
                        "Des pillards sont arrivés avant nous sur le champ de bataille et ils ont volé une partie des trésor des monuments");

        }
        //Catastrophe météorologique
        else if (evenement > 55 && evenement < 70) {     
            terrainTempete = null;  
            if (evenement%3 == 0) {
                //On affiche pour les joueurs humains l'action effectuée
                if (!joueurActuel.getEstIa())
                    JOptionPane.showMessageDialog(FenetreJeu, "Une tempete de sable est passé sur le champ de bataille !");
                terrainTempete = new Desert();
            }
                
            else if (evenement%3 == 1) {
                //On affiche pour les joueurs humains l'action effectuée
                if (!joueurActuel.getEstIa())
                    JOptionPane.showMessageDialog(FenetreJeu, "Une tempete de neige est passé sur le champ de bataille !");
                terrainTempete = new ToundraNeige();
            }

            else if (evenement%3 == 2) {
                //On affiche pour les joueurs humains l'action effectuée
                if (!joueurActuel.getEstIa())
                    JOptionPane.showMessageDialog(FenetreJeu, "Un tsunami est passé sur le champ de bataille !");
                
                terrainTempete = new Mer();
            }

            //On parcours le champ de bataille
            for (int i = 0; i < cote; i++) {
                for (int j = 0; j < cote; j++) {
                    if ((i % 2 == 1 && j < cote - 1) || i % 2 == 0) {
                        //On modifie aléatoirement les terrains
                        if (new Random().nextInt(4) == 2) {
                            //On récupere la cellule du plateau a modifier
                            caseTest = plateau.get(i).get(j);
                            hexagoneTest =  cellulesCarte[i][j].getHex();
                            cellTest = new Cellule(hexagoneTest, caseTest);

                            //On applique le nouveau type de terrain
                            cellTest.setTerrain(terrainTempete);
                        }
                    }
                }
            }
        }
    }

    /**
     * Cette fonction initialise un nouveau tour
     * Elle vérifie si la partie est fini ou non
     * Si la partie n'est pa fini elle change de joueur
     */
    public static void initNouveauTour(){
        //Si la partie n'est pas fini
        if (listeJoueur.size() > 0 && tour!=0) {
            do {
                joueurActuel = listeJoueur.get((joueurActuel.getNumeroJoueur() + 1) % (nbJoueursH + nbJoueursIA));
            } while (joueurActuel.getEnJeu() == false);
        }

        //Si aucune condition de victoire n'est respecté
        if (!calculVitoire()) {

            //nouveau temps au timer
            reinitialiserChrono();

            //Affichage pour une partie full IA
            if (tour == 0 && nbJoueursH == 0) {
                JOptionPane.showMessageDialog(FenetreJeu,
                        "Les IA vont s'affronter, vous ne pourrez pas prendre la main tant que les deux ia sont en partie");
            }

            //Regeneration des unite et gain tour
            if (tour != 0) {
                joueurActuel.regenererUniteArmee();
                joueurActuel.genererGainTour(tour);
            }
            tour++;

            //Affichage du nouveau tour
            FenetreJeu.getPanelJeu().getLabelNomJoueur().setText("Tour de : " + joueurActuel.getPseudo());
            FenetreJeu.getPanelJeu().getLabelNbTours().setText("Nombre de tours : " + tour + "/90");
            FenetreJeu.getPanelJeu().updateGoldAffichage(joueurActuel.getPieces());
            if (!joueurActuel.getEstIa())
                JOptionPane.showMessageDialog(FenetreJeu,"Tour n° " + tour + " Joueur : " + joueurActuel.getPseudo());
              
            //Evenement exterieur 
            genererEvenementExterieur();

            //Si c'est une IA qui doit jouer on passe automatique au tour suivant a la fin de son tour
            if (joueurActuel.getEstIa()) {
                faireTourIA();
                try {
                    Thread.sleep(667);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initNouveauTour();
            }
            
        } 
        //Si la partie est fini pas de nouveau tour et on stop le chrono
        else {
            FenetreJeu.getPanelJeu().getTimerHorloge().stop();
            FenetreJeu.getPanelJeu().getTimerTour().stop();
        }

    }

    /**
     * Cette fonction reinitialiser le chronometre
     */
    public static void reinitialiserChrono() {
        //Si la partie est lancée
        if (initPanelJeu) {
            FenetreJeu.getPanelJeu().setSeconde(0);
            FenetreJeu.getPanelJeu().setMinute(2);
            FenetreJeu.getPanelJeu().getTimerTour().restart();
            FenetreJeu.getPanelJeu().getTimerHorloge().restart();
        }
    }

    /**
     * Cette fonction efface toutes les données de la partie
     */
    public static void effacerDonnees() {
        int nbJoueurs = listeJoueur.size();       
        
        for (int i = nbJoueurs - 1; i > 0; i--)
            listeJoueur.remove(i);
        listeJoueur = new ArrayList<Joueur>();
        for (int i = nbJoueurs - 1; i > 0; i--)
            postionBaseJoueur.remove(i);
        postionBaseJoueur = new ArrayList<>();
        

        plateau.removeAll(plateau);
        plateau = new Plateau();

        nbJoueursH = 0; nbJoueursIA = 0;
        Joueur.setCompteur(0);
        Entite.setCompteur(0);
        FenetreJeu.getPanelNouvellePartie().getNbJoueursHumain().setSelectedIndex(0);
        FenetreJeu.getPanelNouvellePartie().getNbJoueursIA().setSelectedIndex(0);
        terrainChoisi = TypeTerrain.NEIGE;
        reinitialiserChrono();
        
    }

    /**
     * Pour un joueur donné en parametre, cette fonction supprimer toutes les unités de l'armée
     * Cette fonction est utilisé lorsqu'un joueur perd sa base
     * @param joueur Joueur qui perd son armée
    */
    public static void supprimerArmee(Joueur joueur){
        Unite uniteJoueur = null;
        int [] coordUniteJoueur = null;
        for (int i = 0; i < joueur.getArmee().size(); i++) {
            uniteJoueur = joueur.getArmee().get(i);
            coordUniteJoueur = rechercherCoordonneUnite(uniteJoueur);
            supprimerEntiteCellule(coordUniteJoueur);
        }
        joueur.supprimerArmee();
    }

    /**
     * Supprime une entite du champs de bataille
     * @param coordEntite Coordonnées de l'unité qui est supprimer
     */
    public static void supprimerEntiteCellule(int [] coordEntite){
        Cellule cell = cellulesCarte[coordEntite[0]][coordEntite[1]];
        cell.clear();
    }

    /**
     * Supprime la base d'un joueur 
     * @param joueur Joueur qui voit sa base supprimer
     */
    public static void supprimerBase(Joueur joueur){
        joueur.setBase(null);
        Cellule cell = cellulesCarte[postionBaseJoueur.get(joueur.getNumeroJoueur()).get(0)][postionBaseJoueur.get(joueur.getNumeroJoueur()).get(1)];
        cell.clear();
    }

    //
    // FIN DONNEES
    //

    //
    // PARTIE IA
    //

    /**
     * Cette fonction permet à l'IA d'acheter des troupes.
     * Elle prend en parametre un budget de dépense et achete des troupes en fonction de ce budget
     * La fonction renvoie l'unité acheté
     * @param depense un entier qui stocke le budget depense du tour
     * @return l'unité qui a été acheté et placé sur le champ de bataille
     */
    public static Unite acheterTroupesIA(int depense){
        Unite troupeAchete = null;
        if (depense >= new InfanterieLourde().getCout()) {
            troupeAchete = new InfanterieLourde();
        } else if (depense >= new Mage().getCout()) {
            troupeAchete = new Mage();
        } else if (depense >= new Cavalerie().getCout()) {
            troupeAchete = new Cavalerie();
        } else if (depense >= new Infanterie().getCout()) {
            troupeAchete = new Infanterie();
        } else if (depense >= new Archer().getCout()) {
            troupeAchete = new Archer();
        }
        if (placerUnite(troupeAchete))
            joueurActuel.acheterUnite(troupeAchete);
        return troupeAchete;
    }
 
    /**
     * Essaye de placer une unité donnée en parametre sur le champ de bataille
     * La fonction regarde si il y a une place de libre autour de la base
     * @param unite unité qui veut etre placé sur le champ de bataille
     * @return booleen Renvoie True si le placement de l'unité a bien été effectué, False sinon
     */
    public static boolean placerUnite(Unite unite) {
        //On regarde toutes les coordonnées possibles autour des bases des joueurs
        int[][][] coordPossible = { { { 0, 1 }, { 0, 2 }, { 1, 0 }, { 2, 0 }, { 2, 1 } },
                { { 13, 14 }, { 14, 14 }, { 14, 15 }, { 15, 12 }, { 15, 13 } },
                { { 0, 14 }, { 1, 14 }, { 2, 15 }, { 0, 13 }, { 1, 13 } },
                { { 12, 0 }, { 13, 0 }, { 14, 0 }, { 14, 1 }, { 15, 1 } } };
        
        int[] coordTest;
        Hexagone hexagoneTest;
        for (int i = 0; i < 4; i++) {
            coordTest = coordPossible[joueurActuel.getNumeroJoueur()][i];
            hexagoneTest = cellulesCarte[coordTest[0]][coordTest[1]].getHex();
            //Si il y a de la place et que l'unité a bien été posé sur le champs de bataille alors je l'affiche
            if (placerUniteJoueur(joueurActuel, unite, coordTest[0], coordTest[1])) {
                hexagoneTest.setUnite(Outils.uniteModelToVue(unite));
                return true;
            }
        }
        return false;
    }

    /**
     * Cette fonction renvoie les des coordonnées autour de celles qui est donné en parametre pour le deplacement de l'ia
     * @param coord 
     * @return
     */
    public static int[] estDeplacementPossible(int[] coord) {
        int coordRowColGL [][] = {{-1,0}, {0,-1} , {1,0}, {1,1}, {0,1}, {-1,1}};
        int coordRowColPL [][] = {{-1,-1}, {0,-1} , {1,-1}, {1,0}, {0,1}, {-1,0}};

        int row, col;
        for (int i = 0; i < 6; i++) {
            if (coord[0]%2 == 0) {
                row = coord[0] + coordRowColPL[i][0];
                col = coord[1]+ coordRowColPL[i][1];
            }
            else {
                row = coord[0] + coordRowColGL[i][0];
                col = coord[1] + coordRowColGL[i][1];
            }
            if (estEmplacementVide(row, col)) {
                int[] coordFinal = { row, col };
                return coordFinal;
            }
        }
        return coord;
    }


    /**
     * Cette fonction prend en paramètre une unité et effectute un deplacement vers l'unité la plus proche
     * Si l'unité est assez proche de sa cible, elle attaque
     * @param unite
     */
    public static void faireActionUniteIA(Unite unite) {
        // recherche Entite plus proche et deplacement vers elle/attaquer
        int[] coordUnite = rechercherCoordonneUnite(unite); 
        int[] coordDeplacement = new int[2]; int[] coordCible = new int[2]; 
        int[][] matricePlateau = new int[cote][cote];
        ArrayList<ArrayList<Integer>> cheminComplet = new ArrayList<>();
        Entite cible = rechercherEntiteProche(coordUnite);
        Hexagone hexAttaquant, hexDefenseur;

        Node chemin;

        //Si l'unité de l'IA a trouvé une cible a attaqué
        if (cible != null) {
            //Elle recupere les coordonées de la cible et se déplace vers elle
            coordCible = rechercherCoordonneUnite(cible);
            coordDeplacement = estDeplacementPossible(coordCible);            
            
            plateauToMatice(matricePlateau);
            chemin = trouverChemin(matricePlateau, coordUnite[0], coordUnite[1], coordDeplacement[0], coordDeplacement[1]);
            Node.nodeToArray(cheminComplet, chemin);
            faireDeplacement(unite, cheminComplet);

        }

        

        
        coordUnite = rechercherCoordonneUnite(unite);
        //Si l'unité est arrivé jusqu'a sa cible
        if (coordUnite[0] == coordDeplacement[0] && coordUnite[1] == coordDeplacement[1]) {
            //L'unité attaque la cible
            hexAttaquant = cellulesCarte[coordUnite[0]][coordUnite[1]].getHex();
            hexDefenseur = cellulesCarte[coordCible[0]][coordCible[1]].getHex();
            combattre(hexAttaquant,  hexDefenseur, 0);
        }
    }

    /**
     * Cette fonction recherche les coordonnées dans le plateau d'une unité donnée en paramètre
     * @param entite entite que l'on recherche sur le plateau
     * @return tableau d'entier qui correspond aux coordonnées de l'unité
     */
    public static int[] rechercherCoordonneUnite(Entite entite) {
        int[] coordUnite = new int[2];
        Entite entiteTest = null;

        for (int i = 0; i < cote; i++) {
            for (int j = 0; j < cote; j++) {
                entiteTest = (Entite) plateau.get(i).get(j).estOccupe();
                //Si l'entite du plateau a le meme identifiant que l'entite donnée en parametre
                if (entiteTest != null && entite.getIdentifiant() == entiteTest.getIdentifiant()) {
                    //On garde les coordonées
                    coordUnite[0] = i; coordUnite[1] = j;
                }
            }
        }
        return coordUnite;
    }

    /**
     * Cette fontion renvoie l'entite la plus proche des coordonées données en paramètre
     * @param coordUnite Les coordonées sources de la recherche
     * @return L'entite la plus proche des coordonées en paramètre
     */
    public static Entite rechercherEntiteProche(int[] coordUnite) {
        int minValue = 99;
        int[][] matriceEmplacement = new int[cote][cote];
        int[][] matricePlateau = new int[cote][cote];
        int[] coordRechercheEntiteProche = new int[2];
        int[] coordFinal = new int[2];
        Case caseTest;
        Entite entiteAAttaquer = new Entite();

        plateauToMatice(matricePlateau);

        for (int i = 0; i < cote; i++) {
            for (int j = 0; j < cote; j++) {
                caseTest = plateau.get(i).get(j);
                matriceEmplacement[i][j] = 99;
                if (plateau.get(i).get(j).estOccupe() != null) {
                    coordRechercheEntiteProche[0] =  i; coordRechercheEntiteProche[1] =  j;
                    if (!joueurActuel.estMonEntite(caseTest) && coordRechercheEntiteProche != estDeplacementPossible(coordRechercheEntiteProche)) {
                        coordRechercheEntiteProche = estDeplacementPossible(coordRechercheEntiteProche);
                        Node chemin = trouverChemin(matricePlateau, coordUnite[0], coordUnite[1], coordRechercheEntiteProche[0], coordRechercheEntiteProche[1]);
                        if (chemin != null)
                            matriceEmplacement[i][j] = chemin.getDist();
                        
                    }
                }
                if (matriceEmplacement[i][j] < minValue) {
                    minValue = matriceEmplacement[i][j];
                    coordFinal[0] = i;
                    coordFinal[1] = j;
                }
            }
        }

        caseTest = plateau.get(coordFinal[0]).get(coordFinal[1]);
        entiteAAttaquer = (Entite) caseTest.estOccupe();

        if (entiteAAttaquer instanceof Batiment)
            entiteAAttaquer = caseTest.getBatiment();

        else if (entiteAAttaquer instanceof Unite)
            entiteAAttaquer = caseTest.getUnite();
        return entiteAAttaquer;
    }

    /**
     * Cette fonction est utilisé par l'IA et permet de faire pour chaque tour de l'IA
     * des achats d'unité et de générer les actions des IA
     */
    public static void faireTourIA() {
        int depense = 4 + (int) (Math.random() * joueurActuel.getPieces());
        Unite uniteachete;
        //ACHAT
        while (depense >= 5) {
            uniteachete = acheterTroupesIA(depense);
            if (uniteachete != null) {
                depense -= uniteachete.getCout();
                FenetreJeu.getPanelJeu().updateGoldAffichage(joueurActuel.getPieces());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }        
        //ACTION UNITE
        for (int i = 0; i < joueurActuel.getArmee().size(); i++) {
            if (!calculVitoire())
                faireActionUniteIA(joueurActuel.getArmee().get(i));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //
    // FIN PARTIE IA
    //

    //
    // AFFICHAGE
    //

    /**
     * Ceffe fonction crée une matrice de cellules en fonction du plateau
     */
    public static void setCellulesMap() {
        TypeTerrain sol;
        TypeUnite unite;
        TypeBatimentVue batiment;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                sol = null;
                unite = null;
                batiment = null;

                sol = Outils.terrainModeleToVue(plateau.get(i).get(j).getTerrain());
                unite = Outils.uniteModelToVue(plateau.get(i).get(j).getUnite());
                if (plateau.get(i).get(j).getBatiment() != null)
                    batiment = Outils.batimentModeleToVue(plateau.get(i).get(j).getBatiment().getEstBase());

                Cellule cell = new Cellule(new Hexagone(sol, unite, batiment, new Point(i, j)), plateau.get(i).get(j));
                cellulesCarte[i][j] = cell;
            }
        }
    }

    /**
     * Renvoie la matrice de cellules en matrice d'hexagone
     * @return
     */
    public static Hexagone[][] cellulesToHexagones(){
        Hexagone[][] hexs = new Hexagone[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                hexs[i][j] = cellulesCarte[i][j].getHex();
            }
        }
        return hexs;
    }

    /**
     * Renvoie une case en fonction de coordonnées données en paramètres
     * @param coordY
     * @param coordX
     * @return
     */
    public static Case coordToCase(int coordY, int coordX) {
        return plateau.get(coordY).get(coordX);
    }

    //
    // GESTION SAUVEGARDE CHARGER
    //    

    /**
     * Cette fonction permet de sauvergarder une partie
     */
    public static void sauvegarderPartie() {
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        FileWriter fw  = null;
        String nomFichier, chaine = new String();
        nomFichier = formatter.format(date).replace("_", "");
        nomFichier = nomFichier.replace(":", "_");
        nomFichier = nomFichier.replace("/", "_");
        File file = new File("src" + File.separator + "data" + File.separator + "partie" + File.separator + nomFichier + ".txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile());
        }
        catch (Exception e) {
            System.out.println("Erreur fichier");
        }

        for (int i = 0; i < listeJoueur.size(); i++) {
            chaine += "[";
            chaine += listeJoueur.get(i).getPseudo() + "," + listeJoueur.get(i).getEstIa() + ","
                    + listeJoueur.get(i).getPieces() + ",{";
            for (int j = 0; j < listeJoueur.get(i).getArmee().size(); j++) {
                chaine += listeJoueur.get(i).getArmee().get(j).getIdentifiant();
                if (j < listeJoueur.get(i).getArmee().size() - 1) {
                    chaine += ".";
                }
            }
            chaine += "}";
            if (i < listeJoueur.size() - 1) {
                chaine += "];";
            }
        }

        chaine += "]\n";
        chaine = sauvegarderStringMap(chaine);
        chaine += tour + "\n";
        chaine += joueurActuel.getNumeroJoueur() + "\n";
        for (int i = 0; i < postionBaseJoueur.size(); i++) {
            chaine += "[";
            chaine += postionBaseJoueur.get(i).get(0) + "," + postionBaseJoueur.get(i).get(1) + ","
                    + plateau.get(postionBaseJoueur.get(i).get(0)).get(postionBaseJoueur.get(i).get(1))
                            .getBatiment().getPointDeVieActuel();
            if (i < postionBaseJoueur.size() - 1) {
                chaine += "];";
            }
        }
        chaine += "]\n";
        chaine += finpartie + "\n";
        chaine += Entite.getCompteur();

        try {
            fw.write(chaine);
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Cette fonction permet de sauvegarder la carte dans un fichier
     * @param fichier
     */
    public static void sauvegarderMap(String fichier) {
        FileWriter fw = null;
        File file = null;
        String chaine = new String();

        try {
            file = new File("src" + File.separator + "data" + File.separator + "cartes" + File.separator + fichier + ".txt");

            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile());
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        chaine = new String();
        chaine = sauvegarderStringMap(chaine);
        try {
            fw.write(chaine);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }


    /**
     * Cette fonction permet de générer la sauvegarde de la carte
     * @param chaine
     * @return
     */
    public static String sauvegarderStringMap(String chaine) {

        Case caseTest;

        Terrain terrain; Unite unite; Batiment batiment;

        String[] splStrings;
        String chaineTerrain;
        String chaineUnite;
        String chaineBatiment;


        for (int i = 0; i < cote; i++) {
            for (int j = 0; j < cote; j++) {
                /// Ajout du premier [ pour chaque case
                chaine += "[";

                /// Initialisation des variables utiles
                caseTest =  plateau.get(i).get(j);

                terrain = caseTest.getTerrain();
                unite = caseTest.getUnite();
                batiment = caseTest.getBatiment();
                chaineTerrain = new String(); chaineUnite = new String();chaineBatiment = new String();

                /// Recuperation du type de Terrain
                chaineTerrain = terrain.toString();
                splStrings = chaineTerrain.split(":");
                chaineTerrain = splStrings[0];
                splStrings = chaineTerrain.split(" ");
                chaine += splStrings[0] + ":";

                /// Unite
                if (unite != null) {
                    chaineUnite = unite.toString() + "," + unite.getIdentifiant() + "," + unite.getPointDeVieActuel()
                            + "," + unite.getDeplacementActuel() + "," + unite.getAAttaque() + "," + unite.getEnRepos();
                }
                chaine += chaineUnite + ":";

                /// Batiment
                if (batiment != null && (batiment.getEstBase() != TypeBatiment.BASE)) {
                    chaineBatiment = batiment.toString() + "," + batiment.getIdentifiant() + ","
                            + batiment.getPointDeVieActuel();
                }
                chaine += chaineBatiment;

                /// Ajout de fin
                chaine += "]";
                if (j < cote - 1) {
                    chaine += ";";
                }
            }
            chaine += "\n";
        }
        return chaine;
    }

    /**
     * Cette fonction permet de charger la carte selectionnee
     * @param file
     */
    public void chargerCarte(FileInputStream file) {
        String line = new String();
        Scanner scanner = new Scanner(file);
        int[][] listeUnite = new int[50][50];
        for (int i = 0; i < cote; i++) {
            line = scanner.nextLine();
            chargerLineMap(line, i, listeUnite);
        }
        scanner.close();
    }

    /**
     * Cette fonction permet de charger une partie selectionne
     * @param file
     */
    public static void chargerPartie(FileInputStream file) {
        String line = new String();
        String[] strValues1;
        String[] strValues2;
        String[] strValues3;
        Scanner scanner = new Scanner(file);
        int[][] listeUnite = new int[4][10];
        Joueur joueur;

        line = scanner.nextLine();
        strValues1 = line.split(";");

        for (int i = 0; i < strValues1.length; i++) {
            strValues1[i] = strValues1[i].replace("[", "");
            strValues1[i] = strValues1[i].replace("]", "");
            strValues2 = strValues1[i].split(",");
            joueur = new Joueur(strValues2[0], Boolean.parseBoolean(strValues2[1]));
            if (joueur.isEstIa())
                nbJoueursIA++;
            else
                nbJoueursH++;
            listeJoueur.add(joueur);
            joueur.setPieces(Integer.parseInt(strValues2[2]));

            strValues2[3] = strValues2[3].replace("{", "");
            strValues2[3] = strValues2[3].replace("}", "");
            if (strValues2[3].length() > 0) {
                strValues3 = strValues2[3].split("\\.");
                for (int j = 0; j < strValues3.length; j++) {
                    listeUnite[i][j] = Integer.parseInt(strValues3[j]);
                }
            } else {
                listeUnite[i][0] = -1;
            }
        }

        for (int i = 0; i < cote; i++) {
            line = scanner.nextLine();
            strValues1 = line.split(",");
            chargerLineMap(line, i, listeUnite);
        }

        line = scanner.nextLine();
        tour = Integer.parseInt(line);
        line = scanner.nextLine();
        joueurActuel = listeJoueur.get(Integer.parseInt(line));

        line = scanner.nextLine();
        strValues1 = line.split(";");

        for (int i = 0; i < strValues1.length; i++) {
            strValues1[i] = strValues1[i].replace("[", "");
            strValues1[i] = strValues1[i].replace("]", "");
            strValues2 = strValues1[i].split(",");
            placerBase(listeJoueur.get(i), Integer.parseInt(strValues2[0]), Integer.parseInt(strValues2[1]));

        }

        line = scanner.nextLine();
        finpartie = Boolean.parseBoolean(line);
        line = scanner.nextLine();
        Entite.setCompteur(Integer.parseInt(line));

        scanner.close();
    }

    
    /**
     * Cette fonction permet de charger une ligne de la carte
     * @param line
     * @param iline
     * @param listeUnite
     */
    public static void chargerLineMap(String line, int iline, int[][] listeUnite) {

        String[] spl1;
        String[] spl2;

        spl1 = line.split(";");

        for (int i = 0; i < spl1.length; i++) {
            spl1[i] = spl1[i].replace("[", "");
            spl1[i] = spl1[i].replace("]", "");
            spl2 = spl1[i].split(":");
            switch (spl2[0]) {
                case "Plaine":
                    plateau.get(iline).get(i).setTerrain(new Plaine());
                    break;
                case "Desert":
                    plateau.get(iline).get(i).setTerrain(new Desert());
                    break;
                case "Foret":
                    plateau.get(iline).get(i).setTerrain(new Foret());
                    break;
                case "Mer":
                    plateau.get(iline).get(i).setTerrain(new Mer());
                    break;
                case "Montagne":
                    plateau.get(iline).get(i).setTerrain(new Montagne());
                    break;
                case "ToundraNeige":
                    plateau.get(iline).get(i).setTerrain(new ToundraNeige());
                    break;
                default:
                    plateau.get(iline).get(i).setTerrain(new Plaine());
                    break;
            }
            if (spl2.length == 2) {
                plateau.get(iline).get(i).setUnite(analyseSplUnite(spl2[1], listeUnite));
            } else if (spl2.length == 3) {
                plateau.get(iline).get(i).setBatiment(analyserSplBatiment(spl2[2]));
            }

        }
    }

    /**
     * Cette fonction analyse une chaine donnée en parametre pour créer un batiment
     * @param spl
     * @return
     */
    public static Batiment analyserSplBatiment(String spl) {
        String[] spl1 = new String[25];
        Batiment bat;
        spl1 = spl.split(",");
        if (spl1.length == 3) {
            bat = new Batiment(TypeBatiment.MONUMENT);
            bat.setIndentifiant(Integer.parseInt(spl1[1]));
            bat.setPointDeVieActuel(Integer.parseInt(spl1[2]));
            return bat;
        }
        return null;
    }

    /**
     * Cette fonction analyse une chaine pour créer une unité
     * @param spl
     * @param listeUnite
     * @return
     */
    public static Unite analyseSplUnite(String spl, int[][] listeUnite) {
        String[] spl1 = new String[25];
        Unite unite;
        if (spl.length() > 2) {
            spl1 = spl.split(",");
            unite = new Unite(0, 0, 0, 0);
            switch (spl1[0]) {
                case "Archer":
                    unite = new Archer();
                    break;

                case "Cavalerie":
                    unite = new Cavalerie();
                    break;

                case "Infanterie":
                    unite = new Infanterie();
                    break;

                case "InfanterirLourde":
                    unite = new InfanterieLourde();
                    break;

                case "Mage":
                    unite = new Mage();
                    break;

                default:
                    break;
            }
            unite.setIndentifiant(Integer.parseInt(spl1[1]));
            unite.setPointDeVieActuel(Integer.parseInt(spl1[2]));
            unite.setDeplacementActuel(Integer.parseInt(spl1[3]));
            unite.setAAttaque(Boolean.parseBoolean(spl1[4]));
            unite.setEnRepos(Boolean.parseBoolean(spl1[5]));
            for (int i = 0; i < listeUnite.length; i++) {
                for (int j = 0; j < listeUnite[i].length; j++) {
                    if (unite.getIdentifiant() == listeUnite[i][j]) {
                        listeJoueur.get(i).getArmee().add(unite);
                    }
                }
            }
            return unite;
        }
        return null;
    }

    //
    // FIN SAUVEGARDE CHARGER PARTIE
    //

    //
    // MOUSE CLICKED + ACTION PERFORMED
    //

    @Override
    public void mouseClicked(MouseEvent e) {
        Hexagone hexClic;
        Case caseClic;
        Cellule cellClic;
        int[][] matricePlateau = new int[cote][cote];
        Node chemin;
        Entite entiteInfo;
        Terrain terrainInfo;

        if (e.getSource() instanceof Hexagone) {
            hexClic = (Hexagone) e.getSource();
            caseClic = cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase();
            cellClic = new Cellule(hexClic,caseClic);

            switch (FenetreJeu.getPanelActuel()) {
                //Si nous somme dans le panel de changer scenario
                case CHANGERSCENARIO:
                    //On selectione un Terrain pour le placer
                    if (terrainChoisi != null && !selectionMonument)
                        cellClic.setTerrain(Outils.terrainVueToModele(terrainChoisi));
                    
                    //On selectionne le bouton pour placer un monument
                    else if (selectionMonument) {
                        if (caseClic.getBatiment() != null) {
                            switch (Outils.batimentModeleToVue(caseClic.getBatiment().getEstBase())) {
                                case MONUMENT:                                    
                                    panelChangerScenario.estAjouterMonument(false);
                                    cellClic.setBatiment(null);
                                    hexClic.setTerrain(Outils.terrainModeleToVue(caseClic.getTerrain()));
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(FenetreJeu, "Il y a déjà une base placé ici.");
                                    break;
                            }
                        } else if (panelChangerScenario.getNbMonumentsRestants() == 0)
                            JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez plus placer de monuments.");
                        else if (caseClic.getUnite() != null)
                            JOptionPane.showMessageDialog(FenetreJeu, "Il y a déjà une unité placé ici.");
                        else {
                            panelChangerScenario.estAjouterMonument(true);
                            cellClic.setBatiment(new Batiment(TypeBatiment.MONUMENT));
                        }
                    }
                    break;

                //Si nous sommes dans le Panel Jeu
                case JEU:

                    //ACHAT DE TROUPES
                    if (uniteAchete != null) {
                        switch (uniteAchete) {
                            case ARCHER:
                                Archer archer = new Archer();
                                if (placerUniteJoueur(joueurActuel, archer, hexClic.getCoord().getX(),hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            case CAVALERIE:
                                Cavalerie cavalerie = new Cavalerie();
                                if (placerUniteJoueur(joueurActuel, cavalerie, hexClic.getCoord().getX(), hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            case INFANTERIE:
                                Infanterie infanterie = new Infanterie();
                                if (placerUniteJoueur(joueurActuel, infanterie, hexClic.getCoord().getX(), hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            case INFANTERIELOURDE:
                                InfanterieLourde infanterieLourde = new InfanterieLourde();
                                if (placerUniteJoueur(joueurActuel, infanterieLourde, hexClic.getCoord().getX(), hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            case MAGE:
                                Mage mage = new Mage();
                                if (placerUniteJoueur(joueurActuel, mage, hexClic.getCoord().getX(), hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            default:
                                break;
                        }
                        uniteAchete = null;
                        caseClic1 = null;
                        FenetreJeu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    } 
                    //DEPLACEMENT DES TROUPES ET ATTAQUE
                    else {
                        if (caseClic1 == null && caseClic2 == null){
                            caseClic1 = cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase();
                            hexCaseClic = hexClic;
                            if (caseClic1.estOccupe() == null || !joueurActuel.estMonEntite(caseClic1)) {
                                caseClic1 = null;
                                FenetreJeu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            } else
                                FenetreJeu.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else if (caseClic1 != null && caseClic2 == null) {
                            caseClic2 = cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase();
                            if (caseClic2.estOccupe() == null && caseClic1.estOccupe() instanceof Unite) {
                                if (((Unite) caseClic1.estOccupe()).getDeplacementActuel() >= caseClic1.getTerrain().getBonusDefense()) {
                                    JOptionPane.showMessageDialog(FenetreJeu, "Deplacement lancé.");
                                    plateauToMatice(matricePlateau);
                                    chemin = trouverChemin(matricePlateau, hexCaseClic.getCoord().getX(),
                                            hexCaseClic.getCoord().getY(), hexClic.getCoord().getX(),
                                            hexClic.getCoord().getY());
                                    ArrayList<ArrayList<Integer>> cheminComplet = new ArrayList<>();
                                    Node.nodeToArray(cheminComplet, chemin);
                                    faireDeplacement((Unite) caseClic1.estOccupe(), cheminComplet);
                                    JOptionPane.showMessageDialog(FenetreJeu, "Deplacement fini.");
                                } else
                                    JOptionPane.showMessageDialog(FenetreJeu,
                                            "Unité n'a plus assez de point déplacement.");
                                FenetreJeu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                            else if (caseClic2.estOccupe() != null && !joueurActuel.estMonEntite(caseClic2)) {
                                plateauToMatice(matricePlateau);
                                chercheAttaque = true;
                                chemin = trouverChemin(matricePlateau, hexCaseClic.getCoord().getX(),
                                        hexCaseClic.getCoord().getY(), hexClic.getCoord().getX(),
                                        hexClic.getCoord().getY());
                                chercheAttaque = false;
                                if (combattre(hexCaseClic, hexClic, chemin.getDist())) {
                                    if (joueurGagnant == null)
                                        JOptionPane.showMessageDialog(FenetreJeu, "Attaque ! ");
                                }
                                if (chemin.getDist() > 2)
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous êtes trop loin pour attaquer...");
                                FenetreJeu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                
                            }
                            caseClic1 = null; caseClic2 = null;
                        }
                    }
                    caseClic = cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase();
                    terrainInfo = caseClic.getTerrain();

                    FenetreJeu.getPanelJeu().getLabelTypeTerrain()
                            .setText(terrainInfo.afficherTypeTerrain());
                    FenetreJeu.getPanelJeu().getLabelPointDeplacementTerrain()
                            .setText(terrainInfo.afficherPointDeplacement());
                    FenetreJeu.getPanelJeu().getLabelBonusTerrain()
                            .setText(terrainInfo.afficherBonus());

                    entiteInfo = (Entite) caseClic.estOccupe();
                    
                    if (entiteInfo!= null) {
                        if (entiteInfo instanceof Unite) {
                            FenetreJeu.getPanelJeu().getLabelNomEntite().setText(((Unite) entiteInfo).afficherNomUnite());
                            FenetreJeu.getPanelJeu().getLabelPointDeplacement().setText("Points de deplacement : " + ((Unite) entiteInfo).getDeplacementActuel());
                            FenetreJeu.getPanelJeu().getLabelAttaque().setText("Attaque : " + ((Entite) entiteInfo).getAttaque());
                        } 
                        else {
                            FenetreJeu.getPanelJeu().getLabelNomEntite().setText(entiteInfo.toString());
                        }
                        FenetreJeu.getPanelJeu().getLabelPointVie().setText("Point de vie : " + entiteInfo.getPointDeVieActuel());
                        
                        FenetreJeu.getPanelJeu().getLabelDefense().setText("Defense : " + entiteInfo.getDefense());

                    } else {
                        FenetreJeu.getPanelJeu().getLabelNomEntite().setText("");
                        FenetreJeu.getPanelJeu().getLabelPointVie().setText("");
                        FenetreJeu.getPanelJeu().getLabelAttaque().setText("");
                        FenetreJeu.getPanelJeu().getLabelDefense().setText("");
                        FenetreJeu.getPanelJeu().getLabelPointDeplacement().setText("");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
    * 
    */
    @Override
    public void actionPerformed(ActionEvent evt) {
        /*
         * Clic sur un bouton
         */
        if (evt.getSource() instanceof JButton) {

            switch (evt.getActionCommand()) {
                //
                // FENETRE MENU PRINCIPALE
                //
                /*
                 * Bouton "Nouvelle Partie"
                 */
                case "nouvellePartie":
                    FenetreJeu.changerPanel(PanelActuel.NOUVELLEPARTIE);
                    ArrayList<String> listNomMap = new ArrayList<String>();
                    FenetreJeu.getPanelNouvellePartie().initListeCartes(listNomMap);
                    FenetreJeu.getPanelNouvellePartie().setChoixMap(listNomMap);
                    break;
                /*
                 * Bouton "Charger Partie"
                 */
                case "chargerPartie":
                    FenetreJeu.changerPanel(PanelActuel.CHARGERPARTIE);
                    break;
                /*
                 * Bouton "Règles"
                 */
                case "afficherRegles":
                    FenetreJeu.changerPanel(PanelActuel.REGLES);
                    break;
                /*
                 * Bouton "Quitter"
                 */
                case "quit":
                    int reponse = JOptionPane.showConfirmDialog(FenetreJeu, "Voulez-vous quitter l'application ?",
                            "Êtes-vous sur ?", 0, 0);
                    if (reponse == 0) {
                        FenetreJeu.dispose();
                        System.exit(0);
                    }
                    break;
                //
                // FIN FENETRE MENU PRINCIPALE
                //

                //
                // FENETRE NOUVELLE PARTIE
                //
                /*
                 * Bouton "Continuer"
                 */
                case "nouvellePartieContinuer":
                    if (carteChoisis.equals("")) {
                        JOptionPane.showMessageDialog(FenetreJeu, "Veuillez choisir une carte ! ");
                    } else if (nbJoueursH + nbJoueursIA < 2 || nbJoueursH + nbJoueursIA > 4)
                        JOptionPane.showMessageDialog(FenetreJeu, "Vous devez choisir entre 2 et 4 joueurs en tout ! ");
                    else if (!FenetreJeu.getPanelNouvellePartie().setAllNames(nbJoueursH+nbJoueursIA))
                        JOptionPane.showMessageDialog(FenetreJeu, "Vous devez entrer les noms des joueurs ! ");
                    else {
                        try {
                            chargerCarte(new FileInputStream("src" + File.separator + "data" + File.separator + "cartes"
                                    + File.separator + "" + carteChoisis + ".txt"));
                            for (int i = 0; i < nbJoueursH + nbJoueursIA; i++) {
                                if (i < nbJoueursH)
                                    listeJoueur.add(new Joueur(
                                            FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].getText(), false));
                                else
                                    listeJoueur.add(new Joueur(
                                            FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].getText(), true));
                            }
                            placerBasesJoueurs();
                            setCellulesMap();
                            panelChangerScenario = new PanelChangerScenario(cellulesToHexagones());
                            panelChangerScenario.setMonumentNombre(6-calculerNombreMonument());
                            FenetreJeu.setPanelChangerScenario(panelChangerScenario);
                            panelChangerScenario.enregistrerEcouteur(this);
                            terrainChoisi = TypeTerrain.NEIGE; tour = 0; joueurActuel = listeJoueur.get(0);
                            FenetreJeu.changerPanel(PanelActuel.CHANGERSCENARIO);
                            JOptionPane.showMessageDialog(FenetreJeu, "Vous pouvez à présent modifier la carte comme bon vous semble, faîtes preuve de créativité !");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                //
                // FIN NOUVELLE PARTIE
                //
                //
                // FENETRE CHARGER PARTIE
                //
                /**
                 * Bouton "Charger une partie sauvegardée"
                 */
                case "chercherSauvegarde":
                    JFileChooser choose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    choose.setCurrentDirectory(new File("src" + File.separator + "data" + File.separator + "partie"));
                    choose.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Documents de type TXT", "txt");
                    choose.addChoosableFileFilter(filter);
                    int res = choose.showOpenDialog(null);
                    if (res == JFileChooser.APPROVE_OPTION) {
                        sauvegardeChoisis = choose.getSelectedFile();
                        ((PanelChargerPartie) FenetreJeu.getPanelChargerPartie()).getLabelCarteChosie()
                                .setText("Sauvegarde chosie : " + sauvegardeChoisis.getName());
                    }
                    break;
                /**
                 * Bouton "Lancer la partie sauvegardée"
                 */
                case "lancerPartieChargee":
                    if (sauvegardeChoisis != null) {
                        // Generer un action pane si fichier pas bon
                        try {
                            chargerPartie(new FileInputStream(sauvegardeChoisis));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        setCellulesMap();
                        try {
                            pj = new PanelJeu(cellulesToHexagones());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        FenetreJeu.setPanelJeu(pj);
                        pj.enregistrerEcouteur(this);
                        FenetreJeu.changerPanel(PanelActuel.JEU);
                        JOptionPane.showMessageDialog(FenetreJeu, "Votre partie est correctement chargée !");
                        initNouveauTour();
                        pj.repaint();
                        reinitialiserChrono();
                        pj.getPanelCentrePlateau().enregistrerEcouteur(this);

                    } else {
                        JOptionPane.showMessageDialog(FenetreJeu, "Vous devez choisir une sauvegarde de partie ! ");
                    }
                    break;
                //
                // FIN FENETRE CHARGER PARTIE
                //
                //
                // FENETRE CHANGER SCENARIO
                //
                /**
                 * Bouton "Choix Monument"
                 */
                case "choixMonument":
                    selectionMonument = true;
                    FenetreJeu.setChoixMonumentTxt("Monument selctionne");
                    break;
                /**
                 * Bouton "Lancer Partie" -- Fenetre nouvelle partie apres scénario
                 */
                case "lancerPartieApresScenario":
                    try {
                        pj = new PanelJeu(cellulesToHexagones());
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    FenetreJeu.setPanelJeu(pj);
                    pj.enregistrerEcouteur(this);
                    FenetreJeu.changerPanel(PanelActuel.JEU);
                    if (!joueurActuel.getEstIa())
                    JOptionPane.showMessageDialog(FenetreJeu,
                        "Votre partie vient d'être lancée, GAGNER CETTE GUERRE ! Mais amusez-vous quand même...");
                    initPanelJeu = true;
                    initNouveauTour();
                    pj.repaint();
                    reinitialiserChrono();
                   
    
                    break;
                /**
                 * Bouton "Sauvrgarder carte"
                 */
                case "sauvegarderCarte":
                    String nomMap = FenetreJeu.getPanelChangerScenario().getNomCarte().getText();
                    nomMap = nomMap.replace("" + File.separator + "", "");
                    nomMap = nomMap.replace("/", "");
                    nomMap = nomMap.replace(".", "");
                    nomMap = nomMap.replace(";", "");
                    nomMap = nomMap.replace("*", "");
                    nomMap = nomMap.replace("?", "");
                    nomMap = nomMap.replace("!", "");
                    nomMap = nomMap.replace("\"", "");
                    nomMap = nomMap.replace(" ", "");
                    if (nomMap.equals(""))
                        JOptionPane.showMessageDialog(FenetreJeu, "Vous devez entrer un nom de fichier ! ");
                    else {
                        JOptionPane.showMessageDialog(FenetreJeu,
                                "Votre sauvegarde est bien passé vous pourrez la retrouver dans la liste des cartes dans le menu \"Nouvelle partie\" !");
                        sauvegarderMap(nomMap);
                    }
                    break;
                //
                // FIN FENETRE CHANGER SCENARIO
                //
                // FENETRE EN PARTIE
                //
                /**
                 * ACHAT
                 */
                case "achatArcher":
                    FenetreJeu.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    uniteAchete = TypeUnite.ARCHER;
                    break;
                case "achatCavalerie":
                    FenetreJeu.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    uniteAchete = TypeUnite.CAVALERIE;
                    break;
                case "achatInfanterie":
                    FenetreJeu.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    uniteAchete = TypeUnite.INFANTERIE;
                    break;
                case "achatInfanterieLourde":
                    FenetreJeu.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    uniteAchete = TypeUnite.INFANTERIELOURDE;
                    break;
                case "achatMage":
                    FenetreJeu.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    uniteAchete = TypeUnite.MAGE;
                    break;
                /*
                 * FIN ACHAT
                 */
                /**
                 * Bouton "Fin de tour" -- Fenetre en jeu
                 */
                case "finTour":
                    initNouveauTour();
                    break;
                case "sauvegarderPartie":
                    JOptionPane.showMessageDialog(FenetreJeu, "SAUVEGARDE PARTIE");
                    sauvegarderPartie();
                    break;
                /**
                 * Bouton "Abandonner" -- Fenetre en jeu
                 */
                case "abandonner":
                    joueurActuel.setEnJeu(false);
                    supprimerArmee(joueurActuel);
                    supprimerBase(joueurActuel);
                    initNouveauTour();
                    break;
                //
                // FIN FENETRE EN PARTIE
                //
                //
                // TOUTES LES FENETRES
                //
                /**
                 * Bouton "Retour"
                 */
                case "retourMenu":
                    effacerDonnees();
                    FenetreJeu.changerPanel(PanelActuel.MENU);
                    if (initPanelJeu) {
                        FenetreJeu.getPanelJeu().getTimerHorloge().stop();
                        FenetreJeu.getPanelJeu().getTimerTour().stop();
                    }

                    break;
                default:
                    break;
            }
        }
        /**
         * Clic sur une liste
         */
        else if (evt.getSource() instanceof JComboBox<?>) {
            int nomJ;
            switch (evt.getActionCommand()) {
                case "nbJoueursH":
                    JComboBox<Integer> nbH = (JComboBox<Integer>) evt.getSource();
                    nbJoueursH = (Integer) nbH.getSelectedItem();
                    nomJ = nbJoueursH + nbJoueursIA;
                    if (nomJ > 4)
                        nomJ = 4;
                    for (int i = 0; i < 4; i++) {
                        if (i < nomJ) {
                            FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(true);
                            FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(true);
                        } else {
                            FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(false);
                            FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(false);
                        }
                    }
                    break;
                case "nbJoueursIA":
                    JComboBox<Integer> nbIA = (JComboBox<Integer>) evt.getSource();
                    nbJoueursIA = (Integer) nbIA.getSelectedItem();
                    nomJ = nbJoueursH + nbJoueursIA;
                    if (nomJ > 4)
                        nomJ = 4;
                    for (int i = 0; i < 4; i++) {
                        if (i < nomJ) {
                            FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(true);
                            FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(true);
                        } else {
                            FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(false);
                            FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(false);
                        }
                    }
                    break;
                case "choixMap":
                    JComboBox<String> nomCarte = (JComboBox<String>) evt.getSource();
                    carteChoisis = (String) nomCarte.getSelectedItem();
                    break;
                case "listeTerrains":
                    selectionMonument = false;
                    JComboBox<TypeTerrain> choixTerrain = (JComboBox<TypeTerrain>) evt.getSource();
                    Jeu.terrainChoisi = (TypeTerrain) choixTerrain.getSelectedItem();
                    FenetreJeu.setChoixTerrainTxt((String) choixTerrain.getSelectedItem().toString());
                    break;
                default:
                    break;
            }
        }
    }
}