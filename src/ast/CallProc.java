package ast;

import java.util.LinkedList;

/**
 * Classe de gestion de boucles
 * @author DURAND-MARAIS
 */
public class CallProc extends AST{
	
	private LinkedList<Expression> args;
	private String name; 
	
	/**
	 * Constructeur d'un appel de procédure
	 * @param  line   line de l'appel de la procédure
	 * @param  column colonne de l'appel de la procédure
	 * @param  name   nom de la procédure
	 * @param  args   arguments de l'appel à la procédure
	 */
	public CallProc(int line, int column, String name, LinkedList<Expression> args){
		super(line,column);
		this.name = name;
		this.args = args;
	}

	/**
	 * On vérifie le typage dans la procédure appelée
	 * (s'il y a plusieurs appels avec des arguments différents, la vérification
	 * du bon typage des arguments pendant les autres appels sera vérifié lors de l'exécution)
	 * @param  env       environnement de valeurs et de fonctions
	 * @throws Exception Erreur de typage
	 */
	public void verifyAll(ValueEnv env) throws Exception{
		for(Expression exp : args){
			exp.setType(env);
			exp.verifyType(env);
		}
		Proc fonction = env.call(name,args);
		if(! fonction.isVerified()){
			LinkedList<AST> bloc = fonction.next;
			for(AST element : bloc){
				element.verifyAll(env);
			}
			fonction.setVerified();
		}
		env.cleanStack();
	}

	/**
	 * Exécution de la procédure
	 * @param  g2d       Element graphique pour l'exécution
	 * @param  env       environnement de valeurs et de fonctions
	 * @throws Exception Erreur d'exécution
	 */
	public void exec(Graphics2D g2d, ValueEnv env) throws Exception{
		Proc fonction = env.call(name,args);
		LinkedList<AST> bloc = fonction.next;
		for(AST element : bloc){
			element.exec(g2d,env);
		}
		env.cleanStack();
	}

	/** Debug */
    public void debug(ValueEnv val, boolean b) {
        System.out.println("Fonction => nom : "+name+", et possède "+args.size()+" arguments.");
    }


	
}