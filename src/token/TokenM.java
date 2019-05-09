/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;
import map.*;
import finstereflure.*;
import character.*;
import java.util.ArrayList;

/**
 *
 * @author Aurelien
 */
public class TokenM extends Token {
  private int orientation;
  private ArrayList<Integer> nbMoves = new ArrayList<>();    // présence de doublons
  private boolean firstTurn = true;
  /**
   * Constructeur par défaut
   * @param myGame [Permet de récupérer les infos du jeu]
   */
  public TokenM (Game myGame) {
    super(myGame, 0, 10);
    this.orientation = 1;
    this.nbMoves.add(-2);
    this.nbMoves.add(-1);
    this.nbMoves.add(5);
    this.nbMoves.add(7);
    this.nbMoves.add(7);
    this.nbMoves.add(8);
    this.nbMoves.add(8);
    this.nbMoves.add(10);
  }
  /**
   * Constructeur pour les tests de déplacement
   * @param myGame    [Permet de récupérer les infos du jeu]
   * @param x         [Position X]
   * @param y         [Position Y]
   * @param direction [Direction (0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest)]
   */
  public TokenM (Game myGame, int x, int y, int direction) {
    super(myGame , x , y);
    this.orientation = direction;
    this.nbMoves.add(-2);
    this.nbMoves.add(-1);
    this.nbMoves.add(5);
    this.nbMoves.add(7);
    this.nbMoves.add(7);
    this.nbMoves.add(8);
    this.nbMoves.add(8);
    this.nbMoves.add(10);
  }
  /**
   * Permet de vérifier si le token est à l'intérieur de la map
   * @return [Vrai = intérieur de la map, Faux = extérieur de la map]
   */
  private boolean isInside(){
    return ( (this.posX >= 0 && this.posX <= 15) && (this.getPosY() >= 0 && this.getPosY() <= 10) ) && ( this.posX + this.posY <= 21) ;
  }

  /**
   * @param direction
   * @change x and y with the current token
   * parametre is a reference of the direction
   * 0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest
   */
  @Override
  public void move(int direction) {
    System.out.println("Avant :\t\tposX = " + this.posX + " posY = " + this.posY);
    // try
    // {
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
        break;
      }
    System.out.println("Destination :\tposX = " + destinationX + " posY = " + destinationY);
    // Si la case suivante ne dépasse pas les limites du tableau...
    if((new TokenM(this.myGame, destinationX, destinationY, direction)).isInside()){
      // s'il y a un Token dans la case suivante...
      if(this.myGame.getMap(destinationX, destinationY).isTokenHere()){
        // si ce Token est un bloc de pierre : ce dernier doit bouger
        if (this.find(destinationX, destinationY) instanceof TokenR){
          TokenR t = (TokenR) this.find(destinationX, destinationY);
          t.move(direction, this);
        }
        else{
          TokenP p = (TokenP) this.find(destinationX, destinationY);
          p.die();
        }
      }
      // déplacement
      this.setPosX(destinationX);
      this.setPosY(destinationY);
    }
    else{
      if(this.getOrientation() >= 2){
        this.orientation -= 2;
      }
      else{
        this.orientation += 2;
      }
      this.move(this.getOrientation());
    }
    System.out.println("Après :\t\tposX = " + this.posX + " posY = " + this.posY);
    System.out.println("--------------------------------------------");
  }
  /**
   * @controle all of the monster's phase
   */
  public void tour()  {
    System.out.println("--------------------------------------------");
    this.myGame.getMap(this.posX, this.posY).setNotTokenHere();
    rollDice();
      if(this.nbMove < 0){
        int maxMove = 0, die1 = TokenP.getVictime();
        while(this.nbMove == die1 - TokenP.getVictime() && maxMove <= 20){
          this.move(look());
          // gestion de la flaque de sang
          while(this.myGame.getMap(this.posX, this.posY).isBloodspot()){
            this.move(look());
          }
          maxMove++;
        }
      }
      else{
        while(this.nbMove > 0){
          // System.out.println("Test 3");
          this.move(look());
          // System.out.println("Test 4");
          // gestion de la flaque de sang
          //
          while(this.myGame.getMap(this.posX, this.posY).isBloodspot()){
            this.move(look());
          }
          this.nbMove--;
        }
      }
      look();
      this.firstTurn = false;
      this.myGame.getMap(this.posX, this.posY).setTokenHere();
      System.out.println("--------------------------------------------");
  }

  /**
   * Permet de savoir si le montre voit un pion joueur au nord
   * @return [Retourne 50 si le monstre ne voit rien, 0 s'il a vu un token joueur]
   */
  private int lookUp() {
    int result = 0;
    int x = this.posX;
    int y = this.posY + 1;
    try{
      while (!this.myGame.getMap(x,y-1).getWall(0)){
        // System.out.println("Test");
        if (this.myGame.getMap(x, y).isTokenHere()){
          if (this.find(x, y) instanceof TokenP){
            return result;
          }
          else{
            return 50;
          }
        }
        else{
          result++;
          y++;
        }
      }
      return 50;
    }
    catch (NullPointerException e){
      return 50;
    }
  }
  /**
   * Permet de savoir si le montre voit un pion joueur au sud
   * @return [Retourne 50 si le monstre ne voit rien, 0 s'il a vu un token joueur]
   */
  private int lookDown() {
    int result = 0;
    int x = this.posX;
    int y = this.posY - 1;
    try{
      // System.out.println(x+" "+y);
      while (!this.myGame.getMap(x, y + 1).getWall(2)){
        if (this.myGame.getMap(x, y).isTokenHere()){
          if (this.find(x, y) instanceof TokenP){
            return result;
          }
          else{
            return 50;
          }
        }
        else{
          result++;
          y--;
        }
      }
      return 50;
    }
    catch (NullPointerException e){
      return 50;
    }
  }
  /**
   * Permet de savoir si le montre voit un pion joueur a l'ouest
   * @return [Retourne 50 si le monstre ne voit rien, 0 s'il a vu un token joueur]
   */
