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

/**
 *
 * @author Cédric
 */

public class Game {
  public final static int nbPlayers = 2;
  protected Cell[][] map = new Cell [16][11];
  //Carte du jeu (Représentation en rectangle)
  protected Player[] p = new Player[nbPlayers];
  //Tableau de nos deux joueurs
  protected Monster m = new Monster();
  //Le montre
  protected ArrayList<Token> tokenOutside = new ArrayList<>();
  //Liste de token à l'extérieur
  protected ArrayList<TokenR> tokenR = new ArrayList<>();
  //Liste de bloc de pierre
  protected ArrayList<TokenP> tokenPWin = new ArrayList<>();
  //Liste des token ayant gagné la partie
  protected boolean turnPlayers = true; // Variable à retirer (inutile)
  //Permet de savoir si c'est au joueur ou non
  protected int nbTurn = 1;
  //Le nombre de tours

  /**
   * Returns map
   * @return
   */
  public Cell[][] getMap () {
    return map;
  }

  /**
   * Returns Players
   * @return
   */
  public Player getPlayer(int x) {
      return p[x];
  }

  public ArrayList<Token> getTokenOutside() {
    return tokenOutside;
  }

  public ArrayList<TokenR> getTokenR() {
    return tokenR;
  }

  public ArrayList<TokenP> getTokenPWin() {
    return tokenPWin;
  }

  public boolean isTurnPlayers() {
      return turnPlayers;
  }

  public void setTurnPlayers(boolean b) {
      this.turnPlayers = b;
  }

  /**
   * Returns value of nbTurn
   * @return
   */
  public int getNbTurn() {
      return nbTurn;
  }

  public void newTurn() {
      nbTurn ++;
  }

  public void iniMap(){
    for(int i = 0; i < 15; i++) {
      for (int j = 0; j < 10; j++) {
        map[i][j] = new Cell(i, j);
      }
    }

    System.out.println("La map a été initialisé");
  }

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

  public void iniDecorations(int i){
    System.out.println("Initialisation des décors pour la partie test.");
    map[0][9].setBloodspot();
    map[0][8].setBloodspot();
    map[0][7].setBloodspot();
    map[0][6].setBloodspot();
    tokenR.add(new TokenR(this, 1, 10));
  }

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

  public String winner(int playerId){
    return p[playerId].getPseudo();
  }

  public void turn(){
    while(!win()){
      for (int i = 0; i < p.length; i++) {

      }
    }
  }
}
