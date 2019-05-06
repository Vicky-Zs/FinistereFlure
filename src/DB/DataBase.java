package DB;
import character.Player;
import java.sql.*;
import java.util.HashSet;
import token.Token;
import java.lang.String;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase implements Parametre {       
        private Connection con = null;
        private ResultSet res;
        private String demande;
        private String comptePseudo;
        private String compteMdp;
        
    private String getPseudo(){
        return this.comptePseudo;
    }
    
    private String getPassword(){
        return this.compteMdp;
    }
        
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
                 
             } catch (Exception e) {}
     }
             
    public boolean verifCompte(String aVerif,Connection co)throws SQLException{
            boolean exist = false;
            Statement verif = co.createStatement();
            ResultSet res = verif.executeQuery("SELECT COUNT(*) FROM compte WHERE Pseudo = "+aVerif);
            while(res.next()){
                if(res.getInt("COUNT(*)") >=1){
                    exist = true;
                }
            }
            return exist;
    }
    
    public void changerPseudo(Connection co)throws SQLException{
        /*Partie ou l'on gere le changement de pseudo*/
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
    }
        
    public void changerMdp(Connection co) throws SQLException{
                Scanner sc = new Scanner(System.in);
                System.out.println("Votre nouveau mot de passe c'est ? ");
                String mdp = new String();
                mdp = sc.nextLine();
                mdp="'"+mdp+"'";
        Statement changeMDP = co.createStatement();
        changeMDP.executeUpdate("UPDATE compte SET Password ="+mdp+"WHERE Pseudo ="+this.comptePseudo);
    }
        
    public void changerMail(Connection co)throws SQLException{
                Scanner sc = new Scanner(System.in);
                System.out.println("Votre nouvelle adrese mail c'est ? ");
                String mail = new String();
                mail = sc.nextLine();
                mail="'"+mail+"'";
        Statement changeEmail = co.createStatement();
        changeEmail.executeUpdate("UPDATE compte SET Email ="+mail+"WHERE Pseudo ="+this.comptePseudo);   
    }
    
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
        }else{
                System.out.println("\nAucun compte existant\n");
            }
        return connect;
    }
    
    //SELECT Password FROM `compte` WHERE Pseudo = 'nico' 
    public boolean decoUser(boolean connected){
        return !connected;
    }

}