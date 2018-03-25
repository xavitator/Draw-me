import java.awt.Color;


/**
 * Classe de gestion des tokens de couleur
 * @author DURAND-MARAIS
 */
public class ColorToken extends Token {

    private Color value;

    
    
    /****************
     * Constructeur *
     ****************/

    /** Constructeur par défaut */
    public ColorToken(Sym sym, int line, int column, String hex){
        super(sym, line, column);
        this.value = Color.decode(hex);
    }



    /**********
     * Getter *
     **********/

    /** Renvoie la valeur du token */
    public Color getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return super.toString() + ", Value : " + this.value;
    }

}
