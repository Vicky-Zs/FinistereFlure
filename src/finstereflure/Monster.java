
package finstereflure;

public class Monster extends Character {
  private Token token = new TokenM();
  
  private int getPositionX(){
      return this.token.posX;
  }
  
   private int getPositionY(){
      return this.token.posY;
  }
   
   private void setPosX(int set){
       this.token.posX = set;
   }


   private void setPosY(int set){
       this.token.posY = set;
   }
   
   
  @Override
   public String toString(){
       return "The monster is à la position"+getPositionX()+','+getPositionY();
   }
}
