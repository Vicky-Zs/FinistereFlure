/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstereflure;

public class Game {
  private Cell[][] map = new Cell [16][11];
  //Carte du jeu (Représentation en rectangle)
  private Player[] p = new Player[2];
  //Tableau de nos deux joueurs
  private Monster m = new Monster();
  //Le montre
  private int nbTurn = 1;
  //Le nombre de tours

  /**
	* Default empty Game constructor
	*/
	public Game() {
	}

  public void iniMap(){
    for(int i = 0; i < 15; i++) {
      for (int j = 0; j < 10; j++) {
        if (i + j < 22){
          //On créer les cases seulement si elles existent
          //(les cases en haut à droite de la carte n'existe pas, ce sont des cases vides)
          map[i][j] = new Cell(i, j);
        }
      }
    }
  }
}
