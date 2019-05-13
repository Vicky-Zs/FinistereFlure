/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstereflure;
import character.*;
import map.*;
import token.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Game est la classe principal sur jeu pour le déroulement de la partie
 * @author Cédric
 */

public class Game {
  public final static int nbPlayers = 2;
  //Nombre de joueurs
  protected Cell[][] map = new Cell[16][11];
  //Carte du jeu (Représentation en rectangle)
  protected Player[] p = new Player[nbPlayers];
  //Tableau de nos deux joueurs
  protected Monster m;
  //Le monstre
  protected ArrayList<Token> tokenOutside = new ArrayList<>();
  //Liste de token à l'extérieur
  protected ArrayList<TokenR> tokenR = new ArrayList<>();
  //Liste de bloc de pierre
  protected ArrayList<TokenP> tokenPWin = new ArrayList<>();
  //Liste des token ayant gagné la partie
  protected boolean turnPlayers = true;
  //Permet de savoir si c'est au joueur ou non
  protected int nbTurn = 1;
  //Le nombre de tours
  private Menu menu = new Menu(this);
  //Classe pour lancer les différents menus

  /**
   *
   * Contructeur de la classe Game
   * Initialisation des joueurs
   */
  public Game(){
    // Initialisation des joueurs
    iniMap();
    iniDecorations();
    Scanner input = new Scanner(System.in);
    String temp;
    System.out.println("\nVeuillez rentrer le pseudo du 1er joueur :");
    String pseudo = input.nextLine();
    this.p[0] = new Player(pseudo,0,this);
    int i = 1;
    do {
      System.out.println("Est-ce qu'il y a un deuxième joueur ? \"Oui\" ou \"Non\"");
      temp = input.nextLine();
      if (null != temp) switch (temp) {
        case "Oui":
        System.out.println("\nVeuillez rentrer le pseudo du 2e joueur :");
        pseudo = input.nextLine();
        this.p[1] = new Player(pseudo,2,this);
        i = 0;
        break;
        case "Non":
        this.p[1] = new IA(1,this);
        i = 0;
        break;
        default:
        System.out.println("Cette réponse n'est pas valide.");
        break;
        }
    }while (i != 0);
    // Initialisation du monstre
    this.m = new Monster(this);
    // Vérification de l'initialisation
    System.out.println("\nListe des joueurs :");
    for(i = 0 ; i < nbPlayers ; i++){
      System.out.println("Joueur "+ (i+1) + "\t" + this.p[i].getPseudo());
    }
    System.out.println(this.m + "\n");
    turn();
    input.close();
  }

  /**
   * Returns map
   * @return [La carte entière]
   */
  public Cell[][] getMap() {
    return this.map;
  }
  /**
   * Returns map
   * @return [Une case de la carte]
   */
  public Cell getMap(int x, int y) {
    return this.map[x][y];
  }
  /**
   * Récupère tous joueurs
   * @return [Tableau de joueur]
   */
  public Player[] getPlayers() {
    return p;
  }
  /**
   * Returns Players
   * @return [Un joueur en particulier]
   */
  public Player getPlayer(int x) {
    return p[x];
  }
  /**
   * Récupère le nombre de joueur
   * @return [Nb de joueur]
   */
  public int getNbPlayer(){
    return p.length;
  }
  /**
   * Récupère le monstre
   * @return [monstre]
   */
  public Monster getMonster() {
    return m;
  }
  /**
   * Permet de récupérer la liste des token qui sont hors de la carte
   * @return [Liste token dehors]
   */
  public ArrayList<Token> getTokenOutside() {
    return tokenOutside;
  }
  /**
   * Permet de récupérer la liste des token pierre
   * @return [Liste de token]
   */
  public ArrayList<TokenR> getTokenR() {
    return tokenR;
  }
  /**
   * Récupère la liste des pions qui sont sortis du plateau
   * @return [Liste tokenP]
   */
  public ArrayList<TokenP> getTokenPWin() {
    return tokenPWin;
  }
  /**
   * Permet de savoir si c'est le tour des joueurs ou du monstre
   * @return [boolean vrai = tour des joueurs, faux = tour du monstre]
   */
  public boolean isTurnPlayers() {
    return turnPlayers;
  }
  /**
   * Change la phase de jeu (joueur ou monstre)
   * @param b [description]
   */
  public void changeTurnPlayers() {
    this.turnPlayers = !turnPlayers;
  }

  /**
   * Returns value of nbTurn
   * @return [nombre de tour]
   */
  public int getNbTurn() {
    return this.nbTurn;
  }
  /**
   * Augmente le nombre de tour
   */
  public void newTurn() {
    nbTurn ++;
  }
  /**
   * Initialise la carte
   */
  public void iniMap(){
    for(int i = 0; i < 16; i++) {
      for (int j = 0; j < 11; j++) {
        map[i][j] = new Cell(i, j);
      }
    }
    System.out.println("La map a été initialisé");
  }
  /**
   * Initialise les décords
   */
  public void iniDecorations(){
    //Initialisation de la première flaque de sang
    map[4][2].setBloodspot();
    map[5][2].setBloodspot();
    map[6][2].setBloodspot();
    map[7][2].setBloodspot();
    //Initialisation de la deuxième flaque de sang
    map[8][8].setBloodspot();
    map[9][8].setBloodspot();
    map[8][7].setBloodspot();
    map[9][7].setBloodspot();
    //Initialisation des 11 blocks de pierre
    tokenR.add(new TokenR(this, 2, 8));
    tokenR.add(new TokenR(this, 4, 3));
    tokenR.add(new TokenR(this, 5, 1));
    tokenR.add(new TokenR(this, 6, 4));
    tokenR.add(new TokenR(this, 7, 6));
    tokenR.add(new TokenR(this, 8, 1));
    tokenR.add(new TokenR(this, 8, 5));
    tokenR.add(new TokenR(this, 12, 3));
    tokenR.add(new TokenR(this, 12, 7));
    tokenR.add(new TokenR(this, 13, 5));
    tokenR.add(new TokenR(this, 14, 2));
    System.out.println("Les flaques de sang et les blocs de pierre ont été disposé");
  }
  /**
   * initialise les décors pour les tests
   * @param i [Pour différencier de l'autre méthode]
   */
  public void iniDecorations(int i){
    System.out.println("Initialisation des décors pour la partie test.");
    map[14][0].setBloodspot();
    map[13][0].setBloodspot();
    map[12][0].setBloodspot();
    map[11][0].setBloodspot();
    tokenR.add(new TokenR(this, 14, 2));
  }
  /**
   * Permet de savoir si un joueur à gagné
   * @return [Vrai = un joueur a gagné la partie, faux = personne n'a encore gagné]
   */
  public boolean win(){
    int[] temp = new int[nbPlayers];
    boolean itsWin = false;
    for (int i = 0; i < p.length; i++) {
      for (TokenP t : tokenPWin) {
        if (t.isWin()) {
          temp[t.getPlayerId()] ++;
        }
      }
      if (temp[i] == 3) {
        itsWin = true;
        System.out.println(winner(i) +"a gagné la partie !");
      }
    }
    return itsWin;
  }
  /**
   * Permet de récupérer le pseudo du gagnant
   * @param  playerId [ID du joueur]
   * @return          [Pseudo du joueur]
   */
  public String winner(int playerId){
    return p[playerId].getPseudo();
  }
  /**
   * Déroulement d'un tour
   */
  public void turn(){
    while(!win()){
      menu.main();
      changeTurnPlayers();
      m.getToken().tour();
      changeTurnPlayers();
    }
  }
}
