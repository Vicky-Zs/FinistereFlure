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

  public Token (Game myGame , int x , int y) {
    this.myGame = myGame;
    this.posX = x;
    this.posY = y;
    this.reference = id;
    id++;
  }

  public Token (Game myGame , int x , int y, int nbMove) { //Création d'un token temporaire
    this.myGame = myGame;
    this.posX = x;
    this.posY = y;
    this.nbMove = nbMove;
    this.reference = -1;
  }

  public int getReference(){
    return this.reference;
  }

  /**
   * @param direction
   * @change x and y with the current token
   */
  // Note : pourra être améliorer grâce à l'astuce des diagonales
  public abstract void move(int direction);
  // donne le Token recherché ; Renvoie null sinon...
  /**
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
   * @return the posX
   */
  public int getPosX() {
    return posX;
  }

  /**
   * @return the posY
   */
  public int getPosY() {
    return posY;
  }

  public int getNbMove(){
    return nbMove;
  }

  // Note : penser à Override equal() et hashcode() afin de rendre les méthodes .get(Object) .contain(Object) des listes opérationnelles
  // et éviter les recherches par boucles for

  // Uniquement pour le test
  /**
   * @param posX the posX to set
   */
  public void setPosX(int posX) {
    this.posX = posX;
  }

  // Uniquement pour le test
  /**
   * @param posY the posY to set
   */
  public void setPosY(int posY) {
    this.posY = posY;
  }
}
