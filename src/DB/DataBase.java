package DB;
import character.Player;
import java.sql.*;
import java.util.HashSet;
import token.Token;
import java.lang.String;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase implements Parametre {       
        Connection con = null;
        ResultSet res;
        String demande;
        
    public static Connection openBDD(){
         Connection con = null;
         ResultSet res;
         String demande;

         try{
             Class.forName("com.mysql.jdbc.Driver").newInstance();

             con = DriverManager.getConnection(chemin, user, password );
             System.out.println("Database Connected ");
             System.out.println(con); 
         }
         catch(Exception e){
                 System.out.println("Erreur");
             }
         return con;
     }

     public static Connection closeConnexion(Connection co) {

         if (co != null) {
             try {
                 co.close();
                 System.out.println("Database connection terminated.");
             } catch (Exception e) {/*De toute façon il doit se fermer*/}
         }
         return co;
     }

     public void supprimerLigne(Player p,Connection co){
             try {
                 Statement sup = co.createStatement();
                 String pseudo = "'"+p.getPseudo()+"'";
                 sup.executeUpdate("DELETE FROM game"+" WHERE Pseudo = "+pseudo);
             } catch (Exception e) {}
     }

     public void insererLigne(Player p, Connection co){
             try {
                 int dead = 4 - p.getNbToken();
                 Statement statement = co.createStatement();
                 statement.executeUpdate("INSERT INTO game"+" VALUES ("+p.getPseudo()+","+p.getNbToken()+","+dead+")");
             } catch (Exception e) {}
         }

     public ResultSet lireScore(Connection co) throws SQLException{
                 /*Fonction pour pouvoir lire une info*/
             Statement st = co.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM game ");

             while (res.next()) {
                 System.out.println(res.getString("Pseudo")+" avec "+res.getInt("ScoreTokenAlive")+" token(s) vivant(s) et "+res.getInt("ScoreTokenDead")+" token(s) mort(s)");
                }
             return res;
     }
}
