package expression;

import ast.Type;
import ast.ValueEnv;
import exception.ParserException;

/**
 * Classe pour l'identificateur
 * @author DURAND-MARAIS
 */
public class Identificateur extends Expression{
    private String nom;

    /** on construit une Expression pour un identificateur (Var et Const) */
    public Identificateur(int line, int column, String value){
        super(line,column, Type.VOID);
        this.nom = value;
    }

    public Expression getExpression(ValueEnv env) throws Exception{
        return env.get(nom,line,column).getExpression(env);
    }

    public void setType(ValueEnv env) throws Exception{
        super.type = env.getType(nom, line, column);
    }

    /** vérifie la portée de la variable */
    public void verifyType(ValueEnv env) throws Exception{
        if(! env.contains(nom)){
            //System.out.println("Here");
            throw new ParserException("L'identificateur "+nom+" n'existe pas, il n'a pas été déclaré",line,column);
        }
    }

    public int evalInt(ValueEnv env) throws Exception{
        return env.get(nom, line, column).evalInt(env);
    }

    public boolean evalBool(ValueEnv env) throws Exception{
        return env.get(nom, line, column).evalBool(env);
    }

    /** Debug */
    public void debug(){
        System.out.println("expression.Identificateur => nom: " + nom);
    }
}
