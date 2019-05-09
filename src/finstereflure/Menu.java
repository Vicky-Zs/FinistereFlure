/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstereflure;
import java.util.Scanner;
import character.*;
import map.*;
import token.*;

/**
 *
 * @author Cédric
 */

public class Menu {
  private Game game;
  /*
  * Default constructor
  */
  public Menu(Game g){
    this.game = g;
  }
  /**
   * Menu général
   */
  public void main(){
  Scanner scan = new Scanner(System.in);
  int in;
  for(int i = 0; i < game.getPlayers().length; i++) {
    System.out.println(i + "\nC'est au tour de " + game.getPlayer(i).getPseudo());
    for (Object o : game.getPlayer(i).getToken()){
      if (o instanceof TokenP){
        TokenP t = (TokenP) o;
        if (!t.isWin()) {
          System.out.println(t);
        }
      }
    }
    System.out.println("Veuillez choisir votre token "
    + "(avec le nombre de déplacement)");
    in = scan.nextInt();
    menuToken(takeToken(i, in));
    }
  scan.close();
  }

  /**
   * Permet de choisir un token parmis les tokens d'un joueur
   *
   * @param  i      [playerId]
   * @param  nbMove [pattern A or B]
   * @return        [return the token pattern A or B of playerId]
   */
  private TokenP takeToken(int i, int nbMove){
    for (Object o : game.getPlayer(i).getToken()){
      if (o instanceof TokenP) {
        TokenP t = (TokenP) o;
        if (t.getPatternA() == nbMove) {
          return t;
        }
        else if (t.getPatternB() == nbMove) {
          return t;
        }
      }
    }
    return null;
  }

  /**
   * Menu du token, gère aussi ses déplacements
   *
   * @param t [Token en question]
   */
  private void menuToken(TokenP t) {
    Scanner scan = new Scanner(System.in);
    t.setNbMove(game.getNbTurn()%2==1);
    game.getMap(posX, posY).setNotTokenHere();
    do {
      if ((t.getPosX() == -1) && (t.getPosY() == -1)) {
        t.move(0);
      }
      else {
        System.out.println("Dans quel direction voulez-vous bougez ? \n"
        + "1 = Nord, 2 = Est, 3 = Sud, 4 = Ouest \n"
        + "0 = Fin du tour en cours");
        int in = scan.nextInt();
        switch (in) {
          case 0:
          if (game.getMap(t.getPosX(), t.getPosY()).isTokenHere()){
            System.out.println("Vous ne pouvez pas finir votre tour ici");
          }
          else{
            t.turnOff();
          }
          break;
          case 1:
          if(t.getPosY() < 10){
            if(game.getMap(t.getPosX(), t.getPosY() + 1).isTokenHere()){
              Token temp = t.find(t.getPosX(), t.getPosY() + 1);
              if(temp instanceof TokenP){
                if(t.getNbMove()>1){
                  t.move(in-1);
                }
                else{
                  System.out.println("Cette case est occupée par un pion d'un joueur");
                }
              }
              else if(temp instanceof TokenR){
                TokenR r = (TokenR) temp;
                r.move(in - 1);
                if(game.getMap(t.getPosX(), t.getPosY() + 1).isTokenHere()){
                  System.out.println("Cette case est occupée par un pion rocher");
                }
                else{
                  t.move(in-1);
                }
              }
            }
          }
          break;
          case 2:
          if(t.getPosY() < 10){
            if(game.getMap(t.getPosX()+1, t.getPosY()).isTokenHere()){
              Token temp = t.find(t.getPosX(), t.getPosY() + 1);
              if(temp instanceof TokenP){
                if(t.getNbMove()>1){
                  t.move(in-1);
                }
                else{
                  System.out.println("Cette case est occupée par un pion d'un joueur");
                }
              }
              else if(temp instanceof TokenR){
                TokenR r = (TokenR) temp;
                r.move(in - 1);
                if(game.getMap(t.getPosX()+1, t.getPosY()).isTokenHere()){
                  System.out.println("Cette case est occupée par un pion rocher");
                }
                else{
                  t.move(in-1);
                }
              }
            }
          }
          break;
          case 3:
          if(t.getPosY() < 10){
            if(game.getMap(t.getPosX(), t.getPosY() - 1).isTokenHere()){
              Token temp = t.find(t.getPosX(), t.getPosY() + 1);
              if(temp instanceof TokenP){
                if(t.getNbMove()>1){
                  t.move(in-1);
                }
                else{
                  System.out.println("Cette case est occupée par un pion d'un joueur");
                }
              }
              else if(temp instanceof TokenR){
                TokenR r = (TokenR) temp;
                r.move(in - 1);
                if(game.getMap(t.getPosX(), t.getPosY() - 1).isTokenHere()){
                  System.out.println("Cette case est occupée par un pion rocher");
                }
                else{
                  t.move(in-1);
                }
              }
            }
          }
          break;
          case 4:
          if(t.getPosY() < 10){
            if(game.getMap(t.getPosX()-1, t.getPosY()).isTokenHere()){
              Token temp = t.find(t.getPosX(), t.getPosY() + 1);
              if(temp instanceof TokenP){
                if(t.getNbMove()>1){
                  t.move(in-1);
                }
                else{
                  System.out.println("Cette case est occupée par un pion d'un joueur");
                }
              }
              else if(temp instanceof TokenR){
                TokenR r = (TokenR) temp;
                r.move(in - 1);
                if(game.getMap(t.getPosX()-1, t.getPosY()).isTokenHere()){
                  System.out.println("Cette case est occupée par un pion rocher");
                }
                else{
                  t.move(in-1);
                }
              }
            }
          }
          break;
        }
      }
    }while (t.isActif());
    scan.close();
    game.getMap(posX, posY).setTokenHere();
  }
}
