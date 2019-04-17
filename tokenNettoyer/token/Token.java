/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;
import finstereflure.*;
import map.*;
import token.*;

/**
 *
 * @author aurelien
 */
public abstract class Token {
    protected static int id = 0;
    protected int reference;
    protected int nbMove;
    protected int posX;
    protected int posY;
    protected Game myGame;
    
    public Token (Game myGame , int x , int y) {
        this.myGame = myGame;
        this.posX = x;
        this.posY = y;
        this.reference = this.id;
        this.id++;
    }
    
    public int getReference()
    {
        return this.reference;
    }
    
    /**
     * @param direction
     * @change x and y with the current token
     */
    // Note : pourra être améliorer grâce à l'astuce des diagonales
    public abstract void move(int direction) ;
    
    // donne le Token recherché ; Renvoie null sinon... 
    // Note : à améliorer pour être comme IsInside
    // Ou bien : faire que ce soit Game qui puisse rendre le Token souhaité, en y rentrant que ses coordonnées X et Y (pourrait réduire les "accidents")
    /**
     * @param x
     * @param y
     * @return the Token on the board, from a given position or null if the search has no done
     */
    public Token find(int x , int y)
    {
        // postulat : chaque player a une LISTE de pions
            
        if( this.myGame.getPlayer()[0].isEmpty() == false )
        {
            for( Token p : this.myGame.getPlayer()[0].getToken() )
            {
                if( p.getPosX() == x && p.getPosY() == y )
                    return p;
            }
        }
        
        if( this.myGame.getPlayer()[1].isEmpty() == false )
        {
            for( Token p : this.myGame.getPlayer()[1].getToken() )
            {
                if( p.getPosX() == x && p.getPosY() == y )
                    return p;
            }
        }
        
        if( myGame.getListeTokenR().isEmpty() == false )
        {
            for( TokenR p : this.myGame.getListeTokenR() )
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
    protected int getPosX() {
        return posX;
    }

    /**
     * @return the posY
     */
    protected int getPosY() {
        return posY;
    }
   
    @Override
    public int hashCode() {
        return this.reference;
    }
    
    @Override
    public boolean equals(Object obj) {
        if( obj instanceof TokenM || obj instanceof TokenR || obj instanceof TokenP || obj instanceof Token ) {
            Token t = (Token)obj;
            return (this.getReference() == t.getReference());
        }
        else
            return false;
    }

}