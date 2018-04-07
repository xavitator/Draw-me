package ast;

import expression.Expression;

import java.util.HashMap;
import java.util.Map;

public class ValueEnv {
	//liste variable
	//liste constante
	

	// getType() --> il faut penser à faire un setType(env) de l'expression avant de getType()
	public Type getType(String nom) throws Exception{
		return null;
	}

	// contains(nom) 				boolean pour savoir si le nom existe
	public boolean contains(String nom){
		return false;
	}

	// get(nom) 					recuperer l'expression associée à 'nom'  
	public Expression get(String nom) throws Exception{
		return null;
	}

	// set(nom, exp1) 				change la valeur de 'nom' si possible (type et constante)  
	public void set(String nom, Expression exp) throws Exception{

	}

	// put(nom, exp, isConstante) 	ajoute <nom, exp> dans l'une des deux linkedlist
	public void put(String nom, Expression exp, boolean isConstante) throws Exception{

	}

	// add() 			ajoute le hasmap dans les deux linkedlist
	public void add(){

	}

	// pollLast() 					renvoie et supprime le dernier hasmap des deux listes
	public Map pollLast() throws Exception{
		return null;
	}
}
