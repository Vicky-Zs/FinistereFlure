/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Token;

/**
 *
 * @author aurelien
 */
public class TokenP extends Token {

    private int patterneA, patterneB;
    private static int victime = 0;
    private String playerId;

    public TokenP(Game myGame, int x, int y, int patterneA, String playerId) {
        super(myGame, x, y);
        this.playerId = playerId;
        this.patterneA = patterneA;
        this.patterneB = 7 - patterneA;
        setNbMove(true);
    }

    public static int getVictime()
    {
        return victime;
    }
    
    private boolean goToBlood(int direction) {
        int destinationX, destinationY;

        switch (direction) {

            case 0: {
                destinationX = super.posX;
                destinationY = super.posY + 1;
                while (this.myGame.getMap()[destinationX][destinationY].special == 1) {
                    if (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenP") == 0) {
                        if (nbMove > 1) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    destinationY++;
                }

                return (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenP") == 0 || super.find(destinationX, destinationY).getClass().getName().compareTo("TokenM") == 0);
            }

            case 1: {
                destinationX = super.posX + 1;
                destinationY = super.posY;
                while (this.myGame.getMap()[destinationX][destinationY].special == 1) {
                    if (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenP") == 0) {
                        if (nbMove > 1) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    destinationX++;
                }
                return (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenP") == 0 || super.find(destinationX, destinationY).getClass().getName().compareTo("TokenM") == 0);
            }

            case 2: {
                destinationX = super.posX;
                destinationY = super.posY - 1;
                while (this.myGame.getMap()[destinationX][destinationY].special == 1) {
                    if (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenP") == 0) {
                        if (nbMove > 1) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    destinationY--;
                }
                return (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenP") == 0 || super.find(destinationX, destinationY).getClass().getName().compareTo("TokenM") == 0);
            }

            case 3: {
                destinationX = super.posX - 1;
                destinationY = super.posY;
                while (this.myGame.getMap()[destinationX][destinationY].special == 1) {
                    if (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenP") == 0) {
                        if (nbMove > 1) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    destinationX--;
                }
                return (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenP") == 0 || super.find(destinationX, destinationY).getClass().getName().compareTo("TokenM") == 0);
            }

            default: {
                return true;
            }
        }
    }

    /**
     * @move the current TokenP to the list of winner TokenP
     */
    private void translationWin() {
        super.myGame.getListeTokenPisWin().add(this);
        // ...puis on le retire de la liste des pions en jeu...
        boolean flag = false;
        int i = 0;
        // ...on visite les pions du joueur 1...
        for (TokenP p : super.myGame.getPlayer1().getPions()) {
            if (p.getPosX() == super.posX && p.getPosY() == super.posY) {
                super.myGame.getPlayer1().getPions().remove(i);
                flag = true;
            }
            i++;
        }
        // ...si on a rien trouvé pour le joueur 1, on visite alors les pions du joueur 2...
        if (flag == false) {
            i = 0;
            for (TokenP p : super.myGame.getPlayer2().getPions()) {
                if (p.getPosX() == super.posX && p.getPosY() == super.posY) {
                    super.myGame.getPlayer1().getPions().remove(i);
                    flag = true;
                }
                i++;
            }
        }
    }

    /**
     * @param phase
     * @set the nbMove of the current TokenP from its dual pattern
     */
    public void setNbMove(boolean phase) {
        if (phase) {
            this.nbMove = patterneA;
        } else {
            this.nbMove = patterneA;
        }
    }

    /**
     * @param direction
     * @change x and y with the current token parametre is a reference of the
     * direction 0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest
     */
    // Note : penser à s'inspirer de move() dans TokenR et les autres afin de réduire la taille du code
    @Override
    public void move(int direction) {

        super.myGame.getMap()[super.posX][super.posY].setEmpty(false);

        if (nbMove > 0 && goToBlood(direction)) {
            if (direction == 0 && super.posX == 0 && super.posY == 10) {
                // si le pion a atteint la sortie
                this.translationWin();
            } else {
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

                // si c'est le tour des joueurs
                if (this.myGame.IsPhase1()) {
                    // si la prochaine case est dans le tableau
                    if ((new TokenR(super.myGame, destinationX, destinationY)).isInside()) {
                        // s'il y a un Token...
                        if (super.myGame.getMap()[destinationX][destinationY].isEmpty()) {
                            // si le Token en question est un bloc de pierre...
                            if (super.find(destinationX, destinationY).getClass().getName().compareTo("TokenR") == 0) {
                                TokenR t = (TokenR) super.find(destinationX, destinationY);
                                if (t.canBePushByPion(direction)) {
                                    // poussé
                                    t.move(direction, this);

                                    // déplacement
                                    super.posX = destinationX;
                                    super.posY = destinationY;
                                    // flaque de sang
                                    if (this.myGame.getMap()[super.posX][super.posY].special == 1 && super.find(destinationX, destinationY).getClass().getName().compareTo("TokenR") != 0) {
                                        this.move(direction);
                                    }
                                    nbMove--;
                                }
                            } else if (nbMove > 1) {
                                // déplacement
                                super.posX = destinationX;
                                super.posY = destinationY;
                                //flaque de sang
                                if (this.myGame.getMap()[super.posX][super.posY].special == 1 && super.find(destinationX, destinationY).getClass().getName().compareTo("TokenR") != 0) {
                                    this.move(direction);
                                }
                                nbMove--;
                            }
                        } //s'il n'y a pas de Token alors le pion peut se déplacer normalement
                        else {
                            // déplacement
                            super.posX = destinationX;
                            super.posY = destinationY;
                            // flaque de sang
                            if (this.myGame.getMap()[super.posX][super.posY].special == 1 && super.find(destinationX, destinationY).getClass().getName().compareTo("TokenR") != 0) {
                                this.move(direction);
                            }
                            nbMove--;
                        }
                    }
                } // si c'est le tour du Monstre, cela veut dire d'après les règles, qu'il est en train de se faire pousser par un bloc de pierre, par le monstre
                else {
                    // si rien ne bloque le pion pendant son mouvement "forcé"... sinon il meurt
                    if ((new TokenR(super.myGame, destinationX, destinationY)).isInside() && this.myGame.getMap()[destinationX][destinationY].isEmpty()) {
                        // déplacement
                        super.posX = destinationX;
                        super.posY = destinationY;
                        // flaque de sang
                        if (this.myGame.getMap()[super.posX][super.posY].special == 1 && super.find(destinationX, destinationY).getClass().getName().compareTo("TokenR") != 0) {
                            this.move(direction);
                        }
                        nbMove--;

                        // si le pion a atteint la sortie
                        this.translationWin();
                    } else {
                        this.die();
                    }
                }
            }

        }
        super.myGame.getMap()[super.posX][super.posY].setEmpty(true);
    }

    /**
     * @erase or destroy the current TokenP
     */
    public void die() {
        
        // on comptabilise sa mort
        this.victime++;

        super.myGame.getMap()[super.posX][super.posY].setEmpty(false);
        
        // dans tous les cas quand à un pion est éliminé, il se retrouve dans une liste qui lui est attribué...
        super.myGame.getListeTokenPisOut().add(this);

        // ...puis on le retire de la liste des pions en jeu...
        boolean flag = false;
        int i = 0;
        // ...on visite les pions du joueur 1...
        for (TokenP p : super.myGame.getPlayer1().getPions()) {
            if (p.getPosX() == super.posX && p.getPosY() == super.posY) {
                super.myGame.getPlayer1().getPions().remove(i);
                flag = true;
            }
            i++;
        }
        // ...si on a rien trouvé pour le joueur 1, on visite alors les pions du joueur 2...
        if (flag == false) {
            i = 0;
            for (TokenP p : super.myGame.getPlayer2().getPions()) {
                if (p.getPosX() == super.posX && p.getPosY() == super.posY) {
                    super.myGame.getPlayer1().getPions().remove(i);
                    flag = true;
                }
                i++;
            }
        }

        // si on est dans la manche 2, tous les pions disqualiés seront retirés du jeu
        if (super.myGame.getNbTour() <= 7 && super.myGame.getListeTokenPisOut().isEmpty() == false) {
            i = 0;
            for (TokenP p : super.myGame.getListeTokenPisOut()) {
                if (p.getPosX() == super.posX && p.getPosY() == super.posY) {
                    super.myGame.getListeTokenPisOut().remove(i);
                }
                i++;
            }
        }
    }

    /**
     * @return the playerId
     */
    public String getPlayerId() {
        return playerId;
    }
}
