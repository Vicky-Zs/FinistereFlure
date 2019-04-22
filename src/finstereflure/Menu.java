/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstereflure;
import character.*;
import map.*;
import token.*;

/**
 *
 * @author Cédric
 */

public class Menu extends Game {
  public void main(){
    for(int i = 0; i < p.length; i++) {
      System.out.println("C'est au tour de " + p[i].getPseudo());

    }
  }

  /*private TokenP chooseToken(int i){
    for (Token t : p[i].getToken()){
      
    }
  }*/
}
