/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */
public abstract class Expression {
	Type type;
	int line;
	int column;

	public Expression(Type type, int line, int column){
		this.type = type;
		this.line = line;
		this.column = column;
	}	
	
	public Type getType(){
		return type;
	}

	abstract String getValue();

	abstract void verifyType();
}

class Value extends Expression{
	String value;

	public Value(Type type, int line, int column,int value){
		super(type,line,column);
		this.value = String.valueOf(value);
	}

	public Value(Type type, int line, int column, boolean value){
		super(type,line,column);
		this.value = String.valueOf(value);
	}
}

class Identificateur extends Expression{
	String nom;

	public Identificateur(Type type, int line, int column, String value){
		super(type,line,column);
		this.value = value;
	}
}

class Operation extends Expression{
	Expression exp1;
	Expression exp2;
	String operateur;

	public Operation(Type type, int line, int column, Expression exp1, Expression exp2, String operateur){
		super(type,line,column);
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.operateur = operateur;
	}
}