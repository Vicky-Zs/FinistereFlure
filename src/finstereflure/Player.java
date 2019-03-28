
package finstereflure;

public class Player extends Character {
  private Token[] token = new Token[4];
  private String nom;
  
  public Token[] getToken(){
      return this.token;
  }

    @Override
    protected void pousser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
  
  
}
