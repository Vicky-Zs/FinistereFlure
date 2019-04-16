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
  private Token token = new TokenM(Main.g, 0, 0); // Mise à jour du token

  private int getPositionX(){
      return this.token.getPosX();
  }

   private int getPositionY(){
      return this.token.getPosY();
  }

  @Override
   public String toString(){
       return "Le montre est à la position "+getPositionX()+","+getPositionY();
   }
}
