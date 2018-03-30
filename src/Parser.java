/**
 * Classe qui se charge de parser les fichiers
 * @author DURAND-MARAIS
 */

import java.io.IOException;
import java.util.List;

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
 * Autre → Else instruction
 * 			| 𝜀
 * expr → Nombre | identificateur | ( expr operateur expr ) | True | False
 * operateur → Op | relation
 * op → + | - | / | *
 * relation → > | < | <= | >= | == | != | && | ||
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
        return null;
    }

    public List<AST> blocInstruction(){
        return null;
    }

    public AST instruction(){
        return null;
    }

    public AST autre(){
        return null;
    }

    public AST expression(){
        return null;
    }

    public AST operateur(){
        return null;
    }

    public AST op(){
        return null;
    }

    public AST relation(){
    	return null;
    }


    /** renvoie si on a atteint la fin du fichier */
    public boolean reachEnd(){
		return reader.isEmpty();
    }

    
}
