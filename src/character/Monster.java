package character;

import finstereflure.*;
import map.*;
import token.*;

/**
 * 
 *
 * @author Nicolas
 */

public class Monster {
  private Token token = new TokenM(Main.g); // Mise à jour du token

  private int getPositionX(){
      return this.token.getPosX();
  }

   private int getPositionY(){
      return this.token.getPosY();
  }
   
   public Token getToken(){
       return token;
   }

  @Override
   public String toString(){
       return "Le montre est à la position "+getPositionX()+","+getPositionY();
   }
}
