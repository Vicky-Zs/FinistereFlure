/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import map.*;
import finstereflure.*;
import character.*;

/**
 *
 * @author aurelien
 */
public class TokenP extends Token {

    private boolean out, win;
    private int patternA, patternB, playerId;
    private static int victime = 0;

    public TokenP(Game myGame, int x, int y, int patterneA, int playerId) {
        super(myGame, -1, -1);
        this.playerId = playerId;
        this.patternA = patterneA;
        this.patternB = 7 - patterneA;
        this.out = true;
        this.win = false;
        setNbMove(true);
    }
    
    public TokenP(Game myGame, int x, int y) {
        super(myGame, x, y);
        this.playerId = playerId;
        this.patternA = 7;
        this.patternB = 7;
        this.out = false;
        this.win = false;
        setNbMove(true);
    }

    public static int getVictime() {
        return victime;
    }
    
    /**
     * @param phase
     * @set the nbMove of the current TokenP from its dual pattern true for
     * patternA , false for patternB ,
     */
    public void setNbMove(boolean phase) {
        if (phase) {
            this.nbMove = patternA;
        } else {
            this.nbMove = patternA;
        }
    }
    
    // indique si le TokenP peut passer son tour
    public boolean canStay()
    {
        return ( this.myGame.getMap()[this.getPosX()][this.getPosY()].isTokenHere() == false );
    }

    // indique si le TokenP peut se dépacer d'une case, vers une direction donnée
    public boolean canMove(int direction) {
        TokenP p = new TokenP( this.myGame , this.posX ,  this.posY );
        boolean flag = p.moveONE(direction);
        
        if( flag && this.nbMove > 0 )
        {
            if( this.myGame.getMap()[p.getPosX()][p.getPosY()].isBloodspot() )
            {
                while( this.myGame.getMap()[p.getPosX()][p.getPosY()].isBloodspot() && flag  )
                {
                    flag = p.moveONE(direction);
                }

                if( this.myGame.getMap()[p.getPosX()][p.getPosY()].isTokenHere() )
                    return (this.nbMove > 1);
                else
                {
                    return true;
                }
            }
            else
            {
                if( this.myGame.getMap()[p.getPosX()][p.getPosY()].isTokenHere() )
                    return (this.nbMove > 1);
                else
                {
                    return (this.nbMove > 0);
                }
            }
        }
        else
            return false;
    }

    /**
     * @translate the current TokenP, from the list of out pions, on the board
     */
    private void inGame() {
        if (this.myGame.getListeTokenPisOut().contains(this)) {
            this.myGame.getPlayer()[this.playerId].add(this.myGame.getListeTokenPisOut().remove(this.myGame.getListeTokenPisOut().indexOf(this)));
            this.out = false;
            this.posX = 0;
            this.posY = 0;
            this.myGame.getMap()[this.posX][this.posY].setTokenHere();
        }
    }

    /**
     * @translate the current TokenP to the list of winner TokenP
     */
    private boolean hasWin() {
        this.myGame.getMap()[this.posX][this.posY].setNotTokenHere();
        if (this.nbMove > 0 && this.posX == 0 && this.posY == 10) {
            this.myGame.getListeTokenPisWin(this.myGame.getPlayer()[this.playerId].remove(this.myGame.getPlayer()[this.playerId].indexOf(this)));
            this.out = false;
            this.win = true;
            this.posX = -1;
            this.posY = -1;
            return true;
        } else {
            return false;
        }
    }

