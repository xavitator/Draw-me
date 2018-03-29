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

	public void exec(Graphics2D g2d){
		//on éxecute l'assignation
	}
	
}
