package expression;

import ast.Type;
import ast.ValueEnv;
import exception.ParserException;

/**
 * Classe pour une valeur
 * @author DURAND-MARAIS
 */
public class Bool extends Expression{
    boolean value;

    /** construit une expression avec une valeur en boolean */
    public Bool(int line, int column, boolean value){
        super(line,column, Type.BOOLEAN);
        this.value = value;
    }

    public void setType(ValueEnv env) throws Exception{
        this.type = Type.BOOLEAN;
    }

    public Expression getExpression(ValueEnv env) throws Exception{
        return this;
    }

    /**
     * On vérifie le type des éléments que doit récupérer l'expression
     */
    public void verifyType(ValueEnv env) throws Exception{}

    public boolean evalBool(ValueEnv env) throws Exception{
        return value;
    }

    /** Debug */
    public void debug(){
        System.out.println("expression.Value => valeur: "+value);
    }
}