/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */

public class Condition extends AST {
	Expression condition;


	public Condition(int line, int column, Expression condition){
		super(line, column);
		this.condition = condition;
	}
	
}