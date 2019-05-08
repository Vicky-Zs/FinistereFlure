/*

import character.*;
import java.util.Scanner;
import map.*;
import token.*;
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstereflure;
import java.util.Scanner;
import map.*;
import token.*;

/**
 *
 * @author Cédric
 */

public class Menu {
    private Game game;
    
    public Menu(Game g){
        this.game = g;
    }
    
    public void main(){
    Scanner scan = new Scanner(System.in);
    int in;
    for(int i = 0; i < game.getPlayers().length; i++) {
      System.out.println("C'est au tour de " + game.getPlayer(i).getPseudo());
      for (Object o : game.getPlayer(i).getToken()){
        if (o instanceof TokenP){
          TokenP t = (TokenP) o;
          if (!t.isWin()) {
            System.out.println(t);
          }
        }
      }
      System.out.println("Veuillez choisir votre token (avec le nombre de déplacement)");
      in = scan.nextInt();
      menuToken(takeToken(i, in));
    }
  }

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

  private void menuToken(TokenP t) {
    Scanner scan = new Scanner(System.in);
    int i;
    t.setNbMove(game.getNbTurn()%2==1);
    do {
      if ((t.getPosX() == -1) && (t.getPosY() == -1)) {
        t.move(0);
      }
      else {
        System.out.println("Dans quel direction voulez-vous bougez ? \n1 = Nord, 2 = Est, 3 = Sud, 4 = Ouest \n 0 = Fin du tour en cours");
        int in = scan.nextInt();
        if (in == 0){
            t.turnOff();
        }
        else if (in>0 && in<5){
           t.move(in-1);
           System.out.println(t);
           System.out.println("Nombre de déplacement restant : "+t.getNbMove()); 
        }
        else{
            System.out.println("Cette réponse n'est pas valide");
        }
      }
    }while (t.isActif());
  }
}
