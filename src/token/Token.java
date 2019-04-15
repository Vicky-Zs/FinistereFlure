package token;
import map.*;
import finstereflure.*;
import character.*;

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
    
    public Token (Game myGame , int x , int y) {
        this.myGame = myGame;
        this.posX = x;
        this.posY = y;
    }
    
    
    /**
     * @param direction
     * @change x and y with the current token
     */
    // Note : pourra être améliorer grâce à l'astuce des diagonales
    public abstract void move(int direction);
    
    /**
     * @return true when the current Token is in the board
     */
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

    // donne le Token recherché ; Renvoie null sinon... 
    // Note : à améliorer pour être comme IsInside
    // Ou bien : faire que ce soit Game qui puisse rendre le Token souhaité, en y rentrant que ses coordonnées X et Y (pourrait réduire les "accidents")
    /**
     * @param x
     * @param y
     * @return the Token from a given position or null if the search has no done
     */
    public Token find(int x , int y)
    {
        // postulat : chaque player a une LISTE de pions
        
        if( myGame.getPlayer1().isEmpty() == false )
        {
            for( Token p : myGame.getPlayer1().getPions() )
            {
                if( p.getPosX() == x && p.getPosY() == y )
                    return p;
            }
        }
        
        if( myGame.getPlayer2().isEmpty() == false )
        {
            for( Token p : myGame.getPlayer2().getPions() )
            {
                if( p.getPosX() == x && p.getPosY() == y )
                    return p;
            }
        }
        
        if( myGame.getListeTokenR().isEmpty() == false )
        {
            for( TokenR p : myGame.getListeTokenR() )
            {
                if( p.getPosX() == x && p.getPosY() == y )
                    return p;
            }
        }
        
        return null;
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
   
    // Note : penser à Override equal() et hashcode() afin de rendre les méthodes .get(Object) .contain(Object) des listes opérationnelles 
    // et éviter les recherches par boucles for
}
