/**
 * Classe de gestion des tokens de chaine de caractères
 * @author DURAND-MARAIS
 */
public class StringToken extends Token {

    private String value;

    
    
    /*****************
     * Constructeur *
     ****************/

    /** Constructeur par défaut */
    public StringToken (Sym sym, int line, int column, String value) {
        super(sym, line, column);
        this.value = value;
    }



    /**********
     * Getter *
     **********/

    /** Renvoie la valeur du token */
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return super.toString() + ", Value : " + this.value;
            
    }
        
}
