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
    temp.move(0);
    System.out.println("Le token s'est déplacé au Nord, nouvelle position [" + temp.getPosX() + ";" + temp.getPosY() + "]");
    System.out.println();
  }

}
