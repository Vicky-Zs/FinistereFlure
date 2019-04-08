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
    
    public TokenR (Game myGame , int x , int y) {
        super( myGame , x , y );
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
                    else if( super.find(super.posX, super.posY + 1) != null )
                    {
                        super.find(super.posX, super.posY + 1).move(ordre);
                        super.posY += 1;
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else 
                {
                    super.myGame.getMap()[super.posX][super.posY].setEmpty(false);
                    int i = 0;
                    for( TokenR p : super.myGame.getListeTokenR() )
                    {
                        if( p.getPosX() == super.posX && p.getPosY() == super.posY )
                            super.myGame.getListeTokenR().remove(i);
                        i++;
                    }
                }
                    
                break;
            }
            
            case 1:
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
                    else if( super.find(super.posX + 1, super.posY) != null )
                    {
                        super.find(super.posX + 1, super.posY).move(ordre);
                        super.posX += 1;
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else 
                {
                    super.myGame.getMap()[super.posX][super.posY].setEmpty(false);
                    int i = 0;
                    for( TokenR p : super.myGame.getListeTokenR() )
                    {
                        if( p.getPosX() == super.posX && p.getPosY() == super.posY )
                            super.myGame.getListeTokenR().remove(i);
                        i++;
                    }
                }
                
                break;
            }
            
            case 2:
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
                    else if( super.find(super.posX, super.posY - 1) != null )
                    {
                        super.find(super.posX, super.posY - 1).move(ordre);
                        super.posY -= 1;
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else 
                {
                    super.myGame.getMap()[super.posX][super.posY].setEmpty(false);
                    int i = 0;
                    for( TokenR p : super.myGame.getListeTokenR() )
                    {
                        if( p.getPosX() == super.posX && p.getPosY() == super.posY )
                            super.myGame.getListeTokenR().remove(i);
                        i++;
                    }
                }
                
                break;
            }
            
            case 3:
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
                    else if( super.find(super.posX - 1, super.posY) != null )
                    {
                        super.find(super.posX - 1, super.posY).move(ordre);
                        super.posX -= 1;
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else 
                {
                    super.myGame.getMap()[super.posX][super.posY].setEmpty(false);
                    int i = 0;
                    for( TokenR p : super.myGame.getListeTokenR() )
                    {
                        if( p.getPosX() == super.posX && p.getPosY() == super.posY )
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
            
            case 0:
            {
                // Si la case suivante est dans le plateau... si elle est une case vide et/ou s'il n'y pas de mur qui bloque le déplacement...
                if( (new TokenR(super.myGame, super.posX, super.posY + 1)).isInside() && ( this.myGame.getMap()[super.posX][super.posY + 1].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] == false ) )
                {
                    super.posY += 1;
                }
                else
                {
                    return false;
                }
                
                break;
            }
            
            case 1:
            {
                // Si la case suivante est dans le plateau, si elle est une case vide et s'il n'y pas de mur qui bloque le déplacement...
                if( (new TokenR(super.myGame, super.posX + 1, super.posY)).isInside() && ( this.myGame.getMap()[super.posX + 1][super.posY].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] == false ) )
                {
                    super.posX += 1;
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
                if( (new TokenR(super.myGame, super.posX, super.posY - 1)).isInside() && ( this.myGame.getMap()[super.posX][super.posY - 1].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] == false ) )
                {
                    super.posY -= 1;
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
                if( (new TokenR(super.myGame, super.posX - 1, super.posY)).isInside() && ( this.myGame.getMap()[super.posX - 1][super.posY].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] == false ) )
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
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * @param direction
     * @change x and y with the current token
     * parametre is a reference of the direction
     * 0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest
     */
    @Override
    public void move(int direction)    {
        super.myGame.getMap()[super.posX][super.posY].setEmpty(false);
        this.move(direction, (new TokenP(super.myGame , super.posX , super.posY)) );
        super.myGame.getMap()[super.posX][super.posY].setEmpty(true);
    }
    
    public void move(int direction, Token acteur) {
        
        super.myGame.getMap()[super.posX][super.posY].setEmpty(false);
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
        super.myGame.getMap()[super.posX][super.posY].setEmpty(true);
    }
    
    public boolean canBePushByPion(int direction)
    {
        switch (direction)  {
            
            case 0:
            {
                return ( this.myGame.getMap()[super.posX][super.posY + 1].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] == false );
            }
            
            case 1:
            {
                return ( this.myGame.getMap()[super.posX + 1][super.posY].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] == false );
            }
            
            case 2:
            {
                return ( this.myGame.getMap()[super.posX][super.posY - 1].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] == false );
            }
            
            case 3:
            {
                return ( this.myGame.getMap()[super.posX - 1][super.posY].isEmpty() || this.myGame.getMap()[super.posX][super.posY].getWalls()[direction] == false );
            }
            
            default:
            {
                return false;
            }
        }
    }
}
