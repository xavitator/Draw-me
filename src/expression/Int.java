package expression;

import ast.Type;
import ast.ValueEnv;
import exception.ParserException;

/**
 * Classe pour une valeur int
 * @author DURAND-MARAIS
 */
public class Int extends Expression{
    int value;

    /** construit une expression avec une valeur en int */
    public Int(int line, int column,int value){
        super(line,column, Type.INT);
        this.value = value;
    }

    public Expression getExpression(ValueEnv env) throws Exception{
        return this;
    }

    public void setType(ValueEnv env) throws Exception{
        this.type = Type.INT;
    }

    /**
     * On vérifie le type des éléments que doit récupérer l'expression
     */
    public void verifyType(ValueEnv env) throws Exception{}

    public int evalInt(ValueEnv env) throws Exception{
        return value;
    }

    /** Debug */
    public void debug(){
        System.out.println("expression.Value => valeur: "+value);
    }
}