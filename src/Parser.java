/**
 * Classe qui se charge de parser les fichiers
 * @author DURAND-MARAIS
 */

import java.io.IOException;
import java.lang.Exception;
import java.awt.Color;


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

    /** Axiome 
     * programme -> suite_instruction
     */
    public AST progNonTerm() throws Exception {
        // Création de l'AST d'origine
        AST tmp =  suite_instruction(new AST(reader.line(),reader.column()));
        reader.eat(Sym.EOF);
        return tmp;
    }

    /**
     * Instruction 
     * @return l'AST représentant l'instruction 
     */
    public AST instruction() throws Exception {
        if (reader.check(Sym.BEGIN)) {
            /* instruction -> Begin suite_instruction End*/
            reader.eat(Sym.BEGIN);
            AST tmp = new AST(reader.line(),reader.column());
            tmp = this.suite_instruction(tmp);
            reader.eat(Sym.END);
            return tmp; 
        } else if (reader.check(Sym.DRAWCIRCLE)){
            /* instruction -> DrawCircle ( exp, exp, couleur) */
            reader.eat(Sym.DRAWCIRCLE);
            reader.eat(Sym.LPAR);
            Expression x = this.non_term_exp();
            Expression y = this.non_term_exp();
            Expression z = this.non_term_exp();
            Color col = reader.getColorValue();
            reader.eat(Sym.COULEUR);
            reader.eat(Sym.RPAR);
            return new DrawCircle(reader.line(),reader.column(),x,y,z,col);
        } else if (reader.check(Sym.DRAWRECT)) {
            /* instruction -> DrawRect(exp,exp,exp,couleur) */
            reader.eat(Sym.DRAWRECT);
            reader.eat(Sym.LPAR);
            Expression x = this.non_term_exp();
            Expression y = this.non_term_exp();
            Expression w = this.non_term_exp();
            Expression h = this.non_term_exp();
            Color col = reader.getColorValue();
            reader.eat(Sym.COULEUR);
            reader.eat(Sym.RPAR);
            return new DrawRect(reader.line(),reader.column(),x,y,w,h,col);
        } else if (reader.check(Sym.FILLCIRCLE)) {
            /* instruction -> FillCircle(exp,exp,exp)*/
            reader.eat(Sym.FILLCIRCLE);
            reader.eat(Sym.LPAR);
            Expression x = this.non_term_exp();
            Expression y = this.non_term_exp();
            Expression z = this.non_term_exp();
            Color col = reader.getColorValue();
            reader.eat(Sym.COULEUR);
            reader.eat(Sym.RPAR);
            return new FillCircle(reader.line(),reader.line(),x,y,z,col);
        } else if (reader.check(Sym.FILLRECT)) {
            /* instruction -> FillRect (exp,exp,exp,exp, couleur) */
            reader.eat(Sym.FILLRECT);
            reader.eat(Sym.LPAR);
            Expression x = this.non_term_exp();
            Expression y = this.non_term_exp();
            Expression w = this.non_term_exp();
            Expression h = this.non_term_exp();
            Color col = reader.getColorValue();
            reader.eat(Sym.COULEUR);
            reader.eat(Sym.RPAR);
            return new FillRect(reader.line(),reader.column(),x,y,w,h,col);
        } else if (reader.check(Sym.CONST)) {
            /* instruction -> Const identificateur = exp */
            reader.eat(Sym.CONST);
            String name = reader.getStringValue();
            reader.eat(Sym.IDENT);
            reader.eat(Sym.ASSIGNATION);
            Expression exp = this.non_term_exp();
            return new Assign(reader.line(),reader.column(),true,name,exp);
        }  else {
            // Futur levée d'Exception
            System.out.println("Erreur dans instruction !!!");
            System.exit(-1);
            return null;
        }
    }

    /**
     * Axiome et partie suite d'instruction de la grammaire 
     * @param current l'AST représentant la file d'exécution
     * @return l'AST courant
     */
    public AST suite_instruction (AST current) throws Exception {
        if(reader.check(Sym.END) || reader.check(Sym.EOF)){
            /* suite_instruction -> Epsilon*/
            return current;
        } else {
            /* suite_instruction -> instruction ; suite_instruction*/
            current.addNext(this.instruction());
            reader.eat(Sym.POINTVIRGULE);
            return this.suite_instruction(current);
        }
    }

    /**
     * Partie des expressions de la grammaire 
     * @return l'expression de la grammaire 
     */
    public Expression non_term_exp() throws Exception {
        if (reader.check(Sym.INT)) {
            /* exp -> nombre */
            Expression toReturn = new Value(reader.line(),reader.column(),reader.getIntValue());
            reader.eat(Sym.INT);
            return toReturn;
        } else if (reader.check(Sym.IDENT)) {
            /* exp -> identificateur */
            String ident = reader.getStringValue();
            reader.eat(Sym.IDENT);
            return new Identificateur(reader.line(), reader.column(),ident);
        } else {
            /* exp -> ( exp operateur exp ) */
            reader.eat(Sym.LPAR);
            Expression left = non_term_exp();
            String operateur = reader.getStringValue();
            reader.eat(Sym.OPERATEUR);
            Expression right = non_term_exp();
            reader.eat(Sym.RPAR);
            return new Operation(reader.line(),reader.column(),left,right,operateur);
        }
    }
}