private int lookLeft() {
  int result = 0;
  int x = this.posX - 1;
  int y = this.posY;
  try{
    while (!this.myGame.getMap(x + 1, y).getWall(3)){
      if (this.myGame.getMap()[x][y].isTokenHere()){
        if (this.find(x, y) instanceof TokenP){
          return result;
        }
        else{
          return 50;
        }
      }
      else{
        result++;
        x--;
        }
      }
      return 50;
    }
    catch (NullPointerException e){
      return 50;
    }
  }
  /**
   * Permet de savoir si le montre voit un pion joueur à l'est
   * @return [Retourne 50 si le monstre ne voit rien, 0 s'il a vu un token joueur]
   */
  private int lookRight() {
    int result = 0;
    int x = this.posX + 1;
    int y = this.posY;
    try{
      while (!this.myGame.getMap()[x - 1][y].getWall(1)){
        if (this.myGame.getMap()[x][y].isTokenHere()){
          if (this.find(x, y) instanceof TokenP){
            return result;
          }
          else{
            return 50;
          }
        }
        else{
          result++;
          x++;
        }
      }
      return 50;
    }
    catch (NullPointerException e){
      return 50;
    }
  }
  // 0 = Nord, 1 = Est (droite / right), 2 = Sud, 3 = Ouest (gauche / left)
  /**
   * Permet de savoir si le montre voit un pion joueur en fonction de son orientation
   * @return [Direction du monstre]
   */
  private int look() {
    int up = 50, down = 50, left = 50, right = 50;
    switch(this.getOrientation()) {
      case 0:
      up = this.lookUp();
      left = this.lookLeft();
      right = this.lookRight();
      break;
      case 1:
      up = this.lookUp();
      down = this.lookDown();
      right = this.lookRight();
      break;
      case 2:
      down = this.lookDown();
      left = this.lookLeft();
      right = this.lookRight();
      break;
      case 3:
      up = this.lookUp();
      down = this.lookDown();
      left = this.lookLeft();
      break;
      default:
      break;
    }
    // si quelque chose a été repéré...
    if(up + down + left + right != 50*4){
      // on détermine la distance minimale
      int min = up;
      if(min > down)    min = down;
      if(min > left)    min = left;
      if(min > right)    min = right;
      // on vérifie qu'il n'y qu'une seulle distance
      int recurence = 0;
      if( min == up )      recurence++;
      if( min == down )    recurence++;
      if( min == left )    recurence++;
      if( min == right )   recurence++;
      // puis on en défini l'orientation
      if(recurence == 1){
        if(min == up)      this.orientation = 0;    // Nord
        if(min == right)   this.orientation = 1;    // Est
        if(min == down)    this.orientation = 2;    // Sud
        if(min == left)    this.orientation = 3;    // Ouest
      }
    }
    System.out.println("Orientation :\t" + getOrientation());
    return this.getOrientation();
  }

  // tire un chiffre, le supprime ; refait nbMoves s'il ne reste qu'une tuile
  /**
   * @configure the current nbMove from the list nbMoves
   */
  private void rollDice() {
    int id = (int) (Math.random()*this.nbMoves.size());
    if (this.nbMoves.size() == 1){
      this.nbMoves.clear();
      this.nbMoves.add(-2);
      this.nbMoves.add(-1);
      this.nbMoves.add(5);
      this.nbMoves.add(7);
      this.nbMoves.add(7);
      this.nbMoves.add(8);
      this.nbMoves.add(8);
      this.nbMoves.add(10);
    }
    // Au premier tour, les cartes "1 mort" et "2 morts" ne peuvent pas être pioché : on repioche alors si on tombe sur cela
    if(this.nbMoves.get(id) < 0 && this.firstTurn){
      while(this.nbMoves.get(id) < 0){
        id = (int) (Math.random()*this.nbMoves.size());
      }
    }
    this.nbMove = (int) this.nbMoves.remove(id);
  }

  /**
   * @return the orientation
   */
  public int getOrientation() {
      return orientation;
  }

}
