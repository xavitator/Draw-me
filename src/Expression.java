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

	public String getValue(){
		return value;
	}

	public void verifyType() throws Exception{
		if(value.matches("^\\p{Digit}+$") && type != Type.INT
			|| value.matches("[Tt]rue | [Ff]alse") && type != Type.BOOLEAN){
			throws new ParserException("Il y a une problème de typage.", line, column);
		}
	}
}

class Identificateur extends Expression{
	String nom;

	public Identificateur(Type type, int line, int column, String value){
		super(type,line,column);
		this.value = value;
	}

	public String getValue(){
		// on retourne la value correspondant à l'identificateur
		return null;
	}

	public void verifyType() throws Exception{
		// on vérifie la portée de la variable ainsi que son type
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

	public String getValue(){
		switch(operateur){
			case "+": return sum(); break;
			case "-": return soustract(); break;
			case "/": return divise(); break;
			case "*": return prod(); break;
			case ">": return compare(); break;
			case "<": return compare(); break;
			case "<=": return compare(); break; 
			case ">=": return compare(); break; 
			case "==": return equal(); break; 
			case "!=": return negate(); break; 
			case "&&": return and(); break; 
			case "||": return or(); break;
		}
	}

	public String sum(){
		try{
			int a = Integer.parseInt(exp1.getValue());
			int b = Integer.parseInt(exp2.getValue());
			return String.valueOf(a+b);
		}
		catch(NumberFormatException exception){
			System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
			return null;
		}
	}

	public String soustract(){
		try{
			int a = Integer.parseInt(exp1.getValue());
			int b = Integer.parseInt(exp2.getValue());
			return String.valueOf(a-b);
		}
		catch(NumberFormatException exception){
			System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
			return null;
		}
	}

	public String divise(){
		try{
			int a = Integer.parseInt(exp1.getValue());
			int b = Integer.parseInt(exp2.getValue());
			return String.valueOf(a/b);
		}
		catch(NumberFormatException exception){
			System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
			return null;
		}
	}

	public String prod(){
		try{
			int a = Integer.parseInt(exp1.getValue());
			int b = Integer.parseInt(exp2.getValue());
			return String.valueOf(a*b);
		}
		catch(NumberFormatException exception){
			System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
			return null;
		}
	}

	public String compare(){
		try{
			int a = Integer.parseInt(exp1.getValue());
			int b = Integer.parseInt(exp2.getValue());
			switch(operateur){
				case ">": return String.valueOf(a>b); break;
				case "<":  return String.valueOf(a<b); break;
				case "<=": return String.valueOf(a<=b); break; 
				case ">=": return String.valueOf(a>=b); break;
			}
		}
		catch(NumberFormatException exception){
			System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
			return null;
		}
	}

	public String equal(){
		String e1 = exp1.getValue();
		String e2 = exp2.getValue();
		if(e1.matches("[Tt]rue | [Ff]alse") && e2.matches("[Tt]rue | [Ff]alse")){
			return e1.toLowerCase().equals(e2.toLowerCase());
		}
		else{
			try{
				int a = Integer.parseInt(e1);
				int b = Integer.parseInt(e2);
				return String.valueOf(a==b);
			}
			catch(NumberFormatException exception){
				System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
				return null;
			}
		}
	}

	public String negate(){
		String e1 = exp1.getValue();
		String e2 = exp2.getValue();
		if(e1.matches("[Tt]rue | [Ff]alse") && e2.matches("[Tt]rue | [Ff]alse")){
			return ! e1.toLowerCase().equals(e2.toLowerCase());
		}
		else{
			try{
				int a = Integer.parseInt(e1);
				int b = Integer.parseInt(e2);
				return String.valueOf(a!=b);
			}
			catch(NumberFormatException exception){
				System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
				return null;
			}
		}
	}

	public String and(){
		String e1 = exp1.getValue();
		String e2 = exp2.getValue();
		return e1.toLowerCase().equals("true") && e2.toLowerCase().equals("true");
	}

	public String or(){
		String e1 = exp1.getValue();
		String e2 = exp2.getValue();
		return e1.toLowerCase().equals("true") || e2.toLowerCase().equals("true");
	}

	public void verifyType() throws Exception{
		Type current;
		if(operateur.matches("[\+-/\*]")) current = Type.INT;
		else current = Type.BOOLEAN;
		if(operateur.matches("[\+-/\*<>]")){
			if(exp1.getType() != Type.INT || exp2.getType() != Type.INT) throw new ParserException("Il y a un problème de typage.", line, column);
		}
		else if (operateur.matches("&& | \|\|")){
			if(exp1.getType() != Type.BOOLEAN || exp2.getType() != Type.BOOLEAN) throw new ParserException("Il y a un problème de typage.", line, column);
		}
		else{
			if((exp1.getType() != Type.INT && exp1.getType() != Type.BOOLEAN) || (exp2.getType() != Type.INT && exp2.getType() != Type.BOOLEAN)) throw new ParserException("Il y a un problème de typage.", line, column);

		}
	}

}