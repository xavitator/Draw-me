package expression;

import ast.Type;
import ast.ValueEnv;
import exception.ParserException;

/**
 * Classe pour une valeur
 * @author DURAND-MARAIS
 */
public class Value extends Expression{
    String value;

    /** construit une expression avec une valeur en int */
    public Value(int line, int column,int value){
        super(line,column, Type.INT);
        this.value = String.valueOf(value);
    }

    /** construit une expression avec une valeur en boolean */
    public Value(int line, int column, boolean value){
        super(line,column, Type.BOOLEAN);
        this.value = String.valueOf(value);
    }

    public void setType(ValueEnv env) throws Exception{
        // rien à faire
    }

    /**
     * On vérifie le type des éléments que doit récupérer l'expression
     */
    public void verifyType(ValueEnv env) throws Exception{
        if( ! value.matches("^\\p{Digit}+$") && ! value.matches("[Tt]rue | [Ff]alse")){
            throw new ParserException("Il y a une problème de typage.", line, column);
        }
    }

    public int evalInt(ValueEnv env) throws Exception{
        if(this.getType() != Type.INT) throw new Exception();
        else return Integer.parseInt(value);
    }

    public boolean evalBool(ValueEnv env) throws Exception{
        if(this.getType() != Type.BOOLEAN) throw new Exception();
        else return Boolean.parseBoolean(value);
    }

    /** Debug */
    public void debug(){
        System.out.println("expression.Value => valeur: "+value);
    }
}