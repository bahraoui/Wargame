package controleur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Vue.FrameJeu;
import Vue.Hexagone;
import Vue.PanelActuel;
import Vue.PanelChargerPartie;
import Vue.PanelChargerScenario;
import Vue.PanelJeu;
import Vue.PanelMap;
import Vue.Point;
import Vue.TypeBatimentVue;
import Vue.TypeTerrain;
import Vue.TypeUnite;
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
    private static PanelChargerScenario panelChargerScenario;
    private static TypeUnite uniteAchete;
    private static Case caseClic1, caseClic2;
    private static Hexagone hexCaseClic;
    private static Joueur joueurGagnant;
    private static boolean initPanelJeu;

    private static int cmpt=0;

    private static final int cote = 16;
    
    //
    //MAIN
    //
    public static void main(String[] args) throws IOException, InterruptedException {

        chronometre();        
        Jeu controleur = new Jeu();
        carteChoisis = "";
        finpartie = false;
        selectionMonument=false;
        uniteAchete=null;
        caseClic1 = null;
        caseClic2 = null;
        initPanelJeu = false;
        joueurGagnant = null;
        cellulesCarte = new Cellule[16][16];
        terrainChoisi = TypeTerrain.NEIGE;
        nbJoueursH = nbJoueursIA = 0;
        listeJoueur = new ArrayList<Joueur>();
        postionBaseJoueur = new ArrayList<ArrayList<Integer>>();
        plateau = new Plateau();
        setCellulesMap();
        FenetreJeu = new FrameJeu();
        FenetreJeu.enregistreEcouteur(controleur);
    }
    //
    //FONCTION
    //

    //
    //DONNES
    //

    //donne en parametre uniquement une case pas null
    public static boolean combat(Hexagone attaquant, Hexagone defenseur, int distanceCases) {
        int rangeAttaque;
        Case attaquantCase = plateau.get(attaquant.getCoord().getX()).get(attaquant.getCoord().getY());
        Case defenseCase = plateau.get(defenseur.getCoord().getX()).get(defenseur.getCoord().getY());
        if (attaquantCase.getBatiment() != null) {
            rangeAttaque = attaquantCase.getBatiment().getVision();
        }
        else {
            rangeAttaque = attaquantCase.getUnite().getVision();
        }
        if (rangeAttaque > distanceCases){
            Case.attaquer(attaquantCase,defenseCase);
            if (((Entite) defenseCase.estOccupe()).getPointDeVieActuel() <= 0){
                mortEntite(defenseCase);
                try {
                    defenseur.setTerrain(terrainModeleToVue(defenseCase.getTerrain()));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                calculVitoire();
            }
            return true;
        }
        return false;
    }

    //revoir
    public static void mortEntite(Case defenseur){
        if (defenseur.getUnite() != null) {
            for (int i = 0; i < listeJoueur.size(); i++) {
                for (int j = 0; j < listeJoueur.get(i).getArmee().size(); j++){
                    if (defenseur.getUnite().getIdentifiant() == listeJoueur.get(i).getArmee().get(j).getIdentifiant()){
                        listeJoueur.get(i).getArmee().remove(j); //armee
                        defenseur.setUnite(null); //plateau
                        if (listeJoueur.get(i).getArmee().size() > 0 || listeJoueur.get(i).getPieces() > 10){
                            listeJoueur.get(i).setEnJeu(false);
                        }
                        return;
                    }
                }
            }
        }
        else if (defenseur.getBatiment() != null) {
            if (defenseur.getBatiment().getEstBase() == TypeBatiment.BASE) {
                for (int i = 0; i < listeJoueur.size(); i++) {
                    if (listeJoueur.get(i).getBase().getIdentifiant() == defenseur.getBatiment().getIdentifiant()) {
                        listeJoueur.get(i).setEnJeu(false);
                        ArrayList<Integer> coordBase = postionBaseJoueur.get(listeJoueur.get(i).getNumeroJoueur());
                        plateau.get(coordBase.get(0)).get(coordBase.get(1)).setBatiment(null);
                    }
                }
            }
            else {
                joueurActuel.setPieces(joueurActuel.getPieces() + defenseur.getBatiment().getTresor());
                defenseur.setBatiment(null); //plateau
                FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
            }   
        }
    }

    public static void placerBasesJoueurs() {
        int nbJoueurs = listeJoueur.size();
        System.out.println("NOMBRE JOUEUR : "+nbJoueurs);
        for (int i = 0; i < listeJoueur.size(); i++) {
            System.out.println(listeJoueur.get(i).getPseudo());
        }
        placerBase(listeJoueur.get(0),0,0);
        placerBase(listeJoueur.get(1),15,14);
        if (nbJoueurs >=3) {
            placerBase(listeJoueur.get(2),0,15);            
        }
        if (nbJoueurs == 4) {
            placerBase(listeJoueur.get(3),15,0);            
        }
    }

    public static void placerBase(Joueur joueur, int coordY, int coordX){
        Batiment base = new Batiment(TypeBatiment.BASE);
        joueur.setBase(base);
        plateau.get(coordY).get(coordX).setBatiment(base);
        ArrayList<Integer> baseJ1 = new ArrayList<Integer>();
        baseJ1.add(0,coordY);
        baseJ1.add(1,coordX);
        postionBaseJoueur.add(baseJ1);
    }

    public static boolean placerUniteJoueur(Joueur joueur, Unite unite, int coordY, int coordX){
        Case caseUnite = plateau.get(coordY).get(coordX);
        int coordYBase = postionBaseJoueur.get(joueur.getNumeroJoueur()).get(0);
        int coordXBase = postionBaseJoueur.get(joueur.getNumeroJoueur()).get(1);
        int calculVisionY = coordY - coordYBase;
        int calculVisionX = coordX - coordXBase;
        if (caseUnite.getBatiment() == null && caseUnite.getUnite() == null && Math.abs(calculVisionY) <= joueur.getBase().getVision() && Math.abs(calculVisionX) <= joueur.getBase().getVision() && joueur.achatUniteArmee(unite)) {
            joueur.getArmee().add(unite);
            plateau.get(coordY).get(coordX).setUnite(unite);
            return true;
        }
        return false;
    }

    public static void plateauToMatice(int[][] matrice){
        boolean petiteLigne = false;
        int totalCells = 248;
        int col=0,ligne=0;
        for(int nbCellules = 0; nbCellules < totalCells; nbCellules++) {
            matrice[ligne][col] = plateau.get(ligne).get(col).getTerrain().getPtsDeplacement();
            col++;
            if (col%cote==0 && !petiteLigne) {
                col=0;
                ligne++;
                petiteLigne = !petiteLigne;
            } 
            else if (col%(cote-1)==0 && petiteLigne) {
                col=0;
                ligne++;
                petiteLigne = !petiteLigne;
            }
        }
    }

    public static boolean estValide(int row, int col) {
        return (row >= 0) && (row < cote) && (col >= 0) && (col < cote) && (plateau.get(row).get(col).estOccupe() == null);
      }
    
    public static Node trouverChemin(int mat[][], int srcX, int srcY, int destX, int destY) {
        int rowNum[] = { -1, 0, 0, 1 };
        int colNum[] = { 0, -1, 1, 0 };

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

            if (ptX == destX && ptY == destY){
            return curr;
            }

            q.remove();

            
            for (int i = 0; i < 4; i++) {
            int row = ptX + rowNum[i];
            int col = ptY + colNum[i];
            if (estValide(row, col) && mat[row][col] !=0 && !visited[row][col]) {
                visited[row][col] = true;
                Node Adjcell = new Node(col,row,curr.getDist()+1, curr);
                q.add(Adjcell);
            }
            }
        }
        return null;
    }

    public static boolean estValideAttaque(int row, int col) {
        return (row >= 0) && (row < cote) && (col >= 0) && (col < cote);
      }

    public static int calculDistanceAttaque(int mat[][], int srcX, int srcY, int destX, int destY) {
        int rowNum[] = { -1, 0, 0, 1 };
        int colNum[] = { 0, -1, 1, 0 };

        if (mat[srcY][srcX] == 0 || mat[destY][destX] == 0)
            return -1;

        boolean[][] visited = new boolean[cote][cote];

        visited[srcY][srcX] = true;

        Queue<Node> q = new LinkedList<>();

        Node s = new Node(srcY, srcX, 0, null);
        q.add(s);

        while (!q.isEmpty()) {
            Node curr = q.peek();
            int ptX = curr.getX();
            int ptY = curr.getY();

            if (ptX == destX && ptY == destY){
            return curr.getDist();
            }

            q.remove();

            
            for (int i = 0; i < 4; i++) {
            int row = ptX + rowNum[i];
            int col = ptY + colNum[i];
            if (estValideAttaque(row, col) && mat[row][col] !=0 && !visited[row][col]) {
                visited[row][col] = true;
                Node Adjcell = new Node(col,row,curr.getDist()+1, curr);
                q.add(Adjcell);
            }
            }
        }
        return -1;
    }

    private static void faireDeplacement(Unite unite, ArrayList<Node> deplacement) throws InterruptedException
    {
        Deplacement:
        for (int i = 1; i < deplacement.size(); i++) {
            plateau.get(deplacement.get(i-1).getX()).get(deplacement.get(i-1).getY()).setUnite(null);
            plateau.get(deplacement.get(i).getX()).get(deplacement.get(i).getY()).setUnite(unite);
            try {
                cellulesCarte[deplacement.get(i-1).getX()][deplacement.get(i-1).getY()].getHex().setTerrain(terrainModeleToVue(plateau.get(deplacement.get(i-1).getX()).get(deplacement.get(i-1).getY()).getTerrain()));
                cellulesCarte[deplacement.get(i).getX()][deplacement.get(i).getY()].getHex().setUnite(uniteModelToVue(unite));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            unite.setDeplacementActuel(unite.getDeplacementActuel()-1);
            Thread.sleep(150);
            System.out.println(plateau.affichage());
            if (unite.getDeplacementActuel() == 0)
                break Deplacement;
        }
    }
  

    public static boolean conditionTourEnJeu(){
        if (tour == 30){
            return true;
        }
        for (int i = 0; i < listeJoueur.size(); i++) {
            if (listeJoueur.get(i).getNumeroJoueur() != joueurActuel.getNumeroJoueur() && listeJoueur.get(i).getEnJeu() == true) {
                return false;
            }
        }
        System.out.println("tout le monde joue");
        return true;
    }

    public static void estEnJeu(Joueur joueurAttaque){
        if ((joueurAttaque.getArmee().size() == 0 && joueurAttaque.getPieces() < 6) || joueurAttaque.getBase() == null){
            joueurAttaque.setEnJeu(false);
        }
    }

    public static void calculVitoire(){
        if (conditionTourEnJeu()){
            if (tour == 30){
                int[] value = new int[listeJoueur.size()];
                for (int i = 0; i < value.length; i++) {
                    if (listeJoueur.get(i).getEnJeu()) {
                        value[i] = joueurActuel.getPieces();
                        for (int j = 0; j < listeJoueur.get(i).getArmee().size(); j++)
                            value[i]=value[i]+listeJoueur.get(i).getArmee().get(j).getCout();
                    }
                    else 
                        value[i] = -1;
                }
                int max = 0;
                int indice = 0;
                for (int i = 0; i < value.length; i++) {
                    if (max < value[i]) {
                        max = value[i];
                        indice = i;
                    }
                }
                joueurGagnant = listeJoueur.get(indice);
            }
            else {
                joueurGagnant = joueurActuel;
            }
        }
        System.out.println("Joueur gagant : "+joueurGagnant.getPseudo());
    }        



    public static void chronometre() {
    	Timer chrono =  new Timer();
    	chrono.schedule(new TimerTask(){
            int index = 0,  i = 0;
			@Override
			public void run() {
                while(finpartie == false){
                    i++;
                    if(i == listeJoueur.size()){
                        i = 0;
                    }else if(index == listeJoueur.size()-1){
                        index = 0;
                    }else if(index < listeJoueur.size() && i < listeJoueur.size()){   
                        System.out.println(joueurActuel);
                        joueurActuel = listeJoueur.get(index+1);
                        System.out.println(joueurActuel);
                        index += 1; 
                    }       
                } 

			}
    	}, 2000 , 2000);
        
    }

    public static void chrono() {
    	Timer chrono =  new Timer();
    	chrono.schedule(new TimerTask(){
            int index = 0,  i = 0;
			@Override
			public void run() {
                while(finpartie == false){
                    i++;
                    if(i == listeJoueur.size()){
                        i = 0;
                    }else if(index == listeJoueur.size()-1){
                        index = 0;
                    }else if(index < listeJoueur.size() && i < listeJoueur.size()){   
                        System.out.println(joueurActuel);
                        joueurActuel = listeJoueur.get(index+1);
                        System.out.println(joueurActuel);
                        index += 1; 
                    }       
                } 

			}
    	}, 2000 , 2000);
    }

    public static void actionDopportunite(Joueur joueur, int coordYFinalJoueur, int coordXFinalJoueur, int coordYOpportunite, int coordXOpportunite, Case caseAttaque) {
    	Case positionFinalJoueur = plateau.get(coordYFinalJoueur).get(coordXFinalJoueur); 
    	Case postionOpportunite = plateau.get(coordYOpportunite).get(coordXOpportunite); 
    	int calculVisionOppX = Math.abs(coordXFinalJoueur - coordXOpportunite);
    	int calculVisionOppY = Math.abs(coordYFinalJoueur - coordYOpportunite);
    	for(int i = 0; i<joueur.getArmee().size();i++) {
    		Entite att = new Entite(); 
	        if (postionOpportunite.getBatiment() != null) {
	            att = postionOpportunite.getBatiment();
	            if(calculVisionOppX <= att.getVision() && calculVisionOppY <= att.getVision()){
            		Case.attaquer(postionOpportunite, positionFinalJoueur);
            	}  
	        }
	        else if (postionOpportunite.getUnite() != null && listeJoueur.get(i).getArmee().get(i).getEnRepos() == true) {
	            att = postionOpportunite.getUnite();
	            if(positionFinalJoueur.getUnite() != att && (calculVisionOppX <= att.getVision() && calculVisionOppY <= att.getVision())){
	            	Unite.deplacementUnite(postionOpportunite.getUnite(), postionOpportunite, caseAttaque);
	            	Case.attaquer(postionOpportunite, positionFinalJoueur);
            	}
	        }
        }
    }

    public static void nouveauTour() throws InterruptedException {
        if (!conditionTourEnJeu()) { //condition de victoire
            if (tour != 0) {
                do {
                    joueurActuel = listeJoueur.get((joueurActuel.getNumeroJoueur()+1)%(nbJoueursH+nbJoueursIA));
                }while(joueurActuel.getEnJeu() == false);
            }
            tour++;
            FenetreJeu.getPanelJeu().getLabelNomJoueur().setText("Tour de : "+joueurActuel.getPseudo());
            FenetreJeu.getPanelJeu().getLabelNbTours().setText("Nombre de tours : "+tour);;
            FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
            if (joueurActuel.getEstIa()){
                tourIA();
                Thread.sleep(1000);
                //nouveauTour();
            }
            System.out.println(joueurActuel.getPseudo() +" - "+joueurActuel.getArmee().size());
        }
        else {
            effacerDonnes();
            FenetreJeu.getPanelJeu().getTimerHorloge().stop();
            FenetreJeu.getPanelJeu().getTimerTour().stop();
            System.out.println("Changer panneau");
        }
        
    }

    public static void resetChrono() {
        if (initPanelJeu) {
            FenetreJeu.getPanelJeu().setSeconde(0);
            FenetreJeu.getPanelJeu().setMinute(2);
            FenetreJeu.getPanelJeu().getTimerTour().restart();
            FenetreJeu.getPanelJeu().getTimerHorloge().restart();
        }
    }
    public static void effacerDonnes() {
        resetChrono();
        int nbJoueurs = listeJoueur.size();
        for (int i = nbJoueurs-1; i > 0 ; i--) {
            listeJoueur.remove(i);
        }
        listeJoueur = new ArrayList<Joueur>();
        plateau.removeAll(plateau);
        plateau = new Plateau();
        System.out.println("RESET HARD");
    }

    //
    // FIN DONNEES
    //

    //
    //PARTIE IA
    //

    /*public static void joueurAAttaquerIA() {
        if (joueurActuel.getIdentifiantCible() == -1 || listeJoueur.get(joueurActuel.getIdentifiantCible()).getEnJeu() == false) {
            ArrayList<Integer> quiAttaquer = new ArrayList<Integer>();
            for (int i = 0; i < listeJoueur.size(); i++) {
                if (listeJoueur.get(i).getNumeroJoueur() != joueurActuel.getNumeroJoueur() && listeJoueur.get(joueurActuel.getIdentifiantCible()).getEnJeu()){
                    quiAttaquer.add(listeJoueur.get(i).getNumeroJoueur());
                }
            }
            joueurActuel.setIdentifiantCible(quiAttaquer.get(new Random().nextInt(quiAttaquer.size())));
        }
    }*/

    //Par rapport à la base placé vers le centre de la map en prio
    public static boolean placementUnite(Unite unite) {
        int[][][] coordPossible = {{{0,1},{0,2},{1,0},{2,0},{2,1}},
                                {{13,14},{14,14},{14,15},{15,12},{15,13}},
                                {{0,14},{1,14},{2,15},{0,13},{1,13}},
                                {{12,0},{13,0},{14,0},{14,1},{15,1}}};
        for (int i = 0; i < 5; i++) {
            if (placerUniteJoueur(joueurActuel, unite, coordPossible[joueurActuel.getNumeroJoueur()][i][0],coordPossible[joueurActuel.getNumeroJoueur()][i][1])){
                cellulesCarte[coordPossible[joueurActuel.getNumeroJoueur()][i][0]][coordPossible[joueurActuel.getNumeroJoueur()][i][1]].getHex().setUnite(uniteModelToVue(unite));
                return true;
            }
        }
        return false;
    }

    public static Unite achatTroupesIA(int depense) throws InterruptedException{
        Unite troupeAchete = null;
        if (depense >= new InfanterieLourde().getCout()) {
            troupeAchete = new InfanterieLourde();
        }
        else if (depense >= new Mage().getCout()) {
            troupeAchete = new Mage();
        }
        else if (depense >= new Cavalerie().getCout()) {
            troupeAchete = new Cavalerie();
        }
        else if (depense >= new Infanterie().getCout()) {
            troupeAchete = new Infanterie();
        }
        else if (depense >= new Archer().getCout()) {
            troupeAchete = new Archer();
        }
        if (placementUnite(troupeAchete)){
            joueurActuel.achatUniteArmee(troupeAchete);
            joueurActuel.getArmee().add(troupeAchete);
            System.out.println("ACHAT POSSIBLE");
        }
        else {
            System.out.println("ACHAT IMPOSSIBLE");
        }
        return troupeAchete;
    }

    public static void rechercheEntiteProche() {


    }

    public static void estDeplacementPossible() {

        //renvoie une pos
    }

    public static void actionUniteIA(Unite unite) {
        //recherche Entite plus proche et deplacement vers elle/attaquer
    }

    public static void rechercheMonUniteDansPlateau(){
        
    }

    public static void tourIA(){
        //joueurAAttaquerIA();
        int depense = new Random().nextInt(joueurActuel.getPieces());
        while(depense >= 5) {
            try {
                Unite uniteachete = achatTroupesIA(depense);
                if (uniteachete != null){
                    depense -= uniteachete.getCout();
                    System.err.println(plateau.affichage());
                    FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < joueurActuel.getArmee().size(); i++) {
            actionUniteIA(joueurActuel.getArmee().get(i));
        }

    }

    //    
    //FIN PARTIE IA
    //

    //
    //AFFICHAGE
    //

    public static void setCellulesMap() throws IOException {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                TypeTerrain sol = null;
                TypeUnite unite = null;
                TypeBatimentVue batiment = null;
                if (plateau.get(i).get(j).getTerrain() instanceof Plaine)
                    sol = TypeTerrain.PLAINE;
                else if (plateau.get(i).get(j).getTerrain() instanceof Desert)
                    sol = TypeTerrain.DESERT;
                else if (plateau.get(i).get(j).getTerrain() instanceof Foret)
                    sol = TypeTerrain.FORET;
                else if (plateau.get(i).get(j).getTerrain() instanceof Mer)
                    sol = TypeTerrain.MER;
                else if (plateau.get(i).get(j).getTerrain() instanceof Montagne)
                    sol = TypeTerrain.MONTAGNE;
                else if (plateau.get(i).get(j).getTerrain() instanceof ToundraNeige)
                    sol = TypeTerrain.NEIGE;

                if (plateau.get(i).get(j).getUnite() instanceof Archer)
                    unite = TypeUnite.ARCHER;
                else if (plateau.get(i).get(j).getUnite() instanceof Cavalerie)
                    unite = TypeUnite.CAVALERIE;
                else if (plateau.get(i).get(j).getUnite() instanceof Infanterie)
                    unite = TypeUnite.INFANTERIE;
                else if (plateau.get(i).get(j).getUnite() instanceof InfanterieLourde)
                    unite = TypeUnite.INFANTERIELOURDE;
                else if (plateau.get(i).get(j).getUnite() instanceof Mage)
                    unite = TypeUnite.MAGE;
                
                
                if (plateau.get(i).get(j).getBatiment() != null && plateau.get(i).get(j).getBatiment().getEstBase() == TypeBatiment.BASE){
                    batiment = TypeBatimentVue.BASE;
                }
                else if (plateau.get(i).get(j).getBatiment() != null && plateau.get(i).get(j).getBatiment().getEstBase() == TypeBatiment.MONUMENT)
                    batiment = TypeBatimentVue.MONUMENT;

                Cellule cell = new Cellule(new Hexagone(sol, unite, batiment,new Point(i, j)), plateau.get(i).get(j));
                cellulesCarte[i][j] = cell;
            }
        }
    }

    public static Hexagone[][] cellulesToHexagones() throws IOException {
        Hexagone[][] hexs = new Hexagone[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                hexs[i][j] = cellulesCarte[i][j].getHex();
            }
        }
        return hexs;
    }

    
    
    public static Case coordToCase(int coordY, int coordX){
        return plateau.get(coordY).get(coordX);
    }



    private static TypeTerrain terrainModeleToVue(Terrain terrain) {
        TypeTerrain typeTerrain = null;
        if (terrain instanceof Plaine)
            typeTerrain = TypeTerrain.PLAINE;
        else if (terrain instanceof Desert)
            typeTerrain = TypeTerrain.DESERT;
        else if (terrain instanceof Foret)
            typeTerrain = TypeTerrain.FORET;
        else if (terrain instanceof Mer)
            typeTerrain = TypeTerrain.MER;
        else if (terrain instanceof Montagne)
            typeTerrain = TypeTerrain.MONTAGNE;
        else if (terrain instanceof ToundraNeige)
            typeTerrain = TypeTerrain.NEIGE;
        return typeTerrain;
    }


    public Terrain terrainVueToModele(TypeTerrain ter) {
        Terrain terrain = null;
        switch (ter) {
            case MER:
                terrain = new Mer();
                break;
            case DESERT:
                terrain = new Desert();
                break;
            case FORET:
                terrain = new Foret();
                break;
            case MONTAGNE:
                terrain = new Montagne();
                break;
            case PLAINE:
                terrain = new Plaine();
                break;
            case NEIGE:
                terrain = new ToundraNeige();
                break;
            default:
                System.err.println("Erreur : la conversion de terrains s'est mal passe, "+ter+" indefini.");
                break;
        }
        return terrain;
    }
    
    public TypeBatimentVue batimentModeleToVue(TypeBatiment batiment) {
        TypeBatimentVue typeBatimentVue = null;
        switch (batiment) {
            case MONUMENT:
                typeBatimentVue = TypeBatimentVue.MONUMENT;
                break;
            case BASE:
                typeBatimentVue = TypeBatimentVue.BASE;
                break;
        
            default:
                break;
        }
        return typeBatimentVue;
    }
   
    public Unite unteVueToModele(TypeUnite typeUnite){
        Unite unite = null;
        switch (typeUnite) {
            case ARCHER:
                unite = new Archer();
                break;
            case CAVALERIE:
                unite = new Cavalerie();
                break;
            case INFANTERIE:
                unite = new Infanterie();
                break;
            case INFANTERIELOURDE:
                unite = new InfanterieLourde();
                break;
            case MAGE:
                unite = new Mage();
                break;
        
            default:
                break;
        }
        return unite;
    }

    public static TypeUnite uniteModelToVue(Unite unite){
        TypeUnite uniteVue = null;
        if (unite instanceof Archer)
            uniteVue = TypeUnite.ARCHER;
        else if (unite instanceof Cavalerie)
            uniteVue = TypeUnite.CAVALERIE;
        else if (unite instanceof Infanterie)
            uniteVue = TypeUnite.INFANTERIE;
        else if (unite instanceof InfanterieLourde)
            uniteVue = TypeUnite.INFANTERIELOURDE;
        else if (unite instanceof Mage)
            uniteVue = TypeUnite.MAGE;
        return uniteVue;
    }

    
    public static Batiment analyseSplBatiment(String spl) {

        String [] spl1 = new String[25];
        spl1 = spl.split(",");
        if (spl1.length == 3) {
            Batiment bat = new Batiment(TypeBatiment.MONUMENT);
            bat.setIndentifiant(Integer.parseInt(spl1[1]));
            bat.setPointDeVieActuel(Integer.parseInt(spl1[2]));
        return bat;
        }
        return null;
    }

    public static Unite analyseSplUnite(String spl, int [][] listeUnite) {
        String [] spl1 = new String[25];
        if (spl.length()>2) {
            spl1 = spl.split(",");
            Unite unite = new Unite(0,0,0,0);
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
                for (int j = 0; j < listeUnite.length; j++) {
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
    //GESTION SAUVEGARDE CHARGER
    //
    public static void sauvegardePartie() {
        try {
			
			File file = new File("src"+File.separator+"data"+File.separator+"partie"+File.separator+"savePartie1.txt");
			
			if (!file.exists()) {
			    file.createNewFile();
			   }
		
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			
			String chaine = new String();
            for (int i = 0; i < listeJoueur.size(); i++) {
                chaine +="[";
                chaine +=listeJoueur.get(i).getPseudo()+","+listeJoueur.get(i).getEstIa()+","+listeJoueur.get(i).getPieces()+",{";
                for (int j = 0; j < listeJoueur.get(i).getArmee().size(); j++) {
                    chaine += listeJoueur.get(i).getArmee().get(j).getIdentifiant();
                    if (j<listeJoueur.get(i).getArmee().size()-1) {
                        chaine+=".";
                    }
                }
                chaine +="}";
                if (i<listeJoueur.size()-1) {
                    chaine+="];";
                }
            }
            chaine +="]\n";
			chaine += sauvegardeStringMap(chaine);
            chaine += tour+"\n";
            chaine += joueurActuel.getNumeroJoueur()+"\n";
            for (int i = 0; i < postionBaseJoueur.size(); i++) {
                chaine +="[";
                chaine += postionBaseJoueur.get(i).get(0)+","+ postionBaseJoueur.get(i).get(1)+","+plateau.get(postionBaseJoueur.get(i).get(0)).get(postionBaseJoueur.get(i).get(1)).getBatiment().getPointDeVieActuel();
                if (i<postionBaseJoueur.size()-1) {
                    chaine+="];";
                }
            }
            chaine +="]\n";
            chaine += finpartie;
			
			fw.write(chaine);
			fw.close();
			
		} catch (Exception e) {
			System.out.println("Erreur ecriture");
		}

    }

    public void chargerCarte(FileInputStream file){
        String line = new String();
        Scanner scanner = new Scanner(file); 
        String [] strValues1;
        int[][] listeUnite = new int[50][50];
        for (int i = 0; i < plateau.size(); i++) {
            line = scanner.nextLine();
            strValues1 = line.split(",");
            chargeLineMap(line,i,listeUnite);
        }
    }


    public static void chargePartie(FileInputStream file) {
        
        //try {

            String line = new String();
		    String [] strValues1;
            String [] strValues2;
            String [] strValues3;
			Scanner scanner = new Scanner(file);  
            

            line = scanner.nextLine();
            strValues1 = line.split(";");

            int[][] listeUnite = new int[50][50];

            for (int i = 0; i < strValues1.length; i++) {
                strValues1[i] = strValues1[i].replace("[", "");
                strValues1[i] = strValues1[i].replace("]", "");
                strValues2 = strValues1[i].split(",");
                Joueur joueur = new Joueur(strValues2[0],Boolean.parseBoolean(strValues2[1]));
                listeJoueur.add(joueur);
                joueur.setPieces(Integer.parseInt(strValues2[2]));

                strValues2[3] = strValues2[3].replace("{", "");
                strValues2[3] = strValues2[3].replace("}", "");
                if(strValues2[3].length() > 0){
                    strValues3 = strValues2[3].split(".");
                    for (int j = 0; j < strValues3.length; j++) {
                        listeUnite[i][j]=Integer.parseInt(strValues3[j]);
                    }
                }
                else{
                    listeUnite[i][0] = -1 ;
                }
            }
            
            for (int i = 0; i < plateau.size(); i++) {
                line = scanner.nextLine();
                strValues1 = line.split(",");
                chargeLineMap(line,i,listeUnite);
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

	        scanner.close();    
    }

    public static void sauvegardeMap(String fichier){
        try {
            File file = new File("src"+File.separator+"data"+File.separator+"cartes"+File.separator+fichier+".txt");
			
			if (!file.exists()) {
			    file.createNewFile();
			   }
		
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			String chaine = new String();
            chaine = sauvegardeStringMap(chaine);
            fw.write(chaine);
            fw.close();
        } catch (Exception e) {
        }
    }

    public static String sauvegardeStringMap(String chaine) {
        for (int i = 0; i < plateau.size(); i++) {
            for (int j = 0; j < plateau.size(); j++) {
                    /// Ajout du premier [ pour chaque case
                    chaine+="[";

                    /// Déclaration des variables utiles
                    Terrain terrain = plateau.get(i).get(j).getTerrain();
                    Unite unite = plateau.get(i).get(j).getUnite();
                    Batiment batiment = plateau.get(i).get(j).getBatiment();
                    String [] splStrings;
                    String chaineTerrain = new String();
                    String chaineUnite = new String();
                    String chaineBatiment = new String();

                    /// Recuperation du type de Terrain
                    chaineTerrain = terrain.toString();
                    splStrings = chaineTerrain.split(":");
                    chaineTerrain = splStrings[0];
                    splStrings = chaineTerrain.split(" ");
                    chaine += splStrings[0]+":";

                    /// Unite
                    if (!(unite == null)) {
                        chaineUnite = unite.toString()+","+unite.getIdentifiant()+","+unite.getPointDeVieActuel()+","+unite.getDeplacementActuel()+","+unite.getAAttaque()+","+unite.getEnRepos();
                    }
                    chaine += chaineUnite+":";

                    /// Batiment
                    if (!(batiment == null) && (batiment.getEstBase() != TypeBatiment.BASE)){
                        chaineBatiment = batiment.toString()+","+batiment.getIdentifiant()+","+batiment.getPointDeVieActuel();
                    }
                    chaine += chaineBatiment;

                    /// Ajout de fin
                    chaine+="]";
                    if (j<plateau.size()-1) {
                    chaine+=";";
                }
            }
            chaine+="\n";
        }
        return chaine;
    }


    public static void chargeLineMap(String line,int iline,int [][] listeUnite) {
        
        String [] spl1;
        String [] spl2;

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
            if(spl2.length == 2){
                plateau.get(iline).get(i).setUnite(analyseSplUnite(spl2[1],listeUnite));
            } 
            else if (spl2.length == 3){
                plateau.get(iline).get(i).setBatiment(analyseSplBatiment(spl2[2]));
            }
        }
    }

    //
    // FIN SAUVEGARDE CHARGER PARTIE
    //
       

    //
    // ACTION PERFORMED
    //

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof Hexagone) {
            Hexagone hexClic = (Hexagone) e.getSource();
            switch (FenetreJeu.getPanelActuel()) {
                case CHANGERSCENARIO:
                    if (terrainChoisi != null && selectionMonument == false) {
                        try {
                            hexClic.setTerrain(terrainChoisi);
                            cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase().setTerrain(terrainVueToModele(terrainChoisi));
                            if (cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase().getBatiment() != null) {
                                hexClic.setBatiment(batimentModeleToVue(cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase().getBatiment().getEstBase()));
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else if (selectionMonument == true) {
                        try {
                            if (cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase().getBatiment() != null) {
                                switch (batimentModeleToVue(cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase().getBatiment().getEstBase())) {
                                    case MONUMENT:
                                        hexClic.setTerrain(terrainModeleToVue(cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase().getTerrain()));
                                        hexClic.setMonument(false);
                                        panelChargerScenario.setMonumentNb(false);
                                        cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase().setBatiment(null);                              
                                        break;
                                    
                                    default:
                                        JOptionPane.showMessageDialog(FenetreJeu, "Il y a déjà une base placé ici.");         
                                        break;
                                }
                            }
                            else if(panelChargerScenario.getNbMonumentsRestants() == 0)
                                JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez plus placer de monuments.");
                            else if (cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase().getUnite() != null)
                                JOptionPane.showMessageDialog(FenetreJeu, "Il y a déjà une unité placé ici.");
                            else {
                                panelChargerScenario.setMonumentNb(true);
                                hexClic.setMonument(true);
                                cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase().setBatiment(new Batiment(TypeBatiment.MONUMENT));
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                case JEU:
                    if (uniteAchete != null){
                        switch (uniteAchete) {
                            case ARCHER:
                                System.out.println("Achat archer");
                                Archer archer = new Archer();
                                if (placerUniteJoueur(joueurActuel, archer, hexClic.getCoord().getX(), hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                    System.out.println(joueurActuel.getNumeroJoueur());
                                    System.out.println(postionBaseJoueur.get(joueurActuel.getNumeroJoueur()).get(0));
                                    System.out.println(postionBaseJoueur.get(joueurActuel.getNumeroJoueur()).get(1));
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            case CAVALERIE:
                                Cavalerie cavalerie = new Cavalerie(); 
                                if (placerUniteJoueur(joueurActuel, cavalerie, hexClic.getCoord().getX(), hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            case INFANTERIE:
                                Infanterie infanterie = new Infanterie(); 
                                if (placerUniteJoueur(joueurActuel, infanterie, hexClic.getCoord().getX(), hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            case INFANTERIELOURDE:
                                InfanterieLourde infanterieLourde = new InfanterieLourde(); 
                                if (placerUniteJoueur(joueurActuel, infanterieLourde, hexClic.getCoord().getX(), hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            case MAGE:
                                Mage mage = new Mage(); 
                                if (placerUniteJoueur(joueurActuel, mage, hexClic.getCoord().getX(), hexClic.getCoord().getY())) {
                                    FenetreJeu.getPanelJeu().updateGoldJoueurAffichage(joueurActuel.getPieces());
                                    hexClic.setUnite(uniteAchete);
                                }
                                else 
                                    JOptionPane.showMessageDialog(FenetreJeu, "Vous ne pouvez pas acheter cette unité et la placer ici ! ");
                                break;
                            default:
                                break;
                        }
                        uniteAchete = null;                        
                    }
                    else {
                        if (caseClic2 == null && caseClic1 != null) {
                            caseClic2 = cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase();
                            System.out.println("Deuxieme clic recup");

                            if (caseClic2.estOccupe() == null) {
                                if (caseClic1.estOccupe() instanceof Unite) {
                                    if (((Unite) caseClic1.estOccupe()).getDeplacementActuel() > 0) {
                                        JOptionPane.showMessageDialog(FenetreJeu, "Deplacement lancé");
                                        int[][] matricePlateau = new int[cote][cote];
                                        plateauToMatice(matricePlateau);
                                        Node chemin = trouverChemin(matricePlateau,hexCaseClic.getCoord().getX(),hexCaseClic.getCoord().getY(),hexClic.getCoord().getX(),hexClic.getCoord().getY());
                                        ArrayList<Node> cheminComplet = new ArrayList<Node>();
                                        Node.nodeToArray(cheminComplet,chemin);
                                        try {
                                            faireDeplacement((Unite)caseClic1.estOccupe(),cheminComplet);
                                            JOptionPane.showMessageDialog(FenetreJeu, "Deplacement fini");
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    else 
                                        JOptionPane.showMessageDialog(FenetreJeu, "Unité n'a plus de point déplacement");
                                }
                            }
                            else if (caseClic2.estOccupe() != null && !joueurActuel.estMonUnite(caseClic2)){
                                int[][] matricePlateau = new int[cote][cote];
                                plateauToMatice(matricePlateau);
                                int distanceCase = calculDistanceAttaque(matricePlateau, hexCaseClic.getCoord().getX(),hexCaseClic.getCoord().getY(),hexClic.getCoord().getX(),hexClic.getCoord().getY());
                                if (combat(hexCaseClic, hexClic, distanceCase)){
                                    JOptionPane.showMessageDialog(FenetreJeu, "Attaque");
                                }
                            }
                            caseClic1 = null;
                            caseClic2 = null;
                            System.out.println("Reset");
                        }
                        else if (caseClic1 == null && caseClic2 == null){
                           caseClic1 = cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase();
                           System.out.println("Premier clic recup");
                           hexCaseClic = hexClic;
                           if (caseClic1.estOccupe() == null || !joueurActuel.estMonUnite(caseClic1)){
                                caseClic1 = null;
                                JOptionPane.showMessageDialog(FenetreJeu, "Mauvaise case");
                           }
                        }
                    }
                    Case caseSelectionne = cellulesCarte[hexClic.getCoord().getX()][hexClic.getCoord().getY()].getCase();
                    System.out.println(caseSelectionne);
                    System.out.println(hexClic.getCoord().getX()+" - "+hexClic.getCoord().getY());
                    FenetreJeu.getPanelJeu().getLabelTypeTerrain().setText(caseSelectionne.getTerrain().afficherTypeTerrain());
                    FenetreJeu.getPanelJeu().getLabelBonusTerrain().setText(caseSelectionne.getTerrain().afficherBonus());
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
                //FENETRE MENU PRINCIPALE
                //
                /*
                 * Bouton "Nouvelle Partie"
                 */
                case "nouvellePartie":
                    FenetreJeu.changePanel(PanelActuel.NOUVELLEPARTIE);
                    ArrayList<String> listNomMap = new ArrayList<String>();
                    FenetreJeu.getPanelNouvellePartie().initListeCartes(listNomMap);
                    FenetreJeu.getPanelNouvellePartie().setChoixMap(listNomMap);
                    break;
                /*
                 * Bouton "Charger Partie"
                 */
                case "chargerPartie":
                    FenetreJeu.changePanel(PanelActuel.CHARGERPARTIE);
                    break;
                /*
                 * Bouton "Règles"
                 */
                case "afficherRegles":
                    FenetreJeu.changePanel(PanelActuel.REGLES);
                    break;
                /*
                * Bouton "Quitter"
                */
                case "quit":
                    int reponse = JOptionPane.showConfirmDialog(FenetreJeu, "Voulez-vous quitter l'application ?", "Êtes-vous sur ?",0, 0);
                    if (reponse == 0) {
                        FenetreJeu.dispose();
                        System.exit(0);
                    }
                    break;
                //
                //FIN FENETRE MENU PRINCIPALE
                //

                //
                //FENETRE NOUVELLE PARTIE
                //
                /*
                 * Bouton "Continuer"
                 */
                case "nouvellePartieContinuer":
                    if (carteChoisis.equals("")){
                        JOptionPane.showMessageDialog(FenetreJeu, "Veuillez choisir une carte ! ");
                    }
                    else if (nbJoueursH + nbJoueursIA < 2  || nbJoueursH + nbJoueursIA > 4 )
                        JOptionPane.showMessageDialog(FenetreJeu, "Vous devez choisir entre 2 et 4 joueurs en tout ! ");
                    else if (!FenetreJeu.getPanelNouvellePartie().setAllNames(nbJoueursH+nbJoueursIA))
                        JOptionPane.showMessageDialog(FenetreJeu, "Vous devez entrer les noms des joueurs ! ");
                    else {
                        try {
                            chargerCarte(new FileInputStream("src"+File.separator+"data"+File.separator+"cartes"+File.separator+""+carteChoisis+".txt"));
                            for (int i = 0; i < nbJoueursH+nbJoueursIA; i++) {
                                if (i < nbJoueursH)
                                    listeJoueur.add(new Joueur(FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].getText(),false));
                                else 
                                    listeJoueur.add(new Joueur(FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].getText(),true));
                            }
                            placerBasesJoueurs();
                            //REGROUPER ?
                            setCellulesMap();
                            panelChargerScenario = new PanelChargerScenario(cellulesToHexagones());
                            FenetreJeu.setPanelChangerScenario(panelChargerScenario);
                            panelChargerScenario.enregistreEcouteur(this);
                            //REGROUPER
                            terrainChoisi = TypeTerrain.NEIGE; tour = 0; joueurActuel = listeJoueur.get(0);
                            FenetreJeu.changePanel(PanelActuel.CHANGERSCENARIO);  
                        } catch (IOException e) {
                            e.printStackTrace();
                        }  
                    }
                    break;
                //
                //FIN NOUVELLE PARTIE
                //
                //
                //FENETRE CHARGER PARTIE
                //
                /**
                 * Bouton "Charger une partie sauvegardée"
                 */
                case "chercherSauvegarde":
                    JFileChooser choose = new JFileChooser(
                        FileSystemView
                        .getFileSystemView()
                        .getHomeDirectory()
                    );
                    choose.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Documents de type TXT", "txt");
                    choose.addChoosableFileFilter(filter);
                    int res = choose.showOpenDialog(null);
                    if (res == JFileChooser.APPROVE_OPTION) {
                        sauvegardeChoisis = choose.getSelectedFile();
                        ((PanelChargerPartie) FenetreJeu.getPanelChargerPartie()).getLblCarteChosie().setText("Sauvegarde chosie : " + sauvegardeChoisis.getName());
                    }
                    break;
                /**
                 * Bouton "Lancer la partie sauvegardée"
                 */
                case "lancerPartieChargee":
                    if (sauvegardeChoisis != null) {
                        try {
                            // Generer un action pane si fichier pas bon
                            chargePartie(new FileInputStream(sauvegardeChoisis));
                            setCellulesMap();
                            pj = new PanelJeu(cellulesToHexagones());
                            FenetreJeu.setPanelJeu(pj);
                            pj.enregistreEcouteur(this);
                            FenetreJeu.changePanel(PanelActuel.JEU);
                            initPanelJeu = true;
                            resetChrono();
                        } catch (IOException e) {
                            e.printStackTrace();                   
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(FenetreJeu, "Vous devez choisir une sauvegarde de partie ! ");
                    }
                    break;          
                //
                //FIN FENETRE CHARGER PARTIE
                //
                //
                //FENETRE CHANGER SCENARIO
                //
                /**
                 * Bouton "Choix Monument"
                 */
                case "choixMonument" :
                    selectionMonument=true;
                    FenetreJeu.setChoixMonumentTxt("Monument selctionne");
                    break;
                /**
                 * Bouton "Lancer Partie" -- Fenetre nouvelle partie apres scénario
                 */
                case "lancerPartieApresScenario":
                    try {
                        pj = new PanelJeu(cellulesToHexagones());
                        FenetreJeu.setPanelJeu(pj);
                        pj.enregistreEcouteur(this);
                        FenetreJeu.changePanel(PanelActuel.JEU);
                        initPanelJeu = true;
                        nouveauTour(); 
                        resetChrono(); 
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }  
                    break;
                /**
                 * Bouton "Sauvrgarder carte"
                 */
                case "sauvegarderCarte" :
                    String nomMap = FenetreJeu.getPanelChangerScenario().getNomCarte().getText();
                    nomMap = nomMap.replace(""+File.separator+"", "");
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
                    else 
                        sauvegardeMap(nomMap);
                    break;
                //
                //FIN FENETRE CHANGER SCENARIO
                //
                //FENETRE EN PARTIE
                //
                /**
                 * ACHAT
                 */
                case "achatArcher":
                    uniteAchete=TypeUnite.ARCHER;
                    break;
                case "achatCavalerie":
                    uniteAchete=TypeUnite.CAVALERIE;
                    break;
               case "achatInfanterie":
                    uniteAchete=TypeUnite.INFANTERIE;
                    break;
                case "achatInfanterieLourde":
                    uniteAchete=TypeUnite.INFANTERIELOURDE;
                    break;
                case "achatMage":
                    uniteAchete=TypeUnite.MAGE;
                    break;
                /*
                * FIN ACHAT
                */
                /**
                 * Bouton "Fin de tour" -- Fenetre en jeu
                 */
                case "finTour":
                    try {
                        resetChrono();
                        nouveauTour();
                        joueurActuel.regenerationUniteArmee();
                        joueurActuel.gainTourJoueur(tour);
                        JOptionPane.showMessageDialog(FenetreJeu, "Tour n° "+tour+" Joueur : "+joueurActuel.getPseudo());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                /**
                 * Bouton "Abandonner" -- Fenetre en jeu
                 */
                case "abandonner":
                    try {
                        joueurActuel.setEnJeu(false);
                        nouveauTour();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                //
                //FIN FENETRE EN PARTIE
                //
                //
                //TOUTES LES FENETRES
                //
                /**
                 * Bouton "Retour" 
                 */
                case "retourMenu":
                    effacerDonnes();
                    FenetreJeu.changePanel(PanelActuel.MENU);
                    if (initPanelJeu){
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
                        if(i < nomJ) {
                            FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(true);
                            FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(true);
                        }
                        else {
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
                        if(i < nomJ) {
                            FenetreJeu.getPanelNouvellePartie().getNomJoueur()[i].setVisible(true);
                            FenetreJeu.getPanelNouvellePartie().getTxtNomJoueur()[i].setVisible(true);
                        }
                        else {
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

