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
		//Mur uniquement au Nord
		if (((x > 0) && (x<11)) && (y==10)){
			this.wall[0] = true;
			this.wall[1] = false;
			this.wall[2] = false;
			this.wall[3] = false;
		}
		//Mur uniquement à l'Est
		else if ((x==15) && ((y>0) && (y<6))){
			this.wall[0] = false;
			this.wall[1] = true;
			this.wall[2] = false;
			this.wall[3] = false;
		}
		//Mur uniquement au Sud
		else if (( ((x==1)||(x==2)) || ((x>4) && (x<15)) ) && (y == 0)){
			this.wall[0] = false;
			this.wall[1] = false;
			this.wall[2] = true;
			this.wall[3] = false;
		}
		//Mur uniquement à l'Ouest
		else if ((x == 0) && (((y==1)||(y==2)) || ((x>4) && (x<10))) ){
			this.wall[0] = false;
			this.wall[1] = false;
			this.wall[2] = false;
			this.wall[3] = true;
		}
		//Mur au Nord et à l'Est
		else if((x+y == 21) || ((x==1) && (y==2)) || ((x==2)&&(y==1))){
			this.wall[0] = true;
			this.wall[1] = true;
			this.wall[2] = false;
			this.wall[3] = false;
		}
		//Mur au Nord et à l'Ouest
		else if ((x==0) && (y == 10)) {
			this.wall[0] = true;
			this.wall[1] = false;
			this.wall[2] = false;
			this.wall[3] = true;
		}
		//Mur au Sud et à l'Est
		else if ((x==15)&&(y==0)) {
			this.wall[0] = false;
			this.wall[1] = true;
			this.wall[2] = true;
			this.wall[3] = false;
		}
		//Mur au Sud et à l'Ouest
		else if ((x==15)&&(y==0)) {
			this.wall[0] = false;
			this.wall[1] = false;
			this.wall[2] = true;
			this.wall[3] = true;
		}
		//Mur à l'Ouest, au Nord et à l'Est
		else if ((x==0) && (y == 3)) {
			this.wall[0] = true;
			this.wall[1] = true;
			this.wall[2] = false;
			this.wall[3] = true;
		}
		//Mur au Nord, à l'Est et au Sud
		else if ((x==3) && (y == 0)) {
			this.wall[0] = true;
			this.wall[1] = true;
			this.wall[2] = true;
			this.wall[3] = false;
		}
		else{
			this.wall[0] = false;
			this.wall[1] = false;
			this.wall[2] = false;
			this.wall[3] = false;
		}
	}


	/**
	* Returns value of bloodspot
	* @return
	*/
	public boolean isBloodspot() {
		return bloodspot;
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
			System.out.println("Erreur");
			System.exit(1);
			return false;
		}
	}
}
