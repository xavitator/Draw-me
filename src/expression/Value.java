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
        super(line,column);
        this.value = String.valueOf(value);
    }

    /** construit une expression avec une valeur en boolean */
    public Value(int line, int column, boolean value){
        super(line,column);
        this.value = String.valueOf(value);
    }

    /**
     * On récupère le ast.Type de l'expression :
     * - INT si c'est une suite uniquement de chiffre
     * - BOOLEAN si c'est 'true' ou 'false'
     * @return ast.Type de l'expression
     */
    public Type getType(){
        if(value.matches("^\\p{Digit}+$")) return Type.INT;
        if(value.matches("[Tt]rue | [Ff]alse")) return Type.BOOLEAN;
        return null;
    }

    /**
     * Récupère la valeur de l'expression
     * @return valeur de l'expression sous forme de String
     */
    public String getValue(ValueEnv val) throws Exception {
        if(debugMode()){debug();}
        return value;
    }

    /**
     * On vérifie le type des éléments que doit récupérer l'expression
     */
    public void verifyType() throws Exception{
        if( ! value.matches("^\\p{Digit}+$") && ! value.matches("[Tt]rue | [Ff]alse")){
            throw new ParserException("Il y a une problème de typage.", line, column);
        }
    }

    /** Debug */
    public void debug(){
        System.out.println("expression.Value => valeur: "+value);
    }
}