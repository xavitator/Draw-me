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
	
	
}
