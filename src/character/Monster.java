package characters;

import finistereflure.*;
import map.*;
import token.*;

/**
 *
 * @author Nicolas
 */

public class Monster extends Character {
  private Token token = new TokenM();

  private int getPositionX(){
      return this.token.posX;
  }

   private int getPositionY(){
      return this.token.posY;
  }

  @Override
   public String toString(){
       return "Le montre est à la position "+getPositionX()+","+getPositionY();
   }
}
