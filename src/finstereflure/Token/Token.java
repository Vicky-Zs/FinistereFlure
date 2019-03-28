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
        if( (this.posX >= 0 && this.posX <= 15) && (this.posY >= 0 && this.posY <= 6) )
        {
            return true;
        }
        else if( ((this.posX >= 0 && this.posX <= 14) && this.posY == 7) ||
                ((this.posX >= 0 && this.posX <= 13) && this.posY == 8) ||
                ((this.posX >= 0 && this.posX <= 12) && this.posY == 9) ||
                ((this.posX >= 0 && this.posX <= 11) && this.posY == 10) ) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
   
    
}
