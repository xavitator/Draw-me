package expression;

import ast.Type;
import ast.ValueEnv;
import exception.ParserException;

/**
 * Classe pour l'identificateur
 * @author DURAND-MARAIS
 */
public class Identificateur extends Expression{
    String nom;

    /** on construit une expression.Expression pour un identificateur (Var et Const) */
    public Identificateur(int line, int column, String value){
        super(line,column);
        this.nom = value;
    }

    /** renvoie le type de l'identificateur */
    public Type getType(){
        return null;
    }

    /** retourne la valeur de l'identificateur dans les variables d'environnement */
    public String getValue(ValueEnv val) throws Exception {
        // on retourne la value correspondant à l'identificateur
        if(debugMode()){debug();}
        if(val.containsKey(this.nom)) {
            return Integer.toString(val.get(this.nom));
        }
        throw new ParserException("Cette valeur n'a jamais été déclarée. Nom:" + this.nom,line,column);
    }

    /** vérifie la portée de la variable */
    public void verifyType() throws Exception{
        // on vérifie la portée de la variable ainsi que son type
    }

    /** Debug */
    public void debug(){
        System.out.println("expression.Identificateur => nom: " + nom);
    }
}