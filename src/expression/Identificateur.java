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

    public void setType(ValueEnv env) throws Exception{
        super.type = env.getType(nom);
    }

    /** vérifie la portée de la variable */
    public void verifyType(ValueEnv env) throws Exception{
        if(! env.contains(nom)){
            System.out.println("Here");
            throw new ParserException("erreur", line,column);
        }
    }

    public int evalInt(ValueEnv env) throws Exception{
        return env.get(nom).evalInt(env);
    }

    public boolean evalBool(ValueEnv env) throws Exception{
        return env.get(nom).evalBool(env);
    }

    /** Debug */
    public void debug(){
        System.out.println("expression.Identificateur => nom: " + nom);
    }
}
