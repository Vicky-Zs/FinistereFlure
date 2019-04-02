
package finstereflure;

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
       return "The monster is à la position"+getPositionX()+','+getPositionY();
   }
}
