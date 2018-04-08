package ast;

import java.awt.*;
import java.util.LinkedList;
import java.lang.Exception;

/**
 * Classe de gestion des arbres de syntaxe abrstraite
 * @author DURAND-MARAIS
 */
public class AST {

    protected LinkedList<AST> next;
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

    /** on vérifie le type de chacun des ast.AST suivants */
    public void verifyAll(ValueEnv env) throws Exception{
        env.add();
    	for (AST suivant : next) {
            suivant.verifyAll(env);
    	}
        env.pollLast();
    }

    /** 
     * fonction d'exécution des ast.AST suivants
     * @param g2d élément de gestion du graphique
     * @param val registre de variables
     */
    public void exec(Graphics2D g2d,ValueEnv val) throws Exception {
        val.add();
        for (AST suivant : next) {
            suivant.exec(g2d,val);
        }
        val.pollLast();
    }

    /**
     * On ajoute un ast.AST dans les suivants
     * @param suivant l'ast.AST qu'on rajoute dans les suivants de this
     * @return true si l'opération d'ajout s'est bien passée, false sinon
     */
    public boolean addNext(AST suivant){
        next.add(suivant);
        return next.contains(suivant);
    }

    /** Permet d'activer ou non le mode debug */
    public boolean debugMode(){
        return false; // Pour la phase de débug !
    }

    
}
