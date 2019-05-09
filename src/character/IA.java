package character;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import finstereflure.*;
import map.*;
import token.*;

/**
 *
 * @author Nicolas
 */

public class IA extends Player {
  private int destination = -1;
  private TokenP caseSelected;
  private TokenP pionSelected;
  private int nbRules = 20;

  /**
   * Constructeur de l'IA
   * @param playerId [Numéro du joueur]
   * @param g        [Permet de récupérer des infos du jeu]
   */
  public IA(int playerId, Game g) {
    super(defineName(), playerId, g);
  }
  /**
   * Permet de sélectionner un nombre aléatoire
   * @return [Pseudo de l'IA]
   */
  public static String defineName(){ //Modification en Static
    LinkedList<String> nomIA = new LinkedList<>();
    String name;
    nomIA.add("HAL 9000");
    nomIA.add("Deep Blue");
    nomIA.add("Erasme");
    nomIA.add("Skynet");
    nomIA.add("Omnius");
    nomIA.add("Multivac");
    nomIA.add("Marvin");
    nomIA.add("ED 209");
    nomIA.add("R2D2");
    for(int i = 0;i<nomIA.size();i++){ //Permet de mélanger les noms
      Collections.shuffle(nomIA);
    }
    name = nomIA.getFirst();
    return name;
  }
  /**
   * Permet de savoir le pseudo et le nombre de token
   * @return [Phrase]
   */
  @Override
  public String toString(){
    return super.pseudo +", il vous reste "+super.getNbToken()+" encore en vie";
  }

  /**
   * Permet de générer le tour de l'IA
   */
  public void tourIA(){
    this.turn(constructInitList());
  }

