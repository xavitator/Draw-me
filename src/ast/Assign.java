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

    @Override
    public void verifyAll() throws Exception{
        //on vérifie l'assignation des variables
    }

    @Override
    public void exec(Graphics2D g2d,ValueEnv val) throws Exception {
        //on exécute l'assignation
        if(debugMode()) { debug(val); }
        if (val.containsKey(nom)) {
            if(isConstante) {
                throw new ParserException("Vous essayez de changer une constante. " + "Nom: "+nom,line,column);
            } else {
                throw new ParserException("La variable " + nom + " a déjà été initialisée", this.line, this.column);
            }
        } else {
            val.put(nom,Integer.parseInt(exp1.getValue(val)));
        }
    }

    /** Debug */
    public void debug(ValueEnv val) throws Exception {
        System.out.println("Assignation => Nom: " + nom + " Valeur:"+exp1.getValue(val));
    }
	
}