    // déplace le TokenP de 1 case vers une direction donnée : indique s'il a réussit à se déplacer ou non
    private boolean moveONE(int direction) 
    {
        if (this.nbMove >= 1) 
        {
            // Coordonnées fictives de la prochaine case après le déplacement
            int destinationX = this.posX, destinationY = this.posY;
            switch (direction) {

                case 0: {
                    destinationX = this.posX;
                    destinationY = this.posY + 1;
                    break;
                }

                case 1: {
                    destinationX = this.posX + 1;
                    destinationY = this.posY;
                    break;
                }

                case 2: {
                    destinationX = this.posX;
                    destinationY = this.posY - 1;
                    break;
                }

                case 3: {
                    destinationX = this.posX - 1;
                    destinationY = this.posY;
                    break;
                }

                default: {
                    return false;
                }
            }
            
            // Gestion des colisions : s'il y a un Token...
            if (this.myGame.getMap()[destinationX][destinationY].isTokenHere()) 
            {
                // ...si ce Token est un bloc de pierre...
                if (this.find(destinationX, destinationY) instanceof TokenR) 
                {
                    TokenR t = (TokenR) super.find(destinationX, destinationY);

                    // ...si ce TokenR peut être poussé par un pion : ce TokenR doit bouger
                    if (t.canBePushByPion(direction)) {
                        // Token R poussé
                        t.move(direction, this);

                        // déplacement
                        this.posX = destinationX;
                        this.posY = destinationY;
                        
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    // ...si ce Token est un pion et qu'il reste plus de 1 point de mouvement : le pion peut se déplacer
                    if(this.find(destinationX, destinationY) instanceof TokenP)
                    {
                        if( this.nbMove > 1)
                        {
                            // déplacement
                            this.posX = destinationX;
                            this.posY = destinationY;

                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else    // ...si ce Token est un monstre : le pion ne peut pas se déplacer
                    {
                        return false;
                    }
                }
            }
            else
            {
                // S'il n'y a pas de mur : le pion peut se déplacer (exception pour les murs d'enceinte)
                if( this.myGame.getMap()[this.posX][this.posY].getWall(direction) == false || destinationX + destinationY == 4 )
                {
                    // déplacement
                    this.posX = destinationX;
                    this.posY = destinationY;
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * @param direction
     * @change x and y with the current token parametre is a reference of the
     * direction 0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest
     */
    @Override
    public void move(int direction) {
        if (this.isOut()) {
            this.inGame();
        } else if (this.hasWin() == false) {

            this.myGame.getMap()[this.posX][this.posY].setNotTokenHere();
            
            // si c'est le tour des joueurs
            if (this.myGame.IsPhaseJoueur()) 
            {
                if( this.canMove(direction) )
                {
                    boolean flag = this.moveONE(direction);
                    while( this.myGame.getMap()[this.posX][this.posY].isBloodspot() && flag )
                    {
                        flag = this.moveONE(direction);
                    }
                    this.nbMove--;
                }
                this.myGame.getMap()[this.posX][this.posY].setTokenHere();
                
            } 
            else    // si c'est le tour du Monstre, cela veut dire d'après les règles, qu'il est en train de se faire pousser par un bloc de pierre, par le monstre
            {
                // si le pion n'est pas bloqué par son déplacement : il bouge / sinon : il meurt
                if( this.moveONE(direction) )
                {
                    boolean flag = true;
                    while( this.myGame.getMap()[this.posX][this.posY].isBloodspot() && flag )
                    {
                        flag = this.moveONE(direction);
                    }
                    this.myGame.getMap()[this.posX][this.posY].setTokenHere();
                }
                else
                {
                    this.die();
                }
            }
            
        }

    }


    /**
     * @erase or destroy the current TokenP
     */
    public void die() 
    {
        // on comptabilise sa mort
        this.victime++;
        this.myGame.getListeTokenPisOut().add(this.myGame.getPlayer()[this.playerId].remove( this.myGame.getPlayer()[this.playerId].indexOf(this) ));

        // si on est dans la manche 2, tous les pions disqualiés seront retirés du jeu
        if ( this.myGame.getNbTour() > 7 ) 
        {
            this.myGame.getListeTokenPisOut().clear();
        }
        else
        {
            this.out = true;
            this.posX = -1;
            this.posY = -1;
        }
    }

    /**
     * @return the out
     */
    public boolean isOut() {
        return out;
    }

    /**
     * @return the win
     */
    public boolean isWin() {
        return win;
    }
}
