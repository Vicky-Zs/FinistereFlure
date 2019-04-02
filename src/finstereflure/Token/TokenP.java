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
    
    int patterneA, patterneB, nbMove;
    boolean win;
    
    public TokenP (Game myGame , int x , int y, int patterneA) {
        super( myGame , x , y );
        this.patterneA = patterneA;
        this.patterneB = 7 - patterneA;
        this.win = false;
    }
    
    private void translationWin()
    {
        if( super.posX == 0 && super.posY == 10 ) 
        {
            super.myGame.getListeTokenPisWin().add( this ) ;
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
    }
    
    public void setNbMove(boolean phase)
    {
        if( phase )
        {
            this.nbMove = patterneA;
        }
        else
        {
            this.nbMove = patterneA;
        }
    }
    
    /**
     * @param direction
     * @change x and y with the current token
     * parametre is a reference of the direction
     * 0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest
     */
    // Note : penser à s'inspirer de move() dans TokenR et les autres afin de réduire la taille du code
    @Override
    public void move(int direction)
    {
        if( nbMove > 0)
        {
            int destinationX = 0 , destinationY = 0; // cette déclaration permettra de détecter des problèmes
            switch (direction)  {
            
                case 0: 
                {
                    destinationX = super.posX;
                    destinationY = super.posY + 1;
                    break;
                }

                case 1:
                {
                    destinationX = super.posX + 1;
                    destinationY = super.posY;
                    break;
                }

                case 2:
                {
                    destinationX = super.posX;
                    destinationY = super.posY - 1;
                    break;
                }

                case 3:
                {
                    destinationX = super.posX - 1;
                    destinationY = super.posY;

                    break;
                }

                default:
                {
                    break;
                }
            }
            
            // si c'est le tour des joueurs
            if( this.myGame.IsPhase1() )
            {
                // si la prochaine case est dans le tableau
                if( (new TokenR(super.myGame, destinationX, destinationY)).isInside() )
                {
                    // s'il y a un Token...
                    if( super.myGame.getMap()[destinationX][destinationY].isEmpty() )
                    {
                        // si le Token en question est un bloc de pierre...
                        if( super.find(destinationX, destinationY).getClass().getName().compareTo("TokenR") == 0 )
                        {
                            TokenR t = (TokenR) super.find(destinationX, destinationY);
                            if( t.canBePushByPion(direction) )
                            {
                                // poussé
                                t.move(direction);
                                
                                // déplacement
                                super.posX = destinationX;
                                super.posY = destinationY;
                                while( this.myGame.getMap()[super.posX][super.posY].special == 1 && this.myGame.getMap()[super.posX][super.posY + 1].isEmpty() )
                                {
                                    super.posX = destinationX;
                                    super.posY = destinationY;
                                    nbMove--;
                                }
                                
                                // si le pion a atteint la sortie
                                this.translationWin();
                            }
                        }
                    }
                    //s'il n'y a pas de Token alors le pion peut se déplacer normalement
                    else
                    {
                        // déplacement
                        super.posX = destinationX;
                        super.posY = destinationY;
                        while( this.myGame.getMap()[super.posX][super.posY].special == 1 && this.myGame.getMap()[super.posX][super.posY + 1].isEmpty() )
                        {
                            super.posX = destinationX;
                            super.posY = destinationY;
                            nbMove--;
                        }
                        // si le pion a atteint la sortie
                        this.translationWin();
                    }
                }
            }
            // si ce n'est pas le cas, cela veut dire d'après les règles, qu'il est en train de se faire pousser par un bloc de pierre, par le monstre
            else 
            {
                // si rien ne bloque le pion pendant son mouvement "forcé"...
                if( (new TokenR(super.myGame, destinationX, destinationY)).isInside() && this.myGame.getMap()[destinationX][destinationY].isEmpty() )
                {
                    // déplacement
                    super.posX = destinationX;
                    super.posY = destinationY;
                    while( this.myGame.getMap()[super.posX][super.posY].special == 1 && this.myGame.getMap()[super.posX][super.posY + 1].isEmpty() )
                    {
                        super.posX = destinationX;
                        super.posY = destinationY;
                    }
                    // si le pion a atteint la sortie
                    this.translationWin();
                }
                else
                {
                    // dans tous les cas quand à un pion est éliminé, il se retrouve dans une liste qui lui est attribué...
                    super.myGame.getListeTokenPisOut().add( this ) ;

                    // ...puis on le retire de la liste des pions en jeu...
                    boolean flag = false;   
                    int i = 0;
                    // ...on visite les pions du joueur 1...
                    for( TokenP p : super.myGame.getPlayer1().getPions() )
                    {
                        if( p.getPosX() == super.posX && p.getPosY() == super.posY )
                        {
                            super.myGame.getPlayer1().getPions().remove(i);
                            flag = true;
                        }
                        i++;
                    }
                    // ...si on a rien trouvé pour le joueur 1, on visite alors les pions du joueur 2...
                    if( flag == false )
                    {
                        i = 0;
                        for( TokenP p : super.myGame.getPlayer2().getPions() )
                        {
                            if( p.getPosX() == super.posX && p.getPosY() == super.posY )
                            {
                                super.myGame.getPlayer1().getPions().remove(i);
                                flag = true;
                            }
                            i++;
                        }
                    }

                    // si on est dans la manche 2, tous les pions disqualiés seront retirés du jeu
                    if( super.myGame.getNbTour() <= 7 && super.myGame.getListeTokenPisOut().isEmpty() == false )
                    {
                        i = 0;
                        for( TokenP p : super.myGame.getListeTokenPisOut() )
                        {
                            if( p.getPosX() == super.posX && p.getPosY() == super.posY )
                                super.myGame.getListeTokenPisOut().remove(i);
                            i++;
                        }
                    }
                }
            }
        }
        
    }
    
    
}
