import java.awt.*;

/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */

public class Condition extends AST {
	Expression condition;

	/**
	 * constructeur d'un AST correspondant à un If
	 * @param  line      ligne de l'expression dans le fichier
	 * @param  column    colonne de l'expression dans le fichier
	 * @param  condition condition du If
	 */
	public Condition(int line, int column, Expression condition){
		super(line, column);
		this.condition = condition;
	}
	
	/** on vérifie le type de la condition et de chacun des AST suivants */
	public void verifyAll() throws Exception{
		condition.verifyType();
		Type type = condition.getType();
		if(type != Type.BOOLEAN && type != Type.INT) throw new ParserException("Il y a un problème de typage.",line,column);
		super.verifyAll();
	}

	/** fonction d'exécution du If */
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

	/** on vérifie si le string donné en paramètre est composé uniquement de chiffres */
	private boolean isInteger(String str){
		return str.matches("^\\p{Digit}+$");
	}
}