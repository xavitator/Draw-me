package lexer;

import parser.*;
import parser.Sym;
import parser.token.*;

%%
%public
%class Lexer
%line
%column
%unicode
%type Token

%{
  class LexerException extends Exception{
    public LexerException(int line, int column, String caract){
      super("Le caractere "+caract.replace("\\","\\\\")+" a la ligne "+line+" et a la colonne "+column+" n'est pas reconnu par la grammaire.");
    }
  }
%}

%yylexthrow{
  //Exception qu’on va créer nous meme pour l’analyse lexicale
  Exception, LexerException
%yylexthrow}

//definition des differentes variables

commentaire = ("/*"[^]"*/") | ("//"[^\n\r]*)

hex = [0-9A-F]
nombre = [0-9]+ 
couleur = "#"{hex}{hex}{hex}{hex}{hex}{hex}
operateur = "+" | "-" | "/" | "*"
relation = ">" | "<" | "==" | "<=" | ">=" | "!=" |"&&" | "||"
identificateur = [a-z][a-zA-Z_]*
string = "\""[^\"]+[^\\]"\""
blanc = [\n\ \t\r]

%%
{commentaire}              {}
{couleur}  		   {return new ColorToken(Sym.COULEUR,yyline,yycolumn,yytext());}
{nombre}  		   {return new IntToken(Sym.INT,yyline,yycolumn,yytext());}
{operateur}		   {return new StringToken(Sym.OPERATEUR,yyline,yycolumn,yytext());}
{relation}         	   {return new StringToken(Sym.RELATION,yyline,yycolumn, yytext());}
";"                	   {return new Token(Sym.POINTVIRGULE,yyline,yycolumn);}
[Tt]"rue"     		   {return new BooleanToken(Sym.BOOLEAN,yyline,yycolumn,yytext());}
[Ff]"alse"    		   {return new BooleanToken(Sym.BOOLEAN,yyline,yycolumn,yytext());}
"Begin"         	   {return new Token(Sym.BEGIN,yyline,yycolumn);}
"End"           	   {return new Token(Sym.END,yyline,yycolumn);}
"If"            	   {return new Token(Sym.IF,yyline,yycolumn);} 
"Else"          	   {return new Token(Sym.ELSE,yyline,yycolumn);}
"Then"          	   {return new Token(Sym.THEN,yyline,yycolumn);}
"("             	   {return new Token(Sym.LPAR,yyline,yycolumn);}
")"             	   {return new Token(Sym.RPAR,yyline,yycolumn);}
"DrawCircle"    	   {return new Token(Sym.DRAWCIRCLE,yyline,yycolumn);}
"FillCircle"    	   {return new Token(Sym.FILLCIRCLE,yyline,yycolumn);}
"DrawRect"      	   {return new Token(Sym.DRAWRECT,yyline,yycolumn);}
"FillRect"      	   {return new Token(Sym.FILLRECT,yyline,yycolumn);}
"="             	   {return new Token(Sym.ASSIGNATION,yyline,yycolumn);}
"Const"         	   {return new Token(Sym.CONST,yyline,yycolumn);}
"Var"           	   {return new Token(Sym.VAR,yyline,yycolumn);}
{identificateur}	   {return new StringToken(Sym.IDENT,yyline,yycolumn, yytext());}
{string}	           {return new StringToken(Sym.STRING,yyline,yycolumn,yytext().replace("\"",""));}
{blanc}+|","  		   {}
<<EOF>>                    {return new Token(Sym.EOF,yyline,yycolumn);}
[^]                        {throw new LexerException(yyline,yycolumn, yytext());}
