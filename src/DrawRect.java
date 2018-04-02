import java.awt.*;

/**
 * Classe correspondant à la fonction drawRect de Graphics
 * @author DURAND-MARAIS
 */

public class DrawRect extends AST {
	Expression exp1;
	Expression exp2;
	Expression exp3;
	Expression exp4;
	Color color;
	
	/**
	 * on construit un AST correspondant à la fonction drawRect
	 * @param  line   ligne de l'expression dans le fichier
	 * @param  column colonne de l'expression dans le fichier
	 * @param  exp1   premier argument de la fonction en int
	 * @param  exp2   deuxième argument de la fonction en int
	 * @param  exp3   troisème argument de la fonction en int
	 * @param  exp4	  quatrième argument de la fonction en int
	 * @param  color  cinquième argument de la fonction en color
	 * @return        
	 */
	public DrawRect(int line, int column, Expression exp1, Expression exp2, Expression exp3, Expression exp4, Color color){
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
		if(exp1.getType() != Type.INT 
			|| exp2.getType() != Type.INT 
			|| exp3.getType() != Type.INT 
			|| exp4.getType() != Type.INT) throw new ParserException("Il y a un problème.", line, column);
	}

	/** fonction d'exécution de drawRect */
    public void exec(Graphics2D g2d, ValueEnv val){
		try{
			int x = Integer.parseInt(exp1.getValue(val));
			int y = Integer.parseInt(exp2.getValue(val));
			int w = Integer.parseInt(exp3.getValue(val));
			int h = Integer.parseInt(exp4.getValue(val));
                        g2d.setColor(color);
			g2d.drawRect(x, y, w, h);
                        debug(x,y,w,h);
		}
		catch(NumberFormatException e){
			System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
		}
	}


    public void debug(int x, int y, int w, int h){
        System.out.println("DrawRect => x: " + x +" y: " + y + " w: " + w + " h: " + h);
    }

}
