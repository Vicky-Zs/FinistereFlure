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
  /**
   * Default constructor
   * @param g [Lien pour récupérer les infos du jeu]
   */
  public Monster(Game g){
    this.myTokenM = new TokenM(g);
  }
  /**
   * Permet de récupérer la position X
   * @return [Position X]
   */
  private int getPositionX(){
    return this.myTokenM.getPosX();
  }
  /**
   * Permet de récupérer la position Y
   * @return [Position Y]
   */
  private int getPositionY(){
    return this.myTokenM.getPosY();
  }
  /**
   * Permet de récupérer le token du monstre
   * @return [Token Monstre]
   */
  public TokenM getToken(){
    return this.myTokenM;
  }
  /**
   * Réécriture de la méthode toString
   * @return [Phrase]
   */
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
