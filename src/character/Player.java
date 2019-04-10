package character;

import java.util.HashSet;
import java.util.Scanner;
import finistereflure.*;
import map.*;
import token.*;

/**
 *
 * @author Nicolas
 */

public class Player extends Character {
  private HashSet<Token> token = new HashSet<>(); // encapsulation en TokenP apres
  private String pseudo;


                /*Constructeur de la class player*/

  public Player(HashSet<Token> token, String nomPerso){
      for(Token p : token){
          this.token.add(p);
      }
      this.pseudo = nomPerso;
  }

                /*  Méthodes  */
  private void nomPerso(){
      Scanner sc = new Scanner(System.in);
      System.out.println("Veuillez votre pseudo :");
      String pseudo = sc.nextLine();
      System.out.println("Voici ton p'tit nom  : " + pseudo);
  }

  private int getNbToken(){
      // premet de recuperer le nombre de tokens encore en vie
      int cpt = 0;
      for(Token p : token){
          cpt++;
      }
      return cpt;
  }


  @Override
  public String toString(){
      return this.pseudo +" tu as encore "+getNbToken()+" tokens.";
  }
}
