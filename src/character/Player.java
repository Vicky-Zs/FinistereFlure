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
  private HashSet<TokenP> token = new HashSet<>(); // encapsulation en TokenP apres
  protected String pseudo;
  private String adresseMail;
  private String password;
  private boolean connected = false;
  private int partieJouee = 0;


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
  
    private String mail(){
      Scanner sc = new Scanner(System.in);
      System.out.println("Veuillez votre adressse mail :");
      String pseudo = sc.nextLine();
      System.out.println("Voici votre adresse mail  : " + adresseMail);
      return pseudo;
  }
    
    private String password(){
      Scanner sc = new Scanner(System.in);
      System.out.println("Veuillez votre mot de passe :");
      String pseudo = sc.nextLine();
      return pseudo;
  }
   
  public int getNbToken(){ // Modif: Private -> Protected
      // permet de recuperer le nombre de tokens encore en vie
      int cpt = 0;
      for(TokenP t : token){
          cpt++;
      }
      return cpt;
  }

  public String getMail(){
      return adresseMail;
  }
  
    public int getGame(){
      return partieJouee=0;
  }
  
  
  public String getPseudo(){
      return pseudo;
  }
  
  public String getPassword(){
      return password;
  }
  
  public boolean getConnected(){
      return connected;
  }

  public void setConnected(boolean isCo){
      this.connected = isCo;
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

  public TokenP getToken(int pattern) {
      
      for (TokenP t : token) {
          if (t instanceof TokenP) {
              TokenP tP = (TokenP) t;
              if (tP.getPatternA() == pattern) {
                  return t;
              }
              else if (tP.getPatternB() == pattern) {
                  return t;
              }
          }
      }
      return null;
  }
}
