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
    
    private boolean isBlocking()
    {
        return (this.getPosX() == 0 && this.getPosY() == 0 ) || (this.getPosX() == 0 && this.getPosY() == 10 );
    }
    
    // intervertie deux cases dans le tableau de Token
    private void change(int x1 , int y1 , int x2 , int y2)
    {
        Token verre = this.myBoardToken[y2][x2];
        this.myBoardToken[y2][x2] = this.myBoardToken[y1][x1];
        this.myBoardToken[y1][x1] = verre;
        this.setPosX(x2);
        this.setPosY(y2);
    }
    
    
    // quand l'acteur du déplacement est le monstre
    private void moveByMonster(int ordre)
    {
        switch (ordre)  {
            
            case 0: 
            {
                // Si la case suivante est dans le tableau
                if( super.isInside() )
                {
                    // Si la case suivante est vide...
                    if( this.myBoardToken[this.getPosY() + 1][this.getPosX()].getClass().getName().compareTo("TokenV") == 0 )
                    {
                        this.change(this.getPosX(), this.getPosY(), this.getPosX(), this.getPosY() + 1);
                    }
                    // Si la case suivante est un pion ou un bloc de pierre...
                    else 
                    {
                        this.myBoardToken[this.getPosY() + 1][this.getPosX()].move(ordre);
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else {
                    this.myReserve.put(this);
                }
                    
                break;
            }
            
            case 2:
            {
                // Si la case suivante est dans le tableau
                if( this.getPosX() + 1 <= this.myBoardToken.sizeX() )
                {
                    // Si la case suivante est vide...
                    if( this.myBoardToken[this.getPosY()][this.getPosX() + 1].getClass().getName().compareTo("TokenV") == 0 )
                    {
                        this.change(this.getPosX(), this.getPosY(), this.getPosX() + 1, this.getPosY());
                    }
                    // Si la case suivante est un pion ou un bloc de pierre...
                    else 
                    {
                        this.myBoardToken[this.getPosY()][this.getPosX() + 1].move(ordre);
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else {
                    this.myReserve.put(this);
                }
                
                break;
            }
            
            case 3:
            {
                // Si la case suivante est dans le tableau
                if( this.getPosY() - 1 >= 0 )
                {
                    // Si la case suivante est vide...
                    if( this.myBoardToken[this.getPosY() - 1][this.getPosX()].getClass().getName().compareTo("TokenV") == 0 )
                    {
                        this.change(this.getPosX(), this.getPosY(), this.getPosX(), this.getPosY() - 1);
                    }
                    // Si la case suivante est un pion ou un bloc de pierre...
                    else 
                    {
                        this.myBoardToken[this.getPosY() - 1][this.getPosX()].move(ordre);
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else {
                    this.myReserve.put(this);
                }
                
                break;
            }
            
            case 4:
            {
                // Si la case suivante est dans le tableau
                if( this.getPosX() - 1 >= 0 )
                {
                    // Si la case suivante est vide...
                    if( this.myBoardToken[this.getPosY()][this.getPosX() - 1].getClass().getName().compareTo("TokenV") == 0 )
                    {
                        this.change(this.getPosX(), this.getPosY(), this.getPosX() - 1, this.getPosY());
                    }
                    // Si la case suivante est un pion ou un bloc de pierre...
                    else 
                    {
                        this.myBoardToken[this.getPosY()][this.getPosX() - 1].move(ordre);
                    }
                } 
                // Si la case suivante n'est pas dans le tableau...
                else {
                    this.myReserve.put(this);
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
    private boolean moveByPion(int ordre)
    {
        switch (ordre)  {
            
            case 1:
            {
                // Si la case suivante est dans le plateau, si elle est une case vide et s'il n'y pas de mur qui bloque le déplacement...
                if( (this.getPosY() + 1 <= this.myBoardToken.sizeY()) && (this.myBoardToken[this.getPosY() + 1][this.getPosX()].getClass().getName().compareTo("TokenV") == 0 && this.myMap[this.getPosY()][this.getPosX()].wallUP == false) )
                {
                    this.change(this.getPosX(), this.getPosY(), this.getPosX(), this.getPosY() + 1);
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
                if( (this.getPosX() + 1 <= this.myBoardToken.sizeX()) && (this.myBoardToken[this.getPosY()][this.getPosX() + 1].getClass().getName().compareTo("TokenV") == 0 && this.myMap[this.getPosY()][this.getPosX()].wallRIGHT == false) )
                {
                    this.change(this.getPosX(), this.getPosY(), this.getPosX() + 1, this.getPosY());
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
                if( (this.getPosY() - 1 >= 0) && (this.myBoardToken[this.getPosY() - 1][this.getPosX()].getClass().getName().compareTo("TokenV") == 0 && this.myMap[this.getPosY()][this.getPosX()].wallDOWN == false) )
                {
                    this.change(this.getPosX(), this.getPosY(), this.getPosX(), this.getPosY() - 1);
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
                if( (this.getPosX() - 1 >= 0) && (this.myBoardToken[this.getPosY()][this.getPosX() - 1].getClass().getName().compareTo("TokenV") == 0 && this.myMap[this.getPosY()][this.getPosX()].wallLEFT == false) )
                {
                    this.change(this.getPosX(), this.getPosY(), this.getPosX() - 1, this.getPosY());
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
    public void move(int ordre, Token acteur) {
        
        boolean flag = true;
        if( acteur.getClass().getName().compareTo("TokenM") == 0)
        {
            this.moveByMonster(ordre);
            while(this.getMap()[this.getPosY()][this.getPosX()].special == 1 && flag)
            {
                flag = this.moveByPion(ordre);
            }
        }
        else
        {
            this.moveByPion(ordre);
            while(this.myMap[this.getPosY()][this.getPosX()].special == 1 && flag)
            {
                flag = this.moveByPion(ordre);
            }
        }
    }
    
    
}
