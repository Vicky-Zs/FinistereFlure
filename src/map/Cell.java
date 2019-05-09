/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;
import character.*;
import finstereflure.*;
import token.*;
/**
 *
 * @author Cédric
 */

public class Cell {
  private boolean bloodspot;
  //Permet de savoir si cette case est une flaque de sang
  private boolean tokenHere;
  //Permet de savoir si un pion se trouve sur cette case
  private boolean[] wall = new boolean[4];
  //Permet de savoir où se trouve un mur
  //(0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest)

	/**
	* Default empty Cell constructor
	*/
	public Cell(int x, int y) { //TODO: Ne pas créer les 6 cases en dehors de la map
		this.bloodspot = false;
		this.tokenHere = false;
		this.wall[0] = false;
    this.wall[1] = false;
    this.wall[2] = false;
    this.wall[3] = false;
    //Mur en ligne droite
    if (y == 0) {
      this.wall[2] = true;
    }
    if (x == 0) {
      this.wall[3] = true;
    }
    if ((x==15) && (y<7)){
      this.wall[1] = true;
    }
    if ((x<12) && (y==10)){
      this.wall[0] = true;
    }
    //Mur en Diagonal
    if (x+y == 3){
      this.wall[0] = true;
      this.wall[1] = true;
    }
    if (x+y == 4) {
      this.wall[2] = true;
      this.wall[3] = true;
    }
    if (x+y == 21) {
      this.wall[0] = true;
      this.wall[1] = true;
    }
	}

	/**
	* Returns value of bloodspot
	* @return
	*/
	public boolean isBloodspot() {
		return this.bloodspot;
	}

	/**
	* Sets new value of bloodspot
	* @param
	*/
	public void setBloodspot() {
		this.bloodspot = true;
	}

	public void setNotBloodspot() {
		this.bloodspot = false;
	}

	/**
	* Returns value of tokenHere
	* @return
	*/
	public boolean isTokenHere() {
    return tokenHere;
	}

	/**
	* Sets true for tokenHere
	* @param
	*/
	public void setTokenHere() {
		this.tokenHere = true;
	}

	/**
	* Sets false for tokenHere
	* @param
	*/
	public void setNotTokenHere() {
		this.tokenHere = false;
	}

	/**
	* Returns value of n
	* @return
	*/
	public boolean getWall(int i) {
		if ((i>-1) && (i<5)){
			return wall[i];
		}
		else {
			System.out.println("Erreur sur la gestion des murs");
			return false;
		}
	}
}
