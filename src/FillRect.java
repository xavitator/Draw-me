/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */

public class FillRect extends AST {
	Expression exp1;
	Expression exp2;
	Expression exp3;
	Expression exp4;	
	Color color;
	
	public FillRect(int line, int column, Expression exp1, Expression exp2, Expression exp3, Expression exp4, Color color){
		super(line, column);
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
		this.color = color;
	}
	
}