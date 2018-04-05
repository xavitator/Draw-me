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
    public void verifyAll() throws Exception{
        //on vérifie l'assignation des variables
    }

    @Override
    public void exec(Graphics2D g2d,ValueEnv val) throws Exception {
        //on exécute l'assignation
        if(debugMode()) { debug(val); }
        if (!val.containsKey(nom)) {
                throw new ParserException("La variable " + nom + " n'a pas été initialisée", this.line, this.column);
        } else {
            val.put(nom,Integer.parseInt(exp1.getValue(val)));
        }
    }

    /** Debug */
    public void debug(ValueEnv val) throws Exception {
        System.out.println("ast.Change => Nom: " + nom + " Valeur:"+exp1.getValue(val));
    }
}	
