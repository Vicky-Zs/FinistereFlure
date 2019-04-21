package database;
import character.Player;
import java.sql.*;
import java.util.HashSet;
import token.Token;
import java.lang.String;

public class DataBase implements Parametre {       
        Connection con = null;
        ResultSet res;
        String demande;

        Player p = new Player(HashSet<Token> token , String pseudo);
        
    public void openDataBase(){
        try{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        System.out.println("Database Connected ");
        Statement stmt = con.createStatement();
        }
        catch(Exception e){
            System.out.println("Error Database");
        }
    }
    
    public void addPlayer(){
        String pseudo = p.getPseudo();
        int tokenMort = 4 - p.getNbToken();
        int tokenVivant = p.getNbToken();
        demande = "INSERT INTO player(Pseudo,TokenAlive,TokenDead) VALUES("+pseudo+tokenVivant+tokenMort+")";
    }
    
    private void closeConnexion() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Database connection terminated.");
            } catch (Exception e) {}
        }
    }
}
