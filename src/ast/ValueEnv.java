package ast;

import exception.ParserException;
import expression.Expression;
import parser.Parser;

import java.util.HashMap;
import java.util.LinkedList;

public class ValueEnv {
    private LinkedList<HashMap<String,Expression>> variables;
    private LinkedList<HashMap<String,Expression>> constantes;
    private int taille;
    
    public ValueEnv() {
        variables = new LinkedList<>();
        constantes = new LinkedList<>();
        taille = 0;
    }
    
	// getType() --> il faut penser à faire un setType(env) de l'expression avant de getType()
	public Type getType(String nom, int line, int colonne) throws Exception{
            for (int i = taille-1 ; i >= 0 ; i--) {
                Expression resAltern = variables.get(i).get(nom);
                if (resAltern != null) { resAltern.setType(this); return resAltern.getType(); }
                resAltern = constantes.get(i).get(nom);
                if (resAltern != null) { resAltern.setType(this); return resAltern.getType(); }
            }
            throw new ParserException("L'identificateur "+nom+" n'existe pas, il n'a pas été déclaré",line,colonne);
	}

	// contains(nom) 				boolean pour savoir si le nom existe
	public boolean contains(String nom){
            for (int i=taille-1 ; i>=0 ; i--) {
                //System.out.println(i);
                /*Expression res = variables.get(i).get(nom);
                res = constantes.get(i).get(nom); 
                if (res != null) { return true; }*/
                if(variables.get(i).containsKey(nom) || constantes.get(i).containsKey(nom)) return true;
            }
            return false;
	}

	// get(nom) 					recuperer l'expression associée à 'nom'  
	public Expression get(String nom, int line, int colonne) throws Exception{
            for (int i = taille-1 ; i >= 0 ; i--) {
                Expression res = variables.get(i).get(nom);
                if (res != null) { return res; }
                res = constantes.get(i).get(nom);
                if (res != null) { return res; }
            }
            throw new ParserException("L'identificateur "+nom+" n'existe pas, il n'a pas été déclaré",line,colonne);
	}

	// set(nom, exp1) 				change la valeur de 'nom' si possible (type et constante)  
	public void set(String nom, Expression exp) throws Exception{
            for(int i = taille -1; i >= 0 ; i--) {
                Expression res = variables.get(i).get(nom);
                if(res != null) {
                    if(res.getType() == exp.getType()) { variables.get(i).put(nom,exp.getExpression(this)); return;}
                    else { throw new ParserException("Le type de l'expression est "+exp.getExpression(this).getType()+" or on s'attend à avoir pour l'identificateur "+nom+" un type "+ res.getType() +" n'existe pas, il n'a pas été déclaré",exp.getLine(),exp.getColumn()); }
                }
                res = constantes.get(i).get(nom);
                if (res != null) { throw new ParserException("Vous essayez de changer la valeur d'une constante",exp.getLine(),exp.getColumn()); }
            }
            throw new ParserException("L'identificateur "+nom+" n'existe pas, il n'a pas été initialisé",exp.getLine(),exp.getColumn());
                
	}

	// put(nom, exp, isConstante) 	ajoute <nom, exp> dans l'une des deux linkedlist
	public void put(String nom, Expression exp, boolean isConstante) throws Exception{
            if(!variables.getLast().containsKey(nom) && !constantes.getLast().containsKey(nom)) {
                if(isConstante){
                    constantes.getLast().put(nom,exp.getExpression(this));
                }
                else { variables.getLast().put(nom,exp.getExpression(this)); }
            } else {
                throw new ParserException("L'identificateur " + nom + " a déjà été initialisé", exp.getLine(), exp.getColumn());
            }
        }

	// add() 			ajoute le hasmap dans les deux linkedlist
	public void add(){
            this.taille++;
            variables.add(new HashMap<String,Expression>());
            constantes.add(new HashMap<String,Expression>());
	}

	// pollLast() 					renvoie et supprime le dernier hasmap des deux listes
	public void pollLast() throws Exception{
            variables.removeLast();
            constantes.removeLast();
            this.taille--;
	}

    public String toString(){
        String res = "";
        for (int i = 0; i < taille; i++) {
            res += "Variable "+i+" "+ variables.get(i).size() +"\n";
            res += variables.get(i).values().toString() + "\n";
        }
        for (int i = 0; i < taille; i++) {
            res += "Constante "+i+" "+ constantes.get(i).size() +"\n";
            res += constantes.get(i).values().toString() + "\n";
        }
        return res +"\n\n";
    }
}
