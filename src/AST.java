/**
 * Classe de gestion des arbres de syntaxe abrstraite
 * @author DURAND-MARAIS
 */
public class AST {

	protected List<AST> next;
	protected int line;
	protected int column;
    
    /****************
     * Constructeur *
     ****************/
    
    /** Constructeur par défaut*/
    public AST(int line, int column){
    	this.line = line;
    	this.column = column;
    	next = new LinkedList<>();
    }



}
