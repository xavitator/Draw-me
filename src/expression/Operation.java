package expression;

import ast.Type;
import ast.ValueEnv;
import exception.ParserException;

/**
 * Classe pour une opération
 * @author DURAND-MARAIS
 */
public class Operation extends Expression{
    Expression exp1;
    Expression exp2;
    String operateur;
    static final String[] possibleValue = {"+","-","/","*",">","<","<=",">=","==","!=","&&","||"};

    /** construction d'une expression avec un opérateur (on donne le String correspondant) et les deux expressions autour */
    public Operation(int line, int column, Expression exp1, Expression exp2, String operateur){
        super(line,column);
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operateur = operateur;
    }

    /** retourne le type que va renvoyer l'opération */
    public Type getType(){
        if(operateur.matches("[\\+-/\\*]")) return Type.INT;
        else return Type.BOOLEAN;
    }

    /** on récupère la valeur de l'opération, ce que renvoie l'opération */
    public String getValue(ValueEnv val) throws Exception {
        if(debugMode()){debug();}
        switch(operateur){
        case "+": return sum(val);
        case "-": return soustract(val);
        case "/": return divise(val);
        case "*": return prod(val);
        case ">": return compare(val);
        case "<": return compare(val);
        case "<=": return compare(val);
        case ">=": return compare(val);
        case "==": return equal(val);
        case "!=": return negate(val);
        case "&&": return and(val);
        case "||": return or(val);
        default: return null;
        }
    }

    /** retourne la valeur de la somme des deux expressions (en forme d'int)*/
    public String sum(ValueEnv val) throws Exception {
        try{
            int a = Integer.parseInt(exp1.getValue(val));
            int b = Integer.parseInt(exp2.getValue(val));
            return String.valueOf(a+b);
        }
        catch(NumberFormatException exception){
            System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
            return null;
        }
    }

    /** retourne la valeur de la soustraction des deux expressions (en forme d'int)*/
    public String soustract(ValueEnv val) throws Exception {
        try{
            int a = Integer.parseInt(exp1.getValue(val));
            int b = Integer.parseInt(exp2.getValue(val));
            return String.valueOf(a-b);
        }
        catch(NumberFormatException exception){
            System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
            return null;
        }
    }

    /** retourne la valeur de la division des deux expressions (en forme d'int)*/
    public String divise(ValueEnv val) throws Exception {
        try{
            int a = Integer.parseInt(exp1.getValue(val));
            int b = Integer.parseInt(exp2.getValue(val));
            return String.valueOf(a/b);
        }
        catch(NumberFormatException exception){
            System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
            return null;
        }
    }

    /** retourne la valeur de le produit des deux expressions (en forme d'int)*/
    public String prod(ValueEnv val) throws Exception {
        try{
            int a = Integer.parseInt(exp1.getValue(val));
            int b = Integer.parseInt(exp2.getValue(val));
            return String.valueOf(a*b);
        }
        catch(NumberFormatException exception){
            System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
            return null;
        }
    }

    /** retourne la valeur de la comparaison des deux expressions en fonction de l'opération de comparaison (en forme de boolean)*/
    public String compare(ValueEnv val) throws Exception {
        try{
            int a = Integer.parseInt(exp1.getValue(val));
            int b = Integer.parseInt(exp2.getValue(val));
            switch(operateur){
            case ">": return String.valueOf(a>b);
            case "<":  return String.valueOf(a<b);
            case "<=": return String.valueOf(a<=b);
            case ">=": return String.valueOf(a>=b);
            }
        }
        catch(NumberFormatException exception){
            System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
        }
        return null;
    }

    /** retourne la valeur de l'égalité des deux expressions (en forme de boolean)*/
    public String equal(ValueEnv val) throws Exception {
        String e1 = exp1.getValue(val);
        String e2 = exp2.getValue(val);
        if(e1.matches("[Tt]rue | [Ff]alse") && e2.matches("[Tt]rue | [Ff]alse")){
            return String.valueOf(e1.toLowerCase().equals(e2.toLowerCase()));
        }
        else{
            try{
                int a = Integer.parseInt(e1);
                int b = Integer.parseInt(e2);
                return String.valueOf(a==b);
            }
            catch(NumberFormatException exception){
                System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
                return null;
            }
        }
    }

    /** retourne la valeur de la différence des deux expressions (en forme de boolean)*/
    public String negate(ValueEnv val) throws Exception{
        String e1 = exp1.getValue(val);
        String e2 = exp2.getValue(val);
        if(e1.matches("[Tt]rue | [Ff]alse") && e2.matches("[Tt]rue | [Ff]alse")){
            return String.valueOf(! e1.toLowerCase().equals(e2.toLowerCase()));
        }
        else{
            try{
                int a = Integer.parseInt(e1);
                int b = Integer.parseInt(e2);
                return String.valueOf(a!=b);
            }
            catch(NumberFormatException exception){
                System.out.println("Erreur de typage à la ligne "+line+" et à la colonne "+column);
                return null;
            }
        }
    }

    /** retourne la valeur de l'opération '&&'(en java) des deux expressions (en forme de boolean)*/
    public String and(ValueEnv val) throws Exception {
        String e1 = exp1.getValue(val);
        String e2 = exp2.getValue(val);
        return String.valueOf(e1.toLowerCase().equals("true") && e2.toLowerCase().equals("true"));
    }

    /** retourne la valeur de l'opération '||'(en java) des deux expressions (en forme de boolean)*/
    public String or(ValueEnv val) throws Exception {
        String e1 = exp1.getValue(val);
        String e2 = exp2.getValue(val);
        return String.valueOf(e1.toLowerCase().equals("true") || e2.toLowerCase().equals("true"));
    }

    /** on vérifie si le type de l'expression correspond à ce qu'il faut */
    public void verifyType() throws Exception{
        exp1.verifyType();
        exp2.verifyType();
        if(wrongOperator()) throw new ParserException("Il y a un problème de typage.", line, column);
        Type current;
        if(operateur.matches("[\\+-/\\*]")) current = Type.INT;
        else current = Type.BOOLEAN;
        if(operateur.matches("[\\+-/\\*<>]")){
            if(exp1.getType() != Type.INT || exp2.getType() != Type.INT) throw new ParserException("Il y a un problème de typage.", line, column);
        }
        else if (operateur.matches("&& | \\|\\|")){
            if(exp1.getType() != Type.BOOLEAN || exp2.getType() != Type.BOOLEAN) throw new ParserException("Il y a un problème de typage.", line, column);
        }
        else if (operateur.equals("!=") || operateur.equals("==")){
            if((exp1.getType() != Type.INT && exp1.getType() != Type.BOOLEAN) || (exp2.getType() != Type.INT && exp2.getType() != Type.BOOLEAN)) throw new ParserException("Il y a un problème de typage.", line, column);
        }
        else throw new ParserException("Il y a un problème de typage.", line, column);
    }

    /** on vérifie si l'opérateur est un bon caractère */
    public boolean wrongOperator(){
        for (int i = 0; i < possibleValue.length; i++) {
            if(operateur.equals(possibleValue[i])) return false;
        }
        return true;
    }

    /** Debug */
    public void debug(){
        System.out.println("Opération => type: "+operateur);
    }
}