/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;
import map.*;
import finstereflure.*;
import character.*;
//import java.util.Collections;
import java.util.ArrayList;
import static finstereflure.Main.g;

/**
 *
 * @author Aurelien
 */
public class TokenM extends Token {

    private int orientation;
    private ArrayList<Integer> nbMoves = new ArrayList<>();    // présence de doublons
    private boolean firstTurn = true;

    public TokenM (Game myGame) {
        super(myGame, 0, 10);
        this.orientation = 1;
        this.nbMoves.add(-2);
        this.nbMoves.add(-1);
        this.nbMoves.add(5);
        this.nbMoves.add(7);
        this.nbMoves.add(7);
        this.nbMoves.add(8);
        this.nbMoves.add(8);
        this.nbMoves.add(10);
    }
    
    public TokenM (Game myGame, int x, int y, int direction) {
        super(myGame , x , y);
        this.orientation = direction;
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
        
        // return ( (this.posX >= 0 && this.posX <= 15) && (this.getPosY() >= 0 && this.getPosY() <= 10) ) && ( this.posX + this.posY <= 21) ;
    }
    
    /**
     * @param direction
     * @change x and y with the current token
     * parametre is a reference of the direction
     * 0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest
     */
    @Override
    public void move(int direction) {
        System.out.println("Avant : posX = " + this.posX + " posY = " + this.posY);
        // try
        // {
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
        System.out.println("Destination : posX = " + destinationX + " posY = " + destinationY);
        // Si la case suivante ne dépasse pas les limites du tableau...
        if( (new TokenM(this.myGame, destinationX, destinationY, direction)).isInside() )
        {
            // s'il y a un Token dans la case suivante...
            if( g.getMap(destinationX, destinationY).isTokenHere() )
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
            if( this.orientation >= 2 )
                this.orientation -= 2;
            else
                this.orientation += 2;

            this.move(this.orientation);
        }
        
        /*}
        catch (NullPointerException e)
        {
            System.out.println("Mouvement impossible");
        }
        */
        System.out.println("Après : posX = " + this.posX + " posY = " + this.posY );
    }

    /**
     * @controle all of the monster's phase
     */
    public void tour()  {
        g.getMap(this.posX, this.posY).setNotTokenHere();
        rollDice();
        
        if(this.nbMove < 0)
        {
            int maxMove = 0, die1 = TokenP.getVictime();
            while(this.nbMove == die1 - TokenP.getVictime() && maxMove <= 20)
            {
                this.move(look());
                // gestion de la flaque de sang
                while(this.myGame.getMap(this.posX, this.posY).isBloodspot()){
                    this.move(look());
                }
                maxMove++;
            }
        }
        else
        {
            while(this.nbMove > 0){
                // System.out.println("Test 3");
                this.move(look());
                // System.out.println("Test 4");
                // gestion de la flaque de sang
                
                while(g.getMap(this.posX, this.posY).isBloodspot())
                {
                    this.move(look());
                }
                this.nbMove--;
            }
        }
        look();
        this.firstTurn = false;
        g.getMap(this.posX, this.posY).setTokenHere();
    }


    private int lookUp() {
        int result = 0;
        int x = this.posX;
        int y = this.posY + 1;
        
        try
        {
            while (!this.myGame.getMap(x,y-1).getWall(0)) 
            {
                // System.out.println("Test");
                if (this.myGame.getMap(x, y).isTokenHere()) 
                {
                    if (this.find(x, y) instanceof TokenP) 
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
        catch (NullPointerException e)
        {
            return 50;
        }
    }

    private int lookDown() {
        int result = 0;
        int x = this.posX;
        int y = this.posY - 1;
        
        try
        {
            // System.out.println(x+" "+y);
            while (!this.myGame.getMap(x, y + 1).getWall(2))    // la syntaxe de getMap a changé ?
            {
                // System.out.println("Test");
                if (this.myGame.getMap(x, y).isTokenHere()) 
                {
                    if (this.find(x, y) instanceof TokenP) 
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
                    y--;
                }
            }
            return 50;
        }
        catch (NullPointerException e)
        {
            return 50;
        }
    }

    private int lookLeft() {
        int result = 0;
        int x = this.posX - 1;
        int y = this.posY;

        try
        {
            while (!this.myGame.getMap()[x + 1][y].getWall(3)) // c'était pas this.myGame.getMap(x + 1, y ).getWall(3) ?
            {
                if (this.myGame.getMap()[x][y].isTokenHere()) 
                {
                    if (this.find(x, y) instanceof TokenP) 
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
                    x--;
                }
            }
            return 50;
        }
        catch (NullPointerException e)
        {
            return 50;
        }
    }

    private int lookRight() {
        int result = 0;
        int x = this.posX + 1;
        int y = this.posY;

        try
        {
            while (!this.myGame.getMap()[x - 1][y].getWall(1)) 
            {
                if (this.myGame.getMap()[x][y].isTokenHere()) 
                {
                    if (this.find(x, y) instanceof TokenP) 
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
                    x++;
                }
            }
            return 50;
        }
        catch (NullPointerException e)
        {
            return 50;
        }
    }

    // 0 = Nord, 1 = Est (droite / right), 2 = Sud, 3 = Ouest (gauche / left)
    private int look() {
        int up = 50, down = 50, left = 50, right = 50;
        
        switch(this.orientation) {
            case 0:{
                up = this.lookUp();
                left = this.lookLeft();
                right = this.lookRight();
                break;
            }
            case 1:{
                up = this.lookUp();
                // System.out.println("Test 1.1");
                down = this.lookDown();
                // System.out.println("Test 1.2");
                right = this.lookRight();
                // System.out.println("Test 1.3");
                break;
            }
            case 2:{
                down = this.lookDown();
                left = this.lookLeft();
                right = this.lookRight();
                break;
            }
            case 3:{                
                up = this.lookUp();                
                down = this.lookDown();                
                left = this.lookLeft();
                break;
            }
            default:{
                break;
            }
        }
        
        // si quelque chose a été repéré...
        if( up + down + left + right != 50*4 )
        {
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
            if(recurence == 1)
            {
                if(min == up)      this.orientation = 0;
                if(min == down)    this.orientation = 2;
                if(min == left)    this.orientation = 1;
                if(min == right)   this.orientation = 3;
            }
        }
        // System.out.println("up : " + up + " ; right : " + right + " ; down : " + down + " ; left : " + left);
        System.out.println("Orientation : " + orientation);
        return this.orientation;
    }


    // tire un chiffre, le supprime ; refait nbMoves s'il ne reste qu'une tuile
    /**
     * @configure the current nbMove from the list nbMoves
     */
    private void rollDice() {
        
        int id = (int) (Math.random()*this.nbMoves.size());
        if (this.nbMoves.size() == 1) 
        {
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
        
        // Au premier tour, les cartes "1 mort" et "2 morts" ne peuvent pas être pioché : on repioche alors si on tombe sur cela
        if( this.nbMoves.get(id) < 0 && this.firstTurn )
        {
            while(this.nbMoves.get(id) < 0)
            {
                id = (int) (Math.random()*this.nbMoves.size());
            }
        }
        
        this.nbMove = (int) this.nbMoves.remove(id);
    }

}
