/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */


public class DrawCircle extends AST {
	Expression exp1;
	Expression exp2;
	Expression exp3;
	Color color;
	
	public DrawCircle(int line, int column, Expression exp1, Expression exp2, Expression exp3, Color color){
		super(line, column);
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.color = color;
	}
	
}