/**
 * Classe qui se charge de parser les fichiers
 * @author DURAND-MARAIS
 */

/**
 * Gammaire :
 * 
 * programme → blocInstruction
 * blocInstruction → Instruction ; blocInstruction | 𝜀
 * instruction → Begin blocInstruction End 
 * 			| DrawCircle ( expr , expr , expr , couleur ) 
 * 			| FillCircle ( expr , expr , expr , couleur) 
 * 			| DrawRect ( expr , expr , expr , expr , couleur ) 
 * 			| FillRect ( expr , expr , expr , expr , couleur )
 * 			| Const identificateur = expr 
 * 			| Var identificateur = expr
 * 			| If expr Then instruction Autre
 * Autre → Elseif expr Then instruction Autre
 * 		| Else instruction
 * 		| 𝜀
 * expr → Nombre | identificateur | ( expr operateur expr )
 * operateur → Op | relation
 * op → + | - | / | *
 * relation → > | < | <= | >= | == | && | ||
 */
public class Parser{

	protected LookAhead1 reader;
    
    /****************
     * Constructeur *
     ****************/

    /** Constructeur par défaut */
    public Parser(LookAhead1 r) throws IOException {
		reader = r;
    }

    public AST programme(){

    }

    public AST blocInstruction(){

    }

    public AST instruction(){

    }

    public AST autre(){

    }

    public AST expression(){

    }

    public AST operateur(){

    }

    public AST op(){

    }

    public AST relation(){
    	
    }


    /** renvoie si on a atteint la fin du fichier */
    public boolean reachEnd(){
		return reader.isEmpty();
    }

    
}
