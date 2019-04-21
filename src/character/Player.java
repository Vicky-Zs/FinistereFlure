package character;

import finstereflure.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;
import map.*;
import token.*;

import java.util.Scanner;
import map.*;

/**
 *
 * @author Nicolas
 */

public class Player {
  private HashSet<Token> token = new HashSet<>(); // encapsulation en TokenP apres
  protected String pseudo;


                /*Constructeur de la class player*/

  /*public Player(HashSet<Token> token, String nomPerso){
      for(Token p : token){
          this.token.add(p);
      }
      this.pseudo = nomPerso;
  }*/

  public Player(String pseudo, int playerId, Game g){
    this.pseudo = pseudo;
    this.token.add(new TokenP(g, 1, playerId));
    this.token.add(new TokenP(g, 3, playerId));
    this.token.add(new TokenP(g, 4, playerId));
    this.token.add(new TokenP(g, 5, playerId));
  }

                /*  Méthodes  */
  private String pseudo(){
      Scanner sc = new Scanner(System.in);
      System.out.println("Veuillez votre pseudo :");
      String pseudo = sc.nextLine();
      System.out.println("Voici ton p'tit nom  : " + pseudo);
      return pseudo;
  }

  public int getNbToken(){ // Modif: Private -> Protected
      // premet de recuperer le nombre de tokens encore en vie
      int cpt = 0;
      for(Token t : token){
          cpt++;
      }
      return cpt;
  }

  public String getPseudo(){
      return pseudo;
  }

  @Override
  public String toString(){
      return this.pseudo +" tu as encore "+getNbToken()+" tokens.";
  }

  public boolean isEmpty() {
      return token.isEmpty();
  }

  public HashSet getToken() {
      return token;
  }

  public Token getToken(int pattern) {
      for (Token t : token) {
        if (t.getPatternA() == pattern) {
          return t;
        }
        else if (t.getPatternB() == pattern) {
          return t;
        }
      }
      return null;
  }
}
