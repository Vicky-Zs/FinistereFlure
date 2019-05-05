package finstereflure;

import DB.DataBase;
import character.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import map.*;
import token.*;

public class Main {

    // public static final Game g = new Game();

    public static void main(String[] args){
      //Test.main();
      Scanner scan = new Scanner(System.in);
      int in;
      
      do {
          System.out.println("Veuillez entrer le nombre de Joueurs (1 ou 2)");
          in = scan.nextInt();
          if ((in != 1) || (in !=2)) {
              System.out.print("Nombre non accepté");
          }
      }while ((in == 1) || (in == 2));
      if (in == 1) {
          
      }
    }

}
