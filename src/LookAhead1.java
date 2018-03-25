import java.io.*;
import java.awt.Color;

/**
 * Classe de lecture de fichier
 * @author DURAND-MARAIS
 */
public class LookAhead1{

    private Token current;
    private Lexer lex;
    private boolean debug;

    
    /****************
     * Constructeur *
     ***************/

    /** Constructeur par défaut */
    public LookAhead1(Lexer l) throws Exception {
        this.lex = l;
        this.current = lex.yylex();
        this.debug = false;
    }



    
    /**********
     * Reader *
     **********/

    /**
     * Vérifie le token
     * @param sym le symbole à tester
     * @return true si le symbole correspond
     */
    public boolean check (Sym sym) throws Exception {
        return current.symbol() == sym;
    }

    /**
     * Consomme le token courant
     * @param sym le symbole à consommer
     */
    public void eat (Sym sym) throws Exception {
        if (!check(sym)) {
            throw new Exception("Can't eat " + current);
        }
        if (debug) { System.out.println(current); }
        
        this.current = lex.yylex();
    }


    
    /**********
     * Setter *
     **********/

    /** Activer ou désactiver le mode débug */
    public void setDebugOn() {
        this.debug = true;
    }

    public void setDebugOff() {
        this.debug = false;
    }

    

    /***********
     * Getters *
     ***********/

    /** Renvoie le int du token */
    public int getIntValue() throws Exception {
        if (current instanceof IntToken){
            return ((IntToken)current).getValue();
        } else {
            throw new Exception("\n Try to access to an int value");
        }
    }

    /** Renvoie la color du tokne */
    public Color getColorValue() throws Exception {
        if (current instanceof ColorToken) {
            return ((ColorToken)current).getValue();
        } else {
            throw new Exception("\n Try to access to a color value");                
        }
    }

    /** Renvoie la chaine de caractères du token */
    public String getStringValue() throws Exception {
        if (current instanceof StringToken) {
            return ((StringToken)current).getValue();
        } else {
            throw new Exception("\n Try to access to a string value");
        }
    }
    
}
