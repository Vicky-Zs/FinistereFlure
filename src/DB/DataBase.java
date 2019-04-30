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
         // permet de se connecter à la DB
         if (co != null) {
             try {
                 co.close();
                 System.out.println("Database connection terminated.");
             } catch (Exception e) {/*De toute façon il doit se fermer*/}
         }
         return co;
     }

     public void supprimerLigneScore(Player p,Connection co){
         // permet de supprimer une ligne dans le tableau de score
             try {
                 Statement sup = co.createStatement();
                 String pseudo = "'"+p.getPseudo()+"'";
                 sup.executeUpdate("DELETE FROM game"+" WHERE Pseudo = "+pseudo);
             } catch (Exception e) {}
     }

     public void insererLigne(Player p, Connection co){
         // permet d'inserer dans la table de score une nouvelle ligne
             try {
                 int dead = 4 - p.getNbToken();
                 Statement insert = co.createStatement();
                 insert.executeUpdate("INSERT INTO game"+" VALUES ("+p.getPseudo()+","+p.getNbToken()+","+dead+")");
             } catch (Exception e) {}
         }

     public ResultSet lireScore(Connection co) throws SQLException{
                 /*Fonction pour pouvoir lire une info*/
             Statement readScore = co.createStatement();
             ResultSet res = readScore.executeQuery("SELECT * FROM game ");

             while (res.next()) {
                 System.out.println(res.getString("Pseudo")+" avec "+res.getInt("ScoreTokenAlive")+" token(s) vivant(s) et "+res.getInt("ScoreTokenDead")+" token(s) mort(s)");
                }
             return res;
     }
     
        public void creerCompte(Player p,Connection co){
            try {
                 boolean verif = false; // variables permettant de gerer le cas ou 2 personnes ont le meme pseudo
                 String pseudo = p.getPseudo();
                 String mail = p.getMail();
                 String mdp = p.getPassword();
                 
                 verif = verifCompte(p.getPseudo(),co);
                 if(verif == true){
                 Statement create = co.createStatement();
                 create.executeUpdate("INSERT INTO compte"+" VALUES ("+pseudo+","+mail+","+mdp+")");
                 }else{
                    Statement create = co.createStatement();
                   pseudo = pseudo+"1";
                    create.executeUpdate("INSERT INTO compte"+" VALUES ("+pseudo+","+mail+","+mdp+")");
                 }
             } catch (Exception e) {}
        }
        
        public void changerPseudo(Player p,Connection co){}
        
        public void changerMdp(Player p,Connection co){}
        
        public void changerMail(Player p,Connection co){}
        
     
    private boolean verifCompte(String pseudo,Connection co){
        boolean exist = false;
        try{
            Statement verif = co.createStatement();
            ResultSet res = verif.executeQuery("SELECT COUNT(*) FROM compte WHERE Pseudo = "+pseudo);
        }catch(Exception e){}
        return exist;
    }
}
