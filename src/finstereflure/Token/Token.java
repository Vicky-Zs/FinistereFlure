package Token;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aurelien
 */
public abstract class Token {
    protected int nbMove;
    protected int posX;
    protected int posY;
    protected Game myGame;
    
    /**
     * @change x and y with the current token
     */
    public abstract void move(int ordre);
    
    public boolean isInside()
    {
        if( (this.getPosX() >= 0 && this.getPosX() <= 15) && (this.getPosY() >= 0 && this.getPosY() <= 6) )
        {
            return true;
        }
        else if( ((this.getPosX() >= 0 && this.getPosX() <= 14) && this.getPosY() == 7) ||
                ((this.getPosX() >= 0 && this.getPosX() <= 13) && this.getPosY() == 8) ||
                ((this.getPosX() >= 0 && this.getPosX() <= 12) && this.getPosY() == 9) ||
                ((this.getPosX() >= 0 && this.getPosX() <= 11) && this.getPosY() == 10) ) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @return the posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @return the posY
     */
    public int getPosY() {
        return posY;
    }
   
    
}
