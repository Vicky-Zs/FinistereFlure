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
 * @author Aurelien
 */
public class TokenR extends Token {

    public TokenR (Game myGame , int x , int y) {
        super( myGame , x , y );
        myGame.getMap(x, y).isTokenHere();
    }

    // l'acteur du déplacement est un monstre...
    private boolean moveByMonster(int direction){

        // si le bloc de pierre poussé par le monstre, recontre un mur : le bloc de Pierre doit être détruit
        if(this.myGame.getMap()[this.posX][this.posY].getWall(direction)){
            this.myGame.getTokenR().remove(this.myGame.getTokenR().indexOf(this));
        }
        else{
            // Coordonnées fictives de la prochaine case après le déplacement
            int destinationX = 0, destinationY = 0;
            switch (direction) {
              case 0:
              destinationX = this.posX;
              destinationY = this.posY + 1;
              break;
              case 1:
              destinationX = this.posX + 1;
              destinationY = this.posY;
              break;
              case 2:
              destinationX = this.posX;
              destinationY = this.posY - 1;
              break;
              case 3:
              destinationX = this.posX - 1;
              destinationY = this.posY;
              break;
              default:
              return false;
            }
            // si la prochaine case est occupé par un Token : ce dernier doit bouger
            if(this.myGame.getMap()[destinationX][destinationY].isTokenHere()){
                this.find(destinationX, destinationY).move(direction);
            }
            // dans tous les cas, le bloc de pierre doit bouger
            this.setPosX(destinationX);
            this.setPosY(destinationY);
        }
        return true;
    }

    // l'acteur du déplacement est un pion...
    private boolean moveByPion(int direction){
        // si le bloc de pierre poussé par le pion, recontre un mur : le bloc de Pierre ne peut pas bouger
        if(this.myGame.getMap()[this.posX][this.posY].getWall(direction)){
            return false;
        }
        else{
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

            // si la prochaine case est occupé par un Token : le bloc de pierre ne peut pas bouger
            // sinon le bloc de pierre peut bouger normalement
            if(this.myGame.getMap()[destinationX][destinationY].isTokenHere()){
                return false;
            }
            else{
                this.setPosX(destinationX);
                this.setPosY(destinationY);
                return true;
            }
        }
    }


    /**
     * @param direction
     * @change x and y with the current token
     * parametre is a reference of the direction
     * 0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest
     */
    @Override
    public void move(int direction) {
        this.myGame.getMap()[super.posX][super.posY].setNotTokenHere();
        this.move(direction, (new TokenP(super.myGame , super.posX , super.posY)) );
        this.myGame.getMap()[super.posX][super.posY].setTokenHere();
    }

    public void move(int direction, Token acteur) {
        this.myGame.getMap()[super.posX][super.posY].setNotTokenHere();
        boolean flag;

        if(acteur instanceof TokenM){
            flag = this.moveByMonster(direction);
        }
        else{
            flag = this.moveByPion(direction);
        }

        // gestion de la flaque de sang
        while(this.myGame.getMap()[super.posX][super.posY].isBloodspot() && flag){
            flag = this.moveByPion(direction);
        }
        super.myGame.getMap()[super.posX][super.posY].setTokenHere();
    }

    public boolean canBePushByPion(int direction){
        switch (direction)  {
            case 0:{
                return (this.myGame.getMap()[this.posX][this.posY + 1].isTokenHere() == false || this.myGame.getMap()[this.posX][this.posY].getWall(direction) == false);
            }
            case 1:{
                return (this.myGame.getMap()[super.posX + 1][super.posY].isTokenHere() == false || this.myGame.getMap()[this.posX][this.posY].getWall(direction) == false);
            }
            case 2:{
                return (this.myGame.getMap()[super.posX][super.posY - 1].isTokenHere() == false || this.myGame.getMap()[this.posX][this.posY].getWall(direction) == false);
            }
            case 3:{
                return (this.myGame.getMap()[super.posX - 1][super.posY].isTokenHere() == false || this.myGame.getMap()[this.posX][this.posY].getWall(direction) == false);
            }
            default:{
                return false;
            }
        }
    }

}
