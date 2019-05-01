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
 * @author Aurélien
 */
public class TokenP extends Token {

    private boolean out, win;
    private int patternA, patternB, playerId;
    private static int victime = 0;

    public TokenP(Game myGame, int patterneA, int playerId) {
        super(myGame, -1, -1); //Covention: -1/-1 = en dehors de la carte
        this.playerId = playerId;   // permet de savoir si le pion appartient au Joueur 1 ou 2
        this.patternA = patterneA;
        this.patternB = Math.abs(7 - patterneA); //Retourne la valeur absolue
        this.out = true; // Peut-être inutile puisque l'on a la liste Outside
        this.win = false;
        setNbMove(true);
        myGame.getTokenOutside().add(this);
    }

    public TokenP(Game myGame, int posX, int posY, int nbMove) {
        super(myGame, posX, posY); //Constructeur pour tester les cases
        this.nbMove = nbMove;
        this.playerId = -1;
        this.out = false;
        this.win = false;
        // setNbMove(true);
        // myGame.getTokenOutside().add(this);
    }

    /*public TokenP(Game myGame) {
        super(myGame, -1, -1);
        this.playerId = playerId;
        this.patternA = 7;
        this.patternB = 7;
        this.out = false;
        this.win = false;
        setNbMove(true);
    }*/
    public static int getVictime() {
        return victime;
    }

    /**
     * @param phase
     * @set the nbMove of the current TokenP from its dual pattern true for
     * patternA , false for patternB ,
     */
    public void setNbMove(boolean phase) { // doit se mettre après le tour du joueur !
        if (phase) {
            this.nbMove = patternA;
        } else {
            this.nbMove = patternB;
        }
    }

    // indique si le TokenP peut passer son tour
    public boolean canStay() {
        return (this.myGame.getMap()[this.getPosX()][this.getPosY()].isTokenHere() == false);
    }

    // Indique si le TokenP peut se dépacer d'une case, vers une direction donnée
    public boolean canMove(int direction, TokenP p) {
        TokenP t = new TokenP(p.myGame, p.posX, p.posY, p.getNbMove());
        boolean flag = t.moveONE(direction);
        if ((flag) && (this.nbMove > 0)) {
            if (this.myGame.getMap(t.getPosX(), t.getPosY()).isBloodspot()) {
                while ((this.myGame.getMap(t.getPosX(), t.getPosY()).isBloodspot()) && (flag)) {
                    flag = t.moveONE(direction);
                }
                if (this.myGame.getMap(t.getPosX(), t.getPosY()).isTokenHere()) {
                    return (this.nbMove > 1);
                } else {
                    return true;
                }
            } else {
                if (this.myGame.getMap(t.getPosX(), t.getPosY()).isTokenHere()) {
                    return (this.nbMove > 1);
                } else {
                    return (this.nbMove > 0);
                }
            }
        } else {
            return false;
        }
    }

    /**
     * @translate the current TokenP, from the list of out pions, on the board
     */
    private void inGame() {
        if (this.myGame.getTokenOutside().contains(this)) {
            this.myGame.getPlayer(this.playerId).getToken().add(this.myGame.getTokenOutside().remove(this.myGame.getTokenOutside().indexOf(this)));
            this.out = false;
            this.setPosX(15);
            this.setPosY(0);
            this.myGame.getMap()[this.posX][this.posY].setTokenHere();
        }
    }

    /**
     * @translate the current TokenP to the list of winner TokenP
     */
    private void hasWin() {
        this.myGame.getMap()[this.posX][this.posY].setNotTokenHere();
        this.myGame.getTokenPWin().add(this);
        this.myGame.getPlayer(this.playerId).getToken().remove(this);
        this.out = false;
        this.win = true;
        this.setPosX(-1);
        this.setPosY(-1);
    }

