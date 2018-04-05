package parser;
/**
 * Classe qui se charge de parser les fichiers
 * @author DURAND-MARAIS
 */

import ast.*;
import exception.ParserException;
import expression.Expression;
import expression.Identificateur;
import expression.Operation;
import expression.Value;

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
 * 			| identificateur = expression
 *                      | If expr Then instruction Autre
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
        // Création de l'ast.AST d'origine
        AST tmp =  suite_instruction(new AST(reader.line(),reader.column()));
        reader.eat(Sym.EOF);
        return tmp;
    }

    /**
     * Instruction 
     * @return l'ast.AST représentant l'instruction
     */
    public AST instruction() throws Exception {
        int line = reader.line();
        int column = reader.column();
        if (reader.check(Sym.BEGIN))
            {
                /* instruction -> Begin suite_instruction End*/
                reader.eat(Sym.BEGIN);
                AST tmp = new AST(line,column);
                tmp = this.suite_instruction(tmp);
                reader.eat(Sym.END);
                return tmp; 
            }
        else if (reader.check(Sym.DRAWCIRCLE))
            {
                /* instruction -> ast.DrawCircle ( exp, exp, couleur) */
                reader.eat(Sym.DRAWCIRCLE);
                reader.eat(Sym.LPAR);
                Expression x = this.non_term_exp();
                Expression y = this.non_term_exp();
                Expression z = this.non_term_exp();
                Color col = reader.getColorValue();
                reader.eat(Sym.COULEUR);
                reader.eat(Sym.RPAR);
                return new DrawCircle(line,column,x,y,z,col);
            }
        else if (reader.check(Sym.DRAWRECT))
            {
                /* instruction -> ast.DrawRect(exp,exp,exp,couleur) */
                reader.eat(Sym.DRAWRECT);
                reader.eat(Sym.LPAR);
                Expression x = this.non_term_exp();
                Expression y = this.non_term_exp();
                Expression w = this.non_term_exp();
                Expression h = this.non_term_exp();
                Color col = reader.getColorValue();
                reader.eat(Sym.COULEUR);
                reader.eat(Sym.RPAR);
                return new DrawRect(line,column,x,y,w,h,col);
            }
        else if (reader.check(Sym.FILLCIRCLE))
            {
                /* instruction -> ast.FillCircle(exp,exp,exp)*/
                reader.eat(Sym.FILLCIRCLE);
                reader.eat(Sym.LPAR);
                Expression x = this.non_term_exp();
                Expression y = this.non_term_exp();
                Expression z = this.non_term_exp();
                Color col = reader.getColorValue();
                reader.eat(Sym.COULEUR);
                reader.eat(Sym.RPAR);
                return new FillCircle(line, column, x, y, z, col);
            }
        else if (reader.check(Sym.FILLRECT))
            {
                /* instruction -> ast.FillRect (exp,exp,exp,exp, couleur) */
                reader.eat(Sym.FILLRECT);
                reader.eat(Sym.LPAR);
                Expression x = this.non_term_exp();
                Expression y = this.non_term_exp();
                Expression w = this.non_term_exp();
                Expression h = this.non_term_exp();
                Color col = reader.getColorValue();
                reader.eat(Sym.COULEUR);
                reader.eat(Sym.RPAR);
                return new FillRect(line, column ,x,y,w,h,col);
        }
        else if (reader.check(Sym.CONST))
            {
                /* instruction -> Const identificateur = exp */
                reader.eat(Sym.CONST);
                String name = reader.getStringValue();
                reader.eat(Sym.IDENT);
                reader.eat(Sym.ASSIGNATION);
                Expression exp = this.non_term_exp();
                return new Assign(line,column,true,name,exp);
            }
        else if (reader.check(Sym.VAR))
            {
                /* Var identificateur = exp */
                reader.eat(Sym.VAR);
                String name = reader.getStringValue();
                reader.eat(Sym.IDENT);
                reader.eat(Sym.ASSIGNATION);
                Expression exp = this.non_term_exp();
                return new Assign(line, column, false, name, exp);
            }
        else if(reader.check(Sym.IDENT))
            {
                /* identificateur = exp */
                String name = reader.getStringValue();
                reader.eat(Sym.IDENT);
                reader.eat(Sym.ASSIGNATION);
                Expression exp = this.non_term_exp();
                return new Change(line, column, name, exp);
            }
        else if (reader.check(Sym.IF))
            {
                reader.eat(Sym.IF);
                Expression exp = this.non_term_exp();
                reader.eat(Sym.THEN);
                AST ifAST = this.instruction();
                AST elseAST = new AST(line,column);
                if (reader.check(Sym.ELSE))
                    {
                        reader.eat(Sym.ELSE);
                        elseAST = instruction();
                    }
                return new Condition(line,column,exp, ifAST, elseAST);
            }
        else
            {
                throw new ParserException("Motif non reconnu", reader.line(),reader.column());
            }
    }

    /**
     * Axiome et partie suite d'instruction de la grammaire 
     * @param current l'ast.AST représentant la file d'exécution
     * @return l'ast.AST courant
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
