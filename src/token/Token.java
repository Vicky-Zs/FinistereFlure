package token;
import map.*;
import finstereflure.*;
import character.*;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aurelien
 */
public abstract class Token {
  protected static int id = 0;
  protected int reference;
  protected int nbMove;
  protected int posX;
  protected int posY;
  protected Game myGame;
  /**
   * Constructeur de la classe Token
   * @param myGame [Pour avoir les infos de la partie]
   * @param x      [Position x]
   * @param y      [Position y]
   */
  public Token (Game myGame , int x , int y) {
    this.myGame = myGame;
    this.posX = x;
    this.posY = y;
    this.reference = id;
    id++;
  }
   /**
    * Constructeur pour les phases de tests
    * @param myGame [Pour avoir les infos de la partie]
    * @param x      [Position x]
    * @param y      [Position y]
    * @param nbMove [Nombre de mouvement du pion]
    */
  public Token (Game myGame , int x , int y, int nbMove) { //Création d'un token temporaire
    this.myGame = myGame;
    this.posX = x;
    this.posY = y;
    this.nbMove = nbMove;
    this.reference = -1;
  }
  /**
   * Récupère la référence d'un token
   * @return [ref tolen]
   */
  public int getReference(){
    return this.reference;
  }

  /**
   * Permet de bouger
   * @param direction
   * @change x and y with the current token
   */
  // Note : pourra être améliorer grâce à l'astuce des diagonales
  public abstract void move(int direction);
  // donne le Token recherché ; Renvoie null sinon...
  /**
   * Permet de trouver un pion sur une case
   * @param x
   * @param y
   * @return the Token from a given position or null if the search has no done
   */
  public Token find(int x , int y){
    // postulat : chaque player a une LISTE de pions
    Iterator<Token> iterator;
    // fouille dans la liste des pions de chaque joueur
    for (int i = 0; i < this.myGame.getNbPlayer(); i++) {
      iterator = myGame.getPlayer(i).getToken().iterator();
      if(!myGame.getPlayer(i).isEmpty()){
        while (iterator.hasNext()){
          Token p = iterator.next();
          if((p.getPosX() == x) && (p.getPosY() == y)){
            return p;
          }
        }
      }
    }
    // fouille dans la liste des TokenR
    if(!myGame.getTokenR().isEmpty()){
      for(TokenR t : myGame.getTokenR()){
        if( t.getPosX() == x && t.getPosY() == y ){
          return t;
        }
      }
    }
    // fouille pour le monstre
    TokenM m = this.myGame.getMonster().getToken();
    if(m.getPosX() == x &&  m.getPosY() == y){
      return m;
    }
    return null;
  }
  /**
   * Permet de récupérer la position X
   * @return the posX
   */
  public int getPosX() {
    return posX;
  }

  /**
   * Permet de récupérer la position Y
   * @return the posY
   */
  public int getPosY() {
    return posY;
  }
  /**
   * Permet de récupérer le nombre de mouvement restant
   * @return [nombre de mouvement]
   */
  public int getNbMove(){
    return nbMove;
  }
}
