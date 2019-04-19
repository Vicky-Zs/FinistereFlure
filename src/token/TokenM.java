/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;
import map.*;
import finstereflure.*;
import character.*;
import java.util.ArrayList;

/**
 *
 * @author Aurelien
 */
public class TokenM extends Token {

    private int orientation;
    private ArrayList<Integer> nbMoves = new ArrayList<>();    // présence de doublons

    public TokenM (Game myGame) {
        super( myGame , 15 , 0);
        this.orientation = 3;
        this.nbMoves.add(-2);
        this.nbMoves.add(-1);
        this.nbMoves.add(5);
        this.nbMoves.add(7);
        this.nbMoves.add(7);
        this.nbMoves.add(8);
        this.nbMoves.add(8);
        this.nbMoves.add(10);
    }
    
    public TokenM (Game myGame, int x, int y) {
        super(myGame , x , y);
        this.orientation = 3;
        this.nbMoves.add(-2);
        this.nbMoves.add(-1);
        this.nbMoves.add(5);
        this.nbMoves.add(7);
        this.nbMoves.add(7);
        this.nbMoves.add(8);
        this.nbMoves.add(8);
        this.nbMoves.add(10);
    }

    private boolean isInside(){
        return ( (this.getPosX() >= 0 && this.getPosX() <= 15) && (this.getPosY() >= 0 && this.getPosY() <= 10) ) && ( this.posX + this.posY <= 21) ;
    }

    /**
     * @param direction
     * @change x and y with the current token
     * parametre is a reference of the direction
     * 0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest
     */
    @Override
    public void move(int direction) {

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
                    break;
                }
            }

        // Si la case suivante ne dépasse pas les limites du tableau...
        if( (new TokenM(super.myGame, destinationX, destinationY)).isInside() )
        {
            // s'il y a un Token dans la case suivante...
            if( this.myGame.getMap()[destinationX][destinationY].isTokenHere() )
            {
                // si ce Token est un bloc de pierre : ce dernier doit bouger
                if (this.find(destinationX, destinationY) instanceof TokenR )
                {
                    TokenR t = (TokenR) this.find(destinationX, destinationY);
                    t.move(direction, this);
                }
                else
                {
                    TokenP p = (TokenP) this.find(destinationX, destinationY);
                    p.die();
                }
            }

            // déplacement
            this.posX = destinationX;
            this.posY = destinationY;
        }
        else
        {
            if( this.orientation > 2 )
                this.orientation -= 2;
            else
                this.orientation += 2;

            this.move(this.orientation);
        }
    }

    /**
     * @controle all of the monster's phase
     */
    public void tour()  {

        rollDice();

        if( this.nbMove < 0 && this.myGame.getNbTurn() == 1 )
        {
            int maxMove = 0, die1 = TokenP.getVictime();
            while( this.nbMove == die1 - TokenP.getVictime() && maxMove <= 20)
            {
                this.move(look());

                // gestion de la flaque de sang
                while( this.myGame.getMap()[super.posX][super.posY].isBloodspot() )
                {
                    this.move(look());
                }

                maxMove++;
            }
        }
        else
        {
            while( this.nbMove > 0 )
            {
                this.move(look());

                // gestion de la flaque de sang
                while( this.myGame.getMap()[super.posX][super.posY].isBloodspot() )
                {
                    this.move(look());
                }

                this.nbMove--;
            }
        }

        look();
    }


    private int lookUp() {

        int result = 0;
        int x = this.posX;
        int y = this.posY + 1;

        while( this.myGame.getMap()[x][y - 1].getWall(0) == false )
        {
            if(this.myGame.getMap()[x][y].isTokenHere())
            {
                if(this.find(x, y) instanceof TokenP)
                {
                    return result;
                }
                else
                {
                    return 50;
                }
            }
            else
            {
                result++;
                y++;
            }
        }
        return 50;
    }

    private int lookDown() {
        int result = 0;
        int x = this.posX;
        int y = this.posY - 1;

        while( this.myGame.getMap()[x][y + 1].getWall(2) == false )
        {
            if(this.myGame.getMap()[x][y].isTokenHere())
            {
                if( this.find(x, y) instanceof TokenP )
                {
                    return result;
                }
                else
                {
                    return 50;
                }
            }
            else
            {
                result++;
                y++;
            }
        }
        return 50;
    }

    private int lookLeft() {
        int result = 0;
        int x = this.posX - 1;
        int y = this.posY;

        while( this.myGame.getMap()[x + 1][y].getWall(3) == false )
        {
            if(this.myGame.getMap()[x][y].isTokenHere())
            {
                if( this.find(x, y) instanceof TokenP )
                {
                    return result;
                }
                else
                {
                    return 50;
                }
            }
            else
            {
                result++;
                y++;
            }
        }
        return 50;
    }

    private int lookRight() {
        int result = 0;
        int x = this.posX + 1;
        int y = this.posY;

        while( this.myGame.getMap()[x - 1][y].getWall(1) == false )
        {
            if(this.myGame.getMap()[x][y].isTokenHere())
            {
                if( this.find(x, y) instanceof TokenP )
                {
                    return result;
                }
                else
                {
                    return 50;
                }
            }
            else
            {
                result++;
                y++;
            }
        }
        return 50;
    }

    private int look() {
        int up = 50, down = 0, left = 50, right = 50;

        switch(this.orientation) {
            case 0:
            {
                up = this.lookUp();
                left = this.lookLeft();
                right = this.lookRight();
                break;
            }

            case 1:
            {
                up = this.lookUp();
                down = this.lookDown();
                right = this.lookRight();
                break;
            }

            case 2:
            {
                down = this.lookDown();
                left = this.lookLeft();
                right = this.lookRight();
                break;
            }

            case 3:
            {
                up = this.lookUp();
                down = this.lookDown();
                left = this.lookLeft();
                break;
            }

            default:
            {
                break;
            }
        }

        // on détermine la distance minimale
        int min = up;
        if( min > down )    min = down;
        if( min > left )    min = left;
        if( min > right )    min = right;

        // on vérifie qu'il n'y qu'une seulle distance
        int recurence = 0;
        if( min == up )      recurence++;
        if( min == down )    recurence++;
        if( min == left )    recurence++;
        if( min == right )   recurence++;

        // puis on en défini l'orientation
        if( recurence == 1 )
        {
            if( min == up )      this.orientation = 0;
            if( min == down )    this.orientation = 2;
            if( min == left )    this.orientation = 1;
            if( min == right )   this.orientation = 3;
        }

        return this.orientation;
    }


    // tire un chiffre, le supprime ; refait nbMoves s'il ne reste qu'une tuile
    /**
     * @configure the current nbMove from the list nbMoves
     */
    private void rollDice() {

        if (this.nbMoves.size() == 1) {
            this.nbMoves.clear();
            this.nbMoves.add(-2);
            this.nbMoves.add(-1);
            this.nbMoves.add(5);
            this.nbMoves.add(7);
            this.nbMoves.add(7);
            this.nbMoves.add(8);
            this.nbMoves.add(8);
            this.nbMoves.add(10);
        }

        this.nbMove = this.nbMoves.remove( (int) (Math.random()*this.nbMoves.size()) );
    }

}
