
package finstereflure;

import java.util.HashSet;
import java.util.Scanner;

public class Player extends Character {
  private HashSet<TokenP> token = new HashSet<>();
  private String pseudo;
  private int tokenRestant;
  
                /*Constructeur de la class player*/
  
  public Player(HashSet<TokenP> token, String nomPerso){
      for(TokenP p : token){
          this.token.add(p);
      }
      this.pseudo = nomPerso;
  }

                /*  Méthodes  */
  public void nomPerso(){
      Scanner sc = new Scanner(System.in);
      System.out.println("Veuillez votre pseudo :");
      String pseudo = sc.nextLine();
      System.out.println("Voici ton p'tit nom  : " + pseudo);
  }
  
  public int getNbToken(){
      // premet de recuperer le nombre de tokens encore en vie
      int cpt = 0;
      for(TokenP p : token){
          cpt++;
      }
      return cpt;
  }
  
  @Override
  public String toString(){
      return this.pseudo +" tu as encore "+getNbToken()+" tokens.";
  }
}
