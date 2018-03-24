/**
 * Classe de génération de token
 * @author DURAND-MARAIS
 */
public class Token {

    private Sym sym;
    private int line;
    private int column;

    /** Constructeur par défaut */
    public Token(Sym sym, int line, int column){
        this.sym = sym;
        this.line = line;
        this.column = column;
    }
    
}
