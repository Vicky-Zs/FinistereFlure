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
    
    private TokenP pionSelectionne;
    private int nbRegles = 20;
    private LinkedList<Integer> lesReglesDuJeu;
    
    
    public IA(int playerId, Game g) {
        super(defineName(), playerId, g);
        this.lesReglesDuJeu = constructInitList();
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

    // commençement de l'intelligence artificielle
    public void tour(LinkedList<Integer> list)
    {
        // Parcours une seule fois, les règles dans l'ordre croissant de leur numéro
        
        /*
        if( condition de la règle n°X )
        {
            conséquence de la règle X
            gestion de la liste
            (pour la récursivité :
                Utiliser constructNewList() pour obtenir une nouvelle liste
                Puis ensuite appeler la méthode par cette nouvelle liste )
            ( ne jamais touché à la liste en paramètre )
        }
        */
        
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
    private LinkedList<Integer> constructInitList() {
        // initialisation 
        LinkedList<Integer> lesReglesDuJeu = new LinkedList<>();
        for (int i = 0; i < this.nbRegles; i++) {
            lesReglesDuJeu.add(i);
        }
        return lesReglesDuJeu;
    }
}
