package character;
import finstereflure.*;
import map.*;
import token.*;
/**
 *
 * @author Nicolas
 */
public class Monster {
  private TokenM myTokenM;
  // Mise à jour du token (en espérant que ça marche)
  public Monster(Game g){
    this.myTokenM = new TokenM(g);
  }
  private int getPositionX(){
    return this.myTokenM.getPosX();
  }
  private int getPositionY(){
    return this.myTokenM.getPosY();
  }
  public TokenM getToken(){
    return this.myTokenM;
  }
  @Override
  public String toString(){
    String orientation;
    switch (this.myTokenM.getOrientation()){
      case 0:
      orientation = "Nord";
      break;
      case 1:
      orientation = "Est";
      break;
      case 2:
      orientation = "Sud";
      break;
      case 3:
      orientation = "Ouest";
      break;
      default:
      orientation = "Probleme !";
      break;
    }
    return "Le monstre est à la position ["+getPositionX()+";"+getPositionY()+"] ; Orientation : " + orientation;
   }
}
