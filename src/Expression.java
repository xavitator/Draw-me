/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */
public abstract class Expression {
	int line;
	int column;

	/**
	 * On construit une expression avec la ligne et la colonne de l'expression dans le fichier
	 * @param  line   ligne de l'expression dans le fichier
	 * @param  column colonne de l'expression dans le fichier
	 */
	public Expression(int line, int column){
		this.line = line;
		this.column = column;
	}	
	
	/**
	 * On récupère le Type de l'expression
	 * @return Type de l'expression
	 */
	abstract Type getType();

	/**
	 * Récupère la valeur de l'expression
	 * @return valeur de l'expression sous forme de String
	 */
	abstract String getValue();

	/**
	 * On vérifie le type des éléments que doit récupérer l'expression
	 */
	abstract void verifyType() throws Exception;
}

class Value extends Expression{
	String value;

	/** construit une expression avec une valeur en int */
	public Value(int line, int column,int value){
		super(line,column);
		this.value = String.valueOf(value);
	}

	/** construit une expression avec une valeur en boolean */
	public Value(int line, int column, boolean value){
		super(line,column);
		this.value = String.valueOf(value);
	}

	/**
	 * On récupère le Type de l'expression :
	 * - INT si c'est une suite uniquement de chiffre
	 * - BOOLEAN si c'est 'true' ou 'false'
	 * @return Type de l'expression
	 */
	public Type getType(){
		if(value.matches("^\\p{Digit}+$")) return Type.INT;
		if(value.matches("[Tt]rue | [Ff]alse")) return Type.BOOLEAN;
		return null;
	}

	/**
	 * Récupère la valeur de l'expression
	 * @return valeur de l'expression sous forme de String
	 */
	public String getValue(){
		return value;
	}

	/**
	 * On vérifie le type des éléments que doit récupérer l'expression
	 */
	public void verifyType() throws Exception{
		if( ! value.matches("^\\p{Digit}+$") && ! value.matches("[Tt]rue | [Ff]alse")){
			throw new ParserException("Il y a une problème de typage.", line, column);
		}
	}
}

class Identificateur extends Expression{
	String nom;

	/** on construit une Expression pour un identificateur (Var et Const) */
	public Identificateur(int line, int column, String value){
		super(line,column);
		this.nom = value;
	}

	/** renvoie le type de l'identificateur */
	public Type getType(){
		return null;
	}

	/** retourne la valeur de l'identificateur dans les variables d'environnement */
	public String getValue(){
		// on retourne la value correspondant à l'identificateur
		return null;
	}

	/** vérifie la portée de la variable */
	public void verifyType() throws Exception{
		// on vérifie la portée de la variable ainsi que son type
	}
}

class Operation extends Expression{
	Expression exp1;
	Expression exp2;
	String operateur;
	static final String[] possibleValue = {"+","-","/","*",">","<","<=",">=","==","!=","&&","||"};

	/** construction d'une expression avec un opérateur (on donne le String correspondant) et les deux expressions autour */
	public Operation(int line, int column, Expression exp1, Expression exp2, String operateur){
		super(line,column);
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.operateur = operateur;
	}

	/** retourne le type que va renvoyer l'opération */
	public Type getType(){
		if(operateur.matches("[\\+-/\\*]")) return Type.INT;
		else return Type.BOOLEAN;
	}

	/** on récupère la valeur de l'opération, ce que renvoie l'opération */
	public String getValue(){
		switch(operateur){
			case "+": return sum();
			case "-": return soustract();
			case "/": return divise();
			case "*": return prod();
			case ">": return compare();
			case "<": return compare();
			case "<=": return compare();
			case ">=": return compare();
			case "==": return equal();
			case "!=": return negate();
			case "&&": return and();
			case "||": return or();
			default: return null;
		}
	}

	/** retourne la valeur de la somme des deux expressions (en forme d'int)*/
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

	/** retourne la valeur de la soustraction des deux expressions (en forme d'int)*/
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

	/** retourne la valeur de la division des deux expressions (en forme d'int)*/
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

	/** retourne la valeur de le produit des deux expressions (en forme d'int)*/
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

	/** retourne la valeur de la comparaison des deux expressions en fonction de l'opération de comparaison (en forme de boolean)*/
	public String compare(){
		try{
			int a = Integer.parseInt(exp1.getValue());
			int b = Integer.parseInt(exp2.getValue());
			switch(operateur){
				case ">": return String.valueOf(a>b);
				case "<":  return String.valueOf(a<b);
				case "<=": return String.valueOf(a<=b);
				case ">=": return String.valueOf(a>=b);
			}
		}
		catch(NumberFormatException exception){
			System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
		}
		return null;
	}

	/** retourne la valeur de l'égalité des deux expressions (en forme de boolean)*/
	public String equal(){
		String e1 = exp1.getValue();
		String e2 = exp2.getValue();
		if(e1.matches("[Tt]rue | [Ff]alse") && e2.matches("[Tt]rue | [Ff]alse")){
			return String.valueOf(e1.toLowerCase().equals(e2.toLowerCase()));
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

	/** retourne la valeur de la différence des deux expressions (en forme de boolean)*/
	public String negate(){
		String e1 = exp1.getValue();
		String e2 = exp2.getValue();
		if(e1.matches("[Tt]rue | [Ff]alse") && e2.matches("[Tt]rue | [Ff]alse")){
			return String.valueOf(! e1.toLowerCase().equals(e2.toLowerCase()));
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

	/** retourne la valeur de l'opération '&&'(en java) des deux expressions (en forme de boolean)*/
	public String and(){
		String e1 = exp1.getValue();
		String e2 = exp2.getValue();
		return String.valueOf(e1.toLowerCase().equals("true") && e2.toLowerCase().equals("true"));
	}

	/** retourne la valeur de l'opération '||'(en java) des deux expressions (en forme de boolean)*/
	public String or(){
		String e1 = exp1.getValue();
		String e2 = exp2.getValue();
		return String.valueOf(e1.toLowerCase().equals("true") || e2.toLowerCase().equals("true"));
	}

	/** on vérifie si le type de l'expression correspond à ce qu'il faut */
	public void verifyType() throws Exception{
		exp1.verifyType();
		exp2.verifyType();
		if(wrongOperator()) throw new ParserException("Il y a un problème de typage.", line, column);
		Type current;
		if(operateur.matches("[\\+-/\\*]")) current = Type.INT;
		else current = Type.BOOLEAN;
		if(operateur.matches("[\\+-/\\*<>]")){
			if(exp1.getType() != Type.INT || exp2.getType() != Type.INT) throw new ParserException("Il y a un problème de typage.", line, column);
		}
		else if (operateur.matches("&& | \\|\\|")){
			if(exp1.getType() != Type.BOOLEAN || exp2.getType() != Type.BOOLEAN) throw new ParserException("Il y a un problème de typage.", line, column);
		}
		else if (operateur.equals("!=") || operateur.equals("==")){
			if((exp1.getType() != Type.INT && exp1.getType() != Type.BOOLEAN) || (exp2.getType() != Type.INT && exp2.getType() != Type.BOOLEAN)) throw new ParserException("Il y a un problème de typage.", line, column);
		}
		else throw new ParserException("Il y a un problème de typage.", line, column);
	}

	/** on vérifie si l'opérateur est un bon caractère */
	public boolean wrongOperator(){
		for (int i = 0; i < possibleValue.length; i++) {
			if(operateur.equals(possibleValue[i])) return false;
		}
		return true;
	}
}