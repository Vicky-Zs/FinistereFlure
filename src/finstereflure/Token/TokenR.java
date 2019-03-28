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
public class TokenR extends Token {
    
    // put permet d'intervertir une case Token du plateau à une case vide de la réserve
    
    public TokenR (Game myGame , int x , int y) {
        super.myGame = myGame;
        super.posX = x;
        super.posY = y;
    }
    
    
    // donne le Token recherché
    private Token find(int x , int y)
    {
        // postulat : chaque player a une LISTE de pions
        
        if( super.myGame.getPlayer1().isEmpty() == false )
        {
            for( Token p : super.myGame.getPlayer1().getPions() )
            {
                if( p.getPosX() == x && p.getPosY() == y )
                    return p;
            }
        }
        
        if( super.myGame.getPlayer2().isEmpty() == false )
        {
            for( Token p : super.myGame.getPlayer2().getPions() )
            {
                if( p.getPosX() == x && p.getPosY() == y )
                    return p;
            }
        }
        
        if( super.myGame.getListeTokenR().isEmpty() == false )
        {
            for( TokenR p : super.myGame.getListeTokenR() )
            {
                if( p.getPosX() == x && p.getPosY() == y )
                    return p;
            }
        }
        
        return null;
    }
    
    
    // quand l'acteur du déplacement est le monstre
    private void moveByMonster(int ordre)
    {
        switch (ordre)  {
            
            case 0: 
            {
                // Si la case suivante est dans le tableau
                if( (new TokenR(super.myGame, super.posX, super.posY + 1)).isInside() )
                {
                    // Si la case suivante est vide...
                    if( super.myGame.getMap()[super.getPosX()][super.getPosY() + 1].isEmpty() )
                    {
                        super.posY += 1;
                    }
                    // Si la case suivante est un pion ou un bloc de pierre...
                    else if( this.find(super.posX, super.posY + 1) != null )
                    {
                        this.find(super.posX, super.posY + 1).move(ordre);
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else 
                {
                    int i = 0;
                    for( TokenR p : super.myGame.getListeTokenR() )
                    {
                        if( p.getPosX() == x && p.getPosY() == y )
                            super.myGame.getListeTokenR().remove(i);
                        i++;
                    }
                }
                    
                break;
            }
            
            case 2:
            {
                // Si la case suivante est dans le tableau
                if( (new TokenR(super.myGame, super.posX + 1, super.posY)).isInside() )
                {
                    // Si la case suivante est vide...
                    if( super.myGame.getMap()[super.getPosX() + 1][super.getPosY()].isEmpty() )
                    {
                        super.posX += 1;
                    }
                    // Si la case suivante est un pion ou un bloc de pierre...
                    else if( this.find(super.posX + 1, super.posY) != null )
                    {
                        this.find(super.posX + 1, super.posY).move(ordre);
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else 
                {
                    int i = 0;
                    for( TokenR p : super.myGame.getListeTokenR() )
                    {
                        if( p.getPosX() == x && p.getPosY() == y )
                            super.myGame.getListeTokenR().remove(i);
                        i++;
                    }
                }
                
                break;
            }
            
            case 3:
            {
                // Si la case suivante est dans le tableau
                if( (new TokenR(super.myGame, super.posX, super.posY - 1)).isInside() )
                {
                    // Si la case suivante est vide...
                    if( super.myGame.getMap()[super.getPosX()][super.getPosY() - 1].isEmpty() )
                    {
                        super.posY -= 1;
                    }
                    // Si la case suivante est un pion ou un bloc de pierre...
                    else if( this.find(super.posX, super.posY - 1) != null )
                    {
                        this.find(super.posX, super.posY - 1).move(ordre);
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else 
                {
                    int i = 0;
                    for( TokenR p : super.myGame.getListeTokenR() )
                    {
                        if( p.getPosX() == x && p.getPosY() == y )
                            super.myGame.getListeTokenR().remove(i);
                        i++;
                    }
                }
                
                break;
            }
            
            case 4:
            {
                // Si la case suivante est dans le tableau
                if( (new TokenR(super.myGame, super.posX - 1, super.posY)).isInside() )
                {
                    // Si la case suivante est vide...
                    if( super.myGame.getMap()[super.getPosX() - 1][super.getPosY()].isEmpty() )
                    {
                        super.posX -= 1;
                    }
                    // Si la case suivante est un pion ou un bloc de pierre...
                    else if( this.find(super.posX - 1, super.posY) != null )
                    {
                        this.find(super.posX - 1, super.posY).move(ordre);
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else 
                {
                    int i = 0;
                    for( TokenR p : super.myGame.getListeTokenR() )
                    {
                        if( p.getPosX() == x && p.getPosY() == y )
                            super.myGame.getListeTokenR().remove(i);
                        i++;
                    }
                }
                
                break;
            }
            
            default:
            {
                break;
            }
        }
    }
    
    // quand l'acteur du déplacement est un pion
    private boolean moveByPion(int direction)
    {
        switch (direction)  {
            
            case 1:
            {
                // Si la case suivante est dans le plateau... si elle est une case vide et/ou s'il n'y pas de mur qui bloque le déplacement...
                if( (new TokenR(super.myGame, super.posX, super.posY + 1)).isInside() && ( this.myGame.getMap()[super.posX][super.posY].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] ) )
                {
                    super.posY += 1;
                }
                else
                {
                    return false;
                }
                
                break;
            }
            
            case 2:
            {
                // Si la case suivante est dans le plateau, si elle est une case vide et s'il n'y pas de mur qui bloque le déplacement...
                if( (new TokenR(super.myGame, super.posX + 1, super.posY)).isInside() && ( this.myGame.getMap()[super.posX][super.posY].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] ) )
                {
                    super.posX += 1;
                }
                else
                {
                    return false;
                }
                
                break;
            }
            
            case 3:
            {
                // Si la case suivante est dans le plateau, si elle est une case vide et s'il n'y pas de mur qui bloque le déplacement...
                if( (new TokenR(super.myGame, super.posX, super.posY - 1)).isInside() && ( this.myGame.getMap()[super.posX][super.posY].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] ) )
                {
                    super.posY -= 1;
                }
                else
                {
                    return false;
                }
                
                break;
            }
            
            case 4:
            {
                // Si la case suivante est dans le plateau, si elle est une case vide et s'il n'y pas de mur qui bloque le déplacement...
                if( (new TokenR(super.myGame, super.posX - 1, super.posY)).isInside() && ( this.myGame.getMap()[super.posX][super.posY].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] ) )
                {
                    super.posX -= 1;
                }
                else
                {
                    return false;
                }
                
                break;
            }
            
            default:
            {
                break;
            }
        }
        
        return true;
    }
    
    /**
     * @change x and y with the current token
     * parametre is a reference of the direction
     * 1 = Nord, 2 = Est, 3 = Sud, 4 = Ouest
     */
    public void move(int direction, Token acteur) {
        
        boolean flag = true;
        if( acteur.getClass().getName().compareTo("TokenM") == 0)
        {
            this.moveByMonster(direction);
            while(this.myGame.getMap()[super.posX][super.posY].special == 1 && flag)
            {
                flag = this.moveByPion(direction);
            }
        }
        else
        {
            this.moveByPion(direction);
            while(this.myGame.getMap()[super.posX][super.posY].special == 1 && flag)
            {
                flag = this.moveByPion(direction);
            }
        }
    }
    
    
}
