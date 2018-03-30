import java.awt.*;

/**
 * Classe correspondant à la fonction fillRect de Graphics
 * @author DURAND-MARAIS
 */

public class FillRect extends AST {
	Expression exp1;
	Expression exp2;
	Expression exp3;
	Expression exp4;	
	Color color;
	
	/**
	 * on construit un AST correspondant à la fonction fillRect
	 * @param  line   ligne de l'expression dans le fichier
	 * @param  column colonne de l'expression dans le fichier
	 * @param  exp1   premier argument de la fonction en int
	 * @param  exp2   deuxième argument de la fonction en int
	 * @param  exp3   troisème argument de la fonction en int
	 * @param  exp4	  quatrième argument de la fonction en int
	 * @param  color  cinquième argument de la fonction en color
	 * @return        
	 */
	public FillRect(int line, int column, Expression exp1, Expression exp2, Expression exp3, Expression exp4, Color color){
		super(line, column);
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
		this.color = color;
	}

	/** on vérifie le type de chacun des arguments pour qu'ils correspondent à ce qui est attendu */
	public void verifyAll() throws Exception{
		exp1.verifyType();
		exp2.verifyType();
		exp3.verifyType();
		exp4.verifyType();
		if(exp1.getType() != Type.INT || exp2.getType() != Type.INT || exp3.getType() != Type.INT || exp4.getType() != Type.INT) throw new ParserException("Il y a un problème.", line, column);
	}

	/** fonction d'exécution de fillRect */
	public void exec(Graphics2D g2d){
		try{
			int a = Integer.parseInt(exp1.getValue());
			int b = Integer.parseInt(exp2.getValue());
			int c = Integer.parseInt(exp3.getValue());
			int d = Integer.parseInt(exp4.getValue());
			g2d.fillOval(a, b, c, d);
			g2d.setColor(color);
		}
		catch(NumberFormatException e){
			System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
		}
	}
	
}