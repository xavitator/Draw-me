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
	
	public void verifyAll() throws Exception{
		condition.verifyType();
		Type type = condition.getType();
		if(type != Type.BOOLEAN && type != Type.INT) throw new ParserException("Il y a un problème de typage.",line,column);
		super.verifyAll();
	}

	public void exec(Graphics2D g2d){
		String value = condition.getValue();
		boolean b = false;
		if(isInteger(value)) b = Integer.parseInt(value) != 0;
		else b = value.matches("[Tt]rue");
		if(b) next.get(0).exec(g2d);
		else{
			if(next.size() >= 2){
				next.get(1).exec(g2d);
			}
		}
	}

	private boolean isInteger(String str){
		return str.matches("^\\p{Digit}+$");
	}
}