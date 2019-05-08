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

    //private String nameIA;
    // Retrait d'un paramètres
    /*public IA(HashSet<Token> token) { //Modifie TokenP -> Token
        super(defineName());
        for(Token p : token){
            token.add(p);
        }
        this.nameIA = defineName();
    }*/
    
    private int destination = -1;
    private TokenP caseSelectionne;
    private TokenP pionSelectionne;
    private int nbRegles = 20;
    
    
    public IA(int playerId, Game g) {
        super(defineName(), playerId, g);
    }

    public static String defineName(){ // Modification en Static
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
        for(int i = 0;i<nomIA.size();i++){ // Permet de mélanger les noms
            Collections.shuffle(nomIA);
        }
        name = nomIA.getFirst();
        return name;
    }

    @Override
    public String toString(){
        return super.pseudo +", il vous reste "+super.getNbToken()+" encore en vie";
    }

    public void tourIA()
    {
        this.tour(constructInitList());
    }
    
    // commençement de l'intelligence artificielle
    private void tour(LinkedList<Integer> list)
    {
        // Parcours une seule fois, les règles dans l'ordre croissant de leur numéro
        
        // rule n°0 --> rule n°5 : selection of TokenPs
        for( int p = 1 ; p < 7 ; p++)
        {
            if( this.getToken(p) != null && !this.hasSelect() )
            {
                if( this.getToken(p).isActif() && !this.getToken(p).isWin() )
                {
                    this.select(this.getToken(p));
                    this.tour( this.constructNewList(list, (p-1) ));
                }
            }
        }
        
        // rule n°6 : when a TokenP is near to exit, it exit
        if( this.hasSelect() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && this.pionSelectionne.winningCondition() )
            {
                this.pionSelectionne.move(0);
                this.pionSelectionne = null;
                this.caseSelectionne = null;
                this.destination = -1;
            }
        }
        
        // rule n°7 : when a TokenP is out, it can start to the boardgame
        if( this.hasSelect() )
        {
            if( this.pionSelectionne.isActif() && this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && !this.hasDestination() )
            {
                this.destination = 3;
                this.futurCaseTokenP(destination);
                this.tour( this.constructNewList(list,7) );
            }
        }
        
        // rule n°8 : if a TokenP can move on the North, notice it
        if( this.hasSelect() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && !this.hasDestination() && this.pionSelectionne.canMove(0) )
            {
                this.destination = 0;
                this.futurCaseTokenP(destination);
                this.tour( this.constructNewList(list) );
            }
        }
        
        // rule n°9 : if a TokenP can move on the West, notice it
        if( this.hasSelect() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && !this.hasDestination() && this.pionSelectionne.canMove(3) )
            {
                this.destination = 3;
                this.futurCaseTokenP(destination);
                this.tour( this.constructNewList(list) );
            }
        }
        
        // rule n°10 : if a TokenP can move on the Sud, notice it
        if( this.hasSelect() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && !this.hasDestination() && this.pionSelectionne.canMove(2) )
            {
                this.destination = 2;
                this.futurCaseTokenP(destination);
                this.tour( this.constructNewList(list) );
            }
        }
        
        // rule n°11 : if a TokenP can move on the East, notice it
        if( this.hasSelect() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && !this.hasDestination() && this.pionSelectionne.canMove(1) )
            {
                this.destination = 1;
                this.futurCaseTokenP(destination);
                this.tour( this.constructNewList(list) );
            }
        }
        
        // rule n°12 : if a TokenP can finnaly just stay, notice it
        if( this.hasSelect() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && !this.hasDestination() && this.pionSelectionne.canStay() && !this.pionSelectionne.isClose() && !this.pionSelectionne.isAxisMonster() )
            {
                this.pionSelectionne.turnOff();
                this.pionSelectionne = null;
                this.caseSelectionne = null;
                this.destination = -1;
            }
        }
        
        // rule n°13 : if a TokenP is really close from exit without to be endanger, it goes
        if( this.hasSelect() && this.hasDestination() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && this.hasDestination() && this.pionSelectionne.isSave() )
            {
                this.pionSelectionne.move(destination);
                this.caseSelectionne = null;
                this.destination = -1;
                this.tour( this.constructNewList(list) );
            }
        }
        
        // rule n°14 : if there is an endanger TokenP, move to brain the monster (dependent from the presence of an ennemy or not)
        if( this.hasSelect() && this.hasDestination() )
        {
            if( this.caseSelectionne.isTrapped(this.caseSelectionne.distanceM()) && this.caseSelectionne.findTokenP(this.caseSelectionne.distanceM()) != -1 )
            {
                // rule n°14.1 : if the endanger TokenP isn't an ennemy, save it
                if( this.caseSelectionne.findTokenP(this.caseSelectionne.distanceM()) == 1 )
                {
                    this.pionSelectionne.move(destination);
                    this.pionSelectionne.turnOff();
                    this.pionSelectionne = null;
                    this.caseSelectionne = null;
                    this.destination = -1;
                    this.tour( this.constructNewList(list) );
                }
                else    // rule n°14.2 : if the endanger TokenP is an ennemy, doom it
                {
                    if( this.pionSelectionne.distanceM() < this.caseSelectionne.distanceM() )
                    {
                        this.pionSelectionne.move(destination);
                        this.caseSelectionne = null;
                        this.destination = -1;
                        this.tour( this.constructNewList(list) );
                    }
                }
            }
        }
        
        // rule n°15 : if a TokenP could go in the axe of the monster, without make it endanger, it can go
        if( this.hasSelect() && this.hasDestination() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && this.caseSelectionne.isAxisMonster() && this.caseSelectionne.isActif() )
            {
                this.pionSelectionne.move(destination);
                this.caseSelectionne = null;
                this.destination = -1;
                this.tour( this.constructNewList(list) );
            }
        }
        
        // rule n°16 : if a TokenP could go behind the monster without make it endanger, it can go
        if( this.hasSelect() && this.hasDestination() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && !this.caseSelectionne.isAxisMonster() && this.caseSelectionne.isBehind() && this.caseSelectionne.pathNoMonster(destination) )
            {
                this.pionSelectionne.move(destination);
                this.caseSelectionne = null;
                this.destination = -1;
                this.tour( this.constructNewList(list) );
            }
        }
        
        // rule n°17 : if a TokenP is in front of the Monster, it doesn't go more closer him
        if( this.hasSelect() && this.hasDestination() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && !this.caseSelectionne.isClose() && !this.caseSelectionne.isAxisMonster() && !this.caseSelectionne.isBehind() && this.pionSelectionne.distanceM() >= this.caseSelectionne.distanceM() )
            {
                this.pionSelectionne.move(destination);
                this.caseSelectionne = null;
                this.destination = -1;
                this.tour( this.constructNewList(list) );
            }
        }
        
        // rule n°18 : if a TokenP is close from the monster with low movements, it can't go more closer him
        if( this.hasSelect() && this.hasDestination() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && this.caseSelectionne.isClose() && !this.caseSelectionne.isAxisMonster() && this.pionSelectionne.distanceM() >= this.caseSelectionne.distanceM() )
            {
                this.pionSelectionne.move(destination);
                this.caseSelectionne = null;
                this.destination = -1;
                this.tour( this.constructNewList(list) );
            }
        }
        
        // rule n°18 : if a TokenP is close from the monster with high movements, it can go more closer him
        if( this.hasSelect() && this.hasDestination() )
        {
            if( this.pionSelectionne.isActif() && !this.pionSelectionne.isOut() && !this.pionSelectionne.isWin() && this.caseSelectionne.isClose() && !this.caseSelectionne.isAxisMonster() && this.pionSelectionne.distanceM() < this.caseSelectionne.distanceM() && this.caseSelectionne.isActif() )
            {
                this.pionSelectionne.move(destination);
                this.caseSelectionne = null;
                this.destination = -1;
                this.tour( this.constructNewList(list) );
            }
        }
        
    }
    
    
    private boolean hasSelect()
    {
        return this.pionSelectionne != null;
    }
    
    
    private void select(TokenP t)
    {
        this.pionSelectionne = t;
    }

    
    // permet de construire une nouvelle liste à partir d'une ancienne (en retirant une règle)
    private LinkedList<Integer> constructNewList(LinkedList<Integer> list, int no) {
        // définition d'une nouvelle liste
        LinkedList<Integer> lesReglesDuJeu = new LinkedList<>();    
        
        // recopie de la liste en paramètre sur la nouvelle liste : elles ne pointent donc pas sur une même adresse mémoire
        for (int i = 0; i < list.size(); i++) 
        {
            if (list.get(i) != no)  // on recopie la liste sauf pour une règle en particulier
            {
                lesReglesDuJeu.add( list.get(i) );
            }
        }
        return lesReglesDuJeu;
    }
    
    // permet de construire une nouvelle liste à partir d'une ancienne (sans retirer de règles)
    private LinkedList<Integer> constructNewList(LinkedList<Integer> list) {
        // définition d'une nouvelle liste
        LinkedList<Integer> lesReglesDuJeu = new LinkedList<>();    
        
        // recopie de la liste en paramètre sur la nouvelle liste : elles ne pointent donc pas sur une même adresse mémoire
        for (int i = 0; i < list.size(); i++) 
        {
            lesReglesDuJeu.add( list.get(i) );
        }
        return lesReglesDuJeu;
    }
    
    // permet de construire la liste des règles initiale
    public LinkedList<Integer> constructInitList() {
        // initialisation 
        LinkedList<Integer> lesReglesDuJeu = new LinkedList<>();
        for (int i = 0; i < this.nbRegles; i++) {
            lesReglesDuJeu.add(i);
        }
        return lesReglesDuJeu;
    }
    
    // create a false TokenP from another TokenP : it simulate the original after a movement
    private void futurCaseTokenP(int direction)
    {
        if( this.pionSelectionne != null )
        {
            this.caseSelectionne = new TokenP(this.pionSelectionne.getGame() , this.pionSelectionne.getPosX() , this.pionSelectionne.getPosY(), this.pionSelectionne.getNbMove());
            this.caseSelectionne.move(direction);
            this.caseSelectionne.getGame().getMap(this.caseSelectionne.getPosX(), this.caseSelectionne.getPosY()).setNotTokenHere();
            this.destination = direction;
        }
    }
    
    private boolean hasDestination()
    {
        return this.caseSelectionne != null && this.destination >= 0;
    }
}
