import java.awt.*;
import java.util.LinkedList;
import java.util.List;

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

	/** on vérifie le type de chacun des AST suivants */
    public void verifyAll() throws Exception{
    	for (AST suivant : next) {
    		suivant.verifyAll();
    	}
    }

	/** fonction d'exécution des AST suivants */
    public void exec(Graphics2D g2d){
    	for (AST suivant : next) {
    		suivant.exec(g2d);
    	}
    }

    /**
     * On ajoute un AST dans les suivants
     * @param  suivant l'AST qu'on rajoute dans les suivants de this
     * @return         true si l'opération d'ajout s'est bien passée, false sinon
     */
    public boolean addNext(AST suivant){
        return next.add(suivant);
    }

}
