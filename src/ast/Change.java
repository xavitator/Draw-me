package ast;

import exception.ParserException;
import expression.Expression;

import java.lang.Exception;
import java.awt.*;

/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */
public class Change extends AST {
    String nom;
    Expression exp1;

    /**
     * Constructeur
     * @param line la ligne du token
     * @param column la colonne du token
     * @param nom le nom de la variable
     * @param exp1 nouvelle valeur de la variable 'nom'
     */
    public Change(int line, int column, String nom, Expression exp1){
        super(line, column);
        this.nom = nom;
        this.exp1 = exp1;
    }

    @Override
    public void verifyAll(ValueEnv env) throws Exception{
        if(env.contains(nom)){
            exp1.setType(env);
            if(env.getType(nom) != exp1.getType()){
                throw new Exception();
            }
        }
        else{
            throw new Exception();
        }
        super.verifyAll(env);
    }

    @Override
    public void exec(Graphics2D g2d,ValueEnv val) throws Exception {
        //on exécute l'assignation
        if(debugMode()) { debug(val); }
        // on set la valeur de 'nom' si elle n'est pas une constante
        val.set(nom,exp1);
        super.exec(g2d,val);
    }

    /** Debug */
    public void debug(ValueEnv val) throws Exception {
        String value = "";
        if(exp1.getType() == Type.BOOLEAN) value = String.valueOf(exp1.evalBool(val));
        else value = String.valueOf(exp1.evalInt(val));
        System.out.println("Change => Nom: " + nom + " Valeur:"+value);
    }
}	