  /**
   * Début de la liste de l'IA
   *
   * @param list [description]
   */
  private void turn(LinkedList<Integer> list){
    //Parcours une seule fois, les règles dans l'ordre croissant de leur numéro
    //Règle n°0 --> Règle n°5 : selection des TokenP
    for(int p=1; p<7; p++){
      if((this.getToken(p) != null) && (!this.hasSelect())){
        if(this.getToken(p).isActif() && !this.getToken(p).isWin()){
          this.select(this.getToken(p));
          this.turn(this.constructNewList(list, (p-1)));
        }
      }
    }
    //Rule n°6 : when a TokenP is near to exit, it exit
    if(this.hasSelect()){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (this.pionSelected.winningCondition())){
        this.pionSelected.move(0);
        this.pionSelected = null;
        this.caseSelected = null;
        this.destination = -1;
      }
    }
    //Rule n°7 : when a TokenP is out, it can start to the boardgame
    if(this.hasSelect()){
      if((this.pionSelected.isActif()) && (this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (!this.hasDestination())){
        this.destination = 3;
        this.futurCaseTokenP(destination);
        this.turn( this.constructNewList(list,7));
      }
    }
    //Rule n°8 : if a TokenP can move on the North, notice it
    if(this.hasSelect()){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (!this.hasDestination())
      && (this.pionSelected.canMove(0))){
        this.destination = 0;
        this.futurCaseTokenP(destination);
        this.turn(this.constructNewList(list));
      }
    }
    //Rule n°9 : if a TokenP can move on the West, notice it
    if(this.hasSelect()){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (!this.hasDestination())
      && (this.pionSelected.canMove(3))){
        this.destination = 3;
        this.futurCaseTokenP(destination);
        this.turn(this.constructNewList(list));
      }
    }
    //Rule n°10 : if a TokenP can move on the Sud, notice it
    if(this.hasSelect()){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (!this.hasDestination())
      && (this.pionSelected.canMove(2))){
        this.destination = 2;
        this.futurCaseTokenP(destination);
        this.turn(this.constructNewList(list));
      }
    }
    //Rule n°11 : if a TokenP can move on the East, notice it
    if(this.hasSelect()){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (!this.hasDestination())
      && this.pionSelected.canMove(1)){
        this.destination = 1;
        this.futurCaseTokenP(destination);
        this.turn(this.constructNewList(list));
      }
    }
    //Rule n°12 : if a TokenP can finnaly just stay, notice it
    if(this.hasSelect()){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (!this.hasDestination())
      && (this.pionSelected.canStay()) && (!this.pionSelected.isClose())
      && (!this.pionSelected.isAxisMonster())){
        this.pionSelected.turnOff();
        this.pionSelected = null;
        this.caseSelected = null;
        this.destination = -1;
      }
    }
    //Rule n°13 : if a TokenP is really close from exit without to be endanger, it goes
    if(this.hasSelect() && this.hasDestination()){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin())
      && (this.hasDestination()) && (this.pionSelected.isSave())){
        this.pionSelected.move(destination);
        this.caseSelected = null;
        this.destination = -1;
        this.turn( this.constructNewList(list) );
      }
    }
    //Rule n°14 : if there is an endanger TokenP, move to brain the monster (dependent from the presence of an ennemy or not)
    if(this.hasSelect() && this.hasDestination()){
      if((this.caseSelected.isTrapped(this.caseSelected.distanceM()))
      && (this.caseSelected.findTokenP(this.caseSelected.distanceM()) != -1)){
        //Rule n°14.1 : if the endanger TokenP isn't an ennemy, save it
        if(this.caseSelected.findTokenP(this.caseSelected.distanceM()) == 1){
          this.pionSelected.move(destination);
          this.pionSelected.turnOff();
          this.pionSelected = null;
          this.caseSelected = null;
          this.destination = -1;
          this.turn( this.constructNewList(list) );
        }
        else{//Rule n°14.2 : if the endanger TokenP is an ennemy, doom it
          if(this.pionSelected.distanceM() < this.caseSelected.distanceM()){
            this.pionSelected.move(destination);
            this.caseSelected = null;
            this.destination = -1;
            this.turn(this.constructNewList(list));
          }
        }
      }
    }
    //Rule n°15 : if a TokenP could go in the axe of the monster, without make it endanger, it can go
    if((this.hasSelect()) && (this.hasDestination())){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (this.caseSelected.isAxisMonster())
      && (this.caseSelected.isActif())){
        this.pionSelected.move(destination);
        this.caseSelected = null;
        this.destination = -1;
        this.turn(this.constructNewList(list));
      }
    }
    //Rule n°16 : if a TokenP could go behind the monster without make it endanger, it can go
    if((this.hasSelect()) && (this.hasDestination())){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (!this.caseSelected.isAxisMonster())
      && (this.caseSelected.isBehind()) && (this.caseSelected.pathNoMonster(destination))){
        this.pionSelected.move(destination);
        this.caseSelected = null;
        this.destination = -1;
        this.turn(this.constructNewList(list));
      }
    }
    //Rule n°17 : if a TokenP is in front of the Monster, it doesn't go more closer him
    if((this.hasSelect()) && (this.hasDestination())){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (!this.caseSelected.isClose())
      && (!this.caseSelected.isAxisMonster()) && (!this.caseSelected.isBehind())
      && (this.pionSelected.distanceM() >= this.caseSelected.distanceM())){
        this.pionSelected.move(destination);
        this.caseSelected = null;
        this.destination = -1;
        this.turn(this.constructNewList(list));
      }
    }
    //Rule n°18 : if a TokenP is close from the monster with low movements, it can't go more closer him
    if(this.hasSelect() && this.hasDestination()){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (this.caseSelected.isClose())
      && (!this.caseSelected.isAxisMonster())
      && (this.pionSelected.distanceM() >= this.caseSelected.distanceM())){
        this.pionSelected.move(destination);
        this.caseSelected = null;
        this.destination = -1;
        this.turn(this.constructNewList(list));
      }
    }
    //Rule n°19 : if a TokenP is close from the monster with high movements, it can go more closer him
    if(this.hasSelect() && this.hasDestination()){
      if((this.pionSelected.isActif()) && (!this.pionSelected.isOut())
      && (!this.pionSelected.isWin()) && (this.caseSelected.isClose())
      && (!this.caseSelected.isAxisMonster())
      && (this.pionSelected.distanceM() < this.caseSelected.distanceM())
      && (this.caseSelected.isActif())){
        this.pionSelected.move(destination);
        this.caseSelected = null;
        this.destination = -1;
        this.turn(this.constructNewList(list));
      }
    }
  }

  /**
   *
   * Permet de savoir si un token a été sélectionné
   * @return [pionSelected]
   */
  private boolean hasSelect(){
    return this.pionSelected != null;
  }

  /**
   * Permet de mettre à jour le token selectionné
   * @param t [token selectionné]
   */
  private void select(TokenP t){
    this.pionSelected = t;
  }

  /**
   * Permet de construire une nouvelle liste à partir d'une ancienne (en retirant une règle)
   * @param  list [description]
   * @param  no   [description]
   * @return      [description]
   */
  private LinkedList<Integer> constructNewList(LinkedList<Integer> list, int no) {
    //Définition d'une nouvelle liste
    LinkedList<Integer> newRules = new LinkedList<>();
    //Recopie de la liste en paramètre sur la nouvelle liste : elles ne pointent donc pas sur une même adresse mémoire
    for (int i = 0; i < list.size(); i++){
      if (list.get(i) != no){  //on recopie la liste sauf pour une règle en particulier
        newRules.add(list.get(i));
      }
    }
      return newRules;
  }
  /**
   * Permet de construire une nouvelle liste à partir d'une ancienne (sans retirer de règles)
   * @param  list [Ancienne Liste]
   * @return      [Nouvelle Liste]
   */
  private LinkedList<Integer> constructNewList(LinkedList<Integer> list) {
    //Définition d'une nouvelle liste
    LinkedList<Integer> newRules = new LinkedList<>();
    //Recopie de la liste en paramètre sur la nouvelle liste : elles ne pointent donc pas sur une même adresse mémoire
    for (int i = 0; i < list.size(); i++){
      newRules.add( list.get(i) );
    }
    return newRules;
  }
  /**
   * Permet de construire la liste des règles initiale
   * @return [Liste de règle]
   */
  public LinkedList<Integer> constructInitList() {
    //Initialisation
    LinkedList<Integer> newRules = new LinkedList<>();
    for (int i = 0; i < this.nbRules; i++) {
      newRules.add(i);
    }
    return newRules;
  }
  /**
   * Create a false TokenP from another TokenP : it simulate the original after a movement
   * @param direction [0 = Nord, 1 = Est, 2 = Sud, 3 = Ouest]
   */
  private void futurCaseTokenP(int direction){
    if(this.pionSelected != null){
      this.caseSelected = new TokenP(this.pionSelected.getGame() , this.pionSelected.getPosX() , this.pionSelected.getPosY(), this.pionSelected.getNbMove());
      this.caseSelected.move(direction);
      this.caseSelected.getGame().getMap(this.caseSelected.getPosX(), this.caseSelected.getPosY()).setNotTokenHere();
      this.destination = direction;
    }
  }
  /**
   * Permet de savoir si la case et si la direction existe
   * @return [boolean]
   */
  private boolean hasDestination(){
    return this.caseSelected != null && this.destination >= 0;
  }
}
