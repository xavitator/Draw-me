/**
 * Classe des Expressions
 * @author DURAND-MARAIS
 */

public class FillCircle extends AST {
	Expression exp1;
	Expression exp2;
	Expression exp3;
	Color color;
	
	public FillCircle(int line, int column, Expression exp1, Expression exp2, Expression exp3, Color color){
		super(line, column);
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.color = color;
	}

	public void verifyAll() throws Exception{
		if(exp1.getType() != Type.INT || exp2.getType() != Type.INT || exp3.getType() != Type.INT) throw new ParserException("Il y a un problème.", line, column);
	}

	public void exec(Graphics2D g2d){
		try{
			int a = Integer.parseInt(exp1.getValue());
			int b = Integer.parseInt(exp2.getValue());
			int c = Integer.parseInt(exp3.getValue());
			g2d.fillOval(a, b, c, c);
			g2d.setColor(color);
		}
		catch(NumberFormatException e){
			System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
		}
	}

	
}