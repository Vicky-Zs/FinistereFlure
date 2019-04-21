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
        
    private void openConnexion() {
        String connectUrl = "jdbc:mysql://mysql-finstereflure.alwaysdata.net:3306";
        if (con != null) {
            this.closeConnexion();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(connectUrl, username, password);
            System.out.println("Database connection established.");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Cannot load db driver: com.mysql.jdbc.Driver");
            cnfe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erreur inattendue");
            e.printStackTrace();
        }
    }
    
    public void addPlayer(){
        String pseudo = p.getPseudo();
        int tokenMort = 4 - p.getNbToken();
        int tokenVivant = p.getNbToken();
        demande = "INSERT INTO player(Pseudo,TokenAlive,TokenDead) VALUES("+pseudo+","+tokenVivant+","+tokenMort+")";
        Statement state = conn.createStatement();
        state.execute(demande);
    }
    
    
    private void closeConnexion() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Database connection terminated.");
            } catch (Exception e) {/*De toute façon il doit se fermer*/}
        }
    }
}
