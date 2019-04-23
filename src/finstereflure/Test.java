/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstereflure;

import character.*;
import map.*;
import token.*;
import static finstereflure.Main.g;

/**
 *
 * @author Cédric
 */

public class Test extends Game {
  public static void main() {
    Token temp;
    g.iniMap();
    for (int i = 0; i < nbPlayers; i++) {
      g.p[i] = new Player(IA.defineName(), i, g);
    }
    System.out.println("Deux joueurs ont été créé avec des noms aléatoires");
    System.out.println("Ceci est une test automatique du jeu");
    g.iniDecorations(0); // Décoration pour le test
    temp = g.p[0].getToken(5);
    System.out.println(temp);
    //Test décors
    for (int i = 0; i < 16; i++){
        for (int j = 0; j < 11; j++){
            for (int k = 0; k < 4; k++){
                if (g.getMap(i, j).getWall(k)){
                    System.out.print("La case ["+i+";"+j+"] un mur au ");
                    switch (k) {
                        case 0:
                            System.out.print("Nord");
                        break;
                        case 1:
                            System.out.print("Est");
                        break;
                        case 2:
                            System.out.print("Sud");
                        break;
                        case 3:
                            System.out.print("Ouest");
                        break;
                        case 4:
                            System.out.println("Problème");
                        break;
                    }
                    System.out.println("");
                }
                
            }
            System.out.println("La case ["+i+";"+j+"] : Sang = " + g.getMap(i, j).isBloodspot());
            System.out.println("");
        }
    }
    if (temp instanceof TokenP){
        TokenP tP = (TokenP) temp;
        System.out.println(tP);
        tP.move(0);
        System.out.println("Le token s'est déplacé, nouvelle position [" + tP.getPosX() + ";" + tP.getPosY() + "]");
        System.out.println(tP.getNbMove());
        tP.move(0);
        tP.move(0);
        System.out.println("Le token s'est déplacé, nouvelle position [" + tP.getPosX() + ";" + tP.getPosY() + "]");
        System.out.println(tP.getNbMove());
        g.setTurnPlayers(false);
    }
    temp = g.getMonster().getToken();
    if (temp instanceof TokenM){
      TokenM tM = (TokenM) temp;
      System.out.println("Début du tour !!!");
      tM.tour();
      System.out.println("Fin du tour !!!");
    }
  }

}
