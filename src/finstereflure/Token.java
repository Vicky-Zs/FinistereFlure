
package finstereflure;

public abstract class Token {
  protected int nbMove;
  protected int posX;
  protected int posY;

  protected abstract void move(int nbMove);



	/**
	* Returns value of nbMove
	* @return
	*/
	public int getNbMove() {
		return nbMove;
	}

	/**
	* Sets new value of nbMove
	* @param
	*/
	public void setNbMove(int nbMove) {
		this.nbMove = nbMove;
	}

	/**
	* Returns value of posX
	* @return
	*/
	public int getPosX() {
		return posX;
	}

	/**
	* Sets new value of posX
	* @param
	*/
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	* Returns value of posY
	* @return
	*/
	public int getPosY() {
		return posY;
	}

	/**
	* Sets new value of posY
	* @param
	*/
	public void setPosY(int posY) {
		this.posY = posY;
	}
}
