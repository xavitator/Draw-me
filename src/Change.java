import java.lang.Exception;
import java.awt.*;
import java.lang.Exception;

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
     * @param isConstante si c'est une constante
     * @param nom le nom de la variable
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
        System.out.println("Change => Nom: " + nom + " Valeur:"+exp1.getValue(val));
    }
}	
