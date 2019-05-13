/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import map.*;
import finstereflure.*;
import character.*;
import java.util.Iterator;

/**
 *
 * @author Aurélien
 */
public class TokenP extends Token {
  private boolean out, win;
  private int patternA, patternB, playerId;
  private static int victime = 0;
  /**
   * Constructeur par défaut du token joueur
   * @param myGame    [Permet de récupérer les informations de la partie]
   * @param patterneA [Nombre de déplacement (phase clair)]
   * @param playerId  [Attribution du numéro du joueur]
   */
  public TokenP(Game myGame, int patterneA, int playerId) {
    super(myGame, -1, -1);      // Convention: -1/-1 = en dehors de la carte
    this.playerId = playerId;   // permet de savoir si le pion appartient au Joueur 0 ou 1
    this.patternA = patterneA;
    this.patternB = Math.abs(7 - patterneA);    //Retourne la valeur absolue
    this.out = true;            // Peut-être inutile puisque l'on a la liste Outside
    this.win = false;
    setNbMove(true);
    myGame.getTokenOutside().add(this);
  }
  /**
   * Constructeur pour les tests
   * @param myGame [Permet de récupérer les informations de la partie]
   * @param posX   [Position X]
   * @param posY   [Position Y]
   * @param nbMove [Nombre de mouvement restant]
   */
  public TokenP(Game myGame, int posX, int posY, int nbMove) {
    super(myGame, posX, posY); //Constructeur pour tester les cases
    this.nbMove = nbMove;
    this.playerId = -1;
    this.out = false;
    this.win = false;
    setNbMove(true);
    myGame.getTokenOutside().add(this);
  }
  /**
   * Récupère le jeu
   * @return [Jeu en cours]
   */
  public Game getGame(){
    return this.myGame;
  }
  /**
   * Récupère le nombre de victime
   * @return [description]
   */
  public static int getVictime() {
    return victime;
  }
  /**
   * if the Monster's orientation is different from a given direction : return true
   * @param  direction [0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest]
   * @return           []
   */
  public boolean pathNoMonster(int direction){
    return direction != this.myGame.getMonster().getToken().getOrientation();
  }
  // find a doomed TokenP
  public int findTokenP(int distance){
    Iterator<TokenP> iterator;
    // fouille dans la liste des pions de chaque joueur
    for (int i = 0; i < this.myGame.getNbPlayer(); i++){
      iterator = this.myGame.getPlayer(i).getToken().iterator();
      if( !this.myGame.getPlayer(i).isEmpty() ){
        while (iterator.hasNext()){
          TokenP p = iterator.next();
          if( !p.isOut() &&  !p.isWin() ){
            if( p.distanceM() == distance && p.isTrapped(distance)){
              return p.getPlayerId();
            }
          }
        }
      }
    }
    return -1;
  }
  /**
   *
   * @return []
   */
  public boolean isSave(){
    int xM = this.myGame.getMonster().getToken().getPosX();
    int yM = this.myGame.getMonster().getToken().getPosY();
    int xP = this.posX;
    int yP = 10 - this.posY;
    int distanceExit = (int)( Math.sqrt(Math.pow(xP,2)+Math.pow(yP,2)) );
    return (xM != 0 && yM != 10) && !this.isAxisMonster() && this.nbMove > distanceExit;
  }
  /**
   *
   * @param  distance [0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest]
   * @return          []
   */
  public boolean isTrapped(int distance){
    return (!this.isActif() && this.isAxisMonster() && !this.isBehind() && this.distanceM() == distance);
  }
  /**
   * Permet de savoir s'il reste des déplacements à un pion
   * @return [description]
   */
  public boolean isActif(){
    return this.nbMove > 0;
  }
   /**
    *
    * @return [description]
    */
  public boolean isAxisMonster(){
    int xM = this.myGame.getMonster().getToken().getPosX();
    int yM = this.myGame.getMonster().getToken().getPosY();
    int xP = this.posX;
    int yP = this.posY;
    return ( xM == xP || yM == yP );
  }
  /**
   *
   * @return [description]
   */
  public boolean isBehind(){
    int orientation = this.myGame.getMonster().getToken().getOrientation();
    int xM = this.myGame.getMonster().getToken().getPosX();
    int yM = this.myGame.getMonster().getToken().getPosY();
    int xP = this.posX;
    int yP = this.posY;
    switch(orientation){
      case 0:
      return (yP < yM);
      case 1:
      return (xP < xM);
      case 2:
      return (yP > yM);
      case 3:
      return (xP > xM);
      default:
      return false;
    }
  }
  /**
   * Permet de savoir s'il est proche ou pas d'un montre
   * @return
   */
  public boolean isClose(){
    return (this.distanceM() <= 5);
  }
  /**
   * Permet de savoir la distance qui sépare un pion joueur du monstre
   * @return [nombre de cases]
   */
  public int distanceM(){
    int xM = this.myGame.getMonster().getToken().getPosX();
    int yM = this.myGame.getMonster().getToken().getPosY();
    int xP = this.posX;
    int yP = this.posY;
    return (int)( Math.sqrt(Math.pow(Math.abs(xM-xP) , 2) + Math.pow( Math.abs(yM-yP) , 2)) );
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
  /**
   * Permet de modifier le nombre de mouvements pour passer le tour
   */
  public void turnOff(){
    this.nbMove = 0;
  }

  //
  /**
   * indique si le TokenP peut passer son tour
   * @return [Vrai = Oui, Faux = Non]
   */
  public boolean canStay() {
    return (this.myGame.getMap()[this.getPosX()][this.getPosY()].isTokenHere() == false);
  }

  // Indique si le TokenP peut se dépacer d'une case, vers une direction donnée
  public boolean canMove(int direction) {
    TokenP t = new TokenP(this.myGame, this.posX, this.posY, this.getNbMove());
    boolean flag = t.moveONE(direction);
    if ((flag) && (this.nbMove > 0)){
      if (this.myGame.getMap(t.getPosX(), t.getPosY()).isBloodspot()){
        while ((this.myGame.getMap(t.getPosX(), t.getPosY()).isBloodspot()) && (flag)){
          flag = t.moveONE(direction);
        }
        if (this.myGame.getMap(t.getPosX(), t.getPosY()).isTokenHere()){
          return (this.nbMove > 1);
        }
        else{
          return true;
        }
      }
      else{
        if (this.myGame.getMap(t.getPosX(), t.getPosY()).isTokenHere()){
          return (this.nbMove > 1);
        }
        else{
          return (this.nbMove > 0);
        }
      }
    }
    else{
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
  /**
   * déplace le TokenP de 1 case vers une direction donnée : indique s'il a réussi à se déplacer ou non
   * @param  direction [0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest]
   * @return           [Vrai = a réussi à se déplacer]
   */
  private boolean moveONE(int direction) {
    if (this.nbMove > 0){
    // Coordonnées fictives de la prochaine case après le déplacement
      int destinationX = this.posX, destinationY = this.posY;
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
      // Gestion des murs : s'il y a un mur, le mouvement est impossible.
      if (this.myGame.getMap()[this.posX][this.posY].getWall(direction) == false /*|| destinationX + destinationY == 4*/){
        // Gestion des colisions : s'il y a un Token...
        if (this.myGame.getMap(destinationX, destinationY).isTokenHere()){
          // ...si ce Token est un bloc de pierre...
          if (this.find(destinationX, destinationY) instanceof TokenR){
            TokenR t = (TokenR) super.find(destinationX, destinationY);
            // ...si ce TokenR peut être poussé par un pion : ce TokenR doit bouger
            if (t.canBePushByPion(direction)){
              // Token R poussé
                t.move(direction, this);
                // Déplacement
                this.setPosX(destinationX);
                this.setPosY(destinationY);
                return true;
            }
            else{
              return false;
            }
          }
          else{
            // ...si ce Token est un pion
            if (this.find(destinationX, destinationY) instanceof TokenP){
              // ...s'il reste plus de 1 point de mouvement : le pion peut se déplacer
              if (this.nbMove > 1) {
                // Déplacement
                this.setPosX(destinationX);
                this.setPosY(destinationY);
                return true;
              }
              else{
                return false;
              }
            }
            else{    // ...si ce Token est alors un monstre : le pion ne peut pas se déplacer
              return false;
            }
          }
        }
        else{    // s'il n'y a ni mur, ni Token : le pion se déplace librement
          // Déplacement
          this.setPosX(destinationX);
          this.setPosY(destinationY);
          return true;
        }
      }
      else{
        return false;
      }
    }
    else{
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
    else if (( this.winningCondition() && direction == 0) == false) {
      this.myGame.getMap(this.posX, this.posY).setNotTokenHere();
      // Si c'est le tour des joueurs
      if (this.myGame.isTurnPlayers()) {
        if (this.canMove(direction)) {
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
          while ((this.myGame.getMap(this.posX, this.posY).isBloodspot()) && (flag)) {
            flag = this.moveONE(direction);
          }
          this.myGame.getMap(this.posX, this.posY).setTokenHere();
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
    victime++;
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

  public boolean winningCondition(){
    return (this.nbMove > 0) && (this.posX == 0) && (this.posY == 10);
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
   /**
    * Réécriture de la méthode toString
    * @return [Phrase]
    */
  @Override
  public String toString(){
    if (this.isWin()) {
      return "Le pion " + "{" + this.patternA + "/" + this.patternB + "}" +" est il est sortie du donjon";
    }
    else if ((this.posX == -1) && (this.posY == -1)) {
      return "Le pion " + "{" + this.patternA + "/" + this.patternB + "}" +" est il est à l'entrée du donjon";
    }
    else {
      return "Le pion " + "{" + this.patternA + "/" + this.patternB + "}" +" est à la position ["+this.posX+";"+this.posY+"]";
    }
  }
}
