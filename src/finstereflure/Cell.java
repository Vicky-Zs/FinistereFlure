/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstereflure;

/**
 *
 * @author Cédric DARROU
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
	public Cell(int x, int y) {
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
		if ((x==15) && ((y>0) && (y<6))){
			this.wall[0] = false;
			this.wall[1] = true;
			this.wall[2] = false;
			this.wall[3] = false;
		}
		//Mur uniquement au Sud
		if (( ((x==1)||(x==2)) || ((x>4) && (x<15)) ) && (y == 0)){
			this.wall[0] = false;
			this.wall[1] = false;
			this.wall[2] = true;
			this.wall[3] = false;
		}
		//Mur uniquement à l'Ouest
		if ((x == 0) && (((y==1)||(y==2)) || ((x>4) && (x<10))) ){
			this.wall[0] = false;
			this.wall[1] = false;
			this.wall[2] = false;
			this.wall[3] = true;
		}
		//Mur uniquement au Nord-Ouest
		if ((x==0) && (y == 10)) {
			
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
	public void setBloodspot(boolean bloodspot) {
		this.bloodspot = bloodspot;
	}

	/**
	* Returns value of tokenHere
	* @return
	*/
	public boolean isTokenHere() {
		return tokenHere;
	}

	/**
	* Sets new value of tokenHere
	* @param
	*/
	public void setTokenHere(boolean tokenHere) {
		this.tokenHere = tokenHere;
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
