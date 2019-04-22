package token;
import map.*;
import finstereflure.*;
import character.*;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aurelien
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
    
    public Token (Game myGame , int x , int y, int nbMove) { //Création d'un token temporaire
        this.myGame = myGame;
        this.posX = x;
        this.posY = y;
        this.nbMove = nbMove;
        this.reference = -1;
    }

    public int getReference(){
        return this.reference;
    }

    /**
     * @param direction
     * @change x and y with the current token
     */
    // Note : pourra être améliorer grâce à l'astuce des diagonales
    public abstract void move(int direction);

    protected boolean isInside(){
        return ( (this.getPosX() >= 0 && this.getPosX() <= 15) && (this.getPosY() >= 0 && this.getPosY() <= 10) ) && ( this.posX + this.posY <= 21) ;
    }
    
    
    /**
     * @return true when the current Token is in the board
     */
    /*private boolean isInside()
    {
        if((this.getPosX() >= 0 && this.getPosX() <= 15) && (this.getPosY() >= 0 && this.getPosY() <= 6)){
            return true;
        }
        else if(((this.getPosX() >= 0 && this.getPosX() <= 14) && this.getPosY() == 7) ||
                ((this.getPosX() >= 0 && this.getPosX() <= 13) && this.getPosY() == 8) ||
                ((this.getPosX() >= 0 && this.getPosX() <= 12) && this.getPosY() == 9) ||
                ((this.getPosX() >= 0 && this.getPosX() <= 11) && this.getPosY() == 10)){
            return true;
        }
        else{
            return false;
        }
    }*/
    // donne le Token recherché ; Renvoie null sinon...
    // Note : à améliorer pour être comme IsInside
    // Ou bien : faire que ce soit Game qui puisse rendre le Token souhaité, en y rentrant que ses coordonnées X et Y (pourrait réduire les "accidents")
    /**
     * @param x
     * @param y
     * @return the Token from a given position or null if the search has no done
     */
    public Token find(int x , int y){
        // postulat : chaque player a une LISTE de pions
        Iterator<Token> iterator;
        for (int i = 0; i < Game.nbPlayers; i++) {
            iterator = myGame.getPlayer(i).getToken().iterator();
            if(myGame.getPlayer(i).isEmpty() == false ){
                while (iterator.hasNext()){
                    Token p = iterator.next();
                    if((p.getPosX() == x) && (p.getPosY() == y)){
                        return p;
                    }
                }
            }
        }
        if(!myGame.getTokenR().isEmpty()){
            for(TokenR t : myGame.getTokenR()){
                if( t.getPosX() == x && t.getPosY() == y ){
                    return t;
                }
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
    
    public int getNbMove(){
        return nbMove;
    }

    // Note : penser à Override equal() et hashcode() afin de rendre les méthodes .get(Object) .contain(Object) des listes opérationnelles
    // et éviter les recherches par boucles for
}
