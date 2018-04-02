import java.awt.*;

/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */


public class Assign extends AST {
    boolean isConstante;
    String nom;
    Expression exp1;


    public Assign(int line, int column, boolean isConstante, String nom, Expression exp1){
        super(line, column);
        this.isConstante = isConstante;
        this.nom = nom;
        this.exp1 = exp1;
    }
	
    public void verifyAll() throws Exception{
        //on vérifie l'assignation des variables
    }

    public void exec(Graphics2D g2d,ValueEnv val){
        //on éxecute l'assignation
        System.out.println("Herrreee");
        if (val.containsKey(nom)) {
                debug(val); // À remplacer par une exception
        } else {
            val.put(nom,Integer.parseInt(exp1.getValue(val)));
        }
    }

    public void debug(ValueEnv val) {
        System.out.println("Assignation => Nom: " + nom + " Valeur:"+exp1.getValue(val));
    }
	
}
