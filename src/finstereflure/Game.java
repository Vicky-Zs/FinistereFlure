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

public class Game {
  public final static int nbPlayers = 2;
  private Cell[][] map = new Cell [16][11];
  //Carte du jeu (Représentation en rectangle)
  private Player[] p = new Player[nbPlayers];
  //Tableau de nos deux joueurs
  private Monster m = new Monster();
  //Le montre
  private ArrayList<Token> tokenInside = new ArrayList<>();
  private ArrayList<Token> tokenOutside = new ArrayList<>();
  private int nbTurn = 1;
  //Le nombre de tours

  /**
   * Default empty Game constructor
   */
  public Game() {
  }
  
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

  public ArrayList<Token> getTokenInside() {
    return tokenInside;
  }
  
  public ArrayList<Token> getTokenOutside() {
    return tokenOutside;
  }
	/**
	* Returns value of nbTurn
	* @return
	*/
	public int getNbTurn() {
		return nbTurn;
	}

  public void iniMap(){
    for(int i = 0; i < 15; i++) {
      for (int j = 0; j < 10; j++) {
        map[i][j] = new Cell(i, j);
      }
    }
  }
}
