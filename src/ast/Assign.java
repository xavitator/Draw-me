package ast;

import java.awt.*;
import java.lang.Exception;

import exception.ParserException;
import expression.Expression;

/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */
public class Assign extends AST {
    boolean isConstante;
    String nom;
    Expression exp1;

    /**
     * Constructeur
     * @param line la ligne du token
     * @param column la colonne du token
     * @param isConstante si c'est une constante
     * @param nom le nom de la variable
     */
    public Assign(int line, int column, boolean isConstante, String nom, Expression exp1){
        super(line, column);
        this.isConstante = isConstante;
        this.nom = nom;
        this.exp1 = exp1;
    }

    public void verifyAll(ValueEnv env)throws Exception{
        env.put(nom,exp1, isConstante);
        System.out.println(env.contains(nom));
        super.verifyAll(env);
    }

    @Override
    public void exec(Graphics2D g2d,ValueEnv val) throws Exception {
        //on exécute l'assignation
        if(debugMode()) { debug(val); }
        // on ajoute dans 'val' le nom, puis l'expression et enfin si c'est une constante
        // l'exception de si elle existe est géré dans ValueEnv
        val.put(nom,exp1, isConstante);
        super.exec(g2d, val);
    }

    /** Debug */
    public void debug(ValueEnv val) throws Exception {
        String value = "";
        System.out.println("exp =>" + exp1);
        // if(exp1.getType() == Type.BOOLEAN) value = String.valueOf(exp1.evalBool(val));
        //else value = String.valueOf(exp1.evalInt(val));
        System.out.println("Assignation => Nom: " + nom + " Valeur:"+value);
    }
	
}