    // déplace le TokenP de 1 case vers une direction donnée : indique s'il a réussit à se déplacer ou non
    private boolean moveONE(int direction) {

        if (this.nbMove > 0) 
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

            // Gestion des murs : s'il y a un mur, le mouvement est impossible.
            if (this.myGame.getMap()[this.posX][this.posY].getWall(direction) == false || destinationX + destinationY == 4)
            {
                // Gestion des colisions : s'il y a un Token...
                if (this.myGame.getMap(destinationX, destinationY).isTokenHere()) 
                {
                    // ...si ce Token est un bloc de pierre...
                    if (this.find(destinationX, destinationY) instanceof TokenR) 
                    {
                        TokenR t = (TokenR) super.find(destinationX, destinationY);
                        // ...si ce TokenR peut être poussé par un pion : ce TokenR doit bouger
                        if (t.canBePushByPion(direction)) 
                        {
                            // Token R poussé
                            t.move(direction, this);

                            // Déplacement
                            this.setPosX(destinationX);
                            this.setPosY(destinationY);

                            return true;
                        } 
                        else 
                        {
                            return false;
                        }
                    } 
                    else 
                    {
                        // ...si ce Token est un pion
                        if (this.find(destinationX, destinationY) instanceof TokenP) 
                        {
                            // ...s'il reste plus de 1 point de mouvement : le pion peut se déplacer
                            if (this.nbMove > 1) {
                                // Déplacement
                                this.setPosX(destinationX);
                                this.setPosY(destinationY);

                                return true;
                            } 
                            else 
                            {
                                return false;
                            }
                        } 
                        else 
                        {    // ...si ce Token est alors un monstre : le pion ne peut pas se déplacer
                            return false;
                        }
                    }
                }
                else    // s'il n'y a ni mur, ni Token : le pion se déplace librement
                {
                    // Déplacement
                    this.setPosX(destinationX);
                    this.setPosY(destinationY);
                    return true;
                }
            }
            else
            {
                return false;
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
        } // si les conditions de victoires n'ont pas été remplis : le pion bouge
        else if (((this.nbMove > 0) && (this.posX == 0) && (this.posY == 10) && direction == 0) == false) {
            this.myGame.getMap(this.posX, this.posY).setNotTokenHere();
            // Si c'est le tour des joueurs
            if (this.myGame.isTurnPlayers()) {
                if (this.canMove(direction, this)) {
                    boolean flag = this.moveONE(direction);
                    while ((this.myGame.getMap(this.posX, this.posY).isBloodspot()) && (flag)) {
                        flag = this.moveONE(direction);
                    }
                    this.nbMove--;
                }
                this.myGame.getMap(this.posX, this.posY).setTokenHere();
            } else { // Si c'est le tour du Monstre, cela veut dire d'après les règles, qu'il est en train de se faire pousser par un bloc de pierre, par le monstre
                // Si le pion n'est pas bloqué par son déplacement : il bouge 
                // Sinon : il meurt
                if (this.moveONE(direction)) {
                    boolean flag = true;
                    while (this.myGame.getMap()[this.posX][this.posY].isBloodspot() && flag) {
                        flag = this.moveONE(direction);
                    }
                    this.myGame.getMap()[this.posX][this.posY].setTokenHere();
                } else {
                    this.die();
                }
            }
        } else {
            this.hasWin();
        }
    }

    /**
     * @erase or destroy the current TokenP
     */
    public void die() {
        // on comptabilise sa mort
        this.victime++;
        this.myGame.getTokenOutside().add(this);
        this.myGame.getPlayer(this.playerId).getToken().remove(this);

        // si on est dans la manche 2, tous les pions disqualiés seront retirés du jeu
        if (this.myGame.getNbTurn() > 7) {
            this.myGame.getTokenOutside().clear();
        } else {
            this.out = true;
            this.setPosX(-1);
            this.setPosY(-1);
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

    public int getPlayerId() {
        return playerId;
    }

    public int getPatternA() {
        return patternA;
    }

    public int getPatternB() {
        return patternB;
    }
    
    @Override
    public String toString(){
        return "Le pion " + "{" + this.patternA + "/" + this.patternB + "}" +" est à la position ["+this.posX+";"+this.posY+"]";
    }
}
