package character;
import finstereflure.*;
import map.*;
import token.*;
import map.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Scanner;
/**
 *
 * @author Nicolas
 */
public class Player {
  private HashSet<TokenP> token = new HashSet<>(); // encapsulation en TokenP apres
  protected String pseudo;
  private String mail;
  private String password;
  private boolean connected = false;
  /**
   * Constructeur de la classe Player
   * @param pseudo   [Pseudo du joueur]
   * @param playerId [Automatique]
   * @param g        [Permet de récuperer les infos du jeu]
   */
  public Player(String pseudo, int playerId, Game g){
    this.pseudo = pseudo;
    this.token.add(new TokenP(g, 1, playerId));
    this.token.add(new TokenP(g, 3, playerId));
    this.token.add(new TokenP(g, 4, playerId));
    this.token.add(new TokenP(g, 5, playerId));
    if(!(this instanceof IA)){
      this.mail = mail();
      this.password = password();
    }
  }
  /**
   * Permet d'enregister un mail
   * @return [mail]
   */
  private void mail(){
    Scanner sc = new Scanner(System.in);
    System.out.println("Veuillez votre adressse mail :");
    String mail = sc.nextLine();
    System.out.println("Voici votre adresse mail  : " + mail);
    sc.close();
  }
  /**
   * Permet d'enregister un mot de passe
   * @return [mot de passe]
   */
  private String password(){
    Scanner sc = new Scanner(System.in);
    System.out.println("Veuillez votre mot de passe :");
    String password = sc.nextLine();
    sc.close();
    return password;
  }
  /**
   * Permet de savoir le nombre de token
   * @return [nombre de token du joueur]
   */
  public int getNbToken(){
    // permet de recuperer le nombre de tokens encore en vie
    int cpt = 0;
    for(TokenP t : token){
      cpt++;
    }
    return cpt;
  }
  /**
   * Permet de récuperer le mail d'un joueur
   * @return [mail]
   */
  public String getMail(){
    return mail;
  }
  /**
   * Permet de récuperer le nombre de partie jouée
   * @return [nombre de partie jouée]
   */
  public int getGame(){
    return partieJouee=0;
  }
  /**
   * Permet de récupérer le Pseudo d'un joueur
   * @return [Pseudo joueur]
   */
  public String getPseudo(){
    return pseudo;
  }
  /**
   * Permet de récupérer le mot de passe d'un joueur
   * @return [Mot de passe joueur]
   */
  public String getPassword(){
    return password;
  }
  /**
   * Permet de savoir si vous êtes connecté
   * @return [boolean Connexion]
   */
  public boolean getConnected(){
    return connected;
  }
  /**
   * Permet de modifier le boolean de la Connexion
   * @param isCo [Connexion]
   */
  public void setConnected(boolean isCo){
    this.connected = isCo;
  }
  /**
   * Réécriture de la méthode toString
   * @return [Phrase]
   */
  @Override
  public String toString(){
    return this.pseudo +" tu as encore "+getNbToken()+" tokens.";
  }
  /**
   * Permet de savoir s'il reste des Tokens dans la liste du joueur
   * @return [Boolean Vide]
   */
  public boolean isEmpty() {
    return token.isEmpty();
  }
  /**
   * Permet de récupérer la liste des tokens d'un joueur
   * @return [liste token joueur]
   */
  public HashSet getToken() {
    return token;
  }
  /**
   * Permet de récupérer un token particulier
   * @param  pattern [Pattern A ou B]
   * @return         [Token avec le pattern A ou B]
   */
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
