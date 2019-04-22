package character;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
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
        return super.pseudo +", il vous reste"+super.getNbToken()+" encore en vie";
    }

}
