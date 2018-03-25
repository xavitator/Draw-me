/**
 * Classe de gestion des tokens de type integer
 * @author DURAND-MARAIS
 */
public class IntToken extends Token {

    private int value;


    
    /****************
     * Constructeur *
     ***************/

    /** Constructeur par défaut */
    public IntToken(Sym sym, int line, int column, int value) {
        super(sym,line,column);
        this.value = value;
    }



    
    /**********
     * Getter *
     *********/

    /** Renvoie la valeur du token */
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return super.toString() + ", Value : " + this.value;
    }
    
}
