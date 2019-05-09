package DB;
import character.Player;
import java.sql.*;
import java.util.HashSet;
import token.Token;
import java.lang.String;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class DataBase implements Parametre {
  private Connection con = null;
  private ResultSet res;
  private String demande;
  private String comptePseudo;
  private String compteMdp;
  /**
   * Permet de récupérer le Pseudo
   * @return [pseudo]
   */
  private String getPseudo(){
    return this.comptePseudo;
  }
  /**
   * Récupère le mot de passe
   * @return [motdepasse]
   */
  private String getPassword(){
    return this.compteMdp;
  }
  /**
   * Permet de se connecter
   * @return [Connection]
   */
  public static Connection openBDD(){
    Connection con = null;
    ResultSet res;
    String demande;
    try{
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      con = DriverManager.getConnection(chemin, user, password);
      System.out.println("Database Connected ");
      System.out.println(con);
    }
    catch(Exception e){
      System.out.println("Erreur");
    }
    return con;
  }

  /**
   * Coupe la connexion
   * @param  co [L'ancienne connexion]
   */
  public static void closeConnexion(Connection co) {
    // permet de se connecter à la DB
    if (co != null) {
      try {
        co.close();
        System.out.println("Database connection terminated.");
      } catch (Exception e) {}}
  }
  /**
   * Permet de supprimer une ligne du tableau des scores
   * @param p  [Joueur]
   * @param co [Connexion]
   */
  public void supprimerLigneScore(Player p,Connection co){
    try {
      Statement sup = co.createStatement();
      String pseudo = "'"+p.getPseudo()+"'";
      sup.executeUpdate("DELETE FROM game"+" WHERE Pseudo = "+pseudo);
    }catch (Exception e){}}
    /**
     * Permet d'insérer dans le tableau des scores une nouvelle ligne
     * @param p  [Joueur]
     * @param co [Connexion]
     */
  public void insererLigne(Player p, Connection co){
    try {
      int dead = 4 - p.getNbToken();
      Statement insert = co.createStatement();
      insert.executeUpdate("INSERT INTO game"+" VALUES ("+p.getPseudo()+","+p.getNbToken()+","+dead+")");
    } catch (Exception e) {}}
    public ResultSet lireScore(Connection co) throws SQLException{
      /*Fonction pour pouvoir lire une info*/
      Statement readScore = co.createStatement();
      ResultSet res = readScore.executeQuery("SELECT * FROM game ");
      while (res.next()) {
        System.out.println(res.getString("Pseudo")+" avec "+res.getInt("ScoreTokenAlive")+" token(s) vivant(s) et "+res.getInt("ScoreTokenDead")+" token(s) mort(s)");
      }
      return res;
    }
    /**
     * Permet de créer un compte
     * @param co [Connexion]
     */
    public void creerCompte(Connection co){
      Scanner sc = new Scanner(System.in);
      System.out.println("Inserez un pseudo ");
      String pseudo = new String();
      pseudo = sc.nextLine();
      pseudo="'"+pseudo+"'";
      System.out.println("Inserez un mot de passe ");
      String mdp = new String();
      mdp = sc.nextLine();
      mdp="'"+mdp+"'";
      System.out.println("Inserez une adresse mail ");
      String mail = new String();
      mail = sc.nextLine();
      mail="'"+mail+"'";
      try {
        boolean verif = false;
        // variables permettant de gerer le cas ou 2 personnes ont le meme pseudo
        verif = verifCompte(pseudo,co);
        while(verif == true){
          System.out.println("Pseudo déjà existant");
          String newPseudo = new String();
          newPseudo = sc.nextLine();
          newPseudo="'"+newPseudo+"'";
          verif = verifCompte(newPseudo,co);
          pseudo=newPseudo;
        }
        Statement create = co.createStatement();
        create.executeUpdate("INSERT INTO compte"+" VALUES ("+pseudo+","+mail+","+mdp+",'0')");
      } catch (Exception e) {}}
    public boolean verifCompte(String aVerif,Connection co)throws SQLException{
      boolean exist = false;
      Statement verif = co.createStatement();
      ResultSet res = verif.executeQuery("SELECT COUNT(*) FROM compte WHERE Pseudo = "+aVerif);
      while(res.next()){
        if(res.getInt("COUNT(*)") >=1){
          exist = true;
        }
      }
      sc.close();
      return exist;
    }
    /**
     * Permet de changer un pseudo
     * @param  co           [Connexion]
     * @throws SQLException
     */
    public void changerPseudo(Connection co)throws SQLException{
      Scanner sc = new Scanner(System.in);
      System.out.println("Votre nouveau pseudo c'est ? ");
      String pseudo = new String();
      pseudo = sc.nextLine();
      pseudo="'"+pseudo+"'";
      /*Partie on va chercher le pseudo*/
      String compte = this.comptePseudo;
      /*Partie requete*/
      Statement changePseudo = co.createStatement();
      changePseudo.executeUpdate("UPDATE compte SET Pseudo ="+pseudo+"WHERE Pseudo ="+compte);
      changePseudo.executeUpdate("UPDATE game SET Pseudo ="+pseudo+"WHERE Pseudo ="+compte);
      sc.close();
    }
    /**
     * Permet de changer un mot de passe
     * @param  co           [Connexion]
     * @throws SQLException
     */
    public void changerMdp(Connection co) throws SQLException{
      Scanner sc = new Scanner(System.in);
      System.out.println("Votre nouveau mot de passe c'est ? ");
      String mdp = new String();
      mdp = sc.nextLine();
      mdp="'"+mdp+"'";
      Statement changeMDP = co.createStatement();
      changeMDP.executeUpdate("UPDATE compte SET Password ="+mdp+"WHERE Pseudo ="+this.comptePseudo);
      sc.close();
    }
    /**
     * Permet de changer une adresse mail
     * @param  co           [Connexion]
     * @throws SQLException
     */
    public void changerMail(Connection co)throws SQLException{
      Scanner sc = new Scanner(System.in);
      System.out.println("Votre nouvelle adrese mail c'est ? ");
      String mail = new String();
      mail = sc.nextLine();
      mail="'"+mail+"'";
      Statement changeEmail = co.createStatement();
      changeEmail.executeUpdate("UPDATE compte SET Email ="+mail+"WHERE Pseudo ="+this.comptePseudo);
      sc.close();
    }
    /**
     * Permet de se connexter en tant qu'utilisateur
     * @param  co           [Connexion]
     * @return              [boolean pour savoir si l'identifiant et le mot de passe correspond]
     * @throws SQLException
     */
    public boolean coUser(Connection co)throws SQLException{
      Scanner sc = new Scanner(System.in);
      System.out.println("Entrez votre pseudo : ");
      String pseudo = new String();
      pseudo = sc.nextLine();
      pseudo="'"+pseudo+"'";
      System.out.println(pseudo);
      this.comptePseudo =pseudo;
      System.out.println("Entrez votre password : ");
      String mdp = new String();
      mdp = sc.nextLine();
      this.compteMdp = mdp;
      // déjà regarder si le compte exist
      //boolean exist = this.verifCompte(pseudo, co);
      boolean exist = true;
      boolean connect = false;
      String keyWord = null;
      //si inexistant boolean reste à faux
      if(exist == true){
        //si existant regarder si il a selectionné le bon mot de passe
        Statement isCo = co.createStatement();
        ResultSet res = isCo.executeQuery("SELECT Password FROM `compte` WHERE Pseudo = "+pseudo);
        while (res.next()) {
          String em = res.getString("Password");
          keyWord = em.replace("\n", ",");
        }
        //keyWord =keyWord;
        System.out.println(keyWord+" = "+mdp);
        if(mdp == null ? keyWord == null : mdp.equals(keyWord)){
          connect = true;
        }
      }
      else{
        System.out.println("\nAucun compte existant\n");
      }
      sc.close();
      return connect;
    }

    //SELECT Password FROM `compte` WHERE Pseudo = 'nico'
    /**
     * Permet de se déconnecter en utilisateur
     * @param  connected [Boolean pour savoir si une personne est connectée]
     * @return           [Boolean pour savoir si une personne est connectée]
     */
    public boolean decoUser(boolean connected){
        return !connected;
    }

}
