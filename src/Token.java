/**
 * Classe de génération de token
 * @author DURAND-MARAIS
 */
public class Token {

    private Sym sym;
    private int line;
    private int column;


    
    /****************
     * Constructeur *
     ****************/
    
    /** Constructeur par défaut */
    public Token(Sym sym, int line, int column){
        this.sym = sym;
        this.line = line;
        this.column = column;
    }


    
    /***********
     * Getters *
     ***********/

    /** Récupération du symbole */
    public Sym symbol() {
        return this.sym;
    }

    /** Renvoie la line */
    public int line() {
        return this.line;
    }

    /** Renvoie la colonne */
    public int column() {
        return this.column;
    }


    @Override
    public String toString() {
        return "Token => Symbole : " + this.sym;
    }
    
}
