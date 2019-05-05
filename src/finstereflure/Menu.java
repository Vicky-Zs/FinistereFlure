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

public class Menu extends Game {
  public void main(){
    Scanner scan = new Scanner(System.in);
    int in;
    for(int i = 0; i < p.length; i++) {
      System.out.println("C'est au tour de " + p[i].getPseudo());
      for (Object o : p[i].getToken()){
        if (o instanceof TokenP){
          TokenP t = (TokenP) o;
          if (!t.isWin()) {
            System.out.println(t);
          }
        }
      }
      System.out.println("Veuillez choisir votre token (avec le nombre de déplacement)");
      in = scan.nextInt();
      menuToken(takeToken(in));
    }
  }

  private TokenP takeToken(int i){
    for (Object o : p[i].getToken()){
      if (o instanceof TokenP) {
        TokenP t = (TokenP) o;
        if (t.getPatternA() == i) {
          return t;
        }
        else if (t.getPatternB() == i) {
          return t;
        }
      }
    }
    return null;
  }

  private void menuToken(TokenP t) {
    Scanner scan = new Scanner(System.in);
    int i;
    t.setNbMove(nbTurn%2==1);
    do {
      i = getNbMove();
      if ((t.getPosX() == -1) && (t.getPosY() == -1)) {
        t.move(0);
      }
      else {
        System.out.println("Dans quel direction voulez-vous bougez ? \n1 = Nord, 2 = Est, 3 = Sud, 4 = Ouest");
        int in = scan.nextInt();
        t.move(in-1);
      }
    }while (i!=0);
  }
}
