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
  private Scanner scan = new Scanner(System.in);
  private int in;
  public void main(){
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
    }
  }

  private TokenP chooseToken(int i){
    for (Token t : p[i].getToken()){
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
