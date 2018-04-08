package ast;

import expression.Expression;

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
	public Type getType(String nom) throws Exception{
            for (int i = taille-1 ; i >= 0 ; i--) {
                Expression resAltern = variables.get(i).get(nom);
                if (resAltern != null) { resAltern.setType(this); return resAltern.getType(); }
                resAltern = constantes.get(i).get(nom);
                if (resAltern != null) { resAltern.setType(this); return resAltern.getType(); }
            }
            throw new Exception("L'identificateur  " + nom +" n'existe pas.");
	}

	// contains(nom) 				boolean pour savoir si le nom existe
	public boolean contains(String nom){
            for (int i=taille-1 ; i>=0 ; i--) {
                System.out.println(i);
                Expression res = variables.get(i).get(nom);
                res = constantes.get(i).get(nom); 
                if (res != null) { return true; }
            }
            return false;
	}

	// get(nom) 					recuperer l'expression associée à 'nom'  
	public Expression get(String nom) throws Exception{
            for (int i = taille-1 ; i >= 0 ; i--) {
                Expression res = variables.get(i).get(nom);
                if (res != null) { return res; }
                res = constantes.get(i).get(nom);
                if (res != null) { return res; }
            }
            throw new Exception("La valeur que vous cherchez n'existe pas");
	}

	// set(nom, exp1) 				change la valeur de 'nom' si possible (type et constante)  
	public void set(String nom, Expression exp) throws Exception{
            for(int i = taille -1; i >= 0 ; i--) {
                Expression res = variables.get(i).get(nom);
                if(res != null) {
                    if(res.getType() == exp.getType()) { variables.get(i).put(nom,exp); }
                    else { throw new Exception("Le type de "+ nom + "est incompatible avec le type de la nouvelle valeur"); }
                }
                res = constantes.get(i).get(nom);
                if (res != null) { throw new Exception("Vous essayez de changer la valeur d'une constante"); }
            }
            throw new Exception("La valeur "+nom+"n'a jamais été initialisée");
                
	}

	// put(nom, exp, isConstante) 	ajoute <nom, exp> dans l'une des deux linkedlist
	public void put(String nom, Expression exp, boolean isConstante) throws Exception{
            if(!variables.getLast().containsKey(nom) && !constantes.getLast().containsKey(nom)) {
                if(isConstante){
                    constantes.getLast().put(nom,exp);
                }
                else { variables.getLast().put(nom,exp); }
            } else {
                throw new Exception("La variable " + nom + " a déjà été initialisée!");
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
            variables.pop();
            constantes.pop();
            this.taille--;
	}
}
