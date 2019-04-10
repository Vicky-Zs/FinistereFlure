/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;
import character.*;
import finistereflure.*;
import map.*;
import java.util.ArrayList;

/**
 *
 * @author aurelien
 */
public class TokenM extends Token {

    int orientation;
    ArrayList<Integer> nbMove = new ArrayList<>();    // présence de doublons

    public TokenM(Game myGame , int x , int y)
    {
        super( myGame , x , y );
        this.orientation = 1;

        this.nbMove.add(-2);
        this.nbMove.add(-1);
        this.nbMove.add(5);
        this.nbMove.add(7);
        this.nbMove.add(7);
        this.nbMove.add(8);
        this.nbMove.add(8);
        this.nbMove.add(10);
    }


    /**
     * @param direction
     * @change x and y with the current token
     * parametre is a reference of the direction
     * 0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest
     */
    @Override
    public void move(int direction) {
        super.myGame.getMap()[super.posX][super.posY].setEmpty(false);
        int destinationX = 0, destinationY = 0; // cette déclaration permettra de détecter des problèmes
        switch (direction) {

            case 0: {
                destinationX = super.posX;
                destinationY = super.posY + 1;
                break;
            }

            case 1: {
                destinationX = super.posX + 1;
                destinationY = super.posY;
                break;
            }

            case 2: {
                destinationX = super.posX;
                destinationY = super.posY - 1;
                break;
            }

            case 3: {
                destinationX = super.posX - 1;
                destinationY = super.posY;

                break;
            }

            default: {
                break;
            }
        }


        if( (new TokenM(super.myGame, destinationX, destinationY)).isInside() )
        {
            if( super.myGame.getMap()[destinationX][destinationY].isEmpty() )
            {
                if (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenR") == 0)
                {
                    TokenR t = (TokenR) super.find(destinationX, destinationY);
                    t.move(direction, this);
                }

            }
            // déplacement
            super.posX = destinationX;
            super.posY = destinationY;
            // flaque de sang
            if (this.myGame.getMap()[super.posX][super.posY].special == 1) {
                if(super.find(destinationX, destinationY).getClass().getName().compareTo("TokenR") == 0)
                    super.find(destinationX, destinationY).move(direction);

                this.move(direction);
            }

        }
        else
        {
            if( this.orientation > 2 )
                this.orientation -= 2;
            else
                this.orientation += 2;

            this.move(this.orientation);
        }

        // dans tous les cas, s'il y a un pion sur sa case, ce dernier doit être retiré du jeu
        if( super.find(super.posX, super.posY).getClass().getName().compareTo("TokenP") == 0 )
        {
            TokenP p = (TokenP) super.find(super.posX, super.posY);
            p.die();
        }
        super.myGame.getMap()[super.posX][super.posY].setEmpty(true);
    }

    private int lookUp() {
        int result = 0;
        int x = super.posX;
        int y = super.posY + 1;

        while( (new TokenM(super.myGame, x, y)).isInside())
        {
            if(this.myGame.getMap()[x][y].isEmpty())
            {
                result++;
                y++;
            }
            else
            {
                return (result);
            }
        }

        return 20;
    }

    private int lookDown() {
        int result = 0;
        int x = super.posX;
        int y = super.posY - 1;

        while( (new TokenM(super.myGame, x, y)).isInside())
        {
            if(this.myGame.getMap()[x][y].isEmpty())
            {
                result++;
                y++;
            }
            else
            {
                return (result);
            }
        }

        return 20;
    }

    private int lookLeft() {
        int result = 0;
        int x = super.posX - 1;
        int y = super.posY;

        while( (new TokenM(super.myGame, x, y)).isInside())
        {
            if(this.myGame.getMap()[x][y].isEmpty())
            {
                result++;
                y++;
            }
            else
            {
                return (result);
            }
        }

        return 20;
    }

    private int lookRight() {
        int result = 0;
        int x = super.posX + 1;
        int y = super.posY;

        while( (new TokenM(super.myGame, x, y)).isInside())
        {
            if(this.myGame.getMap()[x][y].isEmpty())
            {
                result++;
                y++;
            }
            else
            {
                return (result);
            }
        }

        return 20;
    }

    private int look() {
        int up = 20, down = 20, left = 20, right = 20;

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


    // tire un chiffre, le supprime ; refait nbMove s'il ne reste qu'une tuile
    private int rollDice() {

        if (this.nbMove.size() == 1) {
            this.nbMove.clear();
            this.nbMove.add(-2);
            this.nbMove.add(-1);
            this.nbMove.add(5);
            this.nbMove.add(7);
            this.nbMove.add(7);
            this.nbMove.add(8);
            this.nbMove.add(8);
            this.nbMove.add(10);
        }

        return this.nbMove.remove( (int) (Math.random()*this.nbMove.size()) );
    }


    public void tour()  {

        int nb = rollDice();

        if ( nb > 0 )
        {
            while( nb > 0 )
            {
                this.move(look());
                nb--;
            }
        }
        else
        {
            int maxMove = 0, die1 = TokenP.getVictime();
            while( nb == die1 - TokenP.getVictime()  && maxMove <= 20)
            {
                this.move(look());
                maxMove++;
            }
        }

        look();
    }
}
