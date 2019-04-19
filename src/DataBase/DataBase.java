package DataBase;
import java.sql.*;

public class DataBase implements Parametre {       
        Connection con = null;
        ResultSet res;
        String demande;
        
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
    
    public void closeDataBase(){
        if(con!=null){
                try{
                    con.close();
                    System.out.println("DataBase connection terminated");
                }catch(Exception e){
            }
        }
    }
}
