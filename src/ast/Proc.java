package ast;

import java.util.LinkedList;
import java.awt.*;

public class Proc extends AST{
	
	private LinkedList<String> args;
	private String name;
	private boolean isVerified = false;

	public Proc(int line, int column, String name, AST content, LinkedList<String> args){
		super(line,column);
		super.addNext(content);
		this.name = name;
		this.args = args;
	}

	/** On récupère le nom des argments de la procédure */
	public LinkedList<String> getArgs(){
		return this.args;
	}

	/** On récupère le nom de la procédure */
	public String getName(){
		return name;
	}

	/** On récupère le contenu de la procédure */
	public LinkedList<AST> getContent(){
		return super.next;
	}

	/** On regarde si la procédure a déjà été vérifier au niveau du typage */
	public boolean isVerified(){
		return this.isVerified;
	}

	/** On change le boolean pour dire que le typage de la procédure a été vérifier */
	public void setVerified(){
		this.isVerified = true;
	}

	/** Exécution de la procédure, mais cela ne fait rien car Proc correspond à la déclaration d'une procédure */
	public void exec(Graphics2D g2d, ValueEnv env) throws Exception{
		env.addProc(this);
	}

	/** Vérification du typage de la procédure, mais cela ne fait rien car Proc correspond à la déclaration d'une procédure */
	public void verifyAll(ValueEnv env) throws Exception{
		env.addProc(this);
	}


	/** Debug */
    public void debug(ValueEnv val, boolean b) {
        System.out.println("Fonction => nom : "+name+", et possède "+args.size()+" arguments.");
    }
	
}