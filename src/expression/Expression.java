package expression;

import ast.Type;
import ast.ValueEnv;
import exception.ParserException;

import java.lang.Exception;

/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */
public abstract class Expression {
    int line;
    int column;

    /**
     * On construit une expression avec la ligne et la colonne de l'expression dans le fichier
     * @param  line   ligne de l'expression dans le fichier
     * @param  column colonne de l'expression dans le fichier
     */
    public Expression(int line, int column){
        this.line = line;
        this.column = column;
    }	
	
    /**
     * On récupère le ast.Type de l'expression
     * @return ast.Type de l'expression
     */
    public abstract Type getType();

    /**
     * Récupère la valeur de l'expression
     * @param val registre contenant les variables
     * @return valeur de l'expression sous forme de String
     */
    public abstract String getValue(ValueEnv val) throws Exception ;

    /**
     * On vérifie le type des éléments que doit récupérer l'expression
     */
    public abstract void verifyType() throws Exception;

    /** Pour le mode debug */
    public boolean debugMode(){
        return true; // A changer pour quitter le mode débug
    }
}