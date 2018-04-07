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
    protected int line;
    protected int column;
    protected Type type = Type.VOID;

    /**
     * On construit une expression avec la ligne et la colonne de l'expression dans le fichier
     * @param  line   ligne de l'expression dans le fichier
     * @param  column colonne de l'expression dans le fichier
     * @param  type type de l'expression
     */
    public Expression(int line, int column, Type type){
        this.line = line;
        this.column = column;
        this.type = type;
    }	
	
    /**
     * On récupère le Type de l'expression
     * @return ast.Type de l'expression
     */
    public Type getType(){
        return this.type;
    }

    /**
     * On change le type de l'expression
     */
    public abstract void setType(ValueEnv env) throws Exception;

    /**
     * On vérifie le type des éléments que doit récupérer l'expression
     */
    public abstract void verifyType(ValueEnv env) throws Exception;

    public int evalInt(ValueEnv env) throws Exception{
        throw new Exception();
    }

    public boolean evalBool(ValueEnv env) throws Exception{
        throw new Exception();
    }

    /** Pour le mode debug */
    public boolean debugMode(){
        return true; // A changer pour quitter le mode débug
    }
}